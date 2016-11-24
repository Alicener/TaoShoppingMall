package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.adapter.PreachAdapter;
import com.sun.shoppingmall.adapter.StringAdapter;
import com.sun.shoppingmall.customview.CustomGridView;
import com.sun.shoppingmall.customview.MyListView;
import com.sun.shoppingmall.entity.BrandImage;
import com.sun.shoppingmall.entity.PreachEntity;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetData;
import com.sun.shoppingmall.utils.GetSign;
import com.sun.shoppingmall.utils.PostForPreach;

public class PinpaiActivity extends Activity{
	private GridView gridview;
	///private ListView gridview;
	private CustomGridView gridviews;
	private MyListView listview;
	private List<BrandImage>brand=new ArrayList<BrandImage>();
	private List<PreachEntity>list=new ArrayList<PreachEntity>();
	private PreachAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pinpai_activity);
		gridview=(GridView) findViewById(R.id.gridview);
		gridviews=(CustomGridView) findViewById(R.id.gridviews);
		listview=(MyListView) findViewById(R.id.listview);
		gridview.setAdapter(new StringAdapter(this, GetData.getdata()));
		gridviews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PinpaiActivity.this,PinPaiFirstActivity.class);
				intent.putExtra("id", list.get(position).getId());
				startActivity(intent);
			}
		});
		new BrandTask().execute(Consts.URL);
		PostForPreach forPreach=new PostForPreach(this, "8", listview, mhandler);
		forPreach.addListData();
	}
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 11:
				JSONObject object=(JSONObject) msg.obj;
				try {
					JSONArray array=object.optJSONArray("result");
					if(array==null){

					}else{
						for(int i=0;i<array.length();i++){
							PreachEntity entity=new PreachEntity();
							JSONObject jsonObject=array.getJSONObject(i);
							entity.setId(jsonObject.getString("id"));
							entity.setTitle(jsonObject.getString("title"));
							entity.setShop_name(jsonObject.getString("shop_name"));
							entity.setJuli(jsonObject.getString("juli"));
							entity.setShop_id(jsonObject.getString("shop_id"));
							entity.setCircle_name(jsonObject.getString("circle_name"));
							list.add(entity);
						}
						if(adapter==null){
							adapter=new PreachAdapter(PinpaiActivity.this, list);
							listview.setAdapter(adapter);
						}else{
							adapter.notifyDataSetChanged();
						}
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								Intent intent=new Intent(PinpaiActivity.this,ActivityProductActivity.class);
								intent.putExtra("id", list.get(position).getId());
								startActivity(intent);
							}
						});
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				/*pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);*/
				break;
			}
		};
	};
	class BrandTask extends AsyncTask<String, String, String>{
		@Override
		protected void onPreExecute() {
			//pb=(ProgressBar) findViewById(R.id.progressBar1);
			//pb.setContentDescription("正在加载中，请稍�?");
		}
		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			HttpUtils httpUtils=new HttpUtils(60000);
			try {
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "brand");
				requestParams.addBodyParameter("a", "supplier_list");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("keyword", "");
				jsonObject.put("initial", "");
				jsonObject.put("num", "9");
				jsonObject.put("page", "1");
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("param", jsonObject.toString());
				requestParams.addBodyParameter("timesnamp", cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("brand", "supplier_list", cartTime));
				httpUtils.send(HttpMethod.POST, url, requestParams, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String jsonstr=arg0.result;
						try {
							JSONObject jsonObject=new JSONObject(jsonstr);
							if(jsonObject.getString("error").equals("0")){
								JSONArray array=jsonObject.getJSONArray("result");
								for(int i=0;i<array.length()-1;i++){
									BrandImage entity=new BrandImage();
									JSONObject object=array.getJSONObject(i);
									entity.setId(object.getString("id"));
									entity.setFirm_name(object.getString("firm_name"));
									entity.setLogo(object.getString("logo"));
									entity.setBus_img(object.getString("bus_img"));
									brand.add(entity);
								}
								BrandAdapter adapter=new BrandAdapter(PinpaiActivity.this, brand);
								gridviews.setAdapter(adapter);
								//pb.setVisibility(View.GONE);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			//pb.setVisibility(View.GONE);
		}

	}
	class BrandAdapter extends BaseAdapter{
		Context context;
		List<BrandImage>list=new ArrayList<BrandImage>();
		public BrandAdapter(Context context,List<BrandImage>list) {
			// TODO Auto-generated constructor stub
			this.context=context;
			this.list=list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=View.inflate(PinpaiActivity.this, R.layout.brandadapter, null);
				holder.iv=(ImageView) convertView.findViewById(R.id.imageView1);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			//String str=list.get(position).getLogo();
			//Log.i("", str);
			BitmapUtils bitmapUtils=new BitmapUtils(PinpaiActivity.this);
			bitmapUtils.display(holder.iv, "http://www.tjx365.com/upload/supplier/logo/"+list.get(position).getLogo());
			//imageLoader.displayImage("http://www.tjx365.com/upload/brand/middle/"+list.get(position).getBus_img(), holder.iv);
			return convertView;
		}

	}
	class ViewHolder{
		ImageView iv;
	}
}
