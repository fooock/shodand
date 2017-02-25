package com.fooock.app.shodand.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 *
 */
public class ServicesType {

    public final int imgService;
    public final int titleService;

    public ServicesType(@DrawableRes int imgService, @StringRes int titleService) {
        this.imgService = imgService;
        this.titleService = titleService;
    }
}
