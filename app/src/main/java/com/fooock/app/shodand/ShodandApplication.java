package com.fooock.app.shodand;

import android.app.Application;

import com.fooock.app.shodand.executor.DefaultMainThread;
import com.fooock.app.shodand.executor.DefaultThreadExecutor;
import com.fooock.app.shodand.repository.database.DatabaseHelper;
import com.fooock.app.shodand.repository.datasource.AccountDatabaseDataSource;
import com.fooock.app.shodand.repository.datasource.ShodanDatabaseDataSource;
import com.fooock.shodand.data.DefaultShodanRepository;
import com.fooock.shodand.data.DefaultValidationRepository;
import com.fooock.shodand.data.ShodanApis;
import com.fooock.shodand.data.datasource.AccountDataSource;
import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.repository.ShodanRepository;
import com.fooock.shodand.domain.repository.ValidationRepository;

/**
 *
 */
public abstract class ShodandApplication extends Application {

    private Prefs prefs;
    private Navigator navigator;
    private ShodanApis shodanApis;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        navigator = new Navigator(this);
        prefs = new Prefs(this);

        // Create the database helper
        databaseHelper = new DatabaseHelper(this);

        initialize();
    }

    /**
     * Custom initialization depending on the build type
     */
    abstract void initialize();

    /**
     * @return Event bus
     */
    public RxBus eventBus() {
        return RxBus.getInstance();
    }

    /**
     * @return Navigator
     */
    public Navigator navigator() {
        return navigator;
    }

    /**
     * @return Application preferences
     */
    public Prefs preferences() {
        return prefs;
    }

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
        return DefaultValidationRepository.getInstance(accountDatabaseDataSource());
    }

    /**
     * @return account database data source
     */
    private AccountDataSource accountDatabaseDataSource() {
        return AccountDatabaseDataSource.getInstance(databaseHelper);
    }

    /**
     * @return Shodan repository
     */
    public ShodanRepository shodanRepository() {
        return DefaultShodanRepository.getInstance(
                shodanApis.getShodanRestApi(), shodanDatabaseDataSource());
    }

    /**
     * @return shodan database data source
     */
    private ShodanDataSource shodanDatabaseDataSource() {
        return ShodanDatabaseDataSource.getInstance(databaseHelper);
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
