//
//  WDVerificationInfoModel.h
//  JAHealthCloudPatient
//
//  Created by luzhongchang on 16/6/15.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "BaseModel.h"

@interface WDVerificationInfoModel : BaseModel
@property(nonatomic, strong)NSNumber *can_submit;

@property(nonatomic, strong)NSString *idcard;

@property(nonatomic, strong)NSString *msg;

@property(nonatomic, strong)NSString *name;

@property(nonatomic, strong)NSString *status;

@property(nonatomic, strong)NSNumber *success;

@property(nonatomic, strong)NSString *uid;
@end
