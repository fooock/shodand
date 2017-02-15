package com.fooock.app.shodand;

import android.app.Application;

/**
 *
 */
public abstract class ShodandApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initialize();
    }

    /**
     * Custom initialization depending on the build type
     */
    abstract void initialize();
}
