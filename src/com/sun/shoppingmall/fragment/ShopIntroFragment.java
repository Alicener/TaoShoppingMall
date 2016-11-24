package com.sun.shoppingmall.fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sun.shoppingmall.R;
import com.sun.shoppingmall.utils.Consts;

public class ShopIntroFragment extends Fragment{
	private String cartOrder;
	private String cartTime;
	private String shop_id;
	private WebView webview;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view=inflater.inflate(R.layout.activity_tejia, null);
	webview=(WebView) view.findViewById(R.id.webView);
	new ShopIntroTask().execute(Consts.URL);
	return view;
}
class ShopIntroTask extends AsyncTask<String, String, String>{

	@Override
	protected String doInBackground(String... params) {
		String url=params[0];
		HttpUtils httpUtils=new HttpUtils(60000);
		try {
			RequestParams requestParams=new RequestParams();
			requestParams.addBodyParameter("c", "shop");
			requestParams.addBodyParameter("a", "shop_intro");
			JSONObject jsonObject=new JSONObject();
			shop_id=getArguments().getString("key");  
			jsonObject.put("id", shop_id);
			cartTime=new Date().getTime()+"";
			getCartInfo("shop", "shop_intro");
			requestParams.addBodyParameter("param", jsonObject.toString());
			requestParams.addBodyParameter("timesnamp", cartTime);
			requestParams.addBodyParameter("openid", "1");
			requestParams.addBodyParameter("sign", cartOrder);
			httpUtils.send(HttpMethod.POST, url, requestParams, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub  supplier_list

				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					String jsonstr=arg0.result;
					try {
						JSONObject jsonObject=new JSONObject(jsonstr);
						if(jsonObject.getString("error").equals("0")){
							//JSONObject object=jsonObject.getJSONObject("result");
							String imageurl=jsonObject.getString("intro");
							//Intent intent=new Intent(getActivity(),AdvActivity.class);
							//intent.putExtra("webview", imageurl);
							//getActivity().startActivity(intent);
					    	//webview.setInitialScale(50);					    	
							webview.loadUrl(imageurl);
							//webview.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
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
public void md5sCart(String plainText) {
	try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		cartOrder = buf.toString();
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	}
}
private void getCartInfo(String str, String strr) {

	md5sCart(strr + str + cartTime + "sunrock");
	md5sCart(cartOrder);
}
}
