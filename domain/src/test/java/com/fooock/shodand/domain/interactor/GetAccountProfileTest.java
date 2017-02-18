package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.repository.AccountRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 */
public class GetAccountProfileTest {

    @Mock
    private MainThread mainThread;

    @Mock
    private ThreadExecutor threadExecutor;

    @Mock
    private AccountRepository accountRepository;

    private GetAccountProfile getAccountProfile;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getAccountProfile = new GetAccountProfile(accountRepository, mainThread, threadExecutor);
    }

    @Test
    public void testCalledRepository() throws Exception {
        getAccountProfile.result(null);
        verify(accountRepository).account();
        verifyNoMoreInteractions(accountRepository);
    }
}