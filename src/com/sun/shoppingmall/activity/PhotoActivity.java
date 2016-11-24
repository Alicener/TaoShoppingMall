package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.adapter.ImageviewAdapter;
import com.sun.shoppingmall.customview.CustomGridView;
import com.sun.shoppingmall.entity.GuitaiEntity;
import com.sun.shoppingmall.entity.PhotoEntity;

public class PhotoActivity extends Activity{
	private PullToRefreshListView listview;
	private List<PhotoEntity>list=new ArrayList<PhotoEntity>();
	List<GuitaiEntity>image=new ArrayList<GuitaiEntity>();
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.photo_activity);
	listview=(PullToRefreshListView) findViewById(R.id.listview);
	listview.setMode(Mode.PULL_FROM_END);
	list.add(new PhotoEntity());
	list.add(new PhotoEntity());
	list.add(new PhotoEntity());
	list.add(new PhotoEntity());
	image.add(new GuitaiEntity());
	image.add(new GuitaiEntity());
	image.add(new GuitaiEntity());
	image.add(new GuitaiEntity());
	image.add(new GuitaiEntity());
	image.add(new GuitaiEntity());
	listview.setAdapter(new PhotoAdapter(this, list));
	listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			
		}
	});
}
class PhotoAdapter extends BaseAdapter{
	private Context context;
	private List<PhotoEntity>lists=new ArrayList<PhotoEntity>();
	public PhotoAdapter(Context context,List<PhotoEntity>lists) {
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
		ViewHolder holder=null;
		// TODO Auto-generated method stub
		if(convertView==null){
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.photo_adapter, null);
			holder.gridview=(CustomGridView) convertView.findViewById(R.id.gridview);
			holder.focus=(ImageView) convertView.findViewById(R.id.focus);
			//获取屏幕�??  宽度
			//WindowManager manager=(WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			//Display disPlay = manager.getDefaultDisplay();
			//int  wight= disPlay.getWidth();
			//int  height=wight/2-10;
			//int height=wight/3+10;
			//android.view.ViewGroup.LayoutParams lp = holder.iv.getLayoutParams();
			//lp.height=height;
			
			//holder.shopnameTV=(TextView) convertView.findViewById(R.id.name);
			//holder.pingjiaTV=(TextView) convertView.findViewById(R.id.person);			
			//holder.priceTV=(TextView) convertView.findViewById(R.id.price);
			
			holder.gridview.setAdapter(new ImageviewAdapter(PhotoActivity.this,image));
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		//TApplication.bitmaputils.display(holder.iv, Consts.PRODUCT1+lists.get(position).getImg());
		//holder.shopnameTV.setText(lists.get(position).getGoods_name());
		//holder.priceTV.setText("¥"+lists.get(position).getPrice());
		//holder.pingjiaTV.setText(lists.get(position).getSale());
		//holder.distanceTV.setText("<"+"99");
		//holder.arrrTv.setText(lists.get(position).getAttr2_name());
		//holder.addressTV.setText(list.get(position).getCountry());
		holder.focus.setSelected(true);
		holder.focus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			}
		});
		return convertView;
	}
	class ViewHolder{
		ImageView focus;
		TextView shopnameTV;
		TextView pingjiaTV;
		TextView addressTV;
		TextView priceTV;
		TextView distanceTV;
		TextView arrrTv;
		CustomGridView gridview;
	}
}
}
