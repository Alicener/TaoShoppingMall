package com.sun.shoppingmall.wxapi;

import java.util.Date;

import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.PostForLogin;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	public static final String APP_ID = "wxb7f63a1f10519cf0";
	public static final String APP_sc = "406ed387337e8c6f619b1846ece674e6";
	private IWXAPI mApi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
		mApi.handleIntent(this.getIntent(), this);
	}
	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			/*获取token*/
			SendAuth.Resp sendResp = (SendAuth.Resp) resp;
			String codes=sendResp.code;
			//String openid=sendResp.openId;
			//Log.i("CODE", codes);
			String url="https://api.weixin.qq.com/sns/oauth2/access_token?";
			url+="appid="+APP_ID;
			url+="&secret="+APP_sc;
			url+="&code="+codes;
			url+="&grant_type=authorization_code";
			//Log.i("CODE", url);
			//PostForLogin forLogin=new PostForLogin(WXEntryActivity.this, mHandler, openid, "wechat");031DUUao0cVFOd1K4ico0sRSao0DUUaL
			// new Openidtask().execute("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb7f63a1f10519cf0&secret=406ed387337e8c6f619b1846ece674e6&code=031DUUao0cVFOd1K4ico0sRSao0DUUaL&grant_type=authorization_code");
			new Openidtask().execute(url);
			break;
		}
		finish();
	}
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 198:
				try {
					JSONObject json = (JSONObject) msg.obj;
					//optString
					if (Integer.parseInt(json.optString("error")) == 0) {
						Toast.makeText(getApplicationContext(), "登录成功",
								Toast.LENGTH_LONG).show();
						SharedPreferences mySharedPreferences = getSharedPreferences(
								"user_id", Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = mySharedPreferences
								.edit();
						//String s=json.optString("user_id");
						//Log.i("", s);
						editor.putString("id", json.optString("user_id"));
						//Log.i("userid", json.optString("user_id"));
						editor.commit();
						Intent intent = new Intent();
						intent.setAction("refresh");
						sendBroadcast(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"帐号或密码不正确", Toast.LENGTH_LONG)
								.show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				break;
			}
		}
	};
	class  Openidtask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils(Consts.POST_TIME_OUT);
				httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							JSONObject object=new JSONObject(response);
							String openid=object.getString("openid");
							PostForLogin forLogin=new PostForLogin(WXEntryActivity.this, mHandler, openid, "wechat");
						} catch (Exception e) {
							// TODO Auto-generated catch block
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
