//
//  ReferralStateModel.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralStateModel.h"

@implementation ReferralStateModel
- (instancetype)init
{
    self = [super init];
    if (self) {
        _hasRequesting = false;
        _hasRejected = false;
        _hasReferred = false;
        _hasCanceled = false;
    }
    return self;
}
@end
