//
//  ReportNoticeModel.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSystemListModel.h"

@implementation SCSystemListModel
+ (NSDictionary *)replacedKeyFromPropertyName{
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:[self getDicPropertiesToSameValue]];
    [dic setValue:@"id" forKey:@"messageId"];
    [dic setValue:@"createDate" forKey:@"date"];
    [dic setValue:@"title" forKey:@"title"];
    [dic setValue:@"imgUrl" forKey:@"picurl"];
    [dic setValue:@"content" forKey:@"des"];
    [dic setValue:@"url" forKey:@"linkUp"];
    [dic setValue:@"state" forKey:@"state"];
    

    return dic;
}
@end
