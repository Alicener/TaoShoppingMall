package com.sun.shoppingmall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Time {
	
	/**
	 * @param 时间戳
	 * @return 2016-09-01
	 */
	@SuppressLint("SimpleDateFormat")
	public String timesOne(String time) {  
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");  
        @SuppressWarnings("unused")  
        long lcc = Long.valueOf(time);  
        int i = Integer.parseInt(time);  
        String times = sdr.format(new Date(i * 1000L));  
        return times;  
  
    }  
}
