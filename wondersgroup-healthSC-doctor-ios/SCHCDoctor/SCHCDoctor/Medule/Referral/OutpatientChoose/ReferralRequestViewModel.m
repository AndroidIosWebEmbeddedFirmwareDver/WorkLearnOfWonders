//
//  ReferralRequestViewModel.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralRequestViewModel.h"

@implementation ReferralRequestViewModel

- (instancetype)init
{
    self = [super init];
    if (self) {
        _model = [[PatientInfoModel alloc] init];
        
        //初始化医生数据
//        _model.fromOrgName = 
//        _model.fromDeptName;
//        _model.fromDocName = [UserManager manager].name;
//        _model.fromDocPhone = [UserManager manager].mobile;
    }
    return self;
}

@end
