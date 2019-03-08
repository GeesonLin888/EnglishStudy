package com.lemonread.english.dagger2.component;

import android.app.Activity;
import com.lemonread.english.dagger2.module.ActivityModule;
import com.lemonread.english.dagger2.scope.ActivityScope;
import com.lemonread.english.demo.TestActivity;

import dagger.Component;

/** 
 * @desc 
 * @author zhao
 * @time 2019/3/5 16:15
 */
@ActivityScope
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
    void inject(TestActivity testActivity);
}
