/**
 * @(#) MyListView.java Created on 2013-3-19
 *
 * Copyright (c) 2013 Aspire. All Rights Reserved
 */
package com.sun.shoppingmall.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * The class <code>MyListView</code>
 * 
 * @author likai
 * @version 1.0
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
	 private GestureDetector mGestureDetector;   
	    View.OnTouchListener mGestureListener;   
    /**
     * Constructor
     * 
     * @param context
     */
    public MyHorizontalScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * 
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * 
     * @param context
     * @param attrs
     */
    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mGestureDetector = new GestureDetector(new YScrollDetector());   
        setFadingEdgeLength(0);   
    }

   /* @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }*/
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {   
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);   
    }   
    
    // Return false if we're scrolling in the x direction     
    class YScrollDetector extends SimpleOnGestureListener {   
        @Override  
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {   
            if(Math.abs(distanceY) > Math.abs(distanceX)) {   
                return true;   
            }   
            return false;   
        }   
    }   
}
