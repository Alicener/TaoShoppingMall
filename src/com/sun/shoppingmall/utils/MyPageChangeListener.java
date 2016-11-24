package com.sun.shoppingmall.utils;

import java.util.List;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

@SuppressWarnings({"unused"})
public class MyPageChangeListener implements OnPageChangeListener {

	private List<View> dots; // 图片标题正文的那些点
	public MyPageChangeListener(List<View> dots) {
		this.dots = dots ;
	}

	private int oldPosition = 0;
	private int currentItem = 0 ;

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	@Override
	public void onPageSelected(int position) {
		currentItem = position;
		//dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
		//dots.get(position).setBackgroundResource(R.drawable.dot_focused);
		oldPosition = position;
	}
	
}
