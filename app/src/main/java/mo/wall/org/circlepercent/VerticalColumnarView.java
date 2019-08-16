package mo.wall.org.circlepercent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import mo.wall.org.R;

/**
 * 参考
 * <p>
 * https://blog.csdn.net/zhcswlp0625/article/details/70342029
 * <p>
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/8/10 1:17 PM
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class VerticalColumnarView extends View {


    private final static String TAG = VerticalColumnarView.class.getSimpleName();

    private float totalCount;

    private List<ColumnarData> columnarDatas;

    private TextPaint textPaint;//文字画笔

    private Paint columnarPaint;//柱状画笔


    private int mWidth;

    private int mHeight;

    private int[] currentBarProgress;
    private int[] currentCount;

    int maxTextHeight = 0;


    public VerticalColumnarView(Context context) {
        super(context);
        init();
    }

    public VerticalColumnarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VerticalColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {

        columnarPaint = new Paint();

        columnarPaint.setStyle(Paint.Style.FILL);
        columnarPaint.setAntiAlias(true);
        columnarPaint.setStrokeWidth(dip2px(getContext(), 1));
        columnarPaint.setStrokeCap(Paint.Cap.ROUND);
        columnarPaint.setStrokeJoin(Paint.Join.ROUND);

        textPaint = new TextPaint();

        textPaint.setColor(getResources().getColor(R.color.mask_color));
        textPaint.setTextSize(sp2px(getContext(), 14));
        textPaint.setStrokeWidth(dip2px(getContext(), 1));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();

        //先获取底部字段高度
        int size = columnarDatas.size();
        for (int i = 0; i < size; i++) {
            ColumnarData columnarData = columnarDatas.get(i);
            //绘制底部的文字
            int length = columnarData.name.length();
            //求出最长哪行字体
            maxTextHeight = Math.max(maxTextHeight, length * sp2px(getContext(), 14));
        }
    }


    /**
     * ==================================
     * =======
     * =======
     * =======
     * ==================================
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int startLeftOffset = dip2px(getContext(), 16);
        int spacing = dip2px(getContext(), 10);
        int columnarHeight = dip2px(getContext(), 40);

        int left = startLeftOffset;

        int nextWidth = 0;


        int size = columnarDatas.size();

        for (int i = 0; i < size; i++) {

            //一个的宽度
            int oneWidth = (mWidth - startLeftOffset) / size;
            //下一个宽度
            nextWidth = oneWidth * i;
            //宽
            int rightWidth = oneWidth * 2 / 3;

            ColumnarData columnarData = columnarDatas.get(i);

            //绘制底部的文字
            int length = columnarData.name.length();
            for (int textIndex = 0; textIndex < length; textIndex++) {
                char oneChar = columnarData.name.charAt(textIndex);
                String sContent = String.valueOf(oneChar);
                float measureCharWidth = textPaint.measureText(sContent);
                canvas.drawText(
                        sContent,
                        left + nextWidth + rightWidth / 2 - measureCharWidth / 2,//这是为了剧中
                        mHeight - maxTextHeight + (textIndex + 1) * sp2px(getContext(), 14),
                        textPaint);
            }

//            canvas.save();
//
//            canvas.translate(startLeftOffset, nextWidth + dip2px(getContext(), 5));
//            //实现文字换行显示
//            StaticLayout myStaticLayout
//                    = new StaticLayout(columnarData.name,
//                    textPaint,
//                    left,
//                    Layout.Alignment.ALIGN_NORMAL,
//                    1.0f,
//                    0.0f,
//                    false);
//
//            myStaticLayout.draw(canvas);
//
//            //恢复图层
//            canvas.restore();


            columnarPaint.setColor(columnarData.resIdColor);

            if (currentBarProgress[i] < (mHeight - maxTextHeight - spacing - columnarHeight - startLeftOffset) * columnarData.count / totalCount) {
                currentBarProgress[i] += 5;
                postInvalidateDelayed(10);
            }

            //这是画圆柱
            //float left, float nextWidth, float right, float bottom, @NonNull Paint paint
            drawRect(left + nextWidth,
                    mHeight - maxTextHeight - spacing - currentBarProgress[i],
                    left + nextWidth + rightWidth,
                    mHeight - maxTextHeight - spacing,
                    columnarPaint,
                    canvas);

            if (currentCount[i] < (int) columnarData.count) {
                currentCount[i] += 1;
                postInvalidateDelayed(10);
            }
            //这是画字体
            String content = String.format("%d次", currentCount[i]);
            //获取文字的宽度
            float measureTextWidth = textPaint.measureText(content);
            canvas.drawText(
                    content,
                    left + nextWidth + rightWidth / 2 - measureTextWidth / 2,//这是为了剧中
                    mHeight - maxTextHeight - currentBarProgress[i] - startLeftOffset,
                    textPaint);
        }
    }

    private void drawRect(float left, float top, float right, float bottom, @NonNull Paint paint, Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(left,
                    top,
                    right,
                    bottom,
                    (float) dip2px(getContext(), 2),
                    (float) dip2px(getContext(), 2),
                    paint);
        } else {
            canvas.drawRect(left,
                    top,
                    right,
                    bottom,
                    paint);
        }
    }

    public VerticalColumnarView setTotalCount(float totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public void setColumnarDatas(List<ColumnarData> columnarDatas) {
        this.columnarDatas = columnarDatas;
        int size = columnarDatas.size();
        currentBarProgress = new int[size];
        currentCount = new int[size];
        postInvalidate();
    }

    public static class ColumnarData {
        public String name;//左边名字
        public float count;//数量
        public int resIdColor;//柱状的颜色

        public ColumnarData(String name, float count, int resIdColor) {
            this.name = name;
            this.count = count;
            this.resIdColor = resIdColor;
        }

    }

    /**
     * 创建数据
     *
     * @param name
     * @param count
     * @param resIdColor
     * @return
     */
    public static ColumnarData createColumnarData(String name, float count, int resIdColor) {
        return new ColumnarData(name, count, resIdColor);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
