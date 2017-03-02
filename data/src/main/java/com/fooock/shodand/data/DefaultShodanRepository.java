package com.fooock.shodand.data;

import com.fooock.shodan.ShodanRestApi;
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
    private DefaultShodanRepository(ShodanRestApi restApi, ShodanDataSource dataSource) {
        this.shodanDataRepository = new ShodanDataRepository(restApi, dataSource);
    }

    public static synchronized ShodanRepository getInstance(ShodanRestApi restApi,
                                                            ShodanDataSource dataSource) {
        if (shodanRepository == null) {
            shodanRepository = new DefaultShodanRepository(restApi, dataSource);
        }
        return shodanRepository;
    }

    @Override
    public Observable<List<TagCount>> tags(SizeParam param) {
        return shodanDataRepository.tags(param);
    }
}
