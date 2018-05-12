//
//  AritcleModel.m
//  VaccinePatient
//
//  Created by maorenchao on 16/6/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AritcleModel.h"

@implementation AritcleModel

+ (NSDictionary *)replacedKeyFromPropertyName{
    return @{
             @"articleId" : @"id",
             @"thumb" : @"thumb",
             @"title" : @"title",
             @"brief" : @"brief",
             @"url" : @"url",
             @"useplace" : @"useplace",
             @"pv" : @"pv",
             @"collect" : @"collect",
             @"share" : @"share"
             };
}
@end
