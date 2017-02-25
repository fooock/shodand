package com.fooock.app.shodand.model;

import com.fooock.app.shodand.R;

import java.util.List;

/**
 *
 */
public class ServicesRow implements Row<List<ServicesType>> {

    private final List<ServicesType> servicesTypes;

    public ServicesRow(List<ServicesType> servicesTypes) {
        this.servicesTypes = servicesTypes;
    }

    @Override
    public int getName() {
        return R.string.title_services;
    }

    @Override
    public int getDescription() {
        return R.string.description_services;
    }

    @Override
    public int getIcon() {
        return 0;
    }

    @Override
    public List<ServicesType> data() {
        return servicesTypes;
    }
}
