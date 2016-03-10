package com.example.zts.mv_demo3.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.zts.appbase.activity.BaseActivity;
import com.example.zts.mv_demo3.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by ZTS on 2016/1/29.
 */

public class VideoPlayActivity extends BaseActivity {

    private VideoView videoView;
    private ImageView imgButton;

    private MediaController mediaController;
    @Override
    public int getActivityLayout() {
        return R.layout.video_play;
    }

    @Override
    public void initView(Context context) {
        videoView = (VideoView) findViewById(R.id.videoView);
        imgButton = (ImageView) findViewById(R.id.videoImg_Btn);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String videoPath= intent.getStringExtra("videoPath");
        if(videoPath != null){
            mediaController = new MediaController(this);
            videoView.setVideoPath(videoPath);
            videoView.setMediaController(mediaController);
            videoView.requestFocus();
            videoView.isPlaying();
            videoView.setOnCompletionListener(new MediaCompletionListener());
            //videoView.setOnClickListener(new VideoViewOnClickListener());
        }

    }

    public class MediaCompletionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            finish();
        }
    }
    @Override
    public void initEvent() {
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
