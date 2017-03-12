package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.model.ListQuery;
import com.fooock.shodand.domain.model.params.ListQueryParams;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public class GetListQueries extends BaseInteractor<List<ListQuery>, ListQueryParams> {

    private final ShodanRepository shodanRepository;

    public GetListQueries(ShodanRepository repository, MainThread mainThread,
                          ThreadExecutor threadExecutor) {
        super(mainThread, threadExecutor);
        shodanRepository = repository;
    }

    @Override
    protected Observable<List<ListQuery>> result(ListQueryParams params) {
        return shodanRepository.listQueries(params);
    }
}
