package com.fooock.app.shodand.repository;

import com.fooock.shodand.data.AccountDataSource;
import com.fooock.shodand.data.ValidationDataRepository;
import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.ValidationRepository;

import io.reactivex.Observable;

/**
 *
 */
public class DefaultValidationRepository implements ValidationRepository {

    private static ValidationRepository validationRepository;

    private final ValidationDataRepository dataRepository;

    /**
     * Create the default validation repository
     */
    private DefaultValidationRepository(AccountDataSource accountDataSource) {
        dataRepository = new ValidationDataRepository(accountDataSource);
    }

    public static synchronized ValidationRepository getInstance(AccountDataSource accountDataSource) {
        if (validationRepository == null) {
            validationRepository = new DefaultValidationRepository(accountDataSource);
        }
        return validationRepository;
    }

    @Override
    public Observable<Account> firstInit(ApiKey apiKey) {
        return dataRepository.firstInit(apiKey);
    }
}
