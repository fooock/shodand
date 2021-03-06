package com.fooock.app.shodand.presenter;

import android.support.annotation.NonNull;

import com.fooock.app.shodand.view.BaseView;

import timber.log.Timber;

/**
 * Base class for all presenters
 */
public abstract class BasePresenter<T extends BaseView> {

    T customView;

    /**
     * Method to release resources, like opening connections etc
     */
    abstract void release();

    /**
     * Attach this presenter to the given view type
     *
     * @param view view
     */
    public final void attachView(@NonNull T view) {
        this.customView = view;
        Timber.d("View attached...");
    }

    /**
     * Detach the presenter view
     */
    public final void detachView() {
        release();
        customView = null;
        Timber.d("View detached...");
    }

    /**
     * Check if the view is attached to this presenter
     *
     * @return true if attached, false if not
     */
    final boolean isAttached() {
        return customView != null;
    }

}
