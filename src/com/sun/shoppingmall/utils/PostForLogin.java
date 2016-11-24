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
public class PostForLogin {
	private Context mcontext ;
	private String cartTime ,cartOrder ,openid,type ;
	private JSONObject jsGoodsInfo ;
	private Handler mhandler ;
	

	public PostForLogin(Context context ,Handler mhandler ,String openid ,String type ) {
	     
		this.mcontext = context ;
		this.mhandler = mhandler ;
		this.openid = openid ;
		this.type = type ;

		postToCart("user", "open_new",openid,type);
	}

	private void goodsInfo(String openid ,String type) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("openid", openid);
			jsGoodsInfo.put("type",type) ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,String openid,String type ) {
		goodsInfo(openid,type);

		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.POST_API,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response);
							mhandler.sendMessage(mhandler.obtainMessage(198,json)) ;
						
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
