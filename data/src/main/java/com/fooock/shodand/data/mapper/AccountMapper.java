package com.fooock.shodand.data.mapper;

import com.fooock.shodand.domain.model.Account;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 *
 */
public class AccountMapper implements Function<com.fooock.shodan.model.user.Account, Account> {

    @Override
    public Account apply(@NonNull com.fooock.shodan.model.user.Account account) throws Exception {
        return new Account(account.getCredits(), account.isMember(),
                account.getDisplayName(), account.getCreated());
    }
}
