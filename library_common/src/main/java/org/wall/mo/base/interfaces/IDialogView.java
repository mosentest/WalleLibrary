package org.wall.mo.base.interfaces;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-19 14:58
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public interface IDialogView extends IToastView {
    public void showDialog(String msg);

    public void hideDialog();

    public void showInfoDialog(String msg);
}
