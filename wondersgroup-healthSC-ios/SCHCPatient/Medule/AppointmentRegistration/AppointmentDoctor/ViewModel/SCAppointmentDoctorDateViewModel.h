//
//  SCAppointmentDoctorDateViewModel.h
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCAppointmentDoctorNameViewModel.h"
#import "SCAppointmentDoctorDateModel.h"
#import "SCAppointmentDetailModel.h"

@interface SCAppointmentDoctorDateViewModel : BaseViewModel<AppointmetIMPL>

@property (nonatomic, strong) NSString *hospitalCode;//医院
@property (nonatomic, strong) NSString *hosDeptCode;//科室
@property (nonatomic, strong) NSString *time;//时间
@property (nonatomic, strong) NSArray *datas;

@property (nonatomic, strong) NSMutableArray *dates;//日期

@property (nonatomic, assign) RequestCompeleteType dateRequestCompeleteType;//获取日期接口回调

@end
