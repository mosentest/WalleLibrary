package android.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者 create by moziqi on 2018/6/29
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class NetworkTemplate implements Parcelable {
    protected NetworkTemplate(Parcel in) {
    }

    public static final Creator<NetworkTemplate> CREATOR = new Creator<NetworkTemplate>() {
        @Override
        public NetworkTemplate createFromParcel(Parcel in) {
            return new NetworkTemplate(in);
        }

        @Override
        public NetworkTemplate[] newArray(int size) {
            return new NetworkTemplate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static NetworkTemplate buildTemplateMobileAll(String activeSubscriberId) {
        return null;
    }
}
