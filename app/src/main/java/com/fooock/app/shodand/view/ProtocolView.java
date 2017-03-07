package com.fooock.app.shodand.view;

import com.fooock.shodand.domain.model.Protocol;

import java.util.List;

/**
 *
 */
public interface ProtocolView extends BaseView {

    interface ProtocolClickListener {
        void onProtocolSelected(Protocol protocol);
    }

    void showDefaultTitle();

    void showCollapsedBottomSheet(Protocol protocol);

    void showExpandedBottomSheet(Protocol protocol);

    void onHideBottomSheet();

    void showLoading();

    void hideLoading();

    void showProtocols(List<Protocol> protocols);

    void showUnexpectedError();
}
