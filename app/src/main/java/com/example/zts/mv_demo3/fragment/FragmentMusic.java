package com.example.zts.mv_demo3.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.appbase.view.MusicBottomBar;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.adapter.FragmentAdapter;
import com.yalantis.euclid.library.EuclidActivity;


import java.util.ArrayList;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentMusic extends BaseFragment implements View.OnClickListener {

    // View
    private View poinLine;
    private TextView m_local_tv;
    private TextView m_online_tv;
    private ViewPager music_viewpager;
    private static MusicBottomBar musicBottomBar1,musicBottomBar2;

    private float screenWidth;
    public static int musicMageSelectedPos;

    private ArrayList<Fragment> fragments;

    @Override
    public int getFragmentLayout() {

        return R.layout.fragment_music;
    }
    public static MusicBottomBar getMusicBottomBar1(){
         return musicBottomBar1;
    }
    public static MusicBottomBar getMusicBottomBar2(){
        return musicBottomBar2;
    }
    @Override
    public void initView(View rootview) {

        music_viewpager = (ViewPager) rootview.findViewById(R.id.music_viewpager);
        poinLine = rootview.findViewById(R.id.point_line);
        musicBottomBar1 = (MusicBottomBar) rootview.findViewById(R.id.music_BottomBar);
        musicBottomBar2 = (MusicBottomBar) rootview.findViewById(R.id.music_BottomBar);

        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentMusicLocal());
        //fragments.add(new EuclidActivity());
        fragments.add(new FragmentMusicOnline());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager(), fragments);
        music_viewpager.setAdapter(fragmentAdapter);

        m_local_tv = (TextView) rootview.findViewById(R.id.m_local_tv);
        m_online_tv = (TextView) rootview.findViewById(R.id.m_online_tv);

        m_local_tv.setOnClickListener(this);
        m_online_tv.setOnClickListener(this);
    }

    @Override
    public void initData() {

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels / fragments.size() / 6;
        poinLine.getLayoutParams().width = (int) (screenWidth * 4);
        poinLine.setTranslationX(screenWidth);


    }

    @Override
    public void initEvent() {

        music_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < 0.9999f)
                    poinLine.setTranslationX(positionOffsetPixels / fragments.size() + screenWidth);
                musicMageSelectedPos = position;
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.m_local_tv:
                music_viewpager.setCurrentItem(0);
                break;

            case R.id.m_online_tv:
                music_viewpager.setCurrentItem(1);
                break;

        }
    }
}
