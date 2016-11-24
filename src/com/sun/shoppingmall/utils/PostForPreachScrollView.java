package com.sun.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class PostForPreachScrollView {
	private String shop_num,class_id,order;
	private PullToRefreshScrollView listview;
	private Context context;
	private Handler mhandler;
	protected String cartTime;
	private JSONObject jsGoodsInfo;
	private String cartOrder;
	private int shop_page=1;
	public PostForPreachScrollView(Context context,  String shop_num,PullToRefreshScrollView listview,Handler mhandler) {
		super();
		this.shop_num = shop_num;
		this.context = context;
		this.listview=listview;
		this.mhandler = mhandler;
	}
	public void setAttrs(String order,String class_id){
		this.order=order;
		this.class_id=class_id;
	}
	public void removeListData(){
		shop_page=1;
		
	}
	public void addListData(){
		requestData("preach_list", "preach", shop_num, shop_page+"");
		shop_page++;
	}
	
	private void goodsInfo(String str, String strr ) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("num", "8");
			jsGoodsInfo.put("page", "1");	
			jsGoodsInfo.put("x", "116.12");
			jsGoodsInfo.put("y", "36.123");	
			jsGoodsInfo.put("keyword", "");
			jsGoodsInfo.put("category", "");	
			jsGoodsInfo.put("county", "");	
			jsGoodsInfo.put("circle", "");
			jsGoodsInfo.put("radius", "");	
			jsGoodsInfo.put("order", order);
			jsGoodsInfo.put("class_id", class_id);	
			jsGoodsInfo.put("class_id2", "");
			jsGoodsInfo.put("shop_id", "");	
			jsGoodsInfo.put("supplier_id", "");
			jsGoodsInfo.put("is_fujin", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void requestData(final String a,final String c,final String shop_num,final String shop_page){
		goodsInfo(shop_num, shop_page );

		RequestQueue mQueue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					@SuppressLint("NewApi")
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response) ;
							//JSONArray array = json.optJSONArray("result") ;
							if(response==null){	
								Toast.makeText(context, "请求服务器失�?", Toast.LENGTH_LONG).show();
							}else{
								mhandler.sendMessage(mhandler.obtainMessage(11,json)) ;
							}
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
