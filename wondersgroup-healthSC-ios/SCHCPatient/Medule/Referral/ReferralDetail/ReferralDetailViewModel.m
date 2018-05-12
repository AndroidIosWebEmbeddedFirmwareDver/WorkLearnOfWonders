//
//  ReferralDetailViewModel.m
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "ReferralDetailViewModel.h"

@implementation ReferralDetailViewModel
- (instancetype)init
{
    self = [super init];
    if (self) {
        _baseDataArray = @[@{@"性别":@"test"},
                           @{@"年龄":@"test"},
                           @{@"就诊卡卡号":@"test"},
                           @{@"身份证号":@"test"},
                           @{@"手机号":@"test"},
                           @{@"家庭住址":@"test"}];
        _referralDataArray = @[@{},@{},@{}];
    }
    return self;
}
@end
