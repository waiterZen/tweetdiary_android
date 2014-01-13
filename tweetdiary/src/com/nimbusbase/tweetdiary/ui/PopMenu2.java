package com.nimbusbase.tweetdiary.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nimbusbase.tweetdiary.R;

 

public class PopMenu2 {
	private ArrayList<String> itemList;
	private Context context;
	private PopupWindow popupWindow ;
	private ListView listView;
	//private OnItemClickListener listener;
	

	public PopMenu2(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;

		itemList = new ArrayList<String>(5);
		
		View view = LayoutInflater.from(context).inflate(R.layout.popmenu, null);
        
        //���� listview
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(new PopAdapter());
        listView.setFocusableInTouchMode(true);
        listView.setFocusable(true);
        
        //  popupWindow = new PopupWindow(view, 100, LayoutParams.WRAP_CONTENT);
        popupWindow = new PopupWindow(view, 
        		context.getResources().getDimensionPixelSize(R.dimen.popmenu_width), 
        		LayoutParams.WRAP_CONTENT,true);
         
        
        
        
        // �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı�����������ģ�
        
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
  
	}

	
	
	public OnItemClickListener getListener(){
		return  this.listView.getOnItemClickListener();
	}
	
 
	
	
	
	
	
	
	
	//���ò˵�����������
	public void setOnItemClickListener(OnItemClickListener listener) {
		//this.listener = listener;
		listView.setOnItemClickListener(listener);
		
	}

	//������Ӳ˵���
	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}

	//������Ӳ˵���
	public void addItem(String item) {
		itemList.add(item);
	}
	public  void  clearItem(){
		itemList.clear();
	}

	//����ʽ ���� pop�˵� parent ���½�
	public void showAsDropDown(View parent) {
		popupWindow.showAsDropDown(parent, 10, 
				//��֤�ߴ��Ǹ�����Ļ�����ܶ�����
				context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
		
		// ʹ��ۼ�
        popupWindow.setFocusable(true);
        // ����������������ʧ
        popupWindow.setOutsideTouchable(true);
        //ˢ��״̬
        popupWindow.update();
     
	}
	
	//���ز˵�
	public void dismiss() {
		popupWindow.dismiss();
	}

	// ������
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
				 	Toast.makeText(context, "�����˵����:" + category, 2000).show(); 
				 	 
					
				}
			});
			
			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}
}
