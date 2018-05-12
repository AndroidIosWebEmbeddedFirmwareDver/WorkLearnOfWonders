//
//  AppointmentDetailViewModel.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCAppointmentDetailModel.h"
#import "WDRegistrationPayModel.h"

@interface SCAppointmentDetailViewModel : BaseViewModel<AppointmetIMPL>

@property (nonatomic, strong) SCAppointmentDetailModel *model;
@property (nonatomic, strong) NSString *card;//就诊卡卡号
@property (nonatomic, assign) NSNumber *isNotInputCard;//是否没有输入就诊卡卡号

@end
