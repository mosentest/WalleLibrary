package org.wall.mo.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wall.mo.utils.StringUtils;
import org.wall.mo.utils.log.WLog;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/29 下午4:50
 * Description: ${DESCRIPTION}
 * History: 基础类 列出相关的声明周期方法
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class AbsV4Fragment extends Fragment {

    public final static String TAG = AbsV4Fragment.class.getSimpleName();

    protected Context mContext;

    protected IAttachActivity iAttachActivity;

    protected View rootView;

    /**
     * 例子
     *
     * @param args
     * @return
     */
    public static Fragment newInstance(Bundle args) {
        Fragment fragment = null;
        if (args != null) {
            args.putString(TAG, TAG);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WLog.i(TAG, getName() + ".onAttach mContext is " + (context != null ? context.getClass().getSimpleName() : "--"));
        this.mContext = context;
        if (context instanceof IAttachActivity) {
            iAttachActivity = (IAttachActivity) context;
        } else {
            onAbsV4Attach(context);
        }
    }

    protected abstract void onAbsV4Attach(Context context);

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        WLog.i(TAG, getName() + ".onAttachFragment");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        WLog.i(TAG, getName() + ".setUserVisibleHint isVisibleToUser is" + isVisibleToUser);
//        LogUtils.i(TAG,getName() + "  isResumed() " + isResumed());
//        LogUtils.i(TAG,getName() + "  isAdded() " + isAdded());
//        LogUtils.i(TAG, getName() + "  setUserVisibleHint getParentFragment != null  " + (getParentFragment() != null));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        WLog.i(TAG, getName() + ".onHiddenChanged hidden is " + hidden);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WLog.i(TAG, getName() + ".onCreate");
        /**
         * 保持Fragment
         */
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WLog.i(TAG, getName() + ".onCreateView savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            if (rootView == null) {
                rootView = inflater.inflate(layoutId, container, false);
            }
            //在这里findViewById
            initView(rootView, savedInstanceState);
            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    /**
     * 实现一个findViewById的方法
     *
     * @param idRes
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int idRes) {
        if (idRes == View.NO_ID) {
            return null;
        }
        return rootView.findViewById(idRes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WLog.i(TAG, getName() + ".onViewCreated savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WLog.i(TAG, getName() + ".onActivityCreated savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        initData();
        initClick();
    }

    @Override
    public void onStart() {
        super.onStart();
        WLog.i(TAG, getName() + ".onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        WLog.i(TAG, getName() + ".onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        WLog.i(TAG, getName() + ".onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        WLog.i(TAG, getName() + ".onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WLog.i(TAG, getName() + ".onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WLog.i(TAG, getName() + ".onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        WLog.i(TAG, getName() + ".onDetach");
        mContext = null;
        iAttachActivity = null;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        WLog.i(TAG, getName() + ".onSaveInstanceState outState is " + StringUtils.isNULL(outState));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        WLog.i(TAG, getName() + ".onViewStateRestored savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        WLog.i(TAG, getName() + ".onConfigurationChanged");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WLog.i(TAG, getName() + ".onRequestPermissionsResult");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WLog.i(TAG, getName() + ".onActivityResult");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        WLog.i(TAG, getName() + ".onLowMemory");
    }

    /**
     * 获取onAttach的context
     *
     * @return
     */
    public Context getCurActivity() {
        return mContext;
    }

    public String getName() {
        return getClass().getSimpleName();
    }


    /**
     * 默认返回-1
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化view
     *
     * @param rootView
     * @param savedInstanceState
     */
    public abstract void initView(View rootView, Bundle savedInstanceState);

    public abstract void initData();

    public abstract void initClick();
}
