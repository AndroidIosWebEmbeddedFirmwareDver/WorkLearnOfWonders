//
//  MyOrderListViewController.h
//  HCPatient
//
//  Copyright © 2016年 非丶白. All rights reserved.
//

#import "BaseViewController.h"
#import "SCMyOrderListViewModel.h"
#import "SCMyOrderViewController.h"

typedef void(^OrderListViewPushBlock)(void);

@interface SCMyOrderListViewController : BaseViewController
@property (nonatomic, copy  ) OrderListViewPushBlock        pushBlock;
@property (nonatomic, strong) UIViewController              *mParentViewController;
@property (assign, nonatomic) MyOrderListViewControllerType type;
@property (nonatomic, strong) SCMyOrderViewController       *fatherViewController;
- (void)selectOrderList;
- (void)requestAction;
@end
