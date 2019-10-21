package mo.wall.org.opengl2

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-10-18 11:35
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class OpenGLES20Activity : Activity() {

    private var mGlSurfaceView: MyGLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGlSurfaceView = MyGLSurfaceView(this)

        setContentView(mGlSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        mGlSurfaceView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGlSurfaceView!!.onPause()
    }
}
