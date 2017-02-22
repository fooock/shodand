package com.fooock.app.shodand.presenter;

import com.fooock.app.shodand.view.IntroduceKeyView;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.repository.ValidationRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 */
public class IntroduceKeyPresenterTest {

    @Mock
    private ValidationRepository validationRepository;

    @Mock
    private MainThread mainThread;

    @Mock
    private ThreadExecutor threadExecutor;

    @Mock
    private IntroduceKeyView introduceKeyView;

    private IntroduceKeyPresenter introduceKeyPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        introduceKeyPresenter = new IntroduceKeyPresenter(
                validationRepository, mainThread, threadExecutor);
        introduceKeyPresenter.attachView(introduceKeyView);
    }

    @Test
    public void testEmptyApiKey() throws Exception {
        introduceKeyPresenter.validateApiKey("");
        verify(introduceKeyPresenter.customView, times(1)).showProgress();
        verify(introduceKeyPresenter.customView, times(1)).hideProgress();
        verify(introduceKeyPresenter.customView, times(1)).emptyApiKey();
        verifyNoMoreInteractions(introduceKeyPresenter.customView);
    }

    @After
    public void tearDown() throws Exception {
        introduceKeyPresenter.detachView();
    }
}