package com.fooock.shodand.domain.repository;

import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.model.Account;

import io.reactivex.Observable;

/**
 *
 */
public interface ValidationRepository {

    Observable<Account> firstInit(ApiKey apiKey);
}
