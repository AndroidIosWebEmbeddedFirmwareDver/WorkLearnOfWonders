package com.wonders.health.venus.open.user.component.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AppConfig;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wonders.health.venus.open.user.logic.SignRequest;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.http.CallProxy;
import com.wondersgroup.hs.healthcloud.common.logic.BaseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;
import com.wondersgroup.hs.healthcloud.common.util.NetworkUtil;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.util.UmengClickAgent;
import com.wondersgroup.hs.healthcloud.common.view.NumberProgressBar;

import java.io.File;

/**
 * Created by sujieyi on 2016/8/12.
 */
public class VersionUpdateManager {
    private OnDialogDismissListener mListener;
    private Activity mActivity;
    private NotificationManager mManager;
    private HttpTools mHttpTools;
    private boolean mIsClickPositive;

    NumberProgressBar mProgressBar;
    Dialog mDialog;
    Button mButton;

    private CallProxy mCallProxy;

    public void setListener(OnDialogDismissListener listener) {
        this.mListener = listener;
    }

    public VersionUpdateManager(Activity activity) {
        this.mActivity = activity;
        this.mManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        this.mHttpTools = new HttpTools();
    }

    public interface OnDialogDismissListener {
        void dismiss();
    }

    public void ShowUpdateDialog(AppConfig.AppUpdate update, boolean isHandUpdate) {
        if (update == null) return;
        boolean hasUpdate = update.hasUpdate;
        if (hasUpdate && hasNewVersion(SystemUtil.getVersionName(mActivity), update.lastVersion)) {
            final String url1 = update.androidUrl;
            final String newVersion = update.lastVersion;
            final String packageSize = update.packageSize;
            final String message = update.updateMsg;
            final boolean isForceUpdate = update.forceUpdate;
            ((BaseApp) BaseApp.getApp()).mCurrentAppVersion = newVersion;

            View view = View.inflate(mActivity, R.layout.dialog_version_update, null);
            final Dialog dialog = new Dialog(mActivity, R.style.FullScreenDialog);
            int width = SystemUtil.getScreenWidth() * 12 / 15;

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(view, params);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
            TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
            TextView tv_ok = (TextView) view.findViewById(R.id.tv_update_ok);
            TextView tv_no = (TextView) view.findViewById(R.id.tv_update_no);
            View diUpdate = view.findViewById(R.id.di_update);
            RelativeLayout rl_update_cancel = (RelativeLayout) view.findViewById(R.id.rl_update_cancel);
            UIUtil.setTouchEffect(tv_ok);
            tv_version.setText(getVersionInfo(SystemUtil.getVersionName(mActivity),newVersion));
            tv_message.setText(message);


            if (!mActivity.isFinishing()) {
                dialog.show();
            }
            if (isForceUpdate) {// 是否强制升级
                tv_no.setVisibility(View.GONE);
                rl_update_cancel.setVisibility(View.GONE);
                diUpdate.setVisibility(View.GONE);
                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UmengClickAgent.onEvent(mActivity, "JkyBZUpdateSubmit");
                        if (!NetworkUtil.isNetworkAvailable(mActivity)) {
                            UIUtil.toastShort(mActivity, R.string.notice_network_error);
                            return;
                        }
                        if (mIsClickPositive) {
                            if (((BaseApp) BaseApp.getApp()).mIsFinishDownload) {
                                startInstallNewVersion(url1);
                            } else {
                                UIUtil.toastShort(mActivity, R.string.version_downloading);
                            }
                        } else {
                            mIsClickPositive = true;
                            downNewVersion(dialog, packageSize, url1, newVersion, isForceUpdate);
                        }
                    }
                });
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            mActivity.finish();
                            System.exit(0);
                        }
                        return false;
                    }
                });

            } else {
                if (!Boolean.parseBoolean(PrefUtil.getString(mActivity, newVersion, "false")) || isHandUpdate) {
                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            downNewVersion(dialog, packageSize, url1, newVersion, isForceUpdate);
                            UmengClickAgent.onEvent(mActivity, "JkyBZUpdateSubmit");
                        }
                    });
                    rl_update_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            UmengClickAgent.onEvent(mActivity, "JkyBZUpdateCancel");
                        }
                    });
                    tv_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            UmengClickAgent.onEvent(mActivity, "JkyBZUpdateCancel");
                        }
                    });
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (mListener != null) {
                                mListener.dismiss();
                            }
                        }
                    });
                }

            }

        } else {
            if (isHandUpdate) {
                UIUtil.toastShort(mActivity, R.string.no_new_version);
            }

        }

    }

    /**
     * 版本比较
     *
     * @param v_local  客户端版本
     * @param v_server 服务端版本
     * @return 市场是否有新版本
     */


    public static boolean hasNewVersion(String v_local, String v_server) {
        if (TextUtils.isEmpty(v_local) || TextUtils.isEmpty(v_server)) {
            return false;
        } else {
            String[] aVsArr = v_local.split("\\.");// . 需要转换
            String[] bVsArr = v_server.split("\\.");// . 需要转换

            int subVsLength = aVsArr.length < bVsArr.length ? aVsArr.length : bVsArr.length;// 子版本号长度

            for (int i = 0; i < subVsLength; i++) {
                if (Integer.parseInt(bVsArr[i]) == Integer.parseInt(aVsArr[i])) {
                    continue;
                }
                if (Integer.parseInt(bVsArr[i]) < Integer.parseInt(aVsArr[i])) {
                    return false;
                }
                if (Integer.parseInt(bVsArr[i]) > Integer.parseInt(aVsArr[i])) {
                    return true;
                }
            }
            if (v_server.startsWith(v_local) && v_server.length() > v_local.length()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 启动安装新版本
     * startInstallNewVersion
     *
     * @since 1.0
     */
    public void startInstallNewVersion(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + getLocalDownloadUrl(url)), "application/vnd.android.package-archive");
        mActivity.startActivity(intent);
    }

    /**
     * 根据网络环境下载最新版本
     * downNewVersion
     *
     * @param dialog
     * @param packageSize
     * @param url1
     * @param newVersion
     * @param isForceUpdate
     * @since 1.0
     */
    private void downNewVersion(final DialogInterface dialog, final String packageSize, final String url1,
                                final String newVersion, final boolean isForceUpdate) {
        if (NetworkUtil.isWifiConnected(mActivity)) {
            if (mListener != null) {
                mListener.dismiss();
            }
            executeDownload(url1, newVersion, isForceUpdate);

        } else if (NetworkUtil.isMobileConnected(mActivity)) {
            if (!mActivity.isFinishing()) {
                dialog.dismiss();
            }
            String confirmButtonStr = "有流量，我就是任性";
            String cancelButtonStr = "等等先，要考虑考虑";
//            String message = "当前不在wifi环境下，需要消耗您" + packageSize + "的流量下载";
            String message = "当前不在wifi环境下，需要消耗您的流量下载";
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("提示");
            final AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (isForceUpdate) {
                            mActivity.finish();
                            System.exit(0);
                        }
                    }
                    return false;
                }
            });
            View view = View.inflate(mActivity, R.layout.dialog_check_update_version, null);
            TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
            TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
            TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
            tv_message.setText(message);
            tv_confirm.setText(confirmButtonStr);
            tv_cancel.setText(cancelButtonStr);
            mIsClickPositive = false;
            tv_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!NetworkUtil.isNetworkAvailable(mActivity)) {
                        UIUtil.toastShort(mActivity, R.string.notice_network_error);
                        return;
                    }

                    if (!isForceUpdate) {
                        alertDialog.dismiss();
                    }
                    if (mIsClickPositive) {
                        if (((BaseApp) BaseApp.getApp()).mIsFinishDownload) {
                            startInstallNewVersion(url1);
                        } else {
                            UIUtil.toastShort(mActivity, R.string.version_downloading);
                        }
                    } else {
                        mIsClickPositive = true;
                        UIUtil.toastShort(mActivity, R.string.version_downloading);
                        executeDownload(url1, newVersion, isForceUpdate);
                    }
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    if (isForceUpdate) {
                        mActivity.finish();
                    }
                }
            });

            alertDialog.setView(view);
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (mListener != null) {
                        mListener.dismiss();
                    }
                }
            });
            if (!mActivity.isFinishing()) {
                alertDialog.show();
            }
        } else {
            UIUtil.toastShort(mActivity, R.string.notice_network_error);
        }
    }

    /**
     * 执行下载
     * execute
     *
     * @param url
     * @param version
     * @since 1.0
     */

    public void executeDownload(final String url, String version, final boolean isForceUpdate) {

        if (TextUtils.isEmpty(url) || ((BaseApp) BaseApp.getApp()).mIsDownloading) {
            return;
        }
        if(!url.startsWith("http://")){
            return;
        }
        File downLoadFile = new File(getLocalDownloadUrl(url));

        if (((BaseApp) BaseApp.getApp()).mIsFinishDownload && downLoadFile.exists()) {
            startInstallNewVersion(url);
            return;
        }
        UIUtil.toastShort(mActivity, R.string.version_downloading);
//        if (downLoadFile.exists()) {
//            if (mCallProxy != null) {
//                mCallProxy.cancel();
//            }
//            downLoadFile.delete();
//        }


        File file = new File(Constant.BASE_LOCAL_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (mHttpTools == null) {
            mHttpTools = new HttpTools();
        }

        mCallProxy = mHttpTools.download(url, getLocalDownloadUrl(url), new SignRequest(), new BaseCallback<File>() {
            @Override
            public void onStart() {
                super.onStart();
                View view = View.inflate(mActivity, R.layout.dialog_progress, null);
                mProgressBar = (NumberProgressBar) view.findViewById(R.id.number_progress_bar);
                mButton = (Button) view.findViewById(R.id.btn_cancel_download);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelDownloadTask();
                    }
                });
                if (isForceUpdate) {
                    mButton.setVisibility(View.GONE);
                } else {
                    mButton.setVisibility(View.VISIBLE);
                }
                mDialog = UIUtil.showAlert(mActivity, view);
                mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            cancelDownloadTask();
                            if (isForceUpdate) {
                                if (!mActivity.isFinishing()) {
                                    mActivity.finish();
                                    System.exit(0);
                                }
                            }
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onSuccess(File file) {

                mDialog.hide();
                ((BaseApp) BaseApp.getApp()).mIsFinishDownload = true;
                ((BaseApp) BaseApp.getApp()).mIsDownloading = false;
                startInstallNewVersion(url);
            }

            @Override
            public void onFailure(Exception e) {
                ((BaseApp) BaseApp.getApp()).mIsDownloading = false;
                mIsClickPositive = false;
            }

            @Override
            public void onLoading(long count, long current, boolean isUploading) {
                super.onLoading(count, current, isUploading);
                LogUtils.e("style->"+String.valueOf(count));
                long value = current * 100 / count;
                if (value >= 0 && value <= 100) {
                    ((BaseApp) BaseApp.getApp()).mIsDownloading = true;
                  /*  Notification notification = new Notification(R.mipmap.ic_launcher, "开始下载", System.currentTimeMillis());
                    notification.setLatestEventInfo(mActivity, "健康云", "已下载 " + value + "%",
                            PendingIntent.getActivity(mActivity, 0, new Intent(), 0));
                    mManager.notify(R.string.app_name, notification);*/

                    mProgressBar.setProgress((int) value);
                } else if (value < 0) {
                    ((BaseApp) BaseApp.getApp()).mIsDownloading = false;
                    mManager.cancel(R.string.app_name);
                }

            }
        });


    }


    public String getLocalDownloadUrl(String url) {
        return Constant.BASE_LOCAL_PATH + "/patient-healthcloud_wonders" + url.hashCode() +".apk";
    }

    /**
     * 取消下载任务
     */
    private void cancelDownloadTask() {
        if (!mActivity.isFinishing()) {
            mDialog.dismiss();
        }
        if (mCallProxy != null)
            mCallProxy.cancel();
        ((BaseApp) BaseApp.getApp()).mIsDownloading = false;
        ((BaseApp) BaseApp.getApp()).mIsFinishDownload = false;
    }

    private String getVersionInfo(String oldVersion,String newVersion){
        String info = mActivity.getResources().getString(R.string.version_info);
        String out=String.format(info,oldVersion,newVersion);
        return out;
    }
}
