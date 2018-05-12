//
//  SCHospitalHomePageModel.h
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHospitalModel.h"

@interface SCHospitalHomePageModel : SCHospitalModel

/// 评价量
@property (nonatomic, strong) NSNumber *evaluateCount;
/// 评价列表
@property (nonatomic, strong) NSArray *evaluList;

@end
