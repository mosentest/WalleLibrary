
RelativeLayout相对布局里面左边TextView带icon的，右边固定

```
    <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:padding="@dimen/dp_12">

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_toLeftOf="@id/status"
                    android:orientation="horizontal">

                    <TextView
                        android:id="@+id/name"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_alignParentLeft="true"
                        android:drawableRight="@drawable/icon_arrow_details"
                        android:drawablePadding="@dimen/dp_5"
                        android:textColor="@color/color_333333"
                        android:textSize="@dimen/sp_16"
                        tools:text="11111111111111111111111111无限不影响icon的显示问题" />
                </RelativeLayout>

                <TextView
                    android:id="@+id/status"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_alignParentRight="true"
                    android:layout_centerVertical="true"
                    android:layout_marginLeft="@dimen/dp_10"
                    android:gravity="right"
                    android:textColor="@color/color_4086FF"
                    android:textSize="@dimen/sp_14"
                    tools:text="" />
            </RelativeLayout>
```