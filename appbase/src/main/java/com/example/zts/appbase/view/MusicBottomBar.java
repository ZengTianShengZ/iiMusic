package com.example.zts.appbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zts.appbase.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/25.
 */
public class MusicBottomBar extends RelativeLayout {

    private LayoutParams bottomBarParams;

    private LinearLayout bottom_lin_tv;
    private ImageView music_previous, music_pause, music_next;
    private TextView music_tilteTv,music_artistTv;
    private CircleImageView circleImageView;
    private RoundProgressBar roundProgressBar;

    private static boolean timerTaskStatic = false;
    private static int progressNum;
    private static int progressMaxNum;
    private int bottomWidth;


/*    private MusicPrivateOnClick musicPrivateOnClick;
    public interface MusicPrivateOnClick {
        public void privateOnClick();
    }
    private MusicPauseOnClick musicPauseOnClick;
    public interface MusicPauseOnClick {
        public void pauseOnClick();
    }*/
    private CurrentProgressTime currentProgressTime;
    public interface CurrentProgressTime {
        public void setCurrentTime(int second);
    }
    public void setCurrentProgressTime(CurrentProgressTime currentTime){
        this.currentProgressTime = currentTime;
    }
    private LocalMusicButOnClick localMusicButOnClick;
    public interface LocalMusicButOnClick {
        public void linOnClick();
        public void privateOnClick();
        public void pauseOnClick();
        public void nextOnClick();
    }
    private OnlineMusicButOnClick onlineMusicButOnClick;
    public interface OnlineMusicButOnClick {
        public void privateOnClick();
        public void pauseOnClick();
        public void nextOnClick();
    }

    public MusicBottomBar(Context context) {
        this(context, null);

    }

    public MusicBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
 /*      TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.MusicBottomBar);
        bottomWidth = (int) ta.getDimension(R.styleable.MusicBottomBar_bottomWidth,100);*/

        mTimer.schedule(timerTask, 0, 1000);


        View view = View.inflate(context, R.layout.music_bottom_bar, null);
        bottom_lin_tv = (LinearLayout) view.findViewById(R.id.bottom_lin_tv);
        music_tilteTv = (TextView) view.findViewById(R.id.bottom_music_tilteTv);
        music_artistTv = (TextView) view.findViewById(R.id.bottom_music_artistTv);
        music_previous = (ImageView) view.findViewById(R.id.bottom_music_previous);
        music_pause = (ImageView) view.findViewById(R.id.bottom_music_pause);
        music_next = (ImageView) view.findViewById(R.id.bottom_music_next);
        circleImageView = (CircleImageView) view.findViewById(R.id.bottom_music_cImg);
        roundProgressBar = (RoundProgressBar) view.findViewById(R.id.bottom_roundProgressBar);


        bottomBarParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        bottomBarParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        // 加载的 xml 布局 要 放在 父容器 里面 并 addview 才能显示
        addView(view, bottomBarParams);

        bottom_lin_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localMusicButOnClick != null) {
                    localMusicButOnClick.linOnClick();
                }
            }
        });
        music_previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localMusicButOnClick != null) {
                    localMusicButOnClick.privateOnClick();
                }
                if (onlineMusicButOnClick != null) {
                    onlineMusicButOnClick.privateOnClick();
                }
            }
        });
        music_pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localMusicButOnClick != null){
                    localMusicButOnClick.pauseOnClick();
                }
                if(onlineMusicButOnClick != null){
                    onlineMusicButOnClick.pauseOnClick();
                }
            }
        });
        music_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localMusicButOnClick != null){
                    localMusicButOnClick.nextOnClick();
                }
                if(onlineMusicButOnClick != null){
                    onlineMusicButOnClick.nextOnClick();
                }
            }
        });
    }

    public void setMusicTilteTv(String tilet){
        music_tilteTv.setText(tilet);
    }
    public void setMusicArtistTv(String artistTv){
        music_artistTv.setText(artistTv);
    }
    public void setMusicPauseImg(int resId){
        music_pause.setImageResource(resId);
    }

/*    public void setMusicPrivateOnClick(MusicButOnClick onClick){
        this.musicButOnClick = onClick;
    }
    public void setMusicPauseOnClick(MusicButOnClick onClick){
        this.musicButOnClick = onClick;
    }*/
    public void setLocalMusicButOnClick(LocalMusicButOnClick onClick){
        this.localMusicButOnClick = onClick;
    }
    public void setOnlineMusicButOnClick(OnlineMusicButOnClick onClick){
        this.onlineMusicButOnClick = onClick;
    }
    public void setCircleImageView(int resId){
        circleImageView.setImageResource(resId);
    }

    public void setRoundProgressBarMax(int proMax,int proNum){
        this.progressMaxNum = proMax;
        this.progressNum = proNum;
        roundProgressBar.setMax(proMax);
        // roundProgressBar.setProgress();

    }
    public void showRoundProgressBar(boolean timerTaskStatic){
        this.timerTaskStatic  = timerTaskStatic;
/*        if(timerTaskStatic){
            mTimer.notify();
        }else{
            try {
                mTimer.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
    private Timer mTimer = new Timer(); // 计时器
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            if (timerTaskStatic) {
                progressNum = progressNum + 1000;
                if(currentProgressTime != null){
                    currentProgressTime.setCurrentTime(progressNum);
                }
                if (progressNum > progressMaxNum) {
                    progressNum = progressMaxNum;
                }
                roundProgressBar.setProgress(progressNum);
            }

        }
    };


}

