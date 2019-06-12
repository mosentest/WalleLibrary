package org.wall.mo.compat.topactivity.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;

public class ControlGroup implements Parcelable {

  /** hierarchy ID number */
  public final int id;

  /** set of subsystems bound to the hierarchy */
  public final String subsystems;

  /** control group in the hierarchy to which the process belongs */
  public final String group;

  protected ControlGroup(String line) throws NumberFormatException, IndexOutOfBoundsException {
    String[] fields = line.split(":");
    id = Integer.parseInt(fields[0]);
    subsystems = fields[1];
    group = fields[2];
  }

  protected ControlGroup(Parcel in) {
    this.id = in.readInt();
    this.subsystems = in.readString();
    this.group = in.readString();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.subsystems);
    dest.writeString(this.group);
  }

  @Override public String toString() {
    return String.format(Locale.ENGLISH, "%d:%s:%s", id, subsystems, group);
  }

  public static final Creator<ControlGroup> CREATOR = new Creator<ControlGroup>() {

    @Override public ControlGroup createFromParcel(Parcel source) {
      return new ControlGroup(source);
    }

    @Override public ControlGroup[] newArray(int size) {
      return new ControlGroup[size];
    }
  };

}