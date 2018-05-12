//
//  MyOrderListViewModel.h
//  HCPatient
//
//  Copyright © 2016年 非丶白. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCMyOrderModel.h"

typedef NS_ENUM(NSUInteger, MyOrderListViewControllerType) {
    MyOrderListViewControllerTypeAll,                //所有订单
    MyOrderListViewControllerTypeNotComplete,        //待支付
    MyOrderListViewControllerTypeComplete,           //已完成
    MyOrderListViewControllerTypebackmoney,          //已退款
    MyOrderListViewControllerTypemoreTime,           //已超时
   
};

@interface SCMyOrderListViewModel : BaseViewModel <MyOrderIMPL>

@property (nonatomic, assign) MyOrderListViewControllerType viewType;

@property (nonatomic, strong) NSMutableArray *dataArray;

@property (nonatomic, copy)NSError * error;
@property (nonatomic, assign) BOOL isMore;
@property (nonatomic, assign) BOOL isEmpty;
@property (nonatomic, assign) BOOL isError;

@property (nonatomic, strong)NSDictionary *more_params;


//要取消的订单id
@property (nonatomic, copy) NSString *deleteOrderId;
- (void)generateOrderWithOrder:(SCMyOrderModel *)order success:(void(^)(SCMyOrderModel *order))success failed:(void(^)(void))failed;
@end
