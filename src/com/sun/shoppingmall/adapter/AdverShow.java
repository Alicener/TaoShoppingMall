package com.sun.shoppingmall.adapter;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sun.shoppingmall.utils.MainnActivityHelper;


public class AdverShow extends PagerAdapter{
	

		private Context mcontext ;
		private List<MainnActivityHelper> adList;
		private List<ImageView> imageViews;// 滑动的图片集�?

		public AdverShow(Context mcontext ,List<MainnActivityHelper> adList ,List<ImageView> imageViews) {
			this.mcontext = mcontext ;
			this.adList = adList ;
			this.imageViews = imageViews ;

		}

		@Override
		public int getCount() {

			return adList.size();
		}
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			ImageView iv = imageViews.get(position);
			((ViewPager) container).addView(iv);
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//Intent intent=new Intent(getActivity(),AdvActivity.class);
					//intent.putExtra("webview", webViewurl.get(position));
					//startActivity(intent);
				}
			});
			return iv;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}
		@Override
		public Parcelable saveState() {
			return null;
		}
		@Override
		public void startUpdate(View arg0) {
		}
		@Override
		public void finishUpdate(View arg0) {
		}

	
}
