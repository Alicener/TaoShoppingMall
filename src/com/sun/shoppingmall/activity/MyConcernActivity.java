package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.entity.ConcernEntity;
import com.sun.shoppingmall.utils.PostForFollowList;

public class MyConcernActivity extends Activity implements OnClickListener{
	
	private PostForFollowList post;
	private ListView listView;
	private List<ConcernEntity> list = new ArrayList<ConcernEntity>();
	private MyAdapter adapter;
	private String type;
	private TextView tv1,tv2,tv3;
	private LinearLayout linear;
	private ImageView ivback;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myconcern);
		listView = (ListView) findViewById(R.id.concern_listview);
		type = getIntent().getStringExtra("type");
		post = new PostForFollowList(this, "8", listView, mhandler, "2",type);
		post.addListData();
		initview();
	}
	
	public void initview(){
		ivback = (ImageView) findViewById(R.id.con_return);
		linear = (LinearLayout) findViewById(R.id.con_linear);
		tv1 = (TextView) findViewById(R.id.concern_tv1);
		tv2 = (TextView) findViewById(R.id.concern_tv2);
		tv3 = (TextView) findViewById(R.id.concern_tv3);
		if (type.equals("preach")) {
			tv1.setBackgroundColor(getResources().getColor(R.color.red));
			tv1.setTextColor(getResources().getColor(R.color.white));
		}
		else if (type.equals("shop")) {
			tv2.setBackgroundColor(getResources().getColor(R.color.red));
			tv2.setTextColor(getResources().getColor(R.color.white));
		}
		else {
			tv3.setBackgroundColor(getResources().getColor(R.color.red));
			tv3.setTextColor(getResources().getColor(R.color.white));
		}
		ivback.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.con_return:
			MyConcernActivity.this.finish();
			break;
		case R.id.concern_tv1:
			tv1.setBackgroundColor(getResources().getColor(R.color.red));
			tv1.setTextColor(getResources().getColor(R.color.white));
			tv2.setBackgroundColor(getResources().getColor(R.color.white));
			tv2.setTextColor(getResources().getColor(R.color.black));
			tv3.setBackgroundColor(getResources().getColor(R.color.white));
			tv3.setTextColor(getResources().getColor(R.color.black));
			type = "preach";
			linear.setBackground(getResources().getDrawable(R.drawable.tv_red));
			post = new PostForFollowList(this, "8", listView, mhandler, "2",type);
			post.addListData();
			break;
		case R.id.concern_tv2:
			tv2.setBackgroundColor(getResources().getColor(R.color.red));
			tv2.setTextColor(getResources().getColor(R.color.white));
			tv1.setBackgroundColor(getResources().getColor(R.color.white));
			tv1.setTextColor(getResources().getColor(R.color.black));
			tv3.setBackgroundColor(getResources().getColor(R.color.white));
			tv3.setTextColor(getResources().getColor(R.color.black));
			linear.setBackground(getResources().getDrawable(R.drawable.tv_red));
			type = "shop";
			post = new PostForFollowList(this, "8", listView, mhandler, "2",type);
			post.addListData();
			break;
		case R.id.concern_tv3:
			tv3.setBackgroundColor(getResources().getColor(R.color.red));
			tv3.setTextColor(getResources().getColor(R.color.white));
			tv2.setBackgroundColor(getResources().getColor(R.color.white));
			tv2.setTextColor(getResources().getColor(R.color.black));
			tv1.setBackgroundColor(getResources().getColor(R.color.white));
			tv1.setTextColor(getResources().getColor(R.color.black));
			linear.setBackground(getResources().getDrawable(R.drawable.tv_red));
			type = "goods";
			post = new PostForFollowList(this, "8", listView, mhandler, "2",type);
			post.addListData();
			break;

		default:
			break;
		}
		
	}
	
	Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			switch (msg.what) {
			case 11:
				try {
					list.clear();
					JSONArray array = (JSONArray) msg.obj;
					for (int i = 0; i < array.length(); i++) {
						ConcernEntity entity = new ConcernEntity();
						JSONObject jsonObject = array.getJSONObject(i);
						entity.setId(jsonObject.optString("id"));
						entity.setAddress(jsonObject.optString("address"));
						entity.setCircle(jsonObject.optString("circle"));
						entity.setImg(jsonObject.optString("circle"));
						entity.setPrice(jsonObject.optString("price"));
						entity.setShop_name(jsonObject.optString("shop_name"));
						entity.setTitle(jsonObject.optString("title"));
						list.add(entity);
					}
					
					if (adapter==null) {
						adapter = new MyAdapter(MyConcernActivity.this, list);
						listView.setAdapter(adapter);
					}else {
						adapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
	};
	
	class MyAdapter extends BaseAdapter{
		private Context context;
		private List<ConcernEntity> list;
		public MyAdapter(Context context,List<ConcernEntity> list) {
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
			ViewHolder holder =null;
			if (convertView==null) {
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.item_concern, null);
				holder.delete = (TextView) convertView.findViewById(R.id.con_delete);
				holder.positions = (TextView) convertView.findViewById(R.id.con_position);
				holder.img = (ImageView) convertView.findViewById(R.id.con_img);
				holder.price = (TextView) convertView.findViewById(R.id.con_price);
				holder.title = (TextView) convertView.findViewById(R.id.con_title);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (type.equals("preach")) {
				holder.title.setText(list.get(position).getTitle());
				holder.price.setText("¥"+list.get(position).getPrice());
			}
			else if (type.equals("shop")) {
				holder.title.setText(list.get(position).getShop_name());
				holder.price.setTextColor(getResources().getColor(R.color.gray));
				holder.price.setText("【"+list.get(position).getCircle()+"】"+list.get(position).getAddress());
			}
			else {
				holder.positions.setText("【"+list.get(position).getCircle()+"】"+list.get(position).getShop_name());
				holder.title.setText(list.get(position).getTitle());
				holder.price.setText("¥"+list.get(position).getPrice());
			}
			return convertView;
		}
		
	}
	
	class ViewHolder{
		ImageView img;
		TextView title,price,delete,positions;
	}
}
