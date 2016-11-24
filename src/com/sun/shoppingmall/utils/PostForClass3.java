package com.sun.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sun.shoppingmall.entity.ClassBean2;
import com.sun.shoppingmall.entity.ClassBean3;

import android.content.Context;
import android.os.Handler;

public class PostForClass3 {
	private Context mcontext ;
	private String cartTime ,cartOrder ;
	private JSONObject jsGoodsInfo ;
	private Handler mhandler ;
	private String id ;
	private ClassBean2 bean2 ;

	

	public PostForClass3(Context context ,Handler mhandler ,String id ,ClassBean2 bean2 ) {
	     
		this.mhandler = mhandler ;
		this.mcontext = context ;
		this.id = id ;
		this.bean2 = bean2 ;
		postToCart("goods", "category3_list",id );
	}

	private void goodsInfo(String id ) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("id", id );
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,String id ) {
		goodsInfo(id);

		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.POST_API,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							List<ClassBean3> list3 = new ArrayList<ClassBean3>() ;
							JSONObject json = new JSONObject(response);
							for(int i = 0; i < json.length()- 2 ;i ++){
								JSONObject js = json.optJSONObject(i+"") ;
								if (js==null) {
									
								}else {
									ClassBean3 bean3 = new  ClassBean3() ;
									bean3.setId(js.optString("id")) ;
									bean3.setTitle(js.optString("title")) ;
									list3.add(bean3) ;
								}
								
							}
							bean2.setItems(list3) ;
							mhandler.sendMessage(mhandler.obtainMessage(13,json)) ;
						
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
		stringRequest.setShouldCache(false) ;
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
