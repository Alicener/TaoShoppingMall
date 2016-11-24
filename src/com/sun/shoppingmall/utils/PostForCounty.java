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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sun.shoppingmall.adapter.CircleAdapter;
import com.sun.shoppingmall.adapter.GroupAdapter;
import com.sun.shoppingmall.entity.CountyBean;

@SuppressWarnings({ "unused" })
public class PostForCounty {
	private Context mcontext;
	private String cartTime, cartOrder, city_list;
	private ListView list, listt;
	private JSONObject jsGoodsInfo;
	private List<CountyBean> listData;	
	private ArrayList<CountyBean> lists=new ArrayList<CountyBean>();
	private PopupWindow popupWindow;
	private Handler mhandler;
	private ClickPositonZero recall;
	private GroupAdapter adapter;
	private int positions;
	public PostForCounty(ClickPositonZero listener, Context mcontext,
			String city_list, ListView list, ListView listt,
			PopupWindow popupWindow, Handler mhandler) {
		this.recall = listener;
		this.mcontext = mcontext;
		this.city_list = city_list;
		this.list = list;
		this.listt = listt;
		this.popupWindow = popupWindow;
		this.mhandler = mhandler;
		listData = new ArrayList<CountyBean>();
		postToCart("area", "county_list", city_list);
	}

	private void goodsInfo(String str) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("fid", str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr, String city_list) {
		goodsInfo(city_list);

		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					

					@Override
					public void onResponse(String response) {
						CountyBean county0 = new CountyBean();
						county0.setCounty("附近");
						listData.add(county0);
						try {
							JSONObject json = new JSONObject(response);
							for (int i = 0; i < json.length() - 2; i++) {
								CountyBean county = new CountyBean();
								JSONObject countyJS = json
										.optJSONObject(i + "");
								county.setCounty(countyJS.optString("county"));
								county.setCountyid(countyJS
										.optString("countyid"));
								listData.add(county);
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
						adapter = new GroupAdapter(mcontext,listData);
						list.setAdapter(adapter);
						list.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								//adapter.notifyDataSetChanged();
								lists.clear();
								positions=position;
								if (position == 0) {
									//recall.click() ;
									List<String>county=new ArrayList<String>();
									county.add("附近");
									county.add("1km");
									county.add("3km");
									county.add("5km");
									county.add("10km");
									county.add("全城");
									for(int i=0;i<county.size();i++){
										CountyBean bean=new CountyBean();
										bean.setCounty(county.get(i));
										lists.add(bean);	
									}
									CircleAdapter adapter=new CircleAdapter(mcontext, lists);
									listt.setAdapter(adapter);
									listt.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub
											try {
												JSONObject jsonObject=new JSONObject();
												jsonObject.put("country", lists.get(position).getCounty());
												mhandler.sendMessage(Message.obtain(mhandler, 88, jsonObject));
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
										}
									});
									//popupWindow.dismiss();
								} else {
									PostForCircle circle = new PostForCircle(
											mcontext, listData.get(position)
													.getCountyid().toString(),
											listt, popupWindow, mhandler);
								}
							}
						});
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

	// 设置一个回调接口
	public interface ClickPositonZero {

		public void click();
	}
	//private 
}
