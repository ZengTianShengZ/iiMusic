package com.example.zts.mv_demo3.Broadcast;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.zts.mv_demo3.R;

/**
 * Created by ZTS on 2016/1/20.
 */
public class MusicRemoteView extends AppWidgetProvider {
    private RemoteViews remoteView;
    private ComponentName componentName;
    private int progress;
    private int maxProgerss;
    private boolean musicStatic;
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);


    }
    @Override
    public void onReceive(Context context, Intent intent) {
        maxProgerss = intent.getIntExtra("maxProgerss",-1);
        musicStatic = intent.getBooleanExtra("MusicStatic",false);
        super.onReceive(context, intent);

        remoteView = new RemoteViews(context.getPackageName(), R.layout.remoteview);

        componentName = new ComponentName(context,MusicRemoteView.class);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        progress += 500;
        remoteView.setProgressBar(R.id.remote_progressbar, 400, 300,false);
        Log.i("MusicRemoteView","...progress..."+progress);
        if(musicStatic){
            remoteView.setImageViewResource(R.id.remote_music_pause,R.drawable.ic_media_play);
        }else {
            remoteView.setImageViewResource(R.id.remote_music_pause,R.drawable.ic_media_pause);
        }
        appWidgetManager.updateAppWidget(componentName, remoteView);
        if(progress > maxProgerss){
            progress = 0;
        }
    }

}
