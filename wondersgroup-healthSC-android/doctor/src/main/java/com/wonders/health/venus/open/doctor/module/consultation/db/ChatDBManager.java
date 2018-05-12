package com.wonders.health.venus.open.doctor.module.consultation.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.hyphenate.util.HanziToPinyin;
import com.wonders.health.venus.open.doctor.BaseApp;
import com.wonders.health.venus.open.doctor.module.consultation.InviteMessage;
import com.wondersgroup.hs.healthcloud.common.huanxin.EaseConstant;
import com.wondersgroup.hs.healthcloud.common.huanxin.domain.EaseUser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ChatDBManager {
    static private ChatDBManager dbMgr = new ChatDBManager();
    private DbOpenHelper dbHelper;
    
    private ChatDBManager(){
        dbHelper = DbOpenHelper.getInstance(BaseApp.getApp().getApplicationContext());
    }
    
    public static synchronized ChatDBManager getInstance(){
        if(dbMgr == null){
            dbMgr = new ChatDBManager();
        }
        return dbMgr;
    }
    
    /**
     * 保存好友list
     * 
     * @param contactList
     */
    synchronized public void saveContactList(List<EaseUser> contactList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(ChatUserDao.TABLE_NAME, null, null);
            for (EaseUser user : contactList) {
                ContentValues values = new ContentValues();
                values.put(ChatUserDao.COLUMN_NAME_ID, user.getUsername());
                if(user.getNick() != null)
                    values.put(ChatUserDao.COLUMN_NAME_NICK, user.getNick());
                if(user.getAvatar() != null)
                    values.put(ChatUserDao.COLUMN_NAME_AVATAR, user.getAvatar());
                db.replace(ChatUserDao.TABLE_NAME, null, values);
            }
        }
    }

    /**
     * 获取好友list
     * 
     * @return
     */
    synchronized public Map<String, EaseUser> getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + ChatUserDao.TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(ChatUserDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(ChatUserDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(ChatUserDao.COLUMN_NAME_AVATAR));
                EaseUser user = new EaseUser(username);
                user.setNick(nick);
                user.setAvatar(avatar);
                String headerName = null;
                if (!TextUtils.isEmpty(user.getNick())) {
                    headerName = user.getNick();
                } else {
                    headerName = user.getUsername();
                }
                
                if (username.equals(EaseConstant.NEW_FRIENDS_USERNAME) || username.equals(EaseConstant.GROUP_USERNAME)
                        || username.equals(EaseConstant.CHAT_ROOM)|| username.equals(EaseConstant.CHAT_ROBOT)) {
                    user.setInitialLetter("");
                } else if (Character.isDigit(headerName.charAt(0))) {
                    user.setInitialLetter("#");
                } else {
                    user.setInitialLetter(HanziToPinyin.getInstance().get(headerName.substring(0, 1))
                            .get(0).target.substring(0, 1).toUpperCase());
                    char header = user.getInitialLetter().toLowerCase().charAt(0);
                    if (header < 'a' || header > 'z') {
                        user.setInitialLetter("#");
                    }
                }
                users.put(username, user);
            }
            cursor.close();
        }
        return users;
    }
    
    /**
     * 删除一个联系人
     * @param username
     */
    synchronized public void deleteContact(String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            db.delete(ChatUserDao.TABLE_NAME, ChatUserDao.COLUMN_NAME_ID + " = ?", new String[]{username});
        }
    }
    
    /**
     * 保存一个联系人
     * @param user
     */
    synchronized public void saveContact(EaseUser user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChatUserDao.COLUMN_NAME_ID, user.getUsername());
        if(user.getNick() != null)
            values.put(ChatUserDao.COLUMN_NAME_NICK, user.getNick());
        if(user.getAvatar() != null)
            values.put(ChatUserDao.COLUMN_NAME_AVATAR, user.getAvatar());
        if(db.isOpen()){
            db.replace(ChatUserDao.TABLE_NAME, null, values);
        }
    }
    
    public void setDisabledGroups(List<String> groups){
        setList(ChatUserDao.COLUMN_NAME_DISABLED_GROUPS, groups);
    }
    
    public List<String>  getDisabledGroups(){       
        return getList(ChatUserDao.COLUMN_NAME_DISABLED_GROUPS);
    }
    
    public void setDisabledIds(List<String> ids){
        setList(ChatUserDao.COLUMN_NAME_DISABLED_IDS, ids);
    }
    
    public List<String> getDisabledIds(){
        return getList(ChatUserDao.COLUMN_NAME_DISABLED_IDS);
    }
    
    synchronized private void setList(String column, List<String> strList){
        StringBuilder strBuilder = new StringBuilder();
        
        for(String hxid:strList){
            strBuilder.append(hxid).append("$");
        }
        
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(column, strBuilder.toString());

            db.update(ChatUserDao.PREF_TABLE_NAME, values, null,null);
        }
    }
    
    synchronized private List<String> getList(String column){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + column + " from " + ChatUserDao.PREF_TABLE_NAME,null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        String strVal = cursor.getString(0);
        if (strVal == null || strVal.equals("")) {
            return null;
        }
        
        cursor.close();
        
        String[] array = strVal.split("$");
        
        if(array != null && array.length > 0){
            List<String> list = new ArrayList<String>();
            for(String str:array){
                list.add(str);
            }
            
            return list;
        }
        
        return null;
    }
    
    /**
     * 保存message
     * @param message
     * @return  返回这条messaged在db中的id
     */
    public synchronized Integer saveMessage(InviteMessage message){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_ID, message.getGroupId());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_Name, message.getGroupName());
            values.put(InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
            values.put(InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
            values.put(InviteMessgeDao.COLUMN_NAME_STATUS, message.getStatus().ordinal());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUPINVITER, message.getGroupInviter());
            db.insert(InviteMessgeDao.TABLE_NAME, null, values);
            
            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessgeDao.TABLE_NAME,null); 
            if(cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            
            cursor.close();
        }
        return id;
    }
    
    /**
     * 更新message
     * @param msgId
     * @param values
     */
    synchronized public void updateMessage(int msgId,ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            db.update(InviteMessgeDao.TABLE_NAME, values, InviteMessgeDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(msgId)});
        }
    }
    
    /**
     * 获取messges
     * @return
     */
    synchronized public List<InviteMessage> getMessagesList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from " + InviteMessgeDao.TABLE_NAME + " desc",null);
            while(cursor.moveToNext()){
                InviteMessage msg = new InviteMessage();
                int id = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_ID));
                String from = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_FROM));
                String groupid = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUP_ID));
                String groupname = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUP_Name));
                String reason = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_REASON));
                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
                int status = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_STATUS));
                String groupInviter = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUPINVITER));
                
                msg.setId(id);
                msg.setFrom(from);
                msg.setGroupId(groupid);
                msg.setGroupName(groupname);
                msg.setReason(reason);
                msg.setTime(time);
                msg.setGroupInviter(groupInviter);
                
                if(status == InviteMessage.InviteMesageStatus.BEINVITEED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
                else if(status == InviteMessage.InviteMesageStatus.BEAGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
                else if(status == InviteMessage.InviteMesageStatus.BEREFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
                else if(status == InviteMessage.InviteMesageStatus.AGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.AGREED);
                else if(status == InviteMessage.InviteMesageStatus.REFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
                else if(status == InviteMessage.InviteMesageStatus.BEAPPLYED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
                else if(status == InviteMessage.InviteMesageStatus.GROUPINVITATION.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION);
                else if(status == InviteMessage.InviteMesageStatus.GROUPINVITATION_ACCEPTED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_ACCEPTED);
                else if(status == InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED);
                
                msgs.add(msg);
            }
            cursor.close();
        }
        return msgs;
    }
    
    /**
     * 删除要求消息
     * @param from
     */
    synchronized public void deleteMessage(String from){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            db.delete(InviteMessgeDao.TABLE_NAME, InviteMessgeDao.COLUMN_NAME_FROM + " = ?", new String[]{from});
        }
    }
    
    synchronized int getUnreadNotifyCount(){
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select " + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessgeDao.TABLE_NAME, null);
            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }
            cursor.close();
        }
         return count;
    }
    
    synchronized void setUnreadNotifyCount(int count){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);

            db.update(InviteMessgeDao.TABLE_NAME, values, null,null);
        }
    }
    
    synchronized public void closeDB(){
        if(dbHelper != null){
            dbHelper.closeDB();
        }
        dbMgr = null;
    }

}