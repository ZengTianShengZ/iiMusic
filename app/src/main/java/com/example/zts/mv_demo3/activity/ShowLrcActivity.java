package com.example.zts.mv_demo3.activity;

import android.content.Context;
import android.util.Log;

import com.example.zts.appbase.activity.BaseActivity;
import com.example.zts.appbase.view.MusicBottomBar;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.fragment.FragmentMusic;
import com.example.zts.mv_demo3.tools.LrcProcess;
import com.example.zts.mv_demo3.view.LrcView;

/**
 * Created by Administrator on 2016/3/18.
 */
public class ShowLrcActivity extends BaseActivity implements MusicBottomBar.CurrentProgressTime {

    private LrcView lrcView;
    private LrcProcess lrcProcess;
    private MusicBottomBar musicBottomBar;
    @Override
    public int getActivityLayout() {
        return R.layout.activity_show_lrc;
    }

    @Override
    public void initView(Context context) {
        lrcView = (LrcView) findViewById(R.id.lrcShowView);
        musicBottomBar = FragmentMusic.getMusicBottomBar1();
    }

    @Override
    public void initData() {

        String lrcString =  getIntent().getStringExtra("lrcString");
        lrcProcess = new LrcProcess();
        musicBottomBar.setCurrentProgressTime(this);

        //lrcView.setText(lrcProcess.readLrcString(lrcString));
        lrcView.setLrcMap(lrcProcess.readLRC(lrcString));
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void setCurrentTime(int second) {

        Log.i("second","::"+second);
        lrcView.setIndex(second/1000);
    }
}
