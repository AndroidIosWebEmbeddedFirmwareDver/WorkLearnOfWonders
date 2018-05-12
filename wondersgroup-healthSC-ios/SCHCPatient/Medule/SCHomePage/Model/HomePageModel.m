//
//  HomePageModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HomePageModel.h"

@implementation HomePageModel
+ (NSDictionary *)mj_objectClassInArray {
    return @{@"banners":@"BannersModel",
             @"functionIcons":@"FunctionModel",
             @"news":@"ArticlessModel"
             };
}
@end
