package com.fooock.app.shodand.view;

import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

/**
 *
 */
public interface ExploreView extends BaseView {

    void showLoading();

    void hideLoading();

    void showPopularTags(List<TagCount> tags);

    void showErrorMessage(String message);
}
