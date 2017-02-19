package com.fooock.app.shodand.repository.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fooock.app.shodand.repository.database.DatabaseHelper;
import com.fooock.app.shodand.repository.database.table.AccountTable;
import com.fooock.shodand.data.AccountDataSource;
import com.fooock.shodand.domain.model.Account;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 *
 */
public class AccountDatabaseDataSource implements AccountDataSource {

    private static AccountDataSource accountDataSource;

    private final DatabaseHelper databaseHelper;

    /**
     * Create this object. Force singleton
     *
     * @param databaseHelper database helper
     */
    private AccountDatabaseDataSource(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Get this class instance
     *
     * @param helper database helper
     * @return this object instance
     */
    public static synchronized AccountDataSource getInstance(DatabaseHelper helper) {
        if (accountDataSource == null) {
            accountDataSource = new AccountDatabaseDataSource(helper);
        }
        return accountDataSource;
    }

    @Override
    public void save(Account entity) {
        Timber.d("Save -> %s", entity);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AccountTable.Account.COLUMN_NAME_CREDITS, entity.getCredits());
        values.put(AccountTable.Account.COLUMN_NAME_CREATED, entity.getCreated());
        values.put(AccountTable.Account.COLUMN_NAME_MEMBER, entity.isMember() ? 1 : 0);
        values.put(AccountTable.Account.COLUMN_NAME_DISPLAY_NAME, entity.getDisplayName());
        database.insert(AccountTable.Account.TABLE_NAME, null, values);
    }

    @Override
    public Observable<Account> get() {
        Timber.d("Get account from database");
        return databaseHelper.createObservable(new Callable<Account>() {
            @Override
            public Account call() throws Exception {
                SQLiteDatabase database = databaseHelper.getReadableDatabase();
                Cursor cursor = database.query(AccountTable.Account.TABLE_NAME,
                        null, null, null, null, null, null);
                String displayName = cursor.getString(
                        cursor.getColumnIndex(AccountTable.Account.COLUMN_NAME_DISPLAY_NAME));
                String created = cursor.getString(
                        cursor.getColumnIndex(AccountTable.Account.COLUMN_NAME_CREATED));
                int credits = cursor.getInt(
                        cursor.getColumnIndex(AccountTable.Account.COLUMN_NAME_CREDITS));
                int member = cursor.getInt(
                        cursor.getColumnIndex(AccountTable.Account.COLUMN_NAME_MEMBER));
                Account account = new Account(credits, member == 1, displayName, created);
                cursor.close();
                return account;
            }
        });
    }
}
