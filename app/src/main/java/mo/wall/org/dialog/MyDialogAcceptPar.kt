package mo.wall.org.dialog


import android.os.Parcel
import android.os.Parcelable


class MyDialogAcceptPar : Parcelable {

    protected constructor(`in`: Parcel) {

    }

    protected constructor() {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<MyDialogAcceptPar> = object : Parcelable.Creator<MyDialogAcceptPar> {
            override fun createFromParcel(`in`: Parcel): MyDialogAcceptPar {
                return MyDialogAcceptPar(`in`)
            }

            override fun newArray(size: Int): Array<MyDialogAcceptPar?> {
                return arrayOfNulls(size)
            }
        }
    }
}
