package com.example.zts.appbase.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ZTS on 2016/1/30.
 */
public class MyDiskLruCache {

    private DiskLruCache mdiskLruCache;
    private static final long DISK_CACHE_SIZE = 1024*1024*20; //20 MB
    private Context context;

    public MyDiskLruCache(Context context) {

        File diskCacheDir = getDiskCaherDir(context, "LruBitmap");
        Log.i("File","...file.."+diskCacheDir.getPath());
        //...file../storage/emulated/0/Android/data/com.example.zts.mv_demo3/cache/LruBitmap
        try {
            mdiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void bitmapDiskLruCache(String url,Bitmap bitmap) throws IOException {

        String key = MD5Code.hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mdiskLruCache.edit(key);
        if(editor != null){
            OutputStream outputStream = editor.newOutputStream(0);
            if(bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

            }
            outputStream.close();
           // bitmap.recycle();
            editor.commit();
        }

    }
    public Bitmap getBitmapFromDiakLruCache(String url) throws IOException {
        String key = MD5Code.hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = mdiskLruCache.get(key);
        FileInputStream fileInputStream = null;
        if(snapshot != null){
            fileInputStream = (FileInputStream) snapshot.getInputStream(0);


        }
        return BitmapFactory.decodeStream(fileInputStream);
    }

    private File getDiskCaherDir(Context context, String lruBitmap) {
        boolean enviromentTrue = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if(enviromentTrue){
            cachePath = context.getExternalCacheDir().getPath();//得到外部存储路径
            Log.i("File","...cachePathwww.."+cachePath );
           // ...cachePathwww../storage/emulated/0/Android/data/com.example.zts.mv_demo3/cache
        }else {
            cachePath = context.getCacheDir().getPath();//内部存储路径
            Log.i("File","...cachePathnnn.."+cachePath );
        }
        return new File(cachePath + File.separator + lruBitmap);
    }
}
