package com.fooock.shodand.domain.model;

import java.util.List;

/**
 *
 */
public class ListQuery {

    public final int votes;

    public final String description;
    public final String title;
    public final String timestamp;
    public final String query;

    public final List<Tag> tags;

    public ListQuery(int votes, String description, String title, String timestamp, String query,
                     List<Tag> tags) {
        this.votes = votes;
        this.description = description;
        this.title = title;
        this.timestamp = timestamp;
        this.query = query;
        this.tags = tags;
    }
}
