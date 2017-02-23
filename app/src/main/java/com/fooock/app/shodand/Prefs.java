package com.fooock.app.shodand;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 *
 */
public class Prefs {

    private static final String PREF_API_KEY = "com.fooock.app.shodand.API_KEY";

    private final SharedPreferences preferences;

    Prefs(Context context) {
        preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public String getApiKey() {
        return preferences.getString(PREF_API_KEY, "");
    }

    public void saveApiKey(String apiKey) {
        preferences.edit().putString(PREF_API_KEY, apiKey).apply();
    }
}
