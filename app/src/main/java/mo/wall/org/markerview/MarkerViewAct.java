package mo.wall.org.markerview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import mo.wall.org.R;
import mo.wall.org.base.BaseAppCompatActivity;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-06 19:51
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MarkerViewAct extends BaseAppCompatActivity {
    private TextView mTitle;

    private ValueAnimator textZoom;
    private PolylineView mPlv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_markerview);

        mTitle = findViewById(R.id.title);
        mPlv = findViewById(R.id.plv);

        mTitle.setTextSize(14);
        textZoom = ValueAnimator.ofInt(0, 100);
        textZoom.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            //14->18
            mTitle.setTextSize(14 + 4 * animatedValue / 100);
        });
        textZoom.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTitle.setTextSize(14);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mTitle.setTextSize(18);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mTitle.setTextSize(18);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mTitle.setOnClickListener(v -> textZoom.start());


        List<String> yHearts = new ArrayList<>();


        List<PolylineView.PolylineData> x = new ArrayList<>();
        List<PolylineView.PolylineData> x1 = new ArrayList<>();

        yHearts.add("300");
        yHearts.add("400");
        yHearts.add("500");
        yHearts.add("600");
        yHearts.add("700");
        yHearts.add("800");

        x.add(new PolylineView.PolylineData.Builder().date("11-14").time("11:20").value("300").build());
        x.add(new PolylineView.PolylineData.Builder().date("11-15").time("11:20").value("309").build());
        x.add(new PolylineView.PolylineData.Builder().date("11-14").time("11:20").value("510").build());
        x.add(new PolylineView.PolylineData.Builder().date("11-16").time("11:20").value("510").build());
        x.add(new PolylineView.PolylineData.Builder().date("11-17").time("11:20").value("760").build());
        x.add(new PolylineView.PolylineData.Builder().date("11-18").time("11:20").value("390").build());
        x.add(new PolylineView.PolylineData.Builder().date("11-19").time("11:20").value("620").build());
        x.add(new PolylineView.PolylineData.Builder().date("11-20").time("11:20").value("590").build());


        x1.add(new PolylineView.PolylineData.Builder().date("11-14").time("11:20").value("310").build());
        x1.add(new PolylineView.PolylineData.Builder().date("11-15").time("11:20").value("300").build());
        x1.add(new PolylineView.PolylineData.Builder().date("11-14").time("11:20").value("333").build());
        x1.add(new PolylineView.PolylineData.Builder().date("11-16").time("11:20").value("400").build());
        x1.add(new PolylineView.PolylineData.Builder().date("11-17").time("11:20").value("500").build());
        x1.add(new PolylineView.PolylineData.Builder().date("11-18").time("11:20").value("300").build());
        x1.add(new PolylineView.PolylineData.Builder().date("11-19").time("11:20").value("450").build());
        x1.add(new PolylineView.PolylineData.Builder().date("11-20").time("11:20").value("590").build());

        mPlv.setData(yHearts, x, x1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /**
         * An app might need to continue its operation while it is not topmost.
         * For example, a video-playing app in this state should continue showing its video.
         * For this reason, we recommend that activities that play video
         * not pause video playback in response to the ON_PAUSE lifecycle event.
         * Instead, the activity should begin playback in response to ON_START,
         * and pause playback in response to ON_STOP.
         * If you handle the lifecycle events directly instead of using the Lifecycle package,
         * pause video playback in your onStop() handler, and resume playback in onStart().
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            textZoom.pause();
        } else {
            textZoom.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    /**
     * https://www.jianshu.com/p/964d7667a0f3
     */
    public void release() {
        if (textZoom != null) {
            textZoom.cancel();
            textZoom.removeAllUpdateListeners();
            textZoom.removeAllListeners();
        }
    }

    @Override
    public void handleMessageAct(@Nullable Message msg) {

    }

}
