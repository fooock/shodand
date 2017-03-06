package com.fooock.app.shodand.repository.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fooock.app.shodand.repository.database.DatabaseHelper;
import com.fooock.app.shodand.repository.database.table.ProtocolTable;
import com.fooock.app.shodand.repository.database.table.TagTable;
import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.domain.model.Protocol;
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
    public void saveTags(List<TagCount> tags) {
        deleteAllTags();

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
    public Observable<List<TagCount>> getTags() {
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
    public void deleteAllTags() {
        Timber.d("Delete tags...");
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TagTable.PopularTag.TABLE_NAME, null, null);
    }

    @Override
    public Observable<List<Protocol>> getProtocols() {
        Timber.d("Getting protocols from database");
        return databaseHelper.createObservable(new Callable<List<Protocol>>() {
            @Override
            public List<Protocol> call() throws Exception {
                final List<Protocol> protocols = new ArrayList<>();
                SQLiteDatabase database = databaseHelper.getReadableDatabase();
                Cursor cursor = database.query(ProtocolTable.Protocol.TABLE_NAME,
                        null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(
                                cursor.getColumnIndex(ProtocolTable.Protocol.COLUMN_NAME_PROTOCOL_NAME));
                        String desc = cursor.getString(
                                cursor.getColumnIndex(ProtocolTable.Protocol.COLUMN_NAME_PROTOCOL_DESC));
                        final Protocol protocol = new Protocol(name, desc);
                        protocols.add(protocol);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                Timber.d("Found %s protocols in database", protocols.size());
                return protocols;
            }
        });
    }

    @Override
    public void saveProtocols(List<Protocol> protocols) {
        deleteAllProtocols();
        Timber.d("Save %s protocols in database", protocols.size());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        for (Protocol protocol : protocols) {
            ContentValues values = new ContentValues();
            values.put(ProtocolTable.Protocol.COLUMN_NAME_PROTOCOL_NAME, protocol.name);
            values.put(ProtocolTable.Protocol.COLUMN_NAME_PROTOCOL_DESC, protocol.description);
            database.insert(ProtocolTable.Protocol.TABLE_NAME, null, values);
        }
    }

    @Override
    public void deleteAllProtocols() {
        Timber.d("Delete all protocols from database");
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(ProtocolTable.Protocol.TABLE_NAME, null, null);
    }
}
