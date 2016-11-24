package com.sun.shoppingmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.entity.ProduceNearbyBean;

public class NearbyAdapter extends BaseAdapter{
	private Context context;
	private List<ProduceNearbyBean>lists=new ArrayList<ProduceNearbyBean>();
	public NearbyAdapter(Context context,List<ProduceNearbyBean>lists) {
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
			convertView=View.inflate(context, R.layout.nearby_adapter, null);
			//holder.iv=(ImageView) convertView.findViewById(R.id.imageview);
			//获取屏幕�??  宽度
			//WindowManager manager=(WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			//Display disPlay = manager.getDefaultDisplay();
			//int  wight= disPlay.getWidth();
			//int  height=wight/2-10;
			//int height=wight/3+10;
			//android.view.ViewGroup.LayoutParams lp = holder.iv.getLayoutParams();
			//lp.height=height;
			
			holder.shopnameTV=(TextView) convertView.findViewById(R.id.name);
			holder.addressTV=(TextView) convertView.findViewById(R.id.address);			
			holder.distanceTV=(TextView) convertView.findViewById(R.id.juli);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		//TApplication.bitmaputils.display(holder.iv, Consts.PRODUCT1+lists.get(position).getImg());
		holder.shopnameTV.setText(lists.get(position).getName());
		//holder.priceTV.setText("¥"+lists.get(position).getPrice());
		//holder.pingjiaTV.setText(lists.get(position).getSale());
		holder.distanceTV.setText("500m");
		//holder.arrrTv.setText(lists.get(position).getAttr2_name());
		holder.addressTV.setText("�?"+lists.get(position).getCircle_name()+"�?"+lists.get(position).getAddress());
		return convertView;
	}
	class ViewHolder{
		ImageView iv;
		TextView shopnameTV;
		TextView pingjiaTV;
		TextView addressTV;
		TextView priceTV;
		TextView distanceTV;
		TextView arrrTv;
	}
}