package com.wondersgroup.healthcloud.im.easemob.users;

import com.wondersgroup.healthcloud.im.easemob.util.IdGen;
import com.wondersgroup.healthcloud.im.sdk.easemob.EasemobUsers;

/**
 * Created by jialing.yao on 2017-5-31.
 */
public class EasemobUserService {

    private EasemobUsers easemobUsers;

    public EasemobUserService(EasemobUsers easemobUsers) {
        this.easemobUsers = easemobUsers;
    }

    //医生端账号
    public EasemobAccount createDoctorAccount() {
        String id = "d" + IdGen.uuid();//easemob doctor account id has 33 length and start with character 'd'
        String pwd = IdGen.uuid();
        Boolean result = easemobUsers.register(id, pwd, id);
        if (result) {
            return new EasemobAccount(id, pwd);
        } else {
            return null;
        }
    }

    //用户端账号
    public EasemobAccount createUserAccount() {
        String id = IdGen.uuid();
        String pwd = IdGen.uuid();
        Boolean result = easemobUsers.register(id, pwd, id);
        if (result) {
            return new EasemobAccount(id, pwd);
        } else {
            return null;
        }
    }
}
