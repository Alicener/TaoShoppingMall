package com.sun.shoppingmall.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.activity.ProductListActivity;
import com.sun.shoppingmall.entity.ClassBean1;
import com.sun.shoppingmall.entity.ClassBean2;
import com.sun.shoppingmall.entity.ClassBean3;
import com.sun.shoppingmall.utils.PostForClass1;
import com.sun.shoppingmall.utils.PostForClass2;
import com.sun.shoppingmall.utils.PostForClass3;
import com.sun.shoppingmall.utils.ToastUtil;
/**
 * 有一个问题 两个  listview 的数据是怎么联系 起来的
 * @author sht
 *
 */
@SuppressLint("NewApi")
// 继承的是 fragment
public class Classes extends Fragment implements OnClickListener {
	/**
	 *  Listview 这个是 左边的 listview smalllistView ： 这个是右边的 listview
	 */
	private ListView listView, smallListview;
	// 这个 是 左边的 listview 的 数据源 ClassBean1 封装着 要显示的 内容   汉字
	private List<ClassBean1> list1;
	private List<ClassBean2> list2;
	// private List<ClassBean3> list3 ;
	private int clever = 0;
	private int counts = 0;
	private String class1id;
	private int positioned = 0;
	private ImageAdapter adapter;
	private EditText class_search;
	private Dialog dialog;
	private OnClickChange onClickChange;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_class, container, false);
		list2 = new ArrayList<ClassBean2>();
		ToastUtil.showDialogg(getActivity());
		PostForClass1 pos = new PostForClass1(this.getActivity(), mhandler);
		initeView(v);
		return v;
	}

	/**
	 * 初始化 控件
	 */
	private void initeView(View v) {
		// 悬浮管理者   这个  可以获取屏幕的 宽度
		WindowManager windowManager = (WindowManager) getActivity()
				.getSystemService(Service.WINDOW_SERVICE);
		//这个是输入框的初始化
		class_search = (EditText) v.findViewById(R.id.class_search);
		//给  输入框 设置监听器
		class_search.setOnClickListener(this);
		// 窗口 的属性
		LinearLayout.LayoutParams layoutParamss = (LayoutParams) class_search
				.getLayoutParams();
		// 设置 窗口的 宽度
		layoutParamss.width = windowManager.getDefaultDisplay().getHeight() / 3 * 2;
		
		
		// 左边的 listview 初始化
		listView = (ListView) v.findViewById(R.id.goodsClass);
		// 左边的 listview 加 监听  
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//当点击 这个  item   请求的 是 加载 之后  所获得的  右边 listview 的数据
				PostForClass2 post2 = new PostForClass2(Classes.this
						.getActivity(), mhandler, list1.get(position).getId());
				// 显示 弹窗
				//showDialogg();

				clever = 0;
				counts = 0;
				if (list2 != null) {
					list2.clear();
				}
				// if(list3!= null){
				// list3.clear() ;
				// }
				class1id = list1.get(position).getId();
				positioned = position;
				// 通知 adapter 去更新
				adapter.notifyDataSetChanged();
			}
		});
		//右边的  listview  初始化		 
		smallListview = (ListView) v.findViewById(R.id.smallgoodsClass);
	}

	@SuppressLint("HandlerLeak")
	// 那边传过来的 消息 通过 handler 来处理
	Handler mhandler = new Handler() {
		@Override
		// 分别 拿到 解析的 json 对象 然后放到集合当中去
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 11:
				list1 = new ArrayList<ClassBean1>();
				JSONObject jsClass1 = (JSONObject) msg.obj;
				for (int i = 0; i < jsClass1.length() - 2; i++) {
					ClassBean1 class1 = new ClassBean1();
					JSONObject json = jsClass1.optJSONObject(i + "");
					class1.setId(json.optString("id"));
					class1.setTitle(json.optString("title").replaceAll("\\s", ""));
					class1.setSort(json.optString("sort"));
					list1.add(class1);
				}
				//设置显示
				adapter = new ImageAdapter();
				//左边的 适配器
				listView.setAdapter(adapter);
				//然后  弹出来 小的 dailog  动画
				//showDialogg();
				PostForClass2 post2 = new PostForClass2(
						Classes.this.getActivity(), mhandler, list1.get(0)
								.getId());
				clever = 0;
				counts = 0;
				class1id = list1.get(0).getId();

				break;
			case 12:
				JSONObject jsClass2 = (JSONObject) msg.obj;
				clever = jsClass2.length() - 2;
				for (int i = 0; i < jsClass2.length() - 2; i++) {						
						
						JSONObject jss = jsClass2.optJSONObject(i + "");
						ClassBean2 bean2 = new ClassBean2();
						bean2.setId(jss.optString("id"));
						bean2.setTitle(jss.optString("title"));
						list2.add(bean2);
						PostForClass3 post3 = new PostForClass3(
								Classes.this.getActivity(), mhandler,
								jss.optString("id"), bean2);
					
				}
				break;
			case 13:
				counts++;
				if (counts == clever) {
					//右边的  listview   设置 显示
					smallListview.setAdapter(new ImageAdapterr());
					//dialog  消失
					//dialog.dismiss();
				}
				ToastUtil.DismissDialogg();
				break;

			}
		}

	};

	// 定义一个listview的 适配器  左边的

	public class ImageAdapter extends BaseAdapter {

		private class ViewHolder {

			public TextView category1;
		}

		@Override
		public int getCount() {
			return list1.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.classitem, parent, false);
				holder.category1 = (TextView) view.findViewById(R.id.category1);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			if (positioned == position) {
				/*if(position>=5){
					position=position+1;
				}*/
				holder.category1.setText(list1.get(position).getTitle());
				holder.category1.setTextColor(Color.RED);
				holder.category1.setBackgroundColor(getResources().getColor(R.color.white));
			} else {
				/*if(position>=5){
					position=position+1;
				}*/
				holder.category1.setText(list1.get(position).getTitle());
				holder.category1.setTextColor(Color.BLACK);
				holder.category1.setBackgroundColor(getResources().getColor(R.color.background));
			}
			return view;
		}
	}

	// 配置第二个list的适配器
	// 定义一个listview的 适配器

	public class ImageAdapterr extends BaseAdapter {
		private class ViewHolder {
			public TextView iv;
			public TextView more;
			public GridView gv;
		}

		@Override
		public int getCount() {
			return list2.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.list_content2, parent, false);
				holder.iv = (TextView) view.findViewById(R.id.class22);
				holder.more = (TextView) view.findViewById(R.id.more);
				holder.gv = (GridView) view.findViewById(R.id.gridvieww);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.iv.setText(list2.get(position).getTitle());
			List<ClassBean3> list3 = list2.get(position).getItems();
			holder.gv.setAdapter(new ImageAdapterrr(position, list3));
			holder.more.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(getActivity(),ProductListActivity.class);
					intent.putExtra("c1", class1id);
					intent.putExtra("c3", "");
					intent.putExtra("c2", list2.get(position).getId());
					startActivity(intent);
				}
			});
			return view;
		}
	}

	// 定义第三个listview的 适配器

	public class ImageAdapterrr extends BaseAdapter {

		private int position1;
		private List<ClassBean3> list4;

		private class ViewHolder {
			public TextView iv;
		}

		public ImageAdapterrr(int position, List<ClassBean3> list) {
			this.position1 = position;
			this.list4 = list;
		}

		@Override
		public int getCount() {
			return list4.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.list_content3, parent, false);
				holder.iv = (TextView) view.findViewById(R.id.gridInfo);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			JSONObject js = new JSONObject();
			try {
				holder.iv.setText(list4.get(position).getTitle());
				js.put("class2", position1);
				js.put("class3", position);
				holder.iv.setTag(js);
				holder.iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						TextView tv = (TextView) v;
						JSONObject js = (JSONObject) tv.getTag();
						try {
							String id2 = list2.get(js.getInt("class2")).getId();
							String id3 = list2.get(js.getInt("class2"))
									.getItems().get(js.getInt("class3"))
									.getId();
							Intent intent = new Intent(getActivity(),ProductListActivity.class);
							intent.putExtra("c1", class1id);
							intent.putExtra("c2", id2 + "");
							intent.putExtra("c3", id3 + "");
							startActivity(intent);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return view;
		}
	}

	/**
	 * 显示正在加载的 动画
	 *//*
	private void showDialogg() {
		// 点一下 显示的 那个加载的 动画
		LayoutInflater inflater = LayoutInflater.from(this.getActivity());
		View v = inflater.inflate(R.layout.dialogview, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				this.getActivity(), R.anim.animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

		dialog = new Dialog(this.getActivity(), R.style.FullHeightDialog);
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}*/

	// 定义一个回调的接口

	public interface OnClickChange {
		public void onClick();
	}

	public void setOnClickChange(OnClickChange onClickChange) {
		this.onClickChange = onClickChange;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.class_search:
			onClickChange.onClick();
			break;
		}

	}

}
