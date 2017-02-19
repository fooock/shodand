package com.fooock.shodand.data;

import com.fooock.shodan.ShodanRestApi;
import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.ValidationRepository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 *
 */
public class ValidationDataRepository implements ValidationRepository {

    private ShodanRestApi shodanRestApi;

    @Override
    public Observable<Account> firstInit(ApiKey apiKey) {
        return initializeApi(apiKey).account()
                .map(new Function<com.fooock.shodan.model.user.Account, Account>() {
                    @Override
                    public Account apply(@NonNull com.fooock.shodan.model.user.Account account) throws Exception {
                        return new Account(account.getCredits(), account.isMember(),
                                account.getDisplayName(), account.getCreated());
                    }
                });
    }

    private ShodanRestApi initializeApi(ApiKey apiKey) {
        return shodanRestApi = new ShodanRestApi(apiKey.getApiKey());
    }
}
