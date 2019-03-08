package com.lemonread.base.net;

import com.lemonread.base.utils.LogUtils;
import com.lemonread.base.utils.SpUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HttpRequestLoader {
    private static HttpRequestLoader mInstance = null;

    public static HttpRequestLoader getInstance() {
        if (mInstance == null) {
            synchronized (HttpRequestLoader.class) {
                if (mInstance == null) {
                    mInstance = new HttpRequestLoader();
                }
            }
        }
        return mInstance;
    }
    /**
     * @param observable
     * @param <T>
     * @return
     */
    public <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Disposable requestToRetobj(Observable<BaseResponse<T>> observable, final ResultCallBack<T> callBack) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ObtainRetobj<T>())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T retobj) throws Exception {
                        if(callBack != null){
                            callBack.onNext(retobj);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof RequestFault) {
                            RequestFault fault = (RequestFault) throwable;
                            callBack.onError(throwable,fault.getErrcode(),fault.getErrmsg());
                        }else{
                            callBack.onError(throwable,-1,"");
                        }
                    }
                });
    }

    public Disposable requestToBaseResponse(Observable<BaseResponse> observable, final ResultCallBack<BaseResponse> callBack) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if(callBack != null){
                            callBack.onNext(response);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof RequestFault) {
                            RequestFault fault = (RequestFault) throwable;
                        }
                    }
                });
    }

    public Map<String, Object> addCommonParams() {
        Map<String,Object> commonParams = new HashMap<>();
        commonParams.put("userId",SpUtils.getUserId());
        commonParams.put("token",SpUtils.getToken());
        return commonParams;
    }
}