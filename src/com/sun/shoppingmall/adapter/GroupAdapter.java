package com.sun.shoppingmall.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.entity.CountyBean;

public class GroupAdapter extends BaseAdapter {

	private Context context;

	private List<CountyBean> list;

	public GroupAdapter(Context context, List<CountyBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		
		ViewHolder holder;
		if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.groupitem, null);
			holder=new ViewHolder();
			convertView.setTag(holder);
			holder.groupItem=(TextView) convertView.findViewById(R.id.groupItem);
			holder.groupItem.setTag(position);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.groupItem.setText(list.get(position).getCounty()) ;
		holder.groupItem.setTextColor(context.getResources().getColor(R.color.bg_Black)) ;
		holder.groupItem.setTextSize(15);
		return convertView;
	}

	static class ViewHolder {
		TextView groupItem;
	}

}
