//
//  AppointmentDetailModel.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDetailModel.h"

@implementation SCAppointmentDetailModel
+ (NSDictionary *)mj_replacedKeyFromPropertyName {
    return @{
             @"appointmentId" :@"id"
             };
}


@end


@implementation Schedule

-(NSString *)timeRangeStr {
    if (self.timeRange) {
        switch ([self.timeRange intValue]) {
                case 1:
            {
                _timeRangeStr = @"上午";
            }
                break;
                case 2:
            {
                _timeRangeStr = @"下午";
            }
                break;
                case 3:
            {
                _timeRangeStr = @"晚上";
            }
                break;
                
            default:
            {
                _timeRangeStr = @"";
            }
                break;
        }
    }
    return _timeRangeStr;
}

@end
