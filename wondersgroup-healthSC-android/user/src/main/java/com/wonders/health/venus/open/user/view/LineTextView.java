package com.wonders.health.venus.open.user.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class LineTextView extends TextView {

    private boolean mNeedResize = false;
    private int mOffset;
    private boolean isSub = true;

    public LineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isSub){
            if (getLineCount() >= 2) {
                int lineEndIndex1 = getLayout().getLineEnd(0);
                int lineEndIndex2 = getLayout().getLineEnd(1);
//                if(lineEndIndex2 > lineEndIndex1 * 2  - 5){
//                    // 需要重新设置text文本，所以直接return，不要再画了，画的也是不对的
//                    setText(getText().subSequence(0, lineEndIndex1 * 2  - 5));
//                    return;
//                }
                if(lineEndIndex2 > lineEndIndex1 * 2  - 4){
                    // 需要重新设置text文本，所以直接return，不要再画了，画的也是不对的
                    setText(getText().subSequence(0, lineEndIndex1 * 2  - 7)+"...");
                    return;
                }
            }
        }
        super.onDraw(canvas);
    }

    public void subTextView(boolean isSub){
        this.isSub = isSub;
    }
}