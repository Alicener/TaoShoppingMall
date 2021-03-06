package com.sun.shoppingmall.pullable;

import java.util.LinkedList;
import java.util.List;



import com.sun.shoppingmall.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class PullableScrollViews extends ScrollView implements Pullable
{
	private static final String STICKY = "sticky";
	private View mCurrentStickyView ;
	private Drawable mShadowDrawable;
	private List<View> mStickyViews;
	private int mStickyViewTopOffset;
	private int defaultShadowHeight = 10;
	private float density;
	private boolean redirectTouchToStickyView;
	
	/**
	 * �����Sticky��ʱ��ʵ��ĳЩ�����Ľ���
	 */
	private Runnable mInvalidataRunnable = new Runnable() {
		
		@Override
		public void run() {
			if(mCurrentStickyView != null){
				int left = mCurrentStickyView.getLeft();
				int top = mCurrentStickyView.getTop();
				int right = mCurrentStickyView.getRight();
				int bottom = getScrollY() + (mCurrentStickyView.getHeight() + mStickyViewTopOffset);
				
				invalidate(left, top, right, bottom);
			}
			
			postDelayed(this, 16);
			
			
		}
	};
	public PullableScrollViews(Context context)
	{
		super(context);
	}

	public PullableScrollViews(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mShadowDrawable = context.getResources().getDrawable(R.drawable.sticky_shadow_default);
		mStickyViews = new LinkedList<View>();
		density = context.getResources().getDisplayMetrics().density;
	}

	public PullableScrollViews(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	/**
	 * �ҵ�����tag��View
	 * @param viewGroup
	 */
	private void findViewByStickyTag(ViewGroup viewGroup){
		int childCount = ((ViewGroup)viewGroup).getChildCount();
		for(int i=0; i<childCount; i++){
			View child = viewGroup.getChildAt(i);
			
			if(getStringTagForView(child).contains(STICKY)){
				mStickyViews.add(child);
			}
			
			if(child instanceof ViewGroup){
				findViewByStickyTag((ViewGroup)child);
			}
		}
		
	}
	@Override
	public boolean canPullDown()
	{
		if (getScrollY() == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean canPullUp()
	{
		if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
			return true;
		else
			return false;
	}
	
	 // Edge-effects don't mix well with the translucent action bar in Android 2.X
    private boolean mDisableEdgeEffects = true;

    /**
     * @author Cyril Mottier
     */
    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
        showStickyView();
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        // http://stackoverflow.com/a/6894270/244576
        if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getTopFadingEdgeStrength();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        // http://stackoverflow.com/a/6894270/244576
        if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getBottomFadingEdgeStrength();
    }
    @Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(changed){
			findViewByStickyTag((ViewGroup)getChildAt(0));
		}
		showStickyView();
	}

	
	
	/**
	 * 
	 */
	private void showStickyView(){
		View curStickyView = null;
		View nextStickyView = null;
		
		for(View v : mStickyViews){
			int topOffset = v.getTop() - getScrollY();
			
			if(topOffset <= 0){
				if(curStickyView == null || topOffset > curStickyView.getTop() - getScrollY()){
					curStickyView = v;
				}
			}else{
				if(nextStickyView == null || topOffset < nextStickyView.getTop() - getScrollY()){
					nextStickyView = v;
				}
			}
		}
		
		if(curStickyView != null){
			mStickyViewTopOffset = nextStickyView == null ? 0 : Math.min(0, nextStickyView.getTop() - getScrollY() - curStickyView.getHeight());
			mCurrentStickyView = curStickyView;
			post(mInvalidataRunnable);
		}else{
			mCurrentStickyView = null;
			removeCallbacks(mInvalidataRunnable);
			
		}
		
	}
	
	
	private String getStringTagForView(View v){
		Object tag = v.getTag();
		return String.valueOf(tag);
	}

	
	/**
	 * ��sticky������
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if(mCurrentStickyView != null){
			//�ȱ�������
			canvas.save();
			//�����ԭ���ƶ���?(0, getScrollY() + mStickyViewTopOffset)
			canvas.translate(0, getScrollY() + mStickyViewTopOffset);
			
			if(mShadowDrawable != null){
				int left = 0;
				int top = mCurrentStickyView.getHeight() + mStickyViewTopOffset;
				int right = mCurrentStickyView.getWidth();
				int bottom = top + (int)(density * defaultShadowHeight + 0.5f);
				mShadowDrawable.setBounds(left, top, right, bottom);
				mShadowDrawable.draw(canvas);
			}
			
			
			canvas.clipRect(0, mStickyViewTopOffset, mCurrentStickyView.getWidth(), mCurrentStickyView.getHeight());
			
			mCurrentStickyView.draw(canvas);
			
			//�������ԭ�����
			canvas.restore();
		}
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			redirectTouchToStickyView = true;
		}
		
		if(redirectTouchToStickyView){
			redirectTouchToStickyView = mCurrentStickyView != null;
			
			if(redirectTouchToStickyView){
				redirectTouchToStickyView = ev.getY() <= (mCurrentStickyView
						.getHeight() + mStickyViewTopOffset)
						&& ev.getX() >= mCurrentStickyView.getLeft()
						&& ev.getX() <= mCurrentStickyView.getRight();
			}
		}
		
		if (redirectTouchToStickyView) {
			ev.offsetLocation(0, -1 * ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView.getTop()));
		}
		return super.dispatchTouchEvent(ev);
	}
	
	

	private boolean hasNotDoneActionDown = true;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(redirectTouchToStickyView){
			ev.offsetLocation(0, ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView.getTop()));
		} 
		
		if(ev.getAction()==MotionEvent.ACTION_DOWN){
			hasNotDoneActionDown = false;
		}
		
		if(hasNotDoneActionDown){
			MotionEvent down = MotionEvent.obtain(ev);
			down.setAction(MotionEvent.ACTION_DOWN);
			super.onTouchEvent(down);
			hasNotDoneActionDown = false;
		}
		
		if(ev.getAction()==MotionEvent.ACTION_UP || ev.getAction()==MotionEvent.ACTION_CANCEL){
			hasNotDoneActionDown = true;
		}
		return super.onTouchEvent(ev);
	}
}
