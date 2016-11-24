package com.sun.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.adapter.TeSeTaoAdapter;
import com.sun.shoppingmall.customview.MyListView;
import com.sun.shoppingmall.entity.PreachEntity;
import com.sun.shoppingmall.utils.PostForPreach;

public class TesetaoActivity extends Activity{
	private PullToRefreshScrollView scrollview;
	private MyListView listview;
	private List<PreachEntity>list=new ArrayList<PreachEntity>();
	private TeSeTaoAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teshetao_activity);
		scrollview=(PullToRefreshScrollView) findViewById(R.id.scrollview);
		scrollview.setMode(Mode.PULL_FROM_END);
		listview=(MyListView) findViewById(R.id.listview);
		PostForPreach forPreach=new PostForPreach(this, "8", listview, mhandler);
		forPreach.setClassid("7");
		forPreach.addListData();
		scrollview.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// TODO Auto-generated method stub
				//adapter.notifyDataSetChanged();
				scrollview.onRefreshComplete();
			}
		});
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
						adapter=new TeSeTaoAdapter(TesetaoActivity.this, list);
						listview.setAdapter(adapter);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};
}
