package com.jdk.corso.lesson2;

import java.util.List;

import com.jdk.corso.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PeopleAdapter extends BaseAdapter{
	private final static String TAG = "ADAPTER";
	
	private final List<String> mData;
	private final LayoutInflater mInflater;
	
	public PeopleAdapter(Context context,List<String> data) {
		mData=data;
		mInflater = LayoutInflater.from(context);
//		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mData==null?0:mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount();
	}
	
	@Override
	public int getItemViewType(int position) {
		
		return super.getItemViewType(position);
	}

	@Override
	public View getView(int position, View recylcedView, ViewGroup adapterView) {
		Log.d(TAG,"getView called at position "+position);
		ViewHolder h;
		if(recylcedView == null){
			recylcedView = mInflater.inflate(R.layout.people_row, adapterView,false);
			h = new ViewHolder();
			h.text= (TextView)recylcedView.findViewById(R.id.tv_people_name);
			recylcedView.setTag(h);
		}else{
			h = (ViewHolder)recylcedView.getTag();
		}
		String item = getItem(position);
		h.text.setText(item);
		return recylcedView;
	}
	
	private static class ViewHolder{
		private TextView text;
	}

}
