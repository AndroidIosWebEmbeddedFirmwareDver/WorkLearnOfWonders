package com.wonders.health.venus.open.user.entity;


import java.io.Serializable;

/**
 * Created by wang on 2016/11/8.
 */

public class ReportItemEntity implements Serializable{

        public String id;
        public String date;
        public String hospital;
        public String department;
        public String money;
        public String cfhm;//处方号码
        public String cflx;//处方类型
        public String yljgdm;//医疗机构代码
        public String yljgmc;//医疗机构名称
        public String kfksdm;//开方科室代码
        public String kfksmc;//开方科室名称
        public String kfysbh;//开方医生编号
        public String kfysxm;//开方医生姓名
        public String kfsj;//开方时间
        public String cfje;//处方金额 单位：分
        public String zfzt;//支付状态

}
