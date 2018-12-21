package ${packageName};

<#if includeCallbacks>import android.content.Context;</#if>
<#if includeCallbacks>import android.net.Uri;</#if>
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android${SupportPackage}.app.Fragment;
<#if !includeLayout>import android.widget.TextView;</#if>
<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.xky.app.patient.base.BaseMVPFragment;
import com.xky.app.patient.databinding.${fragmentBinding};

import com.xky.app.patient.base.adapter.BaseFragmentPagerAdapter;
import com.xky.app.patient.base.util.DensityUtil;
import com.xky.app.patient.base.util.ViewUtil;
import com.xky.app.patient.constant.IntConsts;
import java.util.ArrayList;
import java.util.List;
import android.view.WindowManager;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.xky.app.patient.base.BaseMVPFragment;
import com.xky.app.patient.base.savedinstancestate.MoSavedState;
import com.xky.app.patient.base.savedinstancestate.MoSavedStateHelper;
import com.xky.app.patient.base.util.ActivityUtil;
import com.xky.app.patient.base.util.ImageLoaderUtil;
import com.xky.app.patient.base.util.LogUtil;
import com.xky.app.patient.base.util.ViewUtil;
import com.xky.app.patient.base.util.login.BaseLoginCallBackImpl;
import com.xky.app.patient.base.util.login.LoginManager;
import com.xky.app.patient.constant.IntConsts;
import com.xky.app.patient.constant.IntStringConsts;
import com.xky.app.patient.constant.IntentConsts;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.xky.app.patient.event.AbsEvent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 * 作者 : 
 * 邮箱 : 
 * desc   :
 * version: 1.0
 
 */
public class ${className} extends BaseMVPFragment<${contractName}.View,
        ${contractName}.Presenter, ${fragmentBinding}>
        implements ${contractName}.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener  {


    private static final String TAG = ${className}.class.getSimpleName();

    /*
    * 静态创建对象
    */
    public static ${className} newInstance(@Nullable Bundle fragmentArgumentsBundle) {
        ${className} f = new ${className}();
        f.setArguments(fragmentArgumentsBundle);
        return f;
    }


    private BaseQuickAdapter<${infoName}.DataListBean, BaseViewHolder> mAdapter;

	
	 @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            //type = arguments.getInt(TAB_NAME, type);
        }
		
		//更换ScrollView+adjustResize,适配有输入相关的
        //getActivity().getWindow().setSoftInputMode(
        //        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        //);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            //outState.putInt(TAB_NAME, type);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            //savedInstanceState.getInt(TAB_NAME, type);
        }
    }

    /**
     * eventbus 来处理事件，预留在这里
     *
     * @param event
     */
    //@Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(AbsEvent event) {
        LogUtil.i(TAG, "handleEvent..>>>" + event.getClass());
        //if (event instanceof RefreshManageSignListEvent) {
        //    if (mPresenter != null) {
        //        RefreshManageSignListEvent event1 = (RefreshManageSignListEvent) event;
        //        if (type == event1.type && mAdapter != null && isPause) {
        //            onRefresh();
        //            isPause = false;
        //        }
        //    }
        //}
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected ${contractName}.Presenter createPresenter() {
        return new ${presenterName}();
    }

    @Override
    protected int getInflateRootViewLayoutResID() {
        return R.layout.${fragmentName};
    }


    @Override
    public void onFirstVisibleToUser() {
		mViewBindingFgt.setListener(this);
        mViewBindingFgt.srLayout.setDefaultSchemeColors();
        mViewBindingFgt.srLayout.setOnRefreshListener(this);
        mViewBindingFgt.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //@see com.xky.app.patient.ui.queryoutpatientpaymentlist.QueryOutpatientPaymentListFragment
        mAdapter = new BaseQuickAdapter<${infoName}.DataListBean, BaseViewHolder>(R.layout.${fragmentName}, null) {
            @Override
            protected void convert(BaseViewHolder helper, ${infoName}.DataListBean item) {

            }

            @Override
            public void onViewRecycled(@NonNull BaseViewHolder holder) {
                super.onViewRecycled(holder);
                //ImageView iv_resident_icon = holder.getView(R.id.iv_resident_icon);
                //if (iv_resident_icon != null) {
                //    Glide.clear(iv_resident_icon);
                //}
            }
        };
        // 设置上拉加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.${commonUrlName}(IntConsts.INT_2);
            }
        }, mViewBindingFgt.recyclerView);
        if (getActivity() != null) {
            View emptyView = ViewUtil.getAdapterEmptyView(getActivity(),
                    ViewUtil.EmptyImageType.OTHER_BLANK,
                    getString(R.string.base_adapter_empty_no_relevant_records));
            // 添加空视图
            mAdapter.setEmptyView(emptyView);
        }
        mViewBindingFgt.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击事件
            }
        });
        onRefresh();
    }


    @Override
    public void ${commonUrlName}Success(${infoName} object, int pageFlag){ 
        // 返回的一个提示,0为没有更多,1为初始化或刷新逻辑处理,其他则下拉加载
        switch (pageFlag) {
            // 没有更多数据
            case IntConsts.INT_0:
                //showShortToast(getString(R.string.getServer_dataList_all_tips));
                mAdapter.loadMoreEnd();
                break;
            // 刷新和初始化界面
            case IntConsts.INT_1:
                mAdapter.setNewData(object.dataList);
                mViewBindingFgt.srLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
                if (object.dataList != null && !object.dataList.isEmpty()) {
                    mViewBindingFgt.recyclerView.scrollToPosition(0);
                }
                break;
            case IntConsts.INT_2:
                mAdapter.addData(object.dataList);
                mAdapter.loadMoreComplete();
                break;
            case IntConsts.INT_3:
                mViewBindingFgt.srLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mViewBindingFgt.srLayout.setRefreshing(true);
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.setEnableLoadMore(false);
        mPresenter.${commonUrlName}(IntConsts.INT_1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
	
	@Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //MenuItem item = menu.findItem(R.id.menu_toolbar_right_text_btn);
        //if (item != null) {
        //    item.setTitle("保存");
        //}
    }

    @Override
    public int getMenuResId() {
        //return R.menu.toobar_right_text_btn;
        return super.getMenuResId();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //    if (item.getItemId() == R.id.menu_toolbar_right_text_btn) {
		//
        //       return true;
        //    }
        //}
        return super.onOptionsItemSelected(item);
    }


<#if includeFactory>
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
</#if>

<#if includeCallbacks>
    private OnFragmentInteractionListener mListener;
</#if>

<#if includeFactory>
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ${className}.
     */
    // TODO: Rename and change types and number of parameters
    public static ${className} newInstance(String param1, String param2) {
        ${className} fragment = new ${className}();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
</#if>
    public ${className}() {
        // Required empty public constructor
    }

<#if includeFactory>
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
</#if>


<#if includeCallbacks>
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
</#if>
}
