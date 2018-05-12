package com.wondersgroup.hs.healthcloud.common.logic;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.CommonActivity;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.List;

import static com.wondersgroup.hs.healthcloud.common.util.BaseConstant.TYPE_NEXT;

/**
 * 带UI逻辑的回调
 * FinalResponseCallback
 * chenbo
 * 2015年3月13日 下午12:56:40
 * @version 1.0
 */
public class FinalResponseCallback<T> extends ResponseCallback<T> {

    private BaseFragment mFragment;
    private CommonActivity mActivity;
    private ViewGroup mParent;

    private boolean mIsEmpty;
    private int mLoadingType;

    public FinalResponseCallback(BaseFragment fragment) {
        this(fragment, BaseConstant.TYPE_INIT);
    }

    public FinalResponseCallback(CommonActivity activity) {
        this(activity, BaseConstant.TYPE_INIT);
    }

    public FinalResponseCallback(ViewGroup parent) {
        this(parent, BaseConstant.TYPE_INIT);
    }

    public FinalResponseCallback(BaseFragment fragment, int loadingType) {
        super();
        mFragment = fragment;
        mLoadingType = loadingType;
        init();
    }

    public FinalResponseCallback(CommonActivity activity, int loadingType) {
        super();
        mActivity = activity;
        mLoadingType = loadingType;
        init();
    }


    public FinalResponseCallback() {
        super();
    }
    public FinalResponseCallback(ViewGroup parent, int loadingType) {
        super();
        mParent = parent;
        mLoadingType = loadingType;
        init();
    }


    private void init() {
        if (mParent == null) {
            if (mActivity != null) {
                try {
                    mParent = (ViewGroup) mActivity.getContentView();
                } catch (Exception e) {
                }
            } else if (mFragment != null) {
                try {
                    mParent = (ViewGroup) mFragment.getRootView();
                } catch (Exception e) {
                }
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mLoadingType == BaseConstant.TYPE_INIT) {
            init();
            UIUtil.showLoadingView(mParent, getLoadingStr());
        }
    }

    @Override
    public void onFailure(Exception error) {
        super.onFailure(error);
        String errorStr = getErrorStr();
        int errorCode = 0;
        if (error instanceof HttpException) {
            errorStr = error.getMessage();
            errorCode = ((HttpException) error).getExceptionCode();
        }
        if (mLoadingType == BaseConstant.TYPE_INIT) {
            init();
            UIUtil.showErrorView(mParent, errorStr, getErrorResId(errorCode), new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onReload();
                }
            });
        }
    }

    @Override
    public void onSuccess(T t) {
        super.onSuccess(t);
        if (mLoadingType != TYPE_NEXT) {
            init();
            UIUtil.hideAllNoticeView(mParent);
        }
        mIsEmpty = false;
    }

    @Override
    public void onSuccess(List<T> t) {
        super.onSuccess(t);
        if (mLoadingType != TYPE_NEXT) {
            init();
            UIUtil.hideAllNoticeView(mParent);
        }
        mIsEmpty = false;
    }

    /**
     * 重新加载
     * onReload
     * @since 1.0
     */
    public void onReload() {

    }

    /**
     * 设置返回的数据为空时候的显示页面
     * setIsEmpty
     * @param isEmpty
     * @since 1.0
     */
    public void setIsEmpty(boolean isEmpty) {
        setIsEmpty(isEmpty, "");
    }

    /**
     * 设置返回的数据为空时候的显示页面
     * setIsEmpty
     * @param isEmpty
     * @param emptyStr 设置为空时候，提示的字符串
     * @since 1.0
     */
    public void setIsEmpty(boolean isEmpty, String emptyStr) {
        mIsEmpty = isEmpty;
        if (mIsEmpty) {
            init();
            UIUtil.showEmptyView(mParent, emptyStr, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onReload();
                }
            });
        }
    }

    /**
     * 设置返回的数据为空时候的显示页面
     * setIsEmpty
     * @param isEmpty
     * @param emptyStr 设置为空时候，提示的字符串
     * @since 1.0
     */
    public void setIsEmpty(boolean isEmpty, String emptyStr, int resId) {
        mIsEmpty = isEmpty;
        if (mIsEmpty) {
            init();
            UIUtil.showEmptyView(mParent, emptyStr, resId, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onReload();
                }
            });
        }
    }


    /**
     * 设置返回的数据为空时候的显示页面
     * @param isEmpty
     * @param emptyView
     */
    public void setIsEmpty(boolean isEmpty, View emptyView) {
        mIsEmpty = isEmpty;
        if (mIsEmpty) {
            init();
            UIUtil.showEmptyView(mParent, emptyView, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onReload();
                }
            });
        }
    }

    @Override
    public boolean isShowNotice() {
        return false;
    }

    /**
     * 设置加载失败的时候，提示的字符串
     * getErrorStr
     * @since 1.0
     */
    public String getErrorStr() {
        return "";
    }

    public int getErrorResId(int errCode) {
        return 0;
    }

    /**
     * 设置加载中的字符串
     * @since 1.0
     */
    public String getLoadingStr() {
        return "";
    }

}
