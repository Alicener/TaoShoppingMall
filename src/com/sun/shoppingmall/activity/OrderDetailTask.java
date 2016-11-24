//package com.sun.shoppingmall.activity;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.json.JSONObject;
//
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//import com.sun.shoppingmall.entity.OrderDEntity;
//import com.sun.shoppingmall.entity.OrderListEntity;
//import com.sun.shoppingmall.utils.GetSign;
//
//import android.R.integer;
//import android.os.AsyncTask;
//import android.util.Log;
//
//public class OrderDetailTask extends AsyncTask<String, String, List<OrderListEntity>>{
//	
//	private CallBack callback;
//	private List<OrderListEntity> list1;
//	private String id;
//	private int size;
//	public OrderDetailTask(CallBack callback,String id) {
//		this.callback = callback;
//		this.id = id;
//	}
//	
//	@Override
//	protected List<OrderListEntity> doInBackground(String... params) {
//		String url=params[0];
//		try {
//			HttpUtils httpUtils=new HttpUtils();
//			RequestParams requestParams=new RequestParams();
//			requestParams.addBodyParameter("c", "order");
//			requestParams.addBodyParameter("a", "order_d_list");
//			JSONObject jsonObject=new JSONObject();
//			jsonObject.put("order_id", id);
//			requestParams.addBodyParameter("param", jsonObject.toString());
//			String cartTime = new Date().getTime()+"";
//			requestParams.addBodyParameter("timesnamp",cartTime);
//			requestParams.addBodyParameter("openid", "1");
//			requestParams.addBodyParameter("sign", GetSign.getCartInfo("order", "order_d_list", cartTime));
//			httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {
//
//				@Override
//				public void onFailure(HttpException arg0, String arg1) {
//					// TODO Auto-generated method stub
//
//				}
//				@Override
//				public void onSuccess(ResponseInfo<String> arg0) {
//					String response=arg0.result;
//					try {
//						int i = 0;
//						JSONObject object=new JSONObject(response);
//						if(object.getString("error").equals("0")){
//							OrderListEntity entity1 = new OrderListEntity();
//							List<OrderDEntity> listdetail = new ArrayList<OrderDEntity>();
//							while (object.opt(i+"")!=null) {
//								String s = i+"";
//								JSONObject json = object.getJSONObject(s);
//								OrderDEntity entity = new OrderDEntity();
//								entity.setTitle(json.getString("title"));
//								listdetail.add(entity);
//								//Toast.makeText(OrderListActivity.this, object.getJSONObject(s).getString("title"), 1).show();;
//								i++;
//							}
//							entity1.setListdetail(listdetail);
//							list1.add( entity1);
//							String s = list1.size()+"";
//							Log.d("sss", s);
//							
//						}else{
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list1;
//	}
//	
//	@Override
//    protected void onPostExecute(List<OrderListEntity> list1) {
//        // TODO Auto-generated method stub
//        super.onPostExecute(list1);
//        //发送结果
//        callback.send(list1);
//
//    }
//	
//	 public interface CallBack
//	    {
//	        public void send(List<OrderListEntity> list1);
//	    }
//	
//}
