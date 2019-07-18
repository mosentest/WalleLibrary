package org.wall.mo.utils.textview;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/7/4-10:32
 * desc   : 设置高亮显示文字
 * version: 1.0
 */
public class TextViewHighTipUtil {

    /**
     * 设置高亮显示文字
     *
     * @param textView        控件
     * @param content         内容
     * @param isHtml          是否h5文本
     * @param underlineText   下划线 备注：如果为true的时候，字体必须为14sp，不然vivo手机展示出问题
     * @param highTipCallback 回调
     * @param rColor          高亮显示文案的颜色
     * @param highTip         高亮显示文案
     */
    public static void setContent(final TextView textView,
                                  final String content,
                                  final boolean isHtml,
                                  final boolean underlineText,
                                  final HighTipCallback highTipCallback,
                                  final int rColor,
                                  final String... highTip) {
        if (highTip != null) {
            //如果为true的时候，字体必须为14sp，不然vivo手机展示出问题
            if (underlineText) {
                textView.setTextSize(14);
            }
            SpannableString str = new SpannableString(content);
            for (int i = 0; i < highTip.length; i++) {
                final int j = i;
                //修改原本的协议
                if (underlineText) {
                    str.setSpan(new UnderlineSpan(),
                            content.indexOf(highTip[i]),
                            content.indexOf(highTip[i]) + highTip[i].length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                str.setSpan(new ClickableSpan() {
                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    // 设置文本颜色
                                    ds.setColor(textView.getContext().getResources().getColor(rColor));
                                    // 设置没有下划线
                                    ds.setUnderlineText(underlineText);
                                }

                                @Override
                                public void onClick(View widget) {
                                    if (highTipCallback != null) {
                                        highTipCallback.dialogHighTipListener(j);
                                    }
                                }
                            }, content.indexOf(highTip[i]),
                        content.indexOf(highTip[i]) + highTip[i].length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setText(str);
            //不设置 没有点击事件
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            //设置点击后的颜色为透明
            textView.setHighlightColor(Color.TRANSPARENT);
        } else {
            textView.setText(isHtml ? Html.fromHtml(content) : content);
            textView.setMovementMethod(ScrollingMovementMethod.getInstance());
            //如果为true的时候，字体必须为14sp，不然vivo手机展示出问题
            if (underlineText) {
                textView.setTextSize(14);
                textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                textView.getPaint().setAntiAlias(true);//这里要加抗锯齿
            }
        }
    }


    public interface HighTipCallback {
        void dialogHighTipListener(int pos);
    }
}
