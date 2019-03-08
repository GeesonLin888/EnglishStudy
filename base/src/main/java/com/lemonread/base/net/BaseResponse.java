package com.lemonread.base.net;
/**
 * @desc 网络请求结果 基类
 * @author zhao
 * @time 2019/3/5 9:56
 */
public class BaseResponse<T> {
    public int errcode;
    public String errmsg;
    public T retobj;
    public boolean isSuccess() {
        return errcode == 0;
    }
}