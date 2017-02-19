package com.fooock.shodand.data;

import io.reactivex.Observable;

/**
 *
 */
interface GenericDataSource<T> {

    void save(T entity);

    Observable<T> get();
}
