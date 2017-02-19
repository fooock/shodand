package com.fooock.app.shodand.repository.database.table;

import android.provider.BaseColumns;

/**
 *
 */
public class AccountTable {

    private AccountTable() {

    }

    public static class Account implements BaseColumns {
        public static final String TABLE_NAME = "account";
        public static final String COLUMN_NAME_CREDITS = "credits";
        public static final String COLUMN_NAME_MEMBER = "member";
        public static final String COLUMN_NAME_DISPLAY_NAME = "display_name";
        public static final String COLUMN_NAME_CREATED = "created";
    }
}
