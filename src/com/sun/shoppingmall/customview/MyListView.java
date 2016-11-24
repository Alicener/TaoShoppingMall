/**
 * @(#) MyListView.java Created on 2013-3-19
 *
 * Copyright (c) 2013 Aspire. All Rights Reserved
 */
package com.sun.shoppingmall.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;

/**
 * The class <code>MyListView</code>
 * 
 * @author likai
 * @version 1.0
 */
public class MyListView extends ListView {

    /**
     * Constructor
     * 
     * @param context
     */
    public MyListView(Context context) {
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
    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * 
     * @param context
     * @param attrs
     */
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
