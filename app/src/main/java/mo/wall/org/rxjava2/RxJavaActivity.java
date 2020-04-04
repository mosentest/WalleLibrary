package mo.wall.org.rxjava2;

import android.os.Bundle;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;

import org.wall.mo.base.activity.AbsWithV4FragmentActivity;

import mo.wall.org.databinding.ActivityRxJavaBinding;
import mo.wall.org.R;
import mo.wall.org.rxjava2.data.RxJavaAcceptPar;

public class RxJavaActivity extends AbsWithV4FragmentActivity<ActivityRxJavaBinding, RxJavaAcceptPar> {

    @Override
    public int getContainerViewId() {
        return R.id.container;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_java;
    }


    @Override
    public int getTopBarTitleViewId() {
        return R.id.tvTopBarTitle;
    }

    @Override
    public int getTopBarBackViewId() {
        return R.id.tvTopBarLeftBack;
    }

    @Override
    public Fragment createFragment() {
        Intent intent = getIntent();
        Bundle extras = null;
        if (intent != null) {
            extras = intent.getExtras();
        }
        return RxJavaFragment.newInstance(extras);
    }

    @Override
    public void loadIntentData(Intent intent) {

    }

    @Override
    public void handleSubMessage(Message msg) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showShortToast(String msg) {

    }

    @Override
    public void showLongToast(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
