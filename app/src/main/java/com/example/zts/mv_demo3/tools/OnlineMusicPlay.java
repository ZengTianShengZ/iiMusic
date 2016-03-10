package com.example.zts.mv_demo3.tools;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.example.zts.appbase.view.RoundProgressBar;
import com.example.zts.mv_demo3.MyApplication;
import com.example.zts.mv_demo3.domain.OnlineMusicBean;
import com.example.zts.mv_demo3.fragment.FragmentMusic;
import com.example.zts.mv_demo3.fragment.FragmentMusicOnline;
import com.example.zts.mv_demo3.server.MusicServer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZTS on 2016/1/19.
 */
public class OnlineMusicPlay implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;

    private static OnlineMusicPlay onlineMusicPlay;
    public static OnlineMusicPlay getOnlineMusicPlay(){
        if (onlineMusicPlay == null){

            onlineMusicPlay = new OnlineMusicPlay();
        }

        return onlineMusicPlay;
    }

    public void replayMediaPlayer(){

        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        }
    }

    public void play() {
        mediaPlayer.start();
    }

    /**
     * @param url url地址
     *
     */
    public void playUrl(String url) {

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 暂停
    public void pause() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // 停止
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public int mediaPlayerGetDuration(){
        return mediaPlayer.getDuration();
    }
    /**
     * 缓冲更新
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    // 播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    // 播放准备
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    //mFragmentMusic.mRoundProgressBar.setProgress(progressNum);
}
