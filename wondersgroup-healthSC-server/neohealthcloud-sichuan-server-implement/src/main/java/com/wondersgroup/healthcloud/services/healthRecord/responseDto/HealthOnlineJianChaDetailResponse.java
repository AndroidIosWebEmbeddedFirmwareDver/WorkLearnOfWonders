package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author ys
 * 提取报告 - 门诊检查报告详情
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthOnlineJianChaDetailResponse implements Serializable {

    public String resultCode;
    public String resultDesc;

    @XmlElementWrapper(name = "examDetails")
    @XmlElement(name = "examDetail")
    private List<ExamDetail> examDetail;

    @Data
    public static class ExamDetail {
        private String name;//姓名
        private String sex;
        private String age;
        private String serialNum;//就诊流水号
        private String dept;//科室
        private String imageID;//影像号
        private String examDate;//检查日期
        private String examType;//检查类型
        private String examName = "-";//检查名称
        private String examSite = "-";//检查部位
        private String image;//影像表现
        private String imageDiagnosis;//影像诊断
        private String reportDoc;//报告医生
        private String reportDate;//报告日期
        private String audit;//审核医生
        private String auditDate;//审核日期
        private String examNo;//检查号
        private String clinicalDiagnosis = "无";//诊断结果
        private String remark = "无";//医嘱

        public String getExamDate() {
            String date = StringUtils.isEmpty(examDate) ? reportDate : examDate;
            return StringUtils.length(date) >= 10 ? date.substring(0, 10) : "";
        }

        public String getReportDate() {
            String date = StringUtils.isEmpty(reportDate) ? examDate : reportDate;
            return StringUtils.length(date) >= 10 ? date.substring(0, 10) : "";
        }
    }

}
