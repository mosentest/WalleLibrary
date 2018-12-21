package ${packageName};

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xky.app.patient.base.util.FormatValidatorsUtil;
import com.xky.app.patient.constant.IntConsts;

import com.xky.app.patient.api.base.BaseEntityObserver;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者 : 
 * 邮箱 : 
 * desc   :
 * version: 1.0
 */
public class ${presenterName} extends ${contractName}.Presenter {

    /**
     * 页码
     */
    private int mPage;
    /**
     * 总页数
     */
    private int totalpage;

    @Override
    protected ${contractName}.Model createModel() {
        return new ${modelName}();
    }

    @Override
    public void start() {

    }

    @Override
    public void initBundleData(@NonNull Bundle bundle) {

    }

    public void ${commonUrlName}(int pageFlag) {
        // 为1的时候,一般是刷新,或者初始化界面,初始化计数的mPage
        if (pageFlag == 1) {
            mPage = pageFlag;
            // 初始化一下,防止下面的下拉判断直接进入
            totalpage = pageFlag;
        }
        // 上拉的时候,大于总页数,直接返回0,提示没有更多数据
        if (mPage > totalpage) {
            getBaseView().${commonUrlName}Success(null, 0);
            return;
        }
        Map<String, Object> map = new HashMap<>();
        //这里加其他参数
        map.put("page", Integer.toString(mPage));
        baseModel.${commonUrlName}(map, new BaseEntityObserver<${infoName}>(getBaseView(), ${infoName}.class, false) {
            @Override
            public void onSuccess(@NonNull ${infoName} bean) {
                mPage++;
                totalpage = FormatValidatorsUtil.getSafeInt(bean.totalpage);
                if (getBaseView() != null) {
                    getBaseView().${commonUrlName}Success(bean, pageFlag);
                }
            }

            @Override
            public void onFail(@Nullable String s) {
                super.onFail(s);
                if (getBaseView() != null) {
                    getBaseView().${commonUrlName}Success(null, IntConsts.INT_3);
                }
            }

            @Override
            public void onReloadData() {
                super.onReloadData();
                ${commonUrlName}(pageFlag);
            }
        });
    }
}
