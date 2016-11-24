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
import com.sun.shoppingmall.entity.CountyBean;

@SuppressWarnings({"unused"})
public class PostForCircle {
	private Context mcontext ;
	private String cartTime ,cartOrder,circle_list  ;
	private ListView listt  ;
	private JSONObject jsGoodsInfo ;
	private List<CountyBean> listData ;
	private PopupWindow popupWindow ;
	private Handler mhandler ; 
	 

	public PostForCircle(Context context ,String circle_list ,ListView listt ,PopupWindow popupWindow,Handler mhandler ) {
	     
		this.mcontext = context ;
		this.circle_list = circle_list ;
		this.listt = listt ;
		this.popupWindow = popupWindow  ;
		this.mhandler = mhandler ;
		listData = new ArrayList<CountyBean>() ;
		postToCart("area", "circle_list",circle_list);
	}

	private void goodsInfo(String str) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("fid", str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,String city_list) {
		goodsInfo(city_list);
		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response);
							if(json.has("result")){
								
							}else{
								for(int i = 0 ; i <json.length()-2 ; i ++){
									CountyBean county = new CountyBean() ;
									JSONObject countyJS = json.optJSONObject(i+"") ;
									county.setCounty(countyJS.optString("circle")) ;
									county.setCountyid(countyJS.optString("circleid")) ;
									listData.add(county) ;
								}
								CircleAdapter adapter = new CircleAdapter(mcontext,listData) ;
								listt.setAdapter(adapter) ;
								listt.setOnItemClickListener(new OnItemClickListener(){
									@Override
									public void onItemClick(AdapterView<?> parent,
											View view, int position, long id) {
									//System.out.println(listData.get(position).getCountyid().toString()+"我是�?个好人的�?..."+circle_list) ;
									JSONObject js = new JSONObject() ;
									try {
										js.put("countyid",circle_list) ;
										js.put("circleid",listData.get(position).getCountyid().toString()) ;
										js.put("circle", listData.get(position).getCounty().toString()) ;
										mhandler.sendMessage(mhandler.obtainMessage(13,js)) ;
									} catch (JSONException e) {
										e.printStackTrace();
									}
									popupWindow.dismiss() ;
									}
								}) ;
								
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
