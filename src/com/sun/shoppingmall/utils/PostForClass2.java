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
public class PostForClass2 {
	private Context mcontext;
	private String uerId, cartTime, cartOrder, productId, num;
	private JSONObject jsGoodsInfo;
	private Handler mhandler;

	/**
	 * 左边的listview 的数据 构造方法
	 * 分类
	 * @param context
	 * @param mhandler
	 * @param id
	 */
	public PostForClass2(Context context, Handler mhandler, String id) {

		this.mcontext = context;
		this.mhandler = mhandler;
		postToCart("goods", "category2_list", id);
	}

	private void goodsInfo(String id) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr, String id) {
		goodsInfo(id);

		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.POST_API,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response);
							//发消息
							mhandler.sendMessage(mhandler.obtainMessage(12,
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
		stringRequest.setShouldCache(false);
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
