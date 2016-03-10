package com.example.zts.mv_demo3.domain;

import java.util.ArrayList;

/**
 * Created by ZTS on 2016/1/8.
 */
public class AudioMediaBean {

    private int id;
    private String tilte;
    private String album;
    private String artist;
    private String url;
    private int duration;
    private int size;
    private int From_Server_position;
    private ArrayList<String> stringUrlList= new ArrayList<String>();

    private AudioMediaBean() {

    }
    private static AudioMediaBean mAudioMediaBean = null;

    /**
     * 单例设计模式
     * @return
     */
    public static AudioMediaBean getmAudioMediaBean(){
        if(mAudioMediaBean == null){
            mAudioMediaBean = new AudioMediaBean();
        }
        return mAudioMediaBean;
    }
    public AudioMediaBean(int id,String tilte,String album,
                          String artist,String url,int duration,int size) {
        this.id = id;
        this.tilte = tilte;
        this.album = album;
        this.artist = artist;
        this.url = url;
        this.duration = duration;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFrom_Server_position() {
        return From_Server_position;
    }
    public void setFrom_Server_position(int from_Server_position) {
        From_Server_position = from_Server_position;
    }

    public ArrayList<String> getStringUrlList() {
        return stringUrlList;
    }

    public void setStringUrlList(ArrayList<String> stringUrlList) {
        this.stringUrlList = stringUrlList;
    }
}
