package com.lemonread.base.net;

import com.lemonread.base.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @desc 拦截器 向请求头里添加公共参数
 * @author zhao
 * @time 2019/3/5 10:03
 */
public class HttpCommonInterceptor implements Interceptor {
    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    public HttpCommonInterceptor() {
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtils.d("HttpCommonInterceptor", "add common params");
        Request oldRequest = chain.request();
        // 添加新的参数，添加到url 中 
        /*HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder()     
        .scheme(oldRequest.url().scheme()) 
        .host(oldRequest.url().host());*/

        // 新的请求
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());

        if("GET".equals(oldRequest.method())){
            //添加缓存设置
            requestBuilder.header("Cache-Control", OkHttpClientUtils.getCacheControl());
        }
        //添加公共参数,添加到header中
        if (mHeaderParamsMap.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                requestBuilder.header(params.getKey(), params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        //打印请求参数
        String method = newRequest.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (newRequest.body() instanceof FormBody) {
                FormBody body = (FormBody) newRequest.body();
                if(body != null){
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    if(sb.length() > 1){
                        sb.delete(sb.length() - 1, sb.length());
                    }
                }
                LogUtils.d("RequestParams-POST", "POST ==  "+newRequest.url());
                LogUtils.d("RequestParams-POST", "| RequestParams:{"+sb.toString()+"}");
            }
        }else if("GET".equals(method)){
            LogUtils.d("RequestParams-GET", "GET ==  "+newRequest.url());
        }
        Response response = chain.proceed(newRequest);
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        LogUtils.i("Response","Response == "+responseBody.string());
        return response;
    }

    public static class Builder {
        HttpCommonInterceptor mHttpCommonInterceptor;

        public Builder() {
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            mHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public HttpCommonInterceptor build() {
            return mHttpCommonInterceptor;
        }

    }
}
