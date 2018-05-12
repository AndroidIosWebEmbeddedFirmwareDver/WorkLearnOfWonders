package com.wondersgroup.hs.healthcloud.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wondersgroup.hs.healthcloud.common.R;


/**
 * chenbo add
 * 可以动态设置高宽比的ImageView
 */
public class RatioImageView extends ImageView {
    private float mRatio = 2;


    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mRatio = a.getFloat(R.styleable.RatioImageView_ratio, 2);  
        a.recycle();
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mRatio = a.getFloat(R.styleable.RatioImageView_ratio, 2);  
        a.recycle();
    }
    
    public void setRatio(float ratio) {
        mRatio = ratio;
        requestLayout();
    }
    

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        if (mode == MeasureSpec.EXACTLY || Math.abs(mRatio) < 0.1f) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = (int) (width / mRatio);
        }
        setMeasuredDimension(width, height);
    }
    
}
