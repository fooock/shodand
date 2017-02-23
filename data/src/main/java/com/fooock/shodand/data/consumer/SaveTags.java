package com.fooock.shodand.data.consumer;

import com.fooock.shodand.data.datasource.ShodanDataSource;
import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 *
 */
public class SaveTags implements Consumer<List<TagCount>> {

    private final ShodanDataSource shodanDataSource;

    public SaveTags(ShodanDataSource shodanDataSource) {
        this.shodanDataSource = shodanDataSource;
    }

    @Override
    public void accept(@NonNull List<TagCount> tagCounts) throws Exception {
        shodanDataSource.save(tagCounts);
    }
}
