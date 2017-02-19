package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.AccountRepository;

import io.reactivex.Observable;

/**
 * Get the {@link Account} profile from the {@link AccountRepository}
 */
public class GetAccountProfile extends BaseInteractor<Account, ApiKey> {

    private final AccountRepository accountRepository;

    public GetAccountProfile(AccountRepository repository, MainThread mainThread,
                             ThreadExecutor threadExecutor) {
        super(mainThread, threadExecutor);
        accountRepository = repository;
    }

    @Override
    protected Observable<Account> result(ApiKey params) {
        return accountRepository.account(params);
    }
}
