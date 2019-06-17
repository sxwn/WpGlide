package com.xiaowei.ndk.image.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存
 */

public class MemoryCacheUtil {
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtil() {
        //给LruCache手动设置一个内存缓存上限,当大小超过时就回收一部分.底部采用了HashMap.
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;// 模拟器默认是16M
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getRowBytes() * value.getHeight();// 获取图片占用内存大小
                return byteCount;
            }
        };
    }

    /**
     * 从内存读
     *
     * @param url
     */
    public Bitmap getBitmapFromMemory(String url) {
        return mMemoryCache.get(url);
    }

    /**
     * 写内存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
}
