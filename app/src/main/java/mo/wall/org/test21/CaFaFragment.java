package mo.wall.org.test21;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment;

import mo.wall.org.R;
import mo.wall.org.databinding.FragmentCaFaBinding;


public class CaFaFragment extends
        BaseMVPMaxLifecycleFragment<CaFaContract.View,
                CaFaContract.Presenter, FragmentCaFaBinding, CaFaAcceptPar>
        implements CaFaContract.View {

    public static CaFaFragment newInstance(Bundle args) {
        CaFaFragment fragment = new CaFaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected CaFaContract.Presenter createPresenter() {
        return new CaFaPresenter();
    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ca_fa;
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
