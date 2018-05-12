package com.wondersgroup.healthcloud.familydoctor.response.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 签约居民列表
 * Created by jialing.yao on 2017-8-3.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractResidentResponse {
    private String hzxxHzxm;//患者姓名
    private String hzxxSex;//性别
    private String hzxxNl = "0";//年龄
    private String hzxxJzdz;//居住地址
    private String sfzdrk = "0";//是否重点人口(0：否  1：是)
    private String sfpkrk = "0";//是否贫困人口(0：否  1：是)

}
