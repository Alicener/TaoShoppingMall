package com.sun.shoppingmall.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.fragment.NearbyactivityFragment;
import com.sun.shoppingmall.fragment.NearbyshopFragment;

public class NearbyActivity extends FragmentActivity implements OnClickListener{
	private NearbyactivityFragment fragments;
	private NearbyshopFragment shopfragment;
	private TextView activity,shop;
	private ImageView backimage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.nearby_activity);
		activity=(TextView) findViewById(R.id.activity);
		activity.setOnClickListener(this);
		shop=(TextView) findViewById(R.id.shop);
		shop.setOnClickListener(this);
		backimage=(ImageView) findViewById(R.id.backImage);
		backimage.setOnClickListener(this);
		android.support.v4.app.FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
		if (fragments == null) {
			fragments = new NearbyactivityFragment();
		addFragment(fragments);
		showFragment(fragments);
	} else {
		if (fragments.isHidden()) {
			showFragment(fragments);
		}
	}
		activity.setTextColor(getResources().getColor(R.color.white));
		activity.setBackgroundColor(getResources().getColor(R.color.red));
		shop.setTextColor(getResources().getColor(R.color.bg_Black));
		shop.setBackgroundColor(getResources().getColor(R.color.white));
		bt.commit();
		/*pullToRefreshLayout.setOnReflushListener(new OnReflushListener() {

		@Override
		public void isShow(boolean show) {
			// TODO Auto-generated method stub
			if(show){
				//rl_title.setVisibility(View.GONE);
			}else{
				//adViewPager.setVisibility(View.VISIBLE); 
			//	rl_title.setVisibility(View.VISIBLE);
			}
		}
	});*/


	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.activity:
			if (fragments == null) {
				fragments = new NearbyactivityFragment();
			addFragment(fragments);
			showFragment(fragments);
		} else {
			if (fragments.isHidden()) {
				showFragment(fragments);
			}
		}
			activity.setTextColor(getResources().getColor(R.color.white));
			activity.setBackgroundColor(getResources().getColor(R.color.red));
			shop.setTextColor(getResources().getColor(R.color.bg_Black));
			shop.setBackgroundColor(getResources().getColor(R.color.white));
			break;

		case R.id.shop:
			if (shopfragment == null) {
				shopfragment = new NearbyshopFragment();
			addFragment(shopfragment);
			showFragment(shopfragment);
		} else {
			if (shopfragment.isHidden()) {
				showFragment(shopfragment);
			}
		}
			shop.setTextColor(getResources().getColor(R.color.white));
			shop.setBackgroundColor(getResources().getColor(R.color.red));
			activity.setTextColor(getResources().getColor(R.color.bg_Black));
			activity.setBackgroundColor(getResources().getColor(R.color.white));
			break;
		}
		ft.commit();
	}
	/** ���Fragment **/
	public void addFragment(Fragment fragment) {
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.content, fragment);
		ft.commit();
	}

	/** ɾ��Fragment **/
	public void removeFragment(Fragment fragment) {
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** ��ʾFragment **/
	public void showFragment(Fragment fragment) {
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		if (fragments != null) {
			ft.hide(fragments);
		}
		if (shopfragment != null) {
			ft.hide(shopfragment);
		}
		ft.show(fragment);
		ft.commitAllowingStateLoss();
	}
}
