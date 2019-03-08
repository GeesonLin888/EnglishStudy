package com.lemonread.base.net;

import io.reactivex.annotations.NonNull;

public interface ResultCallBack<T> {
    void onNext(@NonNull T t);

    void onError(@NonNull Throwable e,int errcode,String errmsg);
}
