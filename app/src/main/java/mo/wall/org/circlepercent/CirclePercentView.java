package mo.wall.org.circlepercent;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import mo.wall.org.R;

/**
 * <p>
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/8/9 10:39 AM
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class CirclePercentView extends View {


    private final static String TAG = CirclePercentView.class.getSimpleName();

    /**
     * 总数
     */
//    private float totalNum = 100.0f;

    private List<CirclePercentData> circlePercentDatas = new ArrayList<>();

    /**
     * 圆的位置
     */
    private RectF rectF;

    private Paint circlePaint;//当前画笔

    private Paint pointPaint;//点

    private Paint linePaint;//线

    private TextPaint textPaint;//文字

    DecimalFormat df = new DecimalFormat("0%");

    private int startY;

    private int startX;


    //    private ValueAnimator circlePercentAnimator = null;
    private ValueAnimator circlePercentPointAnimator = null;
    private ValueAnimator circlePercentLineAnimator = null;

    //当前位置
    private int circlePercentPos;
    private int circlePercentPointPos;
    private int circlePercentLinePos;

    private float[] currentProgress;


    private final static float START_ANGLE = -90;

    private int centerX;
    private int centerY;
    private int radius;

    private int qianxiejiaoduY;
    private int qianxiejiaoduX;

    private int textNeedHeight;

//    private int paddingLeftAndRight;

    private int textLeftRight;

    private int circleRadius;

    public CirclePercentView(Context context) {
        super(context);
        init();
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        startX = (getWidth() - getHeight() / 2) / 2 + dip2px(getContext(), 20);
        startY = getHeight() / 4 + dip2px(getContext(), 20);
        //设置画圆的空间
        if (rectF == null) {
            rectF = new RectF(startX, startY, getWidth() - startX, getHeight() - startY);
        }


        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        circleRadius = (Math.min(getWidth(), getHeight()) - Math.min(startX, startY)) / 2;
        radius = circleRadius - dip2px(getContext(), 14);
        //设置倾斜角度的距离
        qianxiejiaoduY = dip2px(getContext(), 14);
        qianxiejiaoduX = dip2px(getContext(), 14);
        //文字上下的距离
        textNeedHeight = dip2px(getContext(), 4);
        //左右内边距
//        paddingLeftAndRight = dip2px(getContext(), 16);
        //下面字体离左右的距离
        textLeftRight = dip2px(getContext(), 10);

    }

    private void init() {
        circlePaint = new Paint();

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(dip2px(getContext(), 38));

        pointPaint = new Paint();

        pointPaint.setColor(getResources().getColor(R.color.mask_color));
        pointPaint.setStrokeWidth(dip2px(getContext(), 2));
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setAntiAlias(true);

        linePaint = new Paint();

        linePaint.setColor(getResources().getColor(R.color.mask_color));
        linePaint.setStrokeWidth(dip2px(getContext(), 1));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        textPaint = new TextPaint();

        textPaint.setColor(getResources().getColor(R.color.mask_color));
        textPaint.setTextSize(sp2px(getContext(), 13));
        textPaint.setStrokeWidth(dip2px(getContext(), 2));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCirclePercent(canvas);
        drawCirclePercentPoint(canvas);
        drawCirclePercentLine(canvas);
    }

    /**
     * 绘制圆的占比
     *
     * @param canvas
     */
    private void drawCirclePercent(Canvas canvas) {
        //改为作为成员变量
        float startAngle = START_ANGLE;
        //遍历画占比
        for (int i = 0; i < circlePercentDatas.size(); i++) {
            CirclePercentData circlePercentData = circlePercentDatas.get(i);
            float v = circlePercentData.percentage * 360;
            //v+1为了重合在一起
            if (currentProgress[i] < v + 1) {
                currentProgress[i] += 5;
                postInvalidateDelayed(10);
            }

            circlePaint.setColor(circlePercentData.resIdColor);
            //画弧度
            canvas.drawArc(rectF,
                    startAngle,
                    currentProgress[i],
                    false,
                    circlePaint);

            //当前位置和
            startAngle = startAngle + v;
            //刷新这个位置
            circlePercentPos = i;
        }
    }


    /**
     * 这是画点
     *
     * @param canvas
     */
    private void drawCirclePercentPoint(Canvas canvas) {
        //改为作为成员变量
        float startAngle = START_ANGLE;
        float preAngle = START_ANGLE;
        float preV = 0f;

        //遍历画占比
        for (int i = 0; i < circlePercentPointPos; i++) {

            CirclePercentData circlePercentData = circlePercentDatas.get(i);
            float v = circlePercentData.percentage * 360;
            //获取前一个
            if (i > 0) {
                CirclePercentData preCirclePercentData = circlePercentDatas.get(i - 1);
                preV = preCirclePercentData.percentage * 360;
            }
            //获取前一个位置和
            preAngle = preAngle + preV;

            //当前位置和
            startAngle = startAngle + v;

            //获取弧度中心位置
            float halfAngle = (startAngle - preAngle) / 2 + preAngle;

            //Log.i(TAG, "onDraw.preAngle>>" + preAngle + ",startAngle>>" + startAngle + ",halfAngle>>" + halfAngle);

            //https://www.jianshu.com/p/754b356239c1
            //https://blog.csdn.net/liaoyi_/article/details/61914388
            // 弧度＝度×π/180
            //Math.toRadians()
            float x = centerX + (float) (Math.cos(halfAngle * Math.PI / 180) * radius);
            float y = centerY + (float) (Math.sin(halfAngle * Math.PI / 180) * radius);

            //Log.i(TAG, "onDraw.x>>" + x + ",y>>" + y);
            //设置颜色
            pointPaint.setColor(circlePercentData.resIdColor);
            //画出点的位置
            if (circlePercentData.showLine) {
                canvas.drawCircle(x, y, dip2px(getContext(), 1), pointPaint);
            }
        }
    }

    /**
     * 最后画线跟文字
     *
     * @param canvas
     */
    private void drawCirclePercentLine(Canvas canvas) {
        //改为作为成员变量
        float startAngle = START_ANGLE;
        float preAngle = START_ANGLE;
        float preV = 0f;

        //遍历画占比
        for (int i = 0; i < circlePercentLinePos; i++) {
            CirclePercentData circlePercentData = circlePercentDatas.get(i);
            float v = circlePercentData.percentage * 360;
            //获取前一个
            if (i > 0) {
                CirclePercentData preCirclePercentData = circlePercentDatas.get(i - 1);
                preV = preCirclePercentData.percentage * 360;
            }
            //获取前一个位置和
            preAngle = preAngle + preV;

            //当前位置和
            startAngle = startAngle + v;

            //获取弧度中心位置
            float halfAngle = (startAngle - preAngle) / 2 + preAngle;

            //Log.i(TAG, "onDraw.preAngle>>" + preAngle + ",startAngle>>" + startAngle + ",halfAngle>>" + halfAngle);

            //https://www.jianshu.com/p/754b356239c1
            //https://blog.csdn.net/liaoyi_/article/details/61914388
            // 弧度＝度×π/180
            //Math.toRadians()
            float x = centerX + (float) (Math.cos(halfAngle * Math.PI / 180) * radius);
            float y = centerY + (float) (Math.sin(halfAngle * Math.PI / 180) * radius);

            //Log.i(TAG, "onDraw.x>>" + x + ",y>>" + y);

            //下面是画线的处理
            //设置颜色
            linePaint.setColor(circlePercentData.resIdColor);


            float leanX = 0;
            float leanY = 0;
            float endOffsetX = 0;
            float endOffsetY = 0;
            Path path = new Path();
            path.moveTo(x, y);

            float yRightPy = (1 - x / (getWidth() * 0.75f)) * 100;
            float yLeftPy = x / (getWidth() * 0.75f) * 100;

            float yBottomPx = y / (getHeight() * 0.85f) * 100;

            if (x > centerX) {

                //控制位置的对齐方向
                textPaint.setTextAlign(Paint.Align.RIGHT);

                //右边画线
                if (y > centerY) {


                    //设置一个倾向角度
                    leanX = x + qianxiejiaoduX;
                    leanY = y + yRightPy + (yBottomPx > 85 ? yBottomPx : yBottomPx / 2);

                    endOffsetX = getWidth() - getPaddingRight();
                    endOffsetY = y + yRightPy + (yBottomPx > 85 ? yBottomPx : yBottomPx / 2);


                    textPaint.setColor(getResources().getColor(R.color.color_333333));
                    //设置文字
                    //百分比
                    if (circlePercentData.showLine) {
                        canvas.drawText(
                                circlePercentData.percentageName,
                                endOffsetX,
                                leanY - textNeedHeight,
                                textPaint);
                    }
                    //保存图层
                    canvas.save();

                    canvas.translate(endOffsetX, leanY + textNeedHeight);

                    textPaint.setColor(getResources().getColor(R.color.color_999999));

                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) (getWidth() - leanX - textLeftRight),
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    if (circlePercentData.showLine) {
                        myStaticLayout.draw(canvas);
                    }

                    //恢复图层
                    canvas.restore();

                } else {

                    //设置一个倾向角度
                    leanX = x + qianxiejiaoduX;
                    leanY = y - (i == 0 ? yRightPy * 2 : yRightPy);

                    endOffsetX = getWidth() - getPaddingRight();
                    endOffsetY = y - (i == 0 ? yRightPy * 2 : yRightPy);


                    textPaint.setColor(getResources().getColor(R.color.color_333333));
                    //设置文字
                    //百分比
                    if (circlePercentData.showLine) {
                        canvas.drawText(
                                circlePercentData.percentageName,
                                endOffsetX,
                                leanY - textNeedHeight,
                                textPaint);
                    }
                    //保存图层
                    canvas.save();

                    canvas.translate(endOffsetX, leanY + textNeedHeight);

                    textPaint.setColor(getResources().getColor(R.color.color_999999));
                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) (getWidth() - leanX - textLeftRight),
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    if (circlePercentData.showLine) {
                        myStaticLayout.draw(canvas);
                    }

                    //恢复图层
                    canvas.restore();

                }

            } else {

                textPaint.setTextAlign(Paint.Align.LEFT);

                //左边画线
                if (y > centerY) {


                    //设置一个倾向角度
                    leanX = x - qianxiejiaoduX;
                    leanY = y + yLeftPy - (yBottomPx < 80 ? yBottomPx / 2 : yBottomPx < 90 ? yBottomPx / 3 : -yBottomPx * 1 / 3);

                    endOffsetX = 0 + getPaddingLeft();
                    endOffsetY = y + yLeftPy - (yBottomPx < 80 ? yBottomPx / 2 : yBottomPx < 90 ? yBottomPx / 3 : -yBottomPx * 1 / 3);


                    textPaint.setColor(getResources().getColor(R.color.color_333333));
                    //设置文字
                    //百分比
                    if (circlePercentData.showLine) {
                        canvas.drawText(
                                circlePercentData.percentageName,
                                endOffsetX,
                                leanY - textNeedHeight,
                                textPaint);
                    }
                    //保存图层
                    canvas.save();

                    canvas.translate(endOffsetX, leanY + textNeedHeight);

                    textPaint.setColor(getResources().getColor(R.color.color_999999));

                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) leanX - textLeftRight,
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    if (circlePercentData.showLine) {
                        myStaticLayout.draw(canvas);
                    }

                    //恢复图层
                    canvas.restore();

                } else {


                    //设置一个倾向角度
                    leanX = x - qianxiejiaoduX;
                    leanY = y - (i == (circlePercentLinePos - 1) ? yLeftPy * 2 / 3 : yLeftPy);

                    endOffsetX = 0 + getPaddingLeft();
                    endOffsetY = y - (i == (circlePercentLinePos - 1) ? yLeftPy * 2 / 3 : yLeftPy);

                    textPaint.setColor(getResources().getColor(R.color.color_333333));
                    //设置文字
                    //百分比
                    if (circlePercentData.showLine) {
                        canvas.drawText(
                                circlePercentData.percentageName,
                                endOffsetX,
                                leanY - textNeedHeight,
                                textPaint);
                    }

                    //保存图层
                    canvas.save();

                    canvas.translate(endOffsetX, leanY + textNeedHeight);

                    textPaint.setColor(getResources().getColor(R.color.color_999999));

                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) leanX - textLeftRight,
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    if (circlePercentData.showLine) {
                        myStaticLayout.draw(canvas);
                    }

                    //恢复图层
                    canvas.restore();
                }


            }

            path.lineTo(leanX, leanY);
            path.lineTo(endOffsetX, endOffsetY);

            linePaint.setPathEffect(new CornerPathEffect(dip2px(getContext(), 5)));

            if (circlePercentData.showLine) {
                canvas.drawPath(path, linePaint);
            }
        }
    }

    public CirclePercentView setCirclePercentDatas(List<CirclePercentData> circlePercentDatas) {
        this.circlePercentDatas = circlePercentDatas;
        int size = circlePercentDatas.size();
        currentProgress = new float[size];
        return this;
    }

    public void start() {
        cancel();
        if (circlePercentDatas.isEmpty()) {
            return;
        }
        final int size = circlePercentDatas.size();
        postInvalidate();
        circlePercentPointAnimator = ValueAnimator.ofInt(0, size);
        circlePercentPointAnimator.setDuration(size * 10);
        circlePercentPointAnimator.setInterpolator(new LinearInterpolator());
        circlePercentPointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circlePercentPointPos = (int) animation.getAnimatedValue();
                postInvalidate();
                if (circlePercentPointPos == size) {
                    circlePercentLineAnimator.start();
                }
            }
        });
        circlePercentPointAnimator.start();

        circlePercentLineAnimator = ValueAnimator.ofInt(0, size);
        circlePercentLineAnimator.setDuration(size * 10);
        circlePercentLineAnimator.setInterpolator(new LinearInterpolator());
        circlePercentLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circlePercentLinePos = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    public void onDestroy() {
        cancel();
        circlePercentPos = 0;
        circlePercentPointPos = 0;
        circlePercentLinePos = 0;

        rectF = null;
        circlePaint = null;//当前画笔
        pointPaint = null;
        linePaint = null;//线
        textPaint = null;//文字

        if (circlePercentDatas != null) {
            circlePercentDatas.clear();
        }
        circlePercentDatas = null;
    }

    public void cancel() {
        if (circlePercentPointAnimator != null) {
            circlePercentPointAnimator.cancel();
            circlePercentPointAnimator = null;
        }
        if (circlePercentLineAnimator != null) {
            circlePercentLineAnimator.cancel();
            circlePercentLineAnimator = null;
        }
    }

    public static class CirclePercentData {
        public String name;//科目类型
        public int resIdColor;//圆盘的颜色
        public float percentage;//百分比
        public String percentageName;//百分比
        public boolean showLine = true;//是否展示线

        public CirclePercentData(String name, int resIdColor, float percentage) {
            this.name = name;
            this.resIdColor = resIdColor;
            this.percentage = percentage < 0 ? 0 : percentage;
        }

        public CirclePercentData(String name, int resIdColor, float percentage, boolean showLine) {
            this.name = name;
            this.resIdColor = resIdColor;
            this.percentage = percentage < 0 ? 0 : percentage;
            this.showLine = showLine;
        }

        public CirclePercentData(String name, int resIdColor, float percentage, String percentageName, boolean showLine) {
            this.name = name;
            this.resIdColor = resIdColor;
            this.percentage = percentage < 0 ? 0 : percentage;
            this.percentageName = percentageName;
            this.showLine = showLine;
        }
    }

    /**
     * 提供构建方法创建
     *
     * @param name
     * @param name
     * @param percentage
     * @param percentageName
     * @return
     */
    public static CirclePercentData createCirclePercentData(String name, int resIdColor, float percentage, String percentageName, boolean showLine) {
        CirclePercentData circlePercentData = new CirclePercentData(name, resIdColor, percentage, percentageName, showLine);
        return circlePercentData;
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
