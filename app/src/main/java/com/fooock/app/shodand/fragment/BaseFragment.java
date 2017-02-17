package com.fooock.app.shodand.fragment;

import android.app.Fragment;
import android.content.Context;

import com.fooock.app.shodand.ShodandApplication;

import timber.log.Timber;

/**
 *
 */
abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.d("Fragment attached...");

        initializeComponents(getShodandApplication());
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
