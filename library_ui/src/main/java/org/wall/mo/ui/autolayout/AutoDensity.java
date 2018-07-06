package org.wall.mo.ui.autolayout;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @author ziqi-mo
 * @dec 来自：https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 * 感谢今日头条提供的方案
 */
public class AutoDensity {

    private static float widthUIPX = 750;
    private static float heightUIPX = 1334;
    private static float densityUI = 2.0f;
    private static float sizeUI = 4.7f;
    private static double targetUIDpi;

    private static float sNonCompatDensity; //原始的Density
    private static float sNonCompatScaledDensity;//原始的ScaledDensity

    /**
     * 在activity的onCreate初始化
     *
     * @param activity
     * @param application
     */
    public static void setCustomDensity(Activity activity, final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (Float.compare(sNonCompatDensity, 0f) == 0) {
            targetUIDpi = Math.sqrt(widthUIPX * widthUIPX + heightUIPX * heightUIPX) / sizeUI;
            //算出的densityUI 要在写xml布局的时候去把px转dp ，比如100px / densityUI = dp
            densityUI = (float) (targetUIDpi / 160);
            //原本的density
            sNonCompatDensity = appDisplayMetrics.density;
            ////原本的scaledDensity
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        //android中的dp在渲染前会将dp转为px，计算公式：
        //px = density * dp;
        //density = dpi / 160;
        //px = dp * (dpi / 160);
        //获取宽的dp
        //float wdp = (float) (widthUIPX / (targetDpi / 160));
        float targetDensity = appDisplayMetrics.widthPixels * (densityUI / widthUIPX);
        //这样可以1:1???试试看？？？？
        //这样可以1:1???试试看？？？？
        //这样可以1:1???试试看？？？？
        //float targetDensity = appDisplayMetrics.widthPixels / widthPX;
        final int targetDensityDpi = (int) (160 * targetDensity);
        float targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        appDisplayMetrics.scaledDensity = targetScaledDensity;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();

        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
    }

    /**
     * 在application初始化
     *
     * @param protoDensity
     * @param protoWidth
     * @param protoHeight
     * @param protoSize
     */
    public static void initApplication(float protoDensity, float protoWidth, float protoHeight, float protoSize) {
        AutoDensity.densityUI = protoDensity;
        AutoDensity.widthUIPX = protoWidth;
        AutoDensity.heightUIPX = protoHeight;
        AutoDensity.sizeUI = protoSize;
    }

    public static float getsNonCompatDensity() {
        return sNonCompatDensity;
    }

    public static float getsNonCompatScaledDensity() {
        return sNonCompatScaledDensity;
    }
}
