package com.fooock.shodand.data.datasource;

import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

/**
 *
 */
public interface ShodanDataSource {

    void save(List<TagCount> tags);

    List<TagCount> get();
}
