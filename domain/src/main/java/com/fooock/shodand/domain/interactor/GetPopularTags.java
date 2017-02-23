package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.model.TagCount;
import com.fooock.shodand.domain.model.params.SizeParam;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Get the list of popular tags
 */
public class GetPopularTags extends BaseInteractor<List<TagCount>, SizeParam> {

    private final ShodanRepository shodanRepository;

    public GetPopularTags(ShodanRepository shodanRepository, MainThread mainThread,
                          ThreadExecutor threadExecutor) {
        super(mainThread, threadExecutor);
        this.shodanRepository = shodanRepository;
    }

    @Override
    protected Observable<List<TagCount>> result(SizeParam params) {
        return shodanRepository.tags(params);
    }
}
