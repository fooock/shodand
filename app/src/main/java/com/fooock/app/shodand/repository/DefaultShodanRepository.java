package com.fooock.app.shodand.repository;

import com.fooock.shodan.ShodanRestApi;
import com.fooock.shodand.data.ShodanDataRepository;
import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.domain.model.TagCount;
import com.fooock.shodand.domain.model.params.SizeParam;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public class DefaultShodanRepository implements ShodanRepository {

    private static ShodanRepository shodanRepository;

    private final ShodanDataRepository shodanDataRepository;

    /**
     * Create the default shodan data repository
     */
    public DefaultShodanRepository(ShodanDataRepository shodanDataRepository) {
        this.shodanDataRepository = shodanDataRepository;
    }

    public static synchronized ShodanRepository getInstance(ShodanRestApi restApi,
                                                            ShodanDataSource dataSource) {
        if (shodanRepository == null) {
            shodanRepository = new ShodanDataRepository(restApi, dataSource);
        }
        return shodanRepository;
    }

    @Override
    public Observable<List<TagCount>> tags(SizeParam param) {
        return shodanDataRepository.tags(param);
    }
}
