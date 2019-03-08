package com.lemonread.base.vp;
/**
 * @desc
 * 本着一个View拥有一个Presenter的原则,Presenter------>View,
 * 此类中需要依赖一个视图层的接口，因此在继承此类的时候通过泛型看到需要传入一个继承自BaseView接口的接口。
 * @author zhao
 * @time 2019/3/5 16:23
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
