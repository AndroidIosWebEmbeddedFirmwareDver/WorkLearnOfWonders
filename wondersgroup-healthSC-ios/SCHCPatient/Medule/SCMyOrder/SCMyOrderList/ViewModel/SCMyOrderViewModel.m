//
//  MyOrderViewModel.m
//  HCPatient
//
//  Copyright © 2016年 非丶白. All rights reserved.
//

#import "SCMyOrderViewModel.h"
#import "SCMyOrderListViewController.h"

@interface SCMyOrderViewModel ()


@end

@implementation SCMyOrderViewModel


- (instancetype)init {
    
    if (self == [super init]) {
        
        [self prepareData];
    }
    return self;
}


- (void)prepareData {
    
    self.topTabs = @[@"全部", @"待支付", @"已支付",@"已退款",@"已超时"];
    self.typesArray = @[@(MyOrderListViewControllerTypeAll), @(MyOrderListViewControllerTypeNotComplete), @(MyOrderListViewControllerTypeComplete),@(MyOrderListViewControllerTypebackmoney),
        @(MyOrderListViewControllerTypemoreTime)];
}

- (void)getMyOrderListSuccess:(void (^)(id))success failure:(void (^)(NSError *))failure {
    
//    NSDictionary *params = @{};
//    [self.requestItem getMyOrderListWithParams:params success:^(id response) {
//        
//        success(response);
//    } failure:^(NSError *error) {
//        
//        failure(error);
//    }];
}

@end
