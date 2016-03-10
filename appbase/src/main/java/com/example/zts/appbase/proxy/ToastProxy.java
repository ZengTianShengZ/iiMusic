package com.example.zts.appbase.proxy;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
/**
 * ��˾�Ĵ�����
 * ��˾�����������ʵ��
 * @author MZone
 *
 */
public class ToastProxy {
	private Context mContext;
	private Toast mToast;
	public ToastProxy(Context mContext){
		this.mContext =mContext;
	}
	public void initToast(){
		if(mContext!=null){
			mToast =Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.BOTTOM, 0, 0);
		}
	}
	public void showToast(String text){
		initToast();
		mToast.setText(text);
		mToast.show();
	}
	public void showToast(int resourceId){
		initToast();
		mToast.setText(resourceId);
		mToast.show();
	}
	public interface ToastProxiable{
		public void showToast(String text);
		public void showToast(int resourceId);
	}
}

