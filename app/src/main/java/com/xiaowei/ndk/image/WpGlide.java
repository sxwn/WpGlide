package com.xiaowei.ndk.image;

import android.content.Context;

public class WpGlide {

    public static BitmapRequest with(Context context){
        return new BitmapRequest(context);
    }
}
