package com.wonders.health.venus.open.user.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

/**
 * class:  HintEditTextView
 * auth:  carrey
 * date: 16-11-16.
 * desc:提示剩余字数的EditTextView
 */

public class HintEditTextView extends RelativeLayout implements TextWatcher {

    private static final int COUNT_STYLE_NUMBER = 0;
    private static final int COUNT_STYLE_WORD = 1;

    private EditText mEditText;
    private TextView mTextView;
    private int mEditText_maxLength = 0;
    private int mEditText_minLines = 0;
    private int mEditText_maxLines = 0;
    private float mEditText_textSize = 0;
    private int mEditText_textColor = 0;

    private int count_style = 0;
    private float count_text_size = 0;
    private int count_text_color = 0;


    public HintEditTextView(Context context) {
        this(context, null);

    }

    public HintEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HintEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_hint_edittext, this, true);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.HintEditTextView);
        mEditText_maxLength = mTypedArray.getInt(R.styleable.HintEditTextView_android_maxLength, 0);
        mEditText_minLines = mTypedArray.getInt(R.styleable.HintEditTextView_android_minLines, 0);
        mEditText_maxLines = mTypedArray.getInt(R.styleable.HintEditTextView_android_minLines, 0);
        mEditText_textSize = mTypedArray.getDimension(R.styleable.HintEditTextView_android_textSize, 0f);
        mEditText_textColor = mTypedArray.getColor(R.styleable.HintEditTextView_android_textColor, 0);

        count_style = mTypedArray.getInt(R.styleable.HintEditTextView_count_text_style, 0);
        count_text_size = mTypedArray.getDimension(R.styleable.HintEditTextView_count_text_size, 0.0f);
        count_text_color = mTypedArray.getColor(R.styleable.HintEditTextView_count_text_color, 0);
        mEditText = (EditText) view.findViewById(R.id.edit);
        mTextView = (TextView) view.findViewById(R.id.text);
//        mTextView.setHint(还可输入+maxLength+字);
        setArgs();

        mEditText.addTextChangedListener(this);


    }

    private void setArgs() {
        //限定最多可输入多少字符
        if (mEditText_maxLength != 0) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mEditText_maxLength)});
            setCount(0);
        }
        if (mEditText_minLines != 0) {
            mEditText.setMinLines(mEditText_minLines);
        }
        if (mEditText_maxLines != 0) {
            mEditText.setMaxLines(mEditText_maxLines);
        }
        if (mEditText_textSize != 0f) {
//            mEditText.setTextSize(mEditText_textSize);
            mEditText.setTextSize(UIUtil.px2sp(mEditText_textSize));
        }
        if (mEditText_textColor != 0) {
            mEditText.setTextColor(mEditText_textColor);
        }

        if (count_text_size != 0f) {
            mTextView.setTextSize(UIUtil.px2sp(count_text_size));
//            mTextView.setTextSize(count_text_size);
        }
        if (count_text_color != 0) {
            mTextView.setTextColor(count_text_color);
        }

    }

    private void setCount(int current) {
        if (count_style == COUNT_STYLE_NUMBER) {
            mTextView.setText(current + "/" + mEditText_maxLength);
        } else {
            mTextView.setText("还可输入" + (mEditText_maxLength - current) + "字");
        }
    }

    public String getContent() {
        return mEditText.getText().toString().trim();
    }

    public void setContent(String content) {
        mEditText.setText(content);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEditText_maxLength != 0) {
            setCount(s.toString().length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
