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


    private static AutoDensity instance;

    private float widthPX, heightPX;

    private float sNoncompatDensity;
    private float sNoncompatScaledDensity;
    private double targetDpi;

    private AutoDensity() {

    }


    public static AutoDensity getInstance() {
        if (instance == null) {
            synchronized (AutoDensity.class) {
                if (instance == null) {
                    instance = new AutoDensity();
                }
            }
        }
        return instance;
    }

    /**
     * 在activity的onCreate初始化
     *
     * @param activity
     * @param application
     */
    public void setCustomDensity(Activity activity, final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (Float.compare(sNoncompatDensity, 0f) == 0) {
            //原本的density
            sNoncompatDensity = appDisplayMetrics.density;
            ////原本的scaledDensity
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
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
        float wdp = (float) (widthPX / (targetDpi / 160));
        float targetDensity = appDisplayMetrics.widthPixels / wdp;
        //这样可以1:1???试试看？？？？
        //这样可以1:1???试试看？？？？
        //这样可以1:1???试试看？？？？
        //float targetDensity = appDisplayMetrics.widthPixels / widthPX;
        final int targetDensityDpi = (int) (160 * targetDensity);
        float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        appDisplayMetrics.scaledDensity = targetScaledDensity;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();

        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
    }

    /**
     * 设置相应的UI 宽高图
     *
     * @param widthPX
     * @param heightPX
     * @param size     手机尺寸 一般都是以苹果设计，4.7寸
     */
    public void setUiDesign(float widthPX, float heightPX, float size) {
        this.widthPX = widthPX;
        this.heightPX = heightPX;
        targetDpi = Math.sqrt(this.widthPX * this.widthPX + this.heightPX * this.heightPX) / size;
    }
}
