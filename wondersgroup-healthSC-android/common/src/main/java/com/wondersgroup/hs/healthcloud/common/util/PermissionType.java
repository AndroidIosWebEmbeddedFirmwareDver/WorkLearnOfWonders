package com.wondersgroup.hs.healthcloud.common.util;

import android.Manifest;

/*
 * Created by sunning on 16/4/14.
 */
public enum PermissionType {
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO, "录音"),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE, "电话"),
    CAMERA(Manifest.permission.CAMERA, "相机"),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION, "位置信息"),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间"),
    VIBRATE(Manifest.permission.VIBRATE, "振动");
    private String permissionType;
    private String permissionName;

    PermissionType(String permissionType, String permissionName) {
        this.permissionType = permissionType;
        this.permissionName = permissionName;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public String getPermissionName() {
        return permissionName;
    }

}
