package com.sun.shoppingmall.activity;

import com.sun.shoppingmall.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ChangNameActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.changeusername);
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText("修改用户名");
	}
}
