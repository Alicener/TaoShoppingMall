package com.sun.shoppingmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.shoppingmall.R;


public class StringAdapter extends BaseAdapter{
	private Context context;
	private List<String>lists=new ArrayList<String>();
	public StringAdapter(Context context,List<String>lists) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.lists=lists;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.string_adapter, null);
			holder.tv=(TextView) convertView.findViewById(R.id.textview);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv.getPaint().setFakeBoldText(true);
		holder.tv.setText(lists.get(position));
		return convertView;
	}
	class ViewHolder{
		TextView tv;
	}
}