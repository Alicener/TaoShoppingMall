package com.sun.shoppingmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.shoppingmall.R;
import com.sun.shoppingmall.activity.GuitaiActivity;
import com.sun.shoppingmall.activity.NearbyActivity;
import com.sun.shoppingmall.activity.PhotoActivity;

public class MainsecondFragment extends Fragment implements OnClickListener{
	private TextView nearby;
	private TextView guitai,talk;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view=inflater.inflate(R.layout.mainsecond_fragment, null);
	nearby=(TextView) view.findViewById(R.id.nearby);
	nearby.setOnClickListener(this);
	guitai=(TextView) view.findViewById(R.id.guitai);
	guitai.setOnClickListener(this);
	talk=(TextView) view.findViewById(R.id.talk);
	talk.setOnClickListener(this);
	return view;
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.nearby:
		Intent intent=new Intent(getActivity(),NearbyActivity.class);
		startActivity(intent);
		break;
	case R.id.guitai:
		Intent intentgui=new Intent(getActivity(),GuitaiActivity.class);
		startActivity(intentgui);
		break;
	case R.id.talk:
		Intent intenttalk=new Intent(getActivity(),PhotoActivity.class);
		startActivity(intenttalk);
		break;
	}
	
}
}
