package com.fooock.app.shodand.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 *
 */
public interface Row<T> {

    @StringRes
    int getName();

    @StringRes
    int getDescription();

    @DrawableRes
    int getIcon();

    T data();
}
