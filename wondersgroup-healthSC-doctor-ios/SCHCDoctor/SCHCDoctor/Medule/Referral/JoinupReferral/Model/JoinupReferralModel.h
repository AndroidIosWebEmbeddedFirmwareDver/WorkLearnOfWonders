//
//  JoinupReferralModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface JoinupReferralModel : BaseModel


@property (strong, nonatomic) NSString * name;                         //患者姓名(最多显示5个字，超出部分…)
@property (strong, nonatomic) NSString * avatar;                       //患者头像
@property (strong, nonatomic) NSString * urgency;                      //紧急程度（1:一般，2:紧急）
@property (strong, nonatomic) NSString * toOrgCode;                    //转入医院代码
@property (strong, nonatomic) NSString * toOrgName;                    //转入医院名称
@property (strong, nonatomic) NSString * fromOrgCode;                  //转出医院代码
@property (strong, nonatomic) NSString * fromOrgName;                  //转出医院名称
@property (strong, nonatomic) NSString * referralID;                   //转诊ID
@property (strong, nonatomic) NSString * referralDate;                 //转诊时间（yyyy-MM-dd HH:mm:ss）

@end
