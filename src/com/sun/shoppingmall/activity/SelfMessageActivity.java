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
import com.sun.shoppingmall.MainActivity;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.customview.DialogActivity;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

public class SelfMessageActivity extends Activity implements OnClickListener{
	
	private RelativeLayout rela1,relasex,relabirthday,relaimg;
	private TextView out,username,email,phone,name,birthday,sex,sexcancel,secret,man,woman,imgcancel,camera,photo;
	private SharedPreferences sp;
	private String userid;
	private PopupWindow sexpop,imgpop;
	private int requestCode;
	 View view=null;
	boolean isMonthSetted=false,isDaySetted=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_selfmessage);
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText("个人信息");
		sp = getSharedPreferences("user_id", MODE_PRIVATE);
		userid = "31";
		initview();
	}
	
	public void initview(){
		username = (TextView) findViewById(R.id.msg_username);
		email = (TextView) findViewById(R.id.msg_email);
		phone = (TextView) findViewById(R.id.msg_phone);
		name = (TextView) findViewById(R.id.msg_name);
		birthday = (TextView) findViewById(R.id.msg_birthday);
		sex = (TextView) findViewById(R.id.msg_sex);
		rela1 = (RelativeLayout) findViewById(R.id.msg_rela1);
		relabirthday = (RelativeLayout) findViewById(R.id.msg_relabirthday);
		relasex = (RelativeLayout) findViewById(R.id.msg_relasex);
		relaimg = (RelativeLayout) findViewById(R.id.msg_relaimg);
		out = (TextView) findViewById(R.id.out);
		out.setOnClickListener(this);
		relabirthday.setOnClickListener(this);
		relaimg.setOnClickListener(this);
		relasex.setOnClickListener(this);
		rela1.setOnClickListener(this);
		new userinfoTask().execute(Consts.URL);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.msg_rela1:
			Intent intent2 = new Intent(SelfMessageActivity.this,ChangNameActivity.class);
			startActivity(intent2);
			break;
		case R.id.out:
			Editor editor = sp.edit();
			editor.clear();
			editor.commit();
			Intent intent = new Intent(SelfMessageActivity.this,MainActivity.class);
			startActivity(intent);
			break;
		case R.id.msg_relabirthday:
			Intent intent1 = new Intent();
			intent1.setClass(SelfMessageActivity.this, DialogActivity.class);
			requestCode = 1;  
            startActivityForResult(intent1, requestCode);
			break;
		case R.id.msg_relasex:
			sexWindow(v);
			break;
		case R.id.msg_relaimg:
			imgWindow(v);
			break;
		case R.id.sex_cancel:
			sexpop.dismiss();
			break;
		case R.id.img_cancel:
			imgpop.dismiss();
			break;
		default:
			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String birthda = null;
		if (data == null) {
			birthda = "1996-01-01";
		}else {
			 birthda = data.getStringExtra("birthday");
		}
		switch (requestCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case 1:
			   birthday.setText(birthda);
		    break;
		default:
		    break;
		    }
		}
	
	/**
	 * @param v
	 *  选择性别的popWindow
	 */
	private void sexWindow(View v) {
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View views = layoutInflater.inflate(R.layout.pop_sex, null);
		secret = (TextView) views.findViewById(R.id.sex_secreat);
		sexcancel = (TextView) views.findViewById(R.id.sex_cancel);
		man = (TextView) views.findViewById(R.id.sex_m);
		woman = (TextView) views.findViewById(R.id.sex_w);
		man.setOnClickListener(this);
		woman.setOnClickListener(this);
		sexcancel.setOnClickListener(this);
		secret.setOnClickListener(this);
		sexpop = new PopupWindow(views, windowManager
				.getDefaultDisplay().getWidth(), 500);
		
		WindowManager.LayoutParams params=getWindow().getAttributes();  
		params.alpha=0.7f;  
		getWindow().setAttributes(params); 
		// 使其聚集
		sexpop.setFocusable(true);
		// 设置允许在外点击消失
		sexpop.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		sexpop.setBackgroundDrawable(new BitmapDrawable());
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		sexpop.showAtLocation(views, Gravity.NO_GRAVITY,location[0], 
				location[1]+windowManager.getDefaultDisplay().getHeight()-500);
		
		sexpop.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams params=getWindow().getAttributes();  
			    params.alpha=1f;  
			    getWindow().setAttributes(params); 
			}
		});
	}
	
	/**
	 * @param v
	 *  选择头像的popWindow
	 */
	private void imgWindow(View v) {
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View views = layoutInflater.inflate(R.layout.pop_img, null);
		imgcancel = (TextView) views.findViewById(R.id.img_cancel);
		camera = (TextView) views.findViewById(R.id.img_camera);
		phone = (TextView) views.findViewById(R.id.img_photo);
		phone.setOnClickListener(this);
		camera.setOnClickListener(this);
		imgcancel.setOnClickListener(this);
		imgpop = new PopupWindow(views, windowManager
				.getDefaultDisplay().getWidth(), 400);
		
		WindowManager.LayoutParams params=getWindow().getAttributes();  
		params.alpha=0.7f;  
		getWindow().setAttributes(params); 
		// 使其聚集
		imgpop.setFocusable(true);
		// 设置允许在外点击消失
		imgpop.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		imgpop.setBackgroundDrawable(new BitmapDrawable());
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		imgpop.showAtLocation(views, Gravity.NO_GRAVITY,location[0], 
				location[1]+windowManager.getDefaultDisplay().getHeight()-400);
		
		imgpop.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams params=getWindow().getAttributes();  
			    params.alpha=1f;  
			    getWindow().setAttributes(params); 
			}
		});
	}
	
	/**
	 * @author Administrator
	 * 请求用户信息
	 */
	class userinfoTask extends AsyncTask<String , String, String>{
		
		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			try {
				HttpUtils httpUtils = new HttpUtils();
				RequestParams requestParams = new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "userInfo");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("user_id", "31");
				jsonObject.put("key", "*");
				requestParams.addBodyParameter("param", jsonObject.toString());
				String time = new Date().getTime() + "";
				requestParams.addBodyParameter("timesnamp", time);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("user", "userInfo", time));
				httpUtils.send(HttpMethod.POST, url,requestParams, new RequestCallBack<String>() {

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
							if (json.getString("error").equals("0")) {
								
								username.setText(json.optString("user_name"));
								email.setText(json.optString("email"));
								phone.setText(json.optString("mobile"));
								name.setText(json.optString("name"));
								birthday.setText(json.optString("birthday"));
								sex.setText(json.optString("sex"));
								    /*"id": "31",
								    "user_name": "gaowenwne",
								    "pwd": "350c24520e6fccf1fb83f7fa68eeea25",
								    "nickname": "",
								    "email": "gaowen_wen@163.com",
								    "mobile": "18210766072",
								    "qq": "",
								    "name": "",
								    "sex": "",
								    "birthday": "",
								    "self_ntroduction": "",
								    "credits": "110",
								    "img": "",
								    "reg_date": "2015-12-03",
								    "last_time": "2015-12-03",
								    "state": "0",
								    "openid": "",
								    "open_type": "qq",
								    "error": "0",
								    "msg": "请求成功"*/
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
