package com.wonders.health.venus.open.doctor.logic;

import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.wonders.health.venus.open.doctor.dao.UserDao;
import com.wonders.health.venus.open.doctor.entity.AccountChangeEvent;
import com.wonders.health.venus.open.doctor.entity.User;
import com.wonders.health.venus.open.doctor.module.consultation.ChatManager;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import de.greenrobot.event.EventBus;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/3/1 10:16
 */
public class UserManager {
    public static final int TYPE_SET_PWD_FROM_FORGET_PWD = 0;//忘记密码
    public static final int TYPE_SET_PWD_FROM_MODIFY_PWD = 1;//修改密码

    //0:默认, 1:手机动态码登陆,  2: 忘记密码， 3:设置密码
    public static int CODE_FOR_DEFAULT = 0;
    public static int CODE_FOR_FAST_LOGIN = 1;
    public static int CODE_FOR_REST_PWD = 2;
    public static int CODE_FOR_SET_PWD = 3;

    private static UserManager sInstance;
    private User mUser;
    private HttpTools mHttpTools;

    private UserManager() {
        mHttpTools = new HttpTools();
    }

    public static UserManager getInstance() {
        if (sInstance == null) {
            synchronized (UserManager.class) {
                if (sInstance == null) {
                    sInstance = new UserManager();
                }
            }
        }
        return sInstance;
    }

    public User getUser() {
        if (mUser == null) {
            mUser = new UserDao().getUser();
        }
        return mUser == null ? new User() : mUser;
    }


    public void login(User user, ResponseCallback callback) {
//        mHttpTools.get(UrlConst.USER_LOGIN, user.buildLoginParams(), new LoginCallback(callback));
    }

    public void fastLogin(User user, ResponseCallback callback) {
//        mHttpTools.get(UrlConst.USER_VERIFY_CODE_LOGIN, user.buildFastLoginParams(), new LoginCallback(callback));
    }

    public void register(User user, final ResponseCallback<User> callBack) {
//        mHttpTools.post(UrlConst.USER_REGIST, user.buildRegistParams(), new LoginCallback(callBack));
    }


    public void logout(final ResponseCallback<User> callBack) {
        SignRequest request = new SignRequest();
//        mHttpTools.delete(UrlConst.USER_LOGOUT, request, new ResponseCallback<User>() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                callBack.onStart();
//            }
//
//            @Override
//            public void onSuccess(User t) {
//                super.onSuccess(t);
//                dealLogout(t);
//                callBack.onSuccess(t);
//            }
//
//            @Override
//            public void onFailure(Exception error) {
//                super.onFailure(error);
//                dealLogout(null);
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                callBack.onFinish();
//            }
//
//            @Override
//            public boolean isShowNotice() {
//                return callBack.isShowNotice();
//            }
//        });
    }


    /**
     * 实名认证
     *
     * @param name
     * @param idcard
     * @param picPath
     * @param callback
     */
    public void postAuthInfo(String name, String idcard, String picPath, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addBodyParameter("uid", getUser().uid);
        request.addBodyParameter("name", name);
        request.addBodyParameter("idcard", idcard);
        request.addBodyParameter("photo", picPath);
//        request.setPath(UrlConst.AUTH_SUBMIT);
        mHttpTools.post(request, callback);
    }

    private void setPwdComplete() {
        getUser().password_complete = true;
        new UserDao().updateUser(getUser());
    }


    //本地保存身份证号等信息
    public void setIDCard(String idCard, String name, String phone) {
        getUser().idcard = idCard;
        getUser().verified = true;
        getUser().name = name;
        //此手机号是验证用到的手机号，不会更新到用户信息中
//        getUser().mobile=phone;
        new UserDao().updateUser(getUser());
//        EventBus.getDefault().post(new UserUpdateEvent());
    }

    /**
     * 修改绑定手机号
     *
     * @param old_verify_code 旧手机号的验证码 如果没有注册手机号非必填,有注册手机号必填
     * @param moblie
     * @param new_verify_code
     * @param callback
     */
    public void bindMobile(String old_verify_code, String moblie, String new_verify_code, ResponseCallback<User> callback) {
//        SignRequest request = new SignRequest();
//        request.addBodyParameter("uid", getUser().uid);
//        if (!TextUtils.isEmpty(old_verify_code)) {
//            request.addBodyParameter("old_verify_code", old_verify_code);
//        }
//        request.addBodyParameter("new_mobile", moblie);
//        request.addBodyParameter("new_verify_code", new_verify_code);
//        mHttpTools.post(UrlConst.USER_BIND_MOBILE, request, new UserUpdateCallback(callback) {
//            @Override
//            protected void onUpdate(User newUser) {
//                getUser().mobile = newUser.mobile;
//            }
//        });
    }


    public void dealLogin(User user) {
        mUser = user;
        UserDao userDao = new UserDao();
        userDao.delUser();
        userDao.saveUser(user);
        // TODO
        // 设置推送
//        PushUtil.unBindAlias();
//        PushUtil.bindAlias(BaseApp.getApp());

        ChatManager.getInstance().login(user.talkid, user.talkpwd, null);

        //添加友盟账号统计(用户登入)
        MobclickAgent.onProfileSignIn(user.uid);
        EventBus.getDefault().post(new AccountChangeEvent(true));
    }

    /**
     * 判断是否登录
     * isLogin
     *
     * @return
     * @since 1.0
     */
    public boolean isLogin() {
        return !TextUtils.isEmpty(UserManager.getInstance().getUser().uid);
    }

    /**
     * 设置认证状态
     *
     * @param verifyState
     */
    public void setVerifyState(String verifyState) {
        //如果是3则认证成功，verified为true,否则verified为false
        getUser().verified = "3".equals(verifyState);
        getUser().verificationStatus = verifyState;
        new UserDao().updateUser(getUser());
    }

    /**
     * 认证状态
     *
     * @return
     */
    public boolean getVerifyState() {
        getUser().verified = "3".equals(getUser().verificationStatus);
        new UserDao().updateUser(getUser());
        return getUser().verified;
    }


    /**
     * 获取用户信息
     */
    public void getUserInfo(final ResponseCallback<User> callback) {
        SignRequest request = new SignRequest();
        if (!TextUtils.isEmpty(getUser().uid)) {
            request.addQueryStringParameter("id", getUser().uid);
//            mHttpTools.get(UrlConst.USER_GET_INFO, request, new UserUpdateCallback(callback) {
//
//                @Override
//                protected void onUpdate(User newUser) {
//                    String key = getUser().key;
//                    String uid = getUser().uid;
//                    String token = getUser().token;
//
//                    mUser = newUser;
//                    mUser.key = key;
//                    mUser.uid = uid;
//                    mUser.token = token;
//                }
//            });
        }
    }


    /**
     * 修改头像
     * @param avatar
     * @param callback
     */
//    public void postUserAvatar(String avatar, ResponseCallback<User> callback){
//        SignRequest request = new SignRequest();
//        request.addBodyParameter("uid", getUser().uid);
//        request.addBodyParameter("avatar", avatar);
//        mHttpTools.post(UrlConst.USER_UPDATE, request, new UserUpdateCallback(callback) {
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            protected void onUpdate(User newUser) {
//                getUser().avatar = newUser.avatar;
//            }
//        });
//    }

    /**
     * 获取上传文件到七牛的TOKEN
     *
     * @param callBack
     */
//    public void getQiniuToken(ResponseCallback callBack) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("uid", getUser().uid);
//        request.setPath(UrlConst.GET_QINIU_TOKEN);
//        mHttpTools.get( request, callBack);
//    }

    /**
     * 给user设置别名
     */
    public void setPushAlias(String clientId, final ResponseCallback<String> callBack) {
//        if (isLogin()) {
//            SignRequest request = new SignRequest();
//            request.addBodyParameter("uid", getUser().uid);
//            request.addBodyParameter("cid", clientId);
//            mHttpTools.post(UrlConst.SET_PUSH_ALIAS, request, new ResponseCallback<JSONObject>() {
//
//                @Override
//                public void onSuccess(JSONObject s) {
//                    super.onSuccess(s);
//                    String alias = s.getString("alias");
//                    callBack.onSuccess(alias);
//                }
//
//                @Override
//                public boolean isShowNotice() {
//                    return false;
//                }
//            });
//        } else {
//            PushUtil.unBindAlias();
//        }
    }

    /**
     * 医院详情查询
     *
     * @param callback
     */
    public void queryHospitalInfo(String hosId, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("hosId", hosId);
        signRequest.addQueryStringParameter("userId", getUser().uid);

//        mHttpTools.get(UrlConst.QUERY_HOSPITAL_INFO, signRequest, callback);
    }


    /**
     * 用户反馈
     *
     * @param uid
     * @param comments
     * @param contact
     * @param callback
     */
    public void feedBack(String uid, String comments, String contact, ResponseCallback callback) {
//        SignRequest request = new SignRequest();
//        request.addBodyParameter("uid", uid);
//        request.addBodyParameter("comments", comments);
//        request.addBodyParameter("contact", contact);
//        request.setPath(UrlConst.USER_FEEDBACK);
//        mHttpTools.post(request, callback);
    }

    /**
     * 设置即时聊天登录状态
     *
     * @param isEmLogin
     */
    public void setEMLoginState(boolean isEmLogin) {
        getUser().isEMLogin = isEmLogin;
        new UserDao().updateUser(getUser());
    }




    /**
     * 判断即时聊天是否登录
     *
     * @return
     */
    public boolean isEMLogin() {
        return getUser().isEMLogin;
    }
}
