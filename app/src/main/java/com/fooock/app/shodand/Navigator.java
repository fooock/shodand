package com.fooock.app.shodand;

import android.content.Context;
import android.content.Intent;

import com.fooock.app.shodand.activities.ConfigurationActivity;
import com.fooock.app.shodand.activities.ShodandMainActivity;

/**
 * Class to navigate between activities or start services
 */
public class Navigator {

    private final Context context;

    Navigator(Context context) {
        this.context = context;
    }

    /**
     * Start the {@link ConfigurationActivity}
     */
    public void showConfigurationActivity() {
        Intent confActivity = new Intent(context, ConfigurationActivity.class);
        confActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(confActivity);
    }

    /**
     * Start the {@link ShodandMainActivity}
     */
    public void showShodandActivity() {
        Intent mainActivity = new Intent(context, ShodandMainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(mainActivity);
    }
}
