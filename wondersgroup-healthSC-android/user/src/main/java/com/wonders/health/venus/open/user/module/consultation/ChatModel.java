package com.wonders.health.venus.open.user.module.consultation;

import android.content.Context;


import com.wonders.health.venus.open.user.module.consultation.db.ChatUserDao;
import com.wondersgroup.hs.healthcloud.common.huanxin.domain.EaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatModel {
    ChatUserDao dao = null;
    protected Context context = null;
    protected Map<Key,Object> valueCache = new HashMap<Key,Object>();
    
    public ChatModel(Context ctx){
        context = ctx;
        ChatPreferenceManager.init(context);
    }
    
    public boolean saveContactList(List<EaseUser> contactList) {
        ChatUserDao dao = new ChatUserDao(context);
        dao.saveContactList(contactList);
        return true;
    }

    public Map<String, EaseUser> getContactList() {
        ChatUserDao dao = new ChatUserDao(context);
        return dao.getContactList();
    }
    
    public void saveContact(EaseUser user){
        ChatUserDao dao = new ChatUserDao(context);
        dao.saveContact(user);
    }
    
    /**
     * 设置当前用户的环信id
     * @param username
     */
    public void setCurrentUserName(String username){
        ChatPreferenceManager.getInstance().setCurrentUserName(username);
    }
    
    /**
     * 获取当前用户的环信id
     */
    public String getCurrentUsernName(){
        return ChatPreferenceManager.getInstance().getCurrentUsername();
    }

    public void setSettingMsgNotification(boolean paramBoolean) {
        ChatPreferenceManager.getInstance().setSettingMsgNotification(paramBoolean);
        valueCache.put(Key.VibrateAndPlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgNotification() {
        Object val = valueCache.get(Key.VibrateAndPlayToneOn);

        if(val == null){
            val = ChatPreferenceManager.getInstance().getSettingMsgNotification();
            valueCache.put(Key.VibrateAndPlayToneOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgSound(boolean paramBoolean) {
        ChatPreferenceManager.getInstance().setSettingMsgSound(paramBoolean);
        valueCache.put(Key.PlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgSound() {
        Object val = valueCache.get(Key.PlayToneOn);

        if(val == null){
            val = ChatPreferenceManager.getInstance().getSettingMsgSound();
            valueCache.put(Key.PlayToneOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgVibrate(boolean paramBoolean) {
        ChatPreferenceManager.getInstance().setSettingMsgVibrate(paramBoolean);
        valueCache.put(Key.VibrateOn, paramBoolean);
    }

    public boolean getSettingMsgVibrate() {
        Object val = valueCache.get(Key.VibrateOn);

        if(val == null){
            val = ChatPreferenceManager.getInstance().getSettingMsgVibrate();
            valueCache.put(Key.VibrateOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgSpeaker(boolean paramBoolean) {
        ChatPreferenceManager.getInstance().setSettingMsgSpeaker(paramBoolean);
        valueCache.put(Key.SpakerOn, paramBoolean);
    }

    public boolean getSettingMsgSpeaker() {        
        Object val = valueCache.get(Key.SpakerOn);

        if(val == null){
            val = ChatPreferenceManager.getInstance().getSettingMsgSpeaker();
            valueCache.put(Key.SpakerOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }


    public void setDisabledGroups(List<String> groups){
        if(dao == null){
            dao = new ChatUserDao(context);
        }
        
        dao.setDisabledGroups(groups);
        valueCache.put(Key.DisabledGroups, groups);
    }
    
    public List<String> getDisabledGroups(){
        Object val = valueCache.get(Key.DisabledGroups);

        if(dao == null){
            dao = new ChatUserDao(context);
        }
        
        if(val == null){
            val = dao.getDisabledGroups();
            valueCache.put(Key.DisabledGroups, val);
        }
       
        return (List<String>) val;
    }
    
    public void setDisabledIds(List<String> ids){
        if(dao == null){
            dao = new ChatUserDao(context);
        }
        
        dao.setDisabledIds(ids);
        valueCache.put(Key.DisabledIds, ids);
    }
    
    public List<String> getDisabledIds(){
        Object val = valueCache.get(Key.DisabledIds);
        
        if(dao == null){
            dao = new ChatUserDao(context);
        }

        if(val == null){
            val = dao.getDisabledIds();
            valueCache.put(Key.DisabledIds, val);
        }
       
        return (List<String>) val;
    }
    
    public void setGroupsSynced(boolean synced){
        ChatPreferenceManager.getInstance().setGroupsSynced(synced);
    }
    
    public boolean isGroupsSynced(){
        return ChatPreferenceManager.getInstance().isGroupsSynced();
    }
    
    public void setContactSynced(boolean synced){
        ChatPreferenceManager.getInstance().setContactSynced(synced);
    }
    
    public boolean isContactSynced(){
        return ChatPreferenceManager.getInstance().isContactSynced();
    }
    
    public void setBlacklistSynced(boolean synced){
        ChatPreferenceManager.getInstance().setBlacklistSynced(synced);
    }
    
    public boolean isBacklistSynced(){
        return ChatPreferenceManager.getInstance().isBacklistSynced();
    }
    
    public void allowChatroomOwnerLeave(boolean value){
        ChatPreferenceManager.getInstance().setSettingAllowChatroomOwnerLeave(value);
    }
    
    public boolean isChatroomOwnerLeaveAllowed(){
        return ChatPreferenceManager.getInstance().getSettingAllowChatroomOwnerLeave();
    }
   
    public void setDeleteMessagesAsExitGroup(boolean value) {
        ChatPreferenceManager.getInstance().setDeleteMessagesAsExitGroup(value);
    }
    
    public boolean isDeleteMessagesAsExitGroup() {
        return ChatPreferenceManager.getInstance().isDeleteMessagesAsExitGroup();
    }
    
    public void setAutoAcceptGroupInvitation(boolean value) {
        ChatPreferenceManager.getInstance().setAutoAcceptGroupInvitation(value);
    }
    
    public boolean isAutoAcceptGroupInvitation() {
        return ChatPreferenceManager.getInstance().isAutoAcceptGroupInvitation();
    }
    
    enum Key{
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds
    }
}
