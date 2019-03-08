package com.lemonread.base.utils;

import android.content.Context;

import com.lemonread.base.application.BaseApplication;
import com.lemonread.base.view.activity.BaseActivity;
import com.lemonread.base.view.activity.BaseEventBusActivity;
import com.lemonread.base.view.fragment.BaseFragment;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


/**
 * @desc 友盟统计工具类
 * @author zhao
 * @time 2019/3/7 10:12
 */
public class UmengUtils {
    public static final boolean isUseUmeng = true;

    public static void initUmeng(){
        UMConfigure.init(BaseApplication.getContext(), UMConfigure.DEVICE_TYPE_PHONE, null);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(LogUtils.isDebug());
        // 选用MANUAL页面采集模式,手动集成模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
    }
    public static void onPageStart(BaseFragment fragment) {
        if (isUseUmeng){
            MobclickAgent.onPageStart(fragment.getUmengDesc());
        }
    }
    public static void onPageEnd(BaseFragment  fragment) {
        if (isUseUmeng){
            MobclickAgent.onPageEnd(fragment.getUmengDesc());
        }
    }
    public static void onResume(BaseActivity activity) {
        if (isUseUmeng){
            MobclickAgent.onResume(activity);
        }
    }
    public static void onPause(BaseActivity activity) {
        if (isUseUmeng){
            MobclickAgent.onPause(activity);
        }
    }
}
