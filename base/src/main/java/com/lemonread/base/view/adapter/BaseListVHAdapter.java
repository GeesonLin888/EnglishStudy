package com.lemonread.base.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * @desc 用于PullLoadMoreListView 通用的使用List且有ViewHolder支持的adapter
 * @author zhao
 * @time 2019/3/5 17:04
 */
public abstract class BaseListVHAdapter<T,VH> extends BaseAdapter {
	
	Context mContext;
	LayoutInflater inflater;

	public List<T> list;

	OnClickListener onClickListender;
	public BaseListVHAdapter(Context context) {
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}

	public BaseListVHAdapter(Context context, List<T> list) {
		this(context);
		this.list = list;
	}


	public BaseListVHAdapter(Context context, List<T> list,
                             OnClickListener onClickListender) {
		this(context, list);

		this.onClickListender = onClickListender;
	}
    
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	/**
	 * 更新数据
	 * 
	 * @param newList
	 */
	public void updateDataAndRefresh(List<T> newList) {
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		
		this.list =newList;
		notifyDataSetChanged();
	}
	/**
	 * 追加数据
	 * @param newList
	 */
	public void addAllAndRefresh(List<T> newList){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		if (newList!=null) {
			
			this.list.addAll(newList);
			notifyDataSetChanged();
		}
	}

//    /**
//     * 追加数据,但是检查数据是否已存在
//     * @param newList
//     */
//	public void addAllUniqueAndRefresh(List<T> newList){
//		if (this.list==null) {
//			this.list=new ArrayList<T>();
//		}
//		if (newList!=null) {
//            for(T t:newList){
//                if (!this.list.contains(t)){
//                    this.list.add(t);
//                }
//            }
//			notifyDataSetChanged();
//		}
//	}

	/**
	 * 追加数据,检查数据是否已经存在,有新数据才调用 notifyDataSetChanged
	 * @param newList
	 * @return 是否调用了notifyDataSetChanged
	 */
	public boolean addAllUniqueAndMayRefresh(List<T> newList){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		boolean needRefresh=false;
		if (newList!=null) {

			for(T t:newList){
				if (!this.list.contains(t)){
					this.list.add(t);
					needRefresh=true;
				}
			}
			if (needRefresh){
				notifyDataSetChanged();
			}
		}
		return needRefresh;
	}

	/**
	 * 向头部添加数据,同时检查数据是否已经存在,有新数据才调用 notifyDataSetChanged
	 * @param newList
	 * @return
	 */
	public boolean addToTopAndMayRefresh(List<T> newList){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		boolean needRefresh=false;
		if (newList!=null) {

			for(int i=0;i<newList.size();i++){
				T t=newList.get(i);
				if (!this.list.contains(t)){
					this.list.add(i,t);
					needRefresh=true;
				}
			}
			if (needRefresh){
				notifyDataSetChanged();
			}
		}
		return needRefresh;
	}

	public void addAndRefresh(T data){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		this.list.add(data);
		notifyDataSetChanged();
	}
	public void addAndRefresh(List<T> data){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		if (data!=null) {
			this.list.addAll(data);
			notifyDataSetChanged();
		}
	}
	public void add(T data){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		this.list.add(data);
	}
	public void add(int position,T data){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		this.list.add(position, data);
	}
	public void addAndRefresh(int position,T data){
		if (this.list==null) {
			this.list=new ArrayList<T>();
		}
		this.list.add(position,data);
		notifyDataSetChanged();
	}
	public void removeAndRefresh(int position){
		if (this.list!=null) {
			this.list.remove(position);
		}
        notifyDataSetChanged();
    }
	public void clearAndRefresh(){
		if (this.list!=null) {
			this.list.clear();
		}
        notifyDataSetChanged();
    }
	public void clear(){
		if (this.list!=null) {
			this.list.clear();
		}
    }
	public LayoutInflater getLayoutInflater() {
		return inflater;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	public Context getContext() {
		return mContext;
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VH vh;
		if (convertView==null) {
			convertView = getNewView(position);
			vh=onCreateViewHolder(position,convertView); 
			
		}else{
			vh=(VH) convertView.getTag();
		}
		if (vh instanceof BaseViewHolder){
			BaseViewHolder tempVh=(BaseViewHolder)vh;
			tempVh.data=getItem(position);
		}
		
		onBindViewHolder(vh,position);
		convertView.setTag(vh);
		
		if (onClickListender!=null) {
			convertView.setOnClickListener(onClickListender);
		}
		
		return convertView;
	}

	public abstract View getNewView(int position);
	
	public abstract VH onCreateViewHolder(int position,View newView);
	public abstract void onBindViewHolder(VH vh,int position);

	public static class BaseViewHolder<T>{
		public T data;
		public BaseViewHolder(View view){

		}
	}

}
