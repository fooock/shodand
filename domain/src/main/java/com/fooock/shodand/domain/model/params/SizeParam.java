package com.fooock.shodand.domain.model.params;

/**
 *
 */
public class SizeParam {

    private static final int DEFAULT_SIZE = 10;

    private final int size;

    public SizeParam(int size) {
        this.size = size;
    }

    public SizeParam() {
        size = DEFAULT_SIZE;
    }

    public int getSize() {
        return size;
    }
}
