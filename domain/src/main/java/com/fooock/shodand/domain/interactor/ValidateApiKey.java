package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.ValidationRepository;

import io.reactivex.Observable;

/**
 * Get the {@link Account} profile from the {@link ValidationRepository}
 */
public class ValidateApiKey extends BaseInteractor<Account, ApiKey> {

    private final ValidationRepository validationRepository;

    public ValidateApiKey(ValidationRepository repository, MainThread mainThread,
                          ThreadExecutor threadExecutor) {
        super(mainThread, threadExecutor);
        validationRepository = repository;
    }

    @Override
    protected Observable<Account> result(ApiKey params) {
        return validationRepository.firstInit(params);
    }
}
