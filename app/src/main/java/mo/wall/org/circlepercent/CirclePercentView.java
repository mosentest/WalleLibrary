package mo.wall.org.circlepercent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

import mo.wall.org.R;

/**
 * //https://www.jianshu.com/p/754b356239c1
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

    private int startY;

    private int startX;

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
        pointPaint.setColor(getResources().getColor(R.color.google_red));
        pointPaint.setStrokeWidth(30);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 0;

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 1, pointPaint);

        float radius = (getWidth() - startX) / 2 - 80;

        //遍历画占比
        for (int i = 0; i < circlePercentDatas.size(); i++) {
            CirclePercentData circlePercentData = circlePercentDatas.get(i);
            float v = circlePercentData.num / totalNum * 360;
            circlePaint.setColor(circlePercentData.resIdColor);

            Log.i(TAG, "onDraw.startAngle>>" + startAngle);

            canvas.drawArc(oval,
                    startAngle,
                    v,
                    false,
                    circlePaint);

            startAngle = startAngle + v;

            Log.i(TAG, "onDraw.v>>" + v);

            float x = getWidth() / 2 + (float) (Math.cos(v) * radius);
            float y = getHeight() / 2 + (float) (Math.sin(v) * radius);

            pointPaint.setColor(circlePercentData.resIdColor);
            canvas.drawCircle(x, y, 1, pointPaint);
        }

//        canvas.drawRect(oval, pointPaint);
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public void setCirclePercentDatas(List<CirclePercentData> circlePercentDatas) {
        this.circlePercentDatas = circlePercentDatas;
        invalidate();
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
