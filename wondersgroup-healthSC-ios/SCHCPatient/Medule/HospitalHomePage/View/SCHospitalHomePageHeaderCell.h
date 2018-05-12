//
//  SCHospitalHomePageHeaderCell.h
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "SCHospitalModel.h"

@interface SCHospitalHomePageHeaderCell : BaseTableViewCell

@property (nonatomic, strong) SCHospitalModel *model;

@property (nonatomic, assign, getter=isBookHidden) BOOL bookHidden;

@end
