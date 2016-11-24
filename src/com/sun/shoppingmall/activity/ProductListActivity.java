package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.entity.CouponBean;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.PostForClassProduct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductListActivity extends Activity implements OnClickListener{
	private ImageView backimage,bianhua;
	private PullToRefreshGridView gridview;
	private PullToRefreshListView listview;
	private String c3;
	private List<CouponBean>list=new ArrayList<CouponBean>();
	private ListviewAdapter adapter;
	private GridviewAdapter adapters;
	private boolean isfrist=true;
	private PostForClassProduct classProduct;
	private LinearLayout all,price,xiaoliang,choice;
	private TextView zhonghe,xiaoliangTv,choiceTv,priceTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.products);
		setViews();
	}
	private void setViews() {
		// TODO Auto-generated method stub
		backimage=(ImageView) findViewById(R.id.backImage);
		backimage.setOnClickListener(this);
		bianhua=(ImageView) findViewById(R.id.bianhua);
		bianhua.setOnClickListener(this);
		gridview=(PullToRefreshGridView) findViewById(R.id.gridview);
		listview=(PullToRefreshListView) findViewById(R.id.listview);
		Intent intent=getIntent();
		if(intent.getStringExtra("c3").equals("")){
			c3="";
		}else{
			c3=intent.getStringExtra("c3");
		}
		classProduct=new PostForClassProduct(this, "10", gridview, listview,
				mHandler, "product", "product_list", intent.getStringExtra("c1"), intent.getStringExtra("c2"), c3);
		classProduct.addMore();
		all=(LinearLayout) findViewById(R.id.allLinearLayout);
		all.setOnClickListener(this);
		xiaoliang=(LinearLayout) findViewById(R.id.allLinearLayout);
		xiaoliang.setOnClickListener(this);
		price=(LinearLayout) findViewById(R.id.allLinearLayout);
		price.setOnClickListener(this);
		choice=(LinearLayout) findViewById(R.id.allLinearLayout);
		choice.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.bianhua:
			//list.clear();
			////classProduct.removeAllChild();
			//classProduct.addMore();
			if (isfrist) {
				listview.setVisibility(View.GONE);
				gridview.setVisibility(View.VISIBLE);
				bianhua.setImageResource(R.drawable.two3x);
				if(adapter!=null){
					adapter=null;
				}
					adapters=new GridviewAdapter(ProductListActivity.this, list);
					gridview.setAdapter(adapters);
				isfrist=false;
			}else{
				listview.setVisibility(View.VISIBLE);
				gridview.setVisibility(View.GONE);
				bianhua.setImageResource(R.drawable.one3x);
				if(adapters!=null){
					adapters=null;
				}
					adapter=new ListviewAdapter(ProductListActivity.this, list);
					listview.setAdapter(adapter);
				isfrist=true;
			}
			break;
		}
	}
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 11:
				JSONArray array = (JSONArray) msg.obj;
				for (int i = 0; i < array.length(); i++) {
					CouponBean couBean = new CouponBean();
					JSONObject js = array.optJSONObject(i);
					couBean.setImg(js.optString("img"));
					couBean.setTitle(js.optString("title"));
					couBean.setId(js.optString("id"));
					couBean.setGoods_id(js.optString("goods_id"));
					couBean.setMoney(js.optString("price"));
					couBean.setShop_id(js.optString("shop_id"));
					couBean.setShop_name(js.optString("shop_name")) ;
					couBean.setCiecle_name(js.optString("ciecle_name")) ;
					list.add(couBean);
				}
				if(isfrist){
					if(adapters!=null){
						adapters=null;
					}
					if (adapter==null) {
						adapter=new ListviewAdapter(ProductListActivity.this, list);
						listview.setAdapter(adapter);
					}else{
						adapter.notifyDataSetChanged();
					}
				}else{
					if(adapter!=null){
						adapter=null;
					}
					if(adapters==null){
						adapters=new GridviewAdapter(ProductListActivity.this, list);
						gridview.setAdapter(adapters);
					}else{
						adapters.notifyDataSetChanged();
					}
				}
				
				break;

			}
		}
	};
	class ListviewAdapter extends BaseAdapter{
		private Context context;
		private List<CouponBean>lists=new ArrayList<CouponBean>();
		public ListviewAdapter(Context context,List<CouponBean>lists) {
			// TODO Auto-generated constructor stub
			this.context=context;
			this.lists=lists;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=View.inflate(context, R.layout.listview_adapter, null);
				holder.iv=(ImageView) convertView.findViewById(R.id.imageview);
				//获取屏幕�??  宽度
				//WindowManager manager=(WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
				//Display disPlay = manager.getDefaultDisplay();
				//int  wight= disPlay.getWidth();
				//int  height=wight/2-10;
				//int height=wight/3+10;
				//android.view.ViewGroup.LayoutParams lp = holder.iv.getLayoutParams();
				//lp.height=height;

				holder.shopnameTV=(TextView) convertView.findViewById(R.id.name);
				holder.priceTV=(TextView) convertView.findViewById(R.id.price);			
				//holder.distanceTV=(TextView) convertView.findViewById(R.id.juli);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			BitmapUtils bitmapUtils=new BitmapUtils(context);
			bitmapUtils.display(holder.iv, Consts.IMAGE_GOODS_LARGE+lists.get(position).getImg());
			//TApplication.bitmaputils.display(holder.iv, Consts.PRODUCT1+lists.get(position).getImg());
			holder.shopnameTV.setText(lists.get(position).getTitle());
			holder.priceTV.setText("¥"+lists.get(position).getMoney());
			//holder.pingjiaTV.setText(lists.get(position).getSale());
			//holder.distanceTV.setText("500m");
			//holder.arrrTv.setText(lists.get(position).getAttr2_name());
			//holder.addressTV.setText("�?"+lists.get(position).getCircle_name()+"�?"+lists.get(position).getAddress());
			return convertView;
		}
		class ViewHolder{
			ImageView iv;
			TextView shopnameTV;
			TextView pingjiaTV;
			TextView addressTV;
			TextView priceTV;
			TextView distanceTV;
			TextView arrrTv;
		}
	}
	class GridviewAdapter extends BaseAdapter{
		private Context context;
		private List<CouponBean>lists=new ArrayList<CouponBean>();
		public GridviewAdapter(Context context,List<CouponBean>lists) {
			// TODO Auto-generated constructor stub
			this.context=context;
			this.lists=lists;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=View.inflate(context, R.layout.gridview_adapter1, null);
				holder.iv=(ImageView) convertView.findViewById(R.id.imageview);
				//获取屏幕�??  宽度
				//WindowManager manager=(WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
				//Display disPlay = manager.getDefaultDisplay();
				//int  wight= disPlay.getWidth();
				//int  height=wight/2-10;
				//int height=wight/3+10;
				//android.view.ViewGroup.LayoutParams lp = holder.iv.getLayoutParams();
				//lp.height=height;

				holder.shopnameTV=(TextView) convertView.findViewById(R.id.name);
				holder.priceTV=(TextView) convertView.findViewById(R.id.price);			
				//holder.distanceTV=(TextView) convertView.findViewById(R.id.juli);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			BitmapUtils bitmapUtils=new BitmapUtils(context);
			bitmapUtils.display(holder.iv, Consts.IMAGE_GOODS_LARGE+lists.get(position).getImg());
			//TApplication.bitmaputils.display(holder.iv, Consts.PRODUCT1+lists.get(position).getImg());
			holder.shopnameTV.setText(lists.get(position).getTitle());
			holder.priceTV.setText("¥"+lists.get(position).getMoney());
			//holder.pingjiaTV.setText(lists.get(position).getSale());
			//holder.distanceTV.setText("500m");
			//holder.arrrTv.setText(lists.get(position).getAttr2_name());
			//holder.addressTV.setText("�?"+lists.get(position).getCircle_name()+"�?"+lists.get(position).getAddress());
			return convertView;
		}
		class ViewHolder{
			ImageView iv;
			TextView shopnameTV;
			TextView pingjiaTV;
			TextView addressTV;
			TextView priceTV;
			TextView distanceTV;
			TextView arrrTv;
		}
	}
}
