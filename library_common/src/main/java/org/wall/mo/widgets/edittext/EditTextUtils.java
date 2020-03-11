package org.wall.mo.widgets.edittext;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.wall.mo.utils.StringUtils;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-20 11:56
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class EditTextUtils {

    /**
     * 相应del事件
     *
     * @param editText
     */
    public static void doDel(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = editText.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > editText.getWidth()
                        - editText.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    editText.setText("");
                }
                return false;
            }
        });
    }

    /**
     * 增加监听文字变化
     *
     * @param editText
     */
    public static void toDelListener(final EditText editText, final Drawable drawable) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) {
                    editText.setCompoundDrawables(null, null, null, null);
                } else {
                    //Resources resources = editText.getContext().getResources();
                    //Drawable drawable = resources.getDrawable(R.drawable.icon_delete_registered);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    editText.setCompoundDrawables(null, null, drawable, null);
                }
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    checkValue(editText, drawable);
                } else {
                    editText.setCompoundDrawables(null, null, null, null);
                }
            }
        });
    }


    private static void checkValue(EditText editText, final Drawable drawable) {
        if (StringUtils.isEmpty(editText.getText().toString())) {
            editText.setCompoundDrawables(null, null, null, null);
        } else {
            //Resources resources = editText.getContext().getResources();
            //Drawable drawable = resources.getDrawable(R.drawable.icon_delete_registered);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            editText.setCompoundDrawables(null, null, drawable, null);
        }
    }
}
