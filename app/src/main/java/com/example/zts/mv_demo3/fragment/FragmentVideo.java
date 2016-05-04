package com.example.zts.mv_demo3.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.adapter.FragmentAdapter;

import java.util.ArrayList;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentVideo extends BaseFragment implements View.OnClickListener{

    private ViewPager video_viewpager;
    private ArrayList<Fragment> fragments;
    private View poinLine;
    private TextView v_local_tv;
    private TextView v_online_tv;

    private float screenWidth;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView(View rootview) {

        video_viewpager = (ViewPager) rootview.findViewById(R.id.video_viewpager);
        poinLine = rootview.findViewById(R.id.point_line_v);

        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentVideoLocal());
        fragments.add(new FragmentVideoOnline());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager(),fragments);
        video_viewpager.setAdapter(fragmentAdapter);

        v_local_tv = (TextView) rootview.findViewById(R.id.v_local_tv);
        v_online_tv = (TextView) rootview.findViewById(R.id.v_online_tv);

        v_local_tv.setOnClickListener(this);
        v_online_tv.setOnClickListener(this);

    }

    @Override
    public void initData() {

        WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels/fragments.size()/6;
        Log.i("position", "...///" + screenWidth);
        poinLine.getLayoutParams().width= (int) (screenWidth*4);
        poinLine.setTranslationX(screenWidth);
    }

    @Override
    public void initEvent() {

        video_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < 0.9999f)
                    poinLine.setTranslationX(positionOffsetPixels / fragments.size() + screenWidth);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.v_local_tv:
                video_viewpager.setCurrentItem(0);
                break;

            case R.id.v_online_tv:
                video_viewpager.setCurrentItem(1);
                break;

        }
    }


}
