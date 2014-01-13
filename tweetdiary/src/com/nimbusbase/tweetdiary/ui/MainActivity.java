package com.nimbusbase.tweetdiary.ui;

import java.util.ArrayList;
import java.util.Date;

import nimbusbase.utils.DBAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nimbusbase.tweetdiary.R;
import com.nimbusbase.tweetdiary.model.diaryItem;
import com.nimbusbase.tweetdiary.utils.Utils;
import com.slidingmenu.lib.SlidingMenu;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener {

	private SlidingMenu menu;
	private Fragment mContent;

	 private PopMenu popMenu;
	 
		SharedPreferences sharedPreferences = null;
	 
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContent = new SwipeActivity();
		
//		
//		
//		DBAdapter DB =  new  DBAdapter(MainActivity.this.getApplicationContext(), "A");
//		String[] tables = DB.getAllTable();
//		Boolean  flag = false;
//		for( String t : tables){
//			if(t.equals("Entry")){
//				flag = true;
//			}
//		}
//		if(!flag){
//			String[] table_fileds = {"id","text","create_time","tags","gid","synced","time"};
//			DB.createTable("Entry", table_fileds);
//		}
//		
		
		
		// set the Above View
		setContentView(R.layout.activity_main);
		ViewGroup vg = (ViewGroup)findViewById(R.id.main_root);
		
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();

		ImageButton btnList = (ImageButton) findViewById(R.id.btn_list);
		ImageButton btnCompose = (ImageButton) findViewById(R.id.btn_compose);
		
		btnList.setOnClickListener(this);
		btnCompose.setOnClickListener(this);
		
		Utils.setFontAllView(vg);


		findViewById(R.id.tv_title).setOnClickListener(this);
		popMenu = new PopMenu(this);
        popMenu.addItem("ALL");
        sharedPreferences = getSharedPreferences("tweetDiay", Context.MODE_PRIVATE);
	 	
        
        
   
//         OnItemClickListener popmenuItemClickListener = new OnItemClickListener() {
//      		@Override
//      		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//      			System.out.println("下拉菜单点击" + position);
//      			popMenu.dismiss();
//      		}
//     	};
       
   
		
	}

	
	
	public void onItemClick(int index) {
        Toast.makeText(this, "item clicked " + index + "!", Toast.LENGTH_SHORT).show();
    }
	
	
	
	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View v) {
		

		 
		
		 if(v.getId() == R.id.tv_title){
			 DBAdapter DB2 = ((SwipeActivity)mContent).DB;
				Cursor cursor2 = DB2.getReadableDatabase().query(true, "Entry",new String[]{"tags"}, null , null, null, null, null, null, null);
		        cursor2.moveToFirst();
		        if(cursor2.getCount() > 0){
		        	popMenu.clearItem();
		        	popMenu.addItem("ALL");
		   	    for(int i = 0; i < cursor2.getCount(); i++){
		   	    	if(cursor2.getString(0) != null && !cursor2.getString(0).equals(""))
		   	    		popMenu.addItem(cursor2.getString(0));
		   	    	cursor2.moveToNext();
		   	    } 
		       }
				
             popMenu.showAsDropDown(v); 
             
             
         }
		 
		 
		if (v.getId() == R.id.btn_list) {
		  
			if (menu.isMenuShowing()) {
				menu.showContent();
			} else {
				menu.showMenu();
			}
			
			
		}
		
		if(v.getId() == R.id.btn_compose){
		 
			
			
			// load  layout
			LayoutInflater inflater = (LayoutInflater) MainActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.input_add, null);
			
			 
		     String[] category = null;
			DBAdapter DB = ((SwipeActivity)mContent).DB;
			Cursor cursor = DB.getReadableDatabase().query(true, "Entry",new String[]{"tags"}, null, null, null, null, null, null, null);
	       cursor.moveToFirst();
	       category = new String[cursor.getCount()];
	       if(cursor.getCount() > 0){ 
	    	   for(int i = 0; i < cursor.getCount(); i++){
	    		  String  t =  cursor.getString(0);;
	    		   if(t != null && !t.equals("")){
	    			   category[i] =t;
	    		   }
	    		   cursor.moveToNext();
	    	   } 
	       }
	    
		   System.out.println(" this.getApplicationContext()" + this.getApplicationContext());
		   System.out.println(" android.R.layout.simple_dropdown_item_1line" +  android.R.layout.simple_dropdown_item_1line);
		   System.out.println("category" +category);
		   
		   ArrayAdapter<String> aa = new ArrayAdapter<String>(
		            this.getApplicationContext(),
		            android.R.layout.simple_dropdown_item_1line,
		            category);
		   
		        final AutoCompleteTextView actv = (AutoCompleteTextView)  layout.findViewById(R.id.auto);
		        //设置Adapter
		        System.out.println(aa.toString());
		        System.out.println(actv.toString());
		        actv.setAdapter(aa);
		        actv.setThreshold(0);
		       
			    actv.setOnTouchListener( new  OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						((AutoCompleteTextView) v).showDropDown();
						
						return false;
					}
				});
	       
	       
			//pop dialog
			new AlertDialog.Builder(MainActivity.this,0x00000003) 
					.setTitle("Add New Item") 
					.setIcon(android.R.drawable.ic_dialog_info) 
					.setMessage("Please input  content:")
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

										Toast.makeText(getApplicationContext(),
												"please input  content", Toast.LENGTH_SHORT)
												.show();
									} else {
										  //add to list
									 
										 final String  tags = actv.getText().toString();
										
										diaryItem item =  new diaryItem(); 
										item.setText(str);
										item.setCreate_time(new Date().toGMTString());
										item.setTags(tags);
										//item.setCreate_time(new Date().toLocaleString());
										
										
										  
								 		ContentValues map = new ContentValues();
										map.put("text", item.getText() );
										map.put("create_time",item.getCreate_time().toString());
								        map.put("tags", tags);
										 
										//put to top  each time  
										((SwipeActivity)mContent).lstDiarys.add(0, item);
										 
										DBAdapter DB = ((SwipeActivity)mContent).DB;
										long rowID =DB.insert("Entry", map);
										String  rowIDString =  String.valueOf(rowID);
										Cursor cursor = DB.query("NB_Metadata", "tableName=? and  rowID=?", new String[]{"Entry",rowIDString  });
										cursor.moveToFirst();
										if(cursor.getCount()> 0){
											String  NID =  cursor.getString(cursor.getColumnIndex("NID"));
											ContentValues cv = new ContentValues();
											cv.put("ID", NID);
											cv.put( "create_time", new Date().toGMTString());
											
											DB.update("Entry", "rowID=?", new String[]{rowIDString}, cv); 
										}
										
										
										 
										
										Toast.makeText(MainActivity.this,
												str, Toast.LENGTH_SHORT)
												.show();
									}

								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {  
								public void onClick(
										DialogInterface dialoginterface, int i) {
									Toast.makeText(MainActivity.this,
											"Cancel add Item", Toast.LENGTH_SHORT)
											.show(); 
								}
							}).show();
			
			
			
			
			
			
			
			
			
			
			
		}

	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		menu.showContent();
	}

	

class PopMenu {
	private ArrayList<String> itemList;
	private Context context;
	private PopupWindow popupWindow ;
	private ListView listView;
	//private OnItemClickListener listener;
	

	public PopMenu(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;

		itemList = new ArrayList<String>(5);
		
		View view = LayoutInflater.from(context).inflate(R.layout.popmenu, null);
        
        //设置 listview
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(new PopAdapter());
        listView.setFocusableInTouchMode(true);
        listView.setFocusable(true);
        
        //  popupWindow = new PopupWindow(view, 100, LayoutParams.WRAP_CONTENT);
        popupWindow = new PopupWindow(view, 
        		context.getResources().getDimensionPixelSize(R.dimen.popmenu_width), 
        		LayoutParams.WRAP_CONTENT,true);
         
        
        
        
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
        
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
  
	}

	
	
	public OnItemClickListener getListener(){
		return  this.listView.getOnItemClickListener();
	}
	
 
	
	
	
	
	
	
	
	//设置菜单项点击监听器
	public void setOnItemClickListener(OnItemClickListener listener) {
		//this.listener = listener;
		listView.setOnItemClickListener(listener);
		
	}

	//批量添加菜单项
	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}

	//单个添加菜单项
	public void addItem(String item) {
		itemList.add(item);
	}
	public  void  clearItem(){
		itemList.clear();
	}

	//下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
		popupWindow.showAsDropDown(parent, 10, 
				//保证尺寸是根据屏幕像素密度来的
				context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
		
		// 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        //刷新状态
        popupWindow.update();
     
	}
	
	//隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	// 适配器
	private final class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.pomenu_item, null);
				holder = new ViewHolder();

				convertView.setTag(holder);

				holder.groupItem = (TextView) convertView.findViewById(R.id.textView);
				

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.groupItem.setText(itemList.get(position));
			
			
			final int t = position;
			final String category = itemList.get(t); 
			holder.groupItem.setOnClickListener(new  OnClickListener() {
				
				@Override
				public void onClick(View v) {
				 	Toast.makeText(context, "下拉菜单点击:" + category, 2000).show(); 
				 	
				 	
				    Editor editor = sharedPreferences.edit();
				 	editor.putString("category", category); 
				 	editor.putString("key", "");
				 	editor.putString("change", "true");
				 	editor.commit();
				 	dismiss();
				 	
				 	
					
				}
			});
			
			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}
}

}







