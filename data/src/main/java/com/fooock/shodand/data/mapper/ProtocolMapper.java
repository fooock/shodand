package com.fooock.shodand.data.mapper;

import com.fooock.shodan.model.protocol.Protocol;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 *
 */
public class ProtocolMapper implements Function<List<Protocol>, List<com.fooock.shodand.domain.model.Protocol>> {

    @Override
    public List<com.fooock.shodand.domain.model.Protocol> apply(@NonNull List<Protocol> protocols)
            throws Exception {
        final List<com.fooock.shodand.domain.model.Protocol> list = new ArrayList<>(protocols.size());
        for (Protocol protocol : protocols) {
            list.add(new com.fooock.shodand.domain.model.Protocol(
                    protocol.getName(), protocol.getDescription()));
        }
        return list;
    }
}
