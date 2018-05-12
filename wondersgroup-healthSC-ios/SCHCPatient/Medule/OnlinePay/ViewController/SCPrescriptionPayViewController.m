//
//  SCPrescriptionPayViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCPrescriptionPayViewController.h"
#import "SCPrescriptionPayTableViewCell.h"
#import "WDlnhospitalPayViewController.h"

static NSString *const PRESCRIPTION_PAY_TABLEVIEWCELL = @"PRESCRIPTION_PAY_TABLEVIEWCELL";

@interface SCPrescriptionPayViewController ()<UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation SCPrescriptionPayViewController

- (SCPrescriptionPayViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [SCPrescriptionPayViewModel new];
    }
    return _viewModel;
}

- (UITableView *)tableView {
    if (!_tableView) {
        _tableView = [UITableView new];
        _tableView.dataSource = self;
        _tableView.delegate = self;
        _tableView.backgroundColor = [UIColor clearColor];
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView registerNib:[UINib nibWithNibName:@"SCPrescriptionPayTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:PRESCRIPTION_PAY_TABLEVIEWCELL];
        _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestDataNoLoading)];
    }
    return _tableView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindModel];
}

- (void)viewWillAppear:(BOOL)animated {
    [self requestData];
}

- (void)setupView {
    WS(ws)
    
    self.title = @"诊间支付";
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(ws.view);
    }];
}

- (void)requestData {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel requestUnPayRecords:^{
        [LoadingView hideLoadinForView:self.view];
    } failed:^{
        [LoadingView hideLoadinForView:self.view];
    }];
}

- (void)requestDataNoLoading {
    [self.viewModel requestUnPayRecords:^{
        [self.tableView.mj_header endRefreshing];
    } failed:^{
        [self.tableView.mj_header endRefreshing];
    }];
}

- (void)bindModel {
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:
                failType = FailViewEmpty;
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [self.view hiddenFailView];
                [self.tableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [self.view showFailView:failType withAction:^{
                [self requestData];
            }];
        }
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.orderArr.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 274;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCPrescriptionPayTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:PRESCRIPTION_PAY_TABLEVIEWCELL forIndexPath:indexPath];
    cell.order = self.viewModel.orderArr[indexPath.row];
    cell.doPayBlock = ^(SCMyOrderModel *order) {
        [self doPay:order];
    };
    return cell;
}

- (void)doPay:(SCMyOrderModel *)orderModel {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel requestGenerateOrderWithOrder:orderModel
                                          success:^(SCMyOrderModel *order) {
                                              [LoadingView hideLoadinForView:self.view];
                                              WDlnhospitalPayViewController *vc = [WDlnhospitalPayViewController new];
                                              vc.viewModel.payOrder = order;
                                              [self.navigationController pushViewController:vc animated:YES];
                                          } failed:^{
                                              [LoadingView hideLoadinForView:self.view];
                                              [MBProgressHUDHelper showHudWithText:@"获取支付信息失败"];
                                          }];
}



@end
