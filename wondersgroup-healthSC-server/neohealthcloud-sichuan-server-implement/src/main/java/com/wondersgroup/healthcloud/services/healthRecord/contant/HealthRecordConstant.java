package com.wondersgroup.healthcloud.services.healthRecord.contant;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ys on 16-7-12.
 * 数据转换
 */
public class HealthRecordConstant {

    public static String coverMedicineUseType(String use_type) {
        String use_type_cover;
        use_type = StringUtils.isEmpty(use_type) ? "" : use_type.toLowerCase().trim();
        switch (use_type) {
            case "1":
                use_type_cover = "口服";
                break;
            case "2":
                use_type_cover = "直肠给药";
                break;
            case "3":
                use_type_cover = "舌下给药";
                break;
            case "4":
                use_type_cover = "注射给药";
                break;
            case "401":
                use_type_cover = "皮下注射";
                break;
            case "402":
                use_type_cover = "皮内注射";
                break;
            case "403":
                use_type_cover = "肌肉注射";
                break;
            case "404":
                use_type_cover = "静脉注射或静脉滴注";
                break;
            case "6":
                use_type_cover = "吸入给药";
                break;
            case "601":
                use_type_cover = "椎管内给药";
                break;
            case "602":
                use_type_cover = "关节腔内给药";
                break;
            case "603":
                use_type_cover = "胸膜腔给药";
                break;
            case "604":
                use_type_cover = "腹腔给药";
                break;
            case "605":
                use_type_cover = "阴道用药";
                break;
            case "606":
                use_type_cover = "滴眼";
                break;
            case "607":
                use_type_cover = "滴鼻";
                break;
            case "608":
                use_type_cover = "喷喉";
                break;
            case "609":
                use_type_cover = "含化";
                break;
            case "610":
                use_type_cover = "敷伤口";
                break;
            case "611":
                use_type_cover = "擦皮肤";
                break;
            case "6xx":
                use_type_cover = "局部用药扩充内容";
                break;
            case "699":
                use_type_cover = "其他局部给药途径";
                break;
            case "9":
                use_type_cover = "其他给药途径";
                break;
            case "rp":
                use_type_cover = "请取药";
                break;
            case "po":
                use_type_cover = "口服";
                break;
            case "inj":
                use_type_cover = "注射剂";
                break;
            case "mixt":
                use_type_cover = "合剂";
                break;
            case "tad":
                use_type_cover = "片剂";
                break;
            case "sol":
                use_type_cover = "溶液";
                break;
            case "co":
                use_type_cover = "复方";
                break;
            case "pr":
                use_type_cover = "灌肠";
                break;
            case "id":
                use_type_cover = "皮内注射";
                break;
            case "iv":
                use_type_cover = "静脉注射";
                break;
            case "iv gtt":
                use_type_cover = "静脉点滴";
                break;
            default:
                use_type_cover = use_type;
        }
        return use_type_cover;
    }

    public static String coverDayUsedNum(String day_used_num) {
        String day_used_num_cover;
        day_used_num = null == day_used_num ? "" : day_used_num.toLowerCase();
        switch (day_used_num) {
            case "qd":
                day_used_num_cover = "每日一次";
                break;
            case "bid":
                day_used_num_cover = "每日两次";
                break;
            case "tid":
                day_used_num_cover = "每日三次";
                break;
            case "qid":
                day_used_num_cover = "每日四次";
                break;
            case "qh":
                day_used_num_cover = "每小时一次";
                break;
            case "q1h":
                day_used_num_cover = "每小时一次";
                break;
            case "q2h":
                day_used_num_cover = "每两小时一次";
                break;
            case "q3h":
                day_used_num_cover = "每三小时一次";
                break;
            case "q4h":
                day_used_num_cover = "每四小时一次";
                break;
            case "q8h":
                day_used_num_cover = "每八小时一次";
                break;
            case "q12h":
                day_used_num_cover = "每12小时一次";
                break;
            case "qn":
                day_used_num_cover = "每晚一次";
                break;
            case "qod":
                day_used_num_cover = "每隔天一次";
                break;
            case "biw":
                day_used_num_cover = "每周两次";
                break;
            case "hs":
                day_used_num_cover = "临睡前";
                break;
            case "prn":
                day_used_num_cover = "必要时(长期)";
                break;
            case "sos":
                day_used_num_cover = "紧急时(限用1次，12小时有效)";
                break;
            default:
                day_used_num_cover = day_used_num;
        }
        return day_used_num_cover;
    }

}
