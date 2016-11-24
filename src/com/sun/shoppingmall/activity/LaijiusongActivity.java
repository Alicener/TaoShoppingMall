package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.adapter.PreachAdapter;
import com.sun.shoppingmall.customview.MyListView;
import com.sun.shoppingmall.entity.PreachEntity;
import com.sun.shoppingmall.pullable.PullToRefreshLayout;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnReflushListener;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnRefreshListener;
import com.sun.shoppingmall.pullable.PullableScrollViews;
import com.sun.shoppingmall.utils.PostForCounty;
import com.sun.shoppingmall.utils.PostForCounty.ClickPositonZero;
import com.sun.shoppingmall.utils.PostForPreach;

public class LaijiusongActivity extends Activity implements OnClickListener,OnRefreshListener,ClickPositonZero{
	private MyListView listview;
	private List<String>country=new ArrayList<String>();
	private List<PreachEntity>list=new ArrayList<PreachEntity>();
	private PreachAdapter adapter;
	private PopupWindow popupWindow;
	private PopupWindow orderhighWindow;
	private View view;
	private ListView lv_group;
	private ListView lv_groupp;
	private View view2;
	private LinearLayout orderuseless,zhonghepaiming;
	private LinearLayout orderhigh;
	private LinearLayout orderlow;
	private TextView geniussss,tv1ssssbb;
	private LinearLayout all,nearby,paixu;
	private PullableScrollViews scrollview;
	private PullToRefreshLayout pullToRefreshLayout;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.laijiusong);
	intViews();
}
private void intViews() {
	// TODO Auto-generated method stub
	listview=(MyListView) findViewById(R.id.listview);
	geniussss=(TextView) findViewById(R.id.geniussss);
	geniussss.setOnClickListener(this);
	nearby=(LinearLayout) findViewById(R.id.coupon_nearby);
	nearby.setOnClickListener(this);
	paixu=(LinearLayout) findViewById(R.id.coupon_geniusorder);
	paixu.setOnClickListener(this);
	zhonghepaiming=(LinearLayout) findViewById(R.id.zhonghepaiming);
	zhonghepaiming.setOnClickListener(this);
	tv1ssssbb=(TextView) findViewById(R.id.tv1ssssbb);
	pullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
	pullToRefreshLayout.setOnRefreshListener(this);
	// rl_title = (LinearLayout) view.findViewById(R.id.toprela);
	scrollview = (PullableScrollViews) findViewById(R.id.listview_placemore);
	//rl_title.getBackground().setAlpha(0);
	pullToRefreshLayout.setOnReflushListener(new OnReflushListener() {

		@Override
		public void isShow(boolean show) {
			// TODO Auto-generated method stub
			if(show){
				//rl_title.setVisibility(View.GONE);
			}else{
				//adViewPager.setVisibility(View.VISIBLE);
				//rl_title.setVisibility(View.VISIBLE);
			}
		}
	});
	PostForPreach forPreach=new PostForPreach(this, "8", listview, mhandler);
	forPreach.addListData();
}
Handler mhandler=new Handler(){
	public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
		case 11:
			JSONObject object=(JSONObject) msg.obj;
			try {
				JSONArray array=object.optJSONArray("result");
				//if(object.getString("result").equals("")){
				//xianshi.setVisibility(View.VISIBLE);
				//listview.setVisibility(View.GONE);
				//}
				if(array==null){

				}else{
					for(int i=0;i<array.length();i++){
						PreachEntity entity=new PreachEntity();
						JSONObject jsonObject=array.getJSONObject(i);
						entity.setId(jsonObject.getString("id"));
						entity.setTitle(jsonObject.getString("title"));
						entity.setShop_name(jsonObject.getString("shop_name"));
						entity.setJuli(jsonObject.getString("juli"));
						entity.setShop_id(jsonObject.getString("shop_id"));
						entity.setCircle_name(jsonObject.getString("circle_name"));
						list.add(entity);
					}
					adapter=new PreachAdapter(LaijiusongActivity.this, list);
					listview.setAdapter(adapter);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
	};
};
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.coupon_nearby:
		showWindow(v);
		break;
	case R.id.coupon_geniusorder:
		showGeniusOrderWindow(v);
		break;
	case R.id.zhonghepaiming:
		showWindows(v);
		break;
	case R.id.orderuseless :
		geniussss.setText("智能排序") ;
		TextView tv = (TextView) orderhigh.getChildAt(0) ;
		tv.setTextColor(Color.BLACK) ;
		orderhigh.setBackgroundColor(getResources().getColor(R.color.bg_White));
		TextView tview = (TextView) orderlow.getChildAt(0) ;
		tview.setTextColor(Color.BLACK) ;
		orderlow.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
		TextView vt = (TextView) orderuseless.getChildAt(0) ;
		vt.setTextColor(getResources().getColor(R.color.red)) ;
		orderuseless.setBackgroundColor(getResources().getColor(R.color.bg_Gray_light)) ;
		orderhighWindow.dismiss(); 
		break ;

	case R.id.orderhigh:
		LinearLayout lineV = (LinearLayout) v ;
		TextView tvv = (TextView) lineV.getChildAt(0) ;
		tvv.setTextColor(getResources().getColor(R.color.unknownColor)) ;
		orderhigh.setBackgroundColor(getResources().getColor(R.color.bg_Gray_light));
		geniussss.setText("离我�?�?") ;
		geniussss.setTextColor(getResources().getColor(R.color.red)) ;
		TextView tvieww = (TextView) orderlow.getChildAt(0) ;
		tvieww.setTextColor(Color.BLACK) ;
		orderlow.setBackgroundColor(getResources().getColor(R.color.bg_White));
		TextView vtt = (TextView) orderuseless.getChildAt(0) ;
		vtt.setTextColor(Color.BLACK) ;
		orderuseless.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
		orderhighWindow.dismiss(); 
		break;
	case R.id.orderlow :
		geniussss.setText("好评优先") ;
		geniussss.setTextColor(getResources().getColor(R.color.unknownColor)) ;
		TextView tsv = (TextView) orderhigh.getChildAt(0) ;
		tsv.setTextColor(Color.BLACK) ;
		orderhigh.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
		TextView tviesw = (TextView) orderlow.getChildAt(0) ;
		orderlow.setBackgroundColor(getResources().getColor(R.color.bg_Gray_light)) ;
		tviesw.setTextColor(getResources().getColor(R.color.red)) ;
		TextView vst = (TextView) orderuseless.getChildAt(0) ;
		vst.setTextColor(Color.BLACK) ;
		orderuseless.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
		orderhighWindow.dismiss(); 
		break ;
	}
}
private void showWindow(View parent) {
	WindowManager windowManager = (WindowManager) 
			this.getSystemService(Context.WINDOW_SERVICE);
	if (popupWindow == null) {
		LayoutInflater layoutInflater = (LayoutInflater) 
				this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.grouplist, null);
		view.getBackground().setAlpha(150);
		lv_group = (ListView) view.findViewById(R.id.lvGroup);
		lv_group.getBackground().setAlpha(255);
		
		lv_groupp = (ListView) view.findViewById(R.id.lvGroupp);
		
		lv_groupp.getBackground().setAlpha(255);
		
		/*Certificate certificate = (Certificate) Certificate01.this
		.this;
int titleHeight = certificate.getTitleHeight();
int actualHeight = windowManager.getDefaultDisplay().getHeight()
		- titleHeight - coupon_nearby.getHeight();
LinearLayout.LayoutParams heightParams = (LayoutParams) lv_group
		.getLayoutParams();
heightParams.height = actualHeight / 3 * 2;
LinearLayout.LayoutParams heightParamss = (LayoutParams) lv_groupp
		.getLayoutParams();
heightParamss.height = actualHeight / 3 * 2;*/
		popupWindow = new PopupWindow(view, windowManager
				.getDefaultDisplay().getWidth(), android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		//PostForCounty add = new PostForCounty(Certificate01.this,this.this, "370100",
		//lv_group, lv_groupp, popupWindow, mHandler);
	}
	// 使其聚集
	popupWindow.setFocusable(true);
	// 设置允许在外点击消失
	popupWindow.setOutsideTouchable(true);
	// 这个是为了点击�?�返回Back”也能使其消失，并且并不会影响你的背�?
	popupWindow.setBackgroundDrawable(new BitmapDrawable());
	// 我看明白了，这个x和y 是popup view要显示的左上角的位置...并且是相对于它的父控�?...
	popupWindow.showAsDropDown(parent, 0, 10);
	
	PostForCounty add = new PostForCounty(this,this, "370100",
			lv_group, lv_groupp, popupWindow, mHandler);
}
Handler mHandler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		
		case 13:
			JSONObject js = (JSONObject) msg.obj;
			//listPro.clear();
			System.out.println("我看看这有数据吗" + js.optString("countyid")
					+ "----" + js.optString("circleid"));
			//ser.removeAllChild() ;
			//ser.setAttrs("370100", js.optString("countyid"), js.optString("circleid"), "") ;
			//ser.addMore();
			/*if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
			if (adapterr != null) {
				adapterr.notifyDataSetChanged();
			}*/
			tv1ssssbb.setText(js.optString("circle")) ;
			tv1ssssbb.setTextColor(LaijiusongActivity.this.getResources().getColor(R.color.red)) ;
			//Count = 1;
			break;
		}
	}
};
@SuppressLint("ResourceAsColor")
private void showGeniusOrderWindow(View parent) {
	WindowManager windowManager = (WindowManager) 
			this.getSystemService(Context.WINDOW_SERVICE);
	if (orderhighWindow == null) {
		LayoutInflater layoutInflater = (LayoutInflater) 
				this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view2 = layoutInflater.inflate(R.layout.groupgeniusorder, null);
		view2.getBackground().setAlpha(150);
		orderuseless = (LinearLayout) view2.findViewById(R.id.orderuseless) ;
		orderuseless.setOnClickListener(this) ;
		orderuseless.setBackgroundColor(this.getResources().getColor(R.color.bg_Gray_light)) ;
		TextView ttv = (TextView) orderuseless.getChildAt(0) ;
		ttv.setTextColor(getResources().getColor(R.color.unknownColor)) ;
		orderhigh = (LinearLayout) view2.findViewById(R.id.orderhigh);
		orderhigh.getBackground().setAlpha(255);
		orderhigh.setOnClickListener(this);
		orderlow = (LinearLayout) view2.findViewById(R.id.orderlow);
		orderlow.getBackground().setAlpha(255);
		orderlow.setOnClickListener(this);
		/*NearbyactivityFragment certificate = (Certificate) Certificate01.this
		.this;
int titleHeight = certificate.getTitleHeight();
int actualHeight = windowManager.getDefaultDisplay().getHeight()
		- titleHeight - coupon_nearby.getHeight();*/
		orderhighWindow = new PopupWindow(view2, windowManager
				.getDefaultDisplay().getWidth(), android.view.ViewGroup.LayoutParams.MATCH_PARENT);
	}
	// 使其聚集
	orderhighWindow.setFocusable(true);
	// 设置允许在外点击消失
	orderhighWindow.setOutsideTouchable(true);
	// 这个是为了点击�?�返回Back”也能使其消失，并且并不会影响你的背�?
	orderhighWindow.setBackgroundDrawable(new BitmapDrawable());
	orderhighWindow.showAsDropDown(parent, 0, 10);

}
private void showWindows(View parent) {
	WindowManager windowManager = (WindowManager) 
			this.getSystemService(Context.WINDOW_SERVICE);
	if (popupWindow == null) {
		LayoutInflater layoutInflater = (LayoutInflater) 
				this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.grouplist, null);
		view.getBackground().setAlpha(150);
		lv_group = (ListView) view.findViewById(R.id.lvGroup);
		lv_group.getBackground().setAlpha(255);
		
		lv_groupp = (ListView) view.findViewById(R.id.lvGroupp);
		
		lv_groupp.getBackground().setAlpha(255);
		
		/*Certificate certificate = (Certificate) Certificate01.this
		.this;
int titleHeight = certificate.getTitleHeight();
int actualHeight = windowManager.getDefaultDisplay().getHeight()
		- titleHeight - coupon_nearby.getHeight();
LinearLayout.LayoutParams heightParams = (LayoutParams) lv_group
		.getLayoutParams();
heightParams.height = actualHeight / 3 * 2;
LinearLayout.LayoutParams heightParamss = (LayoutParams) lv_groupp
		.getLayoutParams();
heightParamss.height = actualHeight / 3 * 2;*/
		popupWindow = new PopupWindow(view, windowManager
				.getDefaultDisplay().getWidth(), android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		//PostForCounty add = new PostForCounty(Certificate01.this,this.this, "370100",
		//lv_group, lv_groupp, popupWindow, mHandler);
	}
	// 使其聚集
	popupWindow.setFocusable(true);
	// 设置允许在外点击消失
	popupWindow.setOutsideTouchable(true);
	// 这个是为了点击�?�返回Back”也能使其消失，并且并不会影响你的背�?
	popupWindow.setBackgroundDrawable(new BitmapDrawable());
	// 我看明白了，这个x和y 是popup view要显示的左上角的位置...并且是相对于它的父控�?...
	popupWindow.showAsDropDown(parent, 0, 10);
	
}
@Override
public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
	// TODO Auto-generated method stub
	new Handler().postDelayed(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		}
	}, 2000);
}
@Override
public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
	// TODO Auto-generated method stub
	new Handler().postDelayed(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		}
	}, 2000);
}
@Override
public void click() {
	// TODO Auto-generated method stub
	
}
}
