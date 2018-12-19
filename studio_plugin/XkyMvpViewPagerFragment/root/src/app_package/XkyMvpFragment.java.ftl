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


/**
 *
 * 作者 : 
 * 邮箱 : 
 * desc   :
 * version: 1.0
 
 */
public class ${className}  extends BaseMVPFragment<${contractName}.View,
        ${contractName}.Presenter, ${fragmentBinding}>
        implements ${contractName}.View, View.OnClickListener {


    /*
    * 静态创建对象
    */
    public static ${className} newInstance(@Nullable Bundle fragmentArgumentsBundle) {
        ${className} f = new ${className}();
        f.setArguments(fragmentArgumentsBundle);
        return f;
    }


	private final List<BaseMVPFragment> mListFragments = new ArrayList<>();

    private List<String> titleNames = new ArrayList<>();
	
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
		
		//manageResidentHealthBaseFragment = ManageResidentHealthBaseFragment.newInstance(null);
        //manageResidentHealthHistoryFragment = ManageResidentHealthHistoryFragment.newInstance(null);
        //manageResidentHealthOtherFragment = ManageResidentHealthOtherFragment.newInstance(null);
		
		//设置相应的fragment
        //设置相应的fragment
        //设置相应的fragment
        //mListFragments.add(manageResidentHealthBaseFragment);
        //mListFragments.add(manageResidentHealthHistoryFragment);
        //mListFragments.add(manageResidentHealthOtherFragment);

        //titleNames.add("基本情况");
        //titleNames.add("过往病史");
        //titleNames.add("其他信息");
		
		// tabLayout 标题
        mViewBindingFgt.includeTabViewpager.customTableLayout.setBackgroundColor(
                ContextCompat.getColor(getActivity(), R.color.white));
        mViewBindingFgt.includeTabViewpager.customTableLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(getActivity(), R.color.color_4086ff));
        mViewBindingFgt.includeTabViewpager.customTableLayout.setSelectedTabIndicatorHeight(
                DensityUtil.dp2px(getActivity(), 2));
        mViewBindingFgt.includeTabViewpager.customTableLayout.setTabTextColors(
                ContextCompat.getColor(getActivity(), R.color.color_999999),
                ContextCompat.getColor(getActivity(), R.color.color_4086ff));
        // tab标题不可滑动，默认不可滑动，全部展示
        mViewBindingFgt.includeTabViewpager.customTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewBindingFgt.includeTabViewpager.customTableLayout.setTabMode(TabLayout.MODE_FIXED);

        // Fragment+ViewPager+FragmentViewPager组合的使用
        // mViewBindingFgt.includeTabViewpager.customViewPager 在Binding中不为空就不判断空了
		//这里控制不滑动
        mViewBindingFgt.includeTabViewpager.customViewPager.setNoScroll(true);
        mViewBindingFgt.includeTabViewpager.customViewPager.setAdapter(
                new BaseFragmentPagerAdapter<BaseMVPFragment>(getChildFragmentManager(), mListFragments) {
                    @Override
                    public CharSequence getPageTitle(int position) {
                        return titleNames.get(position);
                    }
                });

        // 分割线
        // mViewBindingFgt.includeTabViewpager.customDividerLine

        // TabLayout关联ViewPager
        mViewBindingFgt.includeTabViewpager.customTableLayout.setupWithViewPager(
                mViewBindingFgt.includeTabViewpager.customViewPager);

        // 再之后调用
        mViewBindingFgt.includeTabViewpager.customTableLayout.post(() ->
                ViewUtil.setTabLayoutIndicatorWidth(mViewBindingFgt.includeTabViewpager.customTableLayout,
                        IntConsts.INT_20, IntConsts.INT_20));


        mViewBindingFgt.includeTabViewpager.customTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewUtil.hideKeyboard(getActivity());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 刷新加载界面
        mViewBindingFgt.includeTabViewpager.customViewPager.getAdapter().notifyDataSetChanged();
        mViewBindingFgt.includeTabViewpager.customViewPager.setOffscreenPageLimit(3);
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

    public void ${commonUrlName}Success(){

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
