package com.fooock.shodand.data.mapper;

import com.fooock.shodan.model.query.Query;
import com.fooock.shodan.model.query.QueryReport;
import com.fooock.shodand.domain.model.ListQuery;
import com.fooock.shodand.domain.model.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 *
 */
public class ListQueryMapper implements Function<QueryReport, List<ListQuery>> {

    @Override
    public List<ListQuery> apply(@NonNull QueryReport queryReport) throws Exception {
        final List<Query> queries = queryReport.getQueries();
        final List<ListQuery> listQueries = new ArrayList<>(queries.size());
        for (Query query : queries) {
            listQueries.add(parseQuery(query));
        }
        return listQueries;
    }

    private ListQuery parseQuery(Query query) {
        final String[] tags = query.getTags();
        if (tags == null || tags.length == 0) {
            return new ListQuery(query.getVotes(), query.getDescription(), query.getTitle(),
                    query.getTimestamp(), query.getQuery(), Collections.<Tag>emptyList());
        }
        final List<Tag> listTag = new ArrayList<>(tags.length);
        for (String tag : tags) {
            if (tag.trim().isEmpty()) {
                continue;
            }
            listTag.add(new Tag(tag));
        }
        return new ListQuery(query.getVotes(), query.getDescription(), query.getTitle(),
                query.getTimestamp(), query.getQuery(), listTag);
    }
}
