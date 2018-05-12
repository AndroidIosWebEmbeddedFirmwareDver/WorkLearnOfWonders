//
//  SCHospitalEvaluationCell.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "BaseTableViewCell.h"

@class SCEvaluationModel;
@interface SCHospitalEvaluationCell : BaseTableViewCell

@property (nonatomic, strong) SCEvaluationModel *model;

@end
