//
//  ReferralDetailReferralModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"
#import "ReferralSituationModel.h"
@interface ReferralDetailReferralModel : BaseModel


@property (copy, nonatomic) NSString * upOrDown;                   //上转 or 下转
@property (copy, nonatomic) NSString * referralStatus;             //转诊状态(0申请中、1已驳回、2已转诊、3已取消)
@property (copy, nonatomic) NSString * urgency;                    //紧急程度（1:一般，2:紧急）
@property (copy, nonatomic) NSString * referralDate;               //转诊时间（yyyy-MM-dd HH:mm:ss）
@property (copy, nonatomic) NSString * toOrgCode;                  //转入医院代码
@property (copy, nonatomic) NSString * toOrgName;                  //转入医院名称
@property (copy, nonatomic) NSString * toDoctorName;               //转入医生姓名
@property (copy, nonatomic) NSString * fromOrgCode;                //转出医院代码
@property (copy, nonatomic) NSString * fromOrgName;                //转出医院名称
@property (copy, nonatomic) NSString * referralReson;              //转诊原因
@property (copy, nonatomic) NSString * tentativeDiagnosis;         //初步诊断
@property (copy, nonatomic) NSString * medicalHistory;             //病史摘要
@property (copy, nonatomic) NSString * pastHistory;                //主要既往史
@property (copy, nonatomic) NSString * treatment;                  //治疗情况
@property (copy, nonatomic) NSString * rejectReson;                //驳回原因

@property (copy, nonatomic) ReferralSituationModel * situationInfo;              //挂号信息
@end
