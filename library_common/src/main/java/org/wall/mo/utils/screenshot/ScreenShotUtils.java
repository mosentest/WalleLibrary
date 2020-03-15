package org.wall.mo.utils.screenshot;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * https://www.jianshu.com/p/8475c17be72f
 */
public class ScreenShotUtils {


    private static final int SIZE = 100;//压缩的图片大小


    /**
     * 获取屏幕截图
     */
    public static Bitmap getScreenShotBmp(Activity activity) {
        View decorView = activity.getWindow().getDecorView();    //获取当前activity所在的最顶层的view--DecorView
        decorView.setDrawingCacheEnabled(true);         //启用绘图缓存
        decorView.buildDrawingCache();                  //强制构建绘图缓存（防止上面启用绘图缓存的操作失败）
        Bitmap bitmap = decorView.getDrawingCache();     //获取绘图缓存中的 bitmap
        if (bitmap == null) {
            return null;
        }
        // int statusBarHeight = getStatusBarHeight();
        int statusBarHeight = getStatusBarHeight(decorView);

        int newBmpHeight = bitmap.getHeight() - statusBarHeight;    //最终截取的图片的高度（取出状态栏之后的高度）

        bitmap = Bitmap.createBitmap(bitmap, 0, statusBarHeight, bitmap.getWidth(), newBmpHeight);

        decorView.setDrawingCacheEnabled(false);    //createBitmap完成之后一定要置为false，否则短时间内多次截图时内容不会变化！
        return bitmap;
    }

    /**
     * 获取状态栏高度方式2
     *
     * @param decorView 要获取状态栏高度的页面所在的顶层布局
     */
    public static int getStatusBarHeight(View decorView) {
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }


    public static String savePath(Context context) {
        return context.getFilesDir().getParentFile().getAbsolutePath() + File.separator + "myupfile";
    }


    /**
     * 保存截图到本地
     *
     * @param bitmap 截取到的图片
     */
    public static File saveScreenShotToSD(Context context, Bitmap bitmap) {
        if (bitmap != null) {
            File dir = new File(savePath(context));
            if (!dir.exists()) {
                dir.mkdirs();   //创建目录
            }
            File file = new File(dir, System.currentTimeMillis() + ".png");
            try {
                //对图片处理
                compressAndGenImage(bitmap, file, SIZE);
                return file;
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * https://www.jianshu.com/p/c117450031ff
     * 按质量压缩，并将图像生成指定的路径
     *
     * @param bm
     * @param outPathFile
     * @param maxSize     目标将被压缩到小于这个大小（KB）。
     * @throws IOException
     */
    public static void compressAndGenImage(Bitmap bm, File outPathFile, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        bm.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize && options > 0) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 5;
            bm.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPathFile);
        try {
            fos.write(os.toByteArray());
        } finally {
            fos.flush();
            fos.close();
        }
    }
}
