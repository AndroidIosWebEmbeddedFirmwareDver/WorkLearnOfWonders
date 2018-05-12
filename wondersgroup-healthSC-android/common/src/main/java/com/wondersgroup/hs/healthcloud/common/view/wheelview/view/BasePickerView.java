package com.wondersgroup.hs.healthcloud.common.view.wheelview.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;


/**
 * Created by Sai on 15/11/22.
 * 精仿iOSPickerViewController控件
 */
public class BasePickerView {
    private Activity context;
    protected ViewGroup rootView;
    protected ViewGroup contentContainer;
    private Dialog dialog;

    public BasePickerView(Activity context){
        this.context = context;

        initViews();
        initEvents();
    }

    protected void initViews(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, null);
        contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
    }

    protected void initEvents() {
    }
    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (rootView.getParent() != null) {
            ((ViewGroup)(rootView.getParent())).removeView(rootView);
        }
        dialog = UIUtil.showBottomMenu(context, rootView);
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public View findViewById(int id){
        return contentContainer.findViewById(id);
    }
}
