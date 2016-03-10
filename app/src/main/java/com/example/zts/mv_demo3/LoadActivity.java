package com.example.zts.mv_demo3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.zts.appbase.activity.BaseActivity;


/**
 * Created by Administrator on 2016/3/1.
 */
public class LoadActivity extends Activity {

    private ImageView imageView;
    private static final int LOAD_DISPLAY_TIME = 1500;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.activity_load);

        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 3000);

    }

    class splashhandler implements Runnable{
        public void run() {
            startActivity(new Intent(getApplication(),MainActivity_.class));
            LoadActivity.this.finish();
        }
    }
}

