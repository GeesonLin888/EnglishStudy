package com.lemonread.english.dagger2.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.lemonread.english.dagger2.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;
/**
 * @desc 容器(用于封装注解对象)
 * @author zhao
 * @time 2019/3/5 16:15
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
