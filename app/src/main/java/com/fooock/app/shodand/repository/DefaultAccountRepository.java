package com.fooock.app.shodand.repository;

import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.AccountRepository;

import io.reactivex.Observable;

/**
 *
 */
public class DefaultAccountRepository implements AccountRepository {

    private static final AccountRepository INSTANCE = new DefaultAccountRepository();

    private DefaultAccountRepository() {
        // force singleton
    }

    public static AccountRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<Account> account(ApiKey apiKey) {
        return Observable.empty();
    }
}
