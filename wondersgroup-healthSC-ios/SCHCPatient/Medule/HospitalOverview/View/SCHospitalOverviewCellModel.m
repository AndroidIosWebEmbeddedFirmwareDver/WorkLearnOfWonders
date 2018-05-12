//
//  SCHospitalOverviewCellModel.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHospitalOverviewCellModel.h"

@implementation SCHospitalOverviewCellModel


+ (instancetype)modelWithCellType:(HospitalOverviewCellType)cellType model:(id)model {
    SCHospitalOverviewCellModel *cellModel = [[self alloc] init];
    
    cellModel.cellType = cellType;
    cellModel.model = model;
    
    return cellModel;
}


@end
