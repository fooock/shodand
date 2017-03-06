package com.fooock.shodand.data;

import com.fooock.shodan.ShodanRestApi;
import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.data.mapper.ProtocolMapper;
import com.fooock.shodand.data.mapper.TagCountMapper;
import com.fooock.shodand.domain.model.Protocol;
import com.fooock.shodand.domain.model.TagCount;
import com.fooock.shodand.domain.model.params.SizeParam;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 *
 */
final class ShodanDataRepository implements ShodanRepository {

    private final ShodanRestApi shodanRestApi;
    private final ShodanDataSource dataSource;

    ShodanDataRepository(ShodanRestApi shodanRestApi, ShodanDataSource dataSource) {
        this.shodanRestApi = shodanRestApi;
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<TagCount>> tags(SizeParam param) {
        final Observable<List<TagCount>> dbObservable = dataSource.getTags()
                .filter(new Predicate<List<TagCount>>() {
                    @Override
                    public boolean test(@NonNull List<TagCount> tagCounts) throws Exception {
                        return tagCounts.size() > 0;
                    }
                });
        final Observable<List<TagCount>> apiObservable = shodanRestApi.tags(param.getSize())
                .map(new TagCountMapper()).doOnNext(new Consumer<List<TagCount>>() {
                    @Override
                    public void accept(@NonNull final List<TagCount> tagCounts) throws Exception {
                        dataSource.saveTags(tagCounts);
                    }
                });
        return Observable.concat(dbObservable, apiObservable)
                .firstElement().toObservable();
    }

    @Override
    public Observable<List<Protocol>> protocols() {
        final Observable<List<Protocol>> dbObservable = dataSource.getProtocols()
                .filter(new Predicate<List<Protocol>>() {
                    @Override
                    public boolean test(@NonNull List<Protocol> protocols) throws Exception {
                        return protocols.size() > 0;
                    }
                });
        final Observable<List<Protocol>> apiObservable = shodanRestApi.protocols()
                .map(new ProtocolMapper()).doOnNext(new Consumer<List<Protocol>>() {
                    @Override
                    public void accept(@NonNull List<Protocol> protocols) throws Exception {
                        dataSource.saveProtocols(protocols);
                    }
                });
        return Observable.concat(dbObservable, apiObservable).firstElement().toObservable();
    }
}
