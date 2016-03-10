package com.example.zts.mv_demo3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.zts.appbase.adapter.CommonAdapter;
import com.example.zts.appbase.adapter.ViewHolder;
import com.example.zts.appbase.view.AudioBar;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.domain.AudioMediaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZTS on 2016/1/8.
 */
public class MusicLocalAdapter extends CommonAdapter<AudioMediaBean>{

    private List<AudioMediaBean> listBean= new ArrayList<AudioMediaBean>();
    private View mConvertView;
    private AudioBar audioBar;

    public MusicLocalAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.listBean = mDatas;
      //  mConvertView = LayoutInflater.from(context).inflate(itemLayoutId,null);
      //  audioBar = (AudioBar) mConvertView.findViewById(R.id.list_item_AudioBar);
    }

    @Override
    public void convert(ViewHolder helper, AudioMediaBean item, int position) {
       // helper.
       // audioBar.setTag(position);
      //  helper.getConvertView().findViewById(R.id.list_item_AudioBar).setTag(position);
        helper.setCustomView(R.id.list_item_AudioBar,position);
        helper.setText(R.id.list_item_num,""+position);
        helper.setText(R.id.list_item_songName,item.getTilte());
        helper.setText(R.id.list_item_singerName,item.getArtist());
        }
        }
