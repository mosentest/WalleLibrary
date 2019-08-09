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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;
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
    private float totalNum;

    private List<CirclePercentData> circlePercentDatas;

    private RectF oval;

    private Paint circlePaint;//当前画笔

    private Paint pointPaint;

    private Paint linePaint;//线

    private Paint textPaint;//文字

    DecimalFormat df = new DecimalFormat("0%");

    private int startY;

    private int startX;


    private ValueAnimator valueAnimator = null;

    //当前位置
    private int pos;

    public CirclePercentView(Context context) {
        super(context);
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        startX = (getWidth() - getHeight() / 2) / 2;
        startY = getHeight() / 4;
        //设置画圆的空间
        if (oval == null) {
            oval = new RectF(startX, startY, getWidth() - startX, getHeight() - startY);
        }
        if (circlePaint == null) {
            circlePaint = new Paint();
        }
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(100);

        if (pointPaint == null) {
            pointPaint = new Paint();
        }
        pointPaint.setColor(getResources().getColor(R.color.mask_color));
        pointPaint.setStrokeWidth(30);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setAntiAlias(true);

        if (linePaint == null) {
            linePaint = new Paint();
        }
        linePaint.setColor(getResources().getColor(R.color.mask_color));
        linePaint.setStrokeWidth(8);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        if (textPaint == null) {
            textPaint = new Paint();
        }
        textPaint.setColor(getResources().getColor(R.color.mask_color));
        textPaint.setTextSize(35);
        textPaint.setStrokeWidth(2);
    }


    //改为作为成员变量
    float startAngle = -30;
    float preAngle = -30;
    float preV = 0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        //画圆心
//        canvas.drawCircle(centerX, centerY, 1, pointPaint);

        float radius = (getWidth() - startX) / 2 - 80;

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

            circlePaint.setColor(circlePercentData.resIdColor);
            //画弧度
            canvas.drawArc(oval,
                    startAngle,
                    v,
                    false,
                    circlePaint);

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

            Log.i(TAG, "onDraw.x>>" + x + ",y>>" + y);
            //设置颜色
            pointPaint.setColor(circlePercentData.resIdColor);
            //画出点的位置
            canvas.drawCircle(x, y, 1, pointPaint);

            //下面是画线的处理
            //设置颜色
            linePaint.setColor(circlePercentData.resIdColor);

            Path path = new Path();
            path.moveTo(x, y);
            if (x > centerX) {
                //右边画线
                path.lineTo(x + 200, y);
                //设置文字
                canvas.drawText(df.format(circlePercentData.num / totalNum), x + 160, y - 20, textPaint);
                canvas.drawText(circlePercentData.name, x + 160, y + 40, textPaint);
            } else {
                //左边画线
                path.lineTo(x - 200, y);
                //设置文字

                canvas.drawText(df.format(circlePercentData.num / totalNum), x - 200, y - 20, textPaint);
                canvas.drawText(circlePercentData.name, x - 200, y + 40, textPaint);
            }
            canvas.drawPath(path, linePaint);
        }

    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public void setCirclePercentDatas(List<CirclePercentData> circlePercentDatas) {
        this.circlePercentDatas = circlePercentDatas;
    }

    public void start() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
        //重置位置
        startAngle = -30;
        preAngle = -30;
        preV = 0f;
        if (circlePercentDatas.isEmpty()) {
            return;
        }
        valueAnimator = ValueAnimator.ofInt(0, circlePercentDatas.size() - 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                pos = (int) animatedValue;
                postInvalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
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
}
