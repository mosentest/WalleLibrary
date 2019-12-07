package mo.wall.org.markerview;

import android.os.Bundle;
import android.os.Message;

import org.jetbrains.annotations.Nullable;

import mo.wall.org.R;
import mo.wall.org.base.BaseAppCompatActivity;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-06 19:51
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MarkerViewAct extends BaseAppCompatActivity {
    @Override
    public void handleMessageAct(@Nullable Message msg) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_markerview);
    }
}
