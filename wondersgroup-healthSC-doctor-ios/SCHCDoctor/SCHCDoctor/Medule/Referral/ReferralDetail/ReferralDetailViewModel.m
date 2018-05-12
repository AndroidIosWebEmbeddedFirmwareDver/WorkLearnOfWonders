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
        _baseDataTitles = @[@"性别",
                           @"年龄",
                           @"就诊卡卡号",
                           @"身份证号",
                           @"手机号",
                           @"家庭住址"];
        _isJoinReferr = NO;
        
    }
    return self;
}

- (void)requestDataWithSuccess:(void(^)(id content))successBlock
                          fail:(void(^)(NSError * error, NSString * errorString))failBloc {
//    NSDictionary * params = @{@"orgCode":@"",
//                              @"referralID":@""};
//    
//    NSString * url = ReferralHomeListDetail;
//    if (_isJoinReferr) {
//        url = ReferralJoinDetail;
//    }
//    [self.adapter request:url params:params class:[ReferralDetailModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//
//    }];
    successBlock(nil);
}
@end
