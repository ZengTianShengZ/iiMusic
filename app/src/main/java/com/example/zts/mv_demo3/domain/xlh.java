package com.example.zts.mv_demo3.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ZTS on 2016/1/12.
 */
public class xlh extends ArrayList<Parcelable>{
    public xlh(Parcel in) {
    }



    public xlh() {

    }

    @Override
    public Parcelable set(int index, Parcelable object) {
        return super.set(index, object);
    }

    @Override
    public Parcelable get(int index) {
        return super.get(index);
    }
}
