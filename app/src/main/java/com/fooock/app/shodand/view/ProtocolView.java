package com.fooock.app.shodand.view;

import com.fooock.shodand.domain.model.Protocol;

import java.util.List;

/**
 *
 */
public interface ProtocolView extends BaseView {

    void showLoading();

    void hideLoading();

    void showProtocols(List<Protocol> protocols);

    void showUnexpectedError();
}
