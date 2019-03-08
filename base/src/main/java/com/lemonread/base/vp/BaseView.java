package com.lemonread.base.vp;
/**
 * @desc 网络请求状态的基础view
 * @author zhao
 * @time 2019/3/5 16:24
 */
public interface BaseView {
    void showToast(String text, int duration);

    void showToast(String text);

    void showLoading();

    void dismissLoading();

    void showErrorPage(String errorMsg);

    void showNoDataPage(String msg);
}
