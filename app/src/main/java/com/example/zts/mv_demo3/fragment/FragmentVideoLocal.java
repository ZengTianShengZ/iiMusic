package com.example.zts.mv_demo3.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.activity.VideoPlayActivity;
import com.example.zts.mv_demo3.adapter.VideoLocalAdapter;
import com.example.zts.mv_demo3.domain.ViedoMediaBean;

import java.util.ArrayList;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentVideoLocal extends BaseFragment {
    private GridView gridView;
    private ViedoMediaBean vdBean ;
    private VideoLocalAdapter mVideoLocalAdapter;
    private ArrayList<ViedoMediaBean> listBean= new ArrayList<ViedoMediaBean>();
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_video_local;
    }

    @Override
    public void initView(View rootview) {

        gridView = (GridView) rootview.findViewById(R.id.video_local_gridView);
    }

    @Override
    public void initData() {
        Cursor cursor = getContext().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,null,null,
                MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        Log.i("FragmentVideoLocal","..cursor..."+cursor.moveToFirst());
        if(cursor.moveToFirst()){

            String tilte;
            String url;
            int duration;

            cursor.moveToFirst();
            //歌曲ID：MediaStore.Audio.Media._ID

            int Media_title = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE);
            int Media_url = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA );
            int Media_duration = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

            do {

                tilte = cursor.getString(Media_title);
                url = cursor.getString(Media_url);
                duration = cursor.getInt(Media_duration);
                vdBean = new ViedoMediaBean(tilte,url,duration,getVideoThumbnail(url));
                listBean.add(vdBean);
            }while (cursor.moveToNext());

        }
        cursor.close();

        mVideoLocalAdapter = new VideoLocalAdapter(getContext(),listBean,R.layout.video_local_gridview_item);
        gridView.setAdapter(mVideoLocalAdapter);
    }

    @Override
    public void initEvent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                intent.putExtra("videoPath",listBean.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    /**
     * Android得到视频缩略图，可以通过接口类 MediaMetadataRetriever 来实现
     * @param filePath
     * @return Bitmap
     */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
