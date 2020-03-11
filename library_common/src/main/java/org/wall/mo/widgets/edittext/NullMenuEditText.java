package org.wall.mo.widgets.edittext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * ————————————————
 * 版权声明：本文为CSDN博主「一个灵活的胖子_Mr.Wang」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq941263013/article/details/82800383
 * <p>
 * 禁止复制粘贴功能的EditText（用于密码）
 * 1.禁止长按；
 * 2.禁止文本选中；
 * 3.处理横屏；
 * 4.用户选择操作无效化处理
 * 5.处理小米/OPPO手机（反射 android.widget.Editor 修改弹框菜单不显示）；
 */
public class NullMenuEditText extends EditText {

    public NullMenuEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //禁止长按
        setLongClickable(false);
        //禁止文本选中
        setTextIsSelectable(false);
        //EditText在横屏的时候会出现一个新的编辑界面，因此要禁止掉这个新的编辑界面；
        //新的编辑界面里有复制粘贴等功能按钮，目前测试是无效果的，以防外一，建议禁止掉。
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        //用户选择操作无效化处理
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // setInsertionDisabled when user touches the view
                    setInsertionDisabled();
                }
                return false;
            }
        });
    }

    /**
     * 小米/OPPO手机上禁止复制粘贴功能
     * 反射 android.widget.Editor 修改弹框菜单不显示
     */
    private void setInsertionDisabled() {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(this);
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        return true;
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }
}