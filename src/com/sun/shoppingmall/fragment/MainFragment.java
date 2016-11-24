package com.sun.shoppingmall.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.activity.BrandKuaiActivity;
import com.sun.shoppingmall.activity.LaijiusongActivity;
import com.sun.shoppingmall.activity.PhotoActivity;
import com.sun.shoppingmall.activity.PinpaiActivity;
import com.sun.shoppingmall.adapter.AdverShow;
import com.sun.shoppingmall.adapter.MyFragmentPagerAdapter;
import com.sun.shoppingmall.customview.CustomGridView;
import com.sun.shoppingmall.customview.MyListView;
import com.sun.shoppingmall.pullable.PullToRefreshLayout;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnReflushListener;
import com.sun.shoppingmall.pullable.PullToRefreshLayout.OnRefreshListener;
import com.sun.shoppingmall.pullable.PullableScrollView;
import com.sun.shoppingmall.utils.MainnActivityHelper;
import com.sun.shoppingmall.utils.MyPageChangeListener;
import com.sun.shoppingmall.utils.PostForShujia;
import com.sun.shoppingmall.utils.PostForZuixin;

public class MainFragment extends Fragment implements OnRefreshListener,OnClickListener{
	private TextView pinpai,brand;
	private MyListView listview;
	private CustomGridView gridview;
	private ViewPager adViewPager,pager;
	// 定义取出的附近商家的地址
	private ScheduledExecutorService scheduledExecutorService;
	// 异步加载图片
	// 轮播banner的数�? banner 横幅 标语-->轮播 横幅标语�? 数据
	private List<MainnActivityHelper> adList;
	private List<ImageView> imageViews=new ArrayList<ImageView>();
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;
	private List<View> dots;
	private int currentItem = 0; // 当前图片的索引号
	private ArrayList<Integer> eightPics = new ArrayList<Integer>();
	private View view;
	private ArrayList<Fragment>list=new ArrayList<Fragment>();
	private MainsecondFragment second;
	private MainThirdFragment third;
	private ImageView image_s,image;
	private LinearLayout rl_title;
	private PullableScrollView scrollview;
	private PullToRefreshLayout pullToRefreshLayout;
	private TextView city;
	private ImageView saoma,tupianqiang,laijiusong,qingcangshuai,qiangxiankan;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.main_fragmentss, null);
		adViewPager=(ViewPager) view.findViewById(R.id.vp);
		// TODO Auto-generated method stub
		InitateImageLoader();
		adList = getBannerAd();// 返回�?�? 集合 �? 图片�? url   {0:{};1{};2{}}
		addDynamicView();
		adViewPager.setAdapter(new AdverShow(getActivity(), adList, imageViews));// 设置填充ViewPager页面的�?�配�?
		// 设置�?个监听器，当ViewPager中的页面改变时调�?
		adViewPager.setOnPageChangeListener(new MyPageChangeListener(dots));
		startAd();

		intinviews();
		return view;
	}
	private void intinviews() {
		// TODO Auto-generated method stub
		tupianqiang=(ImageView) view.findViewById(R.id.tupianqiang);
		tupianqiang.setOnClickListener(this);
		laijiusong=(ImageView) view.findViewById(R.id.laijiusong);
		laijiusong.setOnClickListener(this);
		qingcangshuai=(ImageView) view.findViewById(R.id.qingcangshuai);
		qingcangshuai.setOnClickListener(this);
		qiangxiankan=(ImageView) view.findViewById(R.id.qiangxiankan);
		qiangxiankan.setOnClickListener(this);
		pinpai=(TextView) view.findViewById(R.id.pinpai);
		brand=(TextView) view.findViewById(R.id.brand);
		brand.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),BrandKuaiActivity.class);
				startActivity(intent);
			}
		});
		pinpai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),PinpaiActivity.class);
				startActivity(intent);
			}
		});

		city=(TextView) view.findViewById(R.id.city);
		saoma=(ImageView) view.findViewById(R.id.saoma);
		pullToRefreshLayout = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		pullToRefreshLayout.setOnRefreshListener(this);
		rl_title = (LinearLayout) view.findViewById(R.id.toprela);
		scrollview = (PullableScrollView) view.findViewById(R.id.listview_placemore);
		rl_title.getBackground().setAlpha(0);
		pullToRefreshLayout.setOnReflushListener(new OnReflushListener() {

			@Override
			public void isShow(boolean show) {
				// TODO Auto-generated method stub
				if(show){
					rl_title.setVisibility(View.GONE);
				}else{
					adViewPager.setVisibility(View.VISIBLE);
					rl_title.setVisibility(View.VISIBLE);
				}
			}
		});
		scrollview.setOnScrollChangedListener(new PullableScrollView.OnScrollChangedListener() {

			@Override
			public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
				// TODO Auto-generated method stub
				if(adViewPager != null && adViewPager.getHeight() > 0 ){
					//define it for scroll height
					int lHeight = adViewPager.getHeight();
					int mheight=rl_title.getHeight();
					if(t < lHeight-mheight){//=
						int progress = (int)(new Float(t)/new Float(lHeight-mheight) * 255);//255
						rl_title.getBackground().setAlpha(progress);
						city.setTextColor(Color.WHITE);
						saoma.setImageResource(R.drawable.saoma);
					}else{
						rl_title.getBackground().setAlpha(255);
						city.setTextColor(Color.BLACK);
						saoma.setImageResource(R.drawable.ic_saoma);
					}	
				}
			}
		});




		pager=(ViewPager) view.findViewById(R.id.second);
		image_s=(ImageView) view.findViewById(R.id.image_s);
		image=(ImageView) view.findViewById(R.id.image);
		if(second==null){
			second=new MainsecondFragment();
			list.add(second);
		}
		if(third==null){
			third=new MainThirdFragment();
			list.add(third);
		}
		pager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), list));
		pager.setOnPageChangeListener(new MypagerListener());
		pager.setCurrentItem(0);

		gridview=(CustomGridView) view.findViewById(R.id.shujia);
		PostForShujia forShujia=new PostForShujia(getActivity(), "3",  gridview);
		forShujia.addListData();

		/*taogridview=(CustomGridView) view.findViewById(R.id.tao);
		PostForTao postForTao=new PostForTao(getActivity(), "4", taogridview);
		postForTao.addListData();*/

		listview=(MyListView) view.findViewById(R.id.listview);
		PostForZuixin postForZuixin=new PostForZuixin(getActivity(), "8", listview);
		postForZuixin.addListData();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.qiangxiankan:
			Intent intentqinang=new Intent(getActivity(),LaijiusongActivity.class);
			startActivity(intentqinang);
			break;
		case R.id.laijiusong:
			Intent intentlai=new Intent(getActivity(),LaijiusongActivity.class);
			startActivity(intentlai);
			break;
		case R.id.qingcangshuai:
			Intent intentqing=new Intent(getActivity(),LaijiusongActivity.class);
			startActivity(intentqing);
			break;
		case R.id.tupianqiang:
			Intent intent=new Intent(getActivity(),PhotoActivity.class);
			startActivity(intent);
			break;
		}
	}

	class MypagerListener implements OnPageChangeListener{
		int current=0;
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			if(current==0){
				image_s.setImageResource(R.drawable.homenowpagepoint2x);
				//image_s.setb
				image.setImageResource(R.drawable.homepagepoint2x);
			}else{
				image_s.setImageResource(R.drawable.homepagepoint2x);
				image.setImageResource(R.drawable.homenowpagepoint2x);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			current=arg0;
		}

	}

	/**
	 * 动�?�添加图片和下面指示的圆�? 初始化图片资�?
	 */
	private void addDynamicView() {

		for (int i = 0; i < adList.size(); i++) {
			ImageView imageView = new ImageView(getActivity());
			// 异步加载图片
			mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView,
					options);
			// 按比例扩大图片的size居中 显示�? 使得图片�?(�?)等于或大于view的长（宽�?
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
			//dots.get(i).setVisibility(View.VISIBLE);
		}
	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adViewPager.setCurrentItem(currentItem);
		};
	};
	/**
	 * 当Activity显示出来后，每两秒切换一次图片显�?
	 */
	private void startAd() {
		// 这个 是持续更新的 工具
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒切换一次图片显�?
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,TimeUnit.SECONDS);

	}

	private class ScrollTask implements Runnable {
		@Override
		public void run() {
			// 锁住
			synchronized (adViewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}

	@Override
	public void onStop() {
		if (scheduledExecutorService != null) {
			scheduledExecutorService.shutdown();
			scheduledExecutorService = null;
		}
		if (mImageLoader != null) {
			mImageLoader.clearDiskCache();
			mImageLoader.clearMemoryCache();
		}
		mImageLoader = null;
		if (eightPics.size() != 0) {
			eightPics.clear();
		}
		super.onStop();
	}

	/**
	 * 广告图片的集�?
	 * 
	 * @return
	 */

	public List<MainnActivityHelper> getBannerAd() {
		adList = new ArrayList<MainnActivityHelper>();

		MainnActivityHelper mainnactivityhelper = new MainnActivityHelper();
		mainnactivityhelper
		.setImgUrl("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
		//String s=topimage.get(0);
		//Log.i("", s);
		adList.add(mainnactivityhelper);

		MainnActivityHelper adDomain4 = new MainnActivityHelper();
		adDomain4
		.setImgUrl("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
		adList.add(adDomain4);

		MainnActivityHelper adDomain5 = new MainnActivityHelper();
		adDomain5
		.setImgUrl("http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
		adList.add(adDomain5);
		return adList;
	}
	public void InitateImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this.getActivity())
		.threadPoolSize(2)
		// 线程池内加载的数�?
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
		.discCacheSize(50 * 1024 * 1024)
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		// 将保存的时�?�的URI名称用MD5 加密
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.discCacheFileCount(40)
		.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
		.imageDownloader(
				new BaseImageDownloader(this.getActivity(),
						5 * 1000, 30 * 1000)) // connectTimeout
						.writeDebugLogs() // Remove for release
						.build();// �?始构�?
		// 这个 应该是拿�? jar �? 里边的方�?
		/**
		 * public static ImageLoader getInstance() { if (instance == null) {
		 * synchronized (ImageLoader.class) { if (instance == null) { instance =
		 * new ImageLoader(); } } } return instance; }
		 */
		//得到的是�?�? imageloader  对象
		mImageLoader = ImageLoader.getInstance();

		mImageLoader.init(config);
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.icon_error)
		.showImageForEmptyUri(R.drawable.icon_empty)
		.showImageOnFail(R.drawable.icon_error).cacheInMemory(false)
		.cacheOnDisc(false).bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
	}
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}, 2000);
	}
	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}, 2000);
	}

}
