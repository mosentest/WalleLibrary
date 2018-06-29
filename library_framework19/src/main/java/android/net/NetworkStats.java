package android.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者 create by moziqi on 2018/6/29
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class NetworkStats implements Parcelable {
    protected NetworkStats(Parcel in) {
    }

    public static final Creator<NetworkStats> CREATOR = new Creator<NetworkStats>() {
        @Override
        public NetworkStats createFromParcel(Parcel in) {
            return new NetworkStats(in);
        }

        @Override
        public NetworkStats[] newArray(int size) {
            return new NetworkStats[size];
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
