package com.fooock.shodand.data.mapper;

import com.fooock.shodan.model.Property;
import com.fooock.shodan.model.tag.TagReport;
import com.fooock.shodand.domain.model.TagCount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 *
 */
public class TagCountMapper implements Function<TagReport, List<TagCount>> {

    @Override
    public List<TagCount> apply(@NonNull TagReport tagReport) throws Exception {
        final List<Property> properties = tagReport.getTags();
        if (properties == null || properties.isEmpty()) {
            return Collections.emptyList();
        }
        final List<TagCount> tags = new ArrayList<>(properties.size());
        for (Property property : properties) {
            tags.add(new TagCount(property.getCount(), property.getValue()));
        }
        return tags;
    }
}
