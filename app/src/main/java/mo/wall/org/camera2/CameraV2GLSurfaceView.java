package mo.wall.org.camera2;

import android.content.Context;
import android.opengl.GLSurfaceView;

import org.wall.mo.utils.camera.CameraV2;

public class CameraV2GLSurfaceView extends GLSurfaceView {
    public static final String TAG = "Filter_CameraV2GLSurfaceView";
    private CameraV2Renderer mCameraV2Renderer;

    public void init(CameraV2 camera, boolean isPreviewStarted, Context context) {
        setEGLContextClientVersion(2);

        mCameraV2Renderer = new CameraV2Renderer();
        mCameraV2Renderer.init(this, camera, isPreviewStarted, context);

        setRenderer(mCameraV2Renderer);
    }

    public CameraV2GLSurfaceView(Context context) {
        super(context);
    }
}