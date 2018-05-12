package com.wonders.health.venus.open.doctor.view;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;


public class FadingHeadViewHelper {

    private View mHeadView;
    private int mAlpha = 255;
    private Drawable mDrawable;

    public FadingHeadViewHelper(View view, Drawable drawable) {
        this.mHeadView = view;
        this.mDrawable = drawable;
        setHeadViewBackgroundDrawable(drawable);
        setHeadViewAlpha(0);
    }

    private void setHeadViewBackgroundDrawable(Drawable drawable) {
        setHeadViewBackgroundDrawable(drawable, true);
    }

    private void setHeadViewBackgroundDrawable(Drawable drawable, boolean mutate) {
        mDrawable = mutate ? drawable.mutate() : drawable;
        mHeadView.setBackgroundDrawable(mDrawable);
        if (mAlpha == 255) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                mAlpha = mDrawable.getAlpha();
        } else {
            setHeadViewAlpha(mAlpha);
        }
    }

    public void setHeadViewAlpha(int alpha) {
        if (mDrawable != null) {
            mDrawable.setAlpha(alpha);
            mAlpha = alpha;
        }
    }

    public float onChange(int viewHeight, int scrollHeight) {
        float progress = (float) scrollHeight / viewHeight;
        if (progress > 1f) {
            progress = 1f;
        }
        setHeadViewAlpha((int) (255 * progress));
        return progress;
    }

    public int getAlpha() {
        return mAlpha;
    }
}
