package com.example.zts.mv_demo3.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.mv_demo3.R;

/**
 * Created by ZTS on 2015/12/28.
 */
public class fragment1 extends BaseFragment {
    private LinearLayout lin;
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment1;
    }

    @Override
    public void initView(View rootview) {

        TextView tv = new TextView(rootview.getContext());
        tv.setText("kkkkkkkk");
        lin = (LinearLayout) rootview.findViewById(R.id.linxx);
        lin.addView(tv);

    }


    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
