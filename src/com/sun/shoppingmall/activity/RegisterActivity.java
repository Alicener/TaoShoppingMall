package com.sun.shoppingmall.activity;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.customview.CountDownTimerUtils;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;
import com.sun.shoppingmall.utils.ToastUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnClickListener{
	
	private EditText phone,yanzheng,password;
	private TextView second,register;
	private String code;
	private CountDownTimerUtils count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		initview();
	}
	
	public void initview(){
		phone = (EditText) findViewById(R.id.phone);
		yanzheng = (EditText) findViewById(R.id.yanzhengma);
		password = (EditText) findViewById(R.id.password);
		second = (TextView) findViewById(R.id.second);
		register = (TextView) findViewById(R.id.register);
		second.setText("手机验证码");
		count = new CountDownTimerUtils(second, 60000, 1000);
		register.setOnClickListener(this);
		second.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.second:
			new codeTask().execute(Consts.URL);
			count.start();
			break;
		case R.id.register:
			new registerTask().execute(Consts.URL);
			break;
		default:
			break;
		}
	}
	
	class codeTask extends AsyncTask<String , String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			try {
				HttpUtils httpUtils = new HttpUtils();
				RequestParams requestParams = new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "mobileCode");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("mobile", phone.getText().toString());
				jsonObject.put("code", "");
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime() + "";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("user", "mobileCode", cartTime));
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String response = arg0.result;
						
						JSONObject object;
						try {
							object = new JSONObject(response);
							if(object.getString("error").equals("0")){
								ToastUtil.showToast(RegisterActivity.this, "验证码已发送");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	class registerTask extends AsyncTask<String , String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			try {
				HttpUtils httpUtils = new HttpUtils();
				RequestParams requestParams = new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "register");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("user_name", "");
				jsonObject.put("mobile", phone.getText().toString());
				jsonObject.put("email", "");
				jsonObject.put("pwd", password.getText().toString());
				jsonObject.put("code", yanzheng.getText().toString());
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime() + "";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("user", "register", cartTime));
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String response = arg0.result;
						try {
							JSONObject json = new JSONObject(response);
							if (json.optString("error").equals("0")) {
								ToastUtil.showToast(RegisterActivity.this, "注册成功");
							}else {
								ToastUtil.showToast(RegisterActivity.this, json.getString("msg"));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
