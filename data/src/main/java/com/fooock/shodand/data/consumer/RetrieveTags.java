package com.fooock.shodand.data.consumer;

import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.domain.model.TagCount;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Retrieve tags from database if no connection
 */
public class RetrieveTags implements Function<Throwable, List<TagCount>> {

    private final ShodanDataSource shodanDataSource;

    public RetrieveTags(ShodanDataSource shodanDataSource) {
        this.shodanDataSource = shodanDataSource;
    }

    @Override
    public List<TagCount> apply(@NonNull Throwable throwable) throws Exception {
        if (throwable instanceof UnknownHostException) {
            return shodanDataSource.get();
        }
        throw new Exception(throwable);
    }
}
