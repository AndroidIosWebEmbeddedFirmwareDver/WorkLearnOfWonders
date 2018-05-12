/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wonders.health.venus.open.doctor.module.consultation.db;

import android.content.Context;

import com.wondersgroup.hs.healthcloud.common.huanxin.domain.EaseUser;

import java.util.List;
import java.util.Map;


public class ChatUserDao {
	public static final String TABLE_NAME = "uers";
	public static final String COLUMN_NAME_ID = "username";
	public static final String COLUMN_NAME_NICK = "nick";
	public static final String COLUMN_NAME_AVATAR = "avatar";
	
	public static final String PREF_TABLE_NAME = "pref";
	public static final String COLUMN_NAME_DISABLED_GROUPS = "disabled_groups";
	public static final String COLUMN_NAME_DISABLED_IDS = "disabled_ids";

	public static final String ROBOT_TABLE_NAME = "robots";
	public static final String ROBOT_COLUMN_NAME_ID = "username";
	public static final String ROBOT_COLUMN_NAME_NICK = "nick";
	public static final String ROBOT_COLUMN_NAME_AVATAR = "avatar";
	
	
	public ChatUserDao(Context context) {
	}

	/**
	 * 保存好友list
	 * 
	 * @param contactList
	 */
	public void saveContactList(List<EaseUser> contactList) {
	    ChatDBManager.getInstance().saveContactList(contactList);
	}

	/**
	 * 获取好友list
	 * 
	 * @return
	 */
	public Map<String, EaseUser> getContactList() {
		
	    return ChatDBManager.getInstance().getContactList();
	}
	
	/**
	 * 删除一个联系人
	 * @param username
	 */
	public void deleteContact(String username){
	    ChatDBManager.getInstance().deleteContact(username);
	}
	
	/**
	 * 保存一个联系人
	 * @param user
	 */
	public void saveContact(EaseUser user){
	    ChatDBManager.getInstance().saveContact(user);
	}
	
	public void setDisabledGroups(List<String> groups){
	    ChatDBManager.getInstance().setDisabledGroups(groups);
    }
    
    public List<String>  getDisabledGroups(){       
        return ChatDBManager.getInstance().getDisabledGroups();
    }
    
    public void setDisabledIds(List<String> ids){
        ChatDBManager.getInstance().setDisabledIds(ids);
    }
    
    public List<String> getDisabledIds(){
        return ChatDBManager.getInstance().getDisabledIds();
    }
    
}
