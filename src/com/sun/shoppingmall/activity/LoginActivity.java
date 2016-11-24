package com.sun.shoppingmall.activity;

import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.utils.AccessTokenKeeper;
import com.sun.shoppingmall.utils.AppConstants;
import com.sun.shoppingmall.utils.Constantsss;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;
import com.sun.shoppingmall.utils.PostForLogin;
import com.sun.shoppingmall.utils.ToastUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends Activity implements OnClickListener{
	private ImageView backimagview;
	private EditText name,password;
	private Button button;
	private TextView resigt;
	private SharedPreferences sp;
	private static String mappid;
	private Tencent mTencent;
	private AuthInfo mAuthInfo;
	private ImageView mImageView,clickforweibo,clickforweixin;
	private SsoHandler mSsoHandler;
	private Oauth2AccessToken mAccessToken;
	private String openid, nickname;
	private SendAuth.Req req;
    private IWXAPI msgapi;
    private UserInfo mInfo;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		intviews();
		intetQQinfor();
		intetWBinfo() ;
	}
	
	private void intetQQinfor() {
		mappid = AppConstants.APP_ID;
		String s=mappid;
		//mappid = "222222";
		if (mTencent == null) {
			mTencent = Tencent.createInstance(mappid, LoginActivity.this);
			//Log.i("", s);
		}
	}
	
	private void intetWBinfo() {
		
		   mAuthInfo = new AuthInfo(this, Constantsss.APP_KEY, Constantsss.REDIRECT_URL, Constantsss.SCOPE);
	}
	
	private void intviews() {
		sp=getSharedPreferences("user_id", Context.MODE_PRIVATE);
		mImageView = (ImageView) findViewById(R.id.clickforqq);
		clickforweibo = (ImageView) findViewById(R.id.clickforweibo);
		clickforweixin=(ImageView) findViewById(R.id.clickforweixin);
		backimagview=(ImageView) findViewById(R.id.backImage);
		mImageView.setOnClickListener(this);
		clickforweixin.setOnClickListener(this);
		clickforweibo.setOnClickListener(this) ;
		backimagview.setOnClickListener(this);
		name=(EditText) findViewById(R.id.name);
		password=(EditText) findViewById(R.id.password);
		button=(Button) findViewById(R.id.login);
		button.setOnClickListener(this);
		resigt=(TextView) findViewById(R.id.regist);
		resigt.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.login:
			new Logintask().execute(Consts.URL);
			break;
		case R.id.regist:
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.clickforqq:
			mTencent.login(LoginActivity.this, "all", loginListener);
			break;

		case R.id.clickforweibo:
			   mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
			   mSsoHandler.authorize(new AuthListener());
			break;
		case R.id.clickforweixin:
			msgapi=WXAPIFactory.createWXAPI(this,com.sun.payweixin.Constants.APP_ID ,true);
			msgapi.registerApp(com.sun.payweixin.Constants.APP_ID);
			req=new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			//req.state = "carjob_wx_login";
			req.state = "wechat_sdk_demo_test";
			//发送授权登陆请求
			msgapi.sendReq(req);
			finish();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.REQUEST_LOGIN
				|| requestCode == Constants.REQUEST_APPBAR) {
			Tencent.onActivityResultData(requestCode, resultCode, data,
					loginListener);
		}
		 // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
		super.onActivityResult(requestCode, resultCode, data);
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			initOpenidAndToken(values);
			//updateUserInfo();
		}
	};

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				com.sun.shoppingmall.utils.Util.showResultDialog(LoginActivity.this,
						"返回为空", "登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				com.sun.shoppingmall.utils.Util.showResultDialog(LoginActivity.this,
						"返回为空", "登录失败");
				return;
			}
			initOpenidAndToken((JSONObject) response);
			updateUserInfo();
		//	Util.showResultDialog(Login.this, response.toString(),
				//	"登录成功");
			//doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {
			//initOpenidAndToken(values);
		}

		@Override
		public void onError(UiError e) {
			com.sun.shoppingmall.utils.Util.toastMessage(LoginActivity.this,
					"onError: " + e.errorDetail);
			com.sun.shoppingmall.utils.Util.dismissDialog();
		}

		@Override
		public void onCancel() {
		}
	}
	
	// 下面的代码 是向这个Tencent 对像里面传送刚请求的数据，，方便下次的数据的请求...
	public void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			//openid  00DC31068890F54D0CD8668E58D71839
			openid = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openid)) {
				// 下面的这两行代码 得到之后 ，，就内总实现 mTencent.isSessionValid() 为true 了，之前
				// 为false
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openid);
			}
		} catch (Exception e) {
		}
	}

	// 这下面的是请求用户的用户名和头像信息的 接口
	private void updateUserInfo() {

		IUiListener listener = new IUiListener() {

			@Override
			public void onError(UiError e) {
			}

			@Override
			public void onComplete(final Object response) {

				JSONObject js = (JSONObject) response;
				nickname = js.optString("nickname");
				//String s=nickname;
				//Log.i("nickname", s);
				//System.out.println(openid + "-----------" + nickname);
				PostForLogin postLogin = new PostForLogin(LoginActivity.this, mHandler,openid, "qq");
			}
			@Override
			public void onCancel() {

			}
		};
		// 我就试一下 这两行代码 是弹出对话框的么....
		mInfo = new UserInfo(this, mTencent.getQQToken());
		mInfo.getUserInfo(listener);

	}
	
	/**
	 * @author Administrator
	 * 微博的回调
	 */
	class AuthListener implements WeiboAuthListener{

		@Override
		public void onCancel() {
			
		}

		@Override
		public void onComplete(Bundle values) {
			   mAccessToken = Oauth2AccessToken.parseAccessToken(values);
	            //从这里获取用户输入的 电话号码信息 
	            String  phoneNum =  mAccessToken.getPhoneNum();
	            System.out.println("我想看看用户id:"+mAccessToken.getUid());
	            if (mAccessToken.isSessionValid()) {
	                // 显示 Token
	                // 保存 Token 到 SharedPreferences
	                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
	               // Toast.makeText(Login.this, 
	                       // "授权成功",Toast.LENGTH_SHORT).show();
	            } else {          	
	            	 Toast.makeText(LoginActivity.this, 
		                        "授权失败...",Toast.LENGTH_SHORT).show();
	            }
	            String openid=mAccessToken.getUid();
	            PostForLogin forLoginweibo=new PostForLogin(LoginActivity.this, mHandler, openid, "weibo");
	           // thirdUser = new ThirdUserInfo();  
               // thirdUser.setThirdID( mAccessToken.getUid());  //mAccessToken.getUid() ，获取到UID，作为身份的唯一标示  
              // UsersAPI mUsersAPI = new UsersAPI( mAccessToken );  
              //  long uid = Long.parseLong(mAccessToken.getUid());  
              // mUsersAPI.show(uid, mListener); //获取用户基本信息  
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			
		}
		
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
						String s = json.optString("user_id");
						Editor editor = sp.edit();
						editor.putString("user_id", s);
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
	
	class Logintask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "login");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("phoneNumber", name.getText().toString());
				jsonObject.put("password", password.getText().toString());
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("user", "login", cartTime));
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
								String userid=object.getString("user_id");
								Editor editor = sp.edit();
								editor.putString("user_id", userid);
								editor.putString("phone", name.getText().toString());
								editor.commit();
								ToastUtil.showToast(LoginActivity.this, "登录成功");
								Intent intent = new Intent();
								intent.setAction("refresh");
								sendBroadcast(intent);
								LoginActivity.this.finish();
							}else{
								ToastUtil.showToast(LoginActivity.this, "用户名或密码错误");
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
