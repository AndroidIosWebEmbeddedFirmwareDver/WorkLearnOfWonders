//
//  UserModel.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UserModel.h"

@implementation UserModel

@end


@implementation UserInfoModel
+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    
    return @{
             @"uid":@"id"
             
             };
    
}

@end
