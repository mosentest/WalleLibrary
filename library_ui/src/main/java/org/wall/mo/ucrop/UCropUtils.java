package org.wall.mo.ucrop;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

/**
 * https://blog.csdn.net/liutaoblog/article/details/52452410
 * Copyright (C), 2018-2018
 * Author: ziqimo
 * Date: 2018/12/13 下午8:08
 * Description: ${DESCRIPTION}
 * History:
 * //图片裁剪库 https://github.com/Yalantis/uCrop
 * implementation 'com.github.yalantis:ucrop:2.2.2'
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class UCropUtils {

    /**
     * 启动裁剪
     *
     * @param activity       上下文
     * @param sourceFilePath 需要裁剪图片的绝对路径
     * @param requestCode    比如：UCrop.REQUEST_CROP
     * @param aspectRatioX   裁剪图片宽高比
     * @param aspectRatioY   裁剪图片宽高比
     * @return
     */
    public static String startUCrop(Activity activity, Fragment fragment, String sourceFilePath,
                                    int requestCode, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
        //裁剪后图片的绝对路径
        String cameraScalePath = outFile.getAbsolutePath();
        Uri destinationUri = Uri.fromFile(outFile);
        //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
//        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.zhihu_primary));
        //设置状态栏颜色
//        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.zhihu_primary_dark));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);
        //UCrop配置
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
        //uCrop.useSourceImageAspectRatio();
        //跳转裁剪页面
        uCrop.start(activity, fragment, requestCode);
        return cameraScalePath;
    }
}
