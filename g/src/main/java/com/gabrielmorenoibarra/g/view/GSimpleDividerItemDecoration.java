package com.gabrielmorenoibarra.g.view;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Custom item for decorating lists.
 * Created by Gabriel Moreno on 2017-05-29.
 */
public class GSimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int startIndex;
    private Drawable divider;
    private int paddingLeft;
    private int paddingRight;

    public static final String NO_DIVIDER_TAG = "NO_DIVIDER";

    public GSimpleDividerItemDecoration(Drawable divider) {
        this(divider, 0);
    }

    public GSimpleDividerItemDecoration(Drawable divider, int startIndex) {
        this.divider = divider;
        this.startIndex = startIndex;
    }

    public GSimpleDividerItemDecoration(Drawable divider, int paddingLeft, int paddingRight) {
        this.divider = divider;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + paddingLeft;
        int right = parent.getWidth() - parent.getPaddingRight() - paddingRight;

        int childCount = parent.getChildCount();
        for (int i = startIndex; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child.getTag() == null || !child.getTag().equals(NO_DIVIDER_TAG)) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }
}