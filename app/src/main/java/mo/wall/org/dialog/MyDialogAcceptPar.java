package mo.wall.org.dialog;


import android.os.Parcel;
import android.os.Parcelable;


public class MyDialogAcceptPar implements Parcelable {

    public static final Creator<MyDialogAcceptPar> CREATOR = new Creator<MyDialogAcceptPar>() {
        @Override
        public MyDialogAcceptPar createFromParcel(Parcel in) {
            return new MyDialogAcceptPar(in);
        }

        @Override
        public MyDialogAcceptPar[] newArray(int size) {
            return new MyDialogAcceptPar[size];
        }
    };

    protected MyDialogAcceptPar(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
