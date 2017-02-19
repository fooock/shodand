package com.fooock.shodand.data.consumer;

import com.fooock.shodand.data.AccountDataSource;
import com.fooock.shodand.domain.model.Account;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 *
 */
public class SaveAccount implements Consumer<Account> {

    private final AccountDataSource accountDataSource;

    public SaveAccount(AccountDataSource accountDataSource) {
        this.accountDataSource = accountDataSource;
    }

    @Override
    public void accept(@NonNull Account account) throws Exception {
        accountDataSource.save(account);
    }
}
