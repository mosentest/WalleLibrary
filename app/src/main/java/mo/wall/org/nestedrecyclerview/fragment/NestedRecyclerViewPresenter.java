package mo.wall.org.nestedrecyclerview.fragment;

import android.os.Bundle;

import java.util.Arrays;

import mo.wall.org.nestedrecyclerview.NestedParentMultiItemEntity;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 20:38
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedRecyclerViewPresenter extends NestedRecyclerViewContract.Presenter {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onDestroy() {

    }

    @Override
    public void loadData() {
        if (isAttach()) {
            getView().showData(Arrays.asList(
                    new NestedChildMultiItemEntity.Builder().type(1).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(1).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build(),
                    new NestedChildMultiItemEntity.Builder().type(2).build()
            ));
        }
    }
}
