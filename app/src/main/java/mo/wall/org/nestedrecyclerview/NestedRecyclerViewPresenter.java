package mo.wall.org.nestedrecyclerview;

import android.os.Bundle;

import java.util.Arrays;

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
    public void load() {
        if (getView() != null) {
            getView().showData(Arrays.asList(
                    new NestedParentMultiItemEntity.Builder().type(1).build(),

                    new NestedParentMultiItemEntity.Builder().type(3).build(),

                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),

                    new NestedParentMultiItemEntity.Builder().type(3).build(),

                    new NestedParentMultiItemEntity.Builder().type(4).build(),
                    new NestedParentMultiItemEntity.Builder().type(4).build(),
                    new NestedParentMultiItemEntity.Builder().type(4).build(),
                    new NestedParentMultiItemEntity.Builder().type(4).build(),
                    new NestedParentMultiItemEntity.Builder().type(4).build(),
                    new NestedParentMultiItemEntity.Builder().type(4).build(),

                    new NestedParentMultiItemEntity.Builder().type(3).build(),

                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),
                    new NestedParentMultiItemEntity.Builder().type(2).build(),

                    new NestedParentMultiItemEntity.Builder().type(5).build()
            ));
        }
    }
}
