package com.fooock.app.shodand.presenter;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;

import com.fooock.app.shodand.view.ProtocolView;
import com.fooock.shodand.domain.model.Protocol;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 *
 */
public class ProtocolPresenterTest {

    @Mock
    ProtocolView protocolView;

    private ProtocolPresenter protocolPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        protocolPresenter = new ProtocolPresenter(null, null, null);
        protocolPresenter.attachView(protocolView);
    }

    @Test
    public void testBottomSheetChangeWithoutProtocol() throws Exception {
        protocolPresenter.onBottomSheetStateChange(BottomSheetBehavior.STATE_EXPANDED);
        verify(protocolView).showDefaultTitle();
    }

    @Test
    public void testOnBottomSheetStateChange() throws Exception {
        Protocol protocol = new Protocol("name", "description");
        protocolPresenter.searchProtocolInfo(protocol);

        protocolPresenter.onBottomSheetStateChange(BottomSheetBehavior.STATE_HIDDEN);
        verify(protocolView).showDefaultTitle();

        protocolPresenter.onBottomSheetStateChange(BottomSheetBehavior.STATE_COLLAPSED);
        verify(protocolView).showCollapsedBottomSheet(protocol);

        protocolPresenter.onBottomSheetStateChange(BottomSheetBehavior.STATE_EXPANDED);
        verify(protocolView).showExpandedBottomSheet(protocol);

        protocolPresenter.onBottomSheetStateChange(BottomSheetBehavior.STATE_DRAGGING);
        verifyNoMoreInteractions(protocolView);
    }

    @Test
    public void testRecyclerViewStateChange() throws Exception {
        protocolPresenter.onRecyclerViewStateChange(BottomSheetBehavior.STATE_HIDDEN,
                RecyclerView.SCROLL_STATE_DRAGGING);
        verifyZeroInteractions(protocolView);

        protocolPresenter.onRecyclerViewStateChange(BottomSheetBehavior.STATE_COLLAPSED,
                RecyclerView.SCROLL_STATE_SETTLING);
        verifyZeroInteractions(protocolView);

        protocolPresenter.onRecyclerViewStateChange(BottomSheetBehavior.STATE_DRAGGING,
                RecyclerView.SCROLL_STATE_DRAGGING);
        verifyZeroInteractions(protocolView);

        protocolPresenter.onRecyclerViewStateChange(BottomSheetBehavior.STATE_COLLAPSED,
                RecyclerView.SCROLL_STATE_DRAGGING);
        verify(protocolView).onHideBottomSheet();
        verifyNoMoreInteractions(protocolView);
    }

    @After
    public void tearDown() throws Exception {
        protocolPresenter.detachView();
    }
}