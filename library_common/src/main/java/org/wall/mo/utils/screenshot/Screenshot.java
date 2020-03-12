package org.wall.mo.utils.screenshot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.wall.mo.utils.autolayout.ScreenUtils;

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
public class Screenshot {

    private final static Screenshot oScreenshot = new Screenshot();

    private MediaProjectionManager mMediaProjectionManager;

    private MediaProjection mMediaProjection;

    private VirtualDisplay mVirtualDisplay;

    private ImageReader mImageReader;

    private int mRequestCode;

    public static Screenshot getInstance() {
        return oScreenshot;
    }

    private Screenshot() {
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void requestMediaProject(Activity activity, int requestCode) {
        try {
            if (mMediaProjectionManager == null) {
                mMediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            }
            if (mMediaProjectionManager != null) {
                mRequestCode = requestCode;
                Intent screenCaptureIntent = mMediaProjectionManager.createScreenCaptureIntent();
                activity.startActivityForResult(screenCaptureIntent, mRequestCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        try {
            if (mRequestCode == requestCode && resultCode == Activity.RESULT_OK) {
                if (mMediaProjectionManager != null) {
                    mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startCapture(Activity activity) {
        try {
            int[] screenSize = ScreenUtils.getScreenSize(activity, false);
            if (mImageReader == null) {
                mImageReader = ImageReader.newInstance(screenSize[0], screenSize[1], PixelFormat.RGBA_8888, 2);
            }
            if (mMediaProjection != null && mImageReader != null) {
                //判断这个对象是否为空
                if (mVirtualDisplay == null) {
                    mVirtualDisplay = mMediaProjection.createVirtualDisplay(
                            "screen-mirror",
                            screenSize[0],
                            screenSize[1],
                            activity.getResources().getDisplayMetrics().densityDpi,
                            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                            mImageReader.getSurface(),
                            null,
                            null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
