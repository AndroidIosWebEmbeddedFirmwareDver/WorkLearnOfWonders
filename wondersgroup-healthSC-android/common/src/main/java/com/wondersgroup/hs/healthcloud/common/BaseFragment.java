package com.wondersgroup.hs.healthcloud.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 所有fragment的基类
 * BaseFragment
 * chenbo
 * 2015年3月10日 下午3:55:11
 *
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected CommonActivity mBaseActivity;
    protected Unbinder unbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = (CommonActivity) getActivity();
    }


    public void startActivity(Intent intent, boolean useDefaultFlag) {
        if (useDefaultFlag) {
            super.startActivity(intent);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.startActivity(intent);
    }

    public void startActivityWithNewTask(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        super.startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = this.onCreateView(inflater, savedInstanceState);
            this.initViews();
            this.initData(savedInstanceState);
        }
        if (useButterKnife()) {
            unbinder = ButterKnife.bind(this,mRootView);
        }
        return mRootView;
    }

    protected boolean useButterKnife() {
        return true;
    }

    /**
     * 创建View
     * onCreateView
     *
     * @param inflater
     * @return
     * @since 1.0
     */
    protected abstract View onCreateView(LayoutInflater inflater, Bundle savedInstanceState);

    /**
     * 初始化view
     * initViews
     *
     * @since 1.0
     */
    protected abstract void initViews();

    /**
     * 初始化数据
     * initData
     *
     * @since 1.0
     */
    protected abstract void initData(Bundle savedInstanceState);

    protected View findViewById(int id) {
        return mRootView.findViewById(id);
    }

    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
