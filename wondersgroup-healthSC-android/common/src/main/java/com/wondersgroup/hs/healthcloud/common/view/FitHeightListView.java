package com.wondersgroup.hs.healthcloud.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wondersgroup.hs.healthcloud.common.R;

/**
 * 固定高度的listview
 * FitHeightListView
 * chenbo
 */
public class FitHeightListView extends ViewGroup {
    private BaseAdapter mAdapter;

    private Drawable mDividerDrawable;
    private int mListSelectorId;
    private int mDividerHeight;
    private boolean mFooterDividersEnabled;

    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    private DataChangeObserver mDataSetObserver;

    private boolean mHasMeasure;
    private boolean mIsLayout;

    private LayoutParams mChildLayoutParams;

    public FitHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FitHeightListView);
            mDividerDrawable = a.getDrawable(R.styleable.FitHeightListView_list_divider);
            mListSelectorId = a.getResourceId(R.styleable.FitHeightListView_list_selector, 0);
            mDividerHeight = a.getDimensionPixelOffset(R.styleable.FitHeightListView_list_divider_height, 0);
            mFooterDividersEnabled = a.getBoolean(R.styleable.FitHeightListView_footer_dividers_enabled, true);
            a.recycle();
        }
        mChildLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (mDividerHeight == 0 && mDividerDrawable != null) {
            mDividerHeight = mDividerDrawable.getIntrinsicHeight();
        }
        if (mDividerDrawable != null) {
            setWillNotDraw(false);
        }
    }


    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;
        mDataSetObserver = new DataChangeObserver();
        mAdapter.registerDataSetObserver(mDataSetObserver);

        refresh();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }

    private void refresh() {
        mHasMeasure = false;
        if (mAdapter == null) {
            removeAllViews();
            return;
        }
        final int adapterCount = mAdapter.getCount();
        final int childrenCount = getChildCount();
        if (adapterCount == childrenCount) {
            for (int i = 0; i < adapterCount; i++) {
                mAdapter.getView(i, getChildAt(i), this);
            }
        } else if (adapterCount < childrenCount) {
            for (int i = 0; i < adapterCount; i++) {
                mAdapter.getView(i, getChildAt(i), this);
            }
            removeViews(adapterCount, childrenCount - adapterCount);
        } else {
            for (int i = 0; i < adapterCount; i++) {
                View view;
                if (i < childrenCount) {
                    mAdapter.getView(i, getChildAt(i), this);
                } else {
                    view = mAdapter.getView(i, null, this);
                    addViewInLayout(view, -1, mChildLayoutParams, true);
                    final int position = i;
                    view.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mItemClickListener != null) {
                                mItemClickListener.onItemClick(FitHeightListView.this, v, position);
                            }
                        }
                    });
                    view.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            if (mItemLongClickListener != null) {
                                return mItemLongClickListener.onItemLongClick(FitHeightListView.this, v, position);
                            }
                            return false;
                        }
                    });
                }
            }
        }
        // 修改FitHeightListView在ScrollView中展开和收起bug(如果不合适，就删除下面一句)
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            final int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            for (int i = 0, size = getChildCount(); i < size; i++) {
                View child = getChildAt(i);
//                int childWidthMeasureSpec = widthMeasureSpec;
//                int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//                int h = MeasureSpec.getSize(childHeightMeasureSpec);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
//                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                height += (child.getMeasuredHeight() + mDividerHeight);
            }
            setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mIsLayout = true;
        int height = 0;
        for (int i = 0, size = getChildCount(); i < size; i++) {
            View child = getChildAt(i);
            child.layout(getPaddingLeft(), height + getPaddingTop(),
                    child.getMeasuredWidth() + getPaddingLeft(),
                    height + child.getMeasuredHeight() + getPaddingTop());
            height += child.getMeasuredHeight() + mDividerHeight;
        }
        mIsLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!mIsLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDividerDrawable != null) {
            drawVertical(canvas);
        }
    }

    private void drawVertical(Canvas c) {
        final int left = getPaddingLeft();
        final int right = getWidth() - getPaddingRight();

        final int childCount = mFooterDividersEnabled ? getChildCount() : getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final int top = child.getBottom();
            final int bottom = top + mDividerHeight;
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private class DataChangeObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            refresh();
        }

        @Override
        public void onInvalidated() {

        }
    }

    public static interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, int pos);
    }

    public static interface OnItemLongClickListener {
        boolean onItemLongClick(ViewGroup parent, View view, int pos);
    }
}
