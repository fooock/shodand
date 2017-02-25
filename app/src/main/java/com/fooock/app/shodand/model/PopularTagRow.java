package com.fooock.app.shodand.model;

import com.fooock.app.shodand.R;
import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

/**
 *
 */
public class PopularTagRow implements Row<List<TagCount>> {

    private final List<TagCount> tags;

    public PopularTagRow(List<TagCount> tags) {
        this.tags = tags;
    }

    @Override
    public int getName() {
        return R.string.title_popular_tags;
    }

    @Override
    public int getDescription() {
        return R.string.description_popular_tags;
    }

    @Override
    public int getIcon() {
        throw new RuntimeException();
    }

    @Override
    public List<TagCount> data() {
        return tags;
    }
}
