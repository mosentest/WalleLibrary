package mo.wall.org.rxjava2.data;


import android.os.Parcel;
import android.os.Parcelable;


public class RxJavaAcceptPar implements Parcelable {


    public String name;

    private RxJavaAcceptPar(Builder builder) {
        name = builder.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public RxJavaAcceptPar() {
    }

    protected RxJavaAcceptPar(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<RxJavaAcceptPar> CREATOR = new Creator<RxJavaAcceptPar>() {
        @Override
        public RxJavaAcceptPar createFromParcel(Parcel source) {
            return new RxJavaAcceptPar(source);
        }

        @Override
        public RxJavaAcceptPar[] newArray(int size) {
            return new RxJavaAcceptPar[size];
        }
    };

    public static final class Builder {
        private String name;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public RxJavaAcceptPar build() {
            return new RxJavaAcceptPar(this);
        }
    }
}
