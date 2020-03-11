package mo.wall.org.screenshot;


import android.os.Bundle;
import android.content.Context;
import android.os.Message;

import mo.wall.org.databinding.FragmentScreenshotBinding;

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment;

import mo.wall.org.R;


public class ScreenshotFragment extends
        BaseMVPMaxLifecycleFragment<ScreenshotContract.Presenter, FragmentScreenshotBinding, ScreenshotAcceptPar>
        implements ScreenshotContract.View {

    public static ScreenshotFragment newInstance(Bundle args) {
        ScreenshotFragment fragment = new ScreenshotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ScreenshotContract.Presenter createPresenter() {
        return new ScreenshotPresenter();
    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_screenshot;
    }


    @Override
    public int getTopBarTitleViewId() {
        return 0;
    }

    @Override
    public int getTopBarBackViewId() {
        return 0;
    }

    @Override
    public void onFragmentFirstVisible() {

    }

    /**
     * 会通过activity的onIntent传值过来
     *
     * @param bundle
     */
    @Override
    public void loadIntentData(Bundle bundle) {

    }

    @Override
    public void handleSubMessage(Message msg) {

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
    public void onLoadDialogFail(int flag, Object failObj) {

    }

    @Override
    public void onLoadToastFail(int flag, Object failObj) {

    }


    @Override
    protected void onCurDestroy() {

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
