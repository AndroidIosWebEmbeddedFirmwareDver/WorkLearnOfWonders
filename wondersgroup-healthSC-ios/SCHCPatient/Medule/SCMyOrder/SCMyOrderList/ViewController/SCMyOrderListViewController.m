//
//  MyOrderListViewController.m
//  HCPatient
//
//  Copyright © 2016年 非丶白. All rights reserved.
//

#import "SCMyOrderListViewController.h"
#import "SCorderDetailViewController.h"
#import "SCMyorderListCelltypeZJPay.h"
#import "SCMyorderListCelltypeGHPay.h"
#import "UIViewController+MJPopupViewController.h"
#import "WDLinkPayViewController.h"
#import "WDlnhospitalPayViewController.h"
@interface SCMyOrderListViewController () <UITableViewDelegate, UITableViewDataSource>
{
    BOOL hasInit;
}

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) SCMyOrderListViewModel *viewModel;

@end

@implementation SCMyOrderListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self prepareData];
    [self prepareUI];
    [self bindRac];    
    hasInit = NO;
}

- (void)viewWillAppear:(BOOL)animated {
    [self requestAction];
    [super viewWillAppear:animated];
}

- (void)selectOrderList {
    WS(weakSelf)
    if (self.viewModel.error) {
        [weakSelf.view showFailView:FailViewEmpty withAction:^{
            [weakSelf requestAction];
        }];
    }
    
    if (weakSelf.viewModel.isEmpty) {
        [weakSelf.view showFailView:FailViewError withAction:^{
            [weakSelf requestAction];
        }];
    }
    
    if (hasInit){
        return;
    }
    
    [self requestAction];
    hasInit = YES;
}

- (void)prepareData {
    self.viewModel = [[SCMyOrderListViewModel alloc] init];
    self.viewModel.viewType = self.type;
}

- (void)prepareUI {
    
    self.view.backgroundColor = [UIColor bc2Color];
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0., 0., self.view.bounds.size.width, self.view.bounds.size.height-100) style:UITableViewStyleGrouped];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = [UIColor bc2Color];
    [self.view addSubview:self.tableView];
    self.tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestHeadAction)];
}

- (void)requestHeadAction
{
    [self.viewModel getMyOrderListComplete:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefresh];
        [self.tableView reloadData];
    } failure:^(NSError *error) {
        [LoadingView hideLoadinForView:self.view];
        [self endRefresh];
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(weakSelf.viewModel, isEmpty) subscribeNext:^(id x) {
        
        BOOL empty = [x boolValue];
        
        if (empty) {
            [weakSelf.view showFailView:FailViewEmpty withAction:^{
                [weakSelf requestAction];
            }];
        }
        else {
            [weakSelf.view hiddenFailView];
        }
    }];
    
    [RACObserve(weakSelf.viewModel, isError) subscribeNext:^(id x) {
        
        BOOL error = [x boolValue];
        if (error) {
            if ([Global global].networkReachable) {
                //请求错误
                [weakSelf.view showFailView:FailViewError withAction:^{
                    [weakSelf requestAction];
                }];
            }
            else {
                //无网络
                [weakSelf.view showFailView:FailViewNoWifi withAction:^{
                    [weakSelf requestAction];
                }];
            }
        }
        else {
            [weakSelf.view hiddenFailView];
        }
    }];
    
    [RACObserve(weakSelf, viewModel.more_params) subscribeNext:^(id x) {
        [weakSelf checkHasMore];
    }];
}

- (void)touchReload {
    
    hasInit = NO;
    [self selectOrderList];
}

//#warning LHN_注释请求
- (void)requestAction {
   
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getMyOrderListComplete:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefresh];
        [self.tableView reloadData];
    } failure:^(NSError *error) {
        [LoadingView hideLoadinForView:self.view];
        [self endRefresh];
    }];
}

- (void)requestMoreAction {
    WS(weakSelf)
    [self.viewModel getMoreMyOrderListComplete:^{
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf endRefresh];
        [weakSelf.tableView reloadData];
    } failure:^(NSError *error) {
        [weakSelf endRefresh];
    }];
}

- (void)endRefresh {
    [self.tableView.mj_header endRefreshing];
    [self.tableView.mj_footer endRefreshing];
}

#pragma mark Method
- (void)checkHasMore
{
    WS(weakSelf)
    [RACObserve(self, viewModel.isMore) subscribeNext:^(id x) {
        
        BOOL more = [x boolValue];
        if (more) {
            weakSelf.tableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(requestMoreAction)];
        }
        else {
            weakSelf.tableView.mj_footer = nil;
        }
    }];
 
}

- (void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    if ([view isKindOfClass: [UITableViewHeaderFooterView class]]) {
        UITableViewHeaderFooterView* castView = (UITableViewHeaderFooterView*) view;
        UIView* content = castView.contentView;
        UIColor* color = [UIColor bc2Color];
        content.backgroundColor = color;
    }
}

#pragma mark - tableView delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 1;
}



- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 10;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
   return self.viewModel.dataArray.count;
}


- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 0.1;
}
-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}



- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
//   //  诊间支付  预约挂号
//    SCMyOrderModel *cellModel = [self.viewModel.dataArray objectAtIndex:indexPath.section];
//    if ([cellModel.orderType isEqualToString:@"CLINIC"]) {
//        return 230;
//    } else if ([cellModel.orderType isEqualToString:@"APPOINTMENT"]) {
//        return 230;
//    }
    return 230;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    WS(weakSelf)
    SCMyOrderModel *cellModel = [self.viewModel.dataArray objectAtIndex:indexPath.section];
    if ([cellModel.orderType isEqualToString:@"CLINIC"]) { //诊间支付
        static NSString *identifier = @"MyorderListCelltypeZJPay";
        SCMyorderListCelltypeZJPay *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCMyorderListCelltypeZJPay alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.orderModel = cellModel;
        cell.statusButtonBlock = ^ (SCMyOrderModel*model) {
            [weakSelf cellStatusButtonActionWithModel:model];
        };
      return cell;
    } else if ([cellModel.orderType isEqualToString:@"APPOINTMENT"]) { //挂号费支付
        static NSString *identifier = @"MyorderListCelltypeGHPay";
        SCMyorderListCelltypeGHPay *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCMyorderListCelltypeGHPay alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.orderModel = cellModel;
        cell.statusButtonBlock = ^ (SCMyOrderModel*model) {
            [weakSelf cellStatusButtonActionWithModel:model];
        };
         return cell;
    }
    return nil;
}

- (void)cellStatusButtonActionWithModel:(SCMyOrderModel*)model {

     if ([model.orderType isEqualToString:@"CLINIC"]) {
         if ([model.payStatus isEqualToString:@"FAILURE"]) {
           [LoadingView showLoadingInView:self.view];
           [self.viewModel generateOrderWithOrder:model success:^(SCMyOrderModel *order) {
               [LoadingView hideLoadinForView:self.view];
               WDlnhospitalPayViewController *orderVC = [WDlnhospitalPayViewController new];
               orderVC.viewModel.payOrder = order;
               [self.navigationController pushViewController:orderVC animated:YES];
           } failed:^{
               [LoadingView hideLoadinForView:self.view];
           }];
        } else {
             WDlnhospitalPayViewController *hospitalPayViewController = [[WDlnhospitalPayViewController  alloc] init];
             hospitalPayViewController.viewModel.payOrder = model;
             [self.navigationController pushViewController:hospitalPayViewController animated:YES];
         }
     } else {
         WDLinkPayViewController *linkPayViewController = [[WDLinkPayViewController alloc] init];
         linkPayViewController.viewModel.payOrder = model;
          [self.navigationController pushViewController:linkPayViewController animated:YES];
     }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
//    if (self.mParentViewController) {
//        SCorderDetailViewController *viewController = [[SCorderDetailViewController alloc] init];
////        viewController.orderModel =[self.viewModel.dataArray objectAtIndex:indexPath.row];
//        [self.navigationController pushViewController:viewController animated:YES];
//   
//    }
}



@end
