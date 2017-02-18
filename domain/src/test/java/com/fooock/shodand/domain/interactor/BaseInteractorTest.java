package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.FakeDisposableObserver;
import com.fooock.shodand.domain.FakeInteractorTestClass;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

/**
 *
 */
public class BaseInteractorTest {

    private FakeInteractorTestClass interactorTestClass;
    private FakeDisposableObserver<Object> disposableObserver;

    @Mock
    private MainThread mainThread;

    @Mock
    private ThreadExecutor threadExecutor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        interactorTestClass = new FakeInteractorTestClass(mainThread, threadExecutor);
        disposableObserver = new FakeDisposableObserver<>();
        given(mainThread.scheduler()).willReturn(new TestScheduler());
    }

    @Test
    public void testSuccessResult() throws Exception {
        interactorTestClass.execute(disposableObserver, null);
        assertTrue(disposableObserver.getCounter() == 0);
    }

    @Test
    public void testDispose() throws Exception {
        interactorTestClass.execute(disposableObserver, null);
        interactorTestClass.close();
        assertTrue(disposableObserver.isDisposed());
    }
}
