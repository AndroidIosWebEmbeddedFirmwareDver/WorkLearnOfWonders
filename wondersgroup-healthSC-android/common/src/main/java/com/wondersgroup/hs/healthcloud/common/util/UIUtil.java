package com.wondersgroup.hs.healthcloud.common.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.view.MenuDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * 用来弹出界面上的一些小提示，处理UI交互的功能类
 * UIUtil
 * chenbo
 * 2015年3月6日 上午11:54:20
 * @version 1.0
 */
public class UIUtil {
    private static final int ID_LOADING_VIEW = 0xfffff0;
    private static final int ID_EMPTY_VIEW = 0xfffff1;
    private static final int ID_ERROR_VIEW = 0xfffff2;

    private static WeakReference<Toast> sToastRef = null;
    private static WeakReference<Dialog> sDialogRef;

    /**
     * 显示输入法
     * showSoftInput
     * @param activity
     * @since 1.0
     */
    public static void showSoftInput(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) 
            imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
    }
    
    /**
     * 隐藏输入法
     * hideSoftInput
     * @param activity
     * @since 1.0
     */
    public static void hideSoftInput(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            if (activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
    }

    /**
     * 显示加载弹窗
     * @param activity
     */
    public static Dialog showProgressBar(Activity activity) {
        return showProgressBar(activity, null);
    }

    /**
     * 显示加载弹窗
     * @param activity
     * @param msg
     */
    public static Dialog showProgressBar(Activity activity, String msg) {
        if (activity == null || activity.isFinishing()) return null;
        hideProgressBar(activity);
        Dialog dialog = new Dialog(activity, R.style.FullScreenDialog);
        dialog.getWindow().setDimAmount(0);
        sDialogRef = new WeakReference<Dialog>(dialog);
        LayoutInflater mLayoutInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = mLayoutInflater.inflate(R.layout.dialog_loading, null);
        // 显示宽度为屏幕的3/5
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView tvMsg = (TextView) layout.findViewById(R.id.txtv_loading_text);

        if (TextUtils.isEmpty(msg)) {
            tvMsg.setText(R.string.loading);
        } else {
            tvMsg.setText(msg);
        }
        dialog.setContentView(layout, params);
        dialog.show();
        return dialog;
    }
    /**
     * 显示加载弹窗 点击屏幕不允许被关闭
     * @param activity
     * @param msg
     */
    public static void showProgressBar(Activity activity, String msg,boolean canceledOnTouchOutside) {
        if (activity == null || activity.isFinishing()) return;
        hideProgressBar(activity);
        Dialog dialog = new Dialog(activity, R.style.FullScreenDialog);
        sDialogRef = new WeakReference<Dialog>(dialog);
        LayoutInflater mLayoutInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = mLayoutInflater.inflate(R.layout.dialog_loading, null);
        // 显示宽度为屏幕的4/5
        LayoutParams params = new LayoutParams(SystemUtil.getScreenWidth() / 2, LayoutParams.WRAP_CONTENT);
        TextView tvMsg = (TextView) layout.findViewById(R.id.txtv_loading_text);
        if (TextUtils.isEmpty(msg)) {
            tvMsg.setText(R.string.loading);
        } else {
            tvMsg.setText(msg);
        }
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.setContentView(layout, params);
        dialog.show();
    }
    /**
     * 隐藏进度条
     * hideProgressBar
     * @param activity
     * @since 1.0
     */
    public static void hideProgressBar(Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        if (sDialogRef != null && sDialogRef.get() != null) {
            try {
                sDialogRef.get().dismiss();
            } catch (Exception e) {
            } finally {
                sDialogRef.clear();
                sDialogRef = null;
            }
        }
    }

    /**
     * 显示加载中提示界面
     * showLoadingView
     * @param parent
     * @since 1.0
     */
    public static void showLoadingView(ViewGroup parent) {
        showLoadingView(parent, "");
    }

    /**
     * 显示加载中提示界面
     * showLoadingView
     * @param parent
     * @param tipStr
     * @since 1.0
     */
    public static void showLoadingView(ViewGroup parent, String tipStr) {
        if (parent == null) return;
        Drawable drawable = parent.getContext().getResources().getDrawable(R.mipmap.ic_page_loading);
        View loadingView = getNoticeView(parent, drawable, tipStr, ID_LOADING_VIEW);
        showLoadingView(parent, loadingView);
    }

    /**
     * 显示加载中提示界面
     * showLoadingView
     * @param parent 父控件
     * @param loadingView 加载中控件
     * @since 1.0
     */
    public static void showLoadingView(ViewGroup parent, View loadingView) {
        removeNoticeView(parent, ID_LOADING_VIEW);
        removeNoticeView(parent, ID_EMPTY_VIEW);
        removeNoticeView(parent, ID_ERROR_VIEW);
        hideChildrenView(parent);
        addNoticeView(parent, loadingView, ID_LOADING_VIEW, null);
    }

    public static void showEmptyView(ViewGroup parent, OnClickListener onClickListener) {
        showEmptyView(parent, "", onClickListener);
    }

    public static void showEmptyView(ViewGroup parent, String emptyStr, OnClickListener onClickListener) {
        showEmptyView(parent, emptyStr, 0, onClickListener);
    }

    public static void showEmptyView(ViewGroup parent, String emptyStr, int resId, OnClickListener onClickListener) {
        View emptyView = getNoticeView(parent, parent.getResources().getDrawable(resId == 0 ? R.mipmap.ic_no_data : resId), emptyStr, ID_EMPTY_VIEW);
        showEmptyView(parent, emptyView, onClickListener);
    }

    /**
     * 显示无数据时候提示界面
     * showEmptyView
     * @param parent 父控件
     * @param emptyView 加载中控件
     * @param onClickListener 控件点击事件
     * @since 1.0
     */
    public static void showEmptyView(ViewGroup parent, View emptyView, OnClickListener onClickListener) {
        if (parent == null) return;
        hideAllNoticeView(parent);
        hideChildrenView(parent);
        addNoticeView(parent, emptyView, ID_EMPTY_VIEW, onClickListener);
    }

    public static void showErrorView(ViewGroup parent, OnClickListener onClickListener) {
        showErrorView(parent, "", onClickListener);
    }

    public static void showErrorView(ViewGroup parent, String emptyStr, OnClickListener onClickListener) {
        showErrorView(parent, emptyStr, 0, onClickListener);
    }

    public static void showErrorView(ViewGroup parent, String emptyStr, int resId, OnClickListener onClickListener) {
        Drawable drawable  = parent.getResources().getDrawable(resId == 0 ? R.mipmap.ic_network_failed : resId);
        View errorView = getNoticeView(parent, drawable, emptyStr, ID_ERROR_VIEW);
        showErrorView(parent, errorView, onClickListener);
    }

    /**
     * 显示加载失败时的提示界面
     * showErrorView
     * @param parent 父控件
     * @param errorView 加载失败控件
     * @param onClickListener 控件点击事件
     * @since 1.0
     */
    public static void showErrorView(ViewGroup parent, View errorView, OnClickListener onClickListener) {
        if (parent == null) return;
        hideAllNoticeView(parent);
        hideChildrenView(parent);
        addNoticeView(parent, errorView, ID_ERROR_VIEW, onClickListener);
    }

    /**
     * 隐藏所有的提示控件
     * hideAllNoticeView
     * @param parent 父控件
     * @since 1.0
     */
    public static void hideAllNoticeView(ViewGroup parent) {
        if (parent == null) return;
        boolean result = false;
        result |= removeNoticeView(parent, ID_LOADING_VIEW);
        result |= removeNoticeView(parent, ID_EMPTY_VIEW);
        result |= removeNoticeView(parent, ID_ERROR_VIEW);
        if (result) {
            showChildrenView(parent);
        }
    }

    // 隐藏parent下所有的子view
    public static void hideChildrenView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.GONE) {
                childAt.setVisibility(View.GONE);
            }
        }
    }

    // 显示parent下所有的子view
    public static void showChildrenView(final ViewGroup parent) {
        boolean hasViewChange = false;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.VISIBLE) {
                childAt.setVisibility(View.VISIBLE);
                hasViewChange = true;
            }
        }
        if (hasViewChange) {
            parent.setAnimationCacheEnabled(false);
            Animation showAnim = AnimationUtils.loadAnimation(parent.getContext(), android.R.anim.fade_in);
            showAnim.setStartTime(AnimationUtils.currentAnimationTimeMillis());
            showAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    parent.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            parent.clearAnimation();
            parent.startAnimation(showAnim);
        }
    }

    // 显示parent下所有的子view,  不带动画
    public static void showChildrenViewWithoutAnim(final ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.VISIBLE) {
                childAt.setVisibility(View.VISIBLE);
            }
        }
    }

    // 使parent下所有的子view不可见
    public static void invisibleChildrenView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.INVISIBLE) {
                childAt.setVisibility(View.INVISIBLE);
            }
        }
    }

    // 获取默认的加载界面
    private static View getNoticeView(ViewGroup parent, Drawable srcDrawable, String tipStr, int tag) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice, null);
        TextView tipText = (TextView) view.findViewById(R.id.text_notice_tip);
        TextView extText = (TextView) view.findViewById(R.id.text_notice_ext);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_notice);
        imageView.setImageDrawable(srcDrawable);
        if (TextUtils.isEmpty(tipStr)) {
            if (tag == ID_EMPTY_VIEW) {
                tipText.setText(R.string.notice_no_data);
                extText.setVisibility(View.INVISIBLE);
            } else if (tag == ID_ERROR_VIEW) {
                tipText.setText(R.string.notice_load_error);
                extText.setVisibility(View.VISIBLE);
            } else {
                Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.progress_page_loading);
                imageView.setAnimation(animation);
                animation.start();
                tipText.setText(R.string.loading);
                extText.setVisibility(View.INVISIBLE);
            }
        } else {
            tipText.setText(tipStr);
            extText.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    // 在parent中添加子view，并且监听子view点击事件
    private static void addNoticeView(ViewGroup parent, View child, int tag, OnClickListener listener) {
        hideAllNoticeView(parent);
        hideChildrenView(parent);
        child.setTag(tag + parent.hashCode());
        parent.addView(child, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (listener != null) {
            child.setOnClickListener(listener);
        } else {
            // 防止key事件穿透
            child.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    private static boolean removeNoticeView(final ViewGroup parent, int tag) {
        View view = parent.findViewWithTag(tag + parent.hashCode());

        if (view != null) {
            parent.removeView(view);
            view.setTag(null);
            return true;
        }
        return false;
    }


    /**
     * 弹出短时间的吐司
     * toastShort
     * @param context
     * @param msg
     * @since 1.0
     */
    public static void toastShort(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_SHORT);
    }
    
    /**
     * 弹出短时间的吐司
     * toastShort
     * @param context
     * @param resId
     * @since 1.0
     */
    public static void toastShort(Context context, int resId) {
        toast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }
    
    /**
     * 弹出长时间的吐司
     * toastLong
     * @param context
     * @param msg
     * @since 1.0
     */
    public static void toastLong(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_LONG);
    }
    
    /**
     * 弹出长时间的吐司
     * toastLong
     * @param context
     * @param resId
     * @since 1.0
     */
    public static void toastLong(Context context, int resId) {
        toast(context, context.getString(resId), Toast.LENGTH_LONG);
    }
    
    private static void toast(Context context, String msg, int duration) {
        if (TextUtils.isEmpty(msg)) return;
        Toast t = null;
        if (sToastRef == null || sToastRef.get() == null) {
            t = Toast.makeText(context, msg, duration);
            sToastRef = new WeakReference<Toast>(t);
        } else {
            t = sToastRef.get();
            t.setText(msg);
            t.setDuration(duration);
        }
        t.show();
    }
    
    /**
     * 弹出confirm对话框
     * showConfirm
     * @param context
     * @param msg 内容
     * @param okListener
     * @since 1.0
     */
    public static Dialog showConfirm(Context context, String msg, final OnClickListener okListener) {
        return showConfirm(context, "", msg, null, okListener, null, null);
    }

    /**
     * 弹出没有title的confirm对话框
     * showConfirm1
     * @param context
     * @param msg 内容
     * @param okListener
     * @since 1.0
     */
    public static Dialog showConfirm1(Context context, String msg, final OnClickListener okListener) {
        return showConfirm(context, null, msg, null, okListener, null, null);
    }
    
    /**
     * 弹出confirm对话框
     * showConfirm
     * @param context
     * @param title
     * @param msg
     * @param okStr
     * @param okListener
     * @since 1.0
     */
    public static Dialog showConfirm(Context context, String title, String msg, String okStr, final OnClickListener okListener) {
        return showConfirm(context, title, msg, okStr, okListener, null, null);
    }
    
    
    /**
     * 弹出confirm对话框
     * showConfirm
     * @param context
     * @param title 标题
     * @param msg 内容
     * @param okListener
     * @since 1.0
     */
    public static Dialog showConfirm(Context context, String title, String msg, final OnClickListener okListener) {
        return showConfirm(context, title, msg, null, okListener, null, null);
    }
    
    /**
     * 弹出confirm对话框
     * showConfirm
     * @param context
     * @param title 标题
     * @param msg 内容
     * @param okStr 确定按钮字符串
     * @param okListener 确定点击事件监听
     * @param cancelStr 取消按钮字符串
     * @param cancelListener 取消点击事件监听
     * @since 1.0
     */
    public static Dialog showConfirm(final Context context, String title, String msg, String okStr,
            final OnClickListener okListener, String cancelStr, final OnClickListener cancelListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_confirm, null);
        final Dialog dialog = showAlert(context, view);
        TextView titleText = (TextView) view.findViewById(R.id.tv_confirm_title);
        TextView msgText = (TextView) view.findViewById(R.id.tv_confirm_msg);
        Button leftBtn = (Button) view.findViewById(R.id.btn_confirm_left);
        Button rightBtn = (Button) view.findViewById(R.id.btn_confirm_right);
        titleText.setText("".equals(title) ? context.getString(R.string.notice) : title);
        titleText.setVisibility(title == null ? View.GONE : View.VISIBLE);
        msgText.setText(msg);
        leftBtn.setText(TextUtils.isEmpty(cancelStr) ? context.getString(R.string.cancel) : cancelStr);
        rightBtn.setText(TextUtils.isEmpty(okStr) ? context.getString(R.string.ok) : okStr);
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (cancelListener != null) {
                    cancelListener.onClick(view);
                }
            }
        });
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (okListener != null) {
                    okListener.onClick(view);
                }
            }
        });
        return dialog;
    }

    /**
     * 弹出自定义布局的confirm对话框
     * showConfirm
     * @param context
     * @param title 标题
     * @param content 对话框自定义布局
     * @param okListener
     * @param cancelStr 取消按钮字符串
     * @param cancelListener 取消点击事件监听
     * @since 1.0
     */
    public static Dialog showConfirm(final Context context, String title, View content, String okStr,
                                     final OnClickListener okListener, String cancelStr, final OnClickListener cancelListener) {
        final Dialog dialog = showAlert(context, content);
        TextView titleText = (TextView) content.findViewById(R.id.tv_confirm_title);
        Button leftBtn = (Button) content.findViewById(R.id.btn_confirm_left);
        Button rightBtn = (Button) content.findViewById(R.id.btn_confirm_right);
        titleText.setText(TextUtils.isEmpty(title) ? context.getString(R.string.notice) : title);

        setTouchEffect(leftBtn);
        setTouchEffect(rightBtn);
        leftBtn.setText(TextUtils.isEmpty(cancelStr) ? context.getString(R.string.cancel) : cancelStr);
        rightBtn.setText(TextUtils.isEmpty(okStr) ? context.getString(R.string.ok) : okStr);
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (cancelListener != null) {
                    cancelListener.onClick(view);
                }
            }
        });
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (okListener != null) {
                    okListener.onClick(view);
                }
            }
        });
        return dialog;
    }
    /**
     * 显示自定义弹窗
     * @param context
     * @param view
     * @return
     */
    public static Dialog showAlert(final Context context, View view) {
        return showAlert(context, view, 0);
    }

    public static Dialog showAlert(final Context context, View view, int width) {
        Dialog dialog = new Dialog(context, R.style.FullScreenDialog);
        // 显示宽度为屏幕的5/6
        if(width == 0){
            width = SystemUtil.getScreenWidth() * 5 / 6;
        }
        LayoutParams params = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        if (context instanceof Activity) {// 判断状态
            Activity activity = (Activity) context;
            if (!activity.isFinishing()) {
                dialog.show();
            }
        }
        return dialog;
    }

    public static Dialog showAlert(final Context context, String msg, OnClickListener listener) {
        return showAlert(context, "提示", msg, "确定", listener);
    }

    public static Dialog showAlert(final Context context, String title, String msg) {
        return showAlert(context, title, msg, null, null);
    }
    
    public static Dialog showAlert(final Context context, String title, String msg, String okStr,
            final OnClickListener okListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_confirm, null);
        final Dialog dialog = showAlert(context, view);
        TextView titleText = (TextView) view.findViewById(R.id.tv_confirm_title);
        TextView msgText = (TextView) view.findViewById(R.id.tv_confirm_msg);
        Button leftBtn = (Button) view.findViewById(R.id.btn_confirm_left);
        Button rightBtn = (Button) view.findViewById(R.id.btn_confirm_right);
        titleText.setText(TextUtils.isEmpty(title) ? context.getString(R.string.notice) : title);
        msgText.setText(msg);
        setTouchEffect(rightBtn);
        //leftBtn.setVisibility(View.GONE);
        rightBtn.setText(TextUtils.isEmpty(okStr) ? context.getString(R.string.ok) : okStr);
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (okListener != null) {
                    okListener.onClick(view);
                }
            }
        });
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog showWarningAlert(final Context context, String msg,String rbText){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_confirm, null);
        final Dialog dialog = showAlert(context, view);
        TextView titleText = (TextView) view.findViewById(R.id.tv_confirm_title);
        TextView msgText = (TextView) view.findViewById(R.id.tv_confirm_msg);
        Button leftBtn = (Button) view.findViewById(R.id.btn_confirm_left);
        Button rightBtn = (Button) view.findViewById(R.id.btn_confirm_right);
        titleText.setText("温馨提示");
        msgText.setText(msg);
        setTouchEffect(rightBtn);
        if(TextUtils.isEmpty(rbText)){
            rightBtn.setText(context.getString(R.string.ok));
        }else{
            rightBtn.setText(rbText);
        }
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        leftBtn.setVisibility(View.GONE);
        return dialog;
    }

    public static Dialog showWarningAlert(final Context context, String msg) {
        return showWarningAlert(context,msg,null);
    }

    /**
     * 显示一个底部弹出的menu
     * @param activity
     * @param contentView
     * @return
     */
    public static Dialog showBottomMenu(Activity activity, View contentView) {
        return showBottomMenu(activity,contentView, 0, false);
    }

    public static Dialog showBottomMenu(Activity activity, View contentView, int height, boolean showKeypad){
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        MenuDialog dialog = new MenuDialog(activity, showKeypad ? R.style.menuDialog_keypad : R.style.menuDialog);
        dialog.setContentView(contentView, params);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = SystemUtil.getScreenWidth();
        if (height != 0) {
            lp.height = height;
        }
        lp.x = 0;
        lp.y = 0;
        lp.verticalMargin = 0;
        lp.horizontalMargin = 0;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        dialog.show();
        return dialog;
    }


    /**
     * 显示一个右上角弹出的menu
     * @param activity
     * @param contentView
     * @return
     */
    public static Dialog showTopRightMenu(Activity activity, View contentView) {
        return showTopRightMenu(activity, contentView, 0, 0, 0);
    }
    /**
     * 显示一个右上角弹出的menu
     * @param activity
     * @param contentView
     * @param width
     * @param x
     * @param y
     * @return
     */
    public static Dialog showTopRightMenu(Activity activity, View contentView, int width, int x, int y) {
        Dialog dialog = new Dialog(activity, R.style.FullScreenDialog);
        dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width == 0 ? UIUtil.dip2px(120) : width;
        lp.height = LayoutParams.WRAP_CONTENT;
        lp.x = x == 0 ? UIUtil.dip2px(5) : x;
        lp.y = y == 0 ? UIUtil.dip2px(48) : y;
        lp.verticalMargin = 0;
        lp.horizontalMargin = 0;
        lp.dimAmount = 0.15f;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.topright_dialog);
        window.setGravity(Gravity.TOP|Gravity.RIGHT); // 此处可以设置dialog显示的位置
        dialog.show();
        return dialog;
    }

    /**
     * dip转换为px
     * dip2px
     * @param dpValue
     * @return
     * @since 1.0
     */
    
    public static int dip2px(int dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

   /**
    * px转换为dip
    * px2dip
    * @param pxValue
    * @return
    * @since 1.0
    */
    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    /**
     * sp转为px
     * sp2px
     * @param value
     * @return
     * @since 1.0
     */
    public static int sp2px(float value) {
        Resources r = Resources.getSystem();
        float spvalue = value * r.getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }
    
    /**
     * px转为sp
     * px2sp
     * @param pxValue
     * @return
     * @since 1.0
     */
    public static int px2sp(float pxValue) {
        Resources r = Resources.getSystem();
        float scale = r.getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }
    
    /**
     * 测量这个view
     * 最后通过getMeasuredWidth()获取宽度和高度.
     * @param view 要测量的view
     * @return 测量过的view
     */
    public static void measureView(View view) {
        LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }
    /**
     * 计算控件宽度 measureWidth
     * 
     * measureWidth
     * @param view
     * @return
     * @since 1.0
     */
    public static int measureWidth(View view) {
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getMeasuredWidth();
        return width;
    }

    /**
     * 计算控件高度 measureHeight
     * 
     * @param view
     * @return
     * @since 1.0
     */
    public static int measureHeight(View view) {
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }
    /**
     * 判断是否触摸在view上面
     * @param view
     * @param fX x坐标
     * @param fY y坐标
     * @return
     * @since 1.0
     */
    public static boolean isViewTouched(View view, float fX, float fY) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);

        int nStartY = location[1];
        int nEndY = location[1] + view.getHeight();

        int nStartX = location[0];
        int nEndX = location[0] + view.getWidth();

        if ((fY >= nStartY && fY < nEndY) && (fX > nStartX && fX < nEndX)) {
            return true;
        }
        return false;
    }
    
    /**
     * 设置touch效果
     * 
     * @return
     */
    public static void setTouchEffect(View view) {
        if (view == null)
            return;
        view.setOnTouchListener(buildLayTouchListener(R.color.tc3, view.getContext()));
    }

    /**
     * 设置touch效果
     * 
     * @return
     */
    public static void setTouchEffect(View view, int colorRes) {
        if (view == null)
            return;
        view.setOnTouchListener(buildLayTouchListener(colorRes, view.getContext()));
    }

    /**
     * 自定义按钮点击效果
     * 
     * @param colorRes
     * @param context
     * @return
     */
    public static OnTouchListener buildLayTouchListener(final int colorRes, final Context context) {
        return new OnTouchListener() {

            @Override
            public boolean onTouch(final View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setSelected(view, true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        setSelected(view, false);
                        break;
                    default:
                        break;
                }
                return false;
            }

            private void setSelected(final View view, boolean isSelected) {
                Drawable drawable = null;
                if (view instanceof ImageView) {
                    drawable = ((ImageView) view).getDrawable();
                } else if (view instanceof TextView) {
                    Drawable[] drawables = ((TextView) view).getCompoundDrawables();
                    for (int i = 0; i < drawables.length; i++) {
                        if (drawables[i] != null) {
                            drawable = drawables[i];
                            break;
                        }
                    }
                }
                if (drawable == null) {
                    drawable = view.getBackground();
                }
                if (drawable == null) {
                    return;
                }
                if (isSelected && view.isSelected()) {
                    drawable.setColorFilter(context.getResources().getColor(colorRes), PorterDuff.Mode.MULTIPLY);
                    view.invalidate();
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setSelected(view, false);
                        }
                    }, 120);
                } else {
                    drawable.clearColorFilter();
                    view.invalidate();
                }
            }
        };
    }

    /**
     * 计算出该TextView中文字的长度(像素) getTextViewLength
     * 
     * @param textView
     * @param text
     * @return
     * @since 3.6
     */
    public static float getTextViewWidth(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

    //获取焦点时hint消失
    public static void showHint(EditText ... view){
        List<EditText> list=new ArrayList<>();
        for(EditText edit:view){
            list.add(edit);
        }

        for ( int i = 0; i < list.size(); i++) {
            final int j = i;
            //EditText 获得焦点时hint消失，失去焦点时hint显示
            list.get(j).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        ((TextView) v).setHint("必填");
                    } else {
                        ((TextView) v).setHint("");
                    }

                }
            });
        }
    }

    /**
     * 设置圆点数量提示
     *
     * @param numLabel
     */
    public static void setDotNum(TextView numLabel, int num) {
        if (numLabel == null) {
            return;
        }

        if (num > 99) {
            numLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            numLabel.setVisibility(View.VISIBLE);
            numLabel.setBackgroundResource(R.drawable.dot_stroke_shape);
//            numLabel.setText("99+");
            numLabel.setText("...");
        } else if (num > 9) {
            numLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            numLabel.setVisibility(View.VISIBLE);
            numLabel.setBackgroundResource(R.drawable.dot_stroke_shape);
            numLabel.setText(String.valueOf(num));
        } else if (num > 0) {
            numLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            numLabel.setVisibility(View.VISIBLE);
            numLabel.setBackgroundResource(R.drawable.dot_stroke_shape);
            numLabel.setText(String.valueOf(num));
        } else {
            numLabel.setVisibility(View.GONE);
        }
        numLabel.postInvalidate();
    }

}
