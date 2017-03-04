package com.fooock.app.shodand.view;

import android.support.annotation.NonNull;

import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

/**
 *
 */
public interface ExploreView extends BaseView {

    /**
     * Click listener for the {@link com.fooock.app.shodand.view.adapter.QueriesAdapter}
     */
    interface QueryListener {
        void onQueryListSelected();

        void onQuerySearchSelected();
    }

    /**
     * Click listener for the {@link com.fooock.app.shodand.view.adapter.PopularTagAdapter}
     */
    interface TagListener {
        void onTagSelected(@NonNull TagCount tag);
    }

    /**
     * Click listener for the {@link com.fooock.app.shodand.view.adapter.ServicesAdapter}
     */
    interface ServiceListener {
        void onSearchByIp();

        void onSearchSummaryInfo();

        void onSearchServices();
    }

    /**
     * Click listener for protocols section in explore fragment
     */
    interface ProtocolListener {
        void onProtocolSelected();
    }

    /**
     * Click listener for the dns section in explore fragment
     */
    interface DnsListener {
        void onResolveDnsSelected();

        void onReverseDnsSelected();
    }

    void showLoading();

    void hideLoading();

    void showPopularTags(List<TagCount> tags);

    void showUnexpectedError();
}
