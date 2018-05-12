package com.wondersgroup.healthcloud.familydoctor.response.referral;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 转诊列表(全部/申请中/已驳回/已转诊/已取消)
 * Created by jialing.yao on 2017-8-3.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutpatientListResponse {
    private String mzzz_hzxm;//患者姓名
    private String mzzz_hzsfzh;//患者身份证号码
    private String mzzz_fqsj;//发起时间
    private String mzzz_zrjgdm;//转入医院代码
    private String mzzz_fqjgdm;//发起医院代码
    private String mzzz_state;//流程环节状态
    private String mzzz_zrjgmc;//转入医院名称
    private String mzzz_fqjgmc;//发起医院名称
    private String mzzz_jjcd;//紧急程度(一般 紧急)
    private String mzzz_zzlx;//转诊类型（上转／下转）
    private String referral_code;//转诊编码
}
