package com.fooock.app.shodand.repository.database.table;

import android.provider.BaseColumns;

/**
 *
 */
public class ProtocolTable {

    private ProtocolTable() {

    }

    public static class Protocol implements BaseColumns {
        public static final String TABLE_NAME = "protocols";
        public static final String COLUMN_NAME_PROTOCOL_DESC = "description";
        public static final String COLUMN_NAME_PROTOCOL_NAME = "name";
    }
}
