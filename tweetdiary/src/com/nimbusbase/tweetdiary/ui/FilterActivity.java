package com.nimbusbase.tweetdiary.ui;

import java.util.ArrayList;
import java.util.Date;

import nimbusbase.client.GDriveClient;
import nimbusbase.model.GDriveModel;
import nimbusbase.model.GeneralModel;
import nimbusbase.utils.DBAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nimbusbase.tweetdiary.R;
import com.nimbusbase.tweetdiary.adapter.SwipeAdapter;
import com.nimbusbase.tweetdiary.model.DiaryList;
import com.nimbusbase.tweetdiary.model.VideoItem;
import com.nimbusbase.tweetdiary.model.diaryItem;
import com.nimbusbase.tweetdiary.utils.Utils;

public class FilterActivity extends Fragment implements OnClickListener,
		OnItemClickListener {

	ListView listView;
	int lastIndex = -1;
	ArrayList<VideoItem> lstVideos;
	
	public ArrayList<diaryItem>  lstDiarys;
	ArrayList<diaryItem> lstDiarys2 = new  ArrayList<diaryItem>();
	
	
	View vw_layout;
	CellAnimationOut cellAnimationOutListener = new CellAnimationOut();
	CellAnimationIn cellAnimationInListener = new CellAnimationIn();
	private Animation mCellSlideInRight;
	private Animation mCellSlideOutRight;
    public  static  boolean  changeLocal;
	
	String  filterKey;
	DBAdapter   DB; 
	GDriveModel gdriveModel;
	GDriveClient gDriveClient;
	GeneralModel generalModel;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}
		vw_layout = inflater.inflate(R.layout.activity_swipe, container, false);

		mCellSlideOutRight = AnimationUtils.loadAnimation(getActivity(),
				R.anim.cell_right_out);
		mCellSlideInRight = AnimationUtils.loadAnimation(getActivity(),
				R.anim.cell_right_in);
		mCellSlideOutRight.setAnimationListener(cellAnimationOutListener);
		mCellSlideInRight.setAnimationListener(cellAnimationInListener);

		// get list view
		listView = (ListView) this.vw_layout.findViewById(R.id.lst_diarys);
		//lstVideos = VideoList.getVideoList();
		lstDiarys =  DiaryList.getDiaryList();
		
		
		listView.setAdapter(new SwipeAdapter(getActivity(), lstDiarys, this));

		this.listView.setOnItemClickListener(this);

		Utils.setFontAllView((ViewGroup)vw_layout);
		
		
    	//init();
		filterKey  = getArguments().getString("key");
    	syncData2UI(3000);
	 
		return vw_layout;
	}

	@Override
	public void onItemClick(AdapterView<?> adp, View listview, int position,
			long id) {

		if (adp != null && adp.getAdapter() instanceof SwipeAdapter) {
			SwipeAdapter swipeAdp = (SwipeAdapter) adp.getAdapter();
			diaryItem itm = swipeAdp.getItem(position);
		
			
		//	itm.set_selected(!itm.get_selected());
			
			int currentIndex = position - listView.getFirstVisiblePosition()
					- listView.getHeaderViewsCount();

			lastIndex = lastIndex - listView.getFirstVisiblePosition()
					- listView.getHeaderViewsCount();
			if (lastIndex < 0 || lastIndex >= listView.getChildCount())
				cellAnimationOutListener.setPreviousView(null);

			View _v = listView.getChildAt(currentIndex);

			View vwLayer1 = _v.findViewById(R.id.view_layer1);
			View vwLayer2 = _v.findViewById(R.id.view_layer2);

			cellAnimationOutListener.setCurrentView(_v);
			vwLayer1.startAnimation(mCellSlideOutRight);

			int height = vwLayer1.getHeight();
			int width = vwLayer1.getWidth();
			vwLayer2.setLayoutParams(new RelativeLayout.LayoutParams(width,
					height));
			vwLayer2.setVisibility(View.VISIBLE);

			lastIndex = position;
			
			
			Toast.makeText(FilterActivity.this.getActivity(), "current:" +position , Toast.LENGTH_SHORT).show();
			
		}
	}
	
	
	
	
	
	
	 
	
	// init   
		private void init() {
			
			
			
		    DB =  new  DBAdapter(FilterActivity.this.getActivity(), "D");
			String[] tables = DB.getAllTable();
			Boolean  flag = false;
			for( String t : tables){
				if(t.equals("Entry")){
					flag = true;
				}
			}
			if(!flag){
				String[] table_fileds = {"id","text","create_time","tags","gid","synced","time"};
				DB.createTable("Entry", table_fileds);
			}
			
			
			
			
			String[] tables2 = DB.getAllTable();
			Boolean  flag2 = false;
			for( String t : tables2){
				if(t.equals("commonRecords")){
					flag = true;
				}
			}
			if(!flag){
				String[] table_fileds = {"value","type","key"};
				DB.createTable("commonRecords", table_fileds);
			}
			
			
			
			
			gdriveModel =new GDriveModel(FilterActivity.this.getActivity(), "diary_app", "D",new String[]{"Entry"});	
			
			gdriveModel.getGdriveClient().authorize();
			gdriveModel.syncThread(3000);
		}
		
	
	
	
		
	

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		
		
		//edit 
		if (v.getId() == R.id.btn_love) {
			final Integer pos = (Integer) v.getTag();
		 
			
			// load  layout
			LayoutInflater inflater = (LayoutInflater) FilterActivity.this.getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.input_add, null);
			EditText inputStringr = (EditText) layout.findViewById(R.id.input_add_string);
			inputStringr.setText( lstDiarys.get(pos).getText());
			
			//pop dialog
			new AlertDialog.Builder(FilterActivity.this.getActivity(),0x00000003) 
					.setTitle("Edit Item") 
					.setIcon(android.R.drawable.ic_dialog_info) 
					.setMessage("Please input  new   content:")
					.setView(layout)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {

									EditText inputStringr = (EditText) layout
											.findViewById(R.id.input_add_string);

									String str = inputStringr.getText()
											.toString();

									if (str == null || str.equals("")) {

										Toast.makeText(getActivity().getApplicationContext(),
												"please input  new  content", Toast.LENGTH_SHORT)
												.show();
									} else {
										  //modify to list
										
									
										String id = lstDiarys.get(pos).getId();	
										ContentValues cv = new ContentValues();
										cv.put("text", str);
										DB.update("Entry","rowId=?", new String[]{id},cv );
										
										 
										
										Toast.makeText(FilterActivity.this.getActivity(),
												str + "current:" , Toast.LENGTH_SHORT)
												.show();
										 
										lstDiarys.get(pos).setText(str);
									}

								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {  
								public void onClick(
										DialogInterface dialoginterface, int i) {
									Toast.makeText(FilterActivity.this.getActivity(),
											"Cancel add Item", Toast.LENGTH_SHORT)
											.show();

								}
							}).show();
			
			
			  
			
			
		}

		
		//delete
		if (v.getId() == R.id.btn_like) {
			Integer pos = (Integer) v.getTag();
			String rowId = lstDiarys.get(pos).getId(); 
				lstDiarys.remove(lstDiarys.get(pos));
			ArrayList<diaryItem> temp =   new ArrayList<diaryItem>(lstDiarys);
			lstDiarys.clear();
			for(diaryItem it : temp){ 
				lstDiarys.add(it);
			}
			((SwipeAdapter)listView.getAdapter()).notifyDataSetChanged();
        	
			DB.delete("Entry", "rowId=?" , new String[]{rowId} );
			
			//lstDiarys = DiaryList.getDiaryList();
			//changeLocal = true;
 			
		 
		}

		
		//refresh
		if (v.getId() == R.id.btn_reload) {
			Integer pos = (Integer) v.getTag();
			Toast.makeText(getActivity(),
					"Reload button clicked at position " + pos,
					Toast.LENGTH_SHORT).show();
			
			int currentIndex = pos - listView.getFirstVisiblePosition() - listView.getHeaderViewsCount();
			  
			
			View _v = listView.getChildAt(currentIndex);   
			View vwLayer1 = _v.findViewById(R.id.view_layer1); 
			View vwLayer2 = _v.findViewById(R.id.view_layer2); 
	 
			cellAnimationInListener.setCurrentView(_v); 
			
			vwLayer1.startAnimation(mCellSlideInRight);
			vwLayer1.setVisibility(View.VISIBLE);
			vwLayer2.setVisibility(View.VISIBLE);
			cellAnimationOutListener.setPreviousView(null);
			cellAnimationInListener.setPreviousView(null);
		//	vwLayer2.startAnimation(mCellSlideInRight);
			

		}
		
	 

	}

	public class CellAnimationOut implements Animation.AnimationListener {
		private View previousView;
		private View currentView;

		public CellAnimationOut() {

		}

		public View getPreviousView() {
			return previousView;
		}

		public void setPreviousView(View previousView) {
			this.previousView = previousView;
		}

		public View getCurrentView() {
			return currentView;
		}

		public void setCurrentView(View currentView) {
			this.currentView = currentView;
		}

		@Override
		public void onAnimationStart(Animation arg0) {

			if (previousView != null) {
				View layer1 = previousView.findViewById(R.id.view_layer1);
				cellAnimationInListener.setCurrentView(previousView);
				layer1.startAnimation(mCellSlideInRight);
				previousView = null;

			}

			if (currentView != null) {
				View layer2 = currentView.findViewById(R.id.view_layer2);
				layer2.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
		}

		@Override
		public void onAnimationEnd(Animation arg0) {

			if (currentView != null) {
				View textView = currentView.findViewById(R.id.view_layer1);
				textView.setVisibility(View.GONE);
				//previousView = currentView;
				previousView = null;
			
			}
		}
	}

	public class CellAnimationIn implements Animation.AnimationListener {

		private View currentView;

		public CellAnimationIn() {

		}

		public void setPreviousView(Object object) {
			// TODO Auto-generated method stub
			
		}

		public View getCurrentView() {
			return currentView;
		}

		public void setCurrentView(View currentView) {
			this.currentView = currentView;
		}

		@Override
		public void onAnimationStart(Animation arg0) {

		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
		}

		@Override
		public void onAnimationEnd(Animation arg0) {
			if (currentView != null) {
				View layer1 = currentView.findViewById(R.id.view_layer1);
				View layer2 = currentView.findViewById(R.id.view_layer2);

				layer1.setVisibility(View.VISIBLE);
				layer2.setVisibility(View.INVISIBLE);

			}

		}
	}
	
	
	

	public void syncData2UI(int period) {
		DBAdapter db =  new DBAdapter(getActivity(), "D");
		Cursor cursor = db.getReadableDatabase().
				query("Entry", new String[]{"rowID","text","create_time"}, "text like'%" + filterKey +"%'", null, null, 
						null, "rowID  desc");
	 
		
	 
		cursor.moveToFirst();
		lstDiarys.clear();
		if(cursor.getCount() > 0 ){ 
			for(int i =0; i < cursor.getCount(); i++){
				diaryItem item  = new  diaryItem();
				item.setId(cursor.getString(0) );
				item.setText(	cursor.getString(1) ); 
				item.setCreate_time(cursor.getString(2) ); 
				lstDiarys.add(item);
				cursor.moveToNext();
			}
			
		}
		
	
		if(lstDiarys.size() > 0){
			@SuppressWarnings("deprecation")
			diaryItem[]  temp =   new  diaryItem[lstDiarys.size()];
			int index=0;
			for(diaryItem item : lstDiarys){
				temp[index++] =  item;
			}
			
			
			for(int i = 0;  i < temp.length; i++){
				for(int j = i;  j < temp.length; j++){
					Date a =  new Date(temp[i].getCreate_time());
					Date b =  new Date(temp[j].getCreate_time());
					
					if( b.getTime() > a.getTime()){
						diaryItem  t =   temp[j];
						int  k = j;
						while(k > i){
							temp[k] = temp [k-1];
							k--;
						}
						temp[i] = t;
						
					}
				}
			}
			
			lstDiarys.clear();
			for(int i = 0;  i < temp.length; i++){
				lstDiarys.add(temp[i]);
			}
				
		 
		}
		
		
		
		if( FilterActivity.this != null){
		  if( FilterActivity.this.getActivity() != null){
			  FilterActivity.this.getActivity().runOnUiThread(new Runnable()  
			    {
			        public void run()  
			        {   
			        	if(changeLocal == true){
			        		((SwipeAdapter)listView.getAdapter()).notifyDataSetChanged();
			        		changeLocal = false;
			        	}
			        	if(gdriveModel.changed  &&  gdriveModel.count == 0){
			        		((SwipeAdapter)listView.getAdapter()).notifyDataSetChanged();
			        		gdriveModel.changed = false;
			        	}
			        }
			    });
		 }
		}//end IF
	}
	
	
	
	
	
}
