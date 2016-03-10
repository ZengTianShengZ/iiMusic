package com.example.zts.mv_demo3;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.bmob.v3.Bmob;


/**
 * Created by ZTS on 2016/1/11.
 */
public class MyApplication extends Application {

    private Handler handler;
    private ListView listview;
    public Handler getHandler(){
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ListView getListview() {
        return listview;
    }

    public void setListview(ListView listview) {
        this.listview = listview;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this,"bca6d4c124b02cc9947a9541776ca17c");
        initImageLoader(getApplicationContext());


    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.discCacheFileNameGenerator(new Md5FileNameGenerator());
        config.discCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
