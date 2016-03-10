package com.example.zts.mv_demo3.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import java.util.ArrayList;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> fragments;




    public FragmentAdapter(android.support.v4.app.FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
