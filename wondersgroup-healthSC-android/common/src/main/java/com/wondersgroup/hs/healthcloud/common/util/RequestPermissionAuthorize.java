package com.wondersgroup.hs.healthcloud.common.util;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wondersgroup.hs.healthcloud.common.CommonActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * Created by sunning on 16/4/11.
 */
public class RequestPermissionAuthorize {

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0x1109;

    private AlertDialog dialog;
    private CommonActivity currentActivity;

    private PermissionSuccessCallBack callBack;
    private PermissionType type;
    private static boolean isCheck;
    private Map<String, Boolean> returnSet;
    private Map<String, String> permissionName;

    static {
        isCheck = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /*
     * apply permission method
     *
     * @param activity
     * @param type
     * @return
     */
    public static RequestPermissionAuthorize apply(final CommonActivity activity, PermissionType... type) {
        if (!isCheck) {
            return null;
        }
        if (type.length > 1) {
            return batchApply(activity, type);
        } else {
            RequestPermissionAuthorize request = build(activity);
            String permissionType = type[0].getPermissionType();
            if (!request.checkPermission(permissionType)) {
                request.obtainPermission(permissionType);
                request.setPermission(type[0]);
                request.setCallBack(new PermissionSuccessCallBack() {
                    @Override
                    public void permissionSuccess() {
                        activity.init();
                    }
                });
                return request;
            }
        }
        return null;
    }

    /*
     * 批量请求
     *
     * @param activity
     * @param types    PermissionType[]
     * @return
     */
    private static RequestPermissionAuthorize batchApply(final CommonActivity activity, PermissionType[] types) {
        RequestPermissionAuthorize request = build(activity);
        request.returnSet = new HashMap<>();
        request.permissionName = new HashMap<>();
        for (PermissionType type : types) {
            request.permissionName.put(type.getPermissionType(), type.getPermissionName());
            if (request.checkPermission(type.getPermissionType())) {
                request.returnSet.put(type.getPermissionType(), true);
            } else {
                request.returnSet.put(type.getPermissionType(), false);
            }

        }
        request.setCallBack(new PermissionSuccessCallBack() {
            @Override
            public void permissionSuccess() {
                activity.init();
            }
        });
        request.loopCallRequest();
        return request;
    }

    /*
     * build
     *
     * @param currentActivity
     * @return
     */
    public static RequestPermissionAuthorize build(CommonActivity currentActivity) {
        return new RequestPermissionAuthorize(currentActivity);
    }

    private RequestPermissionAuthorize(CommonActivity context) {
        this.currentActivity = context;
    }

    private void obtainPermission(String permissionType) {
        basePermission(permissionType);
    }

    private void setCallBack(PermissionSuccessCallBack callBack) {
        this.callBack = callBack;
    }

    public PermissionSuccessCallBack getCallBack() {
        return callBack;
    }

    public void setPermission(PermissionType type) {
        this.type = type;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void basePermission(String permissionName) {
        currentActivity.requestPermissions(new String[]{permissionName}, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission(String permissionName) {
        int hasWriteContactsPermission = currentActivity.checkSelfPermission(permissionName);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    public boolean permissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (returnSet != null) {
                    if (grantResult(grantResults[0])) {
                        returnSet.put(permissions[0], true);
                        loopCallRequest();
                    } else {
                        showDenyDialog(permissionName.get(permissions[0]));
                    }
                } else {
                    if (grantResult(grantResults[0])) {
                        if (callBack != null) {
                            callBack.permissionSuccess();
                        }
                        return true;
                    } else {
                        showDenyDialog(type.getPermissionName());
                    }
                    return false;
                }
        }
        return false;
    }

    private void loopCallRequest() {
        Iterator<String> iterator = returnSet.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (!returnSet.get(key)) {
                obtainPermission(key);
                return;
            }
        }
        if (callBack != null) {
            callBack.permissionSuccess();
        }
    }

    private boolean grantResult(int permissionType) {
        if (permissionType == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void showDenyDialog(String permissionName) {
        this.createWarningDialog("在设置-应用-权限中开启" + permissionName + "以正常使用" + getApplicationName() + "功能");
    }

    private void createWarningDialog(String message) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new AlertDialog.Builder(currentActivity)
                    .setTitle("权限申请")
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            currentActivity.startActivity(intent);
                            currentActivity.finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentActivity.finish();
                        }
                    }).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private String getApplicationName() {
        String applicationName = SystemUtil.getApplicationName(currentActivity);
        if (TextUtils.isEmpty(applicationName)) {
            return "";
        }
        return applicationName;
    }

    public interface PermissionSuccessCallBack {
        void permissionSuccess();
    }


    /**
     * 需要该权限的地方单独调用此方法
     */
    public boolean reservedForPermission(PermissionType permissionType, PermissionSuccessCallBack callBack) {
        if (!isCheck) {
            return true;
        }
        boolean hasPermission = checkPermission(permissionType.getPermissionType());
        if (!hasPermission) {
            type = permissionType;
            obtainPermission(permissionType.getPermissionType());
            this.callBack = callBack;
        }
        return hasPermission;
    }
}
