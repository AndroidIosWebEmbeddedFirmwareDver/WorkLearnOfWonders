//
//  AttentionDoctorViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AttentionDoctorViewController.h"
#import "AttentionDoctorViewModel.h"
#import "AttentionDoctorCell.h"
#import "DoctorDetailViewController.h"


@interface AttentionDoctorViewController () <UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) AttentionDoctorViewModel *viewModel;

@end

@implementation AttentionDoctorViewController


- (void)viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    [self requestAction];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self prepareData];
    [self prepareUI];
    [self bindRac];
}

- (void)prepareData {
    
    self.viewModel = [[AttentionDoctorViewModel alloc] init];
}

- (void)prepareUI {
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT-64.-44.) style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.tableFooterView = [UIView new];
    [self.view addSubview:self.tableView];

    self.tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestAction)];
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, viewModel.hasMore) subscribeNext:^(id x) {
        
        BOOL more = [x boolValue];
        if (more) {
            weakSelf.tableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(requestMoreAction)];
        }
        else {
            weakSelf.tableView.mj_footer = nil;
        }
    }];
    
    [RACObserve(self, viewModel.requestCompeleteType) subscribeNext:^(id x) {
        RequestCompeleteType type = [x integerValue];
        
        switch (type) {
            case RequestCompeleteEmpty: {
                [weakSelf.view showFailView:FailViewEmpty withAction:^{
                    [weakSelf requestAction];
                }];
            }
                break;
            case RequestCompeleteError: {
                [weakSelf.view showFailView:FailViewError withAction:^{
                    [weakSelf requestAction];
                }];
            }
                break;
            case RequestCompeleteNoWifi: {
                [weakSelf.view showFailView:FailViewNoWifi withAction:^{
                    [weakSelf requestAction];
                }];
            }
                break;
            case RequestCompeleteSuccess: {
                [weakSelf.tableView reloadData];
                [weakSelf endRefresh];
                [weakSelf.view hiddenFailView];
            }
                break;
            default:
                break;
        }
    }];
}

- (void)requestAction {
    
    if (!self.viewModel.dataArray.count) {
        [LoadingView showLoadingInView:self.view];
    }
    
    WS(weakSelf)
    [self.viewModel getAttentionDoctorList:^{
    
        [weakSelf.tableView reloadData];
        [weakSelf endRefresh];
    } failure:^{
        [weakSelf endRefresh];
    }];
}

- (void)requestMoreAction {
    
    WS(weakSelf)
    [self.viewModel getMoreAttentionDoctorList:^{

        [weakSelf.tableView reloadData];
        [weakSelf endRefresh];
    } failure:^{
        [weakSelf endRefresh];
    }];
}

- (void)endRefresh {
    [LoadingView hideLoadinForView:self.view];
    [self.tableView.mj_header endRefreshing];
    [self.tableView.mj_footer endRefreshing];
}

#pragma mark - tableView delegate && dataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return self.viewModel.dataArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 90.;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"attentionDoctorCellId";
    AttentionDoctorCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[AttentionDoctorCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    MyAttentionDoctorModel *cellModel = self.viewModel.dataArray[indexPath.row];
    cell.cellModel = cellModel;
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    MyAttentionDoctorModel *cellModel = self.viewModel.dataArray[indexPath.row];
    
    DoctorDetailViewController *viewController = [[DoctorDetailViewController alloc] init];
    viewController.showRegister = YES;
    viewController.hospitalCode = cellModel.hosOrgCode;
    viewController.hosDeptCode = cellModel.hosDeptCode;
    viewController.hosDoctCode = cellModel.hosDoctCode;
    [self.navigationController pushViewController:viewController animated:YES];
}

@end
