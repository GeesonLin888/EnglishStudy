package com.lemonread.base.net;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * @desc
 * @author zhao
 * @time 2019/3/7 8:35
 */
@Deprecated
public interface RetrofitApiService<T> {

    /********************************************Get请求********************************************/
    @GET
    Observable<BaseResponse<T>> doGetToRetobj(@Url String url, @QueryMap Map<String, Object> params);
    @GET
    Observable<BaseResponse> doGet(@Url String url, @QueryMap Map<String, Object> params);

    /********************************************Post请求********************************************/

    @POST
    @FormUrlEncoded
    Observable<BaseResponse<T>> doPostByMapToRetobj(@Url String url,@FieldMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseResponse<T>> doPostByMap(@Url String url,@FieldMap Map<String, Object> params);


    /**********************************************************************************************/
    @POST
    @FormUrlEncoded
    Observable<String> doPostWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    @GET
    Observable<String> doGetWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, Object> params);

    @GET
    Observable<String> doGet(@Url String url);

    @GET
    Observable<String> doGetWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap);

    @POST
    @FormUrlEncoded
    Observable<String> doPostByBody(@Url String url, @Body RequestBody requestBody);

}


