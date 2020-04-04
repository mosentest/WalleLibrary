package mo.wall.org.rxjava2;


import android.os.Bundle;
import android.content.Context;
import android.os.Message;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import mo.wall.org.databinding.FragmentRxJavaBinding;

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment;
import org.wall.mo.utils.log.WLog;
import org.wall.mo.utils.thread.CacheThreadExecutor;
import org.wall.mo.utils.thread.ExRunnable;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import mo.wall.org.R;
import mo.wall.org.rxjava2.api.RxjavaLearn;
import mo.wall.org.rxjava2.data.RxJavaAcceptPar;


public class RxJavaFragment extends
        BaseMVPMaxLifecycleFragment<RxJavaContract.Presenter, FragmentRxJavaBinding, RxJavaAcceptPar>
        implements RxJavaContract.View {


    public static RxJavaFragment newInstance(Bundle args) {
        RxJavaFragment fragment = new RxJavaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RxJavaViewModel mRxJavaViewModel;

    private BaseQuickAdapter<RxJavaAcceptPar, BaseViewHolder> mAdapter;

    @Override
    protected RxJavaContract.Presenter createPresenter() {
        return new RxJavaPresenter();
    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rx_java;
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
        mAdapter = new BaseQuickAdapter<RxJavaAcceptPar, BaseViewHolder>(R.layout.fragment_rx_java_item, null) {
            @Override
            protected void convert(BaseViewHolder helper, RxJavaAcceptPar item) {
                helper.setText(R.id.message, item.name);
            }
        };
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getActivity() != null) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.sticky_shadow_default));
            mViewDataBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        }
        mAdapter.bindToRecyclerView(mViewDataBinding.recyclerView);

        mRxJavaViewModel = ViewModelProviders.of(this).get(RxJavaViewModel.class);

        mRxJavaViewModel.loadData(true);

        mRxJavaViewModel.mStatus.observe(getViewLifecycleOwner(), integer -> {
            switch (integer) {
                case 1:
                    mViewDataBinding.message.setText("正在加载");
                    mViewDataBinding.message.setVisibility(View.VISIBLE);
                    mViewDataBinding.recyclerView.setVisibility(View.GONE);
                    break;
                case 2:
                    mViewDataBinding.message.setVisibility(View.GONE);
                    mViewDataBinding.recyclerView.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mViewDataBinding.message.setText("请求失败");
                    mViewDataBinding.message.setVisibility(View.VISIBLE);
                    mViewDataBinding.recyclerView.setVisibility(View.GONE);
                    break;
            }
        });
        mRxJavaViewModel.mListLiveData.observe(getViewLifecycleOwner(), rxJavaAcceptPars -> {
            mAdapter.setNewData(rxJavaAcceptPars);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                Method declaredMethod = RxjavaLearn.class.getDeclaredMethod(mAdapter.getData().get(position).name);
                declaredMethod.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
