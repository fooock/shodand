package com.fooock.app.shodand.model;

import com.fooock.app.shodand.R;

/**
 *
 */
public class ProtocolRow implements Row<Object> {

    @Override
    public int getName() {
        return R.string.title_protocols;
    }

    @Override
    public int getDescription() {
        return R.string.description_protocols;
    }

    @Override
    public int getIcon() {
        return 0;
    }

    @Override
    public Object data() {
        return null;
    }
}
