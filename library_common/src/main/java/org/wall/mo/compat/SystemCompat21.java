package org.wall.mo.compat;

import android.os.Build;

public class SystemCompat21 {
	/**
	 * in L System.arraycopy has been overloaded several methods by primary type
	 * but be set @hide. Apks compiled in L run in KK or below will throw method
	 * not found exception because they just have System.arraycopy(Object, int,
	 * Object, int, int).
	 */
	public static void arrayCopy(Object src, int srcPos, Object dst, int dstPos, int length) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			System.arraycopy(src, srcPos, dst, dstPos, length);
		} else {
			Object srcObj = (Object) src;
			Object dstObj = (Object) dst;
			System.arraycopy(srcObj, srcPos, dstObj, dstPos, length);
		}
	}
}
