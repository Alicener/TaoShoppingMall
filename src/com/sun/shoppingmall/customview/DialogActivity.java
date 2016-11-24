package com.sun.shoppingmall.customview;

import java.util.Calendar;

import com.byl.datepicker.wheelview.OnWheelScrollListener;
import com.byl.datepicker.wheelview.WheelView;
import com.byl.datepicker.wheelview.adapter.NumericWheelAdapter;
import com.sun.shoppingmall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 窗口
 * @author 
 *
 */
public class DialogActivity extends Activity implements OnClickListener{
	private LayoutInflater inflater = null;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	
	private int mYear=1996;
	private int mMonth=0;
	private int mDay=1;
	private View view;
	private LinearLayout ll;
	private TextView qd,qx;
	private int requestCode = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);
		WindowManager.LayoutParams lp = getWindow().getAttributes();  
		lp.width = LayoutParams.FILL_PARENT;  
		lp.gravity = Gravity.BOTTOM;  
		getWindow().setAttributes(lp); 
		ll=(LinearLayout) findViewById(R.id.ll);
		qd=(TextView) findViewById(R.id.qd);
		qx=(TextView) findViewById(R.id.qx);
		qx.setOnClickListener(this);
		ll.addView(getDataPick());
		qd.setOnClickListener(this);
	}
	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);

		int curYear = mYear;
		int curMonth =mMonth+1;
		int curDate = mDay;
		inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.wheel_date_picker, null);

		year = (WheelView) view.findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1=new NumericWheelAdapter(this,1950, norYear); 
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2=new NumericWheelAdapter(this,1, 12, "%02d"); 
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear,curMonth);
		day.setCyclic(true);
		day.addScrollingListener(scrollListener);


		year.setVisibleItems(7);
		month.setVisibleItems(7);
		day.setVisibleItems(7);

		year.setCurrentItem(curYear - 1950);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		return view;
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {

		}
	};

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(this,1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 @Override  
     public void onBackPressed() {  
		 Intent intent=new Intent();
		 intent.putExtra("birthday", "1996-01-01");  
			// 设置结果，并进行传送  
		     DialogActivity.this.setResult(requestCode, intent);
		     DialogActivity.this.finish();
     } 

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qd:
			int n_year = year.getCurrentItem() + 1950;
			int n_month = month.getCurrentItem() + 1;
			initDay(n_year,n_month);
			String birthday=new StringBuilder().append((year.getCurrentItem()+1950)).append("-").append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1)).append("-").append(((day.getCurrentItem()+1) < 10) ? "0" + (day.getCurrentItem()+1) : (day.getCurrentItem()+1)).toString();
			Intent intent=new Intent();
			
		     intent.putExtra("birthday", birthday);  
			// 设置结果，并进行传送  
		     DialogActivity.this.setResult(requestCode, intent);
		     DialogActivity.this.finish();
			
//			intent.setClass(this, MainActivity.class);
//			Bundle bundle=new Bundle();
//			String str1="aaaaaa";
//			bundle.putString("str1", birthday);
//			intent.putExtras(bundle);
//			startActivityForResult(intent, 0);
			
		/*	inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
			View vs = inflater.inflate(R.layout.activity_main, null);
			TextView tv1 = (TextView) vs.findViewById(R.id.tv1);
			tv1.setText(birthday);*/
			//this.finish();
			break;
		case R.id.qx:
			DialogActivity.this.finish();
			break;
		default:
			break;
		}
	}

}
