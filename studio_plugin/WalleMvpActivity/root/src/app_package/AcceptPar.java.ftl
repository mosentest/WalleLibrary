package ${packageName}.${fragmentPackage};


import android.os.Parcel;
import android.os.Parcelable;


public class ${acceptParClass} implements Parcelable {

      protected ${acceptParClass}(Parcel in) {
    }

    public static final Creator<${acceptParClass}> CREATOR = new Creator<${acceptParClass}>() {
        @Override
        public ${acceptParClass} createFromParcel(Parcel in) {
            return new ${acceptParClass}(in);
        }

        @Override
        public ${acceptParClass}[] newArray(int size) {
            return new ${acceptParClass}[size];
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
