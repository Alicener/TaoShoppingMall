package com.sun.shoppingmall;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.lidroid.xutils.BitmapUtils;

import android.app.Application;

public class TApplication extends Application{
	
	public static String CurrentCity = null;
	public static String CurrentCityCode = null;
	/**
	 * 经度 yy
	 */
	public static String CurrentLongitude = null;
	public static String district = null;
	
	/**
	 * 纬度 xx
	 */
	public static String CurrentLatitude = null;
	public static BitmapUtils bitmaputils;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	
	@Override
	public void onCreate(){
		super.onCreate();
		readerText();
		
	}
	
	public void readerText() {
		
		mLocationClient = new LocationClient(this.getApplicationContext());
		initLocation();
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();// ��λSDK
		mLocationClient.requestLocation();

	}
	
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			CurrentLongitude = location.getLongitude() + "";
			CurrentLatitude = location.getLatitude() + "";
			CurrentCity = location.getCity();
			district = location.getDistrict();
			//String s=CurrentCity;
			// ("��γ��"+CurrentLongitude+"---"+CurrentLatitude) ;
			//CurrentCityCode = jsonS.optString(CurrentCity);
		}
	}
	
	public void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");//gcj02,gps,bd09,bd09ll
		option.setScanSpan(0);
		option.setIsNeedAddress(false);
		option.setOpenGps(true);
		option.setLocationNotify(true);
		option.setIgnoreKillProcess(false);
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
}
