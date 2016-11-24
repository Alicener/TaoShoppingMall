package com.sun.shoppingmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	 ArrayList<Fragment> list;  
	    public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {  
	        super(fm);  
	        this.list = list;        
	    }  
	         
	    @Override  
	    public int getCount() {  
	        return list.size();  
	    }  
	      
	    @Override  
	    public Fragment getItem(int arg0) {  
	    	//int size=list.size();
	    	//Log.i("", size+"");
	        return list.get(arg0);  
	    }  
	      
}
