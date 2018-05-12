//
//  SCHospitalHomePageLocalCell.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "SCHospitalHomePageModel.h"
typedef void(^TelClickedHandle)(NSString *tel);

@interface SCHospitalHomePageLocalCell : BaseTableViewCell

@property (nonatomic, strong) SCHospitalHomePageModel *model;
@property (nonatomic, copy) TelClickedHandle telClickedHandler;

@end
