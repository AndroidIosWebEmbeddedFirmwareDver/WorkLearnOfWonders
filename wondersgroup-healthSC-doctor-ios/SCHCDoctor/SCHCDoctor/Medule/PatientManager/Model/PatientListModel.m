//
//  PatientListModel.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientListModel.h"

@implementation PatientListModel

+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    return @{
             @"avatarUrl" : @"avatar"
             };
}

@end
