package mo.wall.org.markerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-06 19:54
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MarkerView extends View {

    private static final String TAG = MarkerView.class.getSimpleName();

    private int width, height;


    public List<String> yHearts;

    public List<String> xTimes;

    public Map<String, String> datas;


    private Paint yTextPaint = null;


    private Paint yLinePaint = null;

    private Paint xLinePaint = null;

    private Paint xTextPaint = null;

    /**
     * 曲线图的画
     */
    private Paint contentPaint = null;

    private Path contentPath = null;

    private int radius;
    private int radiusCenter;

    private Paint normalPointPaint;

    private Paint normalWhilePointPaint;


    private List<Float> xWeizi = new ArrayList<>();


    private List<PointXY> points = new ArrayList<>();


    public MarkerView(Context context) {
        this(context, null);
    }

    public MarkerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MarkerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
    }

    /**
     * 滑动的线在这2个区间内
     */
    float startX = 0;
    float endX = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制y轴上的文字
        int ySize = yHearts.size();

        int yEqually = height / ySize;


        int textH = -1;

        for (int i = 0; i < ySize; i++) {
            String yText = yHearts.get(i);
            int nextY = yEqually * (ySize - i) - getPaddingBottom() - getPaddingTop();
            float yTextMeasureText = yTextPaint.measureText(yText);
            Rect rect = new Rect();
            yTextPaint.getTextBounds(yText, 0, yText.length(), rect);
            textH = rect.height();
            canvas.drawText(yText, getPaddingLeft(), nextY, yTextPaint);
            //这里画条线
            startX = getPaddingLeft() + yTextMeasureText + dip2px(getContext(), 10);
            endX = width - getPaddingRight();
            canvas.drawLine(startX, nextY - textH / 2, endX, nextY - textH / 2, yLinePaint);
        }
        //绘制x轴上的文字
        int xSize = xTimes.size();
        int xEqually = width / xSize;


        int singleY = -1;
        int minY = -1;
        int maxY = -1;
        try {
            minY = Integer.parseInt(yHearts.get(0));
            maxY = Integer.parseInt(yHearts.get(ySize - 1));
        } catch (Exception e) {

        } finally {
            singleY = (maxY - minY) / ySize;
        }

        //y 每一个等分距离
        //每个等分都要减去文字的一半的高度
        int yUnit = (height - getPaddingBottom() - getPaddingTop()) / ySize - textH / 2;


        points.clear();
        xWeizi.clear();
        for (int i = 0; i < xSize; i++) {
            float nextX = (int) (xEqually * i + startX) + getPaddingLeft();
            //记录滑动线的最大值和最小值
            if (i == 0) {
                startX = Math.max(nextX, startX);
            }
            if (i == xSize - 1) {
                endX = Math.min(nextX, endX);
            }
            String xText = xTimes.get(i);
            xWeizi.add(nextX);
            canvas.drawText(xText, nextX, height - getPaddingBottom(), xTextPaint);

            //记录下线的位置
            PointXY point = new PointXY();
            int y = 0;
            try {
                String content = datas.get(xText);
                y = Integer.parseInt(content);
            } catch (Exception e) {

            } finally {
                point.x = nextX;
                //计算y的位置
                point.y = (height - getPaddingBottom() - getPaddingTop()) - yUnit * (y - minY) / singleY;
            }
            points.add(point);
            if (i == 0) {
                contentPath.moveTo(point.x, point.y);
            } else {
                contentPath.lineTo(point.x, point.y);
            }
        }

        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要
//        contentPath.lineTo(points.get(points.size() - 1).x, height - getPaddingBottom() - getPaddingTop() - textH / 2);
//        contentPath.lineTo(startX + getPaddingLeft(), height - getPaddingBottom() - getPaddingTop() - textH / 2);
//        contentPath.close();
        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要
        //这是合并的效果，暂时不需要

        canvas.drawPath(contentPath, contentPaint);

        //画点
        for (int i = 0; i < points.size(); i++) {
            canvas.drawCircle(points.get(i).x, points.get(i).y, radius, normalPointPaint);
            canvas.drawCircle(points.get(i).x, points.get(i).y, radiusCenter, normalWhilePointPaint);//用于消除交际部分，我菜只能这样做了
        }
        //垂直的线
        canvas.drawLine(currentX, height, currentX, 0, xLinePaint);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {

        }

        yTextPaint = new Paint();
        yTextPaint.setColor(Color.parseColor("#333333"));
        yTextPaint.setTextSize(23);
        yTextPaint.setTextAlign(Paint.Align.LEFT);

        xTextPaint = new Paint();
        xTextPaint.setColor(Color.parseColor("#333333"));
        xTextPaint.setTextSize(24);
        xTextPaint.setTextAlign(Paint.Align.CENTER);

        yLinePaint = new Paint();
        yLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yLinePaint.setColor(Color.parseColor("#333333"));


        xLinePaint = new Paint();
        xLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xLinePaint.setColor(Color.parseColor("#333333"));
        xLinePaint.setStrokeWidth(dip2px(getContext(), 1));


        contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setColor(Color.parseColor("#303F9F"));
        contentPaint.setStrokeWidth(dip2px(getContext(), 3));
        contentPaint.setStyle(Paint.Style.STROKE);


        contentPath = new Path();

        radius = dip2px(getContext(), 3);//圆半径

        radiusCenter = radius - dip2px(getContext(), 1);//圆半径


        normalPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalPointPaint.setColor(Color.parseColor("#303F9F"));
        normalPointPaint.setStrokeWidth(dip2px(getContext(), 3));
        normalPointPaint.setStyle(Paint.Style.STROKE);

        normalWhilePointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalWhilePointPaint.setColor(Color.parseColor("#FFFFFF"));
        normalWhilePointPaint.setStrokeWidth(dip2px(getContext(), 3));
        normalWhilePointPaint.setStyle(Paint.Style.FILL);


        yHearts = new ArrayList<>();
        xTimes = new ArrayList<>();

        datas = new HashMap<>();


        yHearts.add("300");
        yHearts.add("400");
        yHearts.add("500");
        yHearts.add("600");
        yHearts.add("700");
        yHearts.add("800");

        xTimes.add("11-13");
        xTimes.add("11-14");
        xTimes.add("11-15");
        xTimes.add("11-16");
        xTimes.add("11-17");
        xTimes.add("11-18");

        datas.put("11-13", "309");
        datas.put("11-14", "510");
        datas.put("11-15", "760");
        datas.put("11-16", "390");
        datas.put("11-17", "620");
        datas.put("11-18", "590");
    }


    private float currentX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                if (currentX > endX) {
                    currentX = endX;
                }
                if (currentX < startX) {
                    currentX = startX;
                }
                int pos = 0;
                for (int i = 0; i < xWeizi.size(); i++) {
                    //每个点的x与touch的x求绝对值……
                    //哪个最小就是哪个点近。。
                    float curX = xWeizi.get(i);
                    if (i == 0 && currentX < xWeizi.get(i + 1) / 2) {
                        pos = i;
                        break;
                    } else if (currentX < curX) {
                        pos = i;
                        break;
                    }
                }
                float curX = xWeizi.get(pos);
                currentX = curX;

//                boolean contains = xWeizi.contains(currentX);
//                if (contains) {
//                    Log.i(TAG, "currentX：" + currentX);
//                    Log.i(TAG, "startX：" + startX);
//                    Log.i(TAG, "endX：" + endX);
//                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                currentX = -100;
                invalidate();
                break;
        }
        return true;
    }


    static class PointXY {
        float x;
        float y;
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
