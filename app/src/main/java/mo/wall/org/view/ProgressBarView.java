package mo.wall.org.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * 作者 create by moziqi on 2018/7/10
 * 邮箱 709847739@qq.com
 * 说明
 * https://www.jb51.net/article/108318.htm
 **/
public class ProgressBarView extends View {

    PathMeasure mPathMeasure = null;

    Path mPath = new Path();
    Path mDst = new Path();

    private float mPathLength;
    private float mRadius = 100;
    private float mPathPercent;
    private int mAnimDuration = 2000;
    private boolean mIsLoading;
    private Paint mPaint;


    public ProgressBarView(Context context) {
        super(context);
        init();
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * https://blog.csdn.net/xmxkf/article/details/51490283
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mPath.addCircle(w / 2, h / 2, mRadius, Path.Direction.CW);
        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);
        mPathLength = mPathMeasure.getLength();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#999999"));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        startAnim();
    }

    private void startAnim() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setDuration(mAnimDuration);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPathPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();

        //再加一个旋转动画以及两倍的时长，形成旋转视差
        ObjectAnimator animRotate = ObjectAnimator.ofFloat(this, View.ROTATION, 0, 360);
        animRotate.setInterpolator(new LinearInterpolator());
        animRotate.setRepeatCount(ValueAnimator.INFINITE);
        animRotate.setDuration(2 * mAnimDuration);
        animRotate.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float stop = mPathLength * mPathPercent;
        float start = (float) (stop - ((0.5 - Math.abs(mPathPercent - 0.5)) * mPathLength * 4));
        mDst.reset();
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }


    public void start() {
        mIsLoading = true;
        setVisibility(View.VISIBLE);
        startAnim();
    }

    public void stop() {
        mIsLoading = false;
        setVisibility(View.GONE);
    }

    public boolean isLoading() {
        return mIsLoading;
    }
}
