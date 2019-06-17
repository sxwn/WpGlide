package com.xiaowei.ndk;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xiaowei.ndk.image.RequestListener;
import com.xiaowei.ndk.image.WpGlide;

/**
 * 1.新建工程
 * 2.配置ndk路径
 * 3.编译生成.class文件,选择Build，点击make project  2.2版本可以,3.3版本没有,可能移动到别的地方了
 * 4.定义本地方法
 * 5.在studio中生成jni目录,以及对应的.h头文件
 * 编译MainActivity，在控制台,进入cd app/src/main/java
 */

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    private LinearLayout scroll_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scroll_ll = findViewById(R.id.container);
        findViewById(R.id.loadmore_image).setOnClickListener(new Listenr());
    }

    class Listenr implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            onButtonClick();
        }
    }

    public void onButtonClick() {
        for (int i = 0; i <= 10; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            scroll_ll.addView(imageView);
            String url = "";
            switch (i) {
                case 0:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314539946&di=a1144fd29a2696c1ffc375bf15c428d8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ae35937b728a8012193a3712e10.jpg%401280w_1l_2o_100sh.jpg";
                    break;
                case 1:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314539946&di=2166c8394f51842258b8d4116ca01640&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01825f5937b70ea8012193a3027f84.jpg%401280w_1l_2o_100sh.jpg";
                    break;
                case 2:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314540004&di=d8bf88737384be0c0d657eda6f8728cb&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161215%2F8208d5bc2d1c4c88847a58d9046a6826_th.jpeg";
                    break;
                case 3:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314540003&di=b4e659dc6c0d05fc470d8a811ced5f43&imgtype=0&src=http%3A%2F%2Fwww.21xc.com%2Fpic%2F201710%2F05%2F1c121b8e-0817-4c8e-8503-95973cd5ea43.jpg";
                    break;
                case 4:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314703086&di=d2e10c6dfea682b25990571811320aec&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Forignal%2F497837b643d8eacd02729";
                    break;
                case 5:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314703086&di=a25f4f1bd3dfea8ad6d44fa3e04e1d86&imgtype=0&src=http%3A%2F%2Fww1.sinaimg.cn%2Flarge%2Fa68d6077jw1eqeahqr4blj20zk0qo7e4.jpg";
                    break;
                case 6:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314739941&di=ef8b05b41e5585f966126d04982aa145&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a16757b530180000012e7e8c06ed.jpg%401280w_1l_2o_100sh.jpg";
                    break;
                case 7:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314739939&di=38e60cbf4674a4397d84dfed14bdf477&imgtype=0&src=http%3A%2F%2Fs3.sinaimg.cn%2Forignal%2F4729da286c08145c34962";
                    break;
                case 8:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314739939&di=cd9edf9c7511fd06bbc1ab08a067cde6&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F6b4e731bjw1f3kwveu2apj20zk0qon1x.jpg";
                    break;
                case 9:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560314739939&di=c791dc06abf92f5e3b4b22ff5881430f&imgtype=0&src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2Ffd%2Ftg%2Fg5%2FM04%2FDF%2FD7%2FCggYr1cxYfaAPopYAAGSAkvaHLQ343.jpg";
                    break;
                default:
                    break;
            }
            WpGlide.with(MainActivity.this).load(url).loading(R.drawable.ic_launcher_background).listener(new RequestListener() {
                @Override
                public boolean onSuccess(Bitmap bitmap) {
                    Toast.makeText(MainActivity.this, "comeing", Toast.LENGTH_LONG).show();
                    return false;
                }

                @Override
                public boolean onFailure() {
                    return false;
                }
            }).into(imageView);
        }
    }
}
