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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_markerview);

        mTitle = findViewById(R.id.title);

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
        }
    }

    @Override
    public void handleMessageAct(@Nullable Message msg) {

    }

}
