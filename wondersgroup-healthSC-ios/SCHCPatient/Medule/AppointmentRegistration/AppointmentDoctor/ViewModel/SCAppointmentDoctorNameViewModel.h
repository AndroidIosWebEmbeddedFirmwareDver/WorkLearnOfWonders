//
//  SCAppointmentDoctorNameViewModel.h
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCAppointmentDetailModel.h"

@interface SCAppointmentDoctorNameViewModel : BaseViewModel<AppointmetIMPL>

@property (nonatomic, strong) NSString *hospitalCode;//医院
@property (nonatomic, strong) NSString *hosDeptCode;//科室
@property (nonatomic, strong) NSArray *datas;

@end
