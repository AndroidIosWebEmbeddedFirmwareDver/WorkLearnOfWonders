//
//  SCAppointmentDoctorDateModel.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCAppointmentDoctorDateModel : BaseModel

@property (nonatomic, strong) NSString *date;//日期
@property (nonatomic, strong) NSString *week;//周
@property (nonatomic, strong) NSString *showDate;//显示的日期


@end
