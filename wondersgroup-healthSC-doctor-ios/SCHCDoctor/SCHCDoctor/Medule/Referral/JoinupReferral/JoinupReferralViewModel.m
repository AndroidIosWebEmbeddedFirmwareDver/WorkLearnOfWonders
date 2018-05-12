//
//  JoinupReferralViewModel.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "JoinupReferralViewModel.h"

@implementation JoinupReferralViewModel
- (instancetype)init
{
    self = [super init];
    if (self) {
        
    }
    return self;
}

- (void)requestListDataWithSuccess:(void(^)(id content))successBlock
                              fail:(void(^)(NSError * error, NSString * errorString))failBlock {
//    NSDictionary * params = @{@"orgCode":@"",
//                              @"doctorIdCard":@"",
//                              @"flag":@""};
//    [self.adapter request:ReferralJoin params:params class:[JoinupReferralModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        successBlock(nil);
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        failBlock(error, error.description);
//    }];
    successBlock(nil);
}
@end
