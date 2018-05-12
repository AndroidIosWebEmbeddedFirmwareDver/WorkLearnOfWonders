package com.wondersgroup.healthcloud.services.evaluate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by ys on 16/11/2.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppEvaluateHospitalListDTO {

    private Integer id;
    private String uid;
    private String nickName;
    @JsonIgnore
    private String mobile;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    public String getNickName() {
        if (StringUtils.isNotEmpty(nickName)) {
            nickName = StringUtils.rightPad(nickName.substring(0, 1), nickName.length(), "*");
        } else if (StringUtils.isNotEmpty(mobile)) {
            nickName = "微健康用户" + mobile.substring(mobile.length() - 4);
        } else {
            nickName = "微健康用户" + uid.substring(0, 4);
        }
        return nickName;
    }
}
