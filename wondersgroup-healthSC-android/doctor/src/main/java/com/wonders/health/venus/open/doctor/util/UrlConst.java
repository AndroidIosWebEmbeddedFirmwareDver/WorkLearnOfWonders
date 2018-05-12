package com.wonders.health.venus.open.doctor.util;


import com.wonders.health.venus.open.doctor.BuildConfig;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/8/10 10:09
 */
public class UrlConst {
    public static final String BASE_URL = BuildConfig.SERVER_IP;

    //重置密码
    public static final String RESET_PWD = BASE_URL + "/user/password";
    //重置密码
    public static final String UPDATE_PWD = BASE_URL + "/user/password/update";
    //查询未读消息和最新消息
    public static final String GET_NEW_MSG = BASE_URL + "/message/newMessage";
    //系统消息列表
    public static final String GET_SYSTEM_MSG = BASE_URL + "/message/queryList";
    //支付消息列表
    public static final String GET_PAY_MSG = BASE_URL + "/message/pay/queryList";
    //更新消息状态
    public static final String UPDATE_MSG_STATE = BASE_URL + "/message/update";
    //医院详情查询
    public static final String QUERY_HOSPITAL_INFO = BASE_URL + "/queryHospitlInfo";
    //医院评价列表
    public static final String HOSPITAL_EVALUATE_LIST = BASE_URL + "/evaluate/hospital/list";
    //对医院评价
    public static final String HOSPITAL_EVALUATE = BASE_URL + "/evaluate/hospital/publish";
    //医生评价列表
    public static final String DOCTOR_EVALUATE_LIST = BASE_URL + "/evaluate/doctor/list";
    //对医生评价
    public static final String DOCTOR_EVALUATE = BASE_URL + "/evaluate/doctor/publish";
    public static final String QUERY_HOSPITL_INFO = BASE_URL + "/queryHospitlInfo";

    // 获取排班列表
    public static final String QUERY_SCHEDULE_INFO = BASE_URL + "/querySchedulInfo";
    //按日期预约医生列表查询
    public static final String QUERY_SCHEDUL_BY_TIME = BASE_URL + "/querySchedulByTime";
    //查询医生详情
    public static final String QUERY_DOCTOR_INFO = BASE_URL + "/queryDoctorInfo";
    //获取服务端系统时间
    public static final String GET_SYSTEM_TIME = BASE_URL + "/getSystemTime";
    //提交订单
    public static final String SUBMIT_ORDER = BASE_URL + "/order/submitOrder";
    //关注取关
    public static final String SET_ATTENTION = BASE_URL + "/favorite/doctor/addDel";
    //我的订单
    public static final String GET_ORDER_LIST = BASE_URL + "/interDiaPayment/orders";
    //诊间支付获取待缴费记录
    public static final String GET_UNPAYRECORDS_LIST = BASE_URL + "/interDiaPayment/unPayRecord";
    //挂号费支付
    public static final String GET_APPOINT_LIST = BASE_URL + "/interDiaPayment/appoint";
    //生成诊间支付订单
    public static final String GENERATE_CLINIC_ORDER = BASE_URL + "/interDiaPayment/generateOrder";

    /*-------------------------user-----------------------------------*/
    // 用户登录
    public static final String USER_LOGIN = BASE_URL + "/token";
    // 微信登录
    public static final String USER_WECHAT_LOGIN = BASE_URL + "/token/thirdparty/wechat";
    // 微博登录
    public static final String USER_WEIBO_LOGIN = BASE_URL + "/token/thirdparty/weibo";
    // QQ登录
    public static final String USER_QQ_LOGIN = BASE_URL + "/token/thirdparty/qq";
    // 验证码登录
    public static final String USER_VERIFY_CODE_LOGIN = BASE_URL + "/token/code";
    // 匿名用户登录
    public static final String USER_GUEST_LOGIN = BASE_URL + "/token/guest";
    // 获取验证码
    public static final String USER_GET_VERIFY_CODE = BASE_URL + "/sms";
    // 验证验证码
    public static final String USER_CHECK_VERIFY_CODE = BASE_URL + "/sms/verification";
    // 获取用户信息
    public static final String USER_GET_INFO = BASE_URL + "/user";
    //修改用户信息
    public static final String USER_UPDATE = BASE_URL + "/user/info";
    // 忘记密码
    public static final String USER_REST_PWD = BASE_URL + "/user/password";
    // 绑定手机号
    public static final String USER_BIND_MOBILE = BASE_URL + "/user/mobile";
    //退出登录
    public static final String USER_LOGOUT = BASE_URL + "/token";


    /*-------------------------首页-----------------------------------*/
    //底部tab
    public static final String APP_NAVIGATION_BAR = BASE_URL + "/common/appNavigationBar";
    //首页Banner,功能图标及广告接口
    public static final String HOME_BANNER_FUNCTION_ADS = BASE_URL + "/home";
    //特殊服务
    public static final String HOME_SPECSERMEASURINGPOINT = BASE_URL + "/spec/home/specSerMeasuringPoint";
    //首页通知
    public static final String HOME_APPTIPS = BASE_URL + "/home/appTips";
    //问答集锦和新闻资讯
    public static final String HOME_NEWSANDQUESTIONS = BASE_URL + "/spec/home/newsAndQuestions";

    public static final String SERVICE_HOME = BASE_URL + "/spec/services/info";
    public static final String REGISTRATION_HOSPITAL_LIST = BASE_URL + "/queryHospitls";
    public static final String REGISTRATION_DEPARTMENT1_LIST = BASE_URL + "/queryDeps";
    public static final String REGISTRATION_DOCTOR_LIST = BASE_URL + "/queryDoctorList";

    //电子处方列表
    public static final String PRESCRIPTION_LIST = BASE_URL + "/eprescription/list";
    //电子处方详情
    public static final String PRESCRIPTION_DETAIL = BASE_URL + "/eprescription/details";
    //中医体质辨识
    public static final String TIZHI_RESULT = BASE_URL + "/physicalIdentify";
    //健康页首页
    public static final String HEALTH_HOME = BASE_URL + "/health/home";
    //资讯标题
    public static final String ZIXUN_CATEGORY = BASE_URL + "/article/articleCategoty";
    //资讯列表详情
    public static final String ZIXUN_LIST = BASE_URL + "/article/articleList";
    //资讯详情
    public static final String ZIXUN_DETAIL = BASE_URL + "/article/detail";
    //分享
    public static final String CHECK_IS_FAVOR = BASE_URL + "/articleFavorite/checkIsFavor";

    /*----------------------搜索------------------*/
    //首页搜索
    public static final String SEARCH_HOME = BASE_URL + "/home/search/all";
    //搜索医院列表
    public static final String SEARCH_HOSPITALLIST = BASE_URL + "/home/search/hospital";
    //搜索医生列表
    public static final String SEARCH_DOCTORLIST = BASE_URL + "/home/search/doctor";
    //搜索资讯列表
    public static final String SEARCH_ARTICLELIST = BASE_URL + "/home/search/article";
    //附近医院列表
    public static final String NEARBY_HOSPITALLIST = BASE_URL + "/hospital/near/list";

    // 获取全局数据
    public static final String GET_APP_CONFIG = BASE_URL + "/common/appConfig";

    //获取七牛token
    public static final String GET_QINIU_TOKEN = BASE_URL + "/common/getQiniuToken";
    //实名认证信息提交
    public static final String AUTH_SUBMIT = BASE_URL + "/verification/submit";
    //实名认证信息查询
    public static final String AUTH_QUERY=BASE_URL+"/verification/submit/info";
    //关注医生列表
    public static final String ATTENTION_DOCTOR=BASE_URL+"/favorite/doctor/list";
    //关注医院列表
    public static final String ATTENTION_HOSPITAL=BASE_URL+"/favorite/hospital/list";
    //关注（取消）医院
    public static final String FAVORITE_HOSPITAL=BASE_URL+"/favorite/hospital/addDel";
    /*---------------------提取报告-------------------------*/

    //推荐医院
    public static final String SEARCH_RECOMMEND = BASE_URL + "/search/recommend";
    public static final String HOME_SEARCH_RECOMMEND = BASE_URL + "/home/search/hospital";
    //检验列表
    public static final String INSPECTION_LIST = BASE_URL + "/healthOnline/jianyan/list";
    //检查列表
    public static final String CHECKREPORT_LIST = BASE_URL + "/healthOnline/jiancha/list";
    /*--------------------我的预约-----------------------*/


    //获取预约 列表
    public static final String GET_APPOINTMENT_LIST=BASE_URL+"/order/getOrderList";
    public static final String GET_APPOINTMENT_DETAL=BASE_URL+"/order/getOrderDetail";
    public static final String CANCEL_APPOINTMENT=BASE_URL+"/order/cancelOrder";
    public static final String EVALUATE_DOCTOR=BASE_URL+"/evaluate/doctor/publish";

    // 获取支付参数
    public static final String GET_PAY_KEY = BASE_URL+"/pay/key";
    // 获取订单支付信息
    public static final String GET_PAY_INFO = BASE_URL+"/pay/info";

    //签约患者列表
    public static final String GET_SIGN_PATIENT_LIST =  BASE_URL+"/patient/list";
    //新增签约患者红点提示
    public static final String GET_NEW_PATIENT_TIP = BASE_URL+"/patient/new/prompt";
    //新增签约患者列表
    public static final String GET_NEW_PATIENT_LIST = BASE_URL+"/patient/new/list";
    //首页红点
    public static final String PROMPT = BASE_URL + "/index/prompt";
    public static final String MY_COUNT = BASE_URL + "/my/count";
    public static final String TEAM_LIST = BASE_URL + "/my/team/list";
    public static final String FEEDBACK = BASE_URL + "/my/help/feedback";
}

















