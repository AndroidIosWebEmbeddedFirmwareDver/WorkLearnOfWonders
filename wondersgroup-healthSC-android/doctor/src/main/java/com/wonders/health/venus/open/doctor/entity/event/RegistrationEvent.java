package com.wonders.health.venus.open.doctor.entity.event;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/1/8 17:10
 */
public class RegistrationEvent {
    public boolean isCancel;

    public String regId;

    public RegistrationEvent() {
    }

    public RegistrationEvent(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public RegistrationEvent(boolean isCancel, String regId) {
        this.isCancel = isCancel;
        this.regId = regId;
    }
}
