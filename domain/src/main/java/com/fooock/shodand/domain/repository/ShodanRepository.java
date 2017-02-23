package com.fooock.shodand.domain.repository;

import com.fooock.shodand.domain.model.TagCount;
import com.fooock.shodand.domain.model.params.SizeParam;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public interface ShodanRepository {

    Observable<List<TagCount>> tags(SizeParam param);
}
