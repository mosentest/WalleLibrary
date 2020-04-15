package mo.wall.org.throwcard;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewPropertyAnimator;

import org.jetbrains.annotations.Nullable;

import mo.wall.org.R;
import mo.wall.org.base.BaseAppCompatActivity;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-10-25 17:36
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ThrowCardJavaActivity extends BaseAppCompatActivity {
    private View mView;
    private ViewPropertyAnimator viewPropertyAnimator;

    @Override
    public void handleMessageAct(@Nullable Message msg) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_throw_card);

        mView = findViewById(R.id.view);

        viewPropertyAnimator = mView.animate();

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPropertyAnimator
                        .rotation(-45f)
                        .translationX(-mView.getWidth() * 2)
                        .translationY(mView.getHeight())
                        .setDuration(10 * 1000)
                        .start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.setListener(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                viewPropertyAnimator.setUpdateListener(null);
            }
            viewPropertyAnimator.cancel();
        }

    }
}
