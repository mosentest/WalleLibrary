package org.wall.mo.utils.keyboard;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 作者 create by moziqi on 2018/6/27
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class KeyBoardHelper {

    private boolean isVisibleForLast;

    /**
     * 用于键盘软键盘是否弹起
     *
     * @param activity
     * @param listener
     */
    public void addOnSoftKeyBoardVisibleListener(Activity activity, final IKeyBoardVisibleListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom;
                //获得屏幕整体的高度
                int hight = decorView.getHeight();
                //获得键盘高度
                int keyboardHeight = hight - displayHight;
                boolean visible = (double) displayHight / hight < 0.8;
                if (visible != isVisibleForLast) {
                    listener.onSoftKeyBoardVisible(visible, keyboardHeight);
                }
                isVisibleForLast = visible;
            }
        });
    }

    public interface IKeyBoardVisibleListener {
        void onSoftKeyBoardVisible(boolean visible, int keyboardHeight);
    }


    /**
     * https://www.jb51.net/article/137229.htm
     *
     * @param main
     * @param scrollView1
     */
    public void addLayoutScrollListener(final View main, final View scrollView1) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();  //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);  //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scrollView1.getLocationInWindow(location);   // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + scrollView1.getHeight()) - rect.bottom;   //5､让界面整体上移键盘的高度
                    main.scrollTo(0, srollHeight);
                } else {  //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    main.scrollTo(0, 0);
                }
            }
        });
    }
}
