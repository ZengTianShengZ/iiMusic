package com.example.zts.mv_demo3.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.appbase.view.AudioBar;
import com.example.zts.appbase.view.MusicBottomBar;
import com.example.zts.mv_demo3.MyApplication;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.activity.ShowLrcActivity;
import com.example.zts.mv_demo3.adapter.MusicLocalAdapter;
import com.example.zts.mv_demo3.domain.AudioMediaBean;
import com.example.zts.mv_demo3.domain.OnlineMusicBean;
import com.example.zts.mv_demo3.server.MusicServer;
import com.example.zts.mv_demo3.tools.IteratorLrcFile;
import com.example.zts.mv_demo3.tools.LrcProcess;
import com.example.zts.mv_demo3.tools.OnlineMusicPlay;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentMusicLocal extends BaseFragment implements MusicBottomBar.LocalMusicButOnClick {

    private View listItemView;
    private ListView mlistView;
    private MusicBottomBar musicBottomBar;
    private AudioBar mAudioBar;
    private AudioBar flageAudioBar;

    private boolean MusicStatic = false;
    private int itemPosition ;
    private static final int mediaPauseImg = R.drawable.ic_media_pause;
    private static final int mediaPlayImg = R.drawable.ic_media_play;
    private String lrcString;
    public static final String BroadCase_Action = "com.example.zts.mv_demo3.fragment.BroadCase_Action";
    public static final String BroadCast_Server = "com.example.zts.mv_demo3.server.BroadCase_Action";

    private ArrayList<AudioMediaBean> listBean= new ArrayList<AudioMediaBean>();
    private ArrayList<String> stringUrlList= new ArrayList<String>();
    private MusicLocalAdapter mMusicLocalAdapter;

    private OnlineMusicPlay mOnlineMusicPlay;
    private FragmentMusicBroadCast mFragmentMusicBroadCast;
    private IteratorLrcFile iteratorLrcFile;

    private Context context;
    private Intent intent;
    private AudioMediaBean amBean ;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_music_local;
    }

    @Override
    public void initView(View rootview) {
        context = getActivity();
        mlistView = (ListView) rootview.findViewById(R.id.music_local_list);

        musicBottomBar = FragmentMusic.getMusicBottomBar1();
        musicBottomBar.setLocalMusicButOnClick(this);
        musicBottomBar.setOnlineMusicButOnClick(null);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCase_Action);
        mFragmentMusicBroadCast = new FragmentMusicBroadCast();
        getContext().registerReceiver(mFragmentMusicBroadCast, intentFilter);
    }

    @Override
    public void initData() {

        mOnlineMusicPlay = OnlineMusicPlay.getOnlineMusicPlay();
        mOnlineMusicPlay.replayMediaPlayer();

        amBean = AudioMediaBean.getmAudioMediaBean();
        Cursor cursor = getContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Audio.Media.DURATION+">?",new String[]{"180000"},
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if(cursor != null){

            int id;
            String tilte;
            String album;
            String artist;
            String url;
            int duration;
            int size;

            cursor.moveToFirst();
            //歌曲ID：MediaStore.Audio.Media._ID
            int Media_id = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            //歌曲的名称 ：MediaStore.Audio.Media.TITLE
            int Media_title =  cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
            int Media_album = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
            int Media_artist = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
            int Media_url = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA );
            //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
            int Media_duration = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
            int Media_size = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);

            do {
                id = cursor.getInt(Media_id);
                tilte = cursor.getString(Media_title);
                album = cursor.getString(Media_album);
                artist = cursor.getString(Media_artist);
                url = cursor.getString(Media_url);
                duration = cursor.getInt(Media_duration);
                size = cursor.getInt(Media_size);

                amBean = new AudioMediaBean(id,tilte,album,artist,url,duration,size);
                listBean.add(amBean);

                stringUrlList.add(url);

            }while (cursor.moveToNext());
            amBean.setStringUrlList(stringUrlList);
            cursor.close();
        }


        mMusicLocalAdapter = new MusicLocalAdapter(context,listBean,R.layout.music_local_listiten);
        mlistView.setAdapter(mMusicLocalAdapter);

        // 遍历歌词
        iteratorLrcFile = new IteratorLrcFile(context);

    }


    @Override
    public void initEvent() {

        /**
         * 启动 MusicServer
         */
        intent = new Intent(context, MusicServer.class);
        intent.putStringArrayListExtra("stringUrlList", stringUrlList);
        context.startService(intent);
        /**
         * 给 MusicServerBroadcast 发送广播
         */
        intent = new Intent(BroadCast_Server);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mOnlineMusicPlay.pause();
                // 一组按钮公用，所以先屏蔽第一个接口 打开第二个接口
                musicBottomBar.setOnlineMusicButOnClick(null);
                musicBottomBar.setLocalMusicButOnClick(FragmentMusicLocal.this);
                OnlineMusicBean mOnlineMusicBean = OnlineMusicBean.getOnlineMusicBean();
                mOnlineMusicBean.setOnlinePosition(0);
                intentPutExtra(position, false);
                amBean.setFrom_Server_position(position);
                FragmentMusicLocal.this.itemPosition = position;
                MusicStatic = true;
                musicBottomBar.setMusicPauseImg(mediaPauseImg);
                musicBottomBar.setMusicTilteTv(listBean.get(position).getTilte());
                musicBottomBar.setMusicArtistTv(listBean.get(position).getArtist());

                // 得到 歌词路径
                lrcString = iteratorLrcFile.getLrcPath(listBean.get(position).getTilte());
                Log.i("lrcString",".."+lrcString);
                //storage/emulated/0/Music/Lyric/Loco & Mamamoo - 이 노래.lrcLoco & Mamamoo - 이 노래.lrc

                setAudioBarVisibility(position);
            }
        });
       mlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(AbsListView view, int scrollState) {

           }

           @Override
           public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               listItemView = view;
               if (firstVisibleItem < itemPosition && itemPosition < (firstVisibleItem + visibleItemCount)) {
                   //  showToast("" + itemPosition + ".." + (firstVisibleItem + visibleItemCount));
                   if (mAudioBar != null) {
                       mAudioBar.setVisibility(View.VISIBLE);
                   } else {
                       setAudioBarVisibility(itemPosition);
                   }
               } else {
                   if (mAudioBar != null) {
                       mAudioBar.setVisibility(View.GONE);
                   }
               }
           }
       });

    }


    @Override
    public void linOnClick() {

        Intent intent = new Intent(getActivity(),ShowLrcActivity.class);
        intent.putExtra("lrcString",lrcString);
        startActivity(intent);
    }

    @Override
    public void privateOnClick() {
        int pos = 0 ;
        if(itemPosition-1<0){
            pos = listBean.size();
            itemPosition = listBean.size();
        }else{
            pos = itemPosition-1;
            itemPosition = itemPosition - 1;
        }
        Log.i("click", "privateOnClick.." + pos + "..." + listBean.size());
        intentPutExtra(pos, false);
        setAudioBarVisibility(pos);
        musicBottomBar.setMusicTilteTv(listBean.get(pos).getTilte());
        musicBottomBar.setMusicArtistTv(listBean.get(pos).getArtist());
    }

    @Override
    public void pauseOnClick() {
        if(MusicStatic){
            MusicStatic = false;
            musicBottomBar.setMusicPauseImg(mediaPlayImg);
            musicBottomBar.showRoundProgressBar(false);
            intentPutExtra(itemPosition,true);
            mAudioBar.setAudioBarHeightToRadiom(true);
        }else {
            MusicStatic = true;
            musicBottomBar.setMusicPauseImg(mediaPauseImg);

            musicBottomBar.showRoundProgressBar(true);
            intentPutExtra(itemPosition, false);
            if(mAudioBar != null){
                mAudioBar.setAudioBarHeightToRadiom(false);
            }else{
                setAudioBarVisibility(itemPosition);
            }
        }
        Log.i("click", "pauseOnClick.." + MusicStatic);

    }

    @Override
    public void nextOnClick() {
        int pos = 0 ;
        if(itemPosition+1>listBean.size()){
            pos = 0;
            itemPosition = 0;
        }else{
            pos = itemPosition+1;
            itemPosition = itemPosition + 1;
        }
        Log.i("click","nextOnClick.."+pos+"..." + listBean.size());
        intentPutExtra(pos,false);
        setAudioBarVisibility(pos);
        musicBottomBar.setMusicTilteTv(listBean.get(pos).getTilte());
        musicBottomBar.setMusicArtistTv(listBean.get(pos).getArtist());
    }

    private class FragmentMusicBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //String stringUrl = intent.getStringExtra("BroadPositions");
            int broadPositions =  intent.getIntExtra("BroadPositions",-1);
            Log.i("broadPositions","broadPositions.."+broadPositions);
            musicBottomBar.setRoundProgressBarMax(listBean.get(broadPositions).getDuration(),0);
            musicBottomBar.showRoundProgressBar(true);
            musicBottomBar.setMusicTilteTv(listBean.get(broadPositions).getTilte());
            musicBottomBar.setMusicArtistTv(listBean.get(broadPositions).getArtist());
        }
    }


    void intentPutExtra(int position,boolean TorF){
        intent.putExtra("position", position);
        intent.putExtra("MusicStatic", TorF);
        context.sendBroadcast(intent);
    }

    private AudioBar setAudioBarVisibility(int position) {
        if (flageAudioBar != null) {
            flageAudioBar.setVisibility(View.GONE);
        }
        //2016-2-18 修改，用 setTag，getTag 避免界面出现多个 AudioBar，还没验证行不行
        //  mAudioBar = (AudioBar) view.getTag(position);
        //   mAudioBar = (AudioBar)  mlistView.getChildAt(position).findViewById(R.id.list_item_AudioBar);
        try {
            if(mlistView.getChildAt(position) != null){
                mAudioBar = (AudioBar) mlistView.getChildAt(position).findViewById(R.id.list_item_AudioBar);
            }else{
                mAudioBar =  (AudioBar)  listItemView.findViewById(R.id.list_item_AudioBar);
            }
            mAudioBar.setVisibility(View.VISIBLE);
            flageAudioBar = mAudioBar;
        }catch (Exception e){
            showToast("Exception");
        }
        return mAudioBar;

    }
}
//317