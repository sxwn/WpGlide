package com.xiaowei.ndk.image.cache;

import android.graphics.Bitmap;

import com.xiaowei.ndk.image.BitmapRequest;

public class DoubleLruCache {

    DiskBitmapCache mDiskBitmapCache;
    MemoryCacheUtil mMemoryCacheUtils;

    public DoubleLruCache(){
        mDiskBitmapCache = new DiskBitmapCache();
        mMemoryCacheUtils = new MemoryCacheUtil();
    }

    public Bitmap get(BitmapRequest br) {
        Bitmap bitmap = mMemoryCacheUtils.getBitmapFromMemory(br.getUrl());
        if (bitmap == null){
            bitmap = mDiskBitmapCache.get(br);
            if (bitmap == null){
                return null;
            }
        }
        return bitmap;
    }

    public void put(BitmapRequest br, Bitmap bitmap) {
        mDiskBitmapCache.put(br,bitmap);// 将图片保存在本地
        mMemoryCacheUtils.setBitmapToMemory(br.getUrl(), bitmap);// 将图片保存在内存
    }
}
