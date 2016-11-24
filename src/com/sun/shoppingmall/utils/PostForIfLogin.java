package com.sun.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

@SuppressWarnings({"unused"})
public class PostForIfLogin {
	private Context mcontext ;
	private String user_Name,cartTime ,cartOrder,mobile , email ,pwd ;
	private JSONObject jsGoodsInfo ;
	private Handler mhandler ;
	

	public PostForIfLogin(Context context ,String user_Name,String mobile ,String email ,String pwd ,Handler mhandler) {
	     
		this.mcontext = context ;
		this.user_Name = user_Name ;
		
		this.mobile = mobile ;
		this.email = email ;
		this.pwd = pwd ;
		this.mhandler = mhandler ;
		postToCart("user", "register",user_Name,mobile,email ,pwd);
	}

	private void goodsInfo(String str, String strr, String strrr, String strrrr) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("user_name", str);
			jsGoodsInfo.put("mobile", strr) ;
			jsGoodsInfo.put("email", strrr) ;
			jsGoodsInfo.put("pwd", strrrr) ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,String user_Name,String mobile ,String email,String pwd) {
		goodsInfo(user_Name, mobile,email,pwd);
		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.POST_API,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response);
							mhandler.sendMessage(mhandler.obtainMessage(11,json)) ;
					
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {

					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("openid", "1");
				cartTime = new Date().getTime() + "";
				getCartInfo(str, strr);
				map.put("sign", cartOrder);
				map.put("a", strr);
				map.put("c", str);
				map.put("timesnamp", cartTime);
				map.put("param", jsGoodsInfo.toString());
				return map;
			}
		};
		mQueue.add(stringRequest);

	}

	public void md5sCart(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			cartOrder = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}
	}

	private void getCartInfo(String str, String strr) {

		md5sCart(strr + str + cartTime + "sunrock");
		md5sCart(cartOrder);

	}
}
