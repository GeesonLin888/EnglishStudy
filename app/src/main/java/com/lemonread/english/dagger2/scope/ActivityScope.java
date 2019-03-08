package com.lemonread.english.dagger2.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @desc
 * @author zhao
 * @time 2019/3/5 16:15
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
