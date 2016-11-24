package com.sun.shoppingmall.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.entity.TicketEntity;
import com.sun.shoppingmall.utils.PostForTicketlist;
import com.sun.shoppingmall.utils.Time;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FulfilFragment extends Fragment implements OnClickListener{
	private View view;
	private ListView listView;
	private PostForTicketlist post;
	private String user_id;
	private SharedPreferences sp;
	private List<TicketEntity> list = new ArrayList<TicketEntity>();
	private MyAdapter adapter;
	private TextView notuse,cantuse,used;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_coupon, null);
		listView = (ListView) view.findViewById(R.id.ticket_listview);
		sp = getActivity().getSharedPreferences("user_id",Context.MODE_PRIVATE);
		user_id = sp.getString("user_id", "");
		user_id = "2";
		post = new PostForTicketlist(getActivity(), "10", listView, mhandler, "fulfil", "order_list", user_id, "1", "");
		post.addListData();
		initview();
		return view;
	}
	
	public void initview(){
		notuse = (TextView) view.findViewById(R.id.notuse);
		cantuse = (TextView) view.findViewById(R.id.cantuse);
		used = (TextView) view.findViewById(R.id.used);
		notuse.setTextColor(Color.RED);
		notuse.setOnClickListener(this);
		cantuse.setOnClickListener(this);
		used.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notuse:
			notuse.setTextColor(Color.RED);
			cantuse.setTextColor(Color.BLACK);
			used.setTextColor(Color.BLACK);
			post = new PostForTicketlist(getActivity(), "10", listView, mhandler, "fulfil", "order_list", user_id, "1", "");
			post.addListData();
			break;
		case R.id.cantuse:
			cantuse.setTextColor(Color.RED);
			notuse.setTextColor(Color.BLACK);
			used.setTextColor(Color.BLACK);
			post = new PostForTicketlist(getActivity(), "10", listView, mhandler, "fulfil", "order_list", user_id, "2", "");
			post.addListData();
			break;
		case R.id.used:
			used.setTextColor(Color.RED);
			notuse.setTextColor(Color.BLACK);
			cantuse.setTextColor(Color.BLACK);
			post = new PostForTicketlist(getActivity(), "10", listView, mhandler, "fulfil", "order_list", user_id, "3", "");
			post.addListData();
			break;

		default:
			break;
		}
		
	}
	
	@SuppressLint("HandlerLeak")
	Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			switch (msg.what) {
			case 11:
				try {
					list.clear();
					JSONArray array = (JSONArray) msg.obj;
					if (array==null) {
						listView.setVisibility(View.GONE);
					}else {
						listView.setVisibility(View.VISIBLE);
					}
					for (int i = 0; i < array.length(); i++) {
						TicketEntity entity = new TicketEntity();
						JSONObject jsonObject = array.getJSONObject(i);
						entity.setStart_time(jsonObject.optString("start_time"));
						entity.setEnd_time(jsonObject.optString("end_time"));
						entity.setShop_name(jsonObject.optString("shop_name"));
						entity.setMoney(jsonObject.optString("fulfil_money"));
						list.add(entity);
					}
					if (adapter==null) {
						adapter = new MyAdapter(getActivity(), list);
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
		private List<TicketEntity> list;
		private Time time;
		public MyAdapter(Context context,List<TicketEntity> list) {
			this.context = context;
			this.list = list;
			time = new Time();
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
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.item_ticket, null);
				holder.name = (TextView) convertView.findViewById(R.id.tv_shop_name);
				holder.ticket = (TextView) convertView.findViewById(R.id.tv_ticket_name);
				holder.money = (TextView) convertView.findViewById(R.id.ticket_money);
				holder.time = (TextView) convertView.findViewById(R.id.ticket_time);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(list.get(position).getShop_name());
			holder.money.setText(list.get(position).getMoney());
			holder.ticket.setText("优惠券");
			holder.time.setText(time.timesOne(list.get(position).getStart_time())+"到"+time.timesOne(list.get(position).getEnd_time()));
			return convertView;
		}
		
	}
	
	class ViewHolder{
		TextView name,money,time,ticket;
	}
}
