package com.lemonread.base.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * @desc 用户创建RetrofitService
 * @author zhao
 * @time 2019/3/5 10:13
 */
public class RetrofitServiceManager {
    private Retrofit mRetrofit;
    private static RetrofitServiceManager mInstance = null;

    public static RetrofitServiceManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitServiceManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitServiceManager();
                }
            }
        }
        return mInstance;
    }
    private RetrofitServiceManager() {
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(OkHttpClientUtils.getBuilder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl.BASE_URL)
                .build();
    }
    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}