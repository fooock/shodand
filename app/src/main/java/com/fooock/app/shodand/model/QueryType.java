package com.fooock.app.shodand.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 *
 */
public class QueryType {

    public final int image;
    public final int name;

    public QueryType(@DrawableRes int image, @StringRes int name) {
        this.image = image;
        this.name = name;
    }
}
