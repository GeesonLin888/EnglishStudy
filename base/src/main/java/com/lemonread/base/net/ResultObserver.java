package com.lemonread.base.net;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
/**
 * @desc 继承Observer，可以减少实现2个方法
 * @author zhao
 * @time 2019/3/5 10:24
 */
public abstract class ResultObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
