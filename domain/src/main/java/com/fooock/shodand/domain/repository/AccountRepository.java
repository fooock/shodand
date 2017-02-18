package com.fooock.shodand.domain.repository;

import com.fooock.shodand.domain.model.Account;

import io.reactivex.Observable;

/**
 *
 */
public interface AccountRepository {

    Observable<Account> account();
}
