package com.wonders.health.venus.open.user.entity;

import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;

import java.io.Serializable;

/**
 * Created by wang on 2016/8/15 18:44.
 * Class Name :AuthStatus
 */
public class AuthStatus implements Serializable {

    public static int verify_to_verify = 0;//待认证
    public static int verify_fail = 1;//审核失败
    public static int verify_checking = 2;//审核中
    public static int verify_success = 3;//审核成功


    public String uid;
    public Boolean success;
    public boolean can_submit;
    public String status; //0-未提交,1-认证失败,2-审核中,3-认证成功
    public String statusSpec;//实名认证状态文字
    public String msg;
    public String name;
    public String idcard;
    public int verify;//审核状态


    public AuthStatus getAuthStatus(int type) {
        return getAuthStatus(type, "", "", null);
    }

    public AuthStatus getAuthStatus(int type, String name, String idcard) {
        return getAuthStatus(type, name, idcard, null);
    }

    public AuthStatus getAuthStatus(int type,AuthStatus result) {
        return getAuthStatus(type,"", "",result);
    }

    public AuthStatus getAuthStatus(int type, String name, String idcard, AuthStatus result) {
        AuthStatus authStatus = new AuthStatus();
        if (result != null) {//网络接口获取数据
            authStatus.uid = result.uid;
            authStatus.success = result.success;
            authStatus.can_submit = result.can_submit;
//            authStatus.msg = type==verify_checking? BaseApp.getApp().getString(R.string.auth_explain):result.msg;
            authStatus.msg = type==verify_checking? "":result.msg;
            authStatus.status = result.status;
            authStatus.statusSpec = result.statusSpec;
            authStatus.name = result.name;
            authStatus.idcard = StringUtil.decodeIdCard(result.idcard);
            authStatus.verify=type;

        } else {//本地数据显示
            if (type == verify_success) {
                authStatus.uid = UserManager.getInstance().getUser().uid;
                authStatus.success = true;
                authStatus.status = verify_success+"";
                authStatus.statusSpec = "已通过";
                authStatus.name = UserManager.getInstance().getUser().name;
                authStatus.idcard = StringUtil.decodeIdCard(UserManager.getInstance().getUser().idcard);
                authStatus.verify = type;
                return authStatus;
            } else if (type == verify_checking) {
                authStatus.uid = UserManager.getInstance().getUser().uid;
                authStatus.status = verify_checking+"";
                authStatus.statusSpec = "审核中";
                authStatus.success = false;
                authStatus.can_submit = false;
                authStatus.msg = "";
//                authStatus.msg = BaseApp.getApp().getString(R.string.auth_explain);
                authStatus.verify = type;
                authStatus.name = name;
                authStatus.idcard = StringUtil.decodeIdCard(idcard);
            }
        }
        return authStatus;
    }


}

