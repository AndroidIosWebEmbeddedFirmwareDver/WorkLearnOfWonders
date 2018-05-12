//
//  ReferralViewModel.h
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "ReferralModel.h"

@interface ReferralViewModel : BaseViewModel

@property (assign, nonatomic) NSInteger currentPage;
@property (assign, nonatomic) NSInteger pageSize;
@property (strong, nonatomic) NSArray<ReferralModel *> * dataArray;

- (void)requestDataWithSuccess:(void(^)(id content))successBlock
                          fail:(void(^)(id error, NSString * msg))failBlock;

@end
