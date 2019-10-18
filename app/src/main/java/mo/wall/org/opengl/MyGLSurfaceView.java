package mo.wall.org.opengl;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-10-17 10:06
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MyGLSurfaceView extends GLSurfaceView {


    private final MyGLRenderer myGLRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        myGLRenderer = new MyGLRenderer();
        setRenderer(myGLRenderer);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        myGLRenderer = new MyGLRenderer();
        setRenderer(myGLRenderer);
    }
}
