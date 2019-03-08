package com.lemonread.base.view.activity;

import android.os.Bundle;

import com.lemonread.base.vp.BasePresenter;
import com.lemonread.base.vp.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @desc
 * @author zhao
 * @time 2019/3/5 16:48
 */
public abstract class BaseActivity<T extends BasePresenter> extends BaseEventBusActivity implements BaseView{
    @Inject
    protected T mPresenter;
    Unbinder bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSoftInputMode();
        setContentView(getContentViewLayoutID());
        bind = ButterKnife.bind(this);
        initPresenter();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        initViewsAndEvents();
    }

    protected void setSoftInputMode(){
        //修改输入法设置
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    public abstract void initPresenter();

    @Override
    public void showToast(String text, int duration) {

    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showErrorPage(String errorMsg) {

    }

    @Override
    public void showNoDataPage(String msg) {

    }
}
