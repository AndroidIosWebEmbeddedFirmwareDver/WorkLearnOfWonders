package com.wondersgroup.hs.healthcloud.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.hs.healthcloud.common.R;

import java.util.ArrayList;


/**
 * 能够控件自动换行的布局
 * AutoLineBreakLayout
 * chenbo
 * 2015年3月16日 上午11:01:50
 * @version 1.0
 */
public class AutoLineBreakLayout extends ViewGroup {
    private ArrayList<View> mLineChilds;
    private boolean mNeedFillChild;

    public ArrayList<View> getLineChilds() {
        return mLineChilds;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        public final int horizontal_spacing;
        public final int vertical_spacing;

        /**
         * @param horizontal_spacing
         *            Pixels between items, horizontally
         * @param vertical_spacing
         *            Pixels between items, vertically
         */
        public LayoutParams(int width, int height, int horizontal_spacing, int vertical_spacing) {
            super(width, height);
            this.horizontal_spacing = horizontal_spacing;
            this.vertical_spacing = vertical_spacing;
        }
    }

    public AutoLineBreakLayout(Context context) {
        super(context);
        init();
    }

    public AutoLineBreakLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoLineBreakLayout);
        mNeedFillChild = a.getBoolean(R.styleable.AutoLineBreakLayout_nee_fill_child, false);
        a.recycle();
        init();
    }

    public boolean isNeedFillChild() {
        return mNeedFillChild;
    }

    public void setNeedFillChild(boolean needFillChild) {
        this.mNeedFillChild = needFillChild;
        requestLayout();
    }

    private void init() {
        if (mNeedFillChild) {
            mLineChilds = new ArrayList<View>();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        assert (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED);

        final int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();

        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        final int count = getChildCount();
        int line_height = 0;

        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();

        int childHeightMeasureSpec;

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        } else {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        for (int i = 0; i < count; i++) {

            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width > 0 || lp.height > 0)
                    child.measure(MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY));
                else
                    child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec);
                final int childw = child.getMeasuredWidth();
                if (mLineChilds != null) {
                    mLineChilds.add(child);
                }
                if (xpos + childw > width) {
                    if (mLineChilds != null) {
                        mLineChilds.remove(child);
                        for (View view : mLineChilds) {
                            view.measure(MeasureSpec.makeMeasureSpec(
                                    (int) (view.getMeasuredWidth() * (float) width / xpos), MeasureSpec.EXACTLY),
                                    childHeightMeasureSpec);
                        }
                        mLineChilds.clear();
                        mLineChilds.add(child);
                    }
                    xpos = getPaddingLeft();
                    ypos += line_height;
                }

                xpos += childw + lp.horizontal_spacing;
                line_height = child.getMeasuredHeight() + lp.vertical_spacing;
            }
        }

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = ypos + line_height;

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (ypos + line_height < height) {
                height = ypos + line_height;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(0, 0, 0, 0); // default of 0px spacing
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof LayoutParams) {
            return true;
        }
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        final int width = r - l;
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        int lineHeight = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int childw = child.getMeasuredWidth();
                final int childh = child.getMeasuredHeight();
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                if (xpos + childw > width) {
                    xpos = getPaddingLeft();
                    ypos += lineHeight;
                }

                lineHeight = child.getMeasuredHeight() + lp.vertical_spacing;

                child.layout(xpos, ypos, xpos + childw, ypos + childh);
                xpos += childw + lp.horizontal_spacing;
            }
        }
    }
}
