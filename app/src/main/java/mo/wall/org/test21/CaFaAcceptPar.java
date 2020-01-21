package mo.wall.org.test21;


import android.os.Parcel;
import android.os.Parcelable;


public class CaFaAcceptPar implements Parcelable {

    public static final Creator<CaFaAcceptPar> CREATOR = new Creator<CaFaAcceptPar>() {
        @Override
        public CaFaAcceptPar createFromParcel(Parcel in) {
            return new CaFaAcceptPar(in);
        }

        @Override
        public CaFaAcceptPar[] newArray(int size) {
            return new CaFaAcceptPar[size];
        }
    };

    protected CaFaAcceptPar(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
