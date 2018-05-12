//
//  ReferralDetailModel.h
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "ReferralDetailReferralModel.h"
@interface ReferralDetailModel : BaseModel

@property (copy, nonatomic) NSString * name;                        //患者姓名
@property (copy, nonatomic) NSString * gender;                      //性别
@property (copy, nonatomic) NSString * age;                         //年龄
@property (copy, nonatomic) NSString * VisitCardNum;                //就诊卡号（前三后四，中间星号隐藏）
@property (copy, nonatomic) NSString * idCard;                      //身份证号（前三后四，中间星号隐藏）
@property (copy, nonatomic) NSString * mobile;                      //手机号
@property (copy, nonatomic) NSString * address;                     //家庭地址

@property  (strong, nonatomic) NSArray<ReferralDetailReferralModel *> * referralModel;

@end
