package com.example.zts.mv_demo3.adapter;

import android.content.Context;

import com.example.zts.appbase.adapter.CommonAdapter;
import com.example.zts.appbase.adapter.ViewHolder;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.domain.ViedoMediaBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZTS on 2016/1/14.
 */
public class VideoLocalAdapter extends CommonAdapter<ViedoMediaBean> {

    private Date date;
    private SimpleDateFormat format;
    public VideoLocalAdapter(Context context, ArrayList<ViedoMediaBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        format = new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public void convert(ViewHolder helper, ViedoMediaBean item, int position) {

        date = new Date(item.getDuration());

        helper.setImageBitmap(R.id.gridview_item_image,item.getVideoImage());
        helper.setText(R.id.gridview_item_duration_time,format.format(date));
        helper.setText(R.id.gridview_item_MvName,item.getTilte());
    }
}
