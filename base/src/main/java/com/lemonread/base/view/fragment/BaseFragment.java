package com.lemonread.base.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lemonread.base.receiver.NetChangeObserver;
import com.lemonread.base.receiver.NetStateReceiver;
import com.lemonread.base.utils.EinkInvalidateUtils;
import com.lemonread.base.utils.LogUtils;
import com.lemonread.base.utils.NetUtils;
import com.lemonread.base.vp.BasePresenter;
import com.lemonread.base.vp.BaseView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    @Inject
    protected T mPresenter;
    private float mScreenDensity;
    protected int mScreenHeight;
    protected int mScreenWidth;

    NetChangeObserver mNetChangeObserver;


    Unbinder bind;
    protected View viewRoot;


    private boolean isLastVisible = false;
    private boolean hidden = false;
    private boolean isFirst = true;
    private boolean isResuming = false;
    private boolean isViewDestroyed = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d("-----onCreateView----");
        if (isSwitchScreen()) {
            smoothSwitchScreen();
        }
        if (getContentViewLayoutID() != 0) {
            LogUtils.d("-----getContentViewLayoutID----");
            viewRoot = inflater.inflate(getContentViewLayoutID(), null);
            bind = ButterKnife.bind(this, viewRoot);
            initPresenter();
            if (mPresenter != null) {
                mPresenter.attachView(this);
            }
            return viewRoot;
        } else {
            LogUtils.d("-----getContentViewLayoutID-===0---");
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isSwitchScreen() {
        return false;
    }

    private void smoothSwitchScreen() {
        ViewGroup rootView = ((ViewGroup) getActivity().findViewById(android.R.id.content));
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        rootView.setPadding(0, statusBarHeight, 0, 0);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


    }

    public boolean isActivityActive() {
        Activity activity = getActivity();
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                return !activity.isDestroyed() && !activity.isFinishing();
            } else {
                return !activity.isFinishing();
            }
        }
        return false;

    }

    public boolean isActivityDestroyed() {
        Activity activity = getActivity();
        if (activity != null) {

            if (Build.VERSION.SDK_INT >= 17) {
                return activity.isDestroyed() || activity.isFinishing();
            } else {
                return activity.isFinishing();
            }
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLastVisible = false;
        hidden = false;
        isFirst = true;
        isViewDestroyed = false;

        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public boolean disableUmeng() {
        return false;
    }

    @Override
    public void onResume() {
        LogUtils.i("onResume----" + this.toString() + ",getUserVisibleHint=" + getUserVisibleHint() + ",isHidden=" + isHidden() + ", class=" + getClass().getName());
        super.onResume();
        isResuming = true;
        tryToChangeVisibility(true);


    }

    @Override
    public void onPause() {
        LogUtils.i("onPause----" + this.toString() + ",getUserVisibleHint=" + getUserVisibleHint() + ",isHidden=" + isHidden() + ", class=" + getClass().getName());
        super.onPause();
        isResuming = false;
        tryToChangeVisibility(false);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewDestroyed = true;

        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i("setUserVisibleHint=" + isVisibleToUser + ", getclass=" + getClass().getName());

        setUserVisibleHintClient(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onHiddenChangedClient(hidden);
        if (!hidden) {
            EinkInvalidateUtils.partRefresh(getActivity());
        }
    }

    private void setUserVisibleHintClient(boolean isVisibleToUser) {
        tryToChangeVisibility(isVisibleToUser);
        if (isAdded()) {
            // 当Fragment不可见时，其子Fragment也是不可见的。因此要通知子Fragment当前可见状态改变了。
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).setUserVisibleHintClient(isVisibleToUser);
                    }
                }
            }
        }
    }

    public void onHiddenChangedClient(boolean hidden) {
        this.hidden = hidden;
        tryToChangeVisibility(!hidden);
        if (isAdded()) {
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).onHiddenChangedClient(hidden);
                    }
                }
            }
        }
    }

    private void tryToChangeVisibility(boolean tryToShow) {
        // 上次可见
        if (isLastVisible) {
            if (tryToShow) {
                return;
            }
            if (!isFragmentVisible()) {
                onFragmentPause();
                isLastVisible = false;
            }
            // 上次不可见
        } else {
            boolean tryToHide = !tryToShow;
            if (tryToHide) {
                return;
            }
            if (isFragmentVisible()) {
                onFragmentResume(isFirst, isViewDestroyed);
                isLastVisible = true;
                isFirst = false;
            }
        }
    }

    /**
     * Fragment是否可见
     *
     * @return
     */
    public boolean isFragmentVisible() {
        if (isResuming()
                && getUserVisibleHint()
                && !hidden) {
            return true;
        }
        return false;
    }

    /**
     * Fragment 是否在前台。
     *
     * @return
     */
    private boolean isResuming() {
        return isResuming;
    }

    /**
     * Fragment 可见时回调
     *
     * @param isFirst         是否是第一次显示
     * @param isViewDestroyed Fragment 的 View 被回收，但是Fragment实例仍在。
     */
    protected void onFragmentResume(boolean isFirst, boolean isViewDestroyed) {
        if (!disableUmeng()) {
            //UmengUtils.onPageStart(this);
        }

    }

    /**
     * Fragment 不可见时回调
     */
    protected void onFragmentPause() {
        if (!disableUmeng()) {
            //UmengUtils.onPageEnd(this);
        }
    }

    public void hideLoadingDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.d("-----onViewCreated----");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

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


        initViewsAndEvents(view);
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

    protected abstract int getContentViewLayoutID();

    protected abstract void initViewsAndEvents(View view);

    public abstract void initPresenter();

    protected FragmentManager getSupportFragmentManager() {
        if (getActivity() != null) {
            return getActivity().getSupportFragmentManager();
        } else {
            return null;
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.log("BaseFragment", "-----onActivityCreated----");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void jumpActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LogUtils.log("---BaseFragment-----", "--------onDestroy----------");
        if (bind != null) {
            bind.unbind();
        }
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }


    }

    protected boolean useEventBus() {

        return false;
    }


    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void jumpActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void jumpActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
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
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public String getUmengDesc() {
        return getClass().getSimpleName();
    }
    @Override
    public void showToast(String text, int duration) {

    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showErrorPage(String errorMsg) {

    }

    @Override
    public void showNoDataPage(String msg) {

    }
}
