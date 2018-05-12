//
//  ArticlessModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ArticlessModel.h"

@implementation ArticlessModel
+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    return @{
             @"articleID" : @"id"
             };
}
@end
