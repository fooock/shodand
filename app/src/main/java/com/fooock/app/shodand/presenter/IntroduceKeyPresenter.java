package com.fooock.app.shodand.presenter;

import com.fooock.app.shodand.view.IntroduceKeyView;
import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.interactor.GetAccountProfile;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.AccountRepository;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 *
 */
public class IntroduceKeyPresenter extends BasePresenter<IntroduceKeyView> {

    private final GetAccountProfile getAccountProfile;

    /**
     * Create this presenter
     *
     * @param repository     {@link AccountRepository}
     * @param mainThread     {@link MainThread}
     * @param threadExecutor {@link ThreadExecutor}
     */
    public IntroduceKeyPresenter(AccountRepository repository, MainThread mainThread,
                                 ThreadExecutor threadExecutor) {
        getAccountProfile = new GetAccountProfile(repository, mainThread, threadExecutor);
    }

    /**
     * Validate the api key. For validation this method makes a request to get the
     * account details for the given user api key, if success, the api key is valid
     *
     * @param apiKey Account api key
     */
    public void validateApiKey(final String apiKey) {
        customView.showProgress();
        Timber.d("Prepare for validate API key");

        if (apiKey == null || apiKey.isEmpty()) {
            customView.hideProgress();
            customView.emptyApiKey();
            Timber.d("Empty API key");
            return;
        }
        getAccountProfile.execute(new DisposableObserver<Account>() {
            @Override
            public void onNext(Account account) {
                Timber.d("Received account: %s", account);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                if (isAttached()) {
                    customView.hideProgress();
                    customView.showError(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                Timber.d("API key validation complete");
                if (isAttached()) {
                    customView.hideProgress();
                    customView.startApplication();
                }
            }
        }, new ApiKey(apiKey));
    }

    @Override
    void release() {
        Timber.d("Closing API key validation...");
        getAccountProfile.close();
    }
}
