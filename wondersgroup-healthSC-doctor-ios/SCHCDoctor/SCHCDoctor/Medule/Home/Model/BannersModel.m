//
//  BannersModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BannersModel.h"

@implementation BannersModel
+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    return @{
             @"imgUrl" : @"imgUrl",
             @"hoplink" : @"hoplink"
             };
}
@end
