package com.lemonread.base.view.activity;

import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ListView;

import com.lemonread.base.R;
import com.lemonread.base.utils.LogUtils;
import com.lemonread.base.utils.NetUtils;
import com.lemonread.base.utils.SpUtils;
import com.lemonread.base.utils.ToastUtils;
import com.lemonread.base.utils.TurnPageUtils;
import com.lemonread.base.view.adapter.CommonAdapter;
import com.lemonread.base.vp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc
 * @author zhao
 * @time 2019/3/5 17:00
 */

public abstract class BasePageRequestActivity<T,R extends BasePresenter> extends BaseActivity<R> {

    private boolean isLoadingData = false;
    private int currentRequestPage = 1;
    private int AllTotalSize = 0;
    private int currentViewingPage = 1;


    private List<T> allList = new ArrayList<>();
    private List<T> currentListForAdapter = new ArrayList<>();


    private ListView listView;
    private CommonAdapter<T> adapterForListView;

    private GridView gridView;
    private CommonAdapter<T> adapterForGridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void provideListView(ListView listView, CommonAdapter<T> commonAdapter) {
        if (listView != null && commonAdapter != null) {
            this.listView = listView;
            this.adapterForListView = commonAdapter;
            TurnPageUtils.setListViewTurnPageListener(listView, turnPageListener);
        }
    }

    public void provideGridView(GridView gridView, CommonAdapter<T> commonAdapter) {
        if (gridView != null && commonAdapter != null) {
            this.gridView = gridView;
            this.adapterForGridView = commonAdapter;
            TurnPageUtils.setGridViewTurnPageListener(gridView, turnPageListener);
        }
    }
    public abstract void refreshPageNumber(int currentPageNumber, int totalPageNumber, int totalItemsCount);

    public int getCurrentPageNumber() {
        return currentViewingPage;
    }

    public List<T> getAllDataList() {
        return allList;
    }

    public void setAllDataList(List<T> list) {
        allList = list;

        AllTotalSize = allList != null ? allList.size() : 0;
        currentRequestPage = 1;
        isLoadingData = false;
        currentViewingPage = 1;

    }

    public List<T> getCurrentAdapterDataList() {
        return currentListForAdapter;
    }


    public abstract void sendPageDataRequest(final int targetRequestingPage, final int targetViewingPage);

    public void sendFirstPageDataRequest() {
        if(NetUtils.isNetworkAvailable(getContext())){
            requestTime = System.currentTimeMillis();
        }else{
            requestTime = SpUtils.getLong(getClass().getSimpleName()+ String.valueOf(SpUtils.getUserId()),0);
            if(requestTime <= 0 ){
                requestTime = System.currentTimeMillis();
            }
        }
        resetPageInfo();
        sendPageDataRequest(currentRequestPage, currentViewingPage);
    }

    public void onPageLoadingDataBegan() {
        isLoadingData = true;
    }

    public void onPageLoadingDataFinished() {
        isLoadingData = false;
    }

    public boolean getIsLoadingData() {
        return isLoadingData;
    }

    public void notifyPageResponseDataIsValid(int totalPageCount, int targetRequestingPage) {
        AllTotalSize = totalPageCount;
        currentRequestPage = targetRequestingPage;
    }
    private List<T> lastData;

    public void onGetPageResponseData(List<T> pageResponseList, int targetViewingPage) {
        if (allList.size() > 0 && lastData != null && lastData == pageResponseList) {
            return;
        } else {
            if(currentRequestPage == 1){
                clearAdapterData();
                //保存当前的时间戳
                SpUtils.putLong(getClass().getSimpleName()+ String.valueOf(SpUtils.getUserId()),requestTime);
            }
            lastData = pageResponseList;
            allList.addAll(pageResponseList);
            refreshByViewingPage(targetViewingPage);
        }
    }
    private long requestTime;

    public long getRequestTime(){
        if(NetUtils.isNetworkAvailable(getContext())){
            if(requestTime <= 0 ){
                requestTime = System.currentTimeMillis();
            }
            return requestTime;
        }else{
            requestTime = SpUtils.getLong(getClass().getSimpleName()+ String.valueOf(SpUtils.getUserId()),0);
            if(requestTime <= 0 ){
                requestTime = System.currentTimeMillis();
            }
            return requestTime;
        }
    }
    TurnPageUtils.TurnPageListener turnPageListener = new TurnPageUtils.TurnPageListener() {
        @Override
        public void onPageUpPrevious() {
            ontouchKeyLeft();
        }

        @Override
        public void onPageDownNext() {
            ontouchKeyRight();
        }

        @Override
        public void onPageLeftPrevious() {
            ontouchKeyLeft();
        }

        @Override
        public void onPageRightNext() {
            ontouchKeyRight();
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
            if (turnPageListener != null) {
                turnPageListener.onPageUpPrevious();
            }
        } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
            if (turnPageListener != null) {
                turnPageListener.onPageDownNext();
            }
        }
        return super.onKeyUp(keyCode, event);
    }


    protected void resetPageInfo() {
        AllTotalSize = 0;
        currentRequestPage = 1;
        isLoadingData = false;
        currentViewingPage = 1;
    }

    private void clearAdapterData(){
        allList.clear();
        currentListForAdapter.clear();
        if (adapterForListView != null) {
            adapterForListView.setList(currentListForAdapter);
        }
        if (adapterForGridView != null) {
            adapterForGridView.setList(currentListForAdapter);
        }
    }


    private void ontouchKeyLeft() {
        LogUtils.i("ontouchKeyLeft----currentViewingPage=" + currentViewingPage);
        if (AllTotalSize > 0 && allList.size() > 0 && isLoadingData == false) {
            if (currentViewingPage >= 2) {

                //说明有数据
                refreshByViewingPage(currentViewingPage - 1);

            } else {
                ToastUtils.show(this, "第一页");
            }

        }
    }

    private void ontouchKeyRight() {
        if(mPerPageItemCount == 0){
            return;
        }
        LogUtils.i("ontouchKeyRight----currentViewingPage=" + currentViewingPage);

        if (AllTotalSize > 0 && allList.size() > 0 && isLoadingData == false) {
            int totalMaxPage = (AllTotalSize - 1) / mPerPageItemCount + 1;
            if (currentViewingPage < totalMaxPage) {

                //说明有数据
                refreshByViewingPage(currentViewingPage + 1);

            } else {
                ToastUtils.show(this, "最后一页");
            }
        }
    }

    /**
     * add by linzhicong
     * 跳转到第几页
     * @param pageIndex 第几页
     */
    public void turnPageIndex(int pageIndex) {
        for (int i = 1; i < pageIndex; i++) {
            ontouchKeyRight();
        }
    }

    public void refreshByViewingPage(int viewingPage) {
        if(mPerPageItemCount == 0){
            return;
        }
        int perPageCount = mPerPageItemCount;
        int begin = perPageCount * (viewingPage - 1);
        int end = perPageCount * viewingPage;
        int totalMaxPage = (AllTotalSize - 1) / perPageCount + 1;
        LogUtils.i("viewingPage=" + viewingPage + ", begin=" + begin +
                ",end=" + end + ",AllTotalSize=" + AllTotalSize +
                ",totalMaxPage=" + totalMaxPage +
                ", allList.size=" + (allList == null ? 0 : allList.size()));

        if (begin >= allList.size() && allList.size() < AllTotalSize) {
            if (!NetUtils.isNetworkAvailable(this)) {
                ToastUtils.show(this, "当前网络不可用");
                return;

            }else{
                currentViewingPage = viewingPage;
                sendPageDataRequest(currentRequestPage + 1, currentViewingPage);
            }

        } else {
            currentViewingPage = viewingPage;
            end = end > allList.size() ? allList.size() : end;
            if (currentListForAdapter != null) {
                currentListForAdapter.clear();
            } else {
                currentListForAdapter = new ArrayList<>();
            }

            LogUtils.i("real begin=" + begin + ",end=" + end);
            currentListForAdapter.addAll(allList.subList(begin, end));
            LogUtils.i("real begin=" + begin + ",end=" + end + ", currentListForAdapter.size=" + (currentListForAdapter == null ? 0 : currentListForAdapter.size()));
            //一定不能用下面这一行, 否则容易报 ConcurrentModificationException 异常.
//            currentListForAdapter = allList.subList(begin, end);


            if (adapterForListView != null) {
                adapterForListView.setList(currentListForAdapter);
            }
            if (adapterForGridView != null) {
                adapterForGridView.setList(currentListForAdapter);
            }
        }

        refreshPageNumber(viewingPage, totalMaxPage, AllTotalSize);

    }

    //计算可ListView可容纳item的个数
    private int mPerPageItemCount = 0;


    public interface ListviewRowCountCallback {
        public void onGetCount(int rowCount);
    }

    /**
     * 计算ListView 每一个item的高度
     *
     * @param listView
     * @param id
     * @param callback
     */
    public void initListviewItemCount(final ListView listView, @DimenRes final int id, final ListviewRowCountCallback callback) {
        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int dimenHeight = getResources().getDimensionPixelSize(id);
                mPerPageItemCount = listView.getHeight() / dimenHeight;
                listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (callback != null) {
                    callback.onGetCount(mPerPageItemCount);
                }
            }
        });
    }

    protected int getPerPageItemCount() {
        return mPerPageItemCount;
    }

    //针对GirdView 不适用于ListView计算mPerPageItemCount，直接赋值
    protected void setPerPageItemCount(int perPageItemCount) {
        mPerPageItemCount = perPageItemCount;
    }

    protected int getPerQuestItemCount() {
        return mPerPageItemCount * 4;
    }

    /**
     * 删除数据
     */

    protected void removeAndRefresh(int currentPage, int index) {
        if (currentPage == 1 && index == 0) {
            sendFirstPageDataRequest();
        } else {
            int deleteIndex = mPerPageItemCount * (currentPage - 1) + index;

            allList.remove(deleteIndex);
            AllTotalSize -= 1;
            currentRequestPage -= 1;
            int endIndex = currentRequestPage * getPerQuestItemCount();

            List<T> tempList = new ArrayList<>();
            tempList.addAll(allList.subList(0, endIndex));
            allList.clear();
            allList.addAll(tempList);
            refreshByViewingPage(currentPage);
        }

    }

}
