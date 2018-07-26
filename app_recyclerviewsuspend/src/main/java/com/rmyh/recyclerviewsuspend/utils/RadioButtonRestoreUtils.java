package com.rmyh.recyclerviewsuspend.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 作者 create by moziqi on 2018/7/26
 * 邮箱 709847739@qq.com
 * 说明
 * https://blog.csdn.net/qq_16811177/article/details/78113791
 **/
public class RadioButtonRestoreUtils {

    public static void restoredRadioButton(@IdRes int checkedId,
                                           RadioGroup radioGroup,
                                           RadioGroup.OnCheckedChangeListener listener,
                                           Context mContext,
                                           int rNormalBg,
                                           int rTextNormalBg,
                                           int rFocusBg,
                                           int rTextFocusBg,
                                           boolean isShowClick) {
        radioGroup.setOnCheckedChangeListener(null);//checked监听事件失效
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
        //清除radioButton的选中状态，只保留背景色
        radioButton.setClickable(true);
        radioButton.setChecked(false);
        int childCount = radioGroup.getChildCount();
        RadioButton childAt = null;
        //将所有radioButton背景色置为未选中状态,目的是清除之前的设置
        for (int i = 0; i < childCount; i++) {
            childAt = (RadioButton) radioGroup.getChildAt(i);
            childAt.setBackgroundResource(rNormalBg);
            childAt.setTextColor(mContext.getResources().getColor(rTextNormalBg));
        }
        if (isShowClick) {
            //将之前选中的radioButton背景色设为选中状态
            radioButton.setBackgroundResource(rFocusBg);
            radioButton.setTextColor(mContext.getResources().getColor(rTextFocusBg));
        }
        radioGroup.setOnCheckedChangeListener(listener);//重新添加
    }
}
