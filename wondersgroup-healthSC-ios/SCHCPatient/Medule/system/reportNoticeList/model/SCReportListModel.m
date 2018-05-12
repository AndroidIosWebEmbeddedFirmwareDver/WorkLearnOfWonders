//
//  SystemDeitalModel.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCReportListModel.h"

@implementation SCReportListModel
+ (NSDictionary *)replacedKeyFromPropertyName{
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:[self getDicPropertiesToSameValue]];
    [dic setValue:@"" forKey:@"title"];
    [dic setValue:@"" forKey:@"paitentName"];
    [dic setValue:@"" forKey:@"PaitentId"];
    [dic setValue:@"" forKey:@"sex"];
    [dic setValue:@"" forKey:@"age"];
    
    [dic setValue:@"" forKey:@"hospitalName"];
    [dic setValue:@"" forKey:@"hospitalId"];
    [dic setValue:@"" forKey:@"checkProject"];
    [dic setValue:@"" forKey:@"checkPart"];
    [dic setValue:@"" forKey:@"department"];
    
    [dic setValue:@"" forKey:@"checkDate"];
    [dic setValue:@"" forKey:@"reportDate"];
    [dic setValue:@"" forKey:@"date"];
    [dic setValue:@"" forKey:@"linkUp"];
    
    
    return dic;
}
@end
