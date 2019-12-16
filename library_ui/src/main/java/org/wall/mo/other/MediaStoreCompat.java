package org.wall.mo.other;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import android.util.DisplayMetrics;

import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Copyright (C), 2018-2018
 * FileName: MediaStoreCompat
 * Author: ziqimo
 * Date: 2018/11/10 下午4:14
 * Description: ${根据知乎的库，直接抠出来 拍照库}
 * History: @see com.zhihu.matisse.internal.utils.MediaStoreCompat
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MediaStoreCompat {

    //private final WeakReference<Activity> mContext;
    //private final WeakReference<Fragment> mFragment;
    private CaptureStrategy mCaptureStrategy;
    private Uri mCurrentPhotoUri;
    private String mCurrentPhotoPath;

    public MediaStoreCompat(Activity activity) {
        //mContext = new WeakReference<>(activity);
        //mFragment = null;
    }

    public MediaStoreCompat(Activity activity, Fragment fragment) {
        //mContext = new WeakReference<>(activity);
        //mFragment = new WeakReference<>(fragment);
    }

    /**
     * Checks whether the device has a camera feature or not.
     *
     * @param context a context to check for camera feature.
     * @return true if the device has a camera feature. false otherwise.
     */
    public static boolean hasCameraFeature(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void setCaptureStrategy(CaptureStrategy strategy) {
        mCaptureStrategy = strategy;
    }

    public void dispatchCaptureIntent(Activity activity, Fragment fragment, int requestCode) {
        Context context = activity == null ? fragment.getActivity() : activity;
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                //适配7.0
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    mCurrentPhotoUri = Uri.fromFile(photoFile);
                } else {
                    mCurrentPhotoUri = FileProvider.getUriForFile(context,
                            mCaptureStrategy.authority, photoFile);
                }
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    List<ResolveInfo> resInfoList = context.getPackageManager()
                            .queryIntentActivities(captureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        context.grantUriPermission(packageName, mCurrentPhotoUri,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
                if (fragment != null) {
                    fragment.startActivityForResult(captureIntent, requestCode);
                } else {
                    activity.startActivityForResult(captureIntent, requestCode);
                }
            }
        }
    }

    public File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir;
        if (mCaptureStrategy.isPublic) {
            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!storageDir.exists()) storageDir.mkdirs();
        } else {
            storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }

        // Avoid joining path components manually
        File tempFile = new File(storageDir, imageFileName);

        // Handle the situation that user's external storage is not ready
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }

        return tempFile;
    }

    public Uri getCurrentPhotoUri() {
        return mCurrentPhotoUri;
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    //=============================================================================================
    //===============https://blog.csdn.net/qq1271396448/article/details/80510085===================
    //=============================================================================================
    //=============================================================================================

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        //传入图片路径
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    //旋将旋转后的图片翻转
    public static Bitmap toTurn(Context context, String path, int degree) {
        //Bitmap img = BitmapFactory.decodeFile(path);
        //这个压缩下图片的质量
        Bitmap img = getSmallBitmap(context, path);
        Matrix matrix = new Matrix();
        matrix.postRotate(degree); /*翻转90度*/
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }

    //将bitmap对象写到本地路径
    public static String saveBitmap(Bitmap mBitmap, String savePath) {
        File filePic;
        try {
            filePic = new File(savePath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
        return filePic.getAbsolutePath();
    }


    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {//图片所在SD卡的路径
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(filePath, options, reqWidth, reqHeight);//自定义一个宽和高
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getSmallBitmap(Context context, String filePath) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        //float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return getSmallBitmap(filePath, (int) (width * 0.75f), (int) (height * 0.75f));
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(String filePath, BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;//获取图片的高
        int width = options.outWidth;//获取图片的框
        if (height == -1 || width == -1) {
            //适配下这个作者的情况
            //https://www.jianshu.com/p/f269bcda335f#
            try {
                ExifInterface exifInterface = new ExifInterface(filePath);
                height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的高度
                width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的宽度
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int inSampleSize = 4;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;//求出缩放值
    }

    /**
     * 支持裁剪，会覆盖原图
     * https://blog.csdn.net/baidu_32363133/article/details/80488254
     *
     * @param activity
     * @param fragment
     * @param uriFile
     * @param requestCode
     */
    public static void startClip(Activity activity, Fragment fragment, Uri uriFile, int requestCode) {
        //这样会覆盖原图
        startClipOut(activity, fragment, uriFile, uriFile, requestCode);
    }

    /**
     * 裁剪，不会覆盖原图
     *
     * @param activity
     * @param fragment
     * @param uriFile
     * @param outUriFile
     * @param requestCode
     */
    public static void startClipOut(Activity activity, Fragment fragment, Uri uriFile, Uri outUriFile, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // android7.0设置输出文件的uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uriFile, "image/*");
        } else {
            intent.setDataAndType(uriFile, "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 3);
        //这是控制宽高比例，先不要控制这
//        intent.putExtra("outputX", 180);
//        intent.putExtra("outputY", 180);
        intent.putExtra("scale", false);
        intent.putExtra("return-data", false);//不返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUriFile); //直接写映射回去
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}
