package com.wonders.health.venus.open.user.logic;

import android.text.TextUtils;

import com.wonders.health.venus.open.user.entity.ContactVO;
import com.wonders.health.venus.open.user.entity.PayInfo;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.HashMap;

/**
 * Created by sunning on 16/1/6.
 * 挂号 联系人(就诊人)Manager
 */
public class RegistrationManager {

    private static RegistrationManager sInstance;

    private HttpTools mHttpTools;

    public static RegistrationManager getInstance() {
        if (sInstance == null) {
            synchronized (RegistrationManager.class) {
                if (sInstance == null) {
                    sInstance = new RegistrationManager();
                }
            }
        }
        return sInstance;
    }

    private RegistrationManager() {
        mHttpTools = new HttpTools();
    }

//    /**
//     * 添加就诊人
//     *
//     * @param contactListVO
//     * @param callBack
//     */
//    public void addContact(ContactVO contactListVO, ResponseCallback callBack) {
//        if (contactListVO == null) {
//            return;
//        }
//        SignRequest request = new SignRequest();
//        request.addBodyParameter("uid", UserManager.getInstance().getUser().uid);
//        request.addBodyParameter("name", contactListVO.getName());
//        request.addBodyParameter("idcard", contactListVO.getIdcard());
//        request.addBodyParameter("mobile", contactListVO.getMobile());
//        request.addBodyParameter("isDefault", String.valueOf(contactListVO.getIsDefault()));
//        mHttpTools.post(UrlConst.REGISTRATION_CONTACTS_ADD, request, callBack);
//    }
//
//    /**
//     * 删除就诊人
//     *
//     * @param id       就诊人ID
//     * @param callBack
//     */
//    public void deleteContact(String id, ResponseCallback callBack) {
//        if (TextUtils.isEmpty(id)) {
//            return;
//        }
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("id", id);
//        mHttpTools.delete(UrlConst.REGISTRATION_CONTACTS_DELETE, request, callBack);
//    }
//
//    /**
//     * 查询就诊人列表
//     *
//     * @param callBack
//     */
//    public void queryContactList(ResponseCallback callBack) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
//        mHttpTools.get(UrlConst.REGISTRATION_CONTACTS_LIST, request, callBack);
//    }
//
//    /**
//     * 获取联系人详情
//     *
//     * @param cID      联系人ID
//     * @param callBack 异步回调
//     */
//    public void queryContactDetail(String cID, ResponseCallback callBack) {
//        if (!TextUtils.isEmpty(cID)) {
//            SignRequest request = new SignRequest();
//            request.addQueryStringParameter("id", cID);
//            mHttpTools.get(UrlConst.REGISTRATION_CONTACTS_DETAIL, request, callBack);
//        }
//    }
//
//    /**
//     * 设置默认就诊人
//     *
//     * @param contact_id 联系人ID
//     * @param callBack   异步回调
//     */
//    public void setContactDefault(String contact_id, ResponseCallback callBack) {
//        if (!TextUtils.isEmpty(contact_id)) {
//            SignRequest request = new SignRequest();
//            request.addBodyParameter("uid", UserManager.getInstance().getUser().uid);
//            request.addBodyParameter("contact_id", contact_id);
//            mHttpTools.post(UrlConst.REGISTRATION_CONTACTS_DEFAULT, request, callBack);
//        }
//    }
//
//    /**
//     * 发送验证短信
//     *
//     * @param contact_id
//     * @param callback
//     */
//    public void getAppointmentVerifyCode(String contact_id, ResponseCallback callback) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("contact_id", contact_id);
//        mHttpTools.get(UrlConst.REGISTRATION_VERIFY_CODE, request, callback);
//    }
//
//
//    /**
//     * 查询我的预约
//     *
//     * @param callback
//     */
//    public void getMyAppointments(HashMap<String, String> moreParams, ResponseCallback callback) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
//        request.addQueryMapParameter(moreParams);
//        mHttpTools.get(UrlConst.USER_APPOINTMENT_LIST, request, callback);
//    }
//

    /*
     * 提交订单
     *
     * @param payInfo 支付封装的对象
     * @param callback
     */
    public void submitOrder(PayInfo payInfo, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.setPath(UrlConst.SUBMIT_ORDER);
        request.addBodyParameter("scheduleId", payInfo.scheduleId);
        request.addBodyParameter("payMode", String.valueOf(payInfo.payMode));
        request.addBodyParameter("payState", String.valueOf(payInfo.payState));
        request.addBodyParameter("orderTime", payInfo.orderTime);
        request.addBodyParameter("uid", payInfo.uid);
        request.addBodyParameter("userCardType", payInfo.userCardType);
        request.addBodyParameter("userCardId", payInfo.userCardId);
        request.addBodyParameter("userName", payInfo.userName);
        request.addBodyParameter("userPhone", payInfo.userPhone);
        request.addBodyParameter("visitCost", payInfo.visitCost);
        request.addBodyParameter("visitLevel", payInfo.visitLevel);
        if(!TextUtils.isEmpty(payInfo.numSourceId)){
            request.addBodyParameter("numSourceId", payInfo.numSourceId);
        }
        request.addBodyParameter("deptName", payInfo.deptName);
        request.addBodyParameter("hosDeptCode", payInfo.hosDeptCode);
        request.addBodyParameter("hosName", payInfo.hosName);
        request.addBodyParameter("hosOrgCode", payInfo.hosOrgCode);
        request.addBodyParameter("hosDoctCode", payInfo.hosDoctCode);
        request.addBodyParameter("doctName", payInfo.doctName);
        request.addBodyParameter("mediCardId", payInfo.mediCardId);
        mHttpTools.post(request, callback);
    }

    /**
     * 获取医生列表
     * @param hospitalCode 医院代码
     * @param hosDeptCode 科室代码
     * @param callBack
     */
    public void queryDoctorList(String hospitalCode, String hosDeptCode,HashMap<String, String> more_params, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        if (more_params != null) {
            request.addQueryMapParameter(more_params);
        }
        request.addQueryStringParameter("hospitalCode", hospitalCode);
        request.addQueryStringParameter("hosDeptCode", hosDeptCode);
        request.setPath(UrlConst.REGISTRATION_DOCTOR_LIST);
        mHttpTools.get(request, callBack);
    }
//
//    /**
//     * 取消预约
//     *
//     * @param callBack
//     * @param id       预约单号
//     */
//    public void cancelRegistration(String id, ResponseCallback callBack) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("id", id);
//        mHttpTools.delete(UrlConst.CANCEL_REGISTRATION, request, callBack);
//    }queryDoctorList
//
//
//    /**
//     * 预约挂号
//     *
//     * @param schedule_id 排班id
//     * @param contact_id  联系人id
//     * @param vCode       验证码
//     * @param callBack
//     */
//    public void registrationAppointment(String schedule_id, String contact_id, String vCode, String orderType, int patientType, ResponseCallback callBack) {
//        SignRequest request = new SignRequest();
//        request.addBodyParameter("schedule_id", schedule_id);
//        request.addBodyParameter("contact_id", contact_id);
//        request.addBodyParameter("order_type", orderType);
//        request.addBodyParameter("pay_mode", "3");//窗口支付
//        request.addBodyParameter("patient_type", String.valueOf(patientType));
//        request.addBodyParameter("code", vCode);
//        mHttpTools.post(UrlConst.REGISTRATION, request, callBack);
//    }
//
//
//    /**
//     * 搜索医生和医院
//     * @param callBack
//     */
//    public void queryAllByKeyWord(String keyWord, HashMap<String, String> more_params, ResponseCallback callBack){
//        SignRequest request = new SignRequest();
//        if (more_params != null) {
//            request.addQueryMapParameter(more_params);
//        }
//        request.addQueryStringParameter("kw", keyWord);
//        mHttpTools.get(UrlConst.GET_REG_DOCTORS_AND_HOSPITALS, request, callBack);
//    }

    /**
     * 关注和取消关注
     * SET_ATTENTION
     */
    public void setAttention(String doctorId, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        request.setPath(UrlConst.SET_ATTENTION);
        request.addBodyParameter("doctorId", doctorId);
        request.addBodyParameter("uid", UserManager.getInstance().getUser().uid);
        mHttpTools.post(request, callBack);
    }


    /**
     * 获取一级科室
     *
     * @param hospitalID
     * @param callBack
     */
    public void queryFirstDepartment(String hospitalID, ResponseCallback callBack) {
        if (!TextUtils.isEmpty(hospitalID)) {
            SignRequest request = new SignRequest();
            request.setPath(UrlConst.REGISTRATION_DEPARTMENT1_LIST);
            request.addQueryStringParameter("hospitalCode", hospitalID);
            mHttpTools.get(request, callBack);
        }
    }

    /**
     * 获取二级科室
     *
     * @param hospitalID
     * @param firstLevelId
     * @param callBack
     */
    public void querySecondDepartment(String hospitalID, String firstLevelId, ResponseCallback callBack) {
        if (!TextUtils.isEmpty(hospitalID)) {
            SignRequest request = new SignRequest();
            request.addQueryStringParameter("hospitalCode", hospitalID);
            request.addQueryStringParameter("hosDeptCode", firstLevelId);
            request.setPath(UrlConst.REGISTRATION_DEPARTMENT1_LIST);
            mHttpTools.get(request, callBack);
        }
    }

    /**
     * 获取医院列表
     *
     * @param moreParams
     * @param callBack
     */
    public void getHospitalList (HashMap<String, String> moreParams, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        request.addQueryStringParameter("cityCode", AreaManager.getInstance().getLocalPoint() == null ? AreaManager.AREA_CODE_CHENGDU:AreaManager.getInstance().getLocalPoint().area_id);
        request.setPath(UrlConst.REGISTRATION_HOSPITAL_LIST);
        mHttpTools.get(request, callBack);
    }

    /**
     * 获取医院列表
     *
     * @param hospitalCode 医院代码
     * @param hosDeptCode 科室代码
     * @param time 日期
     * @param moreParams
     * @param callBack
     */
    public void getDoctorByDate(String hospitalCode, String hosDeptCode, String time, HashMap<String, String> moreParams, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        request.addQueryStringParameter("hospitalCode", hospitalCode);
        request.addQueryStringParameter("hosDeptCode", hosDeptCode);
        request.addQueryStringParameter("time", time);
        request.setPath(UrlConst.QUERY_SCHEDUL_BY_TIME);
        mHttpTools.get(request, callBack);
    }

    /**
     * 获取系统时间
     * @param callBack
     */
    public void getSystemTime(ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        request.setPath(UrlConst.GET_SYSTEM_TIME);
        mHttpTools.get(request, callBack);
    }

    /**
     * 获取排班列表
     *
     * @param hospitalCode 医院代码
     * @param hosDeptCode 科室代码
     * @param hosDoctCode 医生代码
     * @param callBack
     */
    public void getScheduleInfo(String hospitalCode, String hosDeptCode, String hosDoctCode, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("hospitalCode", hospitalCode);
        request.addQueryStringParameter("hosDeptCode", hosDeptCode);
        request.addQueryStringParameter("hosDoctCode", hosDoctCode);
        request.setPath(UrlConst.QUERY_SCHEDULE_INFO);
        mHttpTools.get(request, callBack);
    }

    /**
     * 获取医生详情
     *
     * @param hospitalCode 医院代码
     * @param hosDeptCode  科室代码
     * @param hosDoctCode  医生ID
     * @param callBack
     */
    public void queryDoctorDetail(String hospitalCode, String hosDeptCode, String hosDoctCode, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        request.setPath(UrlConst.QUERY_DOCTOR_INFO);
        request.addQueryStringParameter("hospitalCode", hospitalCode);
        request.addQueryStringParameter("hosDeptCode", hosDeptCode);
        request.addQueryStringParameter("hosDoctCode", hosDoctCode);
        request.addQueryStringParameter("userId", UserManager.getInstance().getUser().uid);
        mHttpTools.get(request, callBack);
    }

    public static class RegistrationContactResponse extends BaseListResponse<ContactVO> {
    }

}
