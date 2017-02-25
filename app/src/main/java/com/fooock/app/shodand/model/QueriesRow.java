package com.fooock.app.shodand.model;

import com.fooock.app.shodand.R;

import java.util.List;

/**
 *
 */
public class QueriesRow implements Row<List<QueryType>> {

    private final List<QueryType> queryFilter;

    public QueriesRow(List<QueryType> queryFilter) {
        this.queryFilter = queryFilter;
    }

    @Override
    public int getName() {
        return R.string.title_recent_shared;
    }

    @Override
    public int getDescription() {
        return R.string.description_recent_shared;
    }

    @Override
    public int getIcon() {
        return 0;
    }

    @Override
    public List<QueryType> data() {
        return queryFilter;
    }
}
