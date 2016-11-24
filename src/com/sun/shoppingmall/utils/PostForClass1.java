package com.sun.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.sun.shoppingmall.entity.ClassBean1;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

@SuppressWarnings({ "unused" })
public class PostForClass1 {
	private Context mcontext;
	private String uerId, cartTime, cartOrder, productId, num;
	private JSONObject jsGoodsInfo;
	private Handler mhandler;

	/**
	 * 构造方法 利用post 去请求 分类 里边的 数据
	 * 
	 * @param context
	 * @param mhandler
	 */

	public PostForClass1(Context context, Handler mhandler) {

		this.mcontext = context;
		this.mhandler = mhandler;
		postToCart("goods", "category1_list");
	}

	private void goodsInfo() {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr) {
		goodsInfo();
		
		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.POST_API, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response);
							mhandler.sendMessage(mhandler.obtainMessage(11,
									json));
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {

					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				// 建立 map 集合 里面封装了 的所有信息
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

	/**
	 * md5 加密
	 * 
	 * @param plainText
	 */
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

	/**
	 * 两次 md5 加密
	 * 
	 * @param str
	 * @param strr
	 */
	private void getCartInfo(String str, String strr) {

		md5sCart(strr + str + cartTime + "sunrock");
		md5sCart(cartOrder);

	}
}
