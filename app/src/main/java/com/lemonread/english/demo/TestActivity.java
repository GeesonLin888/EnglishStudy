package com.lemonread.english.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lemonread.base.utils.LogUtils;
import com.lemonread.base.utils.SpUtils;
import com.lemonread.base.view.activity.BasePageRequestActivity;
import com.lemonread.english.R;
import com.lemonread.english.utils.DaggerComponentUtil;

public class TestActivity extends BasePageRequestActivity<TestBean,TestPresenter> implements Contract.TestView {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void initViewsAndEvents() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IjEzNzI5MDQ3NDIwIiwidXNlcklkIjo4NzQsInNuIjoiUzYyQUMwMTIwMTgwOTAxIiwiY2xpZW50VHlwZSI6IjEiLCJpYXQiOjE1NDUwOTkyMjIsImV4cCI6MTU1MzczOTIyMn0.ef47Uxx3PQK6UG3EHOMX-QGfsMFNpeXxVYhzIQzOO_8";
        int userId = 874;
        SpUtils.putUserId(userId);
        SpUtils.putToken(token);
        mPresenter.getData(1, 10);
    }

    @Override
    public void initPresenter() {
        //mPresenter = new TestPresenter();
        //dagger2注入presenter实例
        DaggerComponentUtil.getActivityComponent(this).inject(this);
    }

    @Override
    public void refreshPageNumber(int currentPageNumber, int totalPageNumber, int totalItemsCount) {

    }

    @Override
    public void sendPageDataRequest(int targetRequestingPage, int targetViewingPage) {

    }

    @Override
    public void setData() {
        LogUtils.i("===========setData");
    }
}
