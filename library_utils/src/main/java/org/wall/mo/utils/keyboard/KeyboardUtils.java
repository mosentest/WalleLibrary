package org.wall.mo.utils.keyboard;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class KeyboardUtils {

    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            // inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);
            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }


    /**
     * 关闭键盘
     * <p/>
     * 注意：editView控件仍然有焦点，想要失去焦点请调用 {@link #hideSoftInput(View, EditText)}
     *
     * @param activity activity
     */
    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            final View focusView = activity.getCurrentFocus();
            if (imm != null && focusView != null) {
                // flags: InputMethodManager.HIDE_NOT_ALWAYS
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 打开系统软键盘
     *
     * @param eTt 编辑控件
     */
    public static void showSoftInput(final EditText eTt) {
        if (eTt == null) {
            return;
        }

        // 让软键盘延时弹出，以更好的加载Activity
        eTt.postDelayed(() -> {
            eTt.setFocusable(true);
            eTt.setFocusableInTouchMode(true);
            eTt.requestFocus();

            InputMethodManager imm = (InputMethodManager)
                    eTt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            // isOpen若返回true，则表示输入法打开，默认是打开的....
            // boolean isOpen = imm.isActive()
            // view为接受软键盘输入的视图，SHOW_FORCED表示强制显示
            // if (!isOpen)
            if (imm != null) {
                imm.showSoftInput(eTt, InputMethodManager.SHOW_FORCED);
            }
            // 指定到最后位置
            setEditTextSelectionEnd(eTt);
            // 如果输入法在窗口上已经显示，则隐藏，反之则显示
            // imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
        }, 500);

//        Disposable d = Observable
//                .timer(600, TimeUnit.MILLISECONDS)
//                .subscribe(aLong -> {
//                });
//
//        if (baseView != null) {
//            baseView.addRxStop(d);
//        }

        // timer.schedule(new TimerTask() {}, 400)
    }


    /**
     * 设置 EditText View的光标在最后
     *
     * @author LuoHao
     * Created on 2018/4/24 16:07
     */
    public static void setEditTextSelectionEnd(@Nullable EditText et) {
        if (et == null) {
            return;
        }
        // 要先设置一下获取焦点 EditText.requestFocus()
        et.requestFocus();
        String text = et.getText().toString().trim();
        et.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
    }

    /**
     * 关闭系统软键盘
     * <p/>
     * 注意：editView控件失去焦点
     *
     * @param otherView 其他View获取到焦点，使编辑控件EditText失去焦点
     * @param eTt       编辑控件
     */
    public static void hideSoftInput(final View otherView, final EditText eTt) {
        if (otherView == null || eTt == null) {
            return;
        }
        otherView.setFocusable(true);
        otherView.setFocusableInTouchMode(true);
        otherView.requestFocus();
        // 隐藏键盘
        InputMethodManager imm = (InputMethodManager)
                eTt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // isOpen若返回true，则表示输入法打开，默认是打开的....
        boolean isOpen = imm != null && imm.isActive();
        // view为接受软键盘输入的视图，hideSoftInputFromWindow强制隐藏键盘
        if (isOpen) {
            imm.hideSoftInputFromWindow(eTt.getWindowToken(), 0);
        }
    }
}