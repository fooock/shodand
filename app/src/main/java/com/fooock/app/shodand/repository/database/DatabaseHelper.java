package com.fooock.app.shodand.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fooock.app.shodand.repository.database.table.AccountTable;
import com.fooock.app.shodand.repository.database.table.ProtocolTable;
import com.fooock.app.shodand.repository.database.table.TagTable;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

/**
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shodand.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE "
            + AccountTable.Account.TABLE_NAME + " ("
            + AccountTable.Account._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
            + AccountTable.Account.COLUMN_NAME_CREATED + TEXT_TYPE + COMMA_SEP
            + AccountTable.Account.COLUMN_NAME_CREDITS + INT_TYPE + COMMA_SEP
            + AccountTable.Account.COLUMN_NAME_DISPLAY_NAME + TEXT_TYPE + COMMA_SEP
            + AccountTable.Account.COLUMN_NAME_MEMBER + INT_TYPE + ")";

    private static final String SQL_DELETE_ACCOUNT_TABLE = "DROP TABLE IF EXISTS "
            + AccountTable.Account.TABLE_NAME;

    private static final String SQL_CREATE_POPULAR_TAGS_TABLE = "CREATE TABLE "
            + TagTable.PopularTag.TABLE_NAME + " ("
            + TagTable.PopularTag._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
            + TagTable.PopularTag.COLUMN_NAME_COUNT + INT_TYPE + COMMA_SEP
            + TagTable.PopularTag.COLUMN_NAME_TAG_NAME + TEXT_TYPE + ")";

    private static final String SQL_DELETE_POPULAR_TAGS = "DROP TABLE IF EXISTS "
            + TagTable.PopularTag.TABLE_NAME;

    private static final String SQL_CREATE_PROTOCOL_TABLE = "CREATE TABLE "
            + ProtocolTable.Protocol.TABLE_NAME + " ("
            + ProtocolTable.Protocol._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
            + ProtocolTable.Protocol.COLUMN_NAME_PROTOCOL_NAME + TEXT_TYPE + COMMA_SEP
            + ProtocolTable.Protocol.COLUMN_NAME_PROTOCOL_DESC + TEXT_TYPE + ")";

    private static final String SQL_DELETE_PROTOCOLS = "DROP TABLE IF EXISTS "
            + ProtocolTable.Protocol.TABLE_NAME;

    /**
     * Create this object
     *
     * @param context application context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.d("Creating sqlite database...");
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_POPULAR_TAGS_TABLE);
        db.execSQL(SQL_CREATE_PROTOCOL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.d("Upgrade sqlite database from version %s to %s", oldVersion, newVersion);
        db.execSQL(SQL_DELETE_ACCOUNT_TABLE);
        db.execSQL(SQL_DELETE_POPULAR_TAGS);
        db.execSQL(SQL_DELETE_PROTOCOLS);
        onCreate(db);
    }

    /**
     * Create an {@link Observable} from a given callable
     *
     * @param callable Callable to execute
     * @param <T>      type of the result
     * @return Observable
     */
    public <T> Observable<T> createObservable(final Callable<T> callable) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    final T result = callable.call();
                    e.onNext(result);
                    e.onComplete();

                } catch (Exception error) {
                    e.onError(error);
                }
            }
        });
    }
}
