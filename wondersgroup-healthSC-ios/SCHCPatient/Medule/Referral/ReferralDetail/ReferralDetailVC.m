//
//  ReferralDetailVC.m
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "ReferralDetailVC.h"

#import "ReferralDetailCell.h"
#import "ReferralDetailInfoCell.h"

#import "ReferralDetailViewModel.h"
#import "RJTools.h"
static NSString * const ReferralDetailInfoCellID = @"ReferralDetailInfoCellID";
static NSString * const ReferralDetailCellID = @"ReferralDetailCellID";

@interface ReferralDetailVC () <UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) UITableView * tableView;

@property (strong, nonatomic) ReferralDetailViewModel * viewModel;
@end

@implementation ReferralDetailVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initData];
    [self initInterface];
}
#pragma mark - user-define initialization
- (void)initData {
    _viewModel = [[ReferralDetailViewModel alloc] init];
}

- (void)initInterface {
    [self configTableView];
    
    [self buildConstraint];
    
    [_tableView reloadData];
}

- (void)buildConstraint {
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.bottom.equalTo(self.view);
    }];
}

- (void)doRequest {
    [_tableView.mj_header endRefreshing];
}

#pragma mark - event

#pragma mark - function

#pragma mark - delegate
#pragma mark - tableView delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _viewModel.baseDataArray.count + _viewModel.referralDataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row < _viewModel.baseDataArray.count) {
        ReferralDetailInfoCell * cell = [tableView dequeueReusableCellWithIdentifier:ReferralDetailInfoCellID forIndexPath:indexPath];
        BOOL showBlank = (indexPath.section ==0 && indexPath.row == 0);
        [cell showTopBlank:showBlank];
        return cell;
    } else {
        ReferralDetailCell * cell = [tableView dequeueReusableCellWithIdentifier:ReferralDetailCellID forIndexPath:indexPath];
        [cell showRegistrationInfoButton:NO];
        //检测详情展示状态
        if (_viewModel.currentDetailShowCount && [indexPath compare:_viewModel.currentDetailShowCount] == NSOrderedSame) {
            [cell setShowDetail:YES];
        } else {
            [cell setShowDetail:NO];
        }
        
        __weak typeof(self) weakSelf = self;
        //点击了展示详情
        [cell setShowDetailBlock:^(UITableViewCell *cell) {
            if (!cell) {
                return;
            }
            NSIndexPath * indexPath = [tableView indexPathForCell:cell];
            if (!indexPath) {
                return;
            }
            NSMutableArray * array = [NSMutableArray arrayWithArray:@[indexPath]];
            NSIndexPath * oldIndexPath = weakSelf.viewModel.currentDetailShowCount;
            
            if ([indexPath compare:oldIndexPath] != NSOrderedSame) {
                if (oldIndexPath) {
                    [array addObject:oldIndexPath];
                }
                weakSelf.viewModel.currentDetailShowCount = indexPath;
            } else {
                weakSelf.viewModel.currentDetailShowCount = nil;
            }
            
            [tableView reloadRowsAtIndexPaths:array withRowAnimation:UITableViewRowAnimationAutomatic];
        }];
        
        //点击了挂号信息
        [cell setClickInfoButton:^(UITableViewCell *cell) {
            if (cell) {
                NSIndexPath * indexPath = [tableView indexPathForCell:cell];
                
            }
        }];
        return cell;
    }
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row < _viewModel.baseDataArray.count) {
        ReferralDetailInfoCell * myCell = (ReferralDetailInfoCell *)cell;
        NSDictionary * data = _viewModel.baseDataArray[indexPath.row];
        [myCell.titleLabel setText:[[data allKeys] lastObject]];
        [myCell.detailLabel setText:[[data allValues] lastObject]];
    } else {
        ReferralDetailCell * myCell = (ReferralDetailCell *)cell;
        
    }
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
    [_tableView registerClass:[ReferralDetailInfoCell class] forCellReuseIdentifier:ReferralDetailInfoCellID];
    [_tableView registerClass:[ReferralDetailCell class] forCellReuseIdentifier:ReferralDetailCellID];
    _tableView.mj_header = [RJTools getRefreshHeader:^{
        [self doRequest];
    }];
    [self.view addSubview:_tableView];
}

@end
