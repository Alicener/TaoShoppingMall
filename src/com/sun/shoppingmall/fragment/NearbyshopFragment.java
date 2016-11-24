package com.sun.shoppingmall.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.activity.ActivityShopActivity;
import com.sun.shoppingmall.adapter.NearbyAdapter;
import com.sun.shoppingmall.customview.MyListView;
import com.sun.shoppingmall.entity.ProduceNearbyBean;
import com.sun.shoppingmall.pullable.PullToRefreshLayout;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnReflushListener;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnRefreshListener;
import com.sun.shoppingmall.utils.PostForCounty.ClickPositonZero;
import com.sun.shoppingmall.utils.PostForRecommendShops;

public class NearbyshopFragment extends Fragment implements OnClickListener,OnRefreshListener,ClickPositonZero{
	private MyListView listview;
	private NearbyAdapter adapter;
	private List<ProduceNearbyBean>list=new ArrayList<ProduceNearbyBean>();
	private PullToRefreshLayout pullToRefreshLayout;
	private PostForRecommendShops forRecommendShops;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.nearby_fragment, null);
		listview=(MyListView) view.findViewById(R.id.listview);
		pullToRefreshLayout = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		pullToRefreshLayout.setOnRefreshListener(this);
		// rl_title = (LinearLayout) view.findViewById(R.id.toprela);
		//scrollview = (PullableScrollViews) view.findViewById(R.id.listview_placemore);
		//rl_title.getBackground().setAlpha(0);
		pullToRefreshLayout.setOnReflushListener(new OnReflushListener() {

			@Override
			public void isShow(boolean show) {
				// TODO Auto-generated method stub
				if(show){
					//rl_title.setVisibility(View.GONE);
				}else{
					//adViewPager.setVisibility(View.VISIBLE);
					//rl_title.setVisibility(View.VISIBLE);
				}
			}
		});
		forRecommendShops=new PostForRecommendShops(getActivity(), "8", "1", mhandler);
		forRecommendShops.addmoredata();
		return view;
	}
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 11:
				JSONObject object=(JSONObject) msg.obj;
				try {
					JSONArray array=object.optJSONArray("result");
					//if(object.getString("result").equals("")){
					//xianshi.setVisibility(View.VISIBLE);
					//listview.setVisibility(View.GONE);
					//}
					if(array==null){

					}else{
						for(int i=0;i<array.length();i++){
							ProduceNearbyBean entity=new ProduceNearbyBean();
							JSONObject jsonObject=array.getJSONObject(i);
							entity.setId(jsonObject.getString("id"));
							entity.setAddress(jsonObject.getString("address"));
							entity.setName(jsonObject.getString("name"));
							entity.setCircle_name(jsonObject.getString("circle_name"));
							list.add(entity);
						}
						if(adapter==null){
							adapter=new NearbyAdapter(getActivity(), list);
							listview.setAdapter(adapter);
						}else{
							adapter.notifyDataSetChanged();
						}
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								Intent intent=new Intent(getActivity(),ActivityShopActivity.class);
								intent.putExtra("id", list.get(position).getId());
								startActivity(intent);
							}
						});
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				break;
			}
		};
	};
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		forRecommendShops.addmoredata();
		/*new Handler().postDelayed(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		}
	}, 2000);*/
	}
	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		forRecommendShops.addmoredata();
		/*new Handler().postDelayed(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		}
	}, 2000);*/
	}
	@Override
	public void click() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
