package com.fooock.shodand.data.mapper;

import com.fooock.shodan.model.user.Account;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class AccountMapperTest {

    @Test
    public void testAccountMapper() throws Exception {
        Account account = mock(Account.class);
        when(account.getDisplayName()).thenReturn("hello");
        when(account.getCredits()).thenReturn(20);
        when(account.isMember()).thenReturn(true);
        String date = new Date(System.currentTimeMillis()).toString();
        when(account.getCreated()).thenReturn(date);

        AccountMapper mapper = new AccountMapper();
        com.fooock.shodand.domain.model.Account transformedAccount = mapper.apply(account);

        assertEquals("hello", transformedAccount.getDisplayName());
        assertEquals(20, transformedAccount.getCredits());
        assertTrue(transformedAccount.isMember());
        assertEquals(date, transformedAccount.getCreated());
    }
}