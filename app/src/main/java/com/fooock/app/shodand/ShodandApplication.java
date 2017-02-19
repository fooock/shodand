package com.fooock.app.shodand;

import android.app.Application;

import com.fooock.app.shodand.executor.DefaultMainThread;
import com.fooock.app.shodand.executor.DefaultThreadExecutor;
import com.fooock.app.shodand.repository.DefaultAccountRepository;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.repository.AccountRepository;

/**
 *
 */
public abstract class ShodandApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initialize();
    }

    /**
     * Custom initialization depending on the build type
     */
    abstract void initialize();

    /**
     * @return Main thread
     */
    public MainThread mainThread() {
        return DefaultMainThread.getInstance();
    }

    /**
     * @return Thread executor
     */
    public ThreadExecutor threadExecutor() {
        return DefaultThreadExecutor.getInstance();
    }

    /**
     * @return Account repository
     */
    public AccountRepository accountRepository() {
        return DefaultAccountRepository.getInstance();
    }
}
