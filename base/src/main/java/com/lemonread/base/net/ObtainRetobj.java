package com.lemonread.base.net;

import io.reactivex.functions.Function;
/**
 * @desc 剥离 最终数据(也就是获取retobj的数据)
 * @author zhao
 * @time 2019/3/5 10:16
 */
public class ObtainRetobj<T> implements Function<BaseResponse<T>,T> {
    @Override
    public T apply(BaseResponse<T> tBaseResponse) throws Exception {
        if (!tBaseResponse.isSuccess()) {
            throw new RequestFault(tBaseResponse.errcode, tBaseResponse.errmsg);
        }
        return tBaseResponse.retobj;
    }
}