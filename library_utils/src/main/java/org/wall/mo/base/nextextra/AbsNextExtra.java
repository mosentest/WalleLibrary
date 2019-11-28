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
public class AbsNextExtra implements Parcelable {


    protected AbsNextExtra(Parcel in) {
    }

    public static final Creator<AbsNextExtra> CREATOR = new Creator<AbsNextExtra>() {
        @Override
        public AbsNextExtra createFromParcel(Parcel in) {
            return new AbsNextExtra(in);
        }

        @Override
        public AbsNextExtra[] newArray(int size) {
            return new AbsNextExtra[size];
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
