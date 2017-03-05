package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.model.Protocol;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public class GetProtocols extends BaseInteractor<List<Protocol>, Void> {

    private final ShodanRepository shodanRepository;

    public GetProtocols(ShodanRepository shodanRepository, MainThread mainThread,
                        ThreadExecutor threadExecutor) {
        super(mainThread, threadExecutor);
        this.shodanRepository = shodanRepository;
    }

    @Override
    protected Observable<List<Protocol>> result(Void params) {
        return shodanRepository.protocols();
    }
}
