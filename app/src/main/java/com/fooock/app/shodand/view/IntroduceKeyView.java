package com.fooock.app.shodand.view;

/**
 * Interface for {@link com.fooock.app.shodand.fragment.IntroduceKeyFragment}
 */
public interface IntroduceKeyView extends BaseView {

    /**
     * Show the validation progress
     */
    void showProgress();

    /**
     * Hide the validation progress
     */
    void hideProgress();

    /**
     * Show an error in the validation process
     *
     * @param message Error message
     */
    void showError(String message);

    /**
     * Validation successful, start the main application
     */
    void startApplication();
}
