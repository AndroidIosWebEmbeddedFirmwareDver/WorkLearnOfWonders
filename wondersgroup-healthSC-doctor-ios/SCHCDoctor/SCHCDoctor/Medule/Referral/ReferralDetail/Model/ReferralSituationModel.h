//
//  ReferralSituationModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface ReferralSituationModel : BaseModel

@property (copy, nonatomic) NSString * doctorName;                 //医生姓名
@property (copy, nonatomic) NSString * doctorRank;                 //医生职级
@property (copy, nonatomic) NSString * orgName;                    //就诊医院
@property (copy, nonatomic) NSString * deptName;                   //就诊科室
@property (copy, nonatomic) NSString * checkDate;                  //门诊时间
@property (copy, nonatomic) NSString * checkType;                  //门诊类型
@property (copy, nonatomic) NSString * patientName;                //就诊人姓名
@property (copy, nonatomic) NSString * situationTimeRange;         //预约时间段

@end
