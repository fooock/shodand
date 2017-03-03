package com.fooock.app.shodand.repository.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fooock.app.shodand.repository.database.DatabaseHelper;
import com.fooock.app.shodand.repository.database.table.TagTable;
import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.domain.model.TagCount;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
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
        deleteAll();

        Timber.d("Save %s tags in database", tags.size());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        for (TagCount tag : tags) {
            ContentValues values = new ContentValues();
            values.put(TagTable.PopularTag.COLUMN_NAME_COUNT, tag.getCount());
            values.put(TagTable.PopularTag.COLUMN_NAME_TAG_NAME, tag.getName());
            database.insert(TagTable.PopularTag.TABLE_NAME, null, values);
        }
    }

    @Override
    public Observable<List<TagCount>> get() {
        Timber.d("Getting popular tags from database");
        return databaseHelper.createObservable(new Callable<List<TagCount>>() {
            @Override
            public List<TagCount> call() throws Exception {
                final List<TagCount> tags = new ArrayList<>();
                SQLiteDatabase database = databaseHelper.getReadableDatabase();
                Cursor cursor = database.query(TagTable.PopularTag.TABLE_NAME,
                        null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(
                                cursor.getColumnIndex(TagTable.PopularTag.COLUMN_NAME_TAG_NAME));
                        int count = cursor.getInt(
                                cursor.getColumnIndex(TagTable.PopularTag.COLUMN_NAME_COUNT));
                        final TagCount tag = new TagCount(count, name);
                        tags.add(tag);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                Timber.d("Found %s popular tags in database", tags.size());
                return tags;
            }
        });
    }

    @Override
    public void deleteAll() {
        Timber.d("Delete tags...");
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TagTable.PopularTag.TABLE_NAME, null, null);
    }
}
