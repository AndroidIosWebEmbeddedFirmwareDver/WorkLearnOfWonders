//
//  ReferralModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface ReferralModel : BaseModel

@property (copy, nonatomic) NSString * name;                    //患者姓名(最多显示5个字，超出部分…)
@property (copy, nonatomic) NSString * avatar;                  //患者头像
@property (copy, nonatomic) NSString * urgency;                 //紧急程度（1:一般，2:紧急）
@property (copy, nonatomic) NSString * toOrgCode;               //转入医院代码
@property (copy, nonatomic) NSString * toOrgName;               //转入医院名称
@property (copy, nonatomic) NSString * fromOrgCode;             //转出医院代码
@property (copy, nonatomic) NSString * fromOrgName;             //转出医院名称
@property (copy, nonatomic) NSString * referralID;              //转诊ID
@property (copy, nonatomic) NSString * referralStatus;          //转诊状态(0申请中、1已驳回、2已转诊、3已取消)
@property (copy, nonatomic) NSString * referralDate;            //转诊时间（yyyy-MM-dd HH:mm:ss）

@end
