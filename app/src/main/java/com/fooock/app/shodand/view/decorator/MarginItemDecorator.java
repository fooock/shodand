package com.fooock.app.shodand.view.decorator;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fooock.app.shodand.R;

/**
 *
 */
public class MarginItemDecorator extends RecyclerView.ItemDecoration {

    private final int margin;

    public MarginItemDecorator(Context context) {
        margin = context.getResources()
                .getDimensionPixelSize(R.dimen.margin_horizontal_element);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}
