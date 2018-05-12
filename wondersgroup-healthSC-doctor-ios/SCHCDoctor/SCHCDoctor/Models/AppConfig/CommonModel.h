//
//  CommonModel.h
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface CommonModel : BaseModel

@property (nonatomic, strong) NSString *publicKey;// // RSA加密公钥
@property (nonatomic, strong) NSString *userAgreement;// 用户协议
@property (nonatomic, strong) NSString *helpCenter;// 帮助中心
@property (nonatomic, strong) NSString *consumerHotline;// 客服热线
@property (nonatomic, strong) NSString *appointmentRule;// 预约规则
@property (nonatomic, strong) NSString *realNameRule;// 实名认证查看条款h5
@end
