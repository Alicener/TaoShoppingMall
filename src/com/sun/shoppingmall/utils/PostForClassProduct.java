package com.sun.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.entity.ProduceNearbyBean;

@SuppressWarnings({"unused"})
public class PostForClassProduct {
	private Context mcontext ;
	private String cartTime ,cartOrder,num,page,ordertype,a ,c ,c1,c2,c3 ;
	private String county,street ;
	private JSONObject jsGoodsInfo ;
	private PullToRefreshGridView list ;
    private PullToRefreshListView refreshList ;
	private int pagee = 1 ;
	private Handler mhandler ;
	private List<ProduceNearbyBean> listPro = new  ArrayList<ProduceNearbyBean>() ;

	public PostForClassProduct(Context context ,String num,PullToRefreshGridView list ,PullToRefreshListView refreshList ,Handler mhandler,String c ,String a,String c1 ,String c2,String c3) {
	     
		this.mcontext = context ;
		this.num  = num  ;
		this.list = list ;
		this.refreshList = refreshList ;
		this.mhandler = mhandler ;
		this.c = c ;
		this.a = a ;
		this.c1 = c1 ;
		this.c2 = c2 ;
		this.c3 = c3 ;
		
	}
	public void setAttrs(String county ,String street ,String ordertype ){
		this.county = county ;
		this.street = street ;
		this.ordertype = ordertype ;
		
	}
	public void addMore(){
		
		postToCart(c,a ,num,pagee+"",ordertype,c1,c2,c3,county,street );
		pagee++ ;
	}
	
	public void removeAllChild (){
		pagee = 1 ;
	
	}

	private void goodsInfo(String str, String strr, String ordertype,String c1 ,String c2 ,String c3,String county,String street  ) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("num", str);
			jsGoodsInfo.put("page", strr) ;
			jsGoodsInfo.put("order_type", ordertype) ;
			jsGoodsInfo.put("c1", c1) ;
			jsGoodsInfo.put("c2", c2) ;
			jsGoodsInfo.put("c3", c3) ;
		    jsGoodsInfo.put("county",county) ;
		    jsGoodsInfo.put("circle", street) ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,final String num,String page ,String ordertype,String c1 ,String c2,String c3,String county ,String street ) {
		goodsInfo(num, page,ordertype,c1,c2,c3,county,street );

		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.POST_API,
				new Response.Listener<String>() {
					@SuppressLint("NewApi")
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response) ;
							JSONArray array = json.optJSONArray("result") ;
							if(array==null){
								Toast.makeText(mcontext, "您已经拉到了最底部...", Toast.LENGTH_LONG).show() ;
								refreshList.onRefreshComplete() ;
								list.onRefreshComplete() ;
								System.out.println("我是真的没有的数据...");
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
