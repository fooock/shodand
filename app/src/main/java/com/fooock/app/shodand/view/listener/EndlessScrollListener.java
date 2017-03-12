package com.fooock.app.shodand.view.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 *
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private static final int CURRENT_PAGE = 1;
    private static final int VISIBLE_THRESHOLD = 1;

    private int prevTotal = 0;
    private int actualPage = CURRENT_PAGE;
    private boolean loading = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // Adapted code from http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
        if (dy < 0) {
            // If dy is < 0 the scroll is up
            return;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        final int childCount = recyclerView.getChildCount();
        final int itemCount = layoutManager.getItemCount();
        final int visibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (itemCount > prevTotal) {
                loading = false;
                prevTotal = itemCount;
            }
        }

        final int lastElements = itemCount - childCount;
        if (!loading && lastElements <= (visibleItemPosition + VISIBLE_THRESHOLD)) {
            actualPage += 1;
            onUpdateMore(actualPage);
            loading = true;
        }
    }

    public abstract void onUpdateMore(int page);
}
