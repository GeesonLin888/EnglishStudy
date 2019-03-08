package com.lemonread.base.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.lemonread.base.application.BaseApplication;
import com.lemonread.base.receiver.NetChangeObserver;
import com.lemonread.base.receiver.NetStateReceiver;
import com.lemonread.base.utils.HidenKeyBoardUtils;
import com.lemonread.base.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @desc 添加了eventbus, talkingdata的支持仅限于BaseActivity集成
 * @author zhao
 * @time 2019/3/5 16:48
 */
public abstract class BaseEventBusActivity extends AppCompatActivity {
    NetChangeObserver mNetChangeObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 默认隐藏键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 固定竖屏,基类为抽象类，不能在manifest中设置
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //全刷一次?
        //EinkInvalidateUtils.fullRefresh(this);
    }

    /**
     * network connected
     */
    protected void onNetworkConnected(NetUtils.NetType type) {
    }

    /**
     * network disconnected
     */
    protected void onNetworkDisConnected() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    public boolean isActivityActive() {
        if (Build.VERSION.SDK_INT >= 17) {
            return !isDestroyed() && !isFinishing();
        } else {
            return !isFinishing();
        }
    }

    public boolean isActivityDestroyed() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyed() || isFinishing();
        } else {
            return isFinishing();
        }
    }

    public Activity getActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }
    public void onResume() {
        super.onResume();
    }
    public void onPause() {
        super.onPause();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean useEventBus() {
        return false;
    }


    /**
     * startActivity
     *
     * @param clazz
     */
    protected void jumpActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void jumpActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void jumpActivityThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void jumpActivityThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void jumpActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void jumpActivityForResult(Class<?> clazz, int requestCode,
                                         Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (HidenKeyBoardUtils.isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public String getUmengDesc(){
        return getClass().getSimpleName();
    }
}
