//
//  JoinupReferralVC.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "JoinupReferralVC.h"
#import "ReferralCell.h"
#import "ReferralDetailVC.h"

#import "JoinupReferralViewModel.h"

static NSString * const JoinupReferralCellID = @"JoinupReferralCellID";

@interface JoinupReferralVC () <UITableViewDelegate, UITableViewDataSource>
@property (strong, nonatomic) UITableView * tableView;
@property (strong, nonatomic) JoinupReferralViewModel * viewModel;

@end

@implementation JoinupReferralVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setTitle:@"接入转诊"];
    [self initData];
    [self initInterface];
}

#pragma mark - user-define initialization
- (void)initData {
    
}

- (void)initInterface {
    [self.view setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [self getTableView];
    [self buildConstraint];
}

- (void)doRequest {
    [_tableView.mj_footer endRefreshing];
    [_tableView.mj_header endRefreshing];
}

- (void)buildConstraint {
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(self.view);
    }];
}

#pragma mark - event
- (void)headerRefresh {
    [self doRequest];
}

- (void)footerRefresh {
    [self doRequest];
}
#pragma mark - function

#pragma mark - delegate
#pragma mark - tableView delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    ReferralDetailVC * vc = [[ReferralDetailVC alloc] initWithType:ReferralProgressNone];
    [self.navigationController pushViewController:vc animated:YES];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
//    return _viewModel.model.count;
    return 10;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:JoinupReferralCellID forIndexPath:indexPath];
    return cell;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    
//    [self configCell:cell widthModel:_viewModel.model[indexPath.row]]
}

- (CGFloat)tableView:(UITableView *)tableView estimatedHeightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 90;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return UITableViewAutomaticDimension;
}
#pragma mark - notification

#pragma mark - setter
- (void)configCell:(ReferralCell *)cell widthModel:(JoinupReferralModel *)model {
    NSString * name = model.name;
    if (name.length > 5) {
        name = [NSString stringWithFormat:@"%@...",[name substringToIndex:5]];
    }
    
    [cell.userNameLabel setText:name];
    [cell.userImageView sd_setImageWithURL:[NSURL URLWithString:model.avatar] placeholderImage:nil];
    [cell.typeLabel setText:([model.urgency isEqualToString:@"1"] ? @"一般":@"紧急")];
    
    //时间
    NSString * time = model.referralDate;
    NSRange range = [time rangeOfString:@" "];
    if (range.location != NSNotFound) {
        time = [time substringToIndex:range.location];
    }
    [cell.timeLabel setText:time];
    
    [cell.inHospitalLabel setText:model.toOrgName];
    [cell.outHospitalLabel setText:model.fromOrgName];
}
#pragma mark - getter
- (UITableView *)getTableView {
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        [_tableView setDelegate:self];
        [_tableView setDataSource:self];
        
        _tableView.mj_insetT = 10;
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView setBackgroundColor:RGB_COLOR(244, 244, 244)];
        [_tableView registerClass:[ReferralCell class] forCellReuseIdentifier:JoinupReferralCellID];
        _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(headerRefresh)];
        _tableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(footerRefresh)];
        [self.view addSubview:_tableView];
    }
    return _tableView;
}

@end
