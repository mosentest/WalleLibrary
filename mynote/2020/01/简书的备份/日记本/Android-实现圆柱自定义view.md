来上来一个图,gif懒得搞，就静态图片了
![image.png](https://upload-images.jianshu.io/upload_images/12139254-48b7a30ec76e6571.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/12139254-00e2ee6bc3183300.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


然后代码
```
package mo.wall.org.circlepercent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import mo.wall.org.R;

/**
 * 参考
 * <p>
 * https://blog.csdn.net/zhcswlp0625/article/details/70342029
 * <p>
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/8/10 1:17 PM
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ColumnarView extends View {


    private final static String TAG = ColumnarView.class.getSimpleName();

    private float totalCount;

    private List<ColumnarData> columnarDatas;

    private TextPaint textPaint;//文字画笔

    private Paint columnarPaint;//柱状画笔


    private int mWidth;

    private int mHeight;

    private int[] currentBarProgress;
    private int[] currentCount;


    public ColumnarView(Context context) {
        super(context);
        init();
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {

        columnarPaint = new Paint();

        columnarPaint.setStyle(Paint.Style.FILL);
        columnarPaint.setAntiAlias(true);
        columnarPaint.setStrokeWidth(dip2px(getContext(), 1));
        columnarPaint.setStrokeCap(Paint.Cap.ROUND);
        columnarPaint.setStrokeJoin(Paint.Join.ROUND);

        textPaint = new TextPaint();

        textPaint.setColor(getResources().getColor(R.color.mask_color));
        textPaint.setTextSize(sp2px(getContext(), 14));
        textPaint.setStrokeWidth(dip2px(getContext(), 1));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    /**
     * ==================================
     * =======
     * =======
     * =======
     * ==================================
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int startLeftOffset = dip2px(getContext(), 16);
        int spacing = dip2px(getContext(), 12);
        int columnarHeight = dip2px(getContext(), 40);

        int left = startLeftOffset + mWidth / 4;

        int top = 0;

        for (int i = 0; i < columnarDatas.size(); i++) {


            top = startLeftOffset + columnarHeight * i + spacing * i;

            ColumnarData columnarData = columnarDatas.get(i);

            canvas.save();

            canvas.translate(startLeftOffset, top + dip2px(getContext(), 5));
            //实现文字换行显示
            StaticLayout myStaticLayout
                    = new StaticLayout(columnarData.name,
                    textPaint,
                    left,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false);

            myStaticLayout.draw(canvas);

            //恢复图层
            canvas.restore();


            columnarPaint.setColor(columnarData.resIdColor);

            if (currentBarProgress[i] < (mWidth - (mWidth / 2 + startLeftOffset)) * columnarData.count / totalCount) {
                currentBarProgress[i] += 5;
                postInvalidateDelayed(10);
            }
            //这是画圆柱
            //float left, float top, float right, float bottom, @NonNull Paint paint
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(left + startLeftOffset,
                        top,
                        left + startLeftOffset + currentBarProgress[i],
                        columnarHeight + top,
                        (float) dip2px(getContext(), 2),
                        (float) dip2px(getContext(), 2),
                        columnarPaint);
            } else {
                canvas.drawRect(left + startLeftOffset,
                        top,
                        left + startLeftOffset + currentBarProgress[i],
                        columnarHeight + top,
                        columnarPaint);
            }

            if (currentCount[i] < (int) columnarData.count) {
                currentCount[i] += 1;
                postInvalidateDelayed(20);
            }
            //这是画字体
            canvas.drawText(String.format("%d次", currentCount[i]),
                    left + startLeftOffset + currentBarProgress[i] + startLeftOffset,
                    top + dip2px(getContext(), 25), textPaint);
        }
    }

    public ColumnarView setTotalCount(float totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public void setColumnarDatas(List<ColumnarData> columnarDatas) {
        this.columnarDatas = columnarDatas;
        int size = columnarDatas.size();
        currentBarProgress = new int[size];
        currentCount = new int[size];
        postInvalidate();
    }

    public static class ColumnarData {
        public String name;//左边名字
        public float count;//数量
        public int resIdColor;//柱状的颜色

        public ColumnarData(String name, float count, int resIdColor) {
            this.name = name;
            this.count = count;
            this.resIdColor = resIdColor;
        }

    }

    /**
     * 创建数据
     *
     * @param name
     * @param count
     * @param resIdColor
     * @return
     */
    public static ColumnarData createColumnarData(String name, float count, int resIdColor) {
        return new ColumnarData(name, count, resIdColor);
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

用法很简单
```
 mCv.setTotalCount(100f).setColumnarDatas(
                Arrays.asList(
                        ColumnarView.createColumnarData("1我爱中国，爱共产党，我爱中国", 30f, resources.getColor(R.color.google_red)),
                        ColumnarView.createColumnarData("2我爱中国，爱共产党，我爱中国", 40f, resources.getColor(R.color.google_green)),
                        ColumnarView.createColumnarData("3我爱中国，爱共产党，我爱中国", 50f, resources.getColor(R.color.google_yellow)),
                        ColumnarView.createColumnarData("4我爱中国，爱共产党，我爱中国", 60f, resources.getColor(R.color.google_blue)),
                        ColumnarView.createColumnarData("5我爱中国，爱共产党，我爱中国", 50f, resources.getColor(R.color.colorAccent)),
                        ColumnarView.createColumnarData("6我爱中国，爱共产党，我爱中国", 100f, resources.getColor(R.color.colorPrimaryDark))
                )
        );
```

最后代码地址：[https://github.com/moz1q1/WalleLibrary](https://github.com/moz1q1/WalleLibrary)

截图如下，点击这个按钮可以看
![image.png](https://upload-images.jianshu.io/upload_images/12139254-c47e656c6c4bca07.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
