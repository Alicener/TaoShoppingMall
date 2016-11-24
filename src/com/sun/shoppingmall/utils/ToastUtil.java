package com.sun.shoppingmall.utils;


import com.sun.shoppingmall.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastUtil {
	private static Toast toast;
	public static void showToast(Context context,String content){
		if(toast==null){
			toast=toast.makeText(context, content, Toast.LENGTH_SHORT);
		}else{
			toast.setText(content);
		}
		toast.show();
	}
	private static Dialog dialog;
	/**
	 * 显示正在加载的 动画
	 */
	public  static void showDialogg(Context context) {
		// 点一下 显示的 那个加载的 动画
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialogview, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

		dialog = new Dialog(context, R.style.FullHeightDialog);
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	public static void DismissDialogg(){
		dialog.dismiss();
	}
}
