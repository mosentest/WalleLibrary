package org.wall.mo.utils.autolayout;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by ziqi-mo on 2017/4/2 0002.
 * 实现代码动态适配，用起来麻烦，但是可以减少dimen.xml文件
 */

public class AutoLayoutHelper {
    public final static String TAG = "AutoLayoutHelper";

    private static AutoLayoutHelper instance;

    private Context context;

    private int currentWidth;

    private int currentHeight;

    private int defaultWidth;

    private int defaultHeight;

    private int mOrientation;

    private AutoLayoutHelper(Context context) {
        this.context = context;
        int[] screenSize = ScreenUtils.getScreenSize(this.context, false);
        currentWidth = screenSize[0];
        currentHeight = screenSize[1];
    }

    public static AutoLayoutHelper getInstance(Context context) {
        if (context == null) {
            throw new RuntimeException("AutoLayoutHelper mContext null");
        }
        //不适合，如果考虑横竖，这种方案不好
        if (instance == null) {
            synchronized (AutoLayoutHelper.class) {
                if (instance == null) {
                    Context temp = context.getApplicationContext();
                    instance = new AutoLayoutHelper(temp);
                }
            }
        }
        return instance;
        //直接创建对象吧
        //return new AutoLayoutHelper(mContext.getApplicationContext());
    }

    /**
     * 美术的设计图 比如 720*1280
     *
     * @param widthPX
     * @param heightPX
     */
    public void initDefaultUISize(int widthPX, int heightPX) {
        defaultWidth = widthPX;
        defaultHeight = heightPX;
    }

    public int getTargetPX(int UIpx) {
        return UIpx * Math.min(currentWidth, currentHeight) / Math.min(defaultWidth, defaultHeight);
    }

    /**
     * 设计图标记的宽
     *
     * @param widthUIpx
     * @return
     */
    private int getTargetWidthPX(int widthUIpx) {
        return widthUIpx * currentWidth / defaultWidth;
    }

    /**
     * 设计图标记的高
     *
     * @param heightUIpx
     * @return
     */
    private int getTargetHeightPX(int heightUIpx) {
        return heightUIpx * currentHeight / defaultHeight;
    }

    public void updateOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public LinearLayout.LayoutParams getLinearLayoutLayoutParams(int widthUIpx, int heightUIpx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getTargetWidthPX(widthUIpx), getTargetHeightPX(heightUIpx));
        return layoutParams;
    }

    public RelativeLayout.LayoutParams getRelativeLayoutLayoutParams(int widthUIpx, int heightUIpx) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getTargetWidthPX(widthUIpx), getTargetHeightPX(heightUIpx));
        return layoutParams;
    }

    public FrameLayout.LayoutParams getFrameLayoutLayoutParams(int widthUIpx, int heightUIpx) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getTargetWidthPX(widthUIpx), getTargetHeightPX(heightUIpx));
        return layoutParams;
    }

    public AbsListView.LayoutParams getAbsListViewLayoutParams(int widthUIpx, int heightUIpx) {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(getTargetWidthPX(widthUIpx), getTargetHeightPX(heightUIpx));
        return layoutParams;
    }

    public ViewGroup.LayoutParams getViewGroupLayoutParams(int widthUIpx, int heightUIpx) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getTargetWidthPX(widthUIpx), getTargetHeightPX(heightUIpx));
        return layoutParams;
    }
}
