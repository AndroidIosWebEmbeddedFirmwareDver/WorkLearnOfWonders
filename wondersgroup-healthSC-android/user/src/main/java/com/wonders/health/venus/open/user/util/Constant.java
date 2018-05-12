package com.wonders.health.venus.open.user.util;

import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2015/8/31 16:27
 */
public class Constant extends BaseConstant {

    /**
     * RSA公钥
     */
    public static final String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg6C5REWkOuDIwHxyjPpCh1YhxXt/Ej+mK+PJh6KJbI5Pzpr7g+S7kmJaRwLo+9OC0OZcdJu36JsoH9FrGetABp98h3Y92gSTdWZMVuhN+xQZ2pOcJavcfPH621R3CIkrY54XbaeDe3nOSsCYQjAuZFpXYGyiuaiUVuYSZe1BtdOQpk6qmXlG8VynOiu8UcqgqoVVwRMQmCH6lLRO9IIqyc5O9haVbdEPN6OPgkQJjfV9DpznY/7jlYhqGTET//sOqRmhyiWM03IbxpqHDMJDIFT/qrUfwqCLpCMv93BehUbS/ONPrbeaNWA8+FHx+GdPM+rm/T4FEqz+AB236/SInQIDAQAB";

    // 用户信息
    public static final String KEY_USER_INFO = "user_info";

    // 全局变量KEY
    public static final String KEY_APP_CONFIG = "key_app_config";

    // 本地版本号
    public static final String KEY_VERSION_CODE = "key_version_code";

    // 是否完成基础数据录入
    public static final String KEY_HAS_FINISH_PRE_DATA = "key_has_finish_pre_data";

    // 基础数据
    public static final String KEY_PRE_DATA = "key_pre_data";

    // 首页缓存数据KEY
    public static final String KEY_HOME_DATA = "key_home_data";
    // 健康页缓存数据KEY
    public static final String KEY_HEALTH_DATA = "key_health_data";
    // 健康页缓存资讯数据KEY
    public static final String KEY_HEALTH_ARTICLE_DATA = "key_health_article_data";
    //电话图
    public static final String CALL_IMAGE_URL = "call_image_url";
    //首页缓存资讯
    public static final String KEY_HOME_NEWS = "key_home_news";
    //
    public static final String KEY_HOME_LOCAL = "key_home_local";

    // 用户手机号码
    public static final String KEY_USER_MOBILE = "user_mobile";
    //推送开关
    public static final String IS_OPEN_NOTIFY = "is_open_notify";

    //App区域版本
    public static final String APP_MAIN_AREA = "51";

    //体征数据来源手输
    public static final String HEALTH_DATA_SOURCE_INPUT = "1";
    //体征数据来源全程设备
    public static final String HEALTH_DATA_SOURCE_FW = "2";
    //体征数据来源蓝牙设备
    public static final String HEALTH_DATA_SOURCE_BLUETOOTH = "3";

    // 风险评估
    public static final String KEY_RISK = "key_risk";
    // 本地风险评估
    public static final String KEY_LOCAL_RISK = "risk2Result";

    // 快捷地区选择
    public static final String KEY_QUICK_AREA_SELECT = "quick_area_select";

    // 弹出允许定位框
    public static final String KEY_NEED_SHOW_LOCATION_DAILOG = "need_show_location_dialog";

    // 是否允许定位
    public static final String KEY_ALLOW_LOCATION = "key_allow_location";

    //提交信息时是否显示弹出框
    public static final String KEY_FIRST_SHOW_DIALOG = "key_first_show_dialog";

    //提取报告本地保存医院信息
    public static final String KEY_EXTRACT_HOSPITAL_INFO="key_extract_hospital_info";

    //在线支付本地医院保存
    public static final String KEY_PAY_HOSPITAL_INFO="key_pay_hospital_info";

    public static final int TYPE_INIT = 0;
    public static final int TYPE_RELOAD = 1;
    public static final int TYPE_NEXT = 2;

    public static final int REQUEST_CODE_PICK = 3001;
    public static final int REQUEST_CODE_TAKE_PHOTO = 3002;
    public static final int REQUEST_CODE_CROP = 3003;


}
