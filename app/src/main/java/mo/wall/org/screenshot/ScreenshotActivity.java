package mo.wall.org.screenshot;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.wall.mo.base.activity.AbsWithV4FragmentActivity;
import org.wall.mo.utils.screenshot.Screenshot21;

import mo.wall.org.databinding.ActivityScreenshotBinding;
import mo.wall.org.R;
import mo.wall.org.screenshot.service.MyAccessibilityService;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Screenshot21.getInstance().requestMediaProject(this, null, 100);
        }
        MyAccessibilityService.getInstance().init(this);
        MyAccessibilityService.getInstance().goAccess();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (mViewDataBinding != null) {
            mViewDataBinding.img.setOnClickListener(v -> startCapture());
        }
        if (mViewDataBinding != null) {
            mViewDataBinding.tvShot.setOnClickListener(v -> {
                mViewDataBinding.img.setImageBitmap(getScreenShotBmp());
            });
        }
    }

    /**
     * 获取屏幕截图
     */
    private Bitmap getScreenShotBmp() {
        View decorView = getWindow().getDecorView();    //获取当前activity所在的最顶层的view--DecorView
        decorView.setDrawingCacheEnabled(true);         //启用绘图缓存
        decorView.buildDrawingCache();                  //强制构建绘图缓存（防止上面启用绘图缓存的操作失败）
        Bitmap bitmap = decorView.getDrawingCache();     //获取绘图缓存中的 bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        decorView.setDrawingCacheEnabled(false);    //createBitmap完成之后一定要置为false，否则短时间内多次截图时内容不会变化！
        return bitmap;
    }

    private void startCapture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap screenshot = Screenshot21.getInstance().screenshot();
            mViewDataBinding.img.setImageBitmap(screenshot);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Screenshot21.getInstance().onActivityResult(this, requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Screenshot21.getInstance().release();
        }
        super.onDestroy();
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
    public void showInfoDialog(String msg) {

    }

    @Override
    public void statusLoadingView() {

    }

    @Override
    public void statusNetWorkView() {

    }

    @Override
    public void statusErrorView(int type, String msg) {

    }

    @Override
    public void statusContentView() {

    }
}
