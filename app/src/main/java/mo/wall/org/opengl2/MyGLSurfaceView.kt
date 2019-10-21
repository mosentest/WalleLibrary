package mo.wall.org.opengl2


import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-10-17 10:06
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class MyGLSurfaceView : GLSurfaceView {


    private lateinit var myGLRenderer: MyGLRenderer

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        // 创建OpenGL ES 2.0的上下文
        setEGLContextClientVersion(2)

        myGLRenderer = MyGLRenderer()
        //设置Renderer用于绘图
        setRenderer(myGLRenderer)

        //只有在绘制数据改变时才绘制view，可以防止GLSurfaceView帧重绘
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
