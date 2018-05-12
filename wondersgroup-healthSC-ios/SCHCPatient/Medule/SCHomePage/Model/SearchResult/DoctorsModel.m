//
//  DoctorsModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorsModel.h"
@implementation DoctorssModel

+ (NSDictionary *)mj_replacedKeyFromPropertyName
{
    return @{
             @"doctorID" : @"id"
             };
}
@end
@implementation DoctorsModel

+ (NSDictionary *)mj_objectClassInArray {
    
    return @{ @"content":@"DoctorssModel"};
}

@end
