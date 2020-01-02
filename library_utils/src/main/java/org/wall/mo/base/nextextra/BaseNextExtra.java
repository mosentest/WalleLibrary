package org.wall.mo.base.nextextra;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-28 14:52
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class BaseNextExtra implements Parcelable {


    protected BaseNextExtra(Parcel in) {
    }

    public static final Creator<BaseNextExtra> CREATOR = new Creator<BaseNextExtra>() {
        @Override
        public BaseNextExtra createFromParcel(Parcel in) {
            return new BaseNextExtra(in);
        }

        @Override
        public BaseNextExtra[] newArray(int size) {
            return new BaseNextExtra[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
