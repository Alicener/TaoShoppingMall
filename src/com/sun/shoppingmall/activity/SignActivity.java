package com.sun.shoppingmall.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;

public class SignActivity extends Activity implements OnClickListener{
	
	private RelativeLayout back;
	private TextView money,sign,data;
	private String time,user_id;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sign);
		sp = getSharedPreferences("user_id", MODE_PRIVATE);
		user_id = sp.getString("user_id", "");
		user_id = "2";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		time = formatter.format(curDate);
		initview();
	}
	
	public void initview(){
		back = (RelativeLayout) findViewById(R.id.relaback);
		money = (TextView) findViewById(R.id.tv_money);
		sign = (TextView) findViewById(R.id.tv_sign);
		data = (TextView) findViewById(R.id.tv_data);
		data.setText(time);
		new numTask().execute(Consts.URL);
		new is_signTask().execute(Consts.URL);
		back.setOnClickListener(this);
		sign.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaback:
			SignActivity.this.finish();
			break;
		case R.id.tv_sign:
			new newSignTask().execute(Consts.URL);
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * @author Administrator
	 * 签到
	 */
	class newSignTask extends AsyncTask<String , String , String >{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "sign");
				requestParams.addBodyParameter("a", "new_sign");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("user_id", user_id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("sign", "new_sign", cartTime));
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
								Toast.makeText(SignActivity.this, "签到成功", 1).show();
								new numTask().execute(Consts.URL);
								new is_signTask().execute(Consts.URL);
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
	
	/**
	 * @author Administrator
	 * 判断是否已签到
	 */
	class is_signTask extends AsyncTask<String , String , String >{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "sign");
				requestParams.addBodyParameter("a", "is_sign");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("user_id", user_id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("sign", "is_sign", cartTime));
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
								if (object.getString("result").equals("1")) {
									sign.setClickable(false);
									sign.setBackground(getResources().getDrawable(R.drawable.tuoyuan1));
									sign.setTextColor(getResources().getColor(R.color.textcolor));
								}else {
									sign.setClickable(true);
									sign.setBackground(getResources().getDrawable(R.drawable.tuoyuan));
									sign.setTextColor(getResources().getColor(R.color.red));
								}
								
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
	
	/**
	 * @author Administrator
	 * 喜币数量
	 */
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
								money.setText(object.optString("credits"));
								
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
