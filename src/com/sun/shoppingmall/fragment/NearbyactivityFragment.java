package com.sun.shoppingmall.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.activity.ActivityProductActivity;
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

public class NearbyactivityFragment extends Fragment implements OnClickListener,OnRefreshListener,ClickPositonZero{
	private MyListView listview;
	private List<PreachEntity>list=new ArrayList<PreachEntity>();
	private PreachAdapter adapter;
	private PopupWindow popupWindow;
	private PopupWindow orderhighWindow;
	private View view;
	private ListView lv_group;
	private ListView lv_groupp;
	private View view2;
	private LinearLayout orderuseless,zhonghepaiming;
	private LinearLayout orderhigh,renqi;
	private LinearLayout orderlow;
	private TextView geniussss,tv1ssssbb;
	private LinearLayout nearby,paixu;
	private PullableScrollViews scrollview;
	private PullToRefreshLayout pullToRefreshLayout;
	private PostForPreach forPreach;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.nearby_fragment, null);
		listview=(MyListView) view.findViewById(R.id.listview);
		geniussss=(TextView) view.findViewById(R.id.geniussss);
		geniussss.setOnClickListener(this);
		nearby=(LinearLayout) view.findViewById(R.id.coupon_nearby);
		nearby.setOnClickListener(this);
		paixu=(LinearLayout) view.findViewById(R.id.coupon_geniusorder);
		paixu.setOnClickListener(this);
		zhonghepaiming=(LinearLayout) view.findViewById(R.id.zhonghepaiming);
		zhonghepaiming.setOnClickListener(this);
		tv1ssssbb=(TextView) view.findViewById(R.id.tv1ssssbb);
		pullToRefreshLayout = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		pullToRefreshLayout.setOnRefreshListener(this);
		// rl_title = (LinearLayout) view.findViewById(R.id.toprela);
		scrollview = (PullableScrollViews) view.findViewById(R.id.listview_placemore);
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
		forPreach=new PostForPreach(getActivity(), "8", listview, mhandler);
		forPreach.addListData();
		return view;
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
						if(adapter==null){
							adapter=new PreachAdapter(getActivity(), list);
							listview.setAdapter(adapter);
						}else{
							adapter.notifyDataSetChanged();
						}
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								Intent intent=new Intent(getActivity(),ActivityProductActivity.class);
								intent.putExtra("id", list.get(position).getId());
								startActivity(intent);
							}
						});
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				/*pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);*/
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
			TextView ha=(TextView) renqi.getChildAt(0);
			ha.setTextColor(Color.BLACK);
			renqi.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			list.clear();
			forPreach.removeListData();
			forPreach.setAttrs("1");
			forPreach.addListData();
			orderhighWindow.dismiss(); 
			break ;

		case R.id.orderhigh:
			LinearLayout lineV = (LinearLayout) v ;
			TextView tvv = (TextView) lineV.getChildAt(0) ;
			tvv.setTextColor(getResources().getColor(R.color.red)) ;
			orderhigh.setBackgroundColor(getResources().getColor(R.color.bg_Gray_light));
			geniussss.setText("离我�?�?") ;
			geniussss.setTextColor(getResources().getColor(R.color.red)) ;
			TextView tvieww = (TextView) orderlow.getChildAt(0) ;
			tvieww.setTextColor(Color.BLACK) ;
			orderlow.setBackgroundColor(getResources().getColor(R.color.bg_White));
			TextView vtt = (TextView) orderuseless.getChildAt(0) ;
			vtt.setTextColor(Color.BLACK) ;
			orderuseless.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			TextView haa=(TextView) renqi.getChildAt(0);
			haa.setTextColor(Color.BLACK);
			renqi.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			list.clear();
			forPreach.removeListData();
			forPreach.setAttrs("4");
			forPreach.addListData();
			orderhighWindow.dismiss(); 
			break;
		case R.id.orderlow :
			geniussss.setText("好评优先") ;
			geniussss.setTextColor(getResources().getColor(R.color.red)) ;
			TextView tsv = (TextView) orderhigh.getChildAt(0) ;
			tsv.setTextColor(Color.BLACK) ;
			orderhigh.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			TextView tviesw = (TextView) orderlow.getChildAt(0) ;
			orderlow.setBackgroundColor(getResources().getColor(R.color.bg_Gray_light)) ;
			tviesw.setTextColor(getResources().getColor(R.color.red)) ;
			TextView vst = (TextView) orderuseless.getChildAt(0) ;
			vst.setTextColor(Color.BLACK) ;
			orderuseless.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			TextView haaa=(TextView) renqi.getChildAt(0);
			haaa.setTextColor(Color.BLACK);
			renqi.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			list.clear();
			forPreach.removeListData();
			forPreach.setAttrs("2");
			forPreach.addListData();
			orderhighWindow.dismiss(); 
			break ;
		case R.id.renqi :
			geniussss.setText("人气�?�?") ;
			geniussss.setTextColor(getResources().getColor(R.color.red)) ;
			TextView tsvs = (TextView) orderhigh.getChildAt(0) ;
			tsvs.setTextColor(Color.BLACK) ;
			orderhigh.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			TextView tviesws = (TextView) orderlow.getChildAt(0) ;
			orderlow.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			tviesws.setTextColor(Color.BLACK) ;
			TextView vsts = (TextView) orderuseless.getChildAt(0) ;
			vsts.setTextColor(Color.BLACK) ;
			orderuseless.setBackgroundColor(getResources().getColor(R.color.bg_White)) ;
			TextView haaaa=(TextView) renqi.getChildAt(0);
			renqi.setBackgroundColor(getResources().getColor(R.color.bg_Gray_light)) ;
			haaaa.setTextColor(getResources().getColor(R.color.red));
			list.clear();
			forPreach.removeListData();
			forPreach.setAttrs("3");
			forPreach.addListData();
			orderhighWindow.dismiss(); 
			break ;
		}
	}
	private void showWindow(View parent) {
		WindowManager windowManager = (WindowManager) 
				getActivity().getSystemService(Context.WINDOW_SERVICE);
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) 
					getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		
		PostForCounty add = new PostForCounty(NearbyactivityFragment.this,this.getActivity(), "370100",
				lv_group, lv_groupp, popupWindow, mHandler);
	}
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 88:
				JSONObject json=(JSONObject) msg.obj;
				String country=json.optString("country");
				tv1ssssbb.setText(country);
				list.clear();
				forPreach.removeListData();
				if(country.equals("1km")){
					forPreach.setRadius("1");
				}else if(country.equals("3km")){
					forPreach.setRadius("3");
				}else if(country.equals("5km")){
					forPreach.setRadius("5");
				}else if(country.equals("10km")){
					forPreach.setRadius("10");
				}else{
					forPreach.setRadius("");
				}
				forPreach.addListData();
				tv1ssssbb.setTextColor(NearbyactivityFragment.this.getResources().getColor(R.color.red));
				popupWindow.dismiss();
				break;
			case 13:
				JSONObject js = (JSONObject) msg.obj;
				//listPro.clear();
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
				tv1ssssbb.setTextColor(NearbyactivityFragment.this.getResources().getColor(R.color.red)) ;
				//Count = 1;
				break;
			}
		}
	};
	@SuppressLint("ResourceAsColor")
	private void showGeniusOrderWindow(View parent) {
		WindowManager windowManager = (WindowManager) 
				getActivity().getSystemService(Context.WINDOW_SERVICE);
		if (orderhighWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) 
					getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view2 = layoutInflater.inflate(R.layout.groupgeniusorder, null);
			view2.getBackground().setAlpha(150);
			orderuseless = (LinearLayout) view2.findViewById(R.id.orderuseless) ;
			orderuseless.setOnClickListener(this) ;
			orderuseless.setBackgroundColor(this.getResources().getColor(R.color.bg_Gray_light)) ;
			TextView ttv = (TextView) orderuseless.getChildAt(0) ;
			ttv.setTextColor(getResources().getColor(R.color.red)) ;
			orderhigh = (LinearLayout) view2.findViewById(R.id.orderhigh);
			orderhigh.getBackground().setAlpha(255);
			orderhigh.setOnClickListener(this);
			orderlow = (LinearLayout) view2.findViewById(R.id.orderlow);
			orderlow.getBackground().setAlpha(255);
			orderlow.setOnClickListener(this);
			renqi=(LinearLayout) view2.findViewById(R.id.renqi);
			renqi.getBackground().setAlpha(255);
			renqi.setOnClickListener(this);
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
				getActivity().getSystemService(Context.WINDOW_SERVICE);
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) 
					getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		this.pullToRefreshLayout=pullToRefreshLayout;
		// TODO Auto-generated method stub
		forPreach.addListData();
		pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
		pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		
	}
	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		this.pullToRefreshLayout=pullToRefreshLayout;
		forPreach.addListData();
		pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
		pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
	}
	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}
}
