package com.example.zts.mv_demo3.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/18.
 */
public class LrcProcess {
    private Map<String, String> lrcMap = new HashMap<String, String>();

    /**
     * 读取歌词
     *
     * @param path
     * @return
     */
    public Map readLRC(String path) {

        lrcMap.clear();
        //定义一个StringBuilder对象，用来存放歌词内容
        StringBuilder stringBuilder = new StringBuilder();
        File f = new File(path);

        try {
            //创建一个文件输入流对象
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                //替换字符
                s = s.replace("[", "");
                s = s.replace("]", "@");

                //分离“@”字符
                String splitLrcData[] = s.split("@");
                if(splitLrcData.length > 1) {
       /*             mLrcContent.setLrcStr(splitLrcData[1]);

                    //处理歌词取得歌曲的时间
                    int lrcTime = time2Str(splitLrcData[0]);

                    mLrcContent.setLrcTime(lrcTime);

                    //添加进列表数组
                    lrcList.add(mLrcContent);

                    //新创建歌词内容对象
                    mLrcContent = new LrcContent();*/
                    lrcMap.put(String.valueOf(time2Str(splitLrcData[0])),splitLrcData[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            stringBuilder.append("木有歌词文件，赶紧去下载！...");

        }catch (IOException e) {
            e.printStackTrace();
            stringBuilder.append("木有读取到歌词哦！");
        }
        return lrcMap;
    }

    /**
     * 解析歌词时间
     * 歌词内容格式如下：
     * [00:02.32]陈奕迅
     * [00:03.43]好久不见
     * [00:05.22]歌词制作  王涛
     * @param timeStr
     * @return
     */
    public int time2Str(String timeStr) {
        timeStr = timeStr.replace(":", ".");
        timeStr = timeStr.replace(".", "@");

        String timeData[] = timeStr.split("@"); //将时间分隔成字符串数组

        //分离出分、秒并转换为整型
        int minute = Integer.parseInt(timeData[0]);
        int second = Integer.parseInt(timeData[1]);
        int millisecond = Integer.parseInt(timeData[2]);

        //计算上一行与下一行的时间转换为毫秒数
        int currentTime = minute * 60 + second;
        return currentTime;
    }
}