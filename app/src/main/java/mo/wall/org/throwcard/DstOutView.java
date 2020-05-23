package mo.wall.org.throwcard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/17 4:52 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DstOutView extends View {
    private int mH;
    private int mW;


    private Paint mPaint = null;
    private PorterDuffXfermode mPorterDuffXfermode;

    public DstOutView(Context context) {
        super(context);
        init();
    }

    public DstOutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DstOutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DstOutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mH = getHeight();
        mW = getWidth();
    }

    /**
     * 教程：http://www.360doc.com/content/16/0705/15/21631240_573292156.shtml
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);
        /**
         * 设置View的离屏缓冲。在绘图的时候新建一个“层”，所有的操作都在该层而不会影响该层以外的图像
         * 必须设置，否则设置的PorterDuffXfermode会无效，具体原因不明
         */
        int sc = canvas.saveLayer(-mW / 2f, -mH / 2f, mW + mW / 2f, mH + mH / 2f, mPaint, Canvas.ALL_SAVE_FLAG);
        drawBackground(canvas);
        mPaint.setXfermode(mPorterDuffXfermode);
        drawHollowFileds(canvas);
        mPaint.setXfermode(null);
        /**
         * 还原画布，与canvas.saveLayer配套使用
         */
        canvas.restoreToCount(sc);
    }


    private void drawBackground(Canvas canvas) {
        mPaint.setColor(0x88000000);
        canvas.drawRect(0, 0, mW, mH, mPaint);
    }


    private void drawHollowFileds(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(-mW / 2f,
                    -mH / 2f,
                    mW + mW / 2f,
                    mH + mH / 2f,
                    -90,
                    360f * progress / 100f,
                    true,
                    mPaint);
        }
    }

    private int progress = 1;

    public void setProgress(int progress) {
        this.progress = progress;
        setVisibility(progress >= 100 ? GONE : VISIBLE);
        invalidate();
    }
}
