package com.lemonread.english.demo;


import com.lemonread.base.net.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import java.util.Map;

public interface MyService {
  @GET("masterpiece/getPlanList")
  Observable<BaseResponse<TestBean>> getPlanList(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize, @Query("userId") int userId, @Query("token") String token);
  @GET("masterpiece/getPlanList")
  Observable<BaseResponse<TestBean>> getPlanList(@QueryMap Map<String, Object> params);
  @POST("news/v3/notifyReadTime")
  @FormUrlEncoded
  Observable<BaseResponse> commitTime(@Field("readTime") long readTime, @Field("userId") int userId, @Field("newsId") int newsId, @Field("token") String token);

  @POST("news/v3/notifyReadTime")
  @FormUrlEncoded
  Observable<BaseResponse> commitTime(@FieldMap Map<String, Object> params);
}