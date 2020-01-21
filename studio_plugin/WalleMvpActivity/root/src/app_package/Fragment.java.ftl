package ${packageName}.${fragmentPackage};

import ${getMaterialComponentName('android.arch.lifecycle.ViewModelProviders', useAndroidX)};
import android.os.Bundle;
import ${getMaterialComponentName('android.support.annotation.NonNull', useAndroidX)};
import ${getMaterialComponentName('android.support.annotation.Nullable', useAndroidX)};
import ${getMaterialComponentName('android.support.v4.app.Fragment', useAndroidX)};
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ${escapeKotlinIdentifiers(packageName)}.R;


import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment;

public class ${fragmentClass} extends 
        BaseMVPMaxLifecycleFragment<${contractClass}.Presenter, Fragment${activityClass}Binding, ${acceptParClass}>
        implements ${contractClass}.View {

    public static ${fragmentClass} newInstance(Bundle args) {
        ${fragmentClass} fragment = new ${fragmentClass}();
        fragment.setArguments(args);
        return fragment;
    }

   @Override
    protected ${contractClass}.Presenter createPresenter() {
        return new ${presenterClass}();
    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.${fragmentLayout};
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
