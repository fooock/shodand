package com.fooock.app.shodand;

import timber.log.Timber;

/**
 *
 */
public class ShodandDefaultApplication extends ShodandApplication {

    @Override
    void initialize() {
        Timber.plant(new Timber.DebugTree());
    }
}
