package mo.wall.org.nodisplay

import android.app.Activity
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import mo.wall.org.R

/**
 *   <style name="NoDisplay" parent="@android:style/Theme.NoDisplay">
 *   <item name="windowActionBar">false</item>
 *   <item name="windowNoTitle">true</item>
 *   </style>
 */
class NoDisplayActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dm = DisplayMetrics()
        val layoutParams = window.attributes
        //修改activity的属性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
            //大于6.0以上
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //大于4.4.4以上
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.height = (dm.widthPixels * 0.95).toInt()
        layoutParams.width = 1
        layoutParams.gravity = Gravity.RIGHT
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_no_display)
        Log.i("moziqi", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        setVisible(true)
    }

    override fun onResume() {
        super.onResume()
        Log.i("moziqi", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("moziqi", "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("moziqi", "onDestroy")
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        Log.i("moziqi", "onBackPressed")
    }
}
