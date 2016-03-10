package com.example.zts.mv_demo3.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.appbase.view.MusicBottomBar;
import com.example.zts.appbase.view.Topbar;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.adapter.MusicOnlineAdapter;
import com.example.zts.mv_demo3.domain.AudioMediaBean;
import com.example.zts.mv_demo3.domain.OnlineMusicBean;
import com.example.zts.mv_demo3.tools.OnlineMusicPlay;
import com.example.zts.mv_demo3.tools.ViewAnimationShow;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentMusicOnline extends BaseFragment implements MusicBottomBar.OnlineMusicButOnClick {

   // private GridView mGridView;
    private ListView listView;
    private MusicBottomBar musicBottomBar;
   // private PullToRefreshGridView mPullRefreshGridView;
    private PullToRefreshListView pullToRefreshListView;
    private ArrayList<OnlineMusicBean> listBean = new ArrayList<OnlineMusicBean>();
    private MusicOnlineAdapter mMusicOnlineAdapter;

    private boolean MusicStatic = false;
    private int itemPosition ;
    private int pageNum = 0;
    private final int NUMBERS_PER_PAGE = 8;
    private static final int mediaPauseImg = R.drawable.ic_media_pause;
    private static final int mediaPlayImg = R.drawable.ic_media_play;
    public static final String BroadCast_Server = "com.example.zts.mv_demo3.server.BroadCase_Action";

    private Intent intent;
    private Context context;
    //private SharedPreferences sharedPreferences;
    //private SharedPreferences.Editor editor;

    private OnlineMusicBean mOnlineMusicBean;
    private OnlineMusicPlay mOnlineMusicPlay;
    private MusicPlayThread musicPlayThread;


    private static ShowOverlayTopbar showOverlayTopbar;
    public interface ShowOverlayTopbar {
        public void showTopbar(OnlineMusicBean onlineMusicBean);

    }
    public static void setShowOverlayTopbar(ShowOverlayTopbar onClick){
         showOverlayTopbar = onClick;
    }
    public enum RefreshType {
        REFRESH, LOAD_MORE
    }
    private RefreshType mRefreshType = RefreshType.REFRESH;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_music_online;
    }

    @Override
    public void initView(View rootview) {
        context = getContext();
       // mPullRefreshGridView = (PullToRefreshGridView) rootview.findViewById(R.id.pull_refresh_grid);
        pullToRefreshListView = (PullToRefreshListView) rootview.findViewById(R.id.pull_refresh_list);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        listView =  pullToRefreshListView.getRefreshableView();
        //mGridView = mPullRefreshGridView.getRefreshableView();
        musicBottomBar = FragmentMusic.getMusicBottomBar2();

        // fragment 不能直接拿到 activty 的 view  只能 通过handle 或 监听的方法来操作 activity的 view
       // overlaytopbar = (Topbar) getActivity().findViewById(R.id.topbar_overlay);

    }

    @Override
    public void initData() {
        intent = new Intent(BroadCast_Server);
        intent.putExtra("position", -2);
        intent.putExtra("MusicStatic", true);

        musicBottomBar.setOnlineMusicButOnClick(this);
        mOnlineMusicBean = OnlineMusicBean.getOnlineMusicBean();
        musicPlayThread = new MusicPlayThread();

        mOnlineMusicPlay = OnlineMusicPlay.getOnlineMusicPlay();
        mOnlineMusicPlay.replayMediaPlayer();
        //mPullRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        mMusicOnlineAdapter = new MusicOnlineAdapter(context, listBean, R.layout.music_online_gridview_item);
        listView.setAdapter(mMusicOnlineAdapter);
        getPublishion();

      /*  sharedPreferences = getContext().getSharedPreferences("OnlineMuaicData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();*/
    }

    @Override
    public void initEvent() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mRefreshType = RefreshType.REFRESH;
                getPublishion();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mRefreshType = RefreshType.LOAD_MORE;
                getPublishion();
                listView.postInvalidate();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showToast("listView"+position);
                FragmentMusicOnline.this.itemPosition = position-1;
                // 一组按钮公用，所以先屏蔽第一个接口 打开第二个接口
                musicBottomBar.setLocalMusicButOnClick(null);
                musicBottomBar.setOnlineMusicButOnClick(FragmentMusicOnline.this);
                //暂停 LocalMusic 播放
                context.sendBroadcast(intent);
                MusicStatic = true;
                musicBottomBar.setMusicPauseImg(mediaPauseImg);
                musicBottomBar.setMusicTilteTv(listBean.get(itemPosition).getName());
                musicBottomBar.setMusicArtistTv(listBean.get(itemPosition).getMusicName());
                Log.i("showTopbar", ".bbbbbnull..showTopbar");
                if(showOverlayTopbar != null){
                    Log.i("showTopbar",".null..showTopbar");
                    showOverlayTopbar.showTopbar(listBean.get(itemPosition));
                }

                new Thread(musicPlayThread).start();
            }
        });
    }

    public class MusicPlayThread implements Runnable {

        @Override
        public void run() {
            mOnlineMusicPlay.playUrl(listBean.get(itemPosition).getMusicUrl().getFileUrl(context));
            musicBottomBar.setRoundProgressBarMax(mOnlineMusicPlay.mediaPlayerGetDuration(), 0);
        }
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
        musicBottomBar.setMusicTilteTv(listBean.get(pos).getName());
        musicBottomBar.setMusicArtistTv(listBean.get(pos).getMusicName());
        new Thread(musicPlayThread).start();
    }

    @Override
    public void pauseOnClick() {
        if(MusicStatic){
            MusicStatic = false;
            musicBottomBar.setMusicPauseImg(mediaPlayImg);
            musicBottomBar.showRoundProgressBar(false);
            mOnlineMusicPlay.pause();
        }else {
            MusicStatic = true;
            musicBottomBar.setMusicPauseImg(mediaPauseImg);
            musicBottomBar.showRoundProgressBar(true);
            mOnlineMusicPlay.play();

        }
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
        musicBottomBar.setMusicTilteTv(listBean.get(pos).getName());
        musicBottomBar.setMusicArtistTv(listBean.get(pos).getMusicName());
        //thread.start(); //java.lang.IllegalThreadStateException: Thread already started
        new Thread(musicPlayThread).start();
    }


    private void getPublishion() {
        BmobQuery<OnlineMusicBean> query = new BmobQuery<OnlineMusicBean>();
        query.order("-createdAt");
        query.setLimit(NUMBERS_PER_PAGE); // 限制 6条 消息
        BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
        query.addWhereLessThan("createdAt", date);
        query.setSkip(NUMBERS_PER_PAGE * (pageNum));//跳过几条数据，如第二次刷新就跳过第一次加载完的数据
        query.include("name");
        query.findObjects(context, new FindListener<OnlineMusicBean>() {
            @Override
            public void onSuccess(List<OnlineMusicBean> list) {

                if (list.size() != 0 && list.get(list.size() - 1) != null) {
                    if (mRefreshType == RefreshType.REFRESH) {
                        listBean.clear();
                        pageNum = 0;
                       // editor.clear();
                    }
                    pageNum++;
                    listBean.addAll(list);
/*
                    for (int i = 0; i < listBean.size(); i++) {
                        editor.putString("name" + String.valueOf(i).toString(), listBean.get(i).getName());
                        editor.putString("musicName" + String.valueOf(i).toString(), listBean.get(i).getMusicName());
                        editor.putString("imageUrl" + String.valueOf(i).toString(), listBean.get(i).getPictureUrl().getFileUrl(context));
                    }
                    editor.commit();*/

                    //更新数据
                    //刷新完成
                    mMusicOnlineAdapter.notifyDataSetChanged();
                    pullToRefreshListView.onRefreshComplete();
                } else {
                    //更新数据
                    //刷新完成
                    if (pageNum > 0)
                        pageNum--;

                    pullToRefreshListView.onRefreshComplete();
                }
            }

            @Override
            public void onError(int i, String s) {

     /*           if (sharedPreferences.getAll() != null) {
                    for (int j = 0; i < sharedPreferences.getAll().size(); j++) {
                        mOnlineMusicBean.setName(sharedPreferences.getString("musicName" + String.valueOf(j).toString(), "null"));
                        mOnlineMusicBean.setImageUrl(sharedPreferences.getString("imageUrl" + String.valueOf(j).toString(), "null"));
                        listBean.add(mOnlineMusicBean);
                    }
                }*/
                pullToRefreshListView.onRefreshComplete();
            }
        });
    }

}
