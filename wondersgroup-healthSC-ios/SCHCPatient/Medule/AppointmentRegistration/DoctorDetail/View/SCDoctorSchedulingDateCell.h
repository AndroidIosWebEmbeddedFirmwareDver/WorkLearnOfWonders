//
//  DoctorDetailDateCell.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "SCAppointmentDoctorDateModel.h"
#import "SCDoctorSchedulingViewModel.h"

typedef void (^SelectedBlock)(Schedule *schedule, DoctorInfo *doctorInfo);

@interface SCDoctorSchedulingDateCell : BaseTableViewCell

@property (nonatomic, strong) SelectedBlock selectedBlock;//点中可预约按钮
@property (nonatomic, strong) dispatch_block_t selectedDisableBlock;//点中查看其他科室按钮

@property (nonatomic, strong) SCDoctorSchedulingViewModel *viewModel;

@property (nonatomic, assign) BOOL isShowAllView;//是否显示所有view


@end
