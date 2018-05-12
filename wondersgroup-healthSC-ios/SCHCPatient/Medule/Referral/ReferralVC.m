//
//  ReferralVC.m
//  SCHCPatient
//
//  Created by Po on 2017/6/1.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "ReferralVC.h"
#import "ReferralViewModel.h"
#import "ReferralCell.h"
#import "ReferralDetailVC.h"

#import "RJTools.h"

static NSString * const ReferralCellID = @"ReferralCellID";

@interface ReferralVC () <UITableViewDelegate, UITableViewDataSource>

@property (strong, nonatomic) UITableView * tableView;

@property (strong, nonatomic) ReferralViewModel * viewModel;

@end

@implementation ReferralVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setTitle:@"转诊信息"];
    
    [self initData];
    [self initInterface];
    [self buildConstraint];
    
    [_tableView reloadData];
}

#pragma mark - user-define initialization
- (void)initData {
    _viewModel = [[ReferralViewModel alloc] init];
}

- (void)initInterface {
    [self configTableView];
}

- (void)doRequest {
    
    
    [self endRefresh];
}


- (void)buildConstraint {
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.bottom.equalTo(self.view);
    }];
}

#pragma mark - event

#pragma mark - function
- (void)endRefresh {
    
//    [_tableView.mj_header endRefreshing];
    [_tableView.mj_footer endRefreshing];
    [_tableView reloadData];
}
#pragma mark - delegate
#pragma mark - tableView delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    ReferralDetailVC * vc = [[ReferralDetailVC alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
//    return _viewModel.dataArray.count;
    return 10;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ReferralCell * cell = [tableView dequeueReusableCellWithIdentifier:ReferralCellID forIndexPath:indexPath];
//    ReferralModel * model = _viewModel.dataArray[indexPath.row];
    [cell setData:nil];
    return cell;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    
}

- (CGFloat)tableView:(UITableView *)tableView estimatedHeightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 50;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return UITableViewAutomaticDimension;
}
#pragma mark - notification

#pragma mark - setter

#pragma mark - getter
- (void)configTableView {
    _tableView = [[UITableView alloc] init];
    [_tableView setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [_tableView setDelegate:self];
    [_tableView setDataSource:self];
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [_tableView registerNib:[UINib nibWithNibName:@"ReferralCell" bundle:nil] forCellReuseIdentifier:ReferralCellID];
    MJWeakSelf
    _tableView.mj_header = [RJTools getRefreshHeader:^{
        weakSelf.viewModel.currentPage = 1;
        [weakSelf doRequest];
    }];
    
    _tableView.mj_header.ignoredScrollViewContentInsetTop = -20;
    
    _tableView.mj_footer = [RJTools getRefreshFooter:^{
        weakSelf.viewModel.currentPage += 1;
        [weakSelf doRequest];
    }];
    [self.view addSubview:_tableView];
}



@end
