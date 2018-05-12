//
//  ReferralRequestViewModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "PatientInfoModel.h"
@interface ReferralRequestViewModel : BaseViewModel
@property (assign, nonatomic) ReferralType referralType;        //转诊类型

@property (strong, nonatomic) PatientInfoModel * model;
@end
