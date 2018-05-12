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
static NSString * const ReferralDetailInfoCellID = @"ReferralDetailInfoCellID";
static NSString * const ReferralDetailCellID = @"ReferralDetailCellID";

@interface ReferralDetailVC () <UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) UITableView * tableView;                  //列表
@property (strong, nonatomic) UIButton * cancelButton;                  //取消申请

@property (strong, nonatomic) ReferralDetailViewModel * viewModel;
@property (assign, nonatomic) ReferralProgress referralType;
@end

@implementation ReferralDetailVC

- (instancetype)initWithType:(ReferralProgress)type {
    self = [super init];
    if (self) {
        _referralType = type;
    }
    return self;
}

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
    [self configCancelButton];
    [self configTableView];
    [self buildConstraint];
    
    [_tableView reloadData];
}

- (void)buildConstraint {
    
    if (_referralType == ReferralProgressRequest) {
        [_cancelButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.view).offset(-15);
            make.left.equalTo(self.view).offset(15);
            make.right.equalTo(self.view).offset(-15);
            make.height.mas_equalTo(44);
        }];
    }
    
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        if (_referralType == ReferralProgressRequest) {
            make.bottom.equalTo(_cancelButton.mas_top).offset(-15);
        } else {
            make.bottom.equalTo(self.view);
        }
    }];
}

- (void)bindRAC {
    MJWeakSelf
    if (_cancelButton) {
        [[_cancelButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
            [weakSelf.navigationController popViewControllerAnimated:YES];
        }];
    }
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
//    return _viewModel.baseDataTitles.count + _viewModel.model.referralModel.count;
    return _viewModel.baseDataTitles.count + 2;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row < _viewModel.baseDataTitles.count) {
        return [self getBaseCellWithTableView:tableView cellForRowAtIndexPath:indexPath];
    } else {
        return [self getDetailCellWithTableView:tableView cellForRowAtIndexPath:indexPath];
    }
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row < _viewModel.baseDataTitles.count) {
        ReferralDetailInfoCell * myCell = (ReferralDetailInfoCell *)cell;
        NSString * title = _viewModel.baseDataTitles[indexPath.row];
        [myCell.titleLabel setText:title];
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
- (UITableViewCell *)getBaseCellWithTableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ReferralDetailInfoCell * cell = [tableView dequeueReusableCellWithIdentifier:ReferralDetailInfoCellID forIndexPath:indexPath];
    BOOL showBlank = (indexPath.section ==0 && indexPath.row == 0);
    [cell showTopBlank:showBlank];
    
    NSString * title = _viewModel.baseDataTitles[indexPath.row];
    NSString * detail = @"";
    switch (indexPath.row) {
        case 0:
            detail = _viewModel.model.gender;
            break;
        case 1:
            detail = _viewModel.model.age;
            break;
        case 2:
            detail = _viewModel.model.VisitCardNum;
            break;
        case 3:
            detail = _viewModel.model.idCard;
            break;
        case 4:
            detail = _viewModel.model.mobile;
            break;
        case 5:
            detail = _viewModel.model.address;
            break;
        default:
            break;
    }
    [cell.titleLabel setText:title];
    [cell.detailLabel setText:detail];
    
    return cell;
}

- (UITableViewCell *)getDetailCellWithTableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ReferralDetailCell * cell = [tableView dequeueReusableCellWithIdentifier:ReferralDetailCellID forIndexPath:indexPath];
    [cell showRegistrationInfoButton:NO];
    //检测详情展示状态
    if (indexPath.row == _viewModel.currentDetailShowCount.row) {
        [cell setShowDetail:YES];
    } else {
        [cell setShowDetail:NO];
    }
    
    //设置数据
    NSInteger count = indexPath.row - _viewModel.baseDataTitles.count;
    ReferralDetailReferralModel * data = _viewModel.model.referralModel[count];
    [cell setData:data];
    
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
        
        [weakSelf.tableView reloadRowsAtIndexPaths:array withRowAnimation:UITableViewRowAnimationAutomatic];
        
    }];
    
    //点击了挂号信息
    [cell setClickInfoButton:^(UITableViewCell *cell) {
        if (cell) {
            NSIndexPath * indexPath = [tableView indexPathForCell:cell];
            
        }
    }];
    return cell;
}

- (void)isJoinReferr:(BOOL)isJoinReferr {
    _viewModel.isJoinReferr = isJoinReferr;
}
#pragma mark - getter
- (void)configTableView {
    _tableView = [[UITableView alloc] init];
    [_tableView setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [_tableView setDelegate:self];
    [_tableView setDataSource:self];
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [_tableView registerClass:[ReferralDetailInfoCell class] forCellReuseIdentifier:ReferralDetailInfoCellID];
    [_tableView registerClass:[ReferralDetailCell class] forCellReuseIdentifier:ReferralDetailCellID];
    _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(doRequest)];
    [self.view addSubview:_tableView];
}

- (void)configCancelButton {
    if (_referralType == ReferralProgressRequest) {
        _cancelButton = [[UIButton alloc] init];
        [_cancelButton setTitle:@"取消申请" forState:UIControlStateNormal];
        [_cancelButton setBackgroundColor:[UIColor whiteColor]];
        [_cancelButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
        [_cancelButton setTitleColor:RGB_COLOR(102, 102, 102) forState:UIControlStateNormal];
        [self.view addSubview:_cancelButton];
    }
}

@end
