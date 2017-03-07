package com.fooock.app.shodand.presenter;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;

import com.fooock.app.shodand.view.ProtocolView;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.interactor.GetProtocols;
import com.fooock.shodand.domain.model.Protocol;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 *
 */
public class ProtocolPresenter extends BasePresenter<ProtocolView> {

    private final GetProtocols getProtocols;

    private Protocol selectedProtocol;

    public ProtocolPresenter(ShodanRepository repository, MainThread mainThread,
                             ThreadExecutor threadExecutor) {
        getProtocols = new GetProtocols(repository, mainThread, threadExecutor);
    }

    /**
     * Update the view with all protocols. If an error occurs then an error message
     * is displayed in screen
     */
    public void update() {
        Timber.d("Prepared to get protocols...");
        customView.showLoading();
        getProtocols.execute(new DisposableObserver<List<Protocol>>() {
            @Override
            public void onNext(List<Protocol> protocols) {
                Timber.d("Received %s protocols", protocols.size());
                if (isAttached()) {
                    customView.showProtocols(protocols);
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                if (isAttached()) {
                    customView.hideLoading();
                    customView.showUnexpectedError();
                }
            }

            @Override
            public void onComplete() {
                Timber.d("Get protocols completed");
                if (isAttached()) {
                    customView.hideLoading();
                }
            }
        }, null);
    }

    @Override
    void release() {
        getProtocols.close();
    }

    /**
     * Search protocol info to show it in the bottom sheet
     *
     * @param protocol selected protocol
     */
    public void searchProtocolInfo(Protocol protocol) {
        selectedProtocol = protocol;
    }

    /**
     * Handle state changes in the bottom sheet to change the state of the view
     *
     * @param newState current state
     */
    public void onBottomSheetStateChange(int newState) {
        if (selectedProtocol == null) {
            customView.showDefaultTitle();
            return;
        }
        switch (newState) {
            case BottomSheetBehavior.STATE_HIDDEN:
                customView.showDefaultTitle();
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
                customView.showCollapsedBottomSheet(selectedProtocol);
                break;
            case BottomSheetBehavior.STATE_EXPANDED:
                customView.showExpandedBottomSheet(selectedProtocol);
                break;
            default:
                break;
        }
    }

    /**
     * Handle the scroll in the recycler view to hide the bottom sheet if is showing. Only
     * when the scroll state is dragging this method rect
     *
     * @param bottomSheetBehaviorState Current state of the bottom sheet
     * @param scrollState              Current state of the scroll
     */
    public void onRecyclerViewStateChange(int bottomSheetBehaviorState, int scrollState) {
        if (bottomSheetBehaviorState == BottomSheetBehavior.STATE_HIDDEN) {
            return;
        }
        if (scrollState != RecyclerView.SCROLL_STATE_DRAGGING) {
            return;
        }
        if (bottomSheetBehaviorState == BottomSheetBehavior.STATE_COLLAPSED
                || bottomSheetBehaviorState == BottomSheetBehavior.STATE_EXPANDED) {
            selectedProtocol = null;
            customView.onHideBottomSheet();
        }
    }
}
