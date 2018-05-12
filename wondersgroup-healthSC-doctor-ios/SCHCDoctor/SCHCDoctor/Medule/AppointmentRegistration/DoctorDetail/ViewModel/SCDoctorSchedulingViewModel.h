//
//  DoctorDetailViewModel.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCDoctorSchedulingModel.h"

@interface SCDoctorSchedulingViewModel : BaseViewModel<AppointmetIMPL>

@property (nonatomic, strong) NSString *hospitalCode;
@property (nonatomic, strong) NSString *hosDeptCode;
@property (nonatomic, strong) NSString *hosDoctCode;

@property (nonatomic, strong) NSMutableArray *dates;//日期

@property (nonatomic, strong) SCDoctorSchedulingModel *model;


//
@property (nonatomic, strong) NSString *message;

@end
