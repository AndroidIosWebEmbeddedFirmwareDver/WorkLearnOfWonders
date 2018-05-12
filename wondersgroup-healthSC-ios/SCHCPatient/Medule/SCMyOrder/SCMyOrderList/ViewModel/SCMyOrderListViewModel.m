//
//  MyOrderListViewModel.m
//  HCPatient
//
//  Copyright © 2016年 非丶白. All rights reserved.
//

#import "SCMyOrderListViewModel.h"


@interface SCMyOrderListViewModel ()
@end

@implementation SCMyOrderListViewModel

- (instancetype)init {
    
    if (self == [super init]) {
        
        [self prepareData];
    }
    return self;
}

- (void)prepareData {
    
    self.isMore = NO;
    self.isEmpty = NO;
    self.error = nil;
    self.more_params = nil;
    self.dataArray = [NSMutableArray array];
}


- (void)analyticalResponse:(NSDictionary *)response {
    
    NSArray *contentArray = response[@"content"];
    
    NSArray *resultArray = [SCMyOrderModel mj_objectArrayWithKeyValuesArray:contentArray];
    
    if (resultArray.count == 0) {
        self.isEmpty = YES;
        return;
    }
    self.isEmpty = NO;
    self.isMore = [response[@"more"] boolValue];
    self.moreParams = response[@"more_params"];
    //ZYCHANGE
    [self.dataArray addObjectsFromArray:resultArray];
}

#pragma mark - 获取 订单列表
/**
 *  参数
 *
 *  @param userId
 *  @param type
 */
- (void)getMyOrderListComplete:(void (^)(void))complete failure:(void (^)(NSError *))failure {

    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
   // 050f96947aa2429a91c306cb8b10840b
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:[UserManager manager].uid forKey:@"uid"];
   // [params setObject:@"050f96947aa2429a91c306cb8b10840b" forKey:@"uid"];
    if (self.viewType == MyOrderListViewControllerTypeAll)
    {
        [params setObject:[NSString stringWithFormat:@"ALL"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypeNotComplete)
    {
        [params setObject:[NSString stringWithFormat:@"NOTPAY"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypeComplete)
    {
        [params setObject:[NSString stringWithFormat:@"SUCCESS"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypebackmoney)
    {
        [params setObject:[NSString stringWithFormat:@"REFUNDSUCCESS"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypemoreTime)
    {
        [params setObject:[NSString stringWithFormat:@"EXPIRED"] forKey:@"status"];
    }
    //MYORDER_LIST_URL  @"http://127.0.0.1/temp1190.json"
    [self.adapter request:MYORDER_LIST_URL params:params class:[SCMyOrderModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel * model = response.data;
        if (model.content.count == 0) {
            self.isEmpty = YES;
            return;
        }
        self.isEmpty = NO;
        self.isMore = [model.more boolValue];
        self.moreParams = model.more_params;
        self.dataArray =  [NSMutableArray arrayWithArray: model.content];
        self.isError = NO;
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        if(error.code == 2021)
        {
          self.isError = NO;
          self.isEmpty =YES;
        }
        else
        {
            self.isEmpty = NO;
            self.isError = YES;
        }
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        failure(error);
    }];
    
}

#pragma mark - 诊间支付支付失败从新生产订单
- (void)generateOrderWithOrder:(SCMyOrderModel *)order success:(void(^)(SCMyOrderModel *order))success failed:(void(^)(void))failed {
    NSDictionary *dic = @{
                          @"prescriptionNum":StringOrNUll(order.business.prescriptionNum),
                          @"hospitalName":StringOrNUll(order.business.hospitalName),
                          @"time":StringOrNUll(order.business.time),
                          @"deptName":StringOrNUll(order.business.deptName),
                          @"hospitalCode":StringOrNUll(order.business.hospitalCode),
                          @"jzlsh":StringOrNUll(order.business.jzlsh),
                          @"price":[NSString stringWithFormat:@"%@", order.price],
                          @"uid":[UserManager manager].uid
                          };
    [self.adapter request:MYORDER_GENERATE_ORDER
                   params:dic
                    class:[SCMyOrderModel class]
             responseType:Response_Object
                   method:Request_POST
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      SCMyOrderModel *model = response.data;
                      success(model);
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      failed();
                  }];
    
}

#pragma mark - 获取更多 订单列表
- (void)getMoreMyOrderListComplete:(void (^)(void))complete failure:(void (^)(NSError *))failure {
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:[UserManager manager].uid forKey:@"uid"];
   // [params setObject:@"050f96947aa2429a91c306cb8b10840b" forKey:@"uid"];
    if (self.viewType == MyOrderListViewControllerTypeAll)
    {
       [params setObject:[NSString stringWithFormat:@"ALL"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypeNotComplete)
    {
       [params setObject:[NSString stringWithFormat:@"NOTPAY"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypeComplete)
    {
       [params setObject:[NSString stringWithFormat:@"SUCCESS"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypebackmoney)
    {
       [params setObject:[NSString stringWithFormat:@"REFUNDSUCCESS"] forKey:@"status"];
    } else if (self.viewType == MyOrderListViewControllerTypemoreTime)
    {
       [params setObject:[NSString stringWithFormat:@"EXPIRED"] forKey:@"status"];
    }
   [params setObject:self.moreParams[@"flag"] forKey:@"flag"];
   [self.adapter request:MYORDER_LIST_URL params:params class:[SCMyOrderModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {

        ListModel * model = response.data;
        self.isEmpty = NO;
        self.isMore = [model.more boolValue];
        self.moreParams = model.more_params;
      
        NSMutableArray * array = [NSMutableArray new];
        [array addObjectsFromArray:self.dataArray];
        [array addObjectsFromArray:model.content];
        self.dataArray = [array copy];
      
        self.isError = NO;
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        self.isEmpty = NO;
        self.isError = YES;
        
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        failure(error);
    }];
}

#pragma mark - 取消订单
- (void)cancelOrderComplete:(void (^)(void))complete failure:(void (^)(NSError *))failure {
    
    /**
     *  参数
     *
     *  @param userId
     *  @param orderNo
     */
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:[UserManager manager].uid forKey:@"userId"];
    [params setObject:self.deleteOrderId forKey:@"orderNo"];
    
    [self.adapter request:MYORDER_CANCEL_ORDER_URL params:params class:nil responseType:Response_Object method:Request_DELETE needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        complete();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        failure(error);
    }];
}


/*
 //取消订单
 
 - (void)cancelOrderSuccess:(void (^)(void))success failure:(void (^)(NSError *))failure {
 
 //    NSDictionary *params = @{@"userId" : [HCContext shareInstance].userInfo.uid,
 //                             @"orderNo" : self.deleteOrderId};
 //
 //    [MBProgressHUDHelper showHudIndeterminate];
 //    [self.requestItem cancelOrderWithParams:params success:^(id response) {
 //
 //        [MBProgressHUDHelper hideHud];
 //        success();
 //    } failure:^(NSError *error) {
 //
 //        [MBProgressHUDHelper hideHud];
 //        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
 //        failure(error);
 //    }];
 }
 */


@end
