package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.TApplication;
import com.sun.shoppingmall.customview.CustomGridView;
import com.sun.shoppingmall.entity.CounterEntity;
import com.sun.shoppingmall.entity.GuitaiEntity;
import com.sun.shoppingmall.utils.PostForCounter;

public class GuitaiActivity extends Activity{
	
	private CustomGridView gridview,gridView1;
	private List<GuitaiEntity>list=new ArrayList<GuitaiEntity>();
	private PostForCounter post;
	private List<CounterEntity> countlist = new ArrayList<CounterEntity>();
	private String xx,yy;
	private LocationClient mLocationClient;
	private MyLocationListener mMyLocationListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guitai_activity);
		gridview=(CustomGridView) findViewById(R.id.gridview);
		gridView1=(CustomGridView) findViewById(R.id.gridviews);
		post = new PostForCounter(this, "10", gridview, mhandler);
		
		mLocationClient = new LocationClient(this);
		initLocation();
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();// 定位SDK
		 //start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
		mLocationClient.requestLocation();
		
		xx = TApplication.CurrentLatitude;
		yy = TApplication.CurrentLongitude;
		Log.d("xx,yy", xx+yy);
		post.addListData();
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		list.add(new GuitaiEntity());
		gridview.setAdapter(new GuitaiAdapter(this, list));
		gridView1.setAdapter(new GuitaiAdapter1(this, list));
	}
	
	
	/**
	 * 实现实时位置回调监听
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location.getCity() == null) {
				//locateProcess = 3;// 定位失败...
				return;
			}
		String city=location.getCity();
		//	postfor();
		}
		
	}
	
	/**
	 * 定位一系列的 要求
	 */
	public void initLocation() {
		// com.baidu.location.LocationClient.LocationClient(Context arg0)
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系，
		option.setScanSpan(0);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(false);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps 可以使用 gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			switch (msg.what) {
			case 11:
				JSONArray array = (JSONArray) msg.obj;
				try {
					for (int i = 0; i < array.length(); i++) {
						CounterEntity entity = new CounterEntity();
						JSONObject jsonObject = array.getJSONObject(i);
						entity.setId(jsonObject.optString("id"));
						entity.setImg(jsonObject.optString("img"));
						entity.setLogo(jsonObject.optString("logo"));
						entity.setName(jsonObject.optString("name"));
						entity.setVid(jsonObject.optString("vid"));
						countlist.add(entity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
	};
	class GuitaiAdapter extends BaseAdapter{
		private Context context;
		private List<GuitaiEntity>lists=new ArrayList<GuitaiEntity>();
		public GuitaiAdapter(Context context,List<GuitaiEntity>lists) {
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
				convertView=View.inflate(context, R.layout.guitai_adapter, null);
				//holder.iv=(ImageView) convertView.findViewById(R.id.imageview);
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
	}class GuitaiAdapter1 extends BaseAdapter{
		private Context context;
		private List<GuitaiEntity>lists=new ArrayList<GuitaiEntity>();
		public GuitaiAdapter1(Context context,List<GuitaiEntity>lists) {
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
				convertView=View.inflate(context, R.layout.guitai_adapter1, null);
				//holder.iv=(ImageView) convertView.findViewById(R.id.imageview);
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
}
