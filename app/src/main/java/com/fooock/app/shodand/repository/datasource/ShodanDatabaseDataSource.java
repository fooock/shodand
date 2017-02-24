package com.fooock.app.shodand.repository.datasource;

import com.fooock.app.shodand.repository.database.DatabaseHelper;
import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.domain.model.TagCount;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 *
 */
public class ShodanDatabaseDataSource implements ShodanDataSource {

    private static ShodanDataSource shodanDataSource;

    private final DatabaseHelper databaseHelper;

    private ShodanDatabaseDataSource(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Get this class instance
     *
     * @param helper database helper
     * @return this object instance
     */
    public static synchronized ShodanDataSource getInstance(DatabaseHelper helper) {
        if (shodanDataSource == null) {
            shodanDataSource = new ShodanDatabaseDataSource(helper);
        }
        return shodanDataSource;
    }

    @Override
    public void save(List<TagCount> tags) {
        Timber.d("Save %s tags in database", tags.size());
    }

    @Override
    public List<TagCount> get() {
        return Collections.emptyList();
    }
}
