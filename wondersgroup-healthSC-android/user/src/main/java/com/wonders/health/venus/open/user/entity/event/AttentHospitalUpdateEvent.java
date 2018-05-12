package com.wonders.health.venus.open.user.entity.event;

/**
 * Created by songzhen on 2015/7/28.
 */
public class AttentHospitalUpdateEvent {
    public boolean isAttented;
    public String hospitalId;

    public AttentHospitalUpdateEvent(boolean isAttented, String hospitalId) {
        this.hospitalId = hospitalId;
        this.isAttented = isAttented;
    }
}
