package com.lemonread.english.utils;

import android.support.v4.app.Fragment;

import com.lemonread.base.view.activity.BaseActivity;
import com.lemonread.base.view.fragment.BaseFragment;
import com.lemonread.english.dagger2.component.ActivityComponent;
import com.lemonread.english.dagger2.component.DaggerActivityComponent;
import com.lemonread.english.dagger2.component.DaggerFragmentComponent;
import com.lemonread.english.dagger2.component.FragmentComponent;
import com.lemonread.english.dagger2.module.ActivityModule;
import com.lemonread.english.dagger2.module.FragmentModule;


public class DaggerComponentUtil {
    public static FragmentComponent getFragmentComponent(BaseFragment fragment) {
        return DaggerFragmentComponent.builder()
                .fragmentModule(getFragmentModule(fragment))
                .build();
    }

    private static  FragmentModule getFragmentModule(BaseFragment fragment) {
        return new FragmentModule(fragment);
    }

    public static ActivityComponent getActivityComponent(BaseActivity activity) {
        return DaggerActivityComponent.builder()
                .activityModule(getActivityModule(activity))
                .build();
    }

    private static ActivityModule getActivityModule(BaseActivity activity) {
        return new ActivityModule(activity);
    }
}
