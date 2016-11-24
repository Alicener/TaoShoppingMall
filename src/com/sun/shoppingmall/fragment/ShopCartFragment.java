package com.sun.shoppingmall.fragment;


import com.sun.shoppingmall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShopCartFragment extends Fragment{
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view=inflater.inflate(R.layout.main_fragment, null);
	return view;
}
}
