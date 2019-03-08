package com.lemonread.base.net;


import com.lemonread.base.utils.LogUtils;

public class BaseUrl {
    private static final String TEST_BASE_URL = "http://121.199.24.124:3100/";
    private static final String PRODUCT_BASE_URL = "http://student.api.lemonread.com:80/";
    public static final String BASE_URL = LogUtils.isDebug() ? TEST_BASE_URL : PRODUCT_BASE_URL;
}
