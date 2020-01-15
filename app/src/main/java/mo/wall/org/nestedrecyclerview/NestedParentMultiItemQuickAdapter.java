package mo.wall.org.nestedrecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.wall.mo.base.activity.AbsDataBindingAppCompatActivity;
import org.wall.mo.utils.log.WLog;

import java.util.List;

import mo.wall.org.R;
import mo.wall.org.nestedrecyclerview.view.ChildRecyclerView;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 22:11
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedParentMultiItemQuickAdapter
        extends BaseMultiItemQuickAdapter<NestedParentMultiItemEntity, BaseViewHolder>
        implements LifecycleObserver {

    private AbsDataBindingAppCompatActivity mActivity;

    private LinearLayout mBottomFl;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NestedParentMultiItemQuickAdapter(AbsDataBindingAppCompatActivity activity, List<NestedParentMultiItemEntity> data) {
        super(data);
        mActivity = activity;
        addItemType(1, R.layout.activity_nested_recyclerview_header);
        addItemType(2, R.layout.activity_nested_recyclerview_header_item);
        addItemType(3, R.layout.activity_nested_recyclerview_title);
        addItemType(4, R.layout.activity_nested_recyclerview_mid_item);
        addItemType(5, R.layout.activity_nested_recyclerview_bottom);


    }

    @Override
    protected void convert(BaseViewHolder helper, NestedParentMultiItemEntity item) {
        switch (item.getItemType()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                mBottomFl = helper.getView(R.id.bottomFl);
                if (mLoadView != null) {
                    mLoadView.loadBottom(mBottomFl, helper.getPosition());
                }
                break;
        }
    }

    private LoadView mLoadView;

    public interface LoadView {
        public void loadBottom(ViewGroup viewGroup, int position);
    }

    public void setLoadView(LoadView loadView) {
        this.mLoadView = loadView;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        WLog.i("mo", "onDestroy");
        mActivity = null;
    }
}
