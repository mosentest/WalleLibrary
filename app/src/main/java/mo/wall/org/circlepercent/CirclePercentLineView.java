package mo.wall.org.circlepercent;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
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
 * c参考这个
 * https://www.cnblogs.com/yishujun/p/5560838.html
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class CirclePercentLineView extends View {


    private final static String TAG = CirclePercentLineView.class.getSimpleName();

    /**
     * 总数
     */
    private float totalNum;

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

    private int[] currentProgress;


    private final static float START_ANGLE = -90;

    private int centerX;
    private int centerY;
    private int radius;

    private int qianxiejiaoduY;
    private int qianxiejiaoduX;

    private int textNeedHeight;

    private int paddingLeftAndRight;

    private int textLeftRight;

    private int position = 0;

    private int textSp;//字体大小也算一个高度

    public CirclePercentLineView(Context context) {
        super(context);
        init();
    }

    public CirclePercentLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePercentLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CirclePercentLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
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
        Log.e("YView", "---minimumWidth = " + minimumWidth + "");
        Log.e("YView", "---minimumHeight = " + minimumHeight + "");
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("YViewWidth", "---speSize = " + specSize + "");


        switch (specMode) {
            //wrap_content
            case MeasureSpec.AT_MOST:
                defaultWidth = specSize;

                Log.e("YViewWidth", "---speMode = AT_MOST");
                break;
            //match_parent
            case MeasureSpec.EXACTLY:
                Log.e("YViewWidth", "---speMode = EXACTLY");
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e("YViewWidth", "---speMode = UNSPECIFIED");
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("YViewHeight", "---speSize = " + specSize + "");

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = specSize;
                Log.e("YViewHeight", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                Log.e("YViewHeight", "---speSize = EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                Log.e("YViewHeight", "---speSize = UNSPECIFIED");
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
        radius = (Math.min(getWidth(), getHeight()) - Math.min(startX, startY)) / 2 - dip2px(getContext(), 10);
        //设置倾斜角度的距离
        qianxiejiaoduY = dip2px(getContext(), 14);
        qianxiejiaoduX = dip2px(getContext(), 14);
        //文字上下的距离
        textNeedHeight = dip2px(getContext(), 4);
        //左右内边距
        paddingLeftAndRight = dip2px(getContext(), 16);
        //下面字体离左右的距离
        textLeftRight = dip2px(getContext(), 20);

        textSp = sp2px(getContext(), 13);
        if (textPaint != null) {
            textPaint.setTextSize(textSp);
        }
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
        textPaint.setTextSize(textSp);
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
//        if (circlePercentPos == circlePercentDatas.size() - 1) {
//            return;
//        }
        //改为作为成员变量
        float startAngle = START_ANGLE;
        //遍历画占比
        for (int i = 0; i < circlePercentDatas.size(); i++) {
//        int i = pos;
            CirclePercentLineView.CirclePercentData circlePercentData = circlePercentDatas.get(i);
            int v = (int) (circlePercentData.num / totalNum * 360);
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
//        int i = pos;
            CirclePercentData circlePercentData = circlePercentDatas.get(i);
            float v = circlePercentData.num / totalNum * 360;
            //获取前一个
            if (i > 0) {
                CirclePercentData preCirclePercentData = circlePercentDatas.get(i - 1);
                preV = preCirclePercentData.num / totalNum * 360;
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
            canvas.drawCircle(x, y, dip2px(getContext(), 1), pointPaint);
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

        int baseTopTextHeight = textSp + getPaddingTop(); //基础上线
        int nextRightTopTextHeight = baseTopTextHeight;
        int baseLefBottomTextHeight = getHeight() - textSp - getPaddingBottom();//基础下线
        int nextLeftBottomTextHeight = baseLefBottomTextHeight;

        //遍历画占比
        for (int i = 0; i < circlePercentDatas.size(); i++) {
//        int i = pos;
            CirclePercentData circlePercentData = circlePercentDatas.get(i);
            float v = circlePercentData.num / totalNum * 360;
            //获取前一个
            if (i > 0) {
                CirclePercentData preCirclePercentData = circlePercentDatas.get(i - 1);
                preV = preCirclePercentData.num / totalNum * 360;
            }
            //获取前一个位置和
            preAngle = preAngle + preV;

            //当前位置和
            startAngle = startAngle + v;

            //获取弧度中心位置
            float halfAngle = (startAngle - preAngle) / 2 + preAngle;

            Log.i(TAG, "onDraw.preAngle>>" + preAngle + ",startAngle>>" + startAngle + ",halfAngle>>" + halfAngle);

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

            if (x > centerX) {
                //控制位置的对齐方向
                textPaint.setTextAlign(Paint.Align.RIGHT);
                //设置一个倾向角度
                leanX = x + qianxiejiaoduX + dip2px(getContext(), 2) * (i + 1);
                leanY = nextRightTopTextHeight;

                endOffsetX = getWidth() - paddingLeftAndRight;
                endOffsetY = nextRightTopTextHeight;

                textPaint.setColor(getResources().getColor(R.color.color_333333));
                //设置文字
                //百分比
                canvas.drawText(
                        df.format(circlePercentData.num / totalNum),
                        endOffsetX,
                        leanY - textNeedHeight,
                        textPaint);

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

                myStaticLayout.draw(canvas);

                //恢复图层
                canvas.restore();
                //累加高度
                nextRightTopTextHeight = nextRightTopTextHeight
                        + baseTopTextHeight + baseTopTextHeight * myStaticLayout.getLineCount() + textNeedHeight * (i + 2);

            } else {

                textPaint.setTextAlign(Paint.Align.LEFT);
                //设置一个倾向角度
                leanX = x - dip2px(getContext(), 2) * (i + 1);

                //实现文字换行显示
                StaticLayout myStaticLayout
                        = new StaticLayout(circlePercentData.name,
                        textPaint,
                        (int) leanX - textLeftRight,
                        Layout.Alignment.ALIGN_NORMAL,
                        1.0f,
                        0.0f,
                        false);

                nextLeftBottomTextHeight = nextLeftBottomTextHeight - textSp * myStaticLayout.getLineCount();

                leanY = nextLeftBottomTextHeight;

                endOffsetX = 0 + paddingLeftAndRight;
                endOffsetY = nextLeftBottomTextHeight;

                textPaint.setColor(getResources().getColor(R.color.color_333333));
                //设置文字
                //百分比
                canvas.drawText(
                        df.format(circlePercentData.num / totalNum),
                        endOffsetX,
                        leanY - textNeedHeight,
                        textPaint);

                //保存图层
                canvas.save();

                canvas.translate(endOffsetX, leanY + textNeedHeight);

                textPaint.setColor(getResources().getColor(R.color.color_999999));

                myStaticLayout.draw(canvas);

                //恢复图层
                canvas.restore();

                //累加高度
                nextLeftBottomTextHeight = nextLeftBottomTextHeight  - textNeedHeight * (i + 1);
            }

            path.lineTo(leanX, leanY);
            path.lineTo(endOffsetX, endOffsetY);

            canvas.drawPath(path, linePaint);
        }
    }

    public CirclePercentLineView setTotalNum(int totalNum) {
        this.totalNum = totalNum;
        return this;
    }

    public CirclePercentLineView setCirclePercentDatas(List<CirclePercentData> circlePercentDatas) {
        if (circlePercentDatas == null || circlePercentDatas.isEmpty()) {
            return this;
        }
        this.circlePercentDatas.clear();
        this.circlePercentDatas.addAll(circlePercentDatas);
        int size = this.circlePercentDatas.size();
        currentProgress = new int[size];
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
        circlePercentPointAnimator.setDuration(size * 100);
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
        circlePercentLineAnimator.setDuration(size * 100);
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
//        if (circlePercentAnimator != null) {
//            circlePercentAnimator.cancel();
//            circlePercentAnimator = null;
//        }
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
        public float num;//当前的数量
        public String name;//科目类型
        public int resIdColor;//圆盘的颜色

        public CirclePercentData(int num, String name, int resIdColor) {
            this.num = num;
            this.name = name;
            this.resIdColor = resIdColor;
        }
    }

    /**
     * 提供构建方法创建
     *
     * @param num
     * @param name
     * @param resIdColor
     * @return
     */
    public static CirclePercentData createCirclePercentData(int num, String name, int resIdColor) {
        CirclePercentData circlePercentData = new CirclePercentData(num, name, resIdColor);
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
