package mo.wall.org.markerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import androidx.core.view.ViewConfigurationCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-06 19:54
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class PolylineView extends View {

    private static final String TAG = PolylineView.class.getSimpleName();

    private int width, height;

    private List<String> mYHearts;

    private List<PolylineData> mDatas1;

    private List<PolylineData> mDatas2;

    private TextPaint yTextPaint = null;

    private Paint yLinePaint = null;

    private Paint xLinePaint = null;

    private TextPaint xTextPaint = null;

    /**
     * 曲线图的画
     */
    private Paint contentPaint = null;

    private Paint contentPaint2 = null;

    private Path contentPath = null;

    private Path contentPath2 = null;

    private int radius;
    private int radiusCenter;

    private Paint normalPointPaint;

    private Paint normalPointPaint2;

    private Paint normalWhilePointPaint;


    private int mTouchSlop;


    private ClickListener clickListener;

    /**
     * x轴的平均值
     */
    private float xEqually = 0f;
    private float mTextXHeight = 0f;
    private float mTextYHeight = 0f;
    private int mYSize;
    private int mXSize;
    private float mMinY;
    private float mMaxY;
    private float realHeight;
    private float yEqually;
    private float xMaxTextMeasureText;
    private float yMaxTextMeasureText;


    /**
     * x的线
     */
    private Path mFLinePath;

    private Path mPointCirclePath;
    private Path mCenterPointCirclePath;

    private Path mPointCirclePath2;
    private Path mCenterPointCirclePath2;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public PolylineView(Context context) {
        this(context, null);
    }

    public PolylineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PolylineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();

        mTextXHeight = xTextPaint.descent() - xTextPaint.ascent();
        mTextYHeight = yTextPaint.descent() - yTextPaint.ascent();
        realHeight = height - getPaddingBottom() - getPaddingTop() - mTextXHeight * 2;

    }

    /**
     * 滑动的线在这2个区间内
     */
    float startX = 0f;
    float endX = 0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mYSize <= 0) {
            return;
        }
        if (mXSize <= 0) {
            return;
        }

        drawY(canvas);
        drawX(canvas);
        //垂直的线
        canvas.drawLine(lastCurX, realHeight, lastCurX, 0, xLinePaint);
    }

    private void drawX(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < mXSize; i++) {
            float nextX = (int) (xEqually * i + startX);
            PolylineData pCenterData = mDatas1.get(i);
            String xDate = pCenterData.date;
            String xTime = pCenterData.time;
            //记录每个位置，滑动的时候用
            pCenterData.currentX = nextX;
            if (mXSize % 2 == 0) {
                //承载偶数
                if (i == 0
                        || i == mXSize / 2
                        || i == mXSize / 2 - 1
                        || i == mXSize - 1) {
                    drawXText(canvas, mTextXHeight, nextX, xDate, xTime);
                }
            } else {
                //承载奇数
                if (i == 0
                        || i == mXSize / 2
                        || i == mXSize - 1) {
                    drawXText(canvas, mTextXHeight, nextX, xDate, xTime);
                }
            }
        }
        drawLine(canvas, contentPath, mPointCirclePath, mCenterPointCirclePath, normalPointPaint, contentPaint);
        //暂时先简单画吧，凑合用
        if (!mDatas2.isEmpty()) {
            drawLine(canvas, contentPath2, mPointCirclePath2, mCenterPointCirclePath2, normalPointPaint2, contentPaint2);
        }
        canvas.restore();
        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要
//        contentPath.lineTo(pointMaps.get(pointMaps.size() - 1).x, height - getPaddingBottom() - getPaddingTop() - textH / 2);
//        contentPath.lineTo(startX + getPaddingLeft(), height - getPaddingBottom() - getPaddingTop() - textH / 2);
//        contentPath.close();
        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要
    }

    /**
     * 绘制y轴的文字
     *
     * @param canvas
     */
    private void drawY(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < mYSize; i++) {
            String yText = mYHearts.get(i);
            float nextY = yEqually * (mYSize - i);
            //文字
            canvas.drawText(yText, getPaddingLeft() + yMaxTextMeasureText / 2, nextY + mTextYHeight / 2, yTextPaint);
            //canvas.drawLine(startX, nextY, endX, nextY, yLinePaint);
        }
        //这里画条线
        canvas.drawPath(mFLinePath, yLinePaint);
        canvas.restore();
    }

    /**
     * 画折线的位置
     *
     * @param canvas
     * @param contentPath
     * @param contentPaint
     */
    private void drawLine(Canvas canvas, Path contentPath, Path pointCirclePath,
                          Path centerPointCirclePath,
                          Paint normalPointPaint, Paint contentPaint) {
        canvas.drawPath(contentPath, contentPaint);
        //canvas.clipPath(pointCirclePath, Region.Op.XOR);
        canvas.drawPath(pointCirclePath, normalPointPaint);
        canvas.drawPath(centerPointCirclePath, normalWhilePointPaint);
    }

    /**
     * 折线的计算位置
     *
     * @param realHeight
     * @param i
     * @param nextX
     * @param contentPath
     */
    private void lineCalc(float realHeight, int i, float nextX, PolylineData centerData, Path contentPath, Path pointCircle, Path centerPointCircle) {
        //记录下线的位置
        float currentY1 = 0;
        try {
            currentY1 = Float.parseFloat(centerData.value);
        } catch (Exception e) {
            //
        } finally {
            //计算y的位置
            float y = realHeight - (realHeight - yEqually) * (currentY1 - mMinY) / (mMaxY - mMinY);
            //centerData.x = nextX;
            //centerData.y = y;
            if (i == 0) {
                contentPath.moveTo(nextX, y);
            } else {
                contentPath.lineTo(nextX, y);
            }
            //增加圆
            pointCircle.addCircle(nextX, y, radius, Path.Direction.CW);
            centerPointCircle.addCircle(nextX, y, radiusCenter, Path.Direction.CW);//用于消除交际部分，我菜只能这样做了
        }
    }

    /**
     * 绘制x轴的文字
     *
     * @param canvas
     * @param textXHeight
     * @param nextX
     * @param xDate
     * @param xText
     */
    private void drawXText(Canvas canvas, float textXHeight, float nextX, String xDate, String xText) {
        xTextPaint.setColor(Color.parseColor("#666666"));
        canvas.drawText(xDate, nextX, height - getPaddingBottom() - textXHeight, xTextPaint);

        xTextPaint.setColor(Color.parseColor("#999999"));
        canvas.drawText(xText, nextX, height - getPaddingBottom(), xTextPaint);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {

        }

        yTextPaint = new TextPaint();
        yTextPaint.setColor(Color.parseColor("#A0A4AA"));
        yTextPaint.setTextSize(24);
        yTextPaint.setTextAlign(Paint.Align.CENTER);

        xTextPaint = new TextPaint();
        xTextPaint.setColor(Color.parseColor("#666666"));
        xTextPaint.setTextSize(22);
        xTextPaint.setTextAlign(Paint.Align.CENTER);

        yLinePaint = new Paint();
        yLinePaint.setColor(Color.parseColor("#416180"));


        xLinePaint = new Paint();
        xLinePaint.setColor(Color.parseColor("#999999"));
        xLinePaint.setStrokeWidth(dip2px(getContext(), 1));


        contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setPathEffect(new CornerPathEffect(dip2px(getContext(), 3)));
        contentPaint.setColor(Color.parseColor("#13CC9F"));
        contentPaint.setStrokeWidth(dip2px(getContext(), 1));
        contentPaint.setStyle(Paint.Style.STROKE);


        contentPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint2.setPathEffect(new CornerPathEffect(dip2px(getContext(), 3)));
        contentPaint2.setColor(Color.parseColor("#008CFF"));
        contentPaint2.setStrokeWidth(dip2px(getContext(), 1));
        contentPaint2.setStyle(Paint.Style.STROKE);

        contentPath = new Path();

        contentPath2 = new Path();


        mFLinePath = new Path();

        mPointCirclePath = new Path();
        mCenterPointCirclePath = new Path();
        mPointCirclePath2 = new Path();
        mCenterPointCirclePath2 = new Path();


        radius = dip2px(getContext(), 2);//圆半径

        radiusCenter = radius - dip2px(getContext(), 1);//圆半径


        normalPointPaint = new Paint();
        normalPointPaint.setColor(Color.parseColor("#13CC9F"));
        normalPointPaint.setStrokeWidth(dip2px(getContext(), 2));
        normalPointPaint.setStyle(Paint.Style.STROKE);

        normalPointPaint2 = new Paint();
        normalPointPaint2.setColor(Color.parseColor("#008CFF"));
        normalPointPaint2.setStrokeWidth(dip2px(getContext(), 2));
        normalPointPaint2.setStyle(Paint.Style.STROKE);

        normalWhilePointPaint = new Paint();
        normalWhilePointPaint.setColor(Color.parseColor("#FFFFFF"));
        normalWhilePointPaint.setStrokeWidth(dip2px(getContext(), 1));
        normalWhilePointPaint.setStyle(Paint.Style.FILL);


        mYHearts = new ArrayList<>();

        mDatas1 = new ArrayList<>();
        mDatas2 = new ArrayList<>();


        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }


    public void setData(List<String> yLists,
                        List<PolylineData> data1,
                        List<PolylineData> data2) {

        mYHearts.clear();
        mDatas1.clear();
        mDatas2.clear();

        mYHearts.addAll(yLists);
        mDatas1.addAll(data1);
        mDatas2.addAll(data2);

        //绘制y轴上的文字
        mYSize = mYHearts.size();
        //绘制x轴上的文字
        mXSize = mDatas1.size();

        if (mYSize == 0 || mXSize == 0) {
            return;
        }
        mMinY = -1;
        mMaxY = -1;
        try {
            mMinY = Float.parseFloat(mYHearts.get(0));
            mMaxY = Float.parseFloat(mYHearts.get(mYSize - 1));
        } catch (Exception e) {
            //...
        }

        post(() -> {
            //取出最大的文字
            String yMaxText = mYHearts.get(mYSize - 1);
            String xMaxText = mDatas1.get(mXSize - 1).date;
            //y轴的文字最长
            yMaxTextMeasureText = yTextPaint.measureText(yMaxText);
            //x轴的文字最长
            xMaxTextMeasureText = yTextPaint.measureText(xMaxText);

            startX = getPaddingLeft() + yMaxTextMeasureText + dip2px(getContext(), 10);
            endX = width - getPaddingRight() - xMaxTextMeasureText / 2;

            xEqually = (endX - startX) * 1f / (mXSize == 1 ? mXSize : mXSize - 1);

            yEqually = realHeight * 1f / mYSize;

            mFLinePath.reset();
            //创建path记录x的线
            for (int i = 0; i < mYSize; i++) {
                float nextY = yEqually * (mYSize - i);
                Path linePath = new Path();
                // public void addRect(float left, float top, float right, float bottom, Direction dir)
                linePath.addRect(startX, nextY + 1, endX, nextY, Path.Direction.CW);
                mFLinePath.addPath(linePath);
            }

            mPointCirclePath.reset();
            mCenterPointCirclePath.reset();
            mPointCirclePath2.reset();
            mCenterPointCirclePath2.reset();

            for (int i = 0; i < mXSize; i++) {
                Path pointCircle = new Path();
                Path centerPointCircle = new Path();
                float nextX = (int) (xEqually * i + startX);
                PolylineData pCenterData = mDatas1.get(i);
                lineCalc(realHeight, i, nextX, pCenterData, contentPath, pointCircle, centerPointCircle);
                mPointCirclePath.addPath(pointCircle);
                mCenterPointCirclePath.addPath(centerPointCircle);
                //暂时先简单画吧，凑合用
                if (!mDatas2.isEmpty()) {
                    Path pointCircle2 = new Path();
                    Path centerPointCircle2 = new Path();
                    PolylineData polylineData2 = mDatas2.get(i);
                    lineCalc(realHeight, i, nextX, polylineData2, contentPath2, pointCircle2, centerPointCircle2);
                    mPointCirclePath2.addPath(pointCircle2);
                    mCenterPointCirclePath2.addPath(centerPointCircle2);
                }
            }

            invalidate();
        });

    }


    private float lastCurX;
    private float lastX;
    private float lastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    /**
     * //https://blog.csdn.net/u012481172/article/details/51280995
     * //true表示子View不要父View拦截事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final ViewParent parent = getParent();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                lastX = event.getX();

                lastCurX = event.getX();

                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                //为了初始化值
                selectPos = -1;
                downAndMove(lastX);
                break;
            case MotionEvent.ACTION_MOVE:

                lastCurX = event.getX();

                float y = event.getY();
                float x = event.getX();

                float dy = y - lastY;
                float dx = x - lastX;

                float diffY = Math.abs(dy);
                float diffX = Math.abs(dx);

                if (diffY > diffX && diffY > dip2px(getContext(), 50)) {
                    //滑动幅度大的时候才允许
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    downAndMove(x);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                lastCurX = -1;
                invalidate();
                if (clickListener != null) {
                    clickListener.hideClick();
                }
                break;
        }
        return true;
    }

    //private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    volatile int selectPos = 0;

    private void downAndMove(float x) {
        int size = mXSize;
        if (size <= 0) {
            return;
        }
        if (size == 1) {
            PolylineData polylineData1 = mDatas1.get(0);
            refresh(0, polylineData1.currentX);
        } else {
//            for (int i = 0; i < mXSize; i++) {
//                PolylineData polylineData = mDatas1.get(i);
//                //每个点的x与touch的x求绝对值……
//                //哪个最小就是哪个点近。。
//                float tempCurX = polylineData.currentX;
//                if (i == 0 && tempCurX < mDatas1.get(i + 1).currentX / 2 && lastCurX > 0) {
//                    refresh(i, tempCurX);
//                    break;
//                } else if (lastCurX <= tempCurX) {
//                    refresh(i, tempCurX);
//                    break;
//                }
//            }
            //求余数，四舍五入计算位置
            float currentX = x - startX;
            int ceil = Math.round(currentX / xEqually);
            if (ceil >= mXSize - 1) {
                ceil = mXSize - 1;
            } else if (ceil <= 0) {
                ceil = 0;
            }
            PolylineData polylineData = mDatas1.get(ceil);
            refresh(ceil, polylineData.currentX);
        }
    }

    private void refresh(int i, float tempCurX) {
        //这里减少滑动，如果2个直一样的画
        if (selectPos == i) {
            return;
        }
        selectPos = i;
        lastCurX = tempCurX;
        if (clickListener != null) {
            clickListener.onClick(selectPos);
        }
        invalidate();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(null);
    }


    public interface ClickListener {
        public void onClick(int pos);

        public void hideClick();
    }

    static class PointXY {
        float x;
        float y;
    }

    public static class PolylineData {
        public String value;
        public String date;
        public String time;
        public String pId;
        public float x, y;
        public float currentX;

        private PolylineData(Builder builder) {
            value = builder.value;
            date = builder.date;
            time = builder.time;
            pId = builder.pId;
            x = builder.x;
            y = builder.y;
            currentX = builder.currentX;
        }

        public static final class Builder {
            private String value;
            private String date;
            private String time;
            private String pId;
            private float x;
            private float y;
            private float currentX;

            public Builder() {
            }

            public Builder value(String val) {
                value = val;
                return this;
            }

            public Builder date(String val) {
                date = val;
                return this;
            }

            public Builder time(String val) {
                time = val;
                return this;
            }

            public Builder pId(String val) {
                pId = val;
                return this;
            }

            public Builder x(float val) {
                x = val;
                return this;
            }

            public Builder y(float val) {
                y = val;
                return this;
            }

            public Builder currentX(float val) {
                currentX = val;
                return this;
            }

            public PolylineData build() {
                return new PolylineData(this);
            }
        }
    }


    /**
     * 将dp转换成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将像素转换成dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
