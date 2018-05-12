//
//  DoctorDetailInfoCell.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "SCDoctorSchedulingModel.h"

@interface SCDoctorSchedulingInfoCell : BaseTableViewCell

@property (nonatomic, strong) SCDoctorSchedulingModel *model;

+(CGFloat)cellHeightWithModel:(SCDoctorSchedulingModel *)model;

@end
