package com.fooock.app.shodand;

import android.app.Application;

import com.fooock.app.shodand.executor.DefaultMainThread;
import com.fooock.app.shodand.executor.DefaultThreadExecutor;
import com.fooock.app.shodand.repository.DefaultValidationRepository;
import com.fooock.shodand.data.ShodanApis;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.repository.ValidationRepository;

/**
 *
 */
public abstract class ShodandApplication extends Application {

    private ShodanApis shodanApis;

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
     * @return Validation repository
     */
    public ValidationRepository validationRepository() {
        return DefaultValidationRepository.getInstance();
    }

    /**
     * Initialize the Shodan APIs
     *
     * @param apiKey Shodan account API key
     */
    public void initializeApiWith(String apiKey) {
        shodanApis = new ShodanApis(apiKey);
    }
}
