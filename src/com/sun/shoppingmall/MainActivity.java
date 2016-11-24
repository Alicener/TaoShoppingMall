package com.sun.shoppingmall;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.sun.shoppingmall.activity.LoginActivity;
import com.sun.shoppingmall.activity.RegisterActivity;
import com.sun.shoppingmall.fragment.CartFragment;
import com.sun.shoppingmall.fragment.CateFragment;
import com.sun.shoppingmall.fragment.Classes;
import com.sun.shoppingmall.fragment.MainFragment;
import com.sun.shoppingmall.fragment.MessageFragment;
import com.sun.shoppingmall.fragment.MineFragment;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private Button main,cate,mine,cart,message;
	private MainFragment mainfragment;
	private MessageFragment messagefragment;
	private MineFragment minefragment;
	private Classes catefragment;
	private CartFragment cartfragment;
	private SharedPreferences sp;
	private String userid;
	private List<Button>list=new ArrayList<Button>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		sp = getSharedPreferences("user_id", MODE_PRIVATE);
		userid = sp.getString("user_id", "");
		intviews();
	}
	private void intviews() {
		// TODO Auto-generated method stub
		//sp=getSharedPreferences("user_id", Context.MODE_PRIVATE);
		main=(Button) findViewById(R.id.mainbutton);
		main.setOnClickListener(this);
		cate=(Button) findViewById(R.id.catebutton);
		cate.setOnClickListener(this);
		mine=(Button) findViewById(R.id.minebutton);
		mine.setOnClickListener(this);
		cart=(Button) findViewById(R.id.cartbutton);
		cart.setOnClickListener(this);
		message=(Button) findViewById(R.id.messagebutton);
		message.setOnClickListener(this);
		//layout=(LinearLayout) findViewById(R.id.layout);
		list.add(main);
		list.add(cate);
		list.add(message);
		list.add(cart);
		list.add(mine);
		 android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		/*if(mainfragment==null){
			mainfragment=new MainFragment();
		}
		ft.replace(R.id.content, mainfragment);
		main.setSelected(true);
		textColor(0);
		ft.commit();*/
		 if (mainfragment == null) {
				mainfragment = new MainFragment();
			// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
			addFragment(mainfragment);
			showFragment(mainfragment);
		} else {
			if (mainfragment.isHidden()) {
				showFragment(mainfragment);
			}
		}
			textColor(0);
	}
	public void textColor(int position){

		for(int i=0;i<list.size();i++){
			if(i==position){
				list.get(position).setTextColor(getResources().getColor(R.color.top_background));
				list.get(position).setSelected(true);
			}else{
				list.get(i).setTextColor(getResources().getColor(R.color.bg_Black));
				list.get(i).setSelected(false);
			}

		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (v.getId()) {
		case R.id.mainbutton:
			if (mainfragment == null) {
				mainfragment = new MainFragment();
			// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
			addFragment(mainfragment);
			showFragment(mainfragment);
		} else {
			if (mainfragment.isHidden()) {
				showFragment(mainfragment);
			}
		}
			/*if(mainfragment==null){
				mainfragment=new MainFragment();
			}
			ft.replace(R.id.content, mainfragment);*/
			textColor(0);
			break;
		case R.id.catebutton:
			if (catefragment == null) {
				catefragment = new Classes();
			// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
			addFragment(catefragment);
			showFragment(catefragment);
		} else {
			if (catefragment.isHidden()) {
				showFragment(catefragment);
			}
		}
			/*if(catefragment==null){
				catefragment=new CateFragment();
			}
			ft.replace(R.id.content, catefragment);*/
			textColor(1);
			break;
		case R.id.minebutton:
			userid = sp.getString("user_id", "");
			if (userid.equals("")) {
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
			}else {
				if (minefragment == null) {
					minefragment = new MineFragment();
				// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
				addFragment(minefragment);
				showFragment(minefragment);
			} else {
				if (minefragment.isHidden()) {
					showFragment(minefragment);
				}
			}
				//if(sp.getString("user_id", "").equals("")){
					//Intent intent=new Intent(this,LoginActivity.class);
					//startActivity(intent);
				//}else{
				/*if(minefragment==null){
					minefragment=new MineFragment();
				}
				ft.replace(R.id.content, minefragment);
					
				//}*/
				  textColor(4);
			}
			break;
		case R.id.cartbutton:
			if (cartfragment == null) {
				cartfragment = new CartFragment();
			// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
			addFragment(cartfragment);
			showFragment(cartfragment);
		} else {
			if (cartfragment.isHidden()) {
				showFragment(cartfragment);
			}
		}
			/*if(cartfragment==null){
				cartfragment=new CartFragment();
			}
			ft.replace(R.id.content, cartfragment);*/
			textColor(3);
			break;
		case R.id.messagebutton:
			if (messagefragment == null) {
				messagefragment = new MessageFragment();
			// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
			addFragment(messagefragment);
			showFragment(messagefragment);
		} else {
			if (messagefragment.isHidden()) {
				showFragment(messagefragment);
			}
		}
			/*if(messagefragment==null){
				messagefragment=new MessageFragment();
			}
			ft.replace(R.id.content, messagefragment);*/
			textColor(2);
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
		// ����Fragment���л�����
		//ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

		// �ж�ҳ���Ƿ��Ѿ�����������Ѿ���������ô�����ص�
		if (mainfragment != null) {
			ft.hide(mainfragment);
		}
		if (catefragment != null) {
			ft.hide(catefragment);
		}
		if (minefragment != null){
			ft.hide(minefragment);
		}
		if (cartfragment != null){
			ft.hide(cartfragment);
		}
		if (messagefragment != null){
			ft.hide(messagefragment);
		}
		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}
}
