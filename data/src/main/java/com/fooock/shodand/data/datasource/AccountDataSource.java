package com.fooock.shodand.data.datasource;

import com.fooock.shodand.domain.model.Account;

import io.reactivex.Observable;

/**
 *
 */
public interface AccountDataSource {

    void save(Account entity);

    Observable<Account> get();
}
