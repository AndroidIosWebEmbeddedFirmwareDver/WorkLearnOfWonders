//
//  DoctorDetailMoreJudgeViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/22.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailMoreJudgeViewController.h"
#import "DoctorDetailJudgeModel.h"
#import "DoctorDetailJudgeCell.h"
#import "DoctorDetailMoreJudgeViewModel.h"


@interface DoctorDetailMoreJudgeViewController () <UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) DoctorDetailMoreJudgeViewModel *viewModel;

@end

@implementation DoctorDetailMoreJudgeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self prepareData];
    [self prepareUI];
    [self bindRac];
    [self requestAction];
}

- (void)prepareData {
    
    self.viewModel = [[DoctorDetailMoreJudgeViewModel alloc] init];
    self.viewModel.request_doctor_id = self.doctorId;
}

- (void)prepareUI {
    
    self.navigationItem.title = [NSString stringWithFormat:@"患者评价(%@)", self.judgeCount];
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT-64.) style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.tableFooterView = [UIView new];
    [self.view addSubview:self.tableView];
    
    self.tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestAction)];
}


- (void)requestAction {
    
    [LoadingView showLoadingInView:self.view];
    
    WS(weakSelf)
    [self.viewModel getJudgeList:^{
        
        [weakSelf.tableView reloadData];
        [weakSelf endRefresh];
    } failure:^{
        [weakSelf endRefresh];
    }];
}

- (void)requestMoreAction {
    
    WS(weakSelf)
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getMoreJudgeList:^{
        
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
            default:
                break;
        }
    }];
}


#pragma mark - tableView delegate & dataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return self.viewModel.dataArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    DoctorDetailJudgeModel *cellModel = self.viewModel.dataArray[indexPath.row];
    return cellModel.cellHeight;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"DoctorDetailMoreJudgeCellIdentifier";
    DoctorDetailJudgeCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[DoctorDetailJudgeCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    DoctorDetailJudgeModel *cellModel = self.viewModel.dataArray[indexPath.row];
    cell.cellModel = cellModel;
    
    return cell;
}

@end
