package mo.wall.org.throwcard;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThinkingFactory implements LayoutInflater.Factory {
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        // 创建View-----------------------start
        View view = null;
        try {
            if (-1 == name.indexOf('.')) {
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name,
                            "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name,
                            "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name,
                            "android.webkit.", attrs);
                }
            } else {
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            view = null;
        }

        // 创建View-----------------------end
        if (view == null) {
            return null;
        }
        // 拿到View之后进行自定义处理---------start
        if (view instanceof TextView) {
            TextView t_view = (TextView) view;
            if (t_view.getText().toString().contains("Q群")) {
                t_view.setVisibility(View.GONE);
            }
        }
        if (view instanceof Button) {
            Button t_view = (Button) view;
            if (t_view.getText().toString().contains("一键加入")) {
                t_view.setVisibility(View.GONE);
            }
        }
        // 拿到View之后进行自定义处理---------end
        return view;
    }


    public static void rep(Activity activity) {
        ThinkingFactory mFactory = new ThinkingFactory();
        activity.getLayoutInflater().setFactory(mFactory);
    }
}