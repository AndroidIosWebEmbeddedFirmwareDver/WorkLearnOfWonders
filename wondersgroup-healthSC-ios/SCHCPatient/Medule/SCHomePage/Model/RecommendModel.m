//
//  RecommendModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "RecommendModel.h"

@implementation RecommendModel
+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    return @{
             @"recommendID" : @"id"
             };
}
@end
