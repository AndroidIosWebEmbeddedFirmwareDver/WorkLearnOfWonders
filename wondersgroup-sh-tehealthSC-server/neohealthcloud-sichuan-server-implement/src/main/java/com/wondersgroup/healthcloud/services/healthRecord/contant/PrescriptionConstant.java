package com.wondersgroup.healthcloud.services.healthRecord.contant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ys on 16-11-5.
 * 处方枚举字段定义
 */
public class PrescriptionConstant {

    public static final Map<String, String> prescriptionBigType = new HashMap<>();

    public static final Map<String, String> prescriptionType = new HashMap<>();

    static {
        prescriptionBigType.put("01", "药品处方");
        prescriptionBigType.put("0101", "西药处方");
        prescriptionBigType.put("0102", "中成药处方");
        prescriptionBigType.put("0103", "西药处方");
        prescriptionBigType.put("02", "检验申请单");
        prescriptionBigType.put("03", "检查申请单");
        prescriptionBigType.put("04", "手术申请单");
        prescriptionBigType.put("99", "其他");

        prescriptionType.put("01", "普通处方");
        prescriptionType.put("02", "急诊处方");
        prescriptionType.put("03", "儿科处方");
        prescriptionType.put("04", "麻醉药品和第一类精神药品处方");
        prescriptionType.put("05", "第二类精神药品处方");
        prescriptionType.put("99", "无法归类处方");
    }

    public static String getPrescriptionBigType(String type) {
        type = type.length() == 1 ? "0" + type : type;
        return prescriptionBigType.containsKey(type) ? prescriptionBigType.get(type) : "其他";
    }

    public static String getPrescriptionType(String type) {
        type = type.length() == 1 ? "0" + type : type;
        return prescriptionType.containsKey(type) ? prescriptionType.get(type) : "其他";
    }

}
