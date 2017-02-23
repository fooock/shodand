package com.fooock.shodand.data;

import com.fooock.shodan.ShodanRestApi;
import com.fooock.shodand.data.consumer.RetrieveTags;
import com.fooock.shodand.data.consumer.SaveTags;
import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.data.mapper.TagCountMapper;
import com.fooock.shodand.domain.model.TagCount;
import com.fooock.shodand.domain.model.params.SizeParam;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public class ShodanDataRepository implements ShodanRepository {

    private final ShodanRestApi shodanRestApi;
    private final ShodanDataSource dataSource;

    public ShodanDataRepository(ShodanRestApi shodanRestApi, ShodanDataSource dataSource) {
        this.shodanRestApi = shodanRestApi;
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<TagCount>> tags(SizeParam param) {
        return shodanRestApi.tags(param.getSize())
                .map(new TagCountMapper())
                .doOnNext(new SaveTags(dataSource)).onErrorReturn(new RetrieveTags(dataSource));
    }
}
