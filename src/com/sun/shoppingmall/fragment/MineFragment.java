package com.sun.shoppingmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.activity.AccountActivity;
import com.sun.shoppingmall.activity.MyConcernActivity;
import com.sun.shoppingmall.activity.OrderListActivity;
import com.sun.shoppingmall.activity.SelfMessageActivity;
import com.sun.shoppingmall.activity.SetActivity;
import com.sun.shoppingmall.activity.SignActivity;
import com.sun.shoppingmall.activity.TicketActivity;
import com.sun.shoppingmall.activity.WaitCommentActivity;

public class MineFragment extends Fragment implements OnClickListener{
	private RelativeLayout orderlist,waitpay,waitreceive,evaluate,rela5,rela6,rela7,rela8,rela9,rela10;
	private View view;
	private ImageView setimg;
	private TextView accountm;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_mine, null);
	//	myinfo=(RelativeLayout) view.findViewById(R.id.jiben);
	//	myinfo.setOnClickListener(this);
		initview();
		return view;
	}
	
	public void initview(){
		accountm = (TextView) view.findViewById(R.id.accountmanage);
		setimg = (ImageView) view.findViewById(R.id.set_imgv);
		orderlist = (RelativeLayout) view.findViewById(R.id.orderlist_rela);
		waitpay = (RelativeLayout) view.findViewById(R.id.rela1);
		waitreceive = (RelativeLayout) view.findViewById(R.id.rela2);
		evaluate = (RelativeLayout) view.findViewById(R.id.rela3);
		rela5 = (RelativeLayout) view.findViewById(R.id.rela5);
		rela6 = (RelativeLayout) view.findViewById(R.id.rela6);
		rela7 = (RelativeLayout) view.findViewById(R.id.rela7);
		rela8 = (RelativeLayout) view.findViewById(R.id.rela8);
		rela9 = (RelativeLayout) view.findViewById(R.id.rela9);
		rela10 = (RelativeLayout) view.findViewById(R.id.rela10);
		rela5.setOnClickListener(this);
		rela6.setOnClickListener(this);
		rela7.setOnClickListener(this);
		rela8.setOnClickListener(this);
		rela9.setOnClickListener(this);
		rela10.setOnClickListener(this);
		waitpay.setOnClickListener(this);
		waitreceive.setOnClickListener(this);
		orderlist.setOnClickListener(this);
		evaluate.setOnClickListener(this);
		setimg.setOnClickListener(this);
		accountm.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	//	case R.id.jiben:
	//		Intent intent=new Intent(getActivity(),MyinfoActivity.class);
	//		startActivity(intent);
	//		break;
		case R.id.orderlist_rela:
			Intent intent=new Intent(getActivity(),OrderListActivity.class);
			intent.putExtra("title", "全部订单");
			intent.putExtra("pay_state", "");
			intent.putExtra("state", "");
			startActivity(intent);
			break;
		case R.id.rela1:
			Intent intent1=new Intent(getActivity(),OrderListActivity.class);
			intent1.putExtra("title", "待付款");
			intent1.putExtra("pay_state", "2");
			startActivity(intent1);
			break;
		case R.id.rela2:
			Intent intent2=new Intent(getActivity(),OrderListActivity.class);
			intent2.putExtra("title", "待付款");
			intent2.putExtra("pay_state", "");
			intent2.putExtra("state", "1");
			startActivity(intent2);
			break;
		case R.id.rela3:
			Intent intent3=new Intent(getActivity(),WaitCommentActivity.class);
			startActivity(intent3);
			break;
		case R.id.set_imgv:
			Intent intent4=new Intent(getActivity(),SetActivity.class);
			startActivity(intent4);
			break;
		case R.id.accountmanage:
			Intent intent5=new Intent(getActivity(),SelfMessageActivity.class);
			startActivity(intent5);
			break;
		case R.id.rela5:
			Intent intent6=new Intent(getActivity(),MyConcernActivity.class);
			intent6.putExtra("type", "preach");
			startActivity(intent6);
			break;
		case R.id.rela6:
			Intent intent7=new Intent(getActivity(),MyConcernActivity.class);
			intent7.putExtra("type", "shop");
			startActivity(intent7);
			break;
		case R.id.rela7:
			Intent intent8=new Intent(getActivity(),MyConcernActivity.class);
			intent8.putExtra("type", "goods");
			startActivity(intent8);
			break;
		case R.id.rela8:
			Intent intent9=new Intent(getActivity(),AccountActivity.class);
			startActivity(intent9);
			break;
		case R.id.rela9:
			Intent intent10=new Intent(getActivity(),TicketActivity.class);
			startActivity(intent10);
			break;
		case R.id.rela10:
			Intent intent11=new Intent(getActivity(),SignActivity.class);
			startActivity(intent11);
			break;
		}
	}
}
