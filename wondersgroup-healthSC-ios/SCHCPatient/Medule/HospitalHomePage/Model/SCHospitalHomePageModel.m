//
//  SCHospitalHomePageModel.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHospitalHomePageModel.h"
#import "SCEvaluationModel.h"
@implementation SCHospitalHomePageModel

+ (NSDictionary *)mj_objectClassInArray {
    return @{
             @"evaluList": @"SCEvaluationModel",
             };
}

@end
