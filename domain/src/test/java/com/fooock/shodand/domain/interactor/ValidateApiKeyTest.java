package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.repository.ValidationRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 */
public class ValidateApiKeyTest {

    @Mock
    private MainThread mainThread;

    @Mock
    private ThreadExecutor threadExecutor;

    @Mock
    private ValidationRepository validationRepository;

    private ValidateApiKey validateApiKey;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        validateApiKey = new ValidateApiKey(validationRepository, mainThread, threadExecutor);
    }

    @Test
    public void testCalledRepository() throws Exception {
        ApiKey apiKey = new ApiKey("1234567890");
        validateApiKey.result(apiKey);
        verify(validationRepository).firstInit(apiKey);
        verifyNoMoreInteractions(validationRepository);
    }
}