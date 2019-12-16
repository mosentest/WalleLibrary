package mo.wall.org.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者 create by moziqi on 2018/6/30
 * 邮箱 709847739@qq.com
 * 说明
 * https://www.cnblogs.com/ldq2016/p/7010402.html
 * https://www.cnblogs.com/wjtaigwh/p/6647114.html
 **/
public class LineChartView extends View {


    private final static String TAG = "LineChartView";

    PathMeasure pathMeasure = new PathMeasure();

    private Map<String, Integer> data = null;
    private List<Integer> yList = null;
    private List<String> xList = null;

    private Paint yPaint = null;
    private Paint xPaint = null;
    private Paint linePaint = null;
    private Paint pathPaint = null;
    private Paint normalWhilePointPaint = null;
    private Paint normalPointPaint = null;
    private Paint clickPointPaint = null;

    private Path path;

    private int w, h;
    private int yStartPoint;
    private int yStartTPoint;
    private int xLeftPadding;
    private int xCircleLeftPadding;
    private int xStartPoint;
    private int radius;
    private int radiusCenter;

    public LineChartView(Context context) {
        super(context);
        initData();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {

        yPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yPaint.setColor(Color.parseColor("#999999"));
        yPaint.setTextSize(dip2px(getContext(), 18));
        yPaint.setTextAlign(Paint.Align.LEFT);

        xPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xPaint.setColor(Color.parseColor("#999999"));
        xPaint.setTextSize(dip2px(getContext(), 18));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#999999"));
        linePaint.setTextSize(dip2px(getContext(), 20));

        normalPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalPointPaint.setColor(Color.parseColor("#303F9F"));
        normalPointPaint.setStrokeWidth(dip2px(getContext(), 3));
        normalPointPaint.setStyle(Paint.Style.STROKE);

        normalWhilePointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalWhilePointPaint.setColor(Color.parseColor("#FFFFFF"));
        normalWhilePointPaint.setStrokeWidth(dip2px(getContext(), 3));
        normalWhilePointPaint.setStyle(Paint.Style.FILL);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.parseColor("#303F9F"));
        pathPaint.setStrokeWidth(dip2px(getContext(), 3));
        pathPaint.setStyle(Paint.Style.STROKE);


        path = new Path();


        yList = new ArrayList<>();
        yList.add(0);
        yList.add(50);
        yList.add(100);
        yList.add(150);
        yList.add(200);


        xList = new ArrayList<>();
        xList.add("5-22");
        xList.add("5-23");
        xList.add("5-24");
        xList.add("5-25");
        xList.add("5-26");
        xList.add("5-27");
        xList.add("5-28");

        data = new HashMap<>();
        data.put("5-22", 47);
        data.put("5-23", 120);
        data.put("5-24", 90);
        data.put("5-25", 60);
        data.put("5-26", 80);
        data.put("5-27", 50);
        data.put("5-28", 100);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //View从API Level 11才加入setLayerType方法
            //关闭硬件加速
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        yStartPoint = dip2px(getContext(), 30);//y轴的起点位置
        yStartTPoint = dip2px(getContext(), 25);//y轴的起点位置
        xLeftPadding = dip2px(getContext(), 10);//y离x的距离
        xCircleLeftPadding = dip2px(getContext(), 16);//圆的偏差值
        xStartPoint = dip2px(getContext(), 55); //x轴的起点位置

        radius = dip2px(getContext(), 6);//圆半径
        radiusCenter = radius - dip2px(getContext(), 1);//圆半径
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = getWidth();
        h = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int ySize = yList.size();
        int xSize = xList.size();

        float yProp = h / ySize;
        Log.i(TAG, "yProp:" + yProp);
        float xFair = (w - xStartPoint) / xSize;

        for (int i = 0; i < ySize; i++) {
            //纵坐标
            float startTextY = yProp * (ySize - i) - yStartTPoint;
            float startLineY = yProp * (ySize - i) - yStartPoint;
            Log.i(TAG, "startTextY:" + startTextY);
            String yName = String.valueOf(yList.get(i));
            canvas.drawText(yName, xLeftPadding, startTextY, yPaint);
            //画对应的线
            canvas.drawLine(xStartPoint, startLineY, w - xLeftPadding, startLineY, linePaint);
        }

        float yUnit = yProp / 50f;

        Log.i(TAG, "yUnit:" + yUnit);
        List<Float> circleXs = new ArrayList<>();
        List<Float> circleYs = new ArrayList<>();
        for (int i = 0; i < xSize; i++) {
            //横坐标
            String xName = String.valueOf(xList.get(i));
            canvas.drawText(xName, xStartPoint + xFair * i, h, xPaint);
            Integer integer = data.get(xList.get(i));
            Log.i(TAG, "integer:" + integer);
            float circleX = xStartPoint + xCircleLeftPadding + xFair * i;
            float circleY = h - (yUnit * integer) - yStartPoint;
            Log.i(TAG, "circleX:" + circleX);
            Log.i(TAG, "cicrleY:" + circleY);
            circleXs.add(circleX);
            circleYs.add(circleY);
        }
        path.moveTo(circleXs.get(0), circleYs.get(0));
        for (int i = 0; i < circleXs.size() - 1; i++) {
            Float currentX = circleXs.get(i);
            Float currentY = circleYs.get(i);
            Float nextX = circleXs.get(i + 1);
            Float nextY = circleYs.get(i + 1);
            int compare = Float.compare(currentY, nextY);
            float controlX = (currentX + nextX) / 2; //控制点
            float controlY = 0; //控制点
            if (compare > 0) {
                controlY = currentY - nextY;
            } else {
                controlY = nextY + currentY / 2;
            }
            path.quadTo(controlX, controlY, nextX, nextY);
        }
        canvas.drawPath(path, pathPaint);
        //pathMeasure.setPath(path, true);
        path.close();
        for (int i = 0; i < circleXs.size(); i++) {
            //normalPointPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            canvas.drawCircle(circleXs.get(i), circleYs.get(i), radius, normalPointPaint);
            canvas.drawCircle(circleXs.get(i), circleYs.get(i), radiusCenter, normalWhilePointPaint);//用于消除交际部分，我菜只能这样做了
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
