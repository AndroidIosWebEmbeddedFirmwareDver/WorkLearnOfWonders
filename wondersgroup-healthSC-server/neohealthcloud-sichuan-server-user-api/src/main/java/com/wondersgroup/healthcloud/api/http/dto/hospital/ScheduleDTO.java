package com.wondersgroup.healthcloud.api.http.dto.hospital;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.entity.response.ScheduleNumInfo;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by dukuanxin on 2016/11/8.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {

    private String scheduleId;//排班id

    private String scheduleDate;//排班日期

    private String weekDay;

    private String startTime;

    private String endTime;

    private String hosOrgCode;

    private String hosName;

    private String hosDeptCode;

    private String deptName;

    private String hosDoctCode;

    private String doctName;

    private String visitLevel;

    private String visitCost;//出诊费用

    private String timeRange;//出诊时间段1:上午2:下午3:晚上

    private int isFull;//0-约满，1-可预约

    public ScheduleDTO(ScheduleNumInfo scheduleNumInfo) {
        this.scheduleId = scheduleNumInfo.getScheduleId();
        this.scheduleDate = scheduleNumInfo.getScheduleDate();
        if (!StringUtils.isEmpty(scheduleDate)) {
            this.weekDay = DateUtils.getWeek(scheduleDate);
        }
        if (scheduleNumInfo.getStartTime() != null) {
            this.startTime = scheduleNumInfo.getStartTime().substring(11, 19);
        }
        if (scheduleNumInfo.getEndTime() != null) {
            this.endTime = scheduleNumInfo.getEndTime().substring(11, 19);
        }
        this.visitLevel = scheduleNumInfo.getVisitLevel();
        this.visitCost = scheduleNumInfo.getVisitCost();
        this.timeRange = scheduleNumInfo.getTimeRange();
        if ((scheduleNumInfo.getReserveOrderNum() - scheduleNumInfo.getOrderedNum()) > 0) {
            this.isFull = 1;
        }
    }

    public static List<ScheduleDTO> infoDTO(List<ScheduleNumInfo> list) {
        List<ScheduleDTO> infos = Lists.newArrayList();
        if (list != null) {
            for (ScheduleNumInfo scheduleNumInfo : list) {
                if (scheduleNumInfo.getTimeRange().equals("3")) continue;//timeRang=3为晚上的排班，app上不展示晚上的排班信息
                infos.add(new ScheduleDTO(scheduleNumInfo));
            }
        }
        return infos;
    }
}


