package mo.wall.org.screenshot;


import android.os.Parcel;
import android.os.Parcelable;


public class ScreenshotAcceptPar implements Parcelable {

    public static final Creator<ScreenshotAcceptPar> CREATOR = new Creator<ScreenshotAcceptPar>() {
        @Override
        public ScreenshotAcceptPar createFromParcel(Parcel in) {
            return new ScreenshotAcceptPar(in);
        }

        @Override
        public ScreenshotAcceptPar[] newArray(int size) {
            return new ScreenshotAcceptPar[size];
        }
    };

    protected ScreenshotAcceptPar(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
