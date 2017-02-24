package com.fooock.app.shodand.model;

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
    public List<TagCount> data() {
        return tags;
    }
}
