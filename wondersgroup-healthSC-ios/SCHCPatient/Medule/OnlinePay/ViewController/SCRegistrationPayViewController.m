//
//  SCRegistrationPayViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCRegistrationPayViewController.h"
#import "SCRegistrationPayTableViewCell.h"
#import "WDLinkPayViewController.h"
#import "SCRegistrationPayViewModel.h"

static NSString *const REGISTRATION_PAY_TABLEVIEWCELL = @"REGISTRATION_PAY_TABLEVIEWCELL";

@interface SCRegistrationPayViewController () <UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) SCRegistrationPayViewModel *viewModel;
@property (nonatomic, strong) UITableView *tableView;

@end

@implementation SCRegistrationPayViewController

- (SCRegistrationPayViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [SCRegistrationPayViewModel new];
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
        _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestDataNoLoading)];
        [_tableView registerNib:[UINib nibWithNibName:@"SCRegistrationPayTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:REGISTRATION_PAY_TABLEVIEWCELL];
    }
    return _tableView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindModel];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self requestData];
}

- (void)setupView {
    WS(ws)
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(ws.view);
    }];
}

- (void)bindModel {
    WS(ws)
    
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
    
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(id x) {
        if (ws.viewModel.hasMore) {
            MJRefreshAutoNormalFooter * footer =[UIUtility footerMoreTarget:self action:@selector(requestMoreData)];
            ws.tableView.mj_footer =footer;
        } else {
            ws.tableView.mj_footer = nil;
        }
    }];
}

- (void)requestData {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel requestRegistration:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
        [self.tableView reloadData];
    } failed:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
    }];
}

- (void)requestDataNoLoading {
    [self.viewModel requestRegistration:^{
        [self endRefreshing];
        [self.tableView reloadData];
    } failed:^{
        [self endRefreshing];
    }];
}

- (void)requestMoreData {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel requestMoreRegistration:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
        [self.tableView reloadData];
    } failed:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
    }];
}

- (void)endRefreshing {
    [self.tableView.mj_header endRefreshing];
    [self.tableView.mj_footer endRefreshing];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.orderArr.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 270;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCRegistrationPayTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:REGISTRATION_PAY_TABLEVIEWCELL forIndexPath:indexPath];
    cell.order = self.viewModel.orderArr[indexPath.row];
    cell.doPayBlock = ^(SCMyOrderModel *order){
        WDLinkPayViewController *vc = [WDLinkPayViewController new];
        vc.viewModel.payOrder = order;
        [self.navigationController pushViewController:vc animated:YES];
    };
    return cell;
}

@end
