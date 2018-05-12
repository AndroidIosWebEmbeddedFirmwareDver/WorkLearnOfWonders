//
//  ReferralViewModel.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralViewModel.h"

@implementation ReferralViewModel
- (instancetype)init
{
    self = [super init];
    if (self) {
        _currentPage = 0;
        _pageSize = 10;
        _segmentTitles = @[@"全部",@"申请中",@"已驳回",@"已转诊",@"已取消"];
        _allModels = [NSMutableArray arrayWithArray:@[]];
        _currentSegmentNum = 0;
        _stateModel = [[ReferralStateModel alloc] init];
    }
    return self;
}

- (void)requestStateWithSuccess:(void(^)(id content))successBlock
                           fail:(void(^)(NSError * error, NSString * errorString))failBloc {
//    NSDictionary * params = @{@"orgCode":@"",
//                              @"doctorIdCard":@""};
//    [self.adapter request:ReferralHomeListState params:params class:[ReferralViewModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        
//    }];
    _stateModel.hasCanceled = YES;
    successBlock(nil);
}

- (void)requestDataWithPage:(NSInteger)page
                    Success:(void(^)(id content))successBlock
                       fail:(void(^)(NSError * error, NSString * errorString))failBlock {
//    NSDictionary * requesetDic = @{@"orgCode":@"",
//                                   @"doctorIdCard":@"",
//                                   @"referralStatus":@"",
//                                   @"flag":[NSString stringWithFormat:@"%ld",page]};
    
//    if (![Global global].networkReachable) {
//        self.requestCompeleteType = RequestCompeleteNoWifi;
//        return;
//    }
//    
//    [self.adapter request:ReferralHomeList params:requesetDic class:nil responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        
//        ListModel *listModel = response.data;
//        self.moreParams = listModel.more_params;
//        self.hasMore = [listModel.more boolValue];
//        
//        NSMutableArray *temp = [[NSMutableArray alloc] initWithArray:self.dataArray];
//        [temp addObjectsFromArray:listModel.content];
//        self.currentModel = temp;
//        
//        if (_dataArray.count == 0) {
//            self.requestCompeleteType = RequestCompeleteEmpty;
//        } else {
//            self.requestCompeleteType = RequestCompeleteSuccess;
//        }
//        
//    successBlock(nil);
//        
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
//        self.requestCompeleteType = RequestCompeleteError;
//        
//        failBlock(error, nil);
//    }];
    
    successBlock(nil);
}

@end
