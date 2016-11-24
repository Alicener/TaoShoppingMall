package com.sun.shoppingmall.activity;

import java.util.Date;

import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AccountActivity extends Activity{
	
	private SharedPreferences sp;
	private String user_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_account);
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText("账户余额");
		sp = getSharedPreferences("user_id", MODE_PRIVATE);
		user_id = sp.getString("user_id", "");
		user_id = "31";
		new numTask().execute(Consts.URL);
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
								TextView money = (TextView) findViewById(R.id.tv_account);
								money.setText("¥"+object.optString("balance"));
								
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
