package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
<#if includeCppSupport!false>
import android.widget.TextView;
</#if>

import android.content.Intent;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;

import org.wall.mo.base.activity.AbsWithV4FragmentActivity;

import mo.wall.org.databinding.Activity${underscoreToCamelCase(classToResource(activityClass))}Binding;
import mo.wall.org.R;

public class ${activityClass} extends AbsWithV4FragmentActivity<Activity${underscoreToCamelCase(classToResource(activityClass))}Binding, ${acceptParClass}> {

    @Override
    public int getContainerViewId() {
        return R.id.container;
    }


    @Override
    public int getLayoutId() {
        return R.layout.${activityLayout};
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
    public Fragment createFragment() {
        Intent intent = getIntent();
        Bundle extras = null;
        if (intent != null) {
            extras = intent.getExtras();
        }
        return ${fragmentClass}.newInstance(extras);
    }

    @Override
    public void loadIntentData(Intent intent) {

    }

    @Override
    public void handleSubMessage(Message msg) {

    }

    @Override
    public void onClick(View v) {

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
}
