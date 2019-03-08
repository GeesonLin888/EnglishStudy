package com.lemonread.english.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.lemonread.base.application.BaseApplication;
import com.lemonread.base.utils.LogUtils;
import com.lemonread.base.utils.UmengUtils;
import com.lemonread.english.MainActivity;
import com.lemonread.english.constant.Const;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.eventbus.EventBus;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

public class MyApplication extends BaseApplication{
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //友盟初始化
        UmengUtils.initUmeng();
        // bugly这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        initBeta();
        Bugly.init(this, Const.BUGLY_APP_ID, LogUtils.isDebug());
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //bugly的一些安装以及配置
        buglyConfig(base);
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }
    private void buglyConfig(Context base){
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
        //设置最大的补丁合成次数
        UpgradePatchRetry.getInstance(base).setMaxRetryCount(100);
    }
    private void initBeta(){
        //Beta.autoCheckUpgrade = true;
        //设置更新检查周期,6个小时检查一遍
        //Beta.upgradeCheckPeriod = 60 * 1000 * 60 * 6;
        //Beta.upgradeCheckPeriod = 60 * 1000 * 6;
        /* 设置更新状态回调接口 */

        //设置sd卡的Download为更新资源存储目录
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //添加可显示弹窗的Activity
        Beta.canShowUpgradeActs.add(MainActivity.class);
        //禁止自动检查更新
        Beta.autoCheckUpgrade = false;
        //自动安装apk
        //Beta.autoInstallApk = true;
        //dealDownloadListener();
        //设置此监听，sdk将不再处理弹窗，下载等事件
        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int i, UpgradeInfo upgradeInfo, boolean b, boolean b1) {
                if(upgradeInfo != null){
                    EventBus.getDefault().post(upgradeInfo);
                }
            }
        };
    }
}
