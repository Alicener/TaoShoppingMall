package com.sun.shoppingmall.customview;

import com.sun.shoppingmall.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Title extends LinearLayout{

	public Title(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this);
		ImageView ivback = (ImageView) findViewById(R.id.iv_back);
		ivback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Activity) getContext()).finish();
			}
		});
	}

}
