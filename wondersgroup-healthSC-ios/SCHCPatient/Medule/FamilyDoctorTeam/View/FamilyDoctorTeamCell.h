//
//  FamilyDoctorTeamCell.h
//  SCHCPatient
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "FamilyDoctorTeamModel.h"

@interface FamilyDoctorTeamCell : BaseTableViewCell

@property (nonatomic, strong) FamilyDoctorTeamModel *model;
@property (nonatomic, assign) BOOL isLast;

@end
