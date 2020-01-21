package mo.wall.org.dialog;


import android.os.Bundle;
import android.content.Context;
import android.os.Message;

import mo.wall.org.databinding.FragmentMyDialogBinding;

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment;

import mo.wall.org.R;


public class MyDialogFragment extends
        BaseMVPMaxLifecycleFragment<MyDialogContract.Presenter, FragmentMyDialogBinding, MyDialogAcceptPar>
        implements MyDialogContract.View {

    public static MyDialogFragment newInstance(Bundle args) {
        MyDialogFragment fragment = new MyDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MyDialogContract.Presenter createPresenter() {
        return new MyDialogPresenter();
    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_dialog;
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
