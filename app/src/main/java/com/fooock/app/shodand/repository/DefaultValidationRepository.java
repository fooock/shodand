package com.fooock.app.shodand.repository;

import com.fooock.shodand.data.ValidationDataRepository;
import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.model.Account;
import com.fooock.shodand.domain.repository.ValidationRepository;

import io.reactivex.Observable;

/**
 *
 */
public class DefaultValidationRepository implements ValidationRepository {

    private static final ValidationRepository INSTANCE = new DefaultValidationRepository();

    private final ValidationDataRepository dataRepository;

    /**
     * Create the default validation repository
     */
    private DefaultValidationRepository() {
        dataRepository = new ValidationDataRepository();
    }

    public static ValidationRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<Account> firstInit(ApiKey apiKey) {
        return dataRepository.firstInit(apiKey);
    }
}
