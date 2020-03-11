![image.png](https://upload-images.jianshu.io/upload_images/12139254-f65ea79969259ffd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
先上个图，代码不多，就一个类

```
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


    private ValueAnimator circlePercentAnimator = null;
    private ValueAnimator circlePercentPointAnimator = null;
    private ValueAnimator circlePercentLineAnimator = null;

    //当前位置
    private int circlePercentPos;
    private int circlePercentPointPos;
    private int circlePercentLinePos;

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
    }

    private void init() {
        circlePaint = new Paint();

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(dip2px(getContext(), 45));

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
        textPaint.setTextSize(sp2px(getContext(), 14));
        textPaint.setStrokeWidth(dip2px(getContext(), 2));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(rectF, pointPaint);
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
        float startAngle = -30;
        //遍历画占比
        for (int i = 0; i < circlePercentPos; i++) {
//        int i = pos;
            CirclePercentData circlePercentData = circlePercentDatas.get(i);
            float v = circlePercentData.num / totalNum * 360;

            circlePaint.setColor(circlePercentData.resIdColor);
            //画弧度
            canvas.drawArc(rectF,
                    startAngle,
                    v,
                    false,
                    circlePaint);

            //当前位置和
            startAngle = startAngle + v;
        }
    }


    /**
     * 这是画点
     *
     * @param canvas
     */
    private void drawCirclePercentPoint(Canvas canvas) {
        //改为作为成员变量
        float startAngle = -30;
        float preAngle = -30;
        float preV = 0f;

        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        float radius = (getWidth() - startX) / 2 - dip2px(getContext(), 30);

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
        float startAngle = -30;
        float preAngle = -30;
        float preV = 0f;

        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        float radius = (getWidth() - startX) / 2 - dip2px(getContext(), 30);

        //遍历画占比
        for (int i = 0; i < circlePercentLinePos; i++) {
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

            //下面是画线的处理
            //设置颜色
            linePaint.setColor(circlePercentData.resIdColor);


            float leanX = 0;
            float leanY = 0;
            float offsetX = 0;
            float offsetY = 0;
            Path path = new Path();
            path.moveTo(x, y);
            if (x > centerX) {

                //控制位置的对齐方向
                textPaint.setTextAlign(Paint.Align.RIGHT);

                //右边画线
                if (y > centerY) {

                    //设置一个倾向角度
                    leanX = x + dip2px(getContext(), 10);
                    leanY = y + dip2px(getContext(), 10);

                    offsetX = getWidth() - dip2px(getContext(), 16);
                    offsetY = y + dip2px(getContext(), 10);

                    //设置文字
                    //百分比
                    canvas.drawText(
                            df.format(circlePercentData.num / totalNum),
                            offsetX,
                            y,
                            textPaint);

                    //标题
//                    canvas.drawText(
//                            circlePercentData.name,
//                            offsetX,
//                            y + dip2px(getContext(), 30),
//                            textPaint);

                    //保存图层
                    canvas.save();

                    canvas.translate(offsetX, y + dip2px(getContext(), 20));

                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) (getWidth() - leanX),
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    myStaticLayout.draw(canvas);

                    //恢复图层
                    canvas.restore();

                } else {
                    //设置一个倾向角度
                    leanX = x + dip2px(getContext(), 10);
                    leanY = y - dip2px(getContext(), 10);

                    offsetX = getWidth() - dip2px(getContext(), 16);
                    offsetY = y - dip2px(getContext(), 10);

                    //设置文字
                    //百分比
                    canvas.drawText(
                            df.format(circlePercentData.num / totalNum),
                            offsetX,
                            y - dip2px(getContext(), 20),
                            textPaint);

                    //标题
//                    canvas.drawText(
//                            circlePercentData.name,
//                            offsetX,
//                            y + dip2px(getContext(), 10),
//                            textPaint);

                    //保存图层
                    canvas.save();

                    canvas.translate(offsetX, y);

                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) (getWidth() - leanX),
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    myStaticLayout.draw(canvas);

                    //恢复图层
                    canvas.restore();

                }

            } else {

                textPaint.setTextAlign(Paint.Align.LEFT);

                //左边画线
                if (y > centerY) {

                    //设置一个倾向角度
                    leanX = x - dip2px(getContext(), 10);
                    leanY = y + dip2px(getContext(), 10);

                    offsetX = 0 + dip2px(getContext(), 16);
                    offsetY = y + dip2px(getContext(), 10);


                    //设置文字
                    //百分比
                    canvas.drawText(
                            df.format(circlePercentData.num / totalNum),
                            offsetX,
                            y,
                            textPaint);

                    //标题
//                    canvas.drawText(
//                            circlePercentData.name,
//                            offsetX,
//                            y + dip2px(getContext(), 30),
//                            textPaint);
                    //保存图层
                    canvas.save();

                    canvas.translate(offsetX, y + dip2px(getContext(), 20));

                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) leanX,
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    myStaticLayout.draw(canvas);

                    //恢复图层
                    canvas.restore();

                } else {

                    //设置一个倾向角度
                    leanX = x - dip2px(getContext(), 10);
                    leanY = y - dip2px(getContext(), 10);

                    offsetX = 0 + dip2px(getContext(), 16);
                    offsetY = y - dip2px(getContext(), 10);

                    //设置文字
                    //百分比
                    canvas.drawText(
                            df.format(circlePercentData.num / totalNum),
                            offsetX,
                            y - dip2px(getContext(), 20),
                            textPaint);


                    //标题
//                    canvas.drawText(
//                            circlePercentData.name,
//                            offsetX,
//                            y + dip2px(getContext(), 10),
//                            textPaint);
                    //保存图层
                    canvas.save();

                    canvas.translate(offsetX, y);

                    //实现文字换行显示
                    StaticLayout myStaticLayout
                            = new StaticLayout(circlePercentData.name,
                            textPaint,
                            (int) leanX,
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);

                    myStaticLayout.draw(canvas);

                    //恢复图层
                    canvas.restore();
                }


            }

            path.lineTo(leanX, leanY);
            path.lineTo(offsetX, offsetY);

            canvas.drawPath(path, linePaint);
        }
    }

    public CirclePercentView setTotalNum(int totalNum) {
        this.totalNum = totalNum;
        return this;
    }

    public CirclePercentView setCirclePercentDatas(List<CirclePercentData> circlePercentDatas) {
        this.circlePercentDatas = circlePercentDatas;
        return this;
    }

    public void start() {
        cancel();
        if (circlePercentDatas.isEmpty()) {
            return;
        }
        circlePercentAnimator = ValueAnimator.ofInt(0, circlePercentDatas.size());
        circlePercentAnimator.setDuration(circlePercentDatas.size() * 100);
        circlePercentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                circlePercentPos = (int) animatedValue;
                postInvalidate();
                if (circlePercentPos == circlePercentDatas.size()) {
                    circlePercentPointAnimator.start();
                }
            }
        });

        circlePercentPointAnimator = ValueAnimator.ofInt(0, circlePercentDatas.size());
        circlePercentPointAnimator.setDuration(circlePercentDatas.size() * 100);
        circlePercentPointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                circlePercentPointPos = (int) animatedValue;
                postInvalidate();
                if (circlePercentPointPos == circlePercentDatas.size()) {
                    circlePercentLineAnimator.start();
                }
            }
        });

        circlePercentLineAnimator = ValueAnimator.ofInt(0, circlePercentDatas.size());
        circlePercentLineAnimator.setDuration(circlePercentDatas.size() * 100);
        circlePercentLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                circlePercentLinePos = (int) animatedValue;
                postInvalidate();
            }
        });

        circlePercentAnimator.start();
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
        if (circlePercentAnimator != null) {
            circlePercentAnimator.cancel();
            circlePercentAnimator = null;
        }
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

```
用法也很简单几行代码
```
mCpv = findViewById<CirclePercentView>(R.id.cpv)

        mCpv.setTotalNum(12)
                .setCirclePercentDatas(
                        Arrays.asList(
                                CirclePercentView.createCirclePercentData(1, "我是红色人,我是红色人", resources.getColor(R.color.google_red)),
                                CirclePercentView.createCirclePercentData(2, "我是绿色人,我是绿色人", resources.getColor(R.color.google_green)),
                                CirclePercentView.createCirclePercentData(3, "我是黄色人,我是黄色人,我是黄色人,我是黄色人", resources.getColor(R.color.google_yellow)),
                                CirclePercentView.createCirclePercentData(3, "我是蓝色人,我是蓝色人", resources.getColor(R.color.google_blue)),
                                CirclePercentView.createCirclePercentData(2, "我不知道自己干嘛，我不知道自己干嘛", resources.getColor(R.color.colorAccent)),
                                CirclePercentView.createCirclePercentData(1, "我好像是什么颜色,我好像是什么颜色", resources.getColor(R.color.colorPrimaryDark))))
                .start()
```
最后代码地址在[https://github.com/moz1q1/WalleLibrary](https://github.com/moz1q1/WalleLibrary)
