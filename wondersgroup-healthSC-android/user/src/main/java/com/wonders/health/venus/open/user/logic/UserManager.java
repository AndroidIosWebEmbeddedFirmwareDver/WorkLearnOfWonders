package com.wonders.health.venus.open.user.logic;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.umeng.analytics.MobclickAgent;
import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.dao.UserDao;
import com.wonders.health.venus.open.user.entity.LoginInfo;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.AccountChangeEvent;
import com.wonders.health.venus.open.user.entity.event.UserUpdateEvent;
import com.wonders.health.venus.open.user.module.consultation.ChatManager;
import com.wonders.health.venus.open.user.module.health.EvaluateListActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.util.WebViewUtil;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
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

    // 初始化用户信息
    public void init() {
        // 如果非登录用户，游客登录
        if (!isLogin()) {
            guestLogin();
        } else {
            User user = getUser();
            ChatManager.getInstance().login(user.talkId, user.talkPwd, null);
        }
    }


    /**
     * 游客登录
     */
    public void guestLogin() {
        mHttpTools.get(UrlConst.USER_GUEST_LOGIN, new SignRequest(), new ResponseCallback<User>() {

            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                mUser = user;
                UserDao userDao = new UserDao();
                userDao.delUser();
                userDao.saveUser(user);
            }

            @Override
            public boolean isShowNotice() {
                return false;
            }
        });
    }

    public void login(User user, ResponseCallback callback) {
        mHttpTools.get(UrlConst.USER_LOGIN, user.buildLoginParams(), new LoginCallback(callback));
    }

    public void fastLogin(User user, ResponseCallback callback) {
        mHttpTools.get(UrlConst.USER_VERIFY_CODE_LOGIN, user.buildFastLoginParams(), new LoginCallback(callback));
    }

    public void register(User user, final ResponseCallback<User> callBack) {
//        mHttpTools.post(UrlConst.USER_REGIST, user.buildRegistParams(), new LoginCallback(callBack));
    }

    /**
     * 第三方登录
     * thirdPartLogin
     *
     * @since 1.0
     */
    public void thirdPartLogin(final String platformName, final ResponseCallback<User> callBack) {
        callBack.onStart();
        Platform platform = ShareSDK.getPlatform(platformName);
        if (Wechat.NAME.equals(platformName) && !platform.isClientValid()) {
            callBack.onFailure(new HttpException(HttpException.CUSTOM_CODE, "您的客户端版本过低或未安装，需要安装客户端才能使用！"));
            callBack.onFinish();
            return;
        }

        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(final Platform platform, final int action, final HashMap<String, Object> res) {

                new Handler(BaseApp.getApp().getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (action == Platform.ACTION_USER_INFOR) {
                            String access_token = platform.getDb().getToken();
                            String openid = (String) res.get("openid");
                            thirdPartBind(platformName, openid, access_token, new LoginCallback(callBack));
                        }
                    }
                });
            }

            @Override
            public void onError(Platform platform, final int action, final Throwable t) {
                if (action == Platform.ACTION_USER_INFOR) {
                    new Handler(BaseApp.getApp().getMainLooper()).post(new Runnable() {
                        public void run() {
                            callBack.onFailure(new HttpException(HttpException.CUSTOM_CODE, "登录失败"));
                            callBack.onFinish();
                        }
                    });
                }
            }

            @Override
            public void onCancel(Platform platform, int action) {
                if (action == Platform.ACTION_USER_INFOR) {
                    new Handler(BaseApp.getApp().getMainLooper()).post(new Runnable() {
                        public void run() {
                            callBack.onCancelled();
                            callBack.onFinish();
                        }
                    });
                }
            }
        });
        platform.showUser(null);
    }


    // 第三方账号绑定
    private void thirdPartBind(String platformName, String openid, String token, LoginCallback callBack) {
//        String url = "";
//        if (Wechat.NAME.equals(platformName)) {
//            url = UrlConst.USER_WECHAT_LOGIN;
//        } else if (QQ.NAME.equals(platformName)) {
//            url = UrlConst.USER_QQ_LOGIN;
//        } else if (SinaWeibo.NAME.equals(platformName)) {
//            url = UrlConst.USER_WEIBO_LOGIN;
//        } else if (PLATFORM_ESHIMIN.equals(platformName)) {
//            url = UrlConst.USER_ESHIMIN_LOGIN;
//        }
//        if (TextUtils.isEmpty(url)) {
//            callBack.onStart();
//            callBack.onFailure(new HttpException(HttpException.CUSTOM_CODE, "不支持的平台！"));
//            callBack.onFinish();
//            return;
//        }
//        SignRequest request = new SignRequest();
//        if (!TextUtils.isEmpty(openid)) {
//            if (PLATFORM_ESHIMIN.equals(platformName)) {
//                request.addQueryStringParameter("username", openid);
//            } else {
//                request.addQueryStringParameter("openid", openid);
//            }
//        }
//        request.addQueryStringParameter("token", token);
//        request.addQueryStringParameter("with_info", true + "");
//        mHttpTools.get(url, request, callBack);
    }

    public void logout(final ResponseCallback<User> callBack) {
        SignRequest request = new SignRequest();
        mHttpTools.delete(UrlConst.USER_LOGOUT, request, new ResponseCallback<User>() {
            @Override
            public void onStart() {
                super.onStart();
                callBack.onStart();
            }

            @Override
            public void onSuccess(User t) {
                super.onSuccess(t);
                dealLogout(t);
                callBack.onSuccess(t);
            }

            @Override
            public void onFailure(Exception error) {
                super.onFailure(error);
                dealLogout(null);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callBack.onFinish();
            }

            @Override
            public boolean isShowNotice() {
                return callBack.isShowNotice();
            }
        });
    }


    /**
     * 忘记密码
     *
     * @param user
     * @param callback
     */
    public void resetPassword(User user, final ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addBodyParameter("mobile", user.mobile);
        request.addBodyParameter("code", user.verify_code);
        request.addBodyParameter("password", user.getPwd());
        mHttpTools.post(UrlConst.USER_REST_PWD, request, new ResponseCallback() {
            @Override
            public void onStart() {
                super.onStart();
                callback.onStart();
            }

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                callback.onSuccess(o);
                setPwdComplete();
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
                callback.onFailure(e);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param callback
     */
    public void updatePassword(String oldPwd, String newPwd, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addBodyParameter("uid", getUser().uid);
        request.addBodyParameter("new_password", newPwd);
        request.addBodyParameter("previous_password", oldPwd);
        mHttpTools.post(UrlConst.UPDATE_PWD, request, callback);
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
        request.setPath(UrlConst.AUTH_SUBMIT);
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
        EventBus.getDefault().post(new UserUpdateEvent());
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
        //环信账号登录
        ChatManager.getInstance().login(user.talkId, user.talkPwd, null);

        //添加友盟账号统计(用户登入)
        MobclickAgent.onProfileSignIn(user.uid);
        EventBus.getDefault().post(new AccountChangeEvent(true));
    }

    public void dealLogout(User u) {
        // 退出登录，记住手机号码
        mUser = getUser();
        boolean isLogin = isLogin();
        String uid = null;
        if (mUser != null && isLogin) {
            PrefUtil.putString(BaseApp.getApp(), Constant.KEY_USER_MOBILE, mUser.mobile);
            uid = mUser.uid;
        }
        mUser = null;
        UserDao userDao = new UserDao();
        if (u == null) {
            userDao.delUser();
            guestLogin();
        } else {
            userDao.saveUser(u);
        }

        if (!TextUtils.isEmpty(uid)) {
            clearPushAlias(uid);
        } else {
            // TODO
//            PushUtil.unBindAlias();
        }

        ShareSDK.initSDK(BaseApp.getApp());
        for (Platform platform : ShareSDK.getPlatformList()) {
            platform.removeAccount(true);
        }


        // 退出登录的同时，清除浏览器的cookie信息
        WebViewUtil.clearWebCache(BaseApp.getApp());
        //添加友盟账号统计(用户登出)
        MobclickAgent.onProfileSignOff();

        // 退出登录清除首页缓存
        // TODO
//        HomeManager.getInstance().clearHomeData();

        //环信退出登录
        ChatManager.getInstance().logout();

        if (isLogin) {
            EventBus.getDefault().post(new AccountChangeEvent(false));
        }
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
     * 获取验证码
     * type 0:`0`:默认, `1`:注册, `2`:手机动态码登陆, `3`:重置密码, 4 :修改手机号 ,5:绑定手机号
     */
    public void getVerifyCode(String mobile, int type, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("mobile", mobile);
        request.addQueryStringParameter("type", type + "");
        mHttpTools.get(UrlConst.USER_GET_VERIFY_CODE, request, callBack);
    }

    /**
     * 验证验证码
     *
     * @param mobile
     * @param code
     * @param |用户修改手机的时候传1 其他时候不传即可
     * @param callBack
     */
    public void checkVerifyCode(String mobile, String code, ResponseCallback callBack) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("mobile", mobile);
//        request.addQueryStringParameter("verify_code", code);
//
//        mHttpTools.get(UrlConst.USER_CHECK_VERIFY_CODE, request, callBack);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(final ResponseCallback<User> callback) {
        SignRequest request = new SignRequest();
        if (!TextUtils.isEmpty(getUser().uid)) {
            request.addQueryStringParameter("id", getUser().uid);
            mHttpTools.get(UrlConst.USER_GET_INFO, request, new UserUpdateCallback(callback) {

                @Override
                protected void onUpdate(User newUser) {
                    String key = getUser().key;
                    String uid = getUser().uid;
                    String token = getUser().token;
                    boolean isEmLogin = getUser().isEMLogin;

                    mUser = newUser;
                    mUser.key = key;
                    mUser.uid = uid;
                    mUser.token = token;
                    mUser.isEMLogin = isEmLogin;
                }
            });
        }
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @param callback
     */
    public void updateUser(User user, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.setJsonBody(user);
        mHttpTools.post(UrlConst.USER_UPDATE, request, new UserUpdateCallback(callback) {

            @Override
            protected void onUpdate(User newUser) {
                String key = getUser().key;
                String uid = getUser().uid;
                String token = getUser().token;
                boolean isEmLogin = getUser().isEMLogin;

                mUser = newUser;
                mUser.key = key;
                mUser.uid = uid;
                mUser.token = token;
                mUser.isEMLogin = isEmLogin;
            }
        });
    }

    /**
     * 修改头像
     *
     * @param avatar
     * @param callback
     */
    public void postUserAvatar(String avatar, ResponseCallback<User> callback) {
        SignRequest request = new SignRequest();
        request.addBodyParameter("uid", getUser().uid);
        request.addBodyParameter("avatar", avatar);
        mHttpTools.post(UrlConst.USER_UPDATE, request, new UserUpdateCallback(callback) {
            @Override
            public void onStart() {
            }

            @Override
            protected void onUpdate(User newUser) {
                getUser().avatar = newUser.avatar;
            }
        });
    }

    /**
     * 获取上传文件到七牛的TOKEN
     *
     * @param callBack
     */
    public void getQiniuToken(ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("uid", getUser().uid);
        request.setPath(UrlConst.GET_QINIU_TOKEN);
        mHttpTools.get(request, callBack);
    }

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
     * 给user设置别名
     */
    private void clearPushAlias(String uid) {
//        SignRequest signRequest = new SignRequest();
//        signRequest.addQueryStringParameter("uid", uid);
//        mHttpTools.delete(UrlConst.DELETE_PUSH_ALIAS, signRequest, new ResponseCallback<String>() {
//
//            @Override
//            public void onSuccess(String s) {
//                super.onSuccess(s);
//                PushUtil.unBindAlias();
//            }
//
//            @Override
//            public boolean isShowNotice() {
//                return false;
//            }
//        });
    }


    private class LoginCallback extends ResponseCallback<LoginInfo> {
        private ResponseCallback<User> callBack;

        public LoginCallback(ResponseCallback<User> callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onStart() {
            super.onStart();
            callBack.onStart();
        }

        @Override
        public void onSuccess(LoginInfo info) {
            super.onSuccess(info);
            User user = info.getUser();
            // 有uid才算登陆成功
            if (!TextUtils.isEmpty(user.uid)) {
                dealLogin(user);
            } else {
                mUser = user;
            }
            callBack.onSuccess(user);
        }

        @Override
        public void onFailure(Exception error) {
            super.onFailure(error);
            callBack.onFailure(error);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            callBack.onFinish();
        }

        @Override
        public boolean isShowNotice() {
            return callBack.isShowNotice();
        }
    }


    private abstract class UserUpdateCallback extends ResponseCallback<User> {
        private ResponseCallback<User> callBack;

        public UserUpdateCallback(ResponseCallback<User> callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onStart() {
            super.onStart();
            callBack.onStart();
        }

        protected abstract void onUpdate(User newUser);


        @Override
        public void onSuccess(User user) {
            super.onSuccess(user);
            if (user != null) {
                onUpdate(user);
            }

            new UserDao().updateUser(getUser());

            EventBus.getDefault().post(new UserUpdateEvent());
            callBack.onSuccess(user);
        }

        @Override
        public void onFailure(Exception error) {
            super.onFailure(error);
            callBack.onFailure(error);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            callBack.onFinish();
        }

        @Override
        public boolean isShowNotice() {
            return callBack.isShowNotice();
        }
    }

    public void setActivedInvitation(boolean actived_invitation) {
        getUser().actived_invitation = actived_invitation;
        new UserDao().updateUser(getUser());
        EventBus.getDefault().post(new UserUpdateEvent());
    }

//    /**
//     * 获取收藏列表
//     */
//    public void getFavoriteList(String uid, Map<String, String> more_params, FinalResponseCallback callback) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("uid", uid);
//        if (more_params != null) {
//            request.addQueryMapParameter(more_params);
//        }
//        mHttpTools.get(UrlConst.GET_FAVORITE_LIST, request, callback);
//    }
//

    /**
     * 获取分享收藏
     *
     * @param callBack
     */
    public void checkIsFavor(Map<String, String> params, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        request.addQueryMapParameter(params);
        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
        mHttpTools.get(UrlConst.CHECK_IS_FAVOR, request, callBack);
    }
//
//    /**
//     * 添加收藏页面
//     *
//     * @param articleid
//     * @param articleurl
//     * @param callback
//     */
//    public void addFavorite(String articleid, String articleurl, ResponseCallback callback) {
//        SignRequest request = new SignRequest();
//        request.addBodyParameter("uid", getUser().uid);
//        request.addBodyParameter("id", articleid);
//        request.addBodyParameter("articleurl", articleurl);
//        new HttpTools().post(UrlConst.ADD_FAVORITE, request, callback);
//    }

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
     * 查询未读消息和最新消息
     *
     * @param callback
     */
    public void getNewMsg(ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("uid", getUser().uid);
        mHttpTools.get(UrlConst.GET_NEW_MSG, signRequest, callback);
    }

    /**
     * 系统消息列表
     *
     * @param callback
     */
    public void getSystemMsg(HashMap<String, String> mMoreParams, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("uid", getUser().uid);
        if (mMoreParams != null) {
            signRequest.addQueryMapParameter(mMoreParams);
        }
        mHttpTools.get(UrlConst.GET_SYSTEM_MSG, signRequest, callback);
    }

    /**
     * 支付消息消息列表
     *
     * @param callback
     */
    public void getPayMsg(HashMap<String, String> mMoreParams, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("uid", getUser().uid);
        if (mMoreParams != null) {
            signRequest.addQueryMapParameter(mMoreParams);
        }
        mHttpTools.get(UrlConst.GET_PAY_MSG, signRequest, callback);
    }

    /**
     * @param callback
     */
    public void getCardType(ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("uid", getUser().uid);
        mHttpTools.post(UrlConst.GET_CARD_TYPE, signRequest, callback);
    }

    /**
     * 获取我的就诊卡
     *
     * @param callback
     */
    public void getCards(ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addBodyParameter("uid", getUser().uid);
        mHttpTools.post(UrlConst.GET_CARD_TYPE, signRequest, callback);
    }

    public void deleteACard(long id, String mediacl_card_no, String hospital_code, String card_type_code,ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addBodyParameter("uid", getUser().uid);
        signRequest.addBodyParameter("id", String.valueOf(id));
        signRequest.addBodyParameter("mediacl_card_no", mediacl_card_no);
        signRequest.addBodyParameter("hospital_code", hospital_code);
        signRequest.addBodyParameter("card_type_code", card_type_code);
        mHttpTools.post(UrlConst.POST_UPDATE_LOGIC_DELETE_CARD, signRequest, callback);
    }


    public void createCard(String hospital_code, long hospital_id, String hospital_name, String mediacl_card_no, String card_type_code, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.setPath(UrlConst.POST_CREATE_CARD);
        signRequest.addBodyParameter("uid", getUser().uid);
        signRequest.addBodyParameter("hospital_code", hospital_code);
        signRequest.addBodyParameter("hospital_id", "" + hospital_id);
        signRequest.addBodyParameter("hospital_name", hospital_name);
        signRequest.addBodyParameter("mediacl_card_no", mediacl_card_no);
        signRequest.addBodyParameter("card_type_code", card_type_code);
        mHttpTools.post(signRequest, callback);
    }

    public void createCardTWO(String mediacl_card_no, String card_type_code, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.setPath(UrlConst.POST_CREATE_CARD);
        signRequest.addBodyParameter("uid", getUser().uid);
        signRequest.addBodyParameter("mediacl_card_no", mediacl_card_no);
        signRequest.addBodyParameter("card_type_code", card_type_code);
        mHttpTools.post(signRequest, callback);
    }

    /**
     * 获取就诊卡
     *
     * @param callback
     */
    public void getCardType1(ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("uid", getUser().uid);
        signRequest.addQueryStringParameter("02", "02");
//        mHttpTools.post(UrlConst.GET_CARD_TYPE_ONE, signRequest, callback);
    }

    /**
     * 支付消息消息列表
     *
     * @param callback
     */
    public void upMsgState(String msgId, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("uid", getUser().uid);
        signRequest.addQueryStringParameter("messageId", "9227ec17288548b28718fe1f5d783dde");
        mHttpTools.get(UrlConst.GET_PAY_MSG, signRequest, callback);
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

        mHttpTools.get(UrlConst.QUERY_HOSPITAL_INFO, signRequest, callback);
    }

    /**
     * 评价列表
     * type:"hospital,doctor"
     *
     * @param callback
     */
    public void getEvaluateList(String type, String id, HashMap<String, String> mMoreParams, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        String url = "";
        if (EvaluateListActivity.EVALUATE_TYPE_HOSPITAL.equals(type)) {
            signRequest.addQueryStringParameter("hospitalId", id);
            url = UrlConst.HOSPITAL_EVALUATE_LIST;
        } else if (EvaluateListActivity.EVALUATE_TYPE_DOCTOR.equals(type)) {
            signRequest.addQueryStringParameter("doctorId", id);
            url = UrlConst.DOCTOR_EVALUATE_LIST;
        }

        if (mMoreParams != null) {
            signRequest.addQueryMapParameter(mMoreParams);
        }
        mHttpTools.get(url, signRequest, callback);
    }

    /**
     * 评价医院
     *
     * @param hospitalId
     * @param content
     * @param callback
     */
    public void postHospitalEvaluate(String hospitalId, String content, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addBodyParameter("uid", getUser().uid);
        request.addBodyParameter("hospitalId", hospitalId);
        request.addBodyParameter("content", content);
        mHttpTools.post(UrlConst.HOSPITAL_EVALUATE, request, callback);
    }

    /**
     * 评价医生
     *
     * @param doctorId
     * @param orderId
     * @param content
     * @param callback
     */
    public void postDoctorEvaluate(String doctorId, String orderId, String content, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addBodyParameter("uid", getUser().uid);
        request.addBodyParameter("doctorId", doctorId);
        request.addBodyParameter("orderId", orderId);
        request.addBodyParameter("content", content);
        mHttpTools.post(UrlConst.DOCTOR_EVALUATE, request, callback);
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
