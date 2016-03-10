package com.example.zts.mv_demo3.domain;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by ZTS on 2016/1/14.
 */
public class ViedoMediaBean {
    private String tilte;
    private String url;
    private int duration;
    private Bitmap videoImage;
    private int From_Server_position;
    private ArrayList<String> stringUrlList= new ArrayList<String>();

    private ViedoMediaBean() {

    }
    private static ViedoMediaBean mViedoMediaBean = null;

    /**
     * 单例设计模式
     * @return
     */
    public static ViedoMediaBean getmViedoMediaBean(){
        if(mViedoMediaBean == null){
            mViedoMediaBean = new ViedoMediaBean();
        }
        return mViedoMediaBean;
    }
    public ViedoMediaBean(String tilte,String url,int duration,Bitmap videoImage) {
        this.tilte = tilte;
        this.url = url;
        this.duration = duration;
        this.videoImage = videoImage;
    }


    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public int getFrom_Server_position() {
        return From_Server_position;
    }

    public void setFrom_Server_position(int from_Server_position) {
        From_Server_position = from_Server_position;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(Bitmap videoImage) {
        this.videoImage = videoImage;
    }

    public ArrayList<String> getStringUrlList() {
        return stringUrlList;
    }

    public void setStringUrlList(ArrayList<String> stringUrlList) {
        this.stringUrlList = stringUrlList;
    }
}
