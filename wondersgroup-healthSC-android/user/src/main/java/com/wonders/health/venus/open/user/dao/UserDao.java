package com.wonders.health.venus.open.user.dao;

import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2015/8/31 16:16
 */
public class UserDao {
    public User getUser() {
        return PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_USER_INFO, User.class);
    }

    public void saveUser(User user) {
        PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_USER_INFO, user);
    }

    public void updateUser(User user) {
        saveUser(user);
    }

    public void delUser() {
        PrefUtil.putString(BaseApp.getApp(), Constant.KEY_USER_INFO, "");
    }
}
