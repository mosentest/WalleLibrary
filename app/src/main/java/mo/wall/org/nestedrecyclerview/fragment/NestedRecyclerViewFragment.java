package mo.wall.org.nestedrecyclerview.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment;

import java.util.List;

import mo.wall.org.R;
import mo.wall.org.databinding.FragmentNestedRecyclerviewBinding;
import mo.wall.org.nestedrecyclerview.view.ChildRecyclerView;
import mo.wall.org.nestedrecyclerview.view.ParentRecyclerView;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-07 09:23
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedRecyclerViewFragment extends
        BaseMVPMaxLifecycleFragment<NestedRecyclerViewPresenter, FragmentNestedRecyclerviewBinding, Parcelable> implements
        NestedRecyclerViewContract.View {


    private NestedChildMultiItemQuickAdapter mChildMultiItemQuickAdapter = null;


    private String title;

    /**
     * @param args
     * @return
     */
    public static Fragment newInstance(Bundle args) {
        Fragment fragment = new NestedRecyclerViewFragment();
        if (args != null) {
            //args.putString(TAG, TAG);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            title = arguments.getString("title");
        }
    }

    @Override
    protected NestedRecyclerViewPresenter createPresenter() {
        return new NestedRecyclerViewPresenter();
    }

    @Override
    public void onFragmentFirstVisible() {

        mChildMultiItemQuickAdapter = new NestedChildMultiItemQuickAdapter(null);

        mViewDataBinding.childView.setAdapter(mChildMultiItemQuickAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getActivity(),
                2,
                RecyclerView.VERTICAL,
                false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                NestedChildMultiItemEntity nestedMultiItemEntity = mChildMultiItemQuickAdapter.getData().get(position);
                int itemType = nestedMultiItemEntity.getItemType();
                switch (itemType) {
                    case 1:
                        return 2;
                }
                return 1;
            }
        });
        mViewDataBinding.childView.setLayoutManager(gridLayoutManager);

        mPresenter.loadData();

        mViewDataBinding.name.setText(title);
    }

//    @Override
//    protected boolean setCurRetainInstance() {
//        return true;
//    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void loadIntentData(Bundle bundle) {

    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nested_recyclerview;
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
    public void showData(List<NestedChildMultiItemEntity> itemEntityList) {
        for (NestedChildMultiItemEntity nestedChildMultiItemEntity : itemEntityList) {
            nestedChildMultiItemEntity.title = title;
        }
        mChildMultiItemQuickAdapter.setNewData(itemEntityList);
    }


    /**
     * 为了获取对象
     *
     * @return
     */
    public ChildRecyclerView getChildRecyclerView() {
        if (mViewDataBinding != null) {
            return mViewDataBinding.childView;
        }
        return null;
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
