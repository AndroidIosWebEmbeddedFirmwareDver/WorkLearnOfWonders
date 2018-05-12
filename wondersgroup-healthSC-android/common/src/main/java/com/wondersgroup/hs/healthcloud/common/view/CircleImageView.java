package com.wondersgroup.hs.healthcloud.common.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.wondersgroup.hs.healthcloud.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;

public class CircleImageView extends ImageView {

    // Constants for tile mode attributes
    private static final int TILE_MODE_UNDEFINED = -2;
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_REPEAT = 1;
    private static final int TILE_MODE_MIRROR = 2;

    public static final String TAG = "RoundedImageView";
    public static final float DEFAULT_RADIUS = 0f;
    public static final float DEFAULT_BORDER_WIDTH = 0f;
    public static final Shader.TileMode DEFAULT_TILE_MODE = Shader.TileMode.CLAMP;
    private static final ScaleType[] SCALE_TYPES = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    private final float[] mCornerRadii =
            new float[] { DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS };

    private Drawable mBackgroundDrawable;
    private ColorStateList mBorderColor =
            ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
    private float mBorderWidth = DEFAULT_BORDER_WIDTH;
    private ColorFilter mColorFilter = null;
    private boolean mColorMod = false;
    private Drawable mDrawable;
    private boolean mHasColorFilter = false;
    private boolean mIsOval = false;
    private boolean mMutateBackground = false;
    private int mResource;
    private int mBackgroundResource;
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    private Shader.TileMode mTileModeX = DEFAULT_TILE_MODE;
    private Shader.TileMode mTileModeY = DEFAULT_TILE_MODE;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);

        int index = a.getInt(R.styleable.CircleImageView_android_scaleType, -1);
        if (index >= 0) {
            setScaleType(SCALE_TYPES[index]);
        } else {
            // default scaletype to FIT_CENTER
            setScaleType(ScaleType.FIT_CENTER);
        }

        float cornerRadiusOverride =
                a.getDimensionPixelSize(R.styleable.CircleImageView_corner_radius, -1);

        mCornerRadii[Corner.TOP_LEFT] =
                a.getDimensionPixelSize(R.styleable.CircleImageView_corner_radius_top_left, -1);
        mCornerRadii[Corner.TOP_RIGHT] =
                a.getDimensionPixelSize(R.styleable.CircleImageView_corner_radius_top_right, -1);
        mCornerRadii[Corner.BOTTOM_RIGHT] =
                a.getDimensionPixelSize(R.styleable.CircleImageView_corner_radius_bottom_right, -1);
        mCornerRadii[Corner.BOTTOM_LEFT] =
                a.getDimensionPixelSize(R.styleable.CircleImageView_corner_radius_bottom_left, -1);

        boolean any = false;
        for (int i = 0, len = mCornerRadii.length; i < len; i++) {
            if (mCornerRadii[i] < 0) {
                mCornerRadii[i] = 0f;
            } else {
                any = true;
            }
        }

        if (!any) {
            if (cornerRadiusOverride < 0) {
                cornerRadiusOverride = DEFAULT_RADIUS;
            }
            for (int i = 0, len = mCornerRadii.length; i < len; i++) {
                mCornerRadii[i] = cornerRadiusOverride;
            }
        }

        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, -1);
        if (mBorderWidth < 0) {
            mBorderWidth = DEFAULT_BORDER_WIDTH;
        }

        mBorderColor = a.getColorStateList(R.styleable.CircleImageView_border_color);
        if (mBorderColor == null) {
            mBorderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        }

        mMutateBackground = a.getBoolean(R.styleable.CircleImageView_mutate_background, false);
        mIsOval = a.getBoolean(R.styleable.CircleImageView_oval, true);
        if (mIsOval) {
            mIsOval = false;
            for (int i = 0, len = mCornerRadii.length; i < len; i++) {
                mCornerRadii[i] = 10000;
            }
            setScaleType(ScaleType.CENTER_CROP);
        }

        final int tileMode = a.getInt(R.styleable.CircleImageView_tile_mode, TILE_MODE_UNDEFINED);
        if (tileMode != TILE_MODE_UNDEFINED) {
            setTileModeX(parseTileMode(tileMode));
            setTileModeY(parseTileMode(tileMode));
        }

        final int tileModeX =
                a.getInt(R.styleable.CircleImageView_tile_mode_x, TILE_MODE_UNDEFINED);
        if (tileModeX != TILE_MODE_UNDEFINED) {
            setTileModeX(parseTileMode(tileModeX));
        }

        final int tileModeY =
                a.getInt(R.styleable.CircleImageView_tile_mode_y, TILE_MODE_UNDEFINED);
        if (tileModeY != TILE_MODE_UNDEFINED) {
            setTileModeY(parseTileMode(tileModeY));
        }

        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(true);

        if (mMutateBackground) {
            // when setBackground() is called by View constructor, mMutateBackground is not loaded from the attribute,
            // so it's false by default, what doesn't allow to create the RoundedDrawable. At this point, after load
            // mMutateBackground and updated BackgroundDrawable to RoundedDrawable, the View's background drawable needs to
            // be changed to this new drawable.
            //noinspection deprecation
            super.setBackgroundDrawable(mBackgroundDrawable);
        }

        a.recycle();
    }

    private static Shader.TileMode parseTileMode(int tileMode) {
        switch (tileMode) {
            case TILE_MODE_CLAMP:
                return Shader.TileMode.CLAMP;
            case TILE_MODE_REPEAT:
                return Shader.TileMode.REPEAT;
            case TILE_MODE_MIRROR:
                return Shader.TileMode.MIRROR;
            default:
                return null;
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        assert scaleType != null;

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            switch (scaleType) {
                case CENTER:
                case CENTER_CROP:
                case CENTER_INSIDE:
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                case FIT_XY:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }

            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
            invalidate();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mResource = 0;
        mDrawable = RoundedDrawable.fromDrawable(drawable);
        updateDrawableAttrs();
        super.setImageDrawable(mDrawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mResource = 0;
        mDrawable = RoundedDrawable.fromBitmap(bm);
        updateDrawableAttrs();
        super.setImageDrawable(mDrawable);
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        if (mResource != resId) {
            mResource = resId;
            mDrawable = resolveResource();
            updateDrawableAttrs();
            super.setImageDrawable(mDrawable);
        }
    }

    @Override public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    private Drawable resolveResource() {
        Resources rsrc = getResources();
        if (rsrc == null) { return null; }

        Drawable d = null;

        if (mResource != 0) {
            try {
                d = rsrc.getDrawable(mResource);
            } catch (Exception e) {
                Log.w(TAG, "Unable to find resource: " + mResource, e);
                // Don't try again.
                mResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(d);
    }

    @Override
    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        if (mBackgroundResource != resId) {
            mBackgroundResource = resId;
            mBackgroundDrawable = resolveBackgroundResource();
            setBackgroundDrawable(mBackgroundDrawable);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundDrawable = new ColorDrawable(color);
        setBackgroundDrawable(mBackgroundDrawable);
    }

    private Drawable resolveBackgroundResource() {
        Resources rsrc = getResources();
        if (rsrc == null) { return null; }

        Drawable d = null;

        if (mBackgroundResource != 0) {
            try {
                d = rsrc.getDrawable(mBackgroundResource);
            } catch (Exception e) {
                Log.w(TAG, "Unable to find resource: " + mBackgroundResource, e);
                // Don't try again.
                mBackgroundResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(d);
    }

    private void updateDrawableAttrs() {
        updateAttrs(mDrawable, mScaleType);
    }

    private void updateBackgroundDrawableAttrs(boolean convert) {
        if (mMutateBackground) {
            if (convert) {
                mBackgroundDrawable = RoundedDrawable.fromDrawable(mBackgroundDrawable);
            }
            updateAttrs(mBackgroundDrawable, ScaleType.FIT_XY);
        }
    }

    @Override public void setColorFilter(ColorFilter cf) {
        if (mColorFilter != cf) {
            mColorFilter = cf;
            mHasColorFilter = true;
            mColorMod = true;
            applyColorMod();
            invalidate();
        }
    }

    private void applyColorMod() {
        // Only mutate and apply when modifications have occurred. This should
        // not reset the mColorMod flag, since these filters need to be
        // re-applied if the Drawable is changed.
        if (mDrawable != null && mColorMod) {
            mDrawable = mDrawable.mutate();
            if (mHasColorFilter) {
                mDrawable.setColorFilter(mColorFilter);
            }
            // TODO: support, eventually...
            //mDrawable.setXfermode(mXfermode);
            //mDrawable.setAlpha(mAlpha * mViewAlphaScale >> 8);
        }
    }

    private void updateAttrs(Drawable drawable, ScaleType scaleType) {
        if (drawable == null) { return; }

        if (drawable instanceof RoundedDrawable) {
            ((RoundedDrawable) drawable)
                    .setScaleType(scaleType)
                    .setBorderWidth(mBorderWidth)
                    .setBorderColor(mBorderColor)
                    .setOval(mIsOval)
                    .setTileModeX(mTileModeX)
                    .setTileModeY(mTileModeY);

            if (mCornerRadii != null) {
                ((RoundedDrawable) drawable).setCornerRadius(
                        mCornerRadii[Corner.TOP_LEFT],
                        mCornerRadii[Corner.TOP_RIGHT],
                        mCornerRadii[Corner.BOTTOM_RIGHT],
                        mCornerRadii[Corner.BOTTOM_LEFT]);
            }

            applyColorMod();
        } else if (drawable instanceof LayerDrawable) {
            // loop through layers to and set drawable attrs
            LayerDrawable ld = ((LayerDrawable) drawable);
            for (int i = 0, layers = ld.getNumberOfLayers(); i < layers; i++) {
                updateAttrs(ld.getDrawable(i), scaleType);
            }
        }
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
        updateBackgroundDrawableAttrs(true);
        //noinspection deprecation
        super.setBackgroundDrawable(mBackgroundDrawable);
    }

    /**
     * @return the largest corner radius.
     */
    public float getCornerRadius() {
        return getMaxCornerRadius();
    }

    /**
     * @return the largest corner radius.
     */
    public float getMaxCornerRadius() {
        float maxRadius = 0;
        for (float r : mCornerRadii) {
            maxRadius = Math.max(r, maxRadius);
        }
        return maxRadius;
    }

    /**
     * Get the corner radius of a specified corner.
     *
     * @param corner the corner.
     * @return the radius.
     */
    public float getCornerRadius(@Corner int corner) {
        return mCornerRadii[corner];
    }

    /**
     * Set all the corner radii from a dimension resource id.
     *
     * @param resId dimension resource id of radii.
     */
    public void setCornerRadiusDimen(@DimenRes int resId) {
        float radius = getResources().getDimension(resId);
        setCornerRadius(radius, radius, radius, radius);
    }

    /**
     * Set the corner radius of a specific corner from a dimension resource id.
     *
     * @param corner the corner to set.
     * @param resId the dimension resource id of the corner radius.
     */
    public void setCornerRadiusDimen(@Corner int corner, @DimenRes int resId) {
        setCornerRadius(corner, getResources().getDimensionPixelSize(resId));
    }

    /**
     * Set the corner radii of all corners in px.
     *
     * @param radius the radius to set.
     */
    public void setCornerRadius(float radius) {
        setCornerRadius(radius, radius, radius, radius);
    }

    /**
     * Set the corner radius of a specific corner in px.
     *
     * @param corner the corner to set.
     * @param radius the corner radius to set in px.
     */
    public void setCornerRadius(@Corner int corner, float radius) {
        if (mCornerRadii[corner] == radius) {
            return;
        }
        mCornerRadii[corner] = radius;

        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    /**
     * Set the corner radii of each corner individually. Currently only one unique nonzero value is
     * supported.
     *
     * @param topLeft radius of the top left corner in px.
     * @param topRight radius of the top right corner in px.
     * @param bottomRight radius of the bottom right corner in px.
     * @param bottomLeft radius of the bottom left corner in px.
     */
    public void setCornerRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        if (mCornerRadii[Corner.TOP_LEFT] == topLeft
                && mCornerRadii[Corner.TOP_RIGHT] == topRight
                && mCornerRadii[Corner.BOTTOM_RIGHT] == bottomRight
                && mCornerRadii[Corner.BOTTOM_LEFT] == bottomLeft) {
            return;
        }

        mCornerRadii[Corner.TOP_LEFT] = topLeft;
        mCornerRadii[Corner.TOP_RIGHT] = topRight;
        mCornerRadii[Corner.BOTTOM_LEFT] = bottomLeft;
        mCornerRadii[Corner.BOTTOM_RIGHT] = bottomRight;

        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(@DimenRes int resId) {
        setBorderWidth(getResources().getDimension(resId));
    }

    public void setBorderWidth(float width) {
        if (mBorderWidth == width) { return; }

        mBorderWidth = width;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    @ColorInt
    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    public void setBorderColor(@ColorInt int color) {
        setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return mBorderColor;
    }

    public void setBorderColor(ColorStateList colors) {
        if (mBorderColor.equals(colors)) { return; }

        mBorderColor =
                (colors != null) ? colors : ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        if (mBorderWidth > 0) {
            invalidate();
        }
    }

    public boolean isOval() {
        return mIsOval;
    }

    public void setOval(boolean oval) {
        mIsOval = oval;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public Shader.TileMode getTileModeX() {
        return mTileModeX;
    }

    public void setTileModeX(Shader.TileMode tileModeX) {
        if (this.mTileModeX == tileModeX) { return; }

        this.mTileModeX = tileModeX;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public Shader.TileMode getTileModeY() {
        return mTileModeY;
    }

    public void setTileModeY(Shader.TileMode tileModeY) {
        if (this.mTileModeY == tileModeY) { return; }

        this.mTileModeY = tileModeY;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public boolean mutatesBackground() {
        return mMutateBackground;
    }

    public void mutateBackground(boolean mutate) {
        if (mMutateBackground == mutate) { return; }

        mMutateBackground = mutate;
        updateBackgroundDrawableAttrs(true);
        invalidate();
    }


    ///////////
    static class RoundedDrawable extends Drawable {

        public static final String TAG = "RoundedDrawable";
        public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

        private final RectF mBounds = new RectF();
        private final RectF mDrawableRect = new RectF();
        private final RectF mBitmapRect = new RectF();
        private final Bitmap mBitmap;
        private final Paint mBitmapPaint;
        private final int mBitmapWidth;
        private final int mBitmapHeight;
        private final RectF mBorderRect = new RectF();
        private final Paint mBorderPaint;
        private final Matrix mShaderMatrix = new Matrix();
        private final RectF mSquareCornersRect = new RectF();

        private Shader.TileMode mTileModeX = Shader.TileMode.CLAMP;
        private Shader.TileMode mTileModeY = Shader.TileMode.CLAMP;
        private boolean mRebuildShader = true;

        // [ topLeft, topRight, bottomLeft, bottomRight ]
        private float mCornerRadius = 0f;
        private final boolean[] mCornersRounded = new boolean[] { true, true, true, true };

        private boolean mOval = false;
        private float mBorderWidth = 0;
        private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
        private ScaleType mScaleType = ScaleType.FIT_CENTER;

        public RoundedDrawable(Bitmap bitmap) {
            mBitmap = bitmap;

            mBitmapWidth = bitmap.getWidth();
            mBitmapHeight = bitmap.getHeight();
            mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);

            mBitmapPaint = new Paint();
            mBitmapPaint.setStyle(Paint.Style.FILL);
            mBitmapPaint.setAntiAlias(true);

            mBorderPaint = new Paint();
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }

        public static RoundedDrawable fromBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                return new RoundedDrawable(bitmap);
            } else {
                return null;
            }
        }

        public static Drawable fromDrawable(Drawable drawable) {
            if (drawable != null) {
                if (drawable instanceof RoundedDrawable) {
                    // just return if it's already a RoundedDrawable
                    return drawable;
                } else if (drawable instanceof LayerDrawable) {
                    LayerDrawable ld = (LayerDrawable) drawable;
                    int num = ld.getNumberOfLayers();

                    // loop through layers to and change to RoundedDrawables if possible
                    for (int i = 0; i < num; i++) {
                        Drawable d = ld.getDrawable(i);
                        ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d));
                    }
                    return ld;
                }

                // try to get a bitmap from the drawable and
                Bitmap bm = drawableToBitmap(drawable);
                if (bm != null) {
                    return new RoundedDrawable(bm);
                }
            }
            return drawable;
        }

        public static Bitmap drawableToBitmap(Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            Bitmap bitmap;
            int width = Math.max(drawable.getIntrinsicWidth(), 2);
            int height = Math.max(drawable.getIntrinsicHeight(), 2);
            try {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
                Log.w(TAG, "Failed to create bitmap from drawable!");
                bitmap = null;
            }

            return bitmap;
        }

        public Bitmap getSourceBitmap() {
            return mBitmap;
        }

        @Override
        public boolean isStateful() {
            return mBorderColor.isStateful();
        }

        @Override
        protected boolean onStateChange(int[] state) {
            int newColor = mBorderColor.getColorForState(state, 0);
            if (mBorderPaint.getColor() != newColor) {
                mBorderPaint.setColor(newColor);
                return true;
            } else {
                return super.onStateChange(state);
            }
        }

        private void updateShaderMatrix() {
            float scale;
            float dx;
            float dy;

            switch (mScaleType) {
                case CENTER:
                    mBorderRect.set(mBounds);
                    mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);

                    mShaderMatrix.reset();
                    mShaderMatrix.setTranslate((int) ((mBorderRect.width() - mBitmapWidth) * 0.5f + 0.5f),
                            (int) ((mBorderRect.height() - mBitmapHeight) * 0.5f + 0.5f));
                    break;

                case CENTER_CROP:
                    mBorderRect.set(mBounds);
                    mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);

                    mShaderMatrix.reset();

                    dx = 0;
                    dy = 0;

                    if (mBitmapWidth * mBorderRect.height() > mBorderRect.width() * mBitmapHeight) {
                        scale = mBorderRect.height() / (float) mBitmapHeight;
                        dx = (mBorderRect.width() - mBitmapWidth * scale) * 0.5f;
                    } else {
                        scale = mBorderRect.width() / (float) mBitmapWidth;
                        dy = (mBorderRect.height() - mBitmapHeight * scale) * 0.5f;
                    }

                    mShaderMatrix.setScale(scale, scale);
                    mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth / 2,
                            (int) (dy + 0.5f) + mBorderWidth / 2);
                    break;

                case CENTER_INSIDE:
                    mShaderMatrix.reset();

                    if (mBitmapWidth <= mBounds.width() && mBitmapHeight <= mBounds.height()) {
                        scale = 1.0f;
                    } else {
                        scale = Math.min(mBounds.width() / (float) mBitmapWidth,
                                mBounds.height() / (float) mBitmapHeight);
                    }

                    dx = (int) ((mBounds.width() - mBitmapWidth * scale) * 0.5f + 0.5f);
                    dy = (int) ((mBounds.height() - mBitmapHeight * scale) * 0.5f + 0.5f);

                    mShaderMatrix.setScale(scale, scale);
                    mShaderMatrix.postTranslate(dx, dy);

                    mBorderRect.set(mBitmapRect);
                    mShaderMatrix.mapRect(mBorderRect);
                    mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                    mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                    break;

                default:
                case FIT_CENTER:
                    mBorderRect.set(mBitmapRect);
                    mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.CENTER);
                    mShaderMatrix.mapRect(mBorderRect);
                    mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                    mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                    break;

                case FIT_END:
                    mBorderRect.set(mBitmapRect);
                    mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.END);
                    mShaderMatrix.mapRect(mBorderRect);
                    mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                    mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                    break;

                case FIT_START:
                    mBorderRect.set(mBitmapRect);
                    mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.START);
                    mShaderMatrix.mapRect(mBorderRect);
                    mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                    mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                    break;

                case FIT_XY:
                    mBorderRect.set(mBounds);
                    mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
                    mShaderMatrix.reset();
                    mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                    break;
            }

            mDrawableRect.set(mBorderRect);
        }

        @Override
        protected void onBoundsChange(@NonNull Rect bounds) {
            super.onBoundsChange(bounds);

            mBounds.set(bounds);

            updateShaderMatrix();
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            if (mRebuildShader) {
                BitmapShader bitmapShader = new BitmapShader(mBitmap, mTileModeX, mTileModeY);
                if (mTileModeX == Shader.TileMode.CLAMP && mTileModeY == Shader.TileMode.CLAMP) {
                    bitmapShader.setLocalMatrix(mShaderMatrix);
                }
                mBitmapPaint.setShader(bitmapShader);
                mRebuildShader = false;
            }

            if (mOval) {
                if (mBorderWidth > 0) {
                    canvas.drawOval(mDrawableRect, mBitmapPaint);
                    canvas.drawOval(mBorderRect, mBorderPaint);
                } else {
                    canvas.drawOval(mDrawableRect, mBitmapPaint);
                }
            } else {
                if (any(mCornersRounded)) {
                    float radius = mCornerRadius;
                    if (mBorderWidth > 0) {
                        canvas.drawRoundRect(mDrawableRect, radius, radius, mBitmapPaint);
                        canvas.drawRoundRect(mBorderRect, radius, radius, mBorderPaint);
                        redrawBitmapForSquareCorners(canvas);
                        redrawBorderForSquareCorners(canvas);
                    } else {
                        canvas.drawRoundRect(mDrawableRect, radius, radius, mBitmapPaint);
                        redrawBitmapForSquareCorners(canvas);
                    }
                } else {
                    canvas.drawRect(mDrawableRect, mBitmapPaint);
                    if (mBorderWidth > 0) {
                        canvas.drawRect(mBorderRect, mBorderPaint);
                    }
                }
            }
        }

        private void redrawBitmapForSquareCorners(Canvas canvas) {
            if (all(mCornersRounded)) {
                // no square corners
                return;
            }

            if (mCornerRadius == 0) {
                return; // no round corners
            }

            float left = mDrawableRect.left;
            float top = mDrawableRect.top;
            float right = left + mDrawableRect.width();
            float bottom = top + mDrawableRect.height();
            float radius = mCornerRadius;

            if (!mCornersRounded[Corner.TOP_LEFT]) {
                mSquareCornersRect.set(left, top, left + radius, top + radius);
                canvas.drawRect(mSquareCornersRect, mBitmapPaint);
            }

            if (!mCornersRounded[Corner.TOP_RIGHT]) {
                mSquareCornersRect.set(right - radius, top, right, radius);
                canvas.drawRect(mSquareCornersRect, mBitmapPaint);
            }

            if (!mCornersRounded[Corner.BOTTOM_RIGHT]) {
                mSquareCornersRect.set(right - radius, bottom - radius, right, bottom);
                canvas.drawRect(mSquareCornersRect, mBitmapPaint);
            }

            if (!mCornersRounded[Corner.BOTTOM_LEFT]) {
                mSquareCornersRect.set(left, bottom - radius, left + radius, bottom);
                canvas.drawRect(mSquareCornersRect, mBitmapPaint);
            }
        }

        private void redrawBorderForSquareCorners(Canvas canvas) {
            if (all(mCornersRounded)) {
                // no square corners
                return;
            }

            if (mCornerRadius == 0) {
                return; // no round corners
            }

            float left = mDrawableRect.left;
            float top = mDrawableRect.top;
            float right = left + mDrawableRect.width();
            float bottom = top + mDrawableRect.height();
            float radius = mCornerRadius;
            float offset = mBorderWidth / 2;

            if (!mCornersRounded[Corner.TOP_LEFT]) {
                canvas.drawLine(left - offset, top, left + radius, top, mBorderPaint);
                canvas.drawLine(left, top - offset, left, top + radius, mBorderPaint);
            }

            if (!mCornersRounded[Corner.TOP_RIGHT]) {
                canvas.drawLine(right - radius - offset, top, right, top, mBorderPaint);
                canvas.drawLine(right, top - offset, right, top + radius, mBorderPaint);
            }

            if (!mCornersRounded[Corner.BOTTOM_RIGHT]) {
                canvas.drawLine(right - radius - offset, bottom, right + offset, bottom, mBorderPaint);
                canvas.drawLine(right, bottom - radius, right, bottom, mBorderPaint);
            }

            if (!mCornersRounded[Corner.BOTTOM_LEFT]) {
                canvas.drawLine(left - offset, bottom, left + radius, bottom, mBorderPaint);
                canvas.drawLine(left, bottom - radius, left, bottom, mBorderPaint);
            }
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public int getAlpha() {
            return mBitmapPaint.getAlpha();
        }

        @Override
        public void setAlpha(int alpha) {
            mBitmapPaint.setAlpha(alpha);
            invalidateSelf();
        }

        @Override
        public ColorFilter getColorFilter() {
            return mBitmapPaint.getColorFilter();
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mBitmapPaint.setColorFilter(cf);
            invalidateSelf();
        }

        @Override
        public void setDither(boolean dither) {
            mBitmapPaint.setDither(dither);
            invalidateSelf();
        }

        @Override
        public void setFilterBitmap(boolean filter) {
            mBitmapPaint.setFilterBitmap(filter);
            invalidateSelf();
        }

        @Override
        public int getIntrinsicWidth() {
            return mBitmapWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mBitmapHeight;
        }

        /**
         * @return the corner radius.
         */
        public float getCornerRadius() {
            return mCornerRadius;
        }

        /**
         * @param corner the specific corner to get radius of.
         * @return the corner radius of the specified corner.
         */
        public float getCornerRadius(@Corner int corner) {
            return mCornersRounded[corner] ? mCornerRadius : 0f;
        }

        /**
         * Sets all corners to the specified radius.
         *
         * @param radius the radius.
         * @return the {@link RoundedDrawable} for chaining.
         */
        public RoundedDrawable setCornerRadius(float radius) {
            setCornerRadius(radius, radius, radius, radius);
            return this;
        }

        /**
         * Sets the corner radius of one specific corner.
         *
         * @param corner the corner.
         * @param radius the radius.
         * @return the {@link RoundedDrawable} for chaining.
         */
        public RoundedDrawable setCornerRadius(@Corner int corner, float radius) {
            if (radius != 0 && mCornerRadius != 0 && mCornerRadius != radius) {
                throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
            }

            if (radius == 0) {
                if (only(corner, mCornersRounded)) {
                    mCornerRadius = 0;
                }
                mCornersRounded[corner] = false;
            } else {
                if (mCornerRadius == 0) {
                    mCornerRadius = radius;
                }
                mCornersRounded[corner] = true;
            }

            return this;
        }

        /**
         * Sets the corner radii of all the corners.
         *
         * @param topLeft top left corner radius.
         * @param topRight top right corner radius
         * @param bottomRight bototm right corner radius.
         * @param bottomLeft bottom left corner radius.
         * @return the {@link RoundedDrawable} for chaining.
         */
        public RoundedDrawable setCornerRadius(float topLeft, float topRight, float bottomRight,
                                               float bottomLeft) {
            Set<Float> radiusSet = new HashSet<>(4);
            radiusSet.add(topLeft);
            radiusSet.add(topRight);
            radiusSet.add(bottomRight);
            radiusSet.add(bottomLeft);

            radiusSet.remove(0f);

            if (radiusSet.size() > 1) {
                throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
            }

            if (!radiusSet.isEmpty()) {
                float radius = radiusSet.iterator().next();
                if (Float.isInfinite(radius) || Float.isNaN(radius) || radius < 0) {
                    throw new IllegalArgumentException("Invalid radius value: " + radius);
                }
                mCornerRadius = radius;
            } else {
                mCornerRadius = 0f;
            }

            mCornersRounded[Corner.TOP_LEFT] = topLeft > 0;
            mCornersRounded[Corner.TOP_RIGHT] = topRight > 0;
            mCornersRounded[Corner.BOTTOM_RIGHT] = bottomRight > 0;
            mCornersRounded[Corner.BOTTOM_LEFT] = bottomLeft > 0;
            return this;
        }

        public float getBorderWidth() {
            return mBorderWidth;
        }

        public RoundedDrawable setBorderWidth(float width) {
            mBorderWidth = width;
            mBorderPaint.setStrokeWidth(mBorderWidth);
            return this;
        }

        public int getBorderColor() {
            return mBorderColor.getDefaultColor();
        }

        public RoundedDrawable setBorderColor(@ColorInt int color) {
            return setBorderColor(ColorStateList.valueOf(color));
        }

        public ColorStateList getBorderColors() {
            return mBorderColor;
        }

        public RoundedDrawable setBorderColor(ColorStateList colors) {
            mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
            mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
            return this;
        }

        public boolean isOval() {
            return mOval;
        }

        public RoundedDrawable setOval(boolean oval) {
            mOval = oval;
            return this;
        }

        public ScaleType getScaleType() {
            return mScaleType;
        }

        public RoundedDrawable setScaleType(ScaleType scaleType) {
            if (scaleType == null) {
                scaleType = ScaleType.FIT_CENTER;
            }
            if (mScaleType != scaleType) {
                mScaleType = scaleType;
                updateShaderMatrix();
            }
            return this;
        }

        public Shader.TileMode getTileModeX() {
            return mTileModeX;
        }

        public RoundedDrawable setTileModeX(Shader.TileMode tileModeX) {
            if (mTileModeX != tileModeX) {
                mTileModeX = tileModeX;
                mRebuildShader = true;
                invalidateSelf();
            }
            return this;
        }

        public Shader.TileMode getTileModeY() {
            return mTileModeY;
        }

        public RoundedDrawable setTileModeY(Shader.TileMode tileModeY) {
            if (mTileModeY != tileModeY) {
                mTileModeY = tileModeY;
                mRebuildShader = true;
                invalidateSelf();
            }
            return this;
        }

        private static boolean only(int index, boolean[] booleans) {
            for (int i = 0, len = booleans.length; i < len; i++) {
                if (booleans[i] != (i == index)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean any(boolean[] booleans) {
            for (boolean b : booleans) {
                if (b) { return true; }
            }
            return false;
        }

        private static boolean all(boolean[] booleans) {
            for (boolean b : booleans) {
                if (b) { return false; }
            }
            return true;
        }

        public Bitmap toBitmap() {
            return drawableToBitmap(this);
        }
    }

    ////////////
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            Corner.TOP_LEFT, Corner.TOP_RIGHT,
            Corner.BOTTOM_LEFT, Corner.BOTTOM_RIGHT
    })
    public @interface Corner {
        int TOP_LEFT = 0;
        int TOP_RIGHT = 1;
        int BOTTOM_RIGHT = 2;
        int BOTTOM_LEFT = 3;
    }
}