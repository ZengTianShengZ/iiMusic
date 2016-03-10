package com.example.zts.mv_demo3.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.zts.appbase.adapter.CommonAdapter;
import com.example.zts.appbase.adapter.ViewHolder;
import com.example.zts.appbase.utils.MyDiskLruCache;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.domain.OnlineMusicBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


import java.io.IOException;
import java.util.List;

/**
 * Created by ZTS on 2016/1/17.
 */
public class MusicOnlineAdapter extends CommonAdapter<OnlineMusicBean> {

    private Context context;
    private ImageView imageView;
    private MyDiskLruCache mMyDiskLruCache;
  //  private ImageLoadingListener animateFirstListener = new ImageLoadingListener() ;

    private DisplayImageOptions options;

    public MusicOnlineAdapter(Context context, List<OnlineMusicBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        mMyDiskLruCache = new MyDiskLruCache(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_media_next)
                .showImageForEmptyUri(R.drawable.ic_media_pause)
                .showImageOnFail(R.drawable.ic_media_play)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public void convert(ViewHolder helper, OnlineMusicBean item, int position) {

/*        String imageUrl = null;
        if(item.getPictureUrl()!= null){
            Log.i("MusicOnlineAdapter","...."+item.getPictureUrl());
            imageUrl = item.getPictureUrl().getFileUrl(context);
            helper.setImageByUrl(R.id.music_gridview_item_image, imageUrl);
        }else {
            if(item.getImageUrl()!= null){
                try {
                    Log.i("MusicOnlineAdapter","...."+item.getImageUrl());
                    // 重本地磁盘中获取图片
                    helper.setImageBitmap(R.id.music_gridview_item_image,mMyDiskLruCache.getBitmapFromDiakLruCache(item.getImageUrl()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
        ImageLoader.getInstance().displayImage(item.getPictureUrl().getFileUrl(context),(ImageView)helper.getView(R.id.music_gridview_item_image),options);
        helper.setText(R.id.music_gridview_item_Name, item.getName()+"-"+item.getMusicName());

     }

}
