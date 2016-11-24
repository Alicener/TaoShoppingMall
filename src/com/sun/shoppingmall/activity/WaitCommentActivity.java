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
import android.widget.ListView;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.entity.EvaluateEntity;
import com.sun.shoppingmall.utils.PostForEvaluate;

public class WaitCommentActivity extends Activity implements OnClickListener{
	
	private PostForEvaluate postForEvaluate;
	private ListView listView;
	private List<EvaluateEntity> list = new ArrayList<EvaluateEntity>();
	private MyAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
		listView = (ListView) findViewById(R.id.comment_listview);
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText("评价晒单");
		postForEvaluate = new PostForEvaluate(this, "8", listView, mhandler, "31");
		postForEvaluate.addListData();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			switch (msg.what) {
			case 11:
				JSONArray array=(JSONArray) msg.obj;
				try {
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						EvaluateEntity entity = new EvaluateEntity();
						entity.setId(object.optString("id"));
						entity.setImg(object.optString("img"));
						entity.setTitle(object.optString("title"));
						list.add(entity);
					}
					adapter = new MyAdapter(WaitCommentActivity.this, list);
					listView.setAdapter(adapter);
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
		private List<EvaluateEntity> list;
		private Context context;
		public MyAdapter(Context context,List<EvaluateEntity> list) {
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
				convertView = View.inflate(context, R.layout.item_comment, null);
				holder.img = (ImageView) convertView.findViewById(R.id.commentimg);
				holder.title = (TextView) convertView.findViewById(R.id.commenttitle);
				holder.btn = (TextView) convertView.findViewById(R.id.commentbtn);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(list.get(position).getTitle());
			return convertView;
		}
		
	}
	
	class ViewHolder{
		ImageView img;
		TextView title,btn;
	}
}
