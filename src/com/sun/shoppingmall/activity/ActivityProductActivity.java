package com.sun.shoppingmall.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.customview.CustomViewPager;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;

public class ActivityProductActivity extends Activity implements OnClickListener{
	private TextView number,numbers;
	private CustomViewPager pager;
	private String id,qq,phone,shop_id;
	private ImageView qqimage,phoneimage,backimage;
	private List<ImageView>list=new ArrayList<ImageView>();
	private TextView name,shopname,price,shopin,time;
	private Button focus;
	private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
			"http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
	"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product);
		intviews();

	}
	private void intviews() {
		// TODO Auto-generated method stub
		name=(TextView) findViewById(R.id.name);
		id=getIntent().getStringExtra("id");
		focus=(Button) findViewById(R.id.focusbutton);
		focus.setOnClickListener(this);
		focus.setSelected(true);
		shopname=(TextView) findViewById(R.id.shopname);
		shopin=(TextView) findViewById(R.id.shopin);
		shopin.setOnClickListener(this);
		price=(TextView) findViewById(R.id.price);
		time=(TextView) findViewById(R.id.time);
		qqimage=(ImageView) findViewById(R.id.qq);
		qqimage.setOnClickListener(this);
		phoneimage=(ImageView) findViewById(R.id.phone);
		phoneimage.setOnClickListener(this);
		backimage=(ImageView) findViewById(R.id.backImage);
		backimage.setOnClickListener(this);
		pager=(CustomViewPager) findViewById(R.id.viewpager);
		number=(TextView) findViewById(R.id.number);
		numbers=(TextView) findViewById(R.id.numbers);
		BitmapUtils utils=new BitmapUtils(this);
		for(int i=0;i<imageUrls.length;i++){
			ImageView imageView=new ImageView(this);
			utils.display(imageView, imageUrls[i]);
			list.add(imageView);
		}
		number.setText(String.valueOf("/"+list.size()));
		pager.setAdapter(new Myadaper());
		pager.setOnPageChangeListener(new MyListener());
		new Infotask().execute(Consts.URL);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.qq:

			break;
		case R.id.phone:
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + phone));
			startActivity(intent);
			break;
		case R.id.shopin:
			Intent intentshop = new Intent(this,ShopActivity.class);
			intentshop.putExtra("id", shop_id);
			startActivity(intentshop);
			break;
		}
	}
	class Myadaper extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((CustomViewPager)container).removeView((ImageView)object);
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			ImageView imageView=list.get(position);
			/*ViewPager.LayoutParams layoutParams=new ViewPager.LayoutParams(ActivityProductActivity.this,null);
			WindowManager manager=ActivityProductActivity.this.getWindowManager();
			Display display = manager.getDefaultDisplay();
			int width=display.getWidth();
			layoutParams.height=width;
			layoutParams.width=android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT;
			imageView.setLayoutParams(layoutParams);*/
			imageView.setScaleType(ScaleType.FIT_XY);
			((CustomViewPager)container).addView(imageView);
			return imageView;
		}
	}
	class MyListener implements OnPageChangeListener{
		int count=0;
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			count=arg0;
			numbers.setText(String.valueOf(count+1));
		}
	}
	class Infotask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "preach");
				requestParams.addBodyParameter("a", "preach_info");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("preach", "preach_info", cartTime));
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
								name.setText(object.optString("title"));
								price.setText(object.optString("price"));
								shop_id=object.optString("shop_id");
								String  times=object.optString("fujin_end_time");
								SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
								time.setText("截止时间:"+dateFormat.format(Long.valueOf(times)*1000));
								new ShopInfotask().execute(Consts.URL);
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
								shopname.setText("�?"+object.optString("name")+"�?");
								phone=object.optString("phone");
								qq=object.optString("qq");
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
