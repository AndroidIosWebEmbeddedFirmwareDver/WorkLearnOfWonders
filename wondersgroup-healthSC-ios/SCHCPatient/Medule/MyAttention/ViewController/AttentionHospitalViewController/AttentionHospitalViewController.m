//
//  AttentionHospitalViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AttentionHospitalViewController.h"
#import "AttentionHospitalViewModel.h"
#import "AttentionHospitalCell.h"
#import "SCHospitalHomePageViewController.h"


@interface AttentionHospitalViewController () <UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) AttentionHospitalViewModel *viewModel;

@end


@implementation AttentionHospitalViewController


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
    
    self.viewModel = [[AttentionHospitalViewModel alloc] init];
}

- (void)prepareUI {
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT-64.-44.) style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = [UIColor clearColor];
    self.tableView.tableFooterView = [UIView new];
    [self.view addSubview:self.tableView];
    
    self.tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestAction)];
}

- (void)requestAction {

    WS(weakSelf)
    
    if (!self.viewModel.dataArray.count) {
        [LoadingView showLoadingInView:self.view];
    }
    
    [self.viewModel getAttentionHospitalList:^{
        
        [weakSelf.tableView reloadData];
        [weakSelf endRefresh];
        
    } failure:^{
        [weakSelf endRefresh];
    }];
}

- (void)requestMoreAction {
    
    WS(weakSelf)
    
    [LoadingView showLoadingInView:self.view];
    
    [self.viewModel getMoreAttentionHospitalList:^{
        
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


#pragma mark - tableView delegate && dataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return self.viewModel.dataArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 78.5;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"attentionDoctorCellId";
    AttentionHospitalCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[AttentionHospitalCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    MyAttentionHospitalModel *cellModel = self.viewModel.dataArray[indexPath.row];
    cell.cellModel = cellModel;
    
    if (indexPath.row == 0) {
        cell.lineTopHidden = NO;
    }
    
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    SCHospitalHomePageViewController *vc = [[SCHospitalHomePageViewController alloc] init];
    MyAttentionHospitalModel *cellModel = self.viewModel.dataArray[indexPath.row];
    vc.hospitalID = cellModel.hospitalId;
    [self.navigationController pushViewController:vc animated:YES];
    
}

@end
