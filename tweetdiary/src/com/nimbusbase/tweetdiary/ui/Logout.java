package com.nimbusbase.tweetdiary.ui;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class Logout extends   Fragment  {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		System.exit(0);
	}
 
}
