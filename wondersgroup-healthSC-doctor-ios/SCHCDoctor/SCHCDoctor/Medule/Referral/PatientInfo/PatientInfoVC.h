//
//  PatientInfoVC.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewController.h"
#import "PatientInfoModel.h"
@interface PatientInfoVC : BaseViewController

- (instancetype)initWithType:(ReferralType)type model:(PatientInfoModel *)model;

@end
