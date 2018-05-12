//
//  JoinupReferralViewModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "JoinupReferralModel.h"
@interface JoinupReferralViewModel : BaseViewModel

@property (strong, nonatomic) NSArray<JoinupReferralModel *> * model;


- (void)requestListDataWithSuccess:(void(^)(id content))successBlock
                              fail:(void(^)(NSError * error, NSString * errorString))failBlock;


@end
