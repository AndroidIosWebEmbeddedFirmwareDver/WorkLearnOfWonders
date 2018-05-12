package com.wonders.health.venus.open.user.entity;


/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/1/7 21:32
 */
public class AppointmentDetail extends RegistrationListVO {

    /**
     * doctor_name :
     * hospital_name :
     * status :
     * location :
     * patient_name :
     * patient_mobile :
     * patient_idcard :
     * clinic_type :
     * patient_type :
     * id :
     * fee :
     * order_time :
     * pay_type :
     * start_time :
     * department_name :
     * fetch_number_location :
     */
    public String location;
    public String patient_mobile;
    public String patient_idcard;
    public String clinic_type;
    public String patient_type;
    public String patient_name;
    public String fee;
    public String order_time;
    public String pay_type;
    public String start_time;
    public String fetch_number_location;
    public String hospital_name;
    public String department_name;
    public String doctor_name;
    public String id;
    public boolean can_cancel;

    public String getStatusStr() {
        if ("1".equals(status)) {
            return "待诊";
        } else if ("2".equals(status)) {
            return "已就诊";
        } else if ("3".equals(status)) {
            return "爽约";
        } else if ("4".equals(status)) {
            return "已取消";
        } else if ("5".equals(status)) {
            return "已评价";
        } else {
            return "未知";
        }
    }
}
