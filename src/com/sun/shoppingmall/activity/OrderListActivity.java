package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.customview.CustomListView;
import com.sun.shoppingmall.entity.OrderDEntity;
import com.sun.shoppingmall.entity.OrderListEntity;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.DoListviewAdapter;
import com.sun.shoppingmall.utils.GetSign;
import com.sun.shoppingmall.utils.PostForOrderDetail;
import com.sun.shoppingmall.utils.PostForOrderList;

public class OrderListActivity extends Activity{
	
	private ListView listview;
	private PostForOrderList postorderlist;
	private List<OrderListEntity> list = new ArrayList<OrderListEntity>();
	private List<OrderListEntity> list1 = new ArrayList<OrderListEntity>();
	//private List<OrderDEntity> listdetail = new ArrayList<OrderDEntity>();
	private MyAdapter adapter;
	private PostForOrderDetail postd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_orderlist);
		
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText(getIntent().getStringExtra("title"));
		if(getIntent().getStringExtra("title").equals("全部订单")){
			ImageView imageView = (ImageView) findViewById(R.id.iv_img);
			imageView.setBackground(getResources().getDrawable(R.drawable.glass));
		}
		listview = (ListView) findViewById(R.id.order_listview);
		postorderlist = new PostForOrderList(this, "10", listview, mhandler,getIntent().getStringExtra("pay_state"),
				getIntent().getStringExtra("state"));
		postorderlist.addListData();
		
	}
	
	
	@SuppressLint("HandlerLeak")
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 11:
				JSONArray array=(JSONArray) msg.obj;
				try {
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						OrderListEntity entity = new OrderListEntity();
						entity.setId(object.optString("id"));
						entity.setPay_type(object.optString("pay_type"));
						entity.setShop_id(object.optString("shop_id"));
						entity.setState(object.optString("state"));
						entity.setTotal_price(object.optString("total_price"));
						entity.setTotal_num(object.optString("total_num"));
						entity.setShop_name(object.optString("shop_name"));
						entity.setState_name(object.optString("state_name"));
						Log.d("idssss",object.optString("id") );
						new orderDtask(object.optString("id"),list,entity).execute(Consts.URL);
						list1.add(entity);
					}
					
//					for (int j = 0; j < list.size(); j++) {
//						String s =  list.get(j).getId();
//						new orderDtask(s).execute(Consts.URL);
//						Log.d("idddddd", s);
//					}
//					adapter = new MyAdapter(OrderListActivity.this, list);
//					listview.setAdapter(adapter);
						
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};
	
	class orderDtask extends AsyncTask<String, String, String >{
		private String id;
		private List<OrderListEntity> list;
		private OrderListEntity entity;
		public orderDtask(String id,List<OrderListEntity> list,OrderListEntity entity) {
			this.id = id;
			this.entity = entity;
			this.list = list;
		}
		
		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "order");
				requestParams.addBodyParameter("a", "order_d_list");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("order_id", id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("order", "order_d_list", cartTime));
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							int i = 0;
							JSONObject object=new JSONObject(response);
							if(object.getString("error").equals("0")){
								OrderListEntity entity1 = new OrderListEntity();
								List<OrderDEntity> listdetail = new ArrayList<OrderDEntity>();
								while (object.opt(i+"")!=null) {
									String s = i+"";
									JSONObject json = object.getJSONObject(s);
									OrderDEntity entity = new OrderDEntity();
									String titleString = json.getString("title");
									entity.setTitle(titleString);
									entity.setId(json.optString("id"));
									entity.setProduct_attr(json.optString("product_attr"));
									entity.setNumber(json.optString("number"));
									entity.setPrice(json.optString("price"));
									listdetail.add(entity);
									//Toast.makeText(OrderListActivity.this, object.getJSONObject(s).getString("title"), 1).show();;
									i++;
								}
								entity.setListdetail(listdetail);
								list.add( entity);
								int ttt = list1.size();
								if (list.size()==ttt) {
									dosometing(list);
								}
								
							}else{
							}
						} catch (Exception e) {
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
		
		
	}
	
	public void dosometing(final List<OrderListEntity> listss){
		int i = listss.size();
        if (list.size()==i) {
        	Log.d("ddd", i+"");
        	adapter = new MyAdapter(OrderListActivity.this, list,listss);
        	listview.setAdapter(adapter);
        }
	}
	
//	class sedDtask extends AsyncTask<String, String, List<OrderListEntity>>{
//		private List<OrderListEntity> list22;
//		public sedDtask(List<OrderListEntity> list22) {
//			this.list22 = list22;
//		}
//		
//		@Override
//		protected List<OrderListEntity> doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			return list22;
//		}
//		
//		@Override
//	    protected void onPostExecute(List<OrderListEntity> list1) {
//	        // TODO Auto-generated method stub
//	        super.onPostExecute(list1);
//	        //发送结果
//	        int i = list1.size();
//	        if (list.size()==i) {
//	        	Log.d("ddd", i+"");
////	        	adapter = new MyAdapter(OrderListActivity.this, list);
////				listview.setAdapter(adapter);
//			}
//	    }
//		
//	}
	
	class MyAdapter extends BaseAdapter{
		
		private Context context;
		private List<OrderListEntity> list;
		private List<OrderListEntity> listss;
		public MyAdapter(Context context,List<OrderListEntity> list,List<OrderListEntity> listss) {
			this.context = context;
			this.list = list;
			this.listss = listss;
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
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.item_orderlist, null);
				holder.title = (TextView) convertView.findViewById(R.id.order_l_name);
				holder.num = (TextView) convertView.findViewById(R.id.order_total);
				holder.redtv = (TextView) convertView.findViewById(R.id.order_l_red);
				holder.graytv = (TextView) convertView.findViewById(R.id.order_l_black);
				holder.state = (TextView) convertView.findViewById(R.id.order_l_state);
				holder.itemlistview = (CustomListView) convertView.findViewById(R.id.orderitem_listview);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			String s = list1.size()+"";
			Log.d("sss", s);
			if (list.get(position).getPay_type().equals("2")) {
				holder .state.setText("待付款");
				holder.redtv.setText("去付款");
				holder.graytv.setVisibility(View.GONE);
			}else {
				if (list.get(position).getState().equals("1")) {
					holder.redtv.setText("确认提货");
					holder.graytv.setVisibility(View.GONE);
				}else{
					holder.redtv.setText("再次购买");
					holder.graytv.setVisibility(View.GONE);
				}
				holder.state.setText(list.get(position).getState_name());
			}
			holder.title.setText(list.get(position).getShop_name());
			holder.num.setText("共"+list.get(position).getTotal_num()+"件商品 实付款：¥ "+list.get(position).getTotal_price());
			MyAdapterone adapterone = new MyAdapterone(OrderListActivity.this, listss.get(position).getListdetail());
			holder.itemlistview.setAdapter(adapterone);
			holder.itemlistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(OrderListActivity.this,OrderDetailActivity.class);
					intent.putExtra("id", list.get(position).getId());
					startActivity(intent);
					
				}
			});
			return convertView;
		}
		
	}
	
	class ViewHolder{
		TextView title,num,redtv,graytv,state,d_title,attr,d_num,d_price;
		ImageView img;
		CustomListView itemlistview;
	}
	
class MyAdapterone extends BaseAdapter{
		
		private Context context;
		private List<OrderDEntity> list;
		
		public MyAdapterone(Context context,List<OrderDEntity> list) {
			this.context = context;
			this.list = list;
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
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.item_order, null);
				holder.d_title = (TextView) convertView.findViewById(R.id.order_d_title);
				holder.attr = (TextView) convertView.findViewById(R.id.order_d_attr);
				holder.d_num = (TextView)convertView.findViewById(R.id.order_d_num);
				holder.d_price = (TextView)convertView.findViewById(R.id.order_d_price);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.d_title.setText(list.get(position).getTitle());
			holder.attr.setText(list.get(position).getProduct_attr());
			holder.d_num.setText("x"+list.get(position).getNumber());
			holder.d_price.setText("¥"+list.get(position).getPrice());
			return convertView;
		}
		
	}

	
}
