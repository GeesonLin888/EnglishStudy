package com.lemonread.base.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

/**
 * @desc
 * @author zhao
 * @time 2019/3/5 10:09
 */

public class BaseApplication extends Application {
    private static Context mContext;
    private static List<Activity> mActivities = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mActivities.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mActivities.remove(activity);
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }

    public static synchronized void clearAllActivities() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            activity.finish();
            i = mActivities.size();
        }
        mActivities.clear();
    }

    //清除除MainActivity以外的所有activity
    public static synchronized void clearToMainActivity() {
        for (int i = mActivities.size() - 1; i > 0; ) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }


    public static int getAllActivitiesCount() {
        return mActivities.size();
    }

    public static synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }
}
