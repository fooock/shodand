package com.fooock.shodand.domain.model.params;

/**
 *
 */
public class ListQueryParams {

    public final int page;
    public final String sort;
    public final String order;

    public ListQueryParams(int page) {
        this(page, "votes", "asc");
    }

    public ListQueryParams() {
        page = 1;
        sort = "votes";
        order = "asc";
    }

    public ListQueryParams(int page, String sort, String order) {
        this.page = page;
        this.sort = sort;
        this.order = order;
    }
}
