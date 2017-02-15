package com.fooock.app.shodand;

import android.util.Log;

import timber.log.Timber;

/**
 *
 */
public class ShodandDefaultApplication extends ShodandApplication {

    @Override
    void initialize() {
        Timber.plant(new CrashReportingTree());
    }

    /**
     * Logs important information for crash reporting
     */
    private final static class CrashReportingTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable throwable) {
            if (priority == Log.DEBUG || priority == Log.VERBOSE) {
                return;
            }
            CrashLibrary.log(priority, tag, message);

            if (throwable == null) {
                return;
            }
            if (priority == Log.WARN) {
                CrashLibrary.logWarning(throwable);

            } else if (priority == Log.ERROR) {
                CrashLibrary.logError(throwable);
            }
        }
    }
}
