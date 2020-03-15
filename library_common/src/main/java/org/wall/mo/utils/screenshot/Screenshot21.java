package org.wall.mo.utils.screenshot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.OrientationEventListener;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.nio.ByteBuffer;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-03-11 21:58
 * Description:
 * <p>
 * https://github.com/goodbranch/ScreenCapture
 * <p>
 * <p>
 * History: 5.0截屏工具类
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class Screenshot21 {

    private final static Screenshot21 oScreenshot = new Screenshot21();

    private MediaProjectionManager mMediaProjectionManager;

    private MediaProjection mMediaProjection;

    private VirtualDisplay mVirtualDisplay;

    private ImageReader mImageReader;

    private Display mDisplay;

    private int mDensity;

    private int mWidth;

    private int mHeight;

    private int mRotation;

    private OrientationChangeCallback mOrientationChangeCallback;

    private int mRequestCode;

    private Handler mHandler;

    private static final String SCREENCAP_NAME = "screencap";

    private static final int VIRTUAL_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY | DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;

    public static Screenshot21 getInstance() {
        return oScreenshot;
    }

    private Screenshot21() {
        mHandler = new Handler(Looper.getMainLooper());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void requestMediaProject(Activity activity, Fragment fragment, int requestCode) {
        try {
            if (mMediaProjectionManager == null) {
                mMediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            }
            if (mMediaProjectionManager != null) {
                mRequestCode = requestCode;
                Intent screenCaptureIntent = mMediaProjectionManager.createScreenCaptureIntent();
                if (fragment != null) {
                    fragment.startActivityForResult(screenCaptureIntent, mRequestCode);
                } else {
                    activity.startActivityForResult(screenCaptureIntent, mRequestCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        try {
            if (mRequestCode == requestCode && resultCode == Activity.RESULT_OK) {
                if (mMediaProjectionManager != null) {
                    mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);

                    // display metrics
                    DisplayMetrics metrics = activity.getResources().getDisplayMetrics();

                    mDensity = metrics.densityDpi;
                    mDisplay = activity.getWindowManager().getDefaultDisplay();

                    // get width and height
                    Point size = new Point();
                    mDisplay.getSize(size);
                    mWidth = size.x;
                    mHeight = size.y;

                    // create virtual display depending on device width / height
                    createVirtualDisplay();

                    // register orientation change callback
                    mOrientationChangeCallback = new OrientationChangeCallback(activity.getApplicationContext());
                    if (mOrientationChangeCallback.canDetectOrientation()) {
                        mOrientationChangeCallback.enable();
                    }

                    // register media projection stop callback
                    mMediaProjection.registerCallback(new MediaProjectionStopCallback(), mHandler);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public MediaProjection getMediaProjection() {
        return mMediaProjection;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class MediaProjectionStopCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mVirtualDisplay != null) {
                        mVirtualDisplay.release();
                    }
                    if (mImageReader != null) {
                        mImageReader.setOnImageAvailableListener(null, null);
                    }
                    if (mOrientationChangeCallback != null) {
                        mOrientationChangeCallback.disable();
                    }
                    if (mMediaProjection != null) {
                        mMediaProjection.unregisterCallback(MediaProjectionStopCallback.this);
                    }
                }
            });
        }
    }

    private void createVirtualDisplay() {
        // start capture reader
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mImageReader == null) {
                mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGBA_8888, 2);
            }
            if (mMediaProjection != null) {
                if (mVirtualDisplay == null) {
                    mVirtualDisplay = mMediaProjection.createVirtualDisplay(SCREENCAP_NAME, mWidth, mHeight, mDensity, VIRTUAL_DISPLAY_FLAGS, mImageReader.getSurface(), null, mHandler);
                }
            }
        }
    }

    private class OrientationChangeCallback extends OrientationEventListener {

        OrientationChangeCallback(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            final int rotation = mDisplay.getRotation();
            if (rotation != mRotation) {
                mRotation = rotation;
                try {
                    // clean up
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (mVirtualDisplay != null) {
                            mVirtualDisplay.release();
                        }
                        if (mImageReader != null) {
                            mImageReader.setOnImageAvailableListener(null, null);
                        }
                    }
                    // re-create virtual display depending on device width / height
                    createVirtualDisplay();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Bitmap screenshot() {
        Image image = null;
        try {
            if (mImageReader == null) {
                return null;
            }
            image = mImageReader.acquireLatestImage();
            if (image == null) {
                return null;
            }
            int width = image.getWidth();
            int height = image.getHeight();
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;
            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(buffer);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (image != null) {
                image.close();
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void release() {
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        if (mImageReader != null) {
            mImageReader.close();
            mImageReader = null;
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }
}
