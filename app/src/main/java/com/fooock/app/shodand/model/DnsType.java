package com.fooock.app.shodand.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 *
 */
public class DnsType {

    public final int image;
    public final int name;

    public DnsType(@DrawableRes int image, @StringRes int name) {
        this.image = image;
        this.name = name;
    }
}
