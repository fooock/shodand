package com.fooock.app.shodand.repository.database.table;

import android.provider.BaseColumns;

/**
 *
 */
public class TagTable {

    private TagTable() {

    }

    public static class PopularTag implements BaseColumns {
        public static final String TABLE_NAME = "popular_tags";
        public static final String COLUMN_NAME_COUNT = "count";
        public static final String COLUMN_NAME_TAG_NAME = "name";
    }
}
