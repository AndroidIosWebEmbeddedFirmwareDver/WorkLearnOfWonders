package com.wondersgroup.healthcloud.familydoctor.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 分页
 * Created by jialing.yao on 2017-8-3.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonListResponse<T> {
    private Integer totalCount;
    private List<T> list;
}
