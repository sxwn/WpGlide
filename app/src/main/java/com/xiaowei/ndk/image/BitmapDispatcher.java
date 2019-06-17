package com.xiaowei.ndk.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import com.xiaowei.ndk.image.cache.DoubleLruCache;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 窗口
 */

public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());
    //创建一个阻塞队列
    private LinkedBlockingQueue<BitmapRequest> requestQuene;

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQuene) {
        this.requestQuene = requestQuene;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                //从队列中获取到图片请求
                BitmapRequest br = requestQuene.take();
                //设置占位图片
                showLoadingImage(br);
                //加载具体的图片
                Bitmap bitmap = findBitmap(br);
                //将图片显示在view上
                showImageView(br, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
        if (br != null && br.getImageView()!=null && br.getUrlMd5().equals(br.getImageView().getTag())){
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    if (br.getRequestListener()!=null){
                        RequestListener listener = br.getRequestListener();
                        listener.onSuccess(bitmap);
                    }
                }
            });
        }
    }
    private DoubleLruCache doubleLruCache;

    private Bitmap findBitmap(BitmapRequest br) {
        doubleLruCache = new DoubleLruCache();
        Bitmap bitmap = null;
        bitmap = doubleLruCache.get(br);
        if (bitmap == null){
            bitmap = downLoadBitmap(br.getUrl());
            if (bitmap!=null){
                doubleLruCache.put(br,bitmap);
            }
        }
        return bitmap;
    }

    private Bitmap downLoadBitmap(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            //创建一个url对象
            URL url = new URL(uri);
            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
            is = coon.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void showLoadingImage(BitmapRequest br) {
        if (br.getResId() > 0 && br.getImageView() != null) {
            final int resId = br.getResId();
            final ImageView imageView = br.getImageView();
            //内存溢出
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }
    }
}
