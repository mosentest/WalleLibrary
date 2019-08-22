package mo.wall.org.circlepercent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
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
public class ColumnarView extends View {


    private final static String TAG = ColumnarView.class.getSimpleName();

    private float totalCount;

    private List<ColumnarData> mColumnarDatas = new ArrayList<>();

    private TextPaint textPaint;//文字画笔

    private Paint columnarPaint;//柱状画笔


    private int mWidth;

    private int mHeight;

    private int[] currentBarProgress;
    private int[] currentCount;

    private Point[] mPoint;

    private CallBack mCallBack;

    public ColumnarView(Context context) {
        super(context);
        init();
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        textPaint.setColor(getResources().getColor(R.color.color_666666));
        textPaint.setTextSize(sp2px(getContext(), 12));
        textPaint.setStrokeWidth(dip2px(getContext(), 1));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //实现点击效果
                float y = event.getY();
                for (int i = 1; i < mPoint.length; i++) {
                    if (y > mPoint[i - 1].y && y <= mPoint[i].y) {
                        if (mCallBack != null) {
                            mCallBack.onClick(i);
                        }
                        Toast.makeText(getContext(), "i=" + i, Toast.LENGTH_SHORT).show();
                    } else if (y <= mPoint[i - 1].y && (i - 1) == 0) {
                        if (mCallBack != null) {
                            mCallBack.onClick(i);
                        }
                        Toast.makeText(getContext(), "i=" + (i - 1), Toast.LENGTH_SHORT).show();
                    }
//                    else if (y <= getHeight() && y > mPoint[i].y && i == mPoint.length - 1) {
//                        if (mCallBack != null) {
//                            mCallBack.onClick(i);
//                        }
//                        Toast.makeText(getContext(), "i=" + i, Toast.LENGTH_SHORT).show();
//                    }
                }
                return true;
        }
        return super.onTouchEvent(event);
    }


    public interface CallBack {
        public void onClick(int pos);
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
        int spacing = dip2px(getContext(), 12);
        int columnarHeight = dip2px(getContext(), 20);
        int columnarBottom = dip2px(getContext(), 30);

        int left = getPaddingLeft() + mWidth / 4;

        int top = 0;

        for (int i = 0; i < mColumnarDatas.size(); i++) {


            top = columnarHeight * i + columnarBottom * i;

            ColumnarData columnarData = mColumnarDatas.get(i);

            float textWidth = textPaint.measureText(columnarData.name);
            if (getPaddingLeft() + textWidth < left + startLeftOffset) {
                //不需要换行
                textPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(columnarData.name,
                        getPaddingLeft() + textWidth,
                        getPaddingTop() + top + dip2px(getContext(), 20),
                        textPaint);
            } else {
                textPaint.setTextAlign(Paint.Align.LEFT);

                canvas.save();

                canvas.translate(getPaddingLeft(), getPaddingTop() + top + dip2px(getContext(), 2));
                //实现文字换行显示
                StaticLayout myStaticLayout
                        = new StaticLayout(columnarData.name,
                        textPaint,
                        left,
                        Layout.Alignment.ALIGN_NORMAL,
                        1.0f,
                        0.0f,
                        false);

                myStaticLayout.draw(canvas);

                //恢复图层
                canvas.restore();
            }


            columnarPaint.setColor(columnarData.resIdColor);

            //left - startLeftOffset 左边的距离
            //getPaddingRight() - getPaddingLeft() 左右内边距
            //spacing 文字的空间
            String formatNum = String.format("%d次", currentCount[i]);
            //获取次数的长度
            float measureTextFormatNum = textPaint.measureText(formatNum);
            float total = (mWidth
                    - left - startLeftOffset
                    - getPaddingRight() - getPaddingLeft()
                    - spacing
                    - measureTextFormatNum) * columnarData.count / totalCount;
            if (currentBarProgress[i] < total) {
                currentBarProgress[i] += 5;
                postInvalidateDelayed(10);
                //不需要
//                columnarPaint.setAlpha((int) (currentBarProgress[i] / total * 255));
            } else {
                //不需要
//                columnarPaint.setAlpha(255);
            }
            //这是画圆柱
            //float left, float top, float right, float bottom, @NonNull Paint paint
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(left + startLeftOffset,
                        getPaddingTop() + top,
                        left + startLeftOffset + currentBarProgress[i],
                        getPaddingTop() + columnarBottom + top,
                        (float) dip2px(getContext(), 2),
                        (float) dip2px(getContext(), 2),
                        columnarPaint);
            } else {
                canvas.drawRect(left + startLeftOffset,
                        getPaddingTop() + top,
                        left + startLeftOffset + currentBarProgress[i],
                        getPaddingTop() + columnarBottom + top,
                        columnarPaint);
            }

            if (currentCount[i] < (int) columnarData.count) {
                currentCount[i] += 1;
                textPaint.setTextScaleX(currentCount[i] / columnarData.count * 1);
                postInvalidateDelayed(20);
            } else {
                textPaint.setTextScaleX(1);
            }
            //这是画字体
            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(formatNum,
                    left + startLeftOffset + currentBarProgress[i] + spacing,
                    getPaddingTop() + top + dip2px(getContext(), 20), textPaint);

            mPoint[i] = new Point();
            mPoint[i].x = (int) (getPaddingTop() + top);
            mPoint[i].y = (int) (getPaddingTop() + columnarBottom + top);
        }
    }

    public ColumnarView setTotalCount(float totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public void setColumnarDatas(List<ColumnarData> columnarDatas) {
        if (columnarDatas == null || columnarDatas.isEmpty()) {
            return;
        }
        this.mColumnarDatas.clear();
        this.mColumnarDatas.addAll(columnarDatas);
        int size = this.mColumnarDatas.size();
        currentBarProgress = new int[size];
        currentCount = new int[size];
        mPoint = new Point[size];
        postInvalidate();
    }

    public void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
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
