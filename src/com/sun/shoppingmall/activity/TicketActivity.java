package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.sun.shoppingmall.adapter.MyFragmentPagerAdapter;
import com.sun.shoppingmall.fragment.CouponFragment;
import com.sun.shoppingmall.fragment.FulfilFragment;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;

public class TicketActivity extends FragmentActivity implements OnClickListener{
	
	private SharedPreferences sp;
	private String user_id;
	private TextView yhq,mhq;
	private CouponFragment coupon;
	private FulfilFragment fulfil;
	private ArrayList<Fragment>list=new ArrayList<Fragment>();
	private ViewPager viewpager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ticket);
		viewpager = (ViewPager) findViewById(R.id.viewpagerticket);
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText("我的喜券");
		sp = getSharedPreferences("user_id", MODE_PRIVATE);
		user_id = sp.getString("user_id", "");
		user_id = "2";
		coupon = new CouponFragment();
		list.add(coupon);
		fulfil = new FulfilFragment();
		list.add(fulfil);
		viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), list));
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
		initview();
	}
	
	public void initview(){
		yhq = (TextView) findViewById(R.id.tv_yhq);
		mhq = (TextView) findViewById(R.id.tv_mhq);
		new numTask().execute(Consts.URL);
		yhq.setOnClickListener(new txListener(0));
		mhq.setOnClickListener(new txListener(1));
	}
	
	private void resetText() {
		yhq.setTextColor(Color.BLACK);
		mhq.setTextColor(Color.BLACK);
	}
	
	class txListener implements View.OnClickListener{  
		private int index=0;  

		public txListener(int i) {  
			index =i;  
		}  
		@Override  
		public void onClick(View v) {  
			// TODO Auto-generated method stub  
			viewpager.setCurrentItem(index);  
		} 
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener{  
		private int currIndex;

		@Override  
		public void onPageScrolled(int arg0, float arg1, int arg2) {  
			// TODO Auto-generated method stub  
			int currentItem = viewpager.getCurrentItem();
			switch (currentItem) {
			case 0:
				resetText();
				yhq.setTextColor(getResources().getColor(R.color.top_background));
				break;
			case 1:
				resetText();
				mhq.setTextColor(getResources().getColor(R.color.top_background));
				break;
			}
		}  

		@Override  
		public void onPageScrollStateChanged(int arg0) {  
			// TODO Auto-generated method stub  

		}  

		@Override  
		public void onPageSelected(int arg0) {  
			currIndex = arg0;  
		}  
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	class numTask extends AsyncTask<String , String , String >{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "user_num");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("user_id", user_id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("user", "user_num", cartTime));
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
								yhq.setText("优惠券 "+object.getString("coupon"));
								mhq.setText("满惠券 "+object.getString("fulfil"));
								
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
	
}
