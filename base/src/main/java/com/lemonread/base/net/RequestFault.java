package com.lemonread.base.net;

/**
 * @author zhao
 * @desc 请求异常处理，例如errcode不等于0的时候的情况，不包括链接超时等的异常
 * @time 2019/3/5 9:58
 */
public class RequestFault extends Exception {
    private int errcode;
    private String errmsg;

    public RequestFault(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
