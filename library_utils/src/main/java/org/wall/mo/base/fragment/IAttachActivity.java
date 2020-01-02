package org.wall.mo.base.fragment;

import android.os.Parcelable;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/31 11:21 AM
 * Description: ${DESCRIPTION}
 * History:
 * fragment 跟 activity通讯类
 * 还有activity的基础方法
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public interface IAttachActivity {

    public void setTopBarTitle();

    public void setTopBarBack();

    public void showShortToast(String msg);

    public void showLongToast(String msg);

    public void showDialog(String msg);

    public void hideDialog();

    public Parcelable getNextExtra();
}
