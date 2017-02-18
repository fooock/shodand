package com.fooock.shodand.domain;

import java.util.List;

/**
 * Base class to create mappers. From one type to other
 */
public interface Mapper<F, T> {

    T map(F from);

    List<T> map(List<F> from);
}
