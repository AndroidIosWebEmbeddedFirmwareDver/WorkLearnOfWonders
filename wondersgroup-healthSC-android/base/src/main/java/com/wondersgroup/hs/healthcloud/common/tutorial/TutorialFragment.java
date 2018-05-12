package com.wondersgroup.hs.healthcloud.common.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.hs.healthcloud.base.R;


/**
 * Created by angelo on 2015/6/16.
 */
public class TutorialFragment extends Fragment {
    final static String STYLE = "style";
    private View mBackgroundView;
    private TextView mTextHead;
    private TextView mTextContent;
    private ImageView mImageView;

    private int mColor = -1;
    private int mResId = -1;
    private int mSrcResId = -1;
    private String mHeadText;
    private String mContentText;
    private int mHeadTextColor = -1;
    private int mContentTextColor = -1;
    private int mHeadTextSize = -1;
    private int mContentTextSize = -1;


    public static TutorialFragment newInstance(int style) {
        TutorialFragment tf = new TutorialFragment();
        Bundle args = new Bundle();
        args.putInt(STYLE, style);
        tf.setArguments(args);
        return tf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        switch (getArguments().getInt(STYLE, 0)) {
            case BaseTutorialActivity.IMAGE_TOP:
                rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);
                initViews(rootView);
                break;
            case BaseTutorialActivity.IMAGE_CENTER:
                rootView = inflater.inflate(R.layout.fragment_tutorial_2, container, false);
                initViews(rootView);
                break;
            case BaseTutorialActivity.IMAGE_BOTTOM:
                rootView = inflater.inflate(R.layout.fragment_tutorial_3, container, false);
                initViews(rootView);
                break;
            default:
                break;
        }

        return rootView;
    }

    public void initViews(View rootView) {
        mBackgroundView = rootView.findViewById(R.id.tutorial_container);
        mTextHead = (TextView) rootView.findViewById(R.id.heading);
        mTextContent = (TextView) rootView.findViewById(R.id.content);
        mImageView = (ImageView) rootView.findViewById(R.id.welcome_img);
        if (mColor != -1 && mColor != 0) {
            mBackgroundView.setBackgroundColor(getResources().getColor(mColor));
        }
        if (mResId != -1 && mColor != 0) {
            mBackgroundView.setBackgroundResource(mResId);
        }
        if (TextUtils.isEmpty(mHeadText)) {
            mTextHead.setVisibility(View.GONE);
        } else {
            mTextHead.setText(mHeadText);
        }
        if (TextUtils.isEmpty(mContentText)) {
            mTextContent.setVisibility(View.GONE);
        } else {
            mTextContent.setText(mContentText);
        }
        if (mSrcResId != -1 && mSrcResId != 0) {
            mImageView.setImageResource(mSrcResId);
        }
        if (mHeadTextColor != -1 && mHeadTextColor != 0) {
            mTextHead.setTextColor(getResources().getColor(mHeadTextColor));
        }
        if (mContentTextColor != -1 && mContentTextColor != 0) {
            mTextContent.setTextColor(getResources().getColor(mContentTextColor));
        }

        if (mHeadTextSize != -1 && mHeadTextSize != 0) {
            mTextHead.setTextSize(TypedValue.COMPLEX_UNIT_SP, mHeadTextSize);
        }
        if (mContentTextSize != -1 && mContentTextSize != 0) {
            mTextContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mContentTextSize);
        }
    }

    public void setBackgroundColor(int color) {
        this.mColor = color;
    }

    public void setBackgroundResource(int resId) {
        this.mResId = resId;
    }

    public void setImageResource(int resId) {
        this.mSrcResId = resId;
    }

    public void setHeadText(String headText) {
        this.mHeadText = headText;
    }

    public void setContentText(String contentText) {
        this.mContentText = contentText;
    }

    public void setHeadTextColor(int headTextColor) {
        mHeadTextColor = headTextColor;
    }

    public void setContentTextColor(int contentTextColor) {
        mContentTextColor = contentTextColor;
    }

    public void setHeadTextSize(int headTextSize) {
        mHeadTextSize = headTextSize;
    }

    public void setContentTextSize(int contentTextSize) {
        mContentTextSize = contentTextSize;
    }

}