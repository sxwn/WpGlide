package com.xiaowei.ndk.image;

import android.content.Context;
import android.widget.ImageView;

import com.xiaowei.ndk.util.Md5Util;

import java.lang.ref.SoftReference;

/**
 * 图片请求
 */
public class BitmapRequest {
    //图片url
    private String url;
    //图片加载控件
    private SoftReference<ImageView> imageView;
    //占位图
    private int resId;
    //回调对象
    private RequestListener requestListener;
    //图片标识
    private String urlMd5;

    private Context context;

    public BitmapRequest(Context context){
        this.context = context;
    }

    public BitmapRequest load(String  url){
        this.url = url;
        this.urlMd5 = Md5Util.md5(url);
        return this;
    }

    public BitmapRequest loading(int resId){
        this.resId = resId;
        return this;
    }

    public BitmapRequest listener(RequestListener listener){
        this.requestListener = listener;
        return this;
    }

    public void  into(ImageView imageView){
        imageView.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(imageView);
        //将图片请求添加到队列中
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }
}
