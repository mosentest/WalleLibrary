package mo.wall.org.view;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.wall.mo.utils.log.WLog


/**
 * author: mtdhllf
 * time  : 2018/07/03 11:49
 * desc  :
 */
class PaintView : View {

    var down = false
    var mPaint = Paint()
    //屏幕采集点
    var mPoints = ArrayList<Point>()
    //贝塞尔计算点
    var resultPoints = ArrayList<Point>()
    var mMidPoints = ArrayList<Point>()
    var mMidMidPoints = ArrayList<Point>()
    var mControlPoints = ArrayList<Point>()
    var mPath = Path()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10f
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec))
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!down) {
                    down = true
                    mPoints.clear()
                    WLog.e("PaintView","ACTION_DOWN: ${event.x},${event.y}")
                    invalidate()
                    return true
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if (down) {
                    mPoints.add(Point(event.x.toInt(), event.y.toInt()))
                    WLog.e("PaintView","ACTION_MOVE: ${event.x},${event.y}")
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (down) {
                    down = false
                    WLog.e("PaintView","ACTION_UP: ${event.x},${event.y}")
                    invalidate()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        /*if (mPoints.size > 2) {
            mPaint.color = Color.BLUE
            mPaint.style = Paint.Style.STROKE
            mPath.moveTo(mPoints[0].x.toFloat(), mPoints[0].y.toFloat())
            (1 until mPoints.size-1).forEach {
                mPath.quadTo(mPoints[it].x.toFloat(), mPoints[it].y.toFloat(), mPoints[it].x.toFloat(), mPoints[it].y.toFloat())
                canvas.drawPath(mPath, mPaint)
            }
            mPaint.color = Color.RED
            mPaint.style = Paint.Style.FILL
            mPoints.forEach {
                canvas.drawCircle(it.x.toFloat(), it.y.toFloat(),5f,mPaint)
            }
            mPath.reset()
        }*/
        if (mPoints.size>2) {
            initMidPoints()
            initMidMidPoints()
            initControlPoints(this.mPoints, this.mMidPoints , this.mMidMidPoints)
            //Path实现
            //drawBezier(canvas)
            //点计算实现
            drawBezierPoint(canvas)
        }

    }


    fun initMidPoints(){
        mMidPoints.clear()
        mPoints.forEachIndexed { index, point ->
            if (index == mPoints.size - 1) {
                return
            }else{
                mMidPoints.add(Point((point.x + mPoints[index+1].x)/2,(point.y + mPoints[index+1].y)/2))
            }
        }
    }

    fun initMidMidPoints() {
        mMidMidPoints.clear()
        mMidPoints.forEachIndexed { index, point ->
            if (index == mMidPoints.size - 1) {
                return
            }else{
                mMidMidPoints.add(Point((point.x + mMidPoints[index+1].x)/2,(point.y + mMidPoints[index+1].y)/2))
            }
        }
    }

    private fun initControlPoints(points: ArrayList<Point>, midPoints: ArrayList<Point>, midMidPoints: ArrayList<Point>) {
        mControlPoints.clear()
        for (i in 0 until points.size) {
            if (i == 0 || i == points.size - 1) {
                continue
            } else {
                val before = Point()
                val after = Point()
                before.x = points[i].x - midMidPoints[i - 1].x + midPoints[i - 1].x
                before.y = points[i].y - midMidPoints[i - 1].y + midPoints[i - 1].y
                after.x = points[i].x - midMidPoints[i - 1].x + midPoints[i].x
                after.y = points[i].y - midMidPoints[i - 1].y + midPoints[i].y
                mControlPoints.add(before)
                mControlPoints.add(after)
            }
        }
    }

    fun drawBezierPoint(canvas: Canvas) {
        resultPoints.clear()
        mPoints.forEachIndexed { i,point->
            when {

                i == 0 -> {
                    resultPoints.addAll(
                            createSecondBezier(30, point, mControlPoints[i], mPoints[i + 1])
                    )

                }
                i < mPoints.size - 2 -> {
                    resultPoints.addAll(
                            createThirdBezier(30, point, mControlPoints[2 * i - 1], mControlPoints[2 * i], mPoints[i + 1])
                    )

                }
                i == mPoints.size - 2 -> {
                    resultPoints.addAll(
                            createSecondBezier(30, point, mControlPoints[mControlPoints.size - 1], mPoints[i + 1])
                    )
                }

            }
        }
        var pointarray = ArrayList<Float>()
        resultPoints.forEach {
            pointarray.add(it.x.toFloat())
            pointarray.add(it.y.toFloat())
        }
        canvas.drawPoints(pointarray.toFloatArray(),mPaint)

    }

    /**
     * @desc 二阶贝塞尔曲线绘点
     * @param count 点的数量
     * @param p0 起始点
     *@param p1 控制点
     *@param p2 结束点
     */
    fun createSecondBezier(count: Int, p0: Point, p1: Point, p2: Point): ArrayList<Point> {

        val result = ArrayList<Point>()

        if (mPoints.size < 3 || count<1) {
            return result
        }

        (1 .. count).forEach {
            val t = 1f/count*it
            if (t > 1) {
                return@forEach
            }
            result.add(
                    Point().apply {
                        x = (p0.x * Math.pow(1.0 - t, 2.0) + 2 * t * (1 - t) * p1.x + p2.x * Math.pow(t.toDouble(), 2.0)).toInt()
                        y = (p0.y * Math.pow(1.0 - t, 2.0) + 2 * t * (1 - t) * p1.y + p2.y * Math.pow(t.toDouble(), 2.0)).toInt()
                    }
            )
        }
        return result
    }

    /**
     * @desc 三阶贝塞尔曲线绘点
     * @param count 点的数量
     * @param p0 起始点
     *@param p1 控制点
     *@param p2 控制点
     *@param p3 结束点
     */
    fun createThirdBezier(count: Int, p0: Point, p1: Point, p2: Point ,p3:Point): ArrayList<Point> {

        val result = ArrayList<Point>()

        if (mPoints.size < 3 || count<1) {
            return result
        }

        (1 .. count).forEach {
            val t = 1f/count*it
            if (t > 1) {
                return@forEach
            }
            result.add(
                    Point().apply {
                        x = (p0.x * Math.pow(1.0-t, 3.0) + 3.0 * p1.x * Math.pow(1.0-t, 2.0) * t + 3.0 * p2.x * Math.pow(t.toDouble(), 2.0) * (1-t) + p3.x * Math.pow(t.toDouble(), 3.0)).toInt()
                        y = (p0.y * Math.pow(1.0-t, 3.0) + 3.0 * p1.y * Math.pow(1.0-t, 2.0) * t + 3.0 * p2.y * Math.pow(t.toDouble(), 2.0) * (1-t) + p3.y * Math.pow(t.toDouble(), 3.0)).toInt()
                    }
            )
        }
        return result
    }

    fun drawBezier(canvas: Canvas){
        mPath.reset()
        mPoints.forEachIndexed { i, point ->
            when {
                i == 0 -> {
                    mPath.moveTo(point.x.toFloat(), point.y.toFloat())// 起点
                    mPath.quadTo(mControlPoints[i].x.toFloat(), mControlPoints[i].y.toFloat(),// 控制点
                            mPoints[i + 1].x.toFloat(), mPoints[i + 1].y.toFloat())
                }
                i < mPoints.size - 2 -> mPath.cubicTo(mControlPoints[2*i-1].x.toFloat(), mControlPoints[2*i-1].y.toFloat(),// 控制点
                        mControlPoints[2*i].x.toFloat(), mControlPoints[2*i].y.toFloat(),// 控制点
                        mPoints[i+1].x.toFloat(), mPoints[i+1].y.toFloat())// 终点
                i == mPoints.size - 2 -> {
                    mPath.moveTo(point.x.toFloat(), point.y.toFloat())// 起点
                    mPath.quadTo(mControlPoints[mControlPoints.size -1].x.toFloat(), mControlPoints[mControlPoints.size -1].y.toFloat(),
                            mPoints[i+1].x.toFloat(), mPoints[i+1].y.toFloat())// 终点
                }
            }
        }
        canvas.drawPath(mPath,mPaint)
    }



}
