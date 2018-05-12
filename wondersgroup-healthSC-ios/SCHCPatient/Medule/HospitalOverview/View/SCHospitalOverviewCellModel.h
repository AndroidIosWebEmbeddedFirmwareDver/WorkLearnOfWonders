//
//  SCHospitalOverviewCellModel.h
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, HospitalOverviewCellType) {
    HospitalOverviewCellTypeHeader,
    HospitalOverviewCellTypeTitle,
    HospitalOverviewCellTypeIntroduce,
};

@interface SCHospitalOverviewCellModel : NSObject

@property (nonatomic, assign) HospitalOverviewCellType cellType;
@property (nonatomic, strong) id model;

+ (instancetype)modelWithCellType:(HospitalOverviewCellType)cellType model:(id)model;

@end
