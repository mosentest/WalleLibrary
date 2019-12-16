package mo.wall.org.circlepercent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewConfigurationCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.lang.reflect.Field;
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

    private int startLeftOffset;
    private int spacing;
    private int columnarHeight;
    private int columnarBottom;


    private float lastUpX;
    private float lastUpY;


    private static long mLastClickTime;
    private final static long timeInterval = 1000L;


    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

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

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

        startLeftOffset = dip2px(getContext(), 10);
        spacing = dip2px(getContext(), 12);
        columnarHeight = dip2px(getContext(), 25);
        columnarBottom = dip2px(getContext(), 30);

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


    /**
     * 比onDraw先执行
     * <p>
     * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
     * 一个MeasureSpec由大小和模式组成
     * 它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小;
     * EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
     * AT_MOST(至多)，子元素至多达到指定大小的值。
     * <p>
     * 它常用的三个函数：
     * 1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
     * 2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
     * 3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        Log.e(TAG, "---minimumWidth = " + minimumWidth + "");
        Log.e(TAG, "---minimumHeight = " + minimumHeight + "");
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e(TAG, "---speSize = " + specSize + "");

        switch (specMode) {
            //wrap_content
            case MeasureSpec.AT_MOST:
                defaultWidth = specSize;

                Log.e(TAG, "---speMode = AT_MOST");
                break;
            //match_parent
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "---speMode = EXACTLY");
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, "---speMode = UNSPECIFIED");
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e(TAG, "---speSize = " + specSize + "");

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                int calculateHeight = calculateHeight();
                defaultHeight = Math.max(1, calculateHeight);
                Log.e(TAG, "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                Log.e(TAG, "---speSize = EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                Log.e(TAG, "---speSize = UNSPECIFIED");
//        1.基准点是baseline
//        2.ascent：是baseline之上至字符最高处的距离
//        3.descent：是baseline之下至字符最低处的距离
//        4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
//        5.top：是指的是最高字符到baseline的值,即ascent的最大值
//        6.bottom：是指最低字符到baseline的值,即descent的最大值

                break;
        }
        return defaultHeight;


    }

    private int calculateHeight() {
        int top = 0;
        for (int i = 0; i < mColumnarDatas.size(); i++) {
            top = columnarHeight * i + columnarBottom * i;
        }
        return getPaddingTop() + columnarBottom + top + getPaddingBottom();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float absY;
        float absX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //实现点击效果
                lastUpY = event.getY();
                lastUpX = event.getX();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                absY = Math.abs(event.getY() - lastUpY);
                absX = Math.abs(event.getX() - lastUpX);
                //在区域内，才响应事件
                if (absY < mTouchSlop && absX < mTouchSlop) {
                    //遍历位置
                    int pos = -1;
                    for (int i = 1; i < mPoint.length; i++) {
                        if (lastUpY > mPoint[i - 1].y && lastUpY <= mPoint[i].y) {
                            //1-end位置
                            pos = i;
                        } else if (lastUpY <= mPoint[i - 1].y && (i - 1) == 0) {
                            ////第一个位置
                            pos = i - 1;
                        }
                    }
                    long nowTime = System.currentTimeMillis();
                    if (pos != -1 && nowTime - mLastClickTime > timeInterval) {
                        mLastClickTime = nowTime;
                        Toast.makeText(getContext(), "i=" + pos + ",nowTime:" + nowTime, Toast.LENGTH_SHORT).show();
                        if (mCallBack != null) {
                            // 单次点击事件
                            mCallBack.onClick(pos);
                        }
                    }
                }
                return true;
        }
        return super.onTouchEvent(event);
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

        int size = mWidth / 4;
        int left = getPaddingLeft() + size;

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
                //为了忽悠设置2行的长度...
                CharSequence ellipsize = TextUtils.ellipsize(columnarData.name,
                        textPaint,
                        left + size - startLeftOffset,
                        TextUtils.TruncateAt.END);
                Log.i(TAG, "ellipsize>>" + ellipsize);
                StaticLayout myStaticLayout
                        = new StaticLayout(ellipsize.toString(),
                        textPaint,
                        left,
                        Layout.Alignment.ALIGN_NORMAL,
                        1.0F,
                        0.0F,
                        false);
                //为了反射，想控制2行...
                try {
                    Field mMaxLines = myStaticLayout.getClass().getDeclaredField("mLineCount");
                    mMaxLines.setAccessible(true);
                    mMaxLines.setInt(myStaticLayout, 2);
                } catch (Exception e) {
                    Log.wtf(TAG, e);
                }
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
//                textPaint.setTextScaleX(currentCount[i] / columnarData.count * 1);
                postInvalidateDelayed(20);
            } else {
//                textPaint.setTextScaleX(1);
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

//    private void setClickEnable(boolean clickEnable) {
//        this.clickEnable = clickEnable;
//    }

    public interface CallBack {
        public void onClick(int pos);
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
