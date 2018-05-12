//
//  SCMyPreorderListsModel.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCMyPreorderListsModel : BaseModel

    //    cardType = 01;
    //    cost = 1;
    //    createTime = 1479181264000;
    //    date = "2016-11-16 17:10:00";
    //    delFlag = 0;
    //    deptCode = 100307;
    //    deptName = "专家门诊";
    //    doctCode = 133;
    //    doctName = "周继明";
    //    hosCode = 450755531;
    //    hosName = "成都市第一人民医院";
    //    id = 13;
    //    numSource = "";
    //    outpatientType = 1;
    //    patientName = "马国平";
    //    scOrderId = 259;
    //    scheduleId = "904||385";
    //    state = 3;
    //    takePassword = "";
    //    timeRnge = 1;
    //    uid = 123456;
    //    updateTime = 1479181276000;
    //    userPhone = 13419573547;
    //    visitAddress = "四川";
    //    week = "周三";
@property(nonatomic,assign)int scOrderId;//就诊ID
@property(nonatomic,assign)int hosCode;//医院ID
@property(nonatomic,copy)NSString *takePassword;//取号密码

@property(nonatomic,copy)NSString *hosName;//医院名称
@property(nonatomic,copy)NSString *deptCode;//科室名称
@property(nonatomic,copy)NSString *doctName;//医生名称
@property(nonatomic,copy)NSString *deptName;//医生等级
@property(nonatomic,copy)NSString *patientName;//患者姓名
@property(nonatomic,copy)NSString *cost;//挂号价格
@property(nonatomic,copy)NSString *date;//就诊年月日
@property(nonatomic,copy)NSString *week;//就诊周几
@property(nonatomic,assign)int timeRnge;//就诊上下午
@property(nonatomic,copy)NSString *preOrderNumber;//订单号
@property(nonatomic,copy)NSString *state;//订单状态.| state | 订单状态 | Y | 1:已预约,2:已支付,3:已退号,4:已取号,5:待退费 |
@property(nonatomic,assign)int isEvaluated;//是否已评价 0---->未评价  1--->已评价


@property(nonatomic,strong)NSArray *content;


@end
