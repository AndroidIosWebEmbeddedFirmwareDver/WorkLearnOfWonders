package com.wondersgroup.healthcloud.api.http.dto.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayMessageDTO {
    private String id;
    private Integer type;
    private Integer payType;
    private String payTypeName;
    private Integer payStatus;
    private String orderId;
    private String hospitalName;
    private String patientName;
    private String department;
    private String doctorName;
    private String price;
    private String clinicType;
    private String prescriptionCode;
    private String prescriptionTime;
    private String orderTime;
    private String createDate;

    private String registerId;
    private String messageId;
    private int state;                                                              //0 未读 1 已读

    private static List<String> weekDays = Arrays.asList("周日", "周一", "周二", "周三", "周四", "周五", "周六");

    public String getPayTypeName() {
        if (payType != null && payType == 1) {
            payTypeName = "诊间支付";
        } else if (payType != null && payType == 2) {
            payTypeName = "挂号费支付";
        }
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    private static final Logger log = LoggerFactory.getLogger("exlog");

    public static PayMessageDTO mapToPayMessageDTO(Map<String, Object> map, String uid, String messageId) {
        PayMessageDTO message = new PayMessageDTO();
        if (map != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
            if (map.containsKey("id")) {
                message.setId(map.get("id").toString());
            }
            if (map.containsKey("type") && map.get("type") != null) {
                message.setType(Integer.valueOf(map.get("type").toString()));
            }
            if (map.containsKey("pay_type") && map.get("pay_type") != null) {
                message.setPayType(Integer.valueOf(map.get("pay_type").toString()));
            }
            if (map.containsKey("price") && map.get("price") != null) {
                message.setPrice(doubleTransform(Double.parseDouble(map.get("price").toString())));
            }
            if (map.containsKey("order_id") && map.get("order_id") != null) {
                message.setOrderId(map.get("order_id").toString());
            }
            if (map.containsKey("hospital_name") && map.get("hospital_name") != null) {
                message.setHospitalName(map.get("hospital_name").toString());
            }
            if (map.containsKey("patient_name") && map.get("patient_name") != null) {
                message.setPatientName(map.get("patient_name").toString());
            }
            if (map.containsKey("department") && map.get("department") != null) {
                message.setDepartment(map.get("department").toString());
            }
            if (map.containsKey("doctor_name") && map.get("doctor_name") != null) {
                message.setDoctorName(map.get("doctor_name").toString());
            }
            if (map.containsKey("pay_status") && map.get("pay_status") != null) {
                message.setPayStatus(Integer.parseInt(map.get("pay_status").toString()));
            }
            if (map.containsKey("clinic_type") && map.get("clinic_type") != null) {
                message.setClinicType(map.get("clinic_type").toString());
            }
            if (map.containsKey("prescription_code") && map.get("prescription_code") != null) {
                message.setPrescriptionCode(map.get("prescription_code").toString());
            }
            if (map.containsKey("prescription_time") && map.get("prescription_time") != null
                    && map.get("prescription_time").toString().length() > 15) {
                message.setPrescriptionTime(map.get("prescription_time").toString().substring(0, 16));
            }
            if (map.containsKey("order_time") && map.get("order_time") != null
                    && map.get("order_time").toString().length() > 10) {
                String orderTime = map.get("order_time").toString();
                try {
                    String day = weekDayNameByTime(format.parse(orderTime)) + " "
                            + ampmNameByTime(format.parse(orderTime));
                    format.parse(orderTime);
                    message.setOrderTime(orderTime.substring(0, 10) + " " + day);
                } catch (ParseException e) {
                    log.error("mapToPayMessageDTO error  " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (map.containsKey("create_date") && map.get("create_date") != null
                    && map.get("create_date").toString().length() > 10) {
                String time = map.get("create_date").toString();
                message.setCreateDate(time.substring(0, 10));
            }

            if (map.containsKey("register_id") && map.get("register_id") != null) {
                message.setRegisterId(map.get("register_id").toString());
            } else {
                message.setRegisterId(uid);
            }
            if (map.containsKey("message_id") && map.get("message_id") != null) {
                message.setMessageId(map.get("message_id").toString());
            } else {
                message.setMessageId(messageId);
            }
            if (map.containsKey("state") && map.get("state") != null) {
                message.setState(Integer.valueOf(map.get("state").toString()));
            }
        }
        return message;
    }

    public static List<PayMessageDTO> listToPayMessageDTO(List<Map<String, Object>> list, String uid, String messageId) {
        List<PayMessageDTO> result = new ArrayList<PayMessageDTO>();
        if (list != null) {
            for (Map<String, Object> map : list) {
                result.add(PayMessageDTO.mapToPayMessageDTO(map, uid, messageId));
            }
        }
        return result;
    }

    public static String ampmNameByTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.AM_PM) == 0 ? "上午" : "下午";
    }

    public static String weekDayNameByTime(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weekDays.get(week_index);
    }

    public static String doubleTransform(double d) {
        return String.format("%.2f", d);
    }
}
