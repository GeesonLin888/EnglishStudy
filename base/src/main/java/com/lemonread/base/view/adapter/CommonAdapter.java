package com.lemonread.base.view.adapter;


/**
 * @desc 通常用于翻页
 * @author zhao
 * @time 2019/3/5 17:04
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;
    protected boolean isfirst = true;
    protected int rowNum;

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
//        this.mContext = context;
//        this.mInflater = LayoutInflater.from(mContext);
//        this.mDatas = mDatas;
//        this.mItemLayoutId = itemLayoutId;
        this(context, mDatas, itemLayoutId, 0);
    }

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId, int rowNum) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
        this.rowNum = rowNum;
    }

    public void setList(List<T> mDatas) {

        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return this.mDatas;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseListViewHolder BaseViewHolder = getBaseViewHolder(position, convertView,
                parent);

        convert(BaseViewHolder, getItem(position), position);

        return BaseViewHolder.getConvertView();
    }

    public abstract void convert(BaseListViewHolder helper, T item, int position);

    private BaseListViewHolder getBaseViewHolder(int position, View convertView,
                                                 ViewGroup parent) {
        return BaseListViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position, rowNum);
    }

    public void removeAndRefresh(int position) {
        if (this.mDatas != null) {
            this.mDatas.remove(position);
        }
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     *
     * @param index, socialItem
     */
    public void updateDataAndRefresh(int index, T item) {
        if (index >= 0 && index <= getCount() - 1) {
            getList().set(index, item);
            notifyDataSetChanged();
        }
    }

    public void setRefreshData() {

        isfirst = false;
        notifyDataSetChanged();
        isfirst = true;

    }

    public void setRefreshData(boolean isfirst) {

        this.isfirst = isfirst;
        notifyDataSetChanged();

    }

    public void setRowNum(int num) {

        this.rowNum = num;
        notifyDataSetChanged();

    }


}

