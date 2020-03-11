package mo.wall.org.screenshot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;

import org.wall.mo.base.activity.AbsWithV4FragmentActivity;

import mo.wall.org.databinding.ActivityScreenshotBinding;
import mo.wall.org.R;

public class ScreenshotActivity extends AbsWithV4FragmentActivity<ActivityScreenshotBinding, ScreenshotAcceptPar> {

    @Override
    public int getContainerViewId() {
        return R.id.container;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_screenshot;
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
        return ScreenshotFragment.newInstance(extras);
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
}
