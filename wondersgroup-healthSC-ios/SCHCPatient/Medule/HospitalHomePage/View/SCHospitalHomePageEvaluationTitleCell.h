//
//  SCHospitalHomePageEvaluationTitleCell.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "SCHospitalHomePageModel.h"
typedef void(^MoreTapHandle)(void);

@interface SCHospitalHomePageEvaluationTitleCell : BaseTableViewCell

@property (nonatomic, strong) SCHospitalHomePageModel *model;
@property (nonatomic, copy) MoreTapHandle moreTapHandler;


@end
