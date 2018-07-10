package mo.wall.org.view;

import android.graphics.Point;

/**
 * 作者 create by moziqi on 2018/7/10
 * 邮箱 709847739@qq.com
 * 说明https://blog.csdn.net/u012124438/article/details/75949057
 * https://www.cnblogs.com/wjtaigwh/p/6647114.html
 **/
public class PointUtils {

    /**
     * 计算中心点
     *
     * @param pointStart
     * @param pointEnd
     * @return
     */
    public static Point calcCenterPoint(Point pointStart, Point pointEnd) {
        Point center = new Point();
        center.x = (pointStart.x + pointEnd.x) / 2;
        center.y = (pointStart.y + pointEnd.y) / 2;
        return center;
    }


    /**
     * 二阶 经过的点
     *
     * @param startValue
     * @param controlPoint 控制点
     * @param endValue
     * @param t
     * @return
     */
    public static Point calcSecondBezier(Point startValue, Point controlPoint, Point endValue, int t) {
        int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controlPoint.x + t * t * endValue.x);
        int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controlPoint.y + t * t * endValue.y);
        return new Point(x, y);
    }

    /**
     * 三阶 经过的点
     *
     * @param startValue
     * @param controlPoint1 控制点
     * @param controlPoint2 控制点
     * @param endValue
     * @param t
     * @return
     */
    public static Point calcThirdBezier(Point startValue, Point controlPoint1, Point controlPoint2, Point endValue, int t) {
        int x = (int) (Math.pow((1 - t), 3) * startValue.x
                + 3 * t * Math.pow((1 - t), 2) * controlPoint1.x
                + 3 * t * t * Math.pow((1 - t), 1) * controlPoint2.x
                + t * t * t * endValue.x);
        int y = (int) (Math.pow((1 - t), 3) * startValue.y
                + 3 * t * Math.pow((1 - t), 2) * controlPoint1.y
                + 3 * t * t * Math.pow((1 - t), 1) * controlPoint2.y
                + t * t * t * endValue.y);
        return new Point(x, y);
    }

}
