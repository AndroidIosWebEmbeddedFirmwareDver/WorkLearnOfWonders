//
//  SCHospitalHomePageCellModel.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalHomePageCellModel.h"

@implementation SCHospitalHomePageCellModel

+ (instancetype)modelWithCellType:(HospitalHomePageCellType)cellType model:(id)model {
    SCHospitalHomePageCellModel *cellModel = [[self alloc] init];
    
    cellModel.cellType = cellType;
    cellModel.model = model;
    
    return cellModel;
}

@end
