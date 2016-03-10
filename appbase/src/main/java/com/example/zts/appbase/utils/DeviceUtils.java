package com.example.zts.appbase.utils;

import android.content.Context;
import android.util.DisplayMetrics;
/**
 *
 * 设备工具类
 *
 * @author MZone
 *
 */
public class DeviceUtils {
	public static DisplayMetrics getDisplayMetrics(Context context)
	{
		return context.getResources().getDisplayMetrics();
	}
}
