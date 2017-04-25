package com.fooock.shodand.cli;

import com.fooock.shodand.data.datasource.AccountDataSource;
import com.fooock.shodand.domain.model.Account;

import io.reactivex.Observable;

/**
 *
 */
final class DefaultAccountSource implements AccountDataSource {

    private static final AccountDataSource INSTANCE = new DefaultAccountSource();

    public static AccountDataSource getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Account entity) {

    }

    @Override
    public Observable<Account> get() {
        return null;
    }
}
