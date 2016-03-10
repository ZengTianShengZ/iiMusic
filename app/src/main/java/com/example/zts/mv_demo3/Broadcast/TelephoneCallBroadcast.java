package com.example.zts.mv_demo3.Broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.example.zts.mv_demo3.fragment.FragmentMusicOnline;
import com.example.zts.mv_demo3.tools.OnlineMusicPlay;

/**
 * Created by Administrator on 2016/3/10.
 */
public class TelephoneCallBroadcast extends BroadcastReceiver{
    private OnlineMusicPlay mOnlineMusicPlay;
    private Intent intent;
    @Override
    public void onReceive(Context context, Intent intent) {

        mOnlineMusicPlay = OnlineMusicPlay.getOnlineMusicPlay();
        mOnlineMusicPlay.replayMediaPlayer();
        intent = new Intent(FragmentMusicOnline.BroadCast_Server);
        intent.putExtra("position", -2);
        intent.putExtra("MusicStatic", true);

        // 如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            mOnlineMusicPlay.pause();
            context.sendBroadcast(intent);
        } else {
            // 如果是来电
            TelephonyManager tManager =
                    (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    mOnlineMusicPlay.pause();
                    context.sendBroadcast (intent);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 接通电话（即挂起）

                    break;
                case TelephonyManager.CALL_STATE_IDLE:

                    break;
            }
        }
    }
}
