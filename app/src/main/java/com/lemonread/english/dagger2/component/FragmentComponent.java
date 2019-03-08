package com.lemonread.english.dagger2.component;

import android.app.Activity;
import com.lemonread.english.dagger2.module.FragmentModule;
import com.lemonread.english.dagger2.scope.FragmentScope;

import dagger.Component;

/** 
 * @desc 
 * @author zhao
 * @time 2019/3/5 16:15
 */
@FragmentScope
@Component(modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
}