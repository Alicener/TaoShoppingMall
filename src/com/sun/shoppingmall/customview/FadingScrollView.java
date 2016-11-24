package com.sun.shoppingmall.customview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.sun.shoppingmall.R;

/**
 * 定义的控件布局
 * 
 * @author seven2729
 */
public class FadingScrollView extends LinearLayout {

	private LinearLayout mActionBar;

	private Drawable mBgDrawable;

	private ViewPager fadingBar;

	private int fadingHeight; // 可隐藏的控件高度
	private int oldY;
	private int fadingOffset;

	public static final int ALPHA_START = 20;
	public static final int ALPHA_END = 255;

	public FadingScrollView(Context context) {
		this(context, null);
	}

	public FadingScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public FadingScrollView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setOrientation(VERTICAL);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		fadingBar = (ViewPager) findViewById(R.id.vp);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		fadingHeight = fadingBar.getMeasuredHeight() - fadingOffset;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int scrollY = getScrollY();
			int y = (int) ev.getY();
			int deltaY = y - oldY;

			int willScrollY = scrollY - deltaY;

			if (willScrollY > fadingHeight) 
			{// 不做操作颜色保持不变
				willScrollY = fadingHeight;
			}
			else 
			{// 颜色渐变
				updateActionBarAlpha(willScrollY * (ALPHA_END - ALPHA_START)
						/ fadingHeight + ALPHA_START);
			}

			if (willScrollY < 0) {
				willScrollY = 0;
			}

			scrollTo(0, willScrollY);
			oldY = y;

			break;
		case MotionEvent.ACTION_UP:
			break;
		}

		return true;
	}

	public void bindingActionBar(LinearLayout actionBar) {
		mActionBar = actionBar;
	}

	@SuppressWarnings("deprecation")
	public void setActionBarBgDrawable(Drawable bgDrawable) {
		if (mActionBar == null) {
			try {
				throw new Exception(
						"Please try to binding the actionBar before set it's background.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mBgDrawable = bgDrawable;
		mBgDrawable.setAlpha(ALPHA_START);
		mActionBar.setBackgroundDrawable(mBgDrawable);
	}

	@SuppressWarnings("deprecation")
	public void setActionBarAlpha(int alpha) throws Exception {
		if (mActionBar == null || mBgDrawable == null) {
			throw new Exception(
					"acitonBar is not binding or bgDrawable is not set.");
		}
		mBgDrawable.setAlpha(alpha);
		mActionBar.setBackgroundDrawable(mBgDrawable);
	}

	void updateActionBarAlpha(int alpha) {
		try {
			setActionBarAlpha(alpha);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFadingOffset(int height) {
		fadingOffset = height;
	}
}
