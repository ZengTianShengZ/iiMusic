package com.example.zts.mv_demo3.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.zts.mv_demo3.MyApplication;
import com.example.zts.mv_demo3.domain.AudioMediaBean;
import com.example.zts.mv_demo3.domain.xlh;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ZTS on 2016/1/8.
 */
public class MusicServer extends Service {

    private static MediaPlayer mPlayer;
    private ArrayList<String> stringUrlList= new ArrayList<String>();
    private int Broad_positions;
    private boolean MusicStatic;
    private static Intent intentBd;
    public static final String BroadCase_Action ="com.example.zts.mv_demo3.server.BroadCase_Action";
    private MusicServiceBroadCast mMusicServiceBroadCast;
    private Context context;
    private int duration;
    private AudioMediaBean amBean;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mPlayer = new MediaPlayer();
        amBean = AudioMediaBean.getmAudioMediaBean();
        mPlayer.setOnCompletionListener(new OnCompletionListions());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCase_Action);
        mMusicServiceBroadCast = new MusicServiceBroadCast();
        context.registerReceiver(mMusicServiceBroadCast, intentFilter);

        intentBd = new Intent("com.example.zts.mv_demo3.fragment.BroadCase_Action");
    }

    private class OnCompletionListions implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            Broad_positions = Broad_positions+1;
            if(Broad_positions>stringUrlList.size()){
                Broad_positions = 0;
            }

            if(stringUrlList == null)
                return;

            playMusic(stringUrlList.get(Broad_positions), Broad_positions);
            amBean.setFrom_Server_position(Broad_positions);
            intentBd.putExtra("BroadPositions", Broad_positions);
            sendBroadcast(intentBd);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        stringUrlList = intent.getStringArrayListExtra("stringUrlList");
        if(stringUrlList!= null){
            amBean.setStringUrlList(stringUrlList);
        }
         return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void playMusic(String url,int pos){

        if(url != null){
            mPlayer.stop();
            try {
                mPlayer.reset();
                mPlayer.setDataSource(url);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class MusicServiceBroadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

            MusicStatic = intent.getBooleanExtra("MusicStatic", false);
            Broad_positions = intent.getIntExtra("position", -1);
            if(MusicStatic){
                if(mPlayer.isPlaying()){
                    mPlayer.pause();

                }else{
                    return;
                }
            }else{

                if(Broad_positions == amBean.getFrom_Server_position()){
                    mPlayer.start();

                }else{
                    duration =intent.getIntExtra("duration", -1);
                    Log.i("MusicServer", ".Broad_positions..."+Broad_positions+"...stringUrlList.."+stringUrlList.get(Broad_positions));
                    playMusic(stringUrlList.get(Broad_positions), Broad_positions);
                    intentBd.putExtra("BroadPositions", Broad_positions);
                    sendBroadcast(intentBd);
                }
                amBean.setFrom_Server_position(Broad_positions);
            }
        }
    }

}
