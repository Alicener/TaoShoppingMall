package com.sun.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class PostForOrderDetail {
	
	private String shop_num,class_id,order,radius,supplier_id;
	private ListView listview;
	private Context context;
	private Handler mhandler;
	protected String cartTime,orderid;
	private JSONObject jsGoodsInfo;
	private String cartOrder;
	private int shop_page=1;
	public PostForOrderDetail(Context context,  String orderid,Handler mhandler) {
		super();
		this.context = context;
		this.mhandler = mhandler;
		this.orderid = orderid;
	}
	
	public void addListData(){
		requestData("order_d_list", "order");
	}
	
	private void goodsInfo( ) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("order_id", orderid);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void requestData(final String a,final String c){
		goodsInfo();

		RequestQueue mQueue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					@SuppressLint("NewApi")
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response) ;
							mhandler.sendMessage(mhandler.obtainMessage(12,json)) ;
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
				getCartInfo(c, a);
				map.put("sign", cartOrder);
				map.put("a", a);
				map.put("c", c);
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

	/**
	 * 这里�?? �?? md5 两次加密�?? 方法
	 * 
	 * @param str
	 *            这个 �?? c
	 * @param strr
	 *            这个 �?? a
	 */
	private void getCartInfo(String str, String strr) {
		md5sCart(strr + str + cartTime + "sunrock");
		md5sCart(cartOrder);
	}
	
}
