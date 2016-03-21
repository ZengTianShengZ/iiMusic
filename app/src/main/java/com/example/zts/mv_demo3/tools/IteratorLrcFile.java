package com.example.zts.mv_demo3.tools;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
public class IteratorLrcFile {

    private File path;
    private ArrayList<String> LrcPath = new ArrayList<String>();

    public IteratorLrcFile(Context context){
        //检测SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory();
            iteratorFile(path);
            Log.i("LrcPath", "/................../");
        }else{
            Toast.makeText(context, "没有SD卡", Toast.LENGTH_LONG).show();
        }
    }

    private void iteratorFile(final File root) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File files[] = root.listFiles();
                if(files != null){
                    for (File f : files){
                        if(f.isDirectory()){
                            iteratorFile(f);
                        }else{

                            if((f.getName()).endsWith(".lrc")){
                                LrcPath.add(f.getAbsolutePath() + f.getName());

                                Log.i("LrcPath", "//" + f.getAbsolutePath()+f.getName());
                                //storage/emulated/0/ttpod/lyric/弗雷德 - 因为爱情 (法语版).lrc弗雷德 - 因为爱情 (法语版).lrc
                            }
                        }
                    }
                }
            }
        }).start();

    }

    public String getLrcPath(String songName){

        for(int i = 0;i<LrcPath.size();i++){
            Log.i("songName","."+songName+".."+LrcPath.get(i));
            if(LrcPath.get(i).endsWith(songName + ".lrc"));{

                return LrcPath.get(17);

            }
        }

        return null;
    }
}
