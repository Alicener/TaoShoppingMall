package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.fragment.ShopAllFragment;
import com.sun.shoppingmall.fragment.ShopCartFragment;
import com.sun.shoppingmall.fragment.ShopCuxiaoFragment;
import com.sun.shoppingmall.fragment.ShopIntroFragment;
import com.sun.shoppingmall.fragment.ShopNewFragment;
import com.sun.shoppingmall.pullable.PullToRefreshLayout;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnReflushListener;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnRefreshListener;
import com.sun.shoppingmall.pullable.PullableScrollViews;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;

public class ShopActivity extends FragmentActivity implements OnRefreshListener,OnClickListener{
	private PullableScrollViews scrollview;
	private PullToRefreshLayout pullToRefreshLayout;
	private TextView cuxiao,newproduct,all,intro,shopcate,phone;
	private String phonenumber,shop_id;
	private ShopAllFragment allfragment;
	private ShopCartFragment catefragment;
	private ShopCuxiaoFragment cuxiaofragment;
	private ShopIntroFragment introfragment;
	private ShopNewFragment newfragment;
	private List<TextView>list=new ArrayList<TextView>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_brand_shops);
		intviews();
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
	}
	private void intviews() {
		// TODO Auto-generated method stub
		shop_id=getIntent().getStringExtra("id");
		cuxiao=(TextView) findViewById(R.id.cuoxiao);
		cuxiao.setOnClickListener(this);
		newproduct=(TextView) findViewById(R.id.newproduct);
		newproduct.setOnClickListener(this);
		all=(TextView) findViewById(R.id.all);
		all.setOnClickListener(this);
		intro=(TextView) findViewById(R.id.shopintro);
		intro.setOnClickListener(this);
		shopcate=(TextView) findViewById(R.id.shopcate);
		shopcate.setOnClickListener(this);
		phone=(TextView) findViewById(R.id.phone);
		phone.setOnClickListener(this);
		list.add(cuxiao);
		list.add(newproduct);
		list.add(all);
		list.add(intro);
		list.add(shopcate);
		new ShopInfotask().execute(Consts.URL);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (v.getId()) {
		case R.id.cuoxiao:
			
			break;
		case R.id.newproduct:

			break;
		case R.id.all:
			if (allfragment == null) {
				allfragment = new ShopAllFragment();
				Bundle bundle = new Bundle();  
				bundle.putString("key", shop_id);  
				allfragment.setArguments(bundle);  
			addFragment(allfragment);
			showFragment(allfragment);
		} else {
			if (allfragment.isHidden()) {
				//Bundle bundle = new Bundle();  
				//bundle.putString("key", shop_id);  
				//allfragment.setArguments(bundle);
				showFragment(allfragment);
			}
		}
			textColor(2);
			break;
		case R.id.shopintro:
			if (introfragment == null) {
				introfragment = new ShopIntroFragment();
				Bundle bundle = new Bundle();  
				bundle.putString("key", shop_id);  
				introfragment.setArguments(bundle);  
			addFragment(introfragment);
			showFragment(introfragment);
		} else {
			if (introfragment.isHidden()) {
				//Bundle bundle = new Bundle();  
				//bundle.putString("key", shop_id);  
				//introfragment.setArguments(bundle);
				showFragment(introfragment);
			}
		}
			textColor(3);
			break;
		case R.id.shopcate:

			break;
		case R.id.phone:
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + phonenumber));
			startActivity(intent);
			break;

		}
		ft.commit();
	}
	class ShopInfotask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "shop");
				requestParams.addBodyParameter("a", "shop_info");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", shop_id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("shop", "shop_info", cartTime));
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							JSONObject object=new JSONObject(response);
							if(object.getString("error").equals("0")){
								//shopname.setText("ã€?"+object.optString("name")+"ã€?");
								phonenumber=object.optString("phone");
								//qq=object.optString("qq");
							}else{
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
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
	/** ï¿½ï¿½ï¿½Fragment **/
	public void addFragment(Fragment fragment) {
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.contents, fragment);
		ft.commit();
	}

	/** É¾ï¿½ï¿½Fragment **/
	public void removeFragment(Fragment fragment) {
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** ï¿½ï¿½Ê¾Fragment **/
	public void showFragment(Fragment fragment) {
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (allfragment != null) {
			ft.hide(allfragment);
		}
		if (catefragment != null) {
			ft.hide(catefragment);
		}
		if (catefragment != null){
			ft.hide(catefragment);
		}
		if (cuxiaofragment != null){
			ft.hide(cuxiaofragment);
		}
		if (introfragment != null){
			ft.hide(introfragment);
		}
		if (newfragment != null){
			ft.hide(newfragment);
		}
		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}
	public void textColor(int position){

		for(int i=0;i<list.size();i++){
			if(i==position){
				list.get(position).setTextColor(getResources().getColor(R.color.top_background));
				list.get(position).setSelected(true);
			}else{
				list.get(i).setTextColor(getResources().getColor(R.color.bg_Black));
				list.get(i).setSelected(false);
			}

		}
	}
}
