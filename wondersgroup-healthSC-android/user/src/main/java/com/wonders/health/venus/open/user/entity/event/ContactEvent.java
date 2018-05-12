package com.wonders.health.venus.open.user.entity.event;

/**
 * Created by sunning on 16/1/10.
 * 联系人事件
 */
public class ContactEvent {
    public String contactName;
    public String contactID;

    public ContactEvent(String contactName, String contactID) {
        this.contactName = contactName;
        this.contactID = contactID;
    }
}
