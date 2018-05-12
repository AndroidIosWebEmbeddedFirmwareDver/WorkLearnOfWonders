//
//  ReferralViewModel.m
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "ReferralViewModel.h"

@implementation ReferralViewModel

- (instancetype)init
{
    self = [super init];
    if (self) {
        _currentPage = 0;
        _pageSize = 10;
        _dataArray = @[];
    }
    return self;
}


- (void)requestDataWithSuccess:(void(^)(id content))successBlock
                          fail:(void(^)(id error, NSString * msg))failBlock {
    
}
@end
