package com.fooock.shodand.domain.repository;

import com.fooock.shodand.domain.model.ListQuery;
import com.fooock.shodand.domain.model.Protocol;
import com.fooock.shodand.domain.model.TagCount;
import com.fooock.shodand.domain.model.params.ListQueryParams;
import com.fooock.shodand.domain.model.params.SizeParam;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public interface ShodanRepository {

    Observable<List<TagCount>> tags(SizeParam param);

    Observable<List<Protocol>> protocols();

    Observable<List<ListQuery>> listQueries(ListQueryParams params);
}
