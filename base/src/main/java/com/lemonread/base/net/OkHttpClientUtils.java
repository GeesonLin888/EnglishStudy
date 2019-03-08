package com.lemonread.base.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.lemonread.base.application.BaseApplication;
import com.lemonread.base.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @desc okhttp工具类，配置基础参数，配置缓存等
 * @author zhao
 * @time 2019/3/5 10:11
 */
public class OkHttpClientUtils {
    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    //设置缓存的最大空间
    private static final long CACHE_MAX_SIZE = 1024 * 1024 * 200;//200Mb
    //请求连接超时的时间
    private static final long TIME_OUT_SECOND = 30;
    //请求重试的次数
    private static final int RETRY_REQUEST_TIME = 2;
    private static OkHttpClient.Builder builder;

    public static synchronized OkHttpClient.Builder getBuilder() {
        //缓存
        File cacheFile = new File(BaseApplication.getContext().getCacheDir(), "cache");
        final Cache cache = new Cache(cacheFile, CACHE_MAX_SIZE);
        if (builder == null) {
            builder = new OkHttpClient.Builder();
            builder.readTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS);
            builder.writeTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS);
            builder.connectTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS);
            builder.interceptors().add(new RetryInterceptor(RETRY_REQUEST_TIME));
            builder.interceptors().add(mRewriteCacheControlInterceptor);
            builder.networkInterceptors().add(mRewriteCacheControlInterceptor);
            // 添加公共参数拦截器
            builder.addInterceptor(new HttpCommonInterceptor.Builder().build());
            builder.cache(cache);
        }
        return builder;
    }

    /**
     * 重试拦截器
     */
    static class RetryInterceptor implements Interceptor {

        public int maxRetry = 2;//最大重试次数
        private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        public RetryInterceptor(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            retryNum = 0;
            Response response = null;
            IOException exception = null;
            try {
                response = chain.proceed(request);
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
            }
            boolean isCanceled = isCanceledMessage(exception);
            while (retryNum < maxRetry && !isCanceled && (response == null || !response.isSuccessful())) {
                retryNum++;
                try {
                    response = chain.proceed(request);
                } catch (IOException e) {
                    e.printStackTrace();
                    exception = e;
                }
                isCanceled = isCanceledMessage(exception);
            }
            if (response == null) {
                throw exception;
            }
            return response;
        }
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            String cacheControl = request.cacheControl().toString();
            if (!NetUtils.isNetworkAvailable(BaseApplication.getContext())) {
                //                ToastUtils.showSingleToast("网络请求失败，请检查网络后重试");
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .build();

            }

            Response originalResponse = chain.proceed(request);
            if (NetUtils.isNetworkAvailable(BaseApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
    /**
     * https://www.jianshu.com/p/b74466039b84
     *
     * @param e
     * @return
     */
    public static boolean isCanceledMessage(Exception e) {
        if (e != null && e.getMessage() != null && (e.getMessage().contains("closed") || e.getMessage().contains("Canceled"))) {

            return true;
        }
        return false;

    }

    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetUtils.isNetworkAvailable(BaseApplication.getContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }
}
