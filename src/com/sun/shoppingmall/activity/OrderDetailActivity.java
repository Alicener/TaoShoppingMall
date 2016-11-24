package com.sun.shoppingmall.activity;

import java.util.Date;

import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.utils.Consts;
import com.sun.shoppingmall.utils.GetSign;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class OrderDetailActivity extends Activity{
	
	private String id;
	private TextView ordernum;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_detail);
		id = getIntent().getStringExtra("id");
		TextView tvtitle = (TextView) findViewById(R.id.tv_title);
		tvtitle.setText("订单详情");
		initview();
	}
	
	public void initview(){
		ordernum = (TextView) findViewById(R.id.order_num);
		new detailTask().execute(Consts.URL);
	}
	
	class detailTask extends AsyncTask<String , String , String >{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "order");
				requestParams.addBodyParameter("a", "order_info");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("order_id", id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				String cartTime = new Date().getTime()+"";
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", GetSign.getCartInfo("order", "order_info", cartTime));
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							int i = 0;
							JSONObject object=new JSONObject(response);
							if(object.getString("error").equals("0")){
								ordernum.setText("订单号："+object.getString("id"));
								
							}else{
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
}
