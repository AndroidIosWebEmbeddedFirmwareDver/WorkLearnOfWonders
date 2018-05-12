//
//  PayNoticeModel.m
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PayNoticeModel.h"
@implementation PayNoticeModel
+ (NSDictionary *)replacedKeyFromPropertyName{
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:[self getDicPropertiesToSameValue]];
    [dic setValue:@"id" forKey:@"ID"];
   
    return dic;
}



@end
