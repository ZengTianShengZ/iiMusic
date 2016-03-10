package com.example.zts.mv_demo3.domain;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ZTS on 2016/1/17.
 */
public class OnlineMusicBean extends BmobObject {

    private String name;
    private String musicName;
    private String imageUrl;
    private String songDetails;
    private BmobFile musicUrl;
    private BmobFile pictureUrl;
    private int onlinePosition;

    private OnlineMusicBean(){}
    private static OnlineMusicBean mOnlineMusicBean = null;
    public static OnlineMusicBean getOnlineMusicBean(){
        if(mOnlineMusicBean == null){
            mOnlineMusicBean = new OnlineMusicBean();
        }
        return mOnlineMusicBean;
    }

    public OnlineMusicBean(String name,String musicName,BmobFile musicUrl,BmobFile pictureUrl){
        this.name = name;
        this.musicName = musicName;
        this.musicUrl = musicUrl;
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BmobFile getMusicUrl() {
        return musicUrl;
    }

    public String getSongDetails() {
        return songDetails;
    }

    public void setSongDetails(String songDetails) {
        this.songDetails = songDetails;
    }

    public void setMusicUrl(BmobFile musicUrl) {
        this.musicUrl = musicUrl;
    }

    public BmobFile getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(BmobFile pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getOnlinePosition() {
        return onlinePosition;
    }

    public void setOnlinePosition(int onlinePosition) {
        this.onlinePosition = onlinePosition;
    }
}
