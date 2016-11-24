package com.sun.shoppingmall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

import com.sun.shoppingmall.R;

public class SetActivity extends Activity implements OnClickListener{
	
	private RelativeLayout rela1,rela2,rela3,rela4,rela5,rela6;
	private PopupWindow popwindow,popwindowitem,popw;
	private TextView cancel,sure,updatesure;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set);
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText("设置");
		initview();
	}
	
	public void initview(){
		rela1 = (RelativeLayout) findViewById(R.id.setrela1);
		rela2 = (RelativeLayout) findViewById(R.id.setrela2);
		rela3 = (RelativeLayout) findViewById(R.id.setrela3);
		rela4 = (RelativeLayout) findViewById(R.id.setrela4);
		rela5 = (RelativeLayout) findViewById(R.id.setrela5);
		rela6 = (RelativeLayout) findViewById(R.id.setrela6);
		rela1.setOnClickListener(this);
		rela2.setOnClickListener(this);
		rela3.setOnClickListener(this);
		rela4.setOnClickListener(this);
		rela5.setOnClickListener(this);
		rela6.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.setrela1:
			
			break;
		case R.id.setrela2:
			showAllcityWindowItem(v);
			break;
		case R.id.setrela3:
			
			break;
		case R.id.setrela4:
			showAllcityWindow(v);
			break;
		case R.id.setrela5:
			Intent intent = new Intent(SetActivity.this,FeedbackActivity.class);
			startActivity(intent);
			break;
		case R.id.setrela6:
	
			break;
		case R.id.popcancel:
			popwindowitem.dismiss();
			break;
		case R.id.pop_sure:
			break;
		case R.id.update_cancel:
			popwindow.dismiss();
			break;
		case R.id.update_sure:
			break;
		case R.id.update_cancelone:
			popw.dismiss();
			break;
		default:
			break;
		}
	}
	
	/**
	 * @param v
	 * 版本更新二
	 */
	private void showAllcityWindowtwo(View v) {
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View views = layoutInflater.inflate(R.layout.pop_updateone, null);
		cancel = (TextView) views.findViewById(R.id.update_cancelone);
		cancel.setOnClickListener(this);
		popw = new PopupWindow(views, windowManager
				.getDefaultDisplay().getWidth(), 400);
		
		WindowManager.LayoutParams params=getWindow().getAttributes();  
		params.alpha=0.7f;  
		getWindow().setAttributes(params); 
		// 使其聚集
		popw.setFocusable(true);
		// 设置允许在外点击消失
		popw.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popw.setBackgroundDrawable(new BitmapDrawable());
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popw.showAtLocation(views, Gravity.NO_GRAVITY,location[0], 
				location[1]+windowManager.getDefaultDisplay().getHeight()-400);
		
		popw.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams params=getWindow().getAttributes();  
			    params.alpha=1f;  
			    getWindow().setAttributes(params); 
			}
		});
	}
	
	/**
	 * @param v
	 * 版本更新
	 */
	private void showAllcityWindow(View v) {
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View views = layoutInflater.inflate(R.layout.pop_update, null);
		updatesure = (TextView) views.findViewById(R.id.update_sure);
		cancel = (TextView) views.findViewById(R.id.update_cancel);
		cancel.setOnClickListener(this);
		updatesure.setOnClickListener(this);
		popwindow = new PopupWindow(views, windowManager
				.getDefaultDisplay().getWidth(), 400);
		
		WindowManager.LayoutParams params=getWindow().getAttributes();  
		params.alpha=0.7f;  
		getWindow().setAttributes(params); 
		// 使其聚集
		popwindow.setFocusable(true);
		// 设置允许在外点击消失
		popwindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popwindow.setBackgroundDrawable(new BitmapDrawable());
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popwindow.showAtLocation(views, Gravity.NO_GRAVITY,location[0], 
				location[1]+windowManager.getDefaultDisplay().getHeight()-400);
		
		popwindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams params=getWindow().getAttributes();  
			    params.alpha=1f;  
			    getWindow().setAttributes(params); 
			}
		});
	}
	
	/**
	 * @param v
	 * 清除缓存
	 */
	private void showAllcityWindowItem(View v) {
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View views = layoutInflater.inflate(R.layout.pop_cash, null);
		sure = (TextView) views.findViewById(R.id.pop_sure);
		cancel = (TextView) views.findViewById(R.id.popcancel);
		cancel.setOnClickListener(this);
		sure.setOnClickListener(this);
		popwindowitem = new PopupWindow(views, windowManager
				.getDefaultDisplay().getWidth(), 400);
		
		WindowManager.LayoutParams params=getWindow().getAttributes();  
		params.alpha=0.7f;  
		getWindow().setAttributes(params); 
		// 使其聚集
		popwindowitem.setFocusable(true);
		// 设置允许在外点击消失
		popwindowitem.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popwindowitem.setBackgroundDrawable(new BitmapDrawable());
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popwindowitem.showAtLocation(views, Gravity.NO_GRAVITY,location[0], 
				location[1]+windowManager.getDefaultDisplay().getHeight()-400);
		
		popwindowitem.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams params=getWindow().getAttributes();  
			    params.alpha=1f;  
			    getWindow().setAttributes(params); 
			}
		});
	}

}
