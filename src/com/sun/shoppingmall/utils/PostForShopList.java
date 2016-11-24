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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sun.shoppingmall.customview.CustomGridView;

public class PostForShopList {
	private String num;
	private Context context;
	private Handler mhandler;
	protected String cartTime,cartOrder;
	private JSONObject json;
	private int shop_page=1;
	private CustomGridView gridview;
	private String x,y,keyword,county,circle,category,radius,order;
	public PostForShopList(Context context,  String num,CustomGridView gridview,Handler mhandler,
			String x,String y,String keyword,String county,String circle,String category,String radius,String order) {
		super();
		this.num = num;
		this.context = context;
		this.gridview=gridview;
		this.mhandler = mhandler;
		this.x = x;
		this.y = y;
		this.keyword = keyword;
		this.county = county;
		this.circle = circle;
		this.category = category;
		this.radius = radius;
		this.order = order;
	}
	
	public void addListData(){
		requestData("shop_list", "shop", num, shop_page+"");
		shop_page++;
	}
	
	private void goodsInfo(String str, String strr ) {

		json = new JSONObject();
		try {
			//keyword,county,circle,category,radius,order;
			json.put("num", num);
			json.put("page", "1");
			json.put("x",x );
			json.put("y", y);
			json.put("keyword", keyword);
			json.put("county", county);
			json.put("circle", circle);
			json.put("category", category);
			json.put("radius", radius);
			json.put("order",order );
			
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
							JSONArray array = json.optJSONArray("result") ;
							if(array==null){	
								Toast.makeText(context, "请求服务器失败?", Toast.LENGTH_LONG).show();
							}else{
								mhandler.sendMessage(mhandler.obtainMessage(11,array)) ;
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
				map.put("param", json.toString());
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
