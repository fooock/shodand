package com.fooock.app.shodand.presenter;

import com.fooock.app.shodand.RxBus;
import com.fooock.app.shodand.view.MainView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 *
 */
public class ShodandPresenter extends BasePresenter<MainView> {

    private final RxBus eventBus;

    public ShodandPresenter(RxBus rxBus) {
        eventBus = rxBus;
        subscribe();
    }

    /**
     * Subscribe this presenter to events in which it is interested
     */
    private void subscribe() {
        eventBus.events().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                switch (integer) {
                    case RxBus.EVENT_CHANGE_DRAWER_TO_BACK:
                        if (isAttached()) {
                            customView.showBackOnDrawer();
                        }
                        break;
                    case RxBus.EVENT_CHANGE_DRAWER_TO_HOME:
                        if (isAttached()) {
                            customView.showHomeInDrawer();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    void release() {

    }

    /**
     * Notify other components that the user has pressed the home button of the toolbar
     */
    public void clickOnNavigationItem() {
        eventBus.pushEvent(RxBus.EVENT_PRESSED_GO_HOME);
    }
}
