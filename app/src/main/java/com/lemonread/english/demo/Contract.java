package com.lemonread.english.demo;

import com.lemonread.base.vp.BasePresenter;
import com.lemonread.base.vp.BaseRxPresenter;
import com.lemonread.base.vp.BaseView;

public interface Contract {
    interface TestView extends BaseView{
        void setData();
    }
    interface Presenter extends BasePresenter<TestView> {
        void getData(int currentPage,int pageSize);
    }
}
