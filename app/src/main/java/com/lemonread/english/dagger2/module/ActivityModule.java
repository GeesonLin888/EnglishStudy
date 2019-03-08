package com.lemonread.english.dagger2.module;

import android.app.Activity;

import com.lemonread.english.dagger2.scope.ActivityScope;
import com.lemonread.english.demo.TestPresenter;

import dagger.Module;
import dagger.Provides;

/** 
 * @desc 
 * @author zhao
 * @time 2019/3/5 16:17
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }

    /*@Provides
    @ActivityScope
    public TestPresenter providePresenter() {
        return new TestPresenter(1,2);
    }*/
}