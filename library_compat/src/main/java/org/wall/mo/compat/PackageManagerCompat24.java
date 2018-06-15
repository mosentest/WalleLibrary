package org.wall.mo.compat;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

/**
 * 参考链接<br/>
 * http://blog.csdn.net/cuiliya0804/article/details/72673869
 * 
 * @author moziqi
 *
 */
public class PackageManagerCompat24 {

	/**
	 * 还有一种方案，不用v4，这里就不说，正常app都用不到
	 * 
	 * @param context
	 * @param apkfile
	 */
	public static void goInstall(Context context, File apkfile, String authorityFileProvider) {
		Intent intent = new Intent();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			// 判读版本是否在7.0以上
			Uri apkUri = FileProvider.getUriForFile(context, authorityFileProvider, apkfile);
			// 添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
