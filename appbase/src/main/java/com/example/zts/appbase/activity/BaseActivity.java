package com.example.zts.appbase.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.example.zts.appbase.R;
import com.example.zts.appbase.view.Topbar;
import com.example.zts.appbase.proxy.ActivityProxy;
import com.example.zts.appbase.proxy.DialogProxy;
import com.example.zts.appbase.proxy.DialogProxy.DialogExtProxiable;
import com.example.zts.appbase.proxy.DialogProxy.DialogProxiable;
import com.example.zts.appbase.proxy.ToastProxy.ToastProxiable;


/**
 * Created by ZTS on 2015/12/25.
 */
public abstract class BaseActivity extends FragmentActivity implements ToastProxiable,DialogProxiable,
        DialogExtProxiable{


    private Context context;
    protected ActivityProxy mActivityProxy;
    private LayoutInflater inflater;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());

        context = getBaseContext();
/*        inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(getActivityLayout(),null);
*/

         mActivityProxy = new ActivityProxy(context);
         initView(context);
         initData() ;
         initEvent() ;

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

    public abstract int getActivityLayout();

    public abstract void initView(Context context);
    public abstract void initData();
    public abstract void initEvent();

   // public abstract void setTopbarDetel(String title, int color, int liftButtonResour, int rightButtonResour);
}
