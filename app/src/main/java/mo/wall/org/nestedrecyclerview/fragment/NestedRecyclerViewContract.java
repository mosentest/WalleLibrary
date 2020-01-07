package mo.wall.org.nestedrecyclerview.fragment;

import org.wall.mo.base.mvp.BaseContract;

import java.util.List;

import mo.wall.org.nestedrecyclerview.NestedParentMultiItemEntity;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 20:35
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedRecyclerViewContract {

    public static abstract class Presenter extends BaseContract.BasePresenter<View> {

        public abstract void loadData();
    }

    public interface View extends BaseContract.BaseView {

        public void showData(List<NestedChildMultiItemEntity> itemEntityList);
    }
}
