package com.xiaowei.ndk.image.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.xiaowei.ndk.image.BitmapRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 磁盘缓存
 */

public class DiskBitmapCache{

    private static volatile DiskBitmapCache instance;

    public static final String imageCachePath = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/ImageCache";

    private static final byte[] lock = new byte[0];

    private int MB = 1024 * 1024;

    private int maxDiskSize = 50 * MB;


    public static DiskBitmapCache getInstance(Context context) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DiskBitmapCache();
                }
            }
        }
        return instance;
    }

    public int getAppVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void put(BitmapRequest request, Bitmap bitmap) {
        String urlMd5 = request.getUrlMd5();
        File file = new File(imageCachePath, urlMd5);
        File parentFile = file.getParentFile();
        // 如果文件夹不存在, 创建文件夹
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            // 将图片保存在本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap get(BitmapRequest request) {
        try {
            String urlMd5 = request.getUrlMd5();
            File file = new File(imageCachePath, urlMd5);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
