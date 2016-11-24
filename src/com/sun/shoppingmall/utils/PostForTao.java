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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sun.shoppingmall.activity.LaijiusongActivity;
import com.sun.shoppingmall.activity.TesetaoActivity;
import com.sun.shoppingmall.adapter.TaoAdapter;
import com.sun.shoppingmall.customview.CustomGridView;
import com.sun.shoppingmall.entity.TaoEntity;

public class PostForTao {
	private String shop_num ;
	private CustomGridView listview;
	private Context context;
	private Handler mhandler;
	protected String cartTime;
	private JSONObject jsGoodsInfo;
	private String cartOrder;
	private int shop_page=1;
	private List<TaoEntity>list=new ArrayList<TaoEntity>();
	public PostForTao(Context context,  String shop_num,CustomGridView listview) {
		super();
		this.shop_num = shop_num;
		this.context = context;
		this.listview=listview;
		this.mhandler = mhandler;
	}
//	public void setAttrs(String order){
//		this.order=order;
//		//this.orderType=orderType;
//	}
	public void removeListData(){
		shop_page=1;
		
	}
	public void addListData(){
		//requestData("userColleList", "user", shop_num, shop_page+"");
		list.add(new TaoEntity());
		list.add(new TaoEntity());
		list.add(new TaoEntity());
		list.add(new TaoEntity());
		listview.setAdapter(new TaoAdapter(context, list));
		shop_page++;
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position==1){
					Intent intent=new Intent(context,TesetaoActivity.class);
					context.startActivity(intent);
				}else{
					Intent intent=new Intent(context,LaijiusongActivity.class);
					context.startActivity(intent);
				}
			}
		});
	}
	
	private void goodsInfo(String str, String strr ) {

		jsGoodsInfo = new JSONObject();
		try {
			//jsGoodsInfo.put("order", order) ;
			jsGoodsInfo.put("user_id", "13");
			jsGoodsInfo.put("num", str);
			jsGoodsInfo.put("page", strr);	
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
								Toast.makeText(context, "请求服务器失�??", Toast.LENGTH_LONG).show();
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
