//
//  SCAppointmentDoctorDateModel.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDoctorDateModel.h"

@implementation SCAppointmentDoctorDateModel

-(NSString *)showDate {
    if (self.date) {
        _showDate = [self.date substringFromIndex:5];
    }
    return _showDate;
}

@end
