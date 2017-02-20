package com.fooock.app.shodand.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.fooock.app.shodand.ShodandApplication;

import timber.log.Timber;

/**
 *
 */
abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Fragment in onCreate()...");
        initializeComponents(getShodandApplication());
    }

    /**
     * Hide the action bar
     */
    protected void hideActionBar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity())
                .getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.hide();
    }

    /**
     * Show the action bar
     */
    protected void showActionBar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity())
                .getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.show();
    }

    /**
     * Change the title of the activity where this fragment is attached
     *
     * @param title string resource
     */
    protected void setTitle(@StringRes int title) {
        ActionBar actionBar = ((AppCompatActivity) getActivity())
                .getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setTitle(title);
    }

    /**
     * Get the {@link ShodandApplication} class to initialize other components
     *
     * @return Application
     */
    private ShodandApplication getShodandApplication() {
        return (ShodandApplication) getActivity().getApplication();
    }

    /**
     * Method to initialize components when {@link #onAttach(Context context)} is called
     *
     * @param application ShodandApplication
     */
    abstract void initializeComponents(ShodandApplication application);

}
