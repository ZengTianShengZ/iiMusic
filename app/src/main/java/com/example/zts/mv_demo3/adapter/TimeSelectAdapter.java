package com.example.zts.mv_demo3.adapter;

import android.content.Context;

import com.example.zts.appbase.adapter.CommonAdapter;
import com.example.zts.appbase.adapter.ViewHolder;
import com.example.zts.mv_demo3.R;

import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 */
public class TimeSelectAdapter extends CommonAdapter<String> {
    public TimeSelectAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {

        helper.setText(R.id.time_tv,item);

    }
}
