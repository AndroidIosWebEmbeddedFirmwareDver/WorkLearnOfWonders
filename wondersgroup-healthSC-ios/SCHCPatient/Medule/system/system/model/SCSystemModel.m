//
//  SystemModel.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSystemModel.h"

@implementation SCSystemModel
+ (NSDictionary *)replacedKeyFromPropertyName{
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:[self getDicPropertiesToSameValue]];
    [dic setValue:@"count" forKey:@"messageCount"];
    [dic setValue:@"type" forKey:@"type"];
    [dic setValue:@"content" forKey:@"content"];
    [dic setValue:@"name" forKey:@"title"];
    [dic setValue:@"date" forKey:@"time"];
    return dic;
}

@end
