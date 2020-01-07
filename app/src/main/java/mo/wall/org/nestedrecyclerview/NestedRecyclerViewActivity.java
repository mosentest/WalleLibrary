package mo.wall.org.nestedrecyclerview;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.wall.mo.base.mvp.BaseMVPAppCompatActivity;

import java.util.List;

import mo.wall.org.R;
import mo.wall.org.databinding.ActivityNestedRecyclerviewBinding;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 20:34
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedRecyclerViewActivity extends
        BaseMVPAppCompatActivity<NestedRecyclerViewPresenter, ActivityNestedRecyclerviewBinding>
        implements NestedRecyclerViewContract.View {

    NestedMultiItemQuickAdapter multiItemQuickAdapter;

    @Override
    public NestedRecyclerViewPresenter createPresenter() {
        return new NestedRecyclerViewPresenter();
    }

    @Override
    public void onCurDestroy() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nested_recyclerview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewDataBinding.topView.tvTopBarLeftBack.setVisibility(View.VISIBLE);
        mViewDataBinding.topView.tvTopBarLeftBack.setOnClickListener(v -> {
            onBackPressed();
        });
        mViewDataBinding.topView.tvTopBarTitle.setText("NestedRecyclerView");


        multiItemQuickAdapter = new NestedMultiItemQuickAdapter(null);
        mViewDataBinding.parentView.setAdapter(multiItemQuickAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,
                12,
                RecyclerView.VERTICAL,
                false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                NestedMultiItemEntity nestedMultiItemEntity = multiItemQuickAdapter.getData().get(position);
                int itemType = nestedMultiItemEntity.getItemType();
                switch (itemType) {
                    case 1:
                    case 3:
                    case 5:
                        return 12;

                    case 4:
                        return 4;
                }
                return 3;
            }
        });
        mViewDataBinding.parentView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void parseIntentData() {

    }

    @Override
    public void handleSubMessage(Message msg) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setTopBarTitle() {

    }

    @Override
    public void setTopBarBack() {

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
    public Parcelable getNextExtra() {
        return null;
    }

    @Override
    public void onRequestInterceptFail(int flag, Object failObj) {

    }

    @Override
    public void onRequestDialogFail(int flag, Object failObj) {

    }

    @Override
    public void onRequestToastFail(int flag, Object failObj) {

    }

    @Override
    public void showData(List<NestedMultiItemEntity> itemEntityList) {
        multiItemQuickAdapter.setNewData(itemEntityList);
    }
}
