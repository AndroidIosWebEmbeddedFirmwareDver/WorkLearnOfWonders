//
//  SCMyPreorderDetailModel.h
//  SCHCPatient
//
//  Created by wuhao on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCMyPreorderDetailModel : BaseModel
//createTime = "2016-11-18 14:34:24";
//deptName = "计划生育科";
//doctName = "周继明";
//endTime = "";
//hosOrgCode = 450755531;
//hosOrgName = "成都市第一人民医院";
//isEvaluated = 0;
//numSourceId = 1;
//orderId = YY201611181434165686;
//orderStatus = 3;
//orderTime = "2016-11-22";
//patientBD = "";
//patientCardId = 510111196411044676;
//patientCardType = 01;
//patientName = "马长树";
//patientPhone = 13062733033;
//patientSex = 1;
//payMode = 3;
//platformPatientId = 2376;
//platformUserId = 2376;
//showOrderId = YY201611181434165686;
//startTime = "";
//takePassword = null;
//timeRange = "下午";
//visitCost = 8;
//visitLevel = "医师";
//visitNo = 5;
@property(nonatomic,copy)NSString *orderId;//就诊ID
@property(nonatomic,copy)NSString *showOrderId;//显示的就诊ID
@property(nonatomic,copy)NSString *hosOrgCode;//医院ID
@property(nonatomic,copy)NSString *takePassword;//取号密码

@property(nonatomic,copy)NSString *hosOrgName;//医院名称
@property(nonatomic,copy)NSString *doctName;//医生名称
@property(nonatomic,copy)NSString *deptName;//医生等级
@property(nonatomic,copy)NSString *patientName;//患者姓名
@property(nonatomic,copy)NSString *visitCost;//预约挂号价格
@property(nonatomic,copy)NSString *visitLevel;//出征级别

@property(nonatomic,copy)NSString *orderTime;//就诊年月日
@property(nonatomic,copy)NSString *timeRange;//就诊上下午
@property(nonatomic,strong)NSNumber *isEvaluated;//是否已预约

@property(nonatomic,strong)NSArray *content;
@property(nonatomic,strong)NSNumber *orderStatus;//预约状态






@end
