package org.wall.mo.utils.screenshot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
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
 *
 * https://github.com/goodbranch/ScreenCapture
 *
 *
 * History: 5.0截屏工具类
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class Screenshot {
    private static final Screenshot ourInstance = new Screenshot();

    private MediaProjectionManager mMediaProjectionManager;

    private MediaProjection mMediaProjection;

    private ImageReader mImageReader;

    public static Screenshot getInstance() {
        return ourInstance;
    }

    private Screenshot() {
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void requestMediaProject(Fragment fragment, int requestCode) {
        if (mMediaProjectionManager == null) {
            mMediaProjectionManager = (MediaProjectionManager) fragment.getActivity().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        }
        if (mMediaProjectionManager != null) {
            fragment.startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), requestCode);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (mMediaProjectionManager != null) {
            mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startCapture(Fragment fragment) {
        int[] screenSize = ScreenUtils.getScreenSize(fragment.getActivity(), false);
        if (mImageReader == null) {
            mImageReader = ImageReader.newInstance(screenSize[0], screenSize[1], PixelFormat.RGBA_8888, 2);
        }
        if (mMediaProjection != null && mImageReader != null) {
            mMediaProjection.createVirtualDisplay(
                    "screen-mirror",
                    screenSize[0],
                    screenSize[1],
                    fragment.getActivity().getResources().getDisplayMetrics().densityDpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    mImageReader.getSurface(),
                    null,
                    null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Bitmap screenshot() {
        if (mImageReader == null) {
            return null;
        }
        Image image = mImageReader.acquireLatestImage();
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
        image.close();
        return bitmap;
    }
}
