package com.wondersgroup.hs.healthcloud.common.view;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * This class is from the v7 samples of the Android SDK. It's not by me!
 * <p/>
 * See the license above for details.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mDividerHeight;

    private int mOrientation;
    private boolean mHeaderDividersEnabled = true;
    private boolean mFooterDividersEnabled = true;

    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    // chenbo add
    public DividerItemDecoration(Context context, int orientation, int dividerId, int dividerHeight) {
        if (dividerId != 0) {
            mDivider = context.getResources().getDrawable(dividerId);
        }
        if (mDivider != null) {
            setOrientation(orientation);

            int dividerIntrinsic = 0;
            if (orientation == HORIZONTAL_LIST) {
                dividerIntrinsic = mDivider.getIntrinsicWidth();
            } else if (orientation == VERTICAL_LIST) {
                dividerIntrinsic = mDivider.getIntrinsicHeight();
            }
            if (dividerIntrinsic > 0) {
                mDividerHeight = dividerIntrinsic;
            } else {
                mDividerHeight = dividerHeight;
            }
        } else {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void setHeaderDividersEnabled(boolean headerDividersEnabled) {
        mHeaderDividersEnabled = headerDividersEnabled;
    }

    public void setFooterDividersEnabled(boolean footerDividersEnabled) {
        mFooterDividersEnabled = footerDividersEnabled;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            if (!mHeaderDividersEnabled || !mFooterDividersEnabled) {
                int pos =params.getViewAdapterPosition();
                if (!mHeaderDividersEnabled && pos == 0) {
                    continue;
                }
                if (!mFooterDividersEnabled && pos == parent.getAdapter().getItemCount() - 1) {
                    continue;
                }
            }
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            if (!mHeaderDividersEnabled || !mFooterDividersEnabled) {
                int pos = params.getViewAdapterPosition();
                if (!mHeaderDividersEnabled && pos == 0) {
                    continue;
                }
                if (!mFooterDividersEnabled && pos == parent.getAdapter().getItemCount() - 1) {
                    continue;
                }
            }

            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (mOrientation == VERTICAL_LIST) {
            if ((itemPosition == 0 && !mHeaderDividersEnabled)
                    || (itemPosition == parent.getAdapter().getItemCount() - 1 && !mFooterDividersEnabled)) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDividerHeight);
            }
        } else {
            if ((itemPosition == 0 && !mHeaderDividersEnabled)
                    || (itemPosition == parent.getAdapter().getItemCount() - 1 && !mFooterDividersEnabled)) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, mDividerHeight, 0);
            }
        }
    }
}
