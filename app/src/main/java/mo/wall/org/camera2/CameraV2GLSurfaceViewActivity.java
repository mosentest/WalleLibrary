package mo.wall.org.camera2;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.DisplayMetrics;

import org.wall.mo.utils.camera.CameraV2;

public class CameraV2GLSurfaceViewActivity extends Activity {
    private CameraV2GLSurfaceView mCameraV2GLSurfaceView;
    private CameraV2 mCamera;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCameraV2GLSurfaceView = new CameraV2GLSurfaceView(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mCamera = new CameraV2(this);
        mCamera.setupCamera(dm.widthPixels, dm.heightPixels);
        if (!mCamera.openCamera()) {
            return;
        }
        mCameraV2GLSurfaceView.init(mCamera, false, CameraV2GLSurfaceViewActivity.this);
        setContentView(mCameraV2GLSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.onDestroy();
        }
    }
}