<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
		<variable
            name="listener"
            type="android.view.View.OnClickListener" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <include
            android:id="@+id/include_tab_viewpager"
            layout="@layout/custom_tab_viewpager" />
    </LinearLayout>
            
            <!--tools:listitem="" 
            android:foreground="?android:selectableItemBackground"
			android:background="?android:selectableItemBackground"
            -->

</layout>