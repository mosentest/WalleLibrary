package org.wall.mo.compat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * 一些常用的view适配封装
 */
public class ViewCompat {

	/**
	 * 设置背景兼容
	 * @param view
	 * @param drawable
	 */
	@SuppressLint("NewApi")
	public void setBackground(View view, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

}
