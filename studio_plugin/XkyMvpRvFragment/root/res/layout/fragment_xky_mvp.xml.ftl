<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
		<variable
            name="listener"
            type="android.view.View.OnClickListener" />
    </data>

    <com.xky.app.patient.view.widget.VerticalSwipeRefreshLayout
        android:id="@+id/sr_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView
            android:id="@+id/recycler_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/color_F9F9F9"
             />

    </com.xky.app.patient.view.widget.VerticalSwipeRefreshLayout>

            
            <!--
            android:foreground="?android:selectableItemBackground"
			android:background="?android:selectableItemBackground"
            tools:listitem="@layout/fragment_manage_consult_list_item"
            -->

</layout>