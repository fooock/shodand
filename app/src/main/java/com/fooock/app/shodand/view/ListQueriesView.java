package com.fooock.app.shodand.view;

import com.fooock.shodand.domain.model.ListQuery;
import com.fooock.shodand.domain.model.Tag;

import java.util.List;

/**
 *
 */
public interface ListQueriesView extends BaseView {

    interface ExecuteQueryListener {
        void onExecuteQuery(ListQuery query);
    }

    interface SearchTagListener {
        void onSearchTag(Tag tag);
    }

    void addTagsToList(List<ListQuery> listQueries);

    void showLoading();

    void hideLoading();

    void showUnexpectedError();

    void showQueries(List<ListQuery> queries);
}
