//
//  SCHospitalHomePageCellModel.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, HospitalHomePageCellType) {
    HospitalHomePageCellTypeHeader,
    HospitalHomePageCellTypeLocal,
    HospitalHomePageCellTypeItems,
    HospitalHomePageCellTypeEvaluationTitle,
    HospitalHomePageCellTypeEvaluation,
};

@interface SCHospitalHomePageCellModel : NSObject

@property (nonatomic, assign) HospitalHomePageCellType cellType;
@property (nonatomic, strong) id model;

+ (instancetype)modelWithCellType:(HospitalHomePageCellType)cellType model:(id)model;

@end
