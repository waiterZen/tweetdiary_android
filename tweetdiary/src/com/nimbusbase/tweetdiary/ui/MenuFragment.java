package com.nimbusbase.tweetdiary.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.nimbusbase.tweetdiary.R;
import com.nimbusbase.tweetdiary.adapter.MenuAdapter;
import com.nimbusbase.tweetdiary.model.MenuItem;
import com.nimbusbase.tweetdiary.utils.Utils;

public class MenuFragment extends Fragment implements OnItemClickListener {

	ListView listView;
	ArrayList<MenuItem> lstMenuItems;
	View vw_layout;
	ImageButton  filterBtn;
	EditText   filterContent;
	public String  keys;
	
	SharedPreferences sharedPreferences = null;
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
		
		
		vw_layout = inflater.inflate(R.layout.fragment_menu, container, false);

		this.initialiseMenuItems(savedInstanceState);
		
		Utils.setFontAllView((ViewGroup) vw_layout);
		
		
		
		
		filterBtn = (ImageButton) this.vw_layout.findViewById(R.id.btn_search);
		filterContent = (EditText) this.vw_layout.findViewById(R.id.text_search);
		 
		
		filterBtn.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 	
				CharSequence c =  filterContent.getText();
				Toast.makeText(MenuFragment.this.getActivity(), c, Toast.LENGTH_SHORT).show();
				  
				 Bundle bundle = new Bundle();  
	              bundle.putString("key", c.toString());  
	         
			 
				
			ListView adp = listView;
			if (adp != null && adp.getAdapter() instanceof MenuAdapter) {
				MenuAdapter menuAdp = (MenuAdapter) adp.getAdapter();
				MenuItem itm = menuAdp.getItem(0);
				Fragment newContent = itm.get_fragment();

				if (newContent == null) {

					newContent = Fragment.instantiate(getActivity(), itm
							.get_class().getName(), itm.get_args());
					itm.set_fragment(newContent);
					  newContent.setArguments(bundle);
					  switchFragment(newContent);
				}
				
			    
			    	newContent.getArguments().putString("key", c.toString());
			    	Editor editor = sharedPreferences.edit();
				 	editor.putString("category", "ALL"); 
				 	editor.putString("key",  c.toString());  
				 	editor.putString("change", "true");
				 	editor.commit();

				  // switchFragment(newContent);
				}
		
			
			
			
			}
			
		});
  

	 sharedPreferences = getActivity().getSharedPreferences("tweetDiay", Context.MODE_PRIVATE);
		 
		
		
		return vw_layout;
	}

	/**
	 * Step 2: Setup Menu Items
	 */
	private void initialiseMenuItems(Bundle args) {
		// get list view
		listView = (ListView) this.vw_layout.findViewById(R.id.lst_menu);

		lstMenuItems = new ArrayList<MenuItem>();
	
		addMenuItem(R.string.txt_menu_swipe, R.drawable.ico_comment,
				SwipeActivity.class, args);
		
		
	//	addMenuItem(R.string.txt_menu_swipe2, R.drawable.ico_comment,
		//		SwipeActivity2.class, args);
		
		
		addMenuItem(R.string.txt_menu_masterdetail,R.drawable.ico_person  ,
				Logout.class, args);
		
	//	addMenuItem(R.string.txt_menu_masterdetail,R.drawable.ico_person  ,
		//		MasterDetailActivity.class, args);
	//	addMenuItem(R.string.txt_menu_CheckList, R.drawable.ico_like,
		//		CheckListActivity.class, args);

		// Assign the items to the list
		listView.setAdapter(new MenuAdapter(getActivity(), lstMenuItems));
		// Register item click listener
		listView.setOnItemClickListener(this);
	}

	@SuppressWarnings("rawtypes")
	private void addMenuItem(int labelID, int drawableId, Class cl, Bundle args) {
		MenuItem mnu = new MenuItem(labelID, drawableId, cl, args);
		lstMenuItems.add(mnu);
	}

	public void onItemClick(AdapterView<?> adp, View listview, int position,
			long id) {

		if (adp != null && adp.getAdapter() instanceof MenuAdapter) {
			MenuAdapter menuAdp = (MenuAdapter) adp.getAdapter();
			MenuItem itm = menuAdp.getItem(position);
			Fragment newContent = itm.get_fragment();

			if (newContent == null) {

				newContent = Fragment.instantiate(getActivity(), itm
						.get_class().getName(), itm.get_args());
				itm.set_fragment(newContent);
				  switchFragment(newContent);
			}
			
			

		   switchFragment(newContent);
	

			
		}
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity mActivity = (MainActivity) getActivity();
			mActivity.switchContent(fragment);
		}
	}

}
