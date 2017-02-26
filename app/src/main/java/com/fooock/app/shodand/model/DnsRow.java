package com.fooock.app.shodand.model;

import com.fooock.app.shodand.R;

import java.util.List;

/**
 *
 */
public class DnsRow implements Row<List<DnsType>> {

    private final List<DnsType> dnsTypes;

    public DnsRow(List<DnsType> dnsTypes) {
        this.dnsTypes = dnsTypes;
    }

    @Override
    public int getName() {
        return R.string.title_dns_name;
    }

    @Override
    public int getDescription() {
        return R.string.description_dns;
    }

    @Override
    public int getIcon() {
        return 0;
    }

    @Override
    public List<DnsType> data() {
        return dnsTypes;
    }
}
