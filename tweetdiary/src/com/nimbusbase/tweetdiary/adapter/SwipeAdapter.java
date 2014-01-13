package com.nimbusbase.tweetdiary.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nimbusbase.tweetdiary.R;
import com.nimbusbase.tweetdiary.model.diaryItem;
import com.nimbusbase.tweetdiary.utils.Utils;

public class SwipeAdapter extends ArrayAdapter<diaryItem> {

	private List<diaryItem> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;
	private OnClickListener _listener = null;

	public SwipeAdapter(Activity context, List<diaryItem> lst,
			OnClickListener listener) {
		super(context, R.layout.row_swipe, lst);
		this._context = context;
		_list = lst;
		_listener = listener;

		_inflater = this._context.getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = _inflater.inflate(R.layout.row_swipe, null);
		}

		Utils.setFontAllView(parent);

		diaryItem dItem = _list.get(position);
		//Integer id = dItem.getId();
		

		View layer1 = view.findViewById(R.id.view_layer1);
		View layer2 = view.findViewById(R.id.view_layer2);

		TextView tvTitle = (TextView) view.findViewById(R.id.text_name);
		TextView tvDesc = (TextView) view.findViewById(R.id.text_desc);
	//	ImageView iv = (ImageView) view.findViewById(R.id.image);

		
		view.setId(position);
		tvTitle.setText(dItem.getText());
		tvDesc.setText(dItem.getCreate_time());

//		Bitmap bmp = Utils.GetImageFromAssets(this._context, "images/"
//				+ "cb_checked_blue.png");
		
	//	Bitmap bmp = Utils.GetImageFromAssets(this._context,null);
	//	iv.setImageBitmap(bmp);

		detailViewListener(position, view);

		layer1.setVisibility(View.VISIBLE);		
		layer2.setVisibility(View.INVISIBLE);

		return view;
	}

	public void detailViewListener(int pos, View convertView) {
		if (this._listener == null)
			return;

		ImageButton btn;
		
		btn = (ImageButton) convertView.findViewById(R.id.btn_love);
		btn.setTag(pos);
		btn.setOnClickListener(this._listener);
	
		btn = (ImageButton) convertView.findViewById(R.id.btn_like);
		btn.setTag(pos);
		btn.setOnClickListener(this._listener);

		btn = (ImageButton) convertView.findViewById(R.id.btn_reload);
		btn.setTag(pos);
		btn.setOnClickListener(this._listener);
		
//		Button  btn2;
//		btn2 = (Button) convertView.findViewById(R.id.btn_love);
//		btn2.setTag(pos);
//		btn2.setOnClickListener(this._listener);


	}

}
