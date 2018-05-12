package com.wonders.health.venus.open.user.entity.event;

/**
 * Created by songzhen on 2015/7/28.
 */
public class AttentDoctorUpdateEvent {
    public boolean isAttented;
    public String doctorId;

    public AttentDoctorUpdateEvent(boolean isAttented, String doctorId) {
        this.doctorId = doctorId;
        this.isAttented = isAttented;
    }
}
