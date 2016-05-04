package com.example.zts.mv_demo3.tools;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.zts.mv_demo3.domain.AudioMediaBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ScannerData {

    public static void scannerMusicData(Context context,AudioMediaBean  amBean, ArrayList<AudioMediaBean> listBean,ArrayList<String> stringUrlList){


        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Audio.Media.DURATION+">?",new String[]{"180000"},
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if(cursor != null) {

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
            int Media_title = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
            int Media_album = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
            int Media_artist = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
            int Media_url = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
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

                amBean = new AudioMediaBean(id, tilte, album, artist, url, duration, size);
                listBean.add(amBean);

                stringUrlList.add(url);

            } while (cursor.moveToNext());
            amBean.setStringUrlList(stringUrlList);
            cursor.close();
        }
    }
}
