package com.lemonread.base.vp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
/**
 * @desc 基于Rx的Presenter封装, 控制订阅的生命周期 并且实现BasePresenter
 * @author zhao
 * @time 2019/3/5 16:26
 */
public class BaseRxPresenter<T extends BaseView> implements BasePresenter<T> {
    private T mView;
    protected boolean viewAttach = false;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        this.mView = view;
        viewAttach = true;
    }

    @Override
    public void detachView() {
        this.mView = null;
        viewAttach = false;
        unSubscribe();//取消订阅
    }

    public T getView() {
        return mView;
    }

    protected boolean isViewAttach() {
        return viewAttach;
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
