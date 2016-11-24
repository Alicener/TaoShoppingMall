package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.adapter.PreachAdapter;
import com.sun.shoppingmall.customview.MyListView;
import com.sun.shoppingmall.entity.PreachEntity;
import com.sun.shoppingmall.pullable.PullToRefreshLayout;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnReflushListener;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnRefreshListener;
import com.sun.shoppingmall.pullable.PullableScrollViews;
import com.sun.shoppingmall.utils.PostForPreach;

public class BrandKuaiActivity extends Activity implements OnRefreshListener{
	private PullableScrollViews scrollview;
	private PullToRefreshLayout pullToRefreshLayout;
	private MyListView listview;
	private List<PreachEntity>list=new ArrayList<PreachEntity>();
	private PreachAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.brandkuaiche);
		//id=getIntent().getStringExtra("id");
		pullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		pullToRefreshLayout.setOnRefreshListener(this);
		// rl_title = (LinearLayout)findViewById(R.id.toprela);
		scrollview = (PullableScrollViews) findViewById(R.id.listview_placemore);
		//rl_title.getBackground().setAlpha(0);
		listview=(MyListView) findViewById(R.id.listview);
		pullToRefreshLayout.setOnReflushListener(new OnReflushListener() {

			@Override
			public void isShow(boolean show) {
				// TODO Auto-generated method stub
				if(show){
					//	rl_title.setVisibility(View.GONE);
				}else{
					//adViewPager.setVisibility(View.VISIBLE);
					//	rl_title.setVisibility(View.VISIBLE);
				}
			}
		});
		PostForPreach forPreach=new PostForPreach(this, "8", listview, mhandler);
		//forPreach.setsupplierid(id);
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
							adapter=new PreachAdapter(BrandKuaiActivity.this, list);
							listview.setAdapter(adapter);
						}else{
							adapter.notifyDataSetChanged();
						}
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								Intent intent=new Intent(BrandKuaiActivity.this,ActivityProductActivity.class);
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
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}, 2000);
	}
	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}, 2000);
	}
}
