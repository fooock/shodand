package com.fooock.shodand.data.datasource;

import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public interface ShodanDataSource {

    void save(List<TagCount> tags);

    Observable<List<TagCount>> get();

    void deleteAll();
}
