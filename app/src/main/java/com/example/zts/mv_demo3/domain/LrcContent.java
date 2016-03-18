package com.example.zts.mv_demo3.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/18.
 */
public class LrcContent {
    private String lrcStr;  //歌词内容
    private int lrcTime;    //歌词当前时间

    public String getLrcStr() {
        return lrcStr;


    }
    public void setLrcStr(String lrcStr) {
        this.lrcStr = lrcStr;
    }
    public int getLrcTime() {
        return lrcTime;
    }
    public void setLrcTime(int lrcTime) {
        this.lrcTime = lrcTime;
    }



}
