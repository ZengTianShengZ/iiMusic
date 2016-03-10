package com.example.zts.appbase.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zts.appbase.R;
import com.example.zts.appbase.utils.ImageUtils;
import com.example.zts.appbase.utils.MyDiskLruCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

public class ViewHolder
{
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context;
	private MyDiskLruCache mMyDiskLruCache;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		this.context = context;
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);

		// DiskLruCache 缓存
		mMyDiskLruCache = new MyDiskLruCache(context);
	}

	/** 
     * �õ�һ��ViewHolder���� 
     *  
     * @param context 
     * @param convertView 
     * @param parent 
     * @param layoutId 
     * @param position 
     * @return 
     */  
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ViewHolder(context, parent, layoutId, position);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}

	public View getConvertView()
	{
		return mConvertView;
	}
    /** 
     * ͨ��ؼ���Id��ȡ���ڵĿؼ������û�������views 
     *  
     * @param viewId 
     * @return 
     */  
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if (view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 设置自定义view
	 * @param viewId
	 * @param position
	 * @return
	 */
	public ViewHolder setCustomView(int viewId, int position)
	{
		View view = getView(viewId);
		view.setVisibility(View.GONE);
		return this;
	}
	/** 
     * ΪTextView�����ַ� 
     *  
     * @param viewId 
     * @param text 
     * @return 
     */  
	public ViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/** 
     * ΪImageView����ͼƬ 
     *  
     * @param viewId 
     * @param drawableId 
     * @return 
     */  
	public ViewHolder setImageResource(int viewId, int drawableId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/** 
     * ΪImageView����ͼƬ 
     *
     */  
	public ViewHolder setImageBitmap(int viewId, Bitmap bm)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}
	
	 /** 
     * ΪImageView����ͼƬ
     */  
	public ViewHolder setImageByUrl(int viewId, String url)
	{
		//可以使用两种开源项目ImageLoader
		//ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url, (ImageView) getView(viewId));

		// 使用第二种时需要 初始化， init 不然会出错。
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
 		ImageLoader.getInstance().displayImage(url,(ImageView) getView(viewId), ImageUtils.getOptions(R.drawable.bb), new SimpleImageLoadingListener() {
			public void onLoadingComplete(String imageUri, View view, android.graphics.Bitmap loadedImage) {
				Log.i("MyDiskLruCache","..MyDiskLruCache..");
				try {
					mMyDiskLruCache.bitmapDiskLruCache(imageUri,loadedImage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		});
		return this;
	}

	public int getPosition()
	{
		return mPosition;
	}

}
