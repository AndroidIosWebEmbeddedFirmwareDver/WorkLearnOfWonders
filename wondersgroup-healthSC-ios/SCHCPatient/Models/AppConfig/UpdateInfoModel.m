//
//  UpdateInfoModel.m
//  VaccinePatient
//
//  Created by maorenchao on 16/6/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UpdateInfoModel.h"

@implementation UpdateInfoModel
+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    
    return @{
             @"downloadUrl":@"iosUrl",
             //@"updateLogs":@"updateMsg",
             //@"enforceUpdate":@"forceUpdate",
             };
    
}
@end
