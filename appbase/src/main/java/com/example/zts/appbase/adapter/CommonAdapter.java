package com.example.zts.appbase.adapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zts.appbase.R;

public abstract class CommonAdapter<T> extends BaseAdapter
{
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;

	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder viewHolder = getViewHolder(position, convertView,parent);
		//将 BaseAdapter 进行封装 ， 最后只留出 这么一个接口 ，外部程序只需调用这个 convert 就可以完成强大的 BaseAdapter 了
		convert(viewHolder, getItem(position),position);
		//convertView.findViewById(R.id.list_item_AudioBar).setTag(1);
		return viewHolder.getConvertView();

	}

	public abstract void convert(ViewHolder helper, T item, int position);

	private ViewHolder getViewHolder(int position, View convertView,ViewGroup parent)
	{
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}

}
