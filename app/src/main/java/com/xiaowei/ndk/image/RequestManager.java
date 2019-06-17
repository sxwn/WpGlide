package com.xiaowei.ndk.image;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 请求队列
 * 1、管理请求
 * 2、开辟窗口
 */

public class RequestManager {

    private static  RequestManager requestManager = new RequestManager();

    //创建队列
    private LinkedBlockingQueue<BitmapRequest> requestQuene = new LinkedBlockingQueue<>();

    private RequestManager(){
        start();
    }

    private void start() {
        stop();
        startAllDispater();
    }

    //创建线程数组
    private BitmapDispatcher[] bitmapDispatchers;

    public static RequestManager getInstance(){
        return requestManager;
    }

    //将图片请求添加到队列
    public void addBitmapRequest(BitmapRequest bitmapRequest){
        if (bitmapRequest == null)
            return;
        if (!requestQuene.contains(bitmapRequest)){
            requestQuene.add(bitmapRequest);
        }
    }
    //提醒窗口开启服务
    private void startAllDispater(){
        //获取手机支持的单个应用的最大线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount ; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQuene);
            bitmapDispatcher.start();
            //将每个dispater放到数组中，方便统一管理
            bitmapDispatchers[i] = bitmapDispatcher;
        }
    }
    //停止所有线程
    private void stop() {
        if (bitmapDispatchers!=null && bitmapDispatchers.length>0){
            for (BitmapDispatcher bitmapDispater:bitmapDispatchers) {
                if (!bitmapDispater.isInterrupted()){
                    bitmapDispater.interrupt();
                }
            }
        }
    }

}
