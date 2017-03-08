package com.fooock.app.shodand;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Reactive bus implementation
 */
public class RxBus {

    public static final int EVENT_CHANGE_DRAWER_TO_BACK = 0;
    public static final int EVENT_CHANGE_DRAWER_TO_HOME = 1;
    public static final int EVENT_PRESSED_GO_HOME = 2;

    private static final RxBus INSTANCE = new RxBus();

    private final PublishSubject<Integer> subject = PublishSubject.create();

    private RxBus() {

    }

    static synchronized RxBus getInstance() {
        return INSTANCE;
    }

    public void pushEvent(int event) {
        subject.onNext(event);
    }

    public Observable<Integer> events() {
        return subject;
    }
}
