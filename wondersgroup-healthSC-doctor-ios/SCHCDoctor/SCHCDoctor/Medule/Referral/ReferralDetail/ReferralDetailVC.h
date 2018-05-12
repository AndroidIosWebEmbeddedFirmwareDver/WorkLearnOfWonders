//
//  ReferralDetailVC.h
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseViewController.h"

@interface ReferralDetailVC : BaseViewController

- (instancetype)initWithType:(ReferralProgress)type;

//是接入转诊
- (void)isJoinReferr:(BOOL)isJoinReferr;
@end
