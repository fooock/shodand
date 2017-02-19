package com.fooock.shodand.data;

import com.fooock.shodan.ShodanRestApi;
import com.fooock.shodand.data.consumer.SaveAccount;
import com.fooock.shodand.data.mapper.AccountMapper;
import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.ValidationRepository;

import io.reactivex.Observable;

/**
 *
 */
public class ValidationDataRepository implements ValidationRepository {

    private final AccountDataSource accountDataSource;

    public ValidationDataRepository(AccountDataSource accountDataSource) {
        this.accountDataSource = accountDataSource;
    }

    @Override
    public Observable<Account> firstInit(ApiKey apiKey) {
        return initializeApi(apiKey).account()
                .map(new AccountMapper()).doOnNext(new SaveAccount(accountDataSource));
    }

    private ShodanRestApi initializeApi(ApiKey apiKey) {
        return new ShodanRestApi(apiKey.getApiKey());
    }
}
