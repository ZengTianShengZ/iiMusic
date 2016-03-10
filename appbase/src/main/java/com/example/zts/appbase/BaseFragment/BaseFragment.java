package com.example.zts.appbase.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zts.appbase.R;
import com.example.zts.appbase.proxy.ActivityProxy;
import com.example.zts.appbase.proxy.DialogProxy;
import com.example.zts.appbase.proxy.DialogProxy.DialogExtProxiable;
import com.example.zts.appbase.proxy.DialogProxy.DialogProxiable;
import com.example.zts.appbase.proxy.ToastProxy.ToastProxiable;

/**
 * Created by ZTS on 2015/12/28.
 */
public abstract  class BaseFragment extends Fragment implements ToastProxiable,DialogProxiable,
        DialogExtProxiable{

    private ActivityProxy mActivityProxy;
    private View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mActivityProxy = new ActivityProxy(getActivity());
         rootview = inflater.inflate(getFragmentLayout(), container, false);

        initView(rootview);
        initData();
        initEvent();

        return rootview;

    }

    @Override
    public void showToast(String text) {
        mActivityProxy.showToast(text);
    }

    @Override
    public void showToast(int resourceId) {
        mActivityProxy.showToast(resourceId);
    }

    @Override
    public void showMsgDialog(String title, String detials, String btnLeft, String btnRight,
                              View.OnClickListener btnLeftListener, View.OnClickListener btnRightListener) {
        mActivityProxy.showMsgDialog(title, detials, btnLeft, btnRight,
                btnLeftListener, btnRightListener);
    }

    @Override
    public void showProgressDialog(String msg, DialogInterface.OnCancelListener listener, boolean cancelable) {

    }

    @Override
    public void showProgressDialog(String msg) {

    }

    @Override
    public void showProgressDialog(int resid) {

    }

    @Override
    public void showMsgDialog(String detials, String btnLeft, View.OnClickListener btnLeftListener) {

    }

    @Override
    public void showMsgDialog(String title, String detials, String btnLeft) {

    }

    @Override
    public void showMsgDialog(String detials, String btnLeft) {

    }

    @Override
    public void showMsgDialog(String detials) {

    }

    @Override
    public void showMsgDialog(int res) {

    }

    @Override
    public void showMsgDialog() {

    }

    @Override
    public void showMsgDialogWithSize(int width, int height) {

    }

    @Override
    public DialogProxy getDialogProxy() {
        return null;
    }

    @Override
    public void cancelMsgDialog() {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void cancelProgressDialog() {

    }

    public abstract int getFragmentLayout();

    public abstract void initView(View rootview);

    public abstract void initData();

    public abstract void initEvent();
}
