//
//  ReferralVC.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralVC.h"
#import "RJSegmentView.h"
#import "ReferralCell.h"
#import "ReferralDetailVC.h"
#import "ReferralActionSheetView.h"
#import "OutpatientChooseVC.h"
#import "JoinupReferralVC.h"

#import "ReferralViewModel.h"
#import "ReferralModel.h"
static NSString * const ReferralCellID = @"ReferralCellID";

@interface ReferralVC () <UITableViewDelegate, UITableViewDataSource>
@property (strong, nonatomic) RJSegmentView             * segmentView;
@property (strong, nonatomic) ReferralActionSheetView   * sheetView;                    //选择列表
@property (strong, nonatomic) NSArray                   * allTableViews;                //所有列表
@property (strong, nonatomic) UIScrollView              * scrollView;
@property (strong, nonatomic) UITableView               * currentTableView;             //当前列表
@property (strong, nonatomic) UIButton                  * requestReferralButton;        //申请转诊
@property (strong, nonatomic) UIButton                  * getReferralButton;            //接入转诊

@property (strong, nonatomic) ReferralViewModel * viewModel;


@end

@implementation ReferralVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setTitle:@"转诊服务"];
    [self initData];
    [self initInterface];
}

#pragma mark - user-define initialization
- (void)initData {
    _viewModel = [[ReferralViewModel alloc] init];
}

- (void)initInterface {
    [self getSegmentView];
    [self getScrollView];
    
    NSMutableArray * temp = [NSMutableArray array];
    for (NSInteger index = 0; index < 5; index ++) {
        [temp addObject:[self getTableView]];
    }
    _allTableViews = [NSArray arrayWithArray:temp];
    _currentTableView = _allTableViews[0];
    
    _requestReferralButton = [[UIButton alloc] init];
    [_requestReferralButton setTitle:@"申请转诊" forState:UIControlStateNormal];
    [_requestReferralButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_requestReferralButton setBackgroundColor:RGB_COLOR(46, 122, 240)];
    [_requestReferralButton.titleLabel setFont:[UIFont fontWithName:@" PingFangSC-Semibold" size:16]];
    [self.view addSubview:_requestReferralButton];
    
    _getReferralButton = [[UIButton alloc] init];
    [_getReferralButton setTitle:@"接入转诊" forState:UIControlStateNormal];
    [_getReferralButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_getReferralButton setBackgroundColor:RGB_COLOR(255, 162, 23)];
    [_getReferralButton.titleLabel setFont:[UIFont fontWithName:@" PingFangSC-Semibold" size:16]];
    [self.view addSubview:_getReferralButton];
    
    [self buildConstraint];
    [self bindRAC];
//
    [self doRequest];
}

- (void)doRequest {
    MJWeakSelf
    [self requestDataWithPage:0];
    
    [_viewModel requestStateWithSuccess:^(id content) {
        ReferralStateModel * model = _viewModel.stateModel;
        RJRedButton * button = weakSelf.segmentView.buttonsArray[1];
        [button showRedPoint:model.hasRequesting];
        button = weakSelf.segmentView.buttonsArray[2];
        [button showRedPoint:model.hasRejected];
        button = weakSelf.segmentView.buttonsArray[3];
        [button showRedPoint:model.hasReferred];
        button = weakSelf.segmentView.buttonsArray[4];
        [button showRedPoint:model.hasCanceled];
        
    } fail:^(NSError *error, NSString *errorString) {
        
    }];
}

- (void)buildConstraint {
    MJWeakSelf
    [_segmentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(self.view);
        make.height.mas_equalTo(44);
    }];
    
    [_scrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_segmentView.mas_bottom);
        make.left.right.equalTo(self.view);
        make.bottom.equalTo(self.view.mas_bottom).offset(-50);
    }];
    
    [_requestReferralButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.equalTo(self.view);
        make.top.equalTo(_scrollView.mas_bottom);
    }];
    
    [_getReferralButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.bottom.equalTo(self.view);
        make.top.equalTo(_requestReferralButton);
        make.left.equalTo(_requestReferralButton.mas_right);
        make.width.equalTo(_requestReferralButton);
    }];
    
    for (NSInteger index = 0; index < _allTableViews.count; index ++) {
        UITableView * tableView = _allTableViews[index];
        [tableView setFrame:CGRectMake(SCREEN_WIDTH * index, 0, SCREEN_WIDTH, SCREEN_HEIGHT - 158)];
    }
}

- (void)bindRAC {
    MJWeakSelf
    
    [[_requestReferralButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
        [weakSelf showShooceSheetView];
    }];
    
    [[_getReferralButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
        JoinupReferralVC * vc = [[JoinupReferralVC alloc] init];
        [weakSelf.navigationController pushViewController:vc animated:YES];
    }];
    
    [[[NSNotificationCenter defaultCenter] rac_addObserverForName:@"PopToReferralHomeVC" object:nil] subscribeNext:^(NSNotification * _Nullable x) {
        NSInteger count = [weakSelf.navigationController.viewControllers indexOfObject:weakSelf];
        if (count) {
            [weakSelf.navigationController popToViewController:weakSelf animated:YES];
        }
    }];

}

#pragma mark - event
- (void)headerRefresh {
    [self requestDataWithPage:0];
}

- (void)footerRefresh {
    [self requestDataWithPage:_viewModel.currentPage + 1];
}
#pragma mark - function
- (void)requestDataWithPage:(NSInteger)page {
    MJWeakSelf
    [_viewModel requestDataWithPage:0 Success:^(id content) {
        
    } fail:^(NSError *error, NSString *errorString) {
        [weakSelf.view showFailViewWith:[UIImage imageNamed:@"无数据"] withTitle:@"暂无数据" withAction:^{
            [weakSelf doRequest];
        }];
        [weakSelf.currentTableView.mj_footer endRefreshing];
        [weakSelf.currentTableView.mj_header endRefreshing];
    }];
}

#pragma mark - delegate
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView {
    NSInteger count = scrollView.contentOffset.x / SCREEN_WIDTH;
    [_segmentView setSelectedCount:count];
    [self changeSegmentIndex:count];
}
#pragma mark - tableView delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    ReferralDetailVC * vc = [[ReferralDetailVC alloc] initWithType:ReferralProgressRequest];
    [self.navigationController pushViewController:vc animated:YES];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    NSInteger count = [_allTableViews indexOfObject:tableView];
//    NSArray * temp = _viewModel.allModels[_viewModel.currentSegmentNum];
//    return temp.count;
    return 10;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ReferralCell * cell = [tableView dequeueReusableCellWithIdentifier:ReferralCellID forIndexPath:indexPath];
    [cell setPriority:ReferralPriorityMedieam];
    if (_viewModel.currentSegmentNum == 0) {
        [cell showProgress:indexPath.row % 4];
    } else {
        [cell showProgress:ReferralProgressNone];
    }
    return cell;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
//    NSArray * temp = _viewModel.allModels[_viewModel.currentSegmentNum];
//    ReferralModel * model = temp[indexPath.row];
//    [self configCell:(ReferralCell *)cell widthModel:model];
}

- (CGFloat)tableView:(UITableView *)tableView estimatedHeightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 90;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return UITableViewAutomaticDimension;
}
#pragma mark - notification

#pragma mark - setter
- (void)configCell:(ReferralCell *)cell widthModel:(ReferralModel *)model {
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
    
    //状态
    if (_viewModel.currentSegmentNum == 0) {
        NSInteger statusNum = [model.referralStatus integerValue];
        if (statusNum && statusNum < 4) {
            NSString * status = @[@"申请中",@"已驳回",@"已转诊",@"已取消"][statusNum];
            [cell.progressLabel setText:status];
        }
    }
}

- (void)changeSegmentIndex:(NSInteger)index {

    [_currentTableView.mj_header endRefreshing];
    [_currentTableView.mj_footer endRefreshing];
    _currentTableView = _allTableViews[index];
    
    _viewModel.currentSegmentNum = index;
//    _viewModel.currentModel = _viewModel.allModels[index];
//    _viewModel.currentPage = _viewModel.currentModel.count / _viewModel.pageSize;
    [_currentTableView reloadData];
    
    [_scrollView setContentOffset:CGPointMake(SCREEN_WIDTH * index, 0)];
}

#pragma mark - getter
- (RJSegmentView *)getSegmentView {
    if (!_segmentView) {
        _segmentView = [[RJSegmentView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 44)];
        [_segmentView setBackgroundColor:[UIColor whiteColor]];
        [_segmentView setTitlesArray:_viewModel.segmentTitles];
        
        //点击事件
        MJWeakSelf
        [_segmentView setSelectBlock:^(NSInteger count, UIButton *currentButton) {
            [weakSelf changeSegmentIndex:count];
        }];
        
        [self.view addSubview:_segmentView];
        [_segmentView build];
    }
    return _segmentView;
}

- (UITableView *)getTableView {
    UITableView * tableView = [[UITableView alloc] init];
    [tableView setDelegate:self];
    [tableView setDataSource:self];
    tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [tableView registerClass:[ReferralCell class] forCellReuseIdentifier:ReferralCellID];
    tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(headerRefresh)];
    tableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(footerRefresh)];
    [_scrollView addSubview:tableView];
    return tableView;
}

- (UIScrollView *)getScrollView {
    _scrollView = [[UIScrollView alloc] init];
    [_scrollView setContentSize:CGSizeMake(SCREEN_WIDTH * 5, 0)];
    [_scrollView setDelegate:self];
    [_scrollView setPagingEnabled:YES];
    [self.view addSubview:_scrollView];
    return _scrollView;
}


- (void) showShooceSheetView {
    if (_sheetView) {
        [_sheetView removeFromSuperview];
    }
    _sheetView = [[ReferralActionSheetView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 132) titles:@[@"门诊转诊",@"住院转诊"]];
    MJWeakSelf
    [_sheetView setClickBlock:^(NSInteger index, BOOL isCancel) {
        if (!isCancel) {
            ReferralType type = (index == 0) ? ReferralTypeOutpatient : ReferralTypeHospitalized;
            OutpatientChooseVC * vc = [[OutpatientChooseVC alloc] initWithType:type];
            [weakSelf.navigationController pushViewController:vc animated:YES];
        }
    }];
    [[[UIApplication sharedApplication].delegate window] addSubview:_sheetView];
    
    [_sheetView showAnim];
}

@end
