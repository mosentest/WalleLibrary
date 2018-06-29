package android.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者 create by moziqi on 2018/6/29
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class NetworkStatsHistory implements Parcelable {
    protected NetworkStatsHistory(Parcel in) {
    }

    public static final Creator<NetworkStatsHistory> CREATOR = new Creator<NetworkStatsHistory>() {
        @Override
        public NetworkStatsHistory createFromParcel(Parcel in) {
            return new NetworkStatsHistory(in);
        }

        @Override
        public NetworkStatsHistory[] newArray(int size) {
            return new NetworkStatsHistory[size];
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
