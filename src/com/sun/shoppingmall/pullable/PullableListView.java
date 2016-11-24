package com.sun.shoppingmall.pullable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import android.widget.TextView;

public class PullableListView extends ListView implements Pullable
{
	//	  private float xDistance, yDistance, xLast, yLast;
	private boolean isUp = true;
	private boolean isDown = true;
	 View footView;
	 TextView moreText;
	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public PullableListView(Context context)
	{
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	

	/* @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                    xDistance = yDistance = 0f;
                    xLast = ev.getX();
                    yLast = ev.getY();
                    break;
            case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();

                    xDistance += Math.abs(curX - xLast);
                    yDistance += Math.abs(curY - yLast);
                    xLast = curX;
                    yLast = curY;
                    Log.d("TAG", xDistance+"");
                    if (xDistance >= yDistance) {
                            return false;   //表示向下传�?�事�??
                    }
            }

            return super.onInterceptTouchEvent(ev);
    }*/

	@Override
	public boolean canPullDown()
	{
		if(isDown() == false){
			return false;
		}
		if (getCount() == 0 )
		{
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0) != null && getChildAt(0).getTop() >= 0)
		{
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}


	@Override
	public boolean canPullUp()
	{
		if (getCount() == 0 || isUp() == false)
		{
			// 没有item的时候也可以上拉加载
			return false;
		} else if (getLastVisiblePosition() == (getCount() - 1))
		{
			// 滑到底部�??
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
							- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}
	
}
