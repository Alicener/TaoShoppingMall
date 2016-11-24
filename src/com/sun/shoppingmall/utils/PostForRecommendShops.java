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

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sun.shoppingmall.entity.ProduceNearbyBean;

@SuppressWarnings({"unused"})
public class PostForRecommendShops {
	private Context mcontext ;
	private String num,page,cartTime ,cartOrder , longitude,latitude;
	private JSONObject jsGoodsInfo  ,jss ;
	private GridView gridView ;
	private ImageLoader mimageLoader ;
	private DisplayImageOptions options  ;
	private Handler mhandler;
	private int pages=1;
	/**
	 * 推荐明星商家�? 构�?�方�?
	 * @param context
	 * @param num
	 * @param page
	 * @param gridView
	 * @param mimageLoader
	 * @param options
	 */
	//String.valueOf(latitude),String.valueOf(longitude)  116.977706   36.651345
	public PostForRecommendShops(Context context , String num ,String page,Handler mhandler) {
		this.mcontext = context ;
		this.num  = num ;
		this.page = page ;
		this.mhandler=mhandler;
	}
	public void addmoredata(){
		postToCart("shop", "nearby",num ,pages+"",longitude,latitude);
		pages++;
	}
	private void goodsInfo(String str, String strr,String x ,String y) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("num", "8");
			jsGoodsInfo.put("page",pages+"");
			jsGoodsInfo.put("circle", "");
			jsGoodsInfo.put("x", "116.977706") ;
			jsGoodsInfo.put("y", "36.651345") ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,final String num ,final String page,String x ,String y ) {
		goodsInfo(num, page,x ,y );
		final List<ProduceNearbyBean> listPro = new  ArrayList<ProduceNearbyBean>() ;
		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject js = new JSONObject (response);
							if(js==null){
								
							}else{
								mhandler.sendMessage(Message.obtain(mhandler, 11, js));
							}
						} catch (Exception e) {
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
