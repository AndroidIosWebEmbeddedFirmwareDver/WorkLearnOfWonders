//
//  CDHospitalHomePageViewController.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalHomePageViewController.h"
#import "SCHospitalHomePageViewModel.h"
#import "SCHospitalHomePageLocalCell.h"
#import "SCHospitalHomePageItemsCell.h"
#import "SCHospitalHomePageEvaluationTitleCell.h"
#import "SCHospitalEvaluationCell.h"
#import "SCHospitalHomePageCellModel.h"
#import "SCHospitalEvaluationListViewController.h"
#import "SCEvaluationInputView.h"
#import "SCHospitalHomePageSummarizeVC.h"
#import "SCHospitalHomePageHeaderCell.h"
#import "WDHospitalListRootViewController.h"
#import "SCDepartmentViewController.h"
#import "WDBaseWebViewController.h"

static NSString *const kHeaderCellReuseID           = @"kHeaderCellReuseID";
static NSString *const kLocalCellReuseID            = @"kLocalCellReuseID";
static NSString *const kItemsCellReuseID            = @"kItemsCellReuseID";
static NSString *const kEvaluationTitleCellReuseID  = @"kEvaluationTitleCellReuseID";
static NSString *const kEvaluationCellReuseID       = @"kEvaluationCellReuseID";
static NSString *const kHeaderViewReuseID           = @"kHeaderViewReuseID";

static CGFloat kBottomInputViewHeight = 44.0;

#define EVALUATION_PUBLISH_LIMITED 150

@interface SCHospitalHomePageViewController ()<UITableViewDataSource, UITableViewDelegate, UIScrollViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) SCHospitalHomePageViewModel *viewModel;
@property (nonatomic, strong) NSMutableArray *dataArray;

@property (nonatomic, strong) SCEvaluationInputView *inputView;

@property (nonatomic, strong) UIView *customNavBar;
@property (nonatomic, strong) UIButton *navCollectBtn;
@property (nonatomic, strong) UIView *navBarBgView;
@property (nonatomic, strong) UIButton *navBackBtn;
@property (nonatomic, strong) UILabel *navTitleLabel;
@property (nonatomic, strong) CAGradientLayer *gradientLayer;

/// 解决来回push nav闪屏的bug用
@property (nonatomic, assign, getter=isKeepNavStatus) BOOL keepNavStatus;


@end

@implementation SCHospitalHomePageViewController


#pragma mark - Life Circle

- (void)viewDidLoad {
    [super viewDidLoad];

    [self setupSubviews];
    [self setupCustomNavigationBar];
    
    [self bind];
    [self.tableView.mj_header beginRefreshing];

}


- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
}


- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    if (self.isKeepNavStatus == NO) {
        [self.navigationController setNavigationBarHidden:NO animated:YES];
    }
}



#pragma mark - Action

- (void)pushToItemVCWithType:(HospitalHomePageItemType)type {
    switch (type) {
        case HospitalHomePageItemTypeHospitalInfo: {
            [self pushToHospitalHomePageSummarizeVC];
            break;
        }
        case HospitalHomePageItemTypeRegister: {
            [self pushToDepartmentVC];
            break;
        }
        case HospitalHomePageItemTypeDoctor: {
            [self pushToDepartmentVC];
            break;
        }
        case HospitalHomePageItemTypeDiagnose: {
            WDBaseWebViewController * vc = [[WDBaseWebViewController alloc]initWithURL:@"http://amc.huimeionline.com?key=E156F227CF8ACC79F19CA744A0A0AD73"];
            vc.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:vc animated:YES];
            break;
        }
        default:
            break;
    }
}


- (void)pushToHospitalHomePageSummarizeVC {
    SCHospitalHomePageSummarizeVC *vc = [[SCHospitalHomePageSummarizeVC alloc] init];
    vc.hospitalModel = self.viewModel.hospitalModel;
    self.keepNavStatus = YES;
    [self.navigationController pushViewController:vc animated:YES];
}


- (void)pushToDepartmentVC {
    if (![UserManager manager].isLogin) {
        [[VCManager manager] presentLoginViewController:YES];
        return;
    }
    
    SCDepartmentViewController *vc = [SCDepartmentViewController new];
    vc.viewModel.hospitalID = self.viewModel.hospitalModel.hospitalId;
    vc.viewModel.hospitalCode = self.viewModel.hospitalModel.hospitalCode;
    self.keepNavStatus = NO;
    [self.navigationController pushViewController:vc animated:YES];
}


- (void)pushToEvaluationVC {
    SCHospitalEvaluationListViewController *vc = [[SCHospitalEvaluationListViewController alloc] init];
    vc.hospitalID = self.hospitalID;
    vc.evaluationCount = self.viewModel.hospitalModel.evaluateCount;
    self.keepNavStatus = NO;
    [self.navigationController pushViewController:vc animated:YES];
}


- (void)doCollectAction:(UIButton *)collectBtn {
    if (![UserManager manager].isLogin) {
        [[VCManager manager] presentLoginViewController:YES];
        return;
    }
    [self doCollectRequstActionWithCollectedStatus:collectBtn.selected];
}


- (void)callActionWithTelNumber:(NSString *)telNumber {
    if (telNumber == nil || [[telNumber stringByDeletingTerminalSpace] isEqualToString:@""]) {
        return;
    }
    
    NSURL *telURL = [NSURL URLWithString:[NSString stringWithFormat:@"tel://%@", telNumber]];
    UIAlertController *alertC = [UIAlertController alertControllerWithTitle:[NSString stringWithFormat:@"拨打%@", telNumber]
                                                                    message:nil
                                                             preferredStyle:UIAlertControllerStyleAlert];
    [alertC addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
    }]];
    
    [alertC addAction:[UIAlertAction actionWithTitle:@"拨打" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        if (![[UIApplication sharedApplication] canOpenURL:telURL]) {
            NSLog(@"=====>> 无法拨打号码: %@", telNumber);
            return;
        }
        
        [[UIApplication sharedApplication] openURL:telURL];
    }]];
    

    [self.navigationController presentViewController:alertC animated:YES completion:^{
        
    }];
}



#pragma mark - Bind

- (void)bind {

    RAC(self.navCollectBtn, selected) = RACObserve(self.viewModel, collected);
    
    @weakify(self);
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *x) {
        @strongify(self);
        RequestCompeleteType requestCompeleteType = [x integerValue];
        FailViewType type = FailViewUnknow;
        switch (requestCompeleteType) {
            case RequestCompeleteEmpty: {
                type = FailViewEmpty;
                break;
            }
            case RequestCompeleteError: {
                type = FailViewError;
                break;
            }
            case RequestCompeleteNoWifi: {
                type = FailViewNoWifi;
                break;
            }
            case RequestCompeleteSuccess: {
                type = FailViewUnknow;
                break;
            }
            default:
                break;
        }
        
        if (type != FailViewUnknow) {
            [self.view showFailView:type withAction:^{
                [self requestData];
            }];
            [self.view bringSubviewToFront:_customNavBar];
        }
        
    }];
    
}



#pragma mark - Request 

- (void)pullRequestData {
    __weak typeof(self) weakSelf = self;
    [self.viewModel requsetHospitalHomePageData:self.hospitalID withSuccessHandle:^(SCHospitalHomePageModel *hospitalModel) {
        __strong typeof(self) self = weakSelf;
        [self.view hiddenFailView];
        [_tableView.mj_header endRefreshing];
        
        if (hospitalModel) {
            [self recombineDataArrayWithHospitalModel:hospitalModel];
            [self.tableView reloadData];
        }
        else {
            [self.view showFailView:FailViewEmpty withAction:^{
                [self requestData];
            }];
            [self.view bringSubviewToFront:_customNavBar];
        }
        
    } failureHandle:^(NSError *error) {
        [_tableView.mj_header endRefreshing];
    }];
}


- (void)requestData {
    __weak typeof(self) weakSelf = self;
    [LoadingView showLoadingInView:self.view];
    [self.viewModel requsetHospitalHomePageData:self.hospitalID withSuccessHandle:^(SCHospitalHomePageModel *hospitalModel) {
        __strong typeof(self) self = weakSelf;
        [self.view hiddenFailView];
        [_tableView.mj_header endRefreshing];
        [LoadingView hideLoadinForView:self.view];
        
        if (hospitalModel) {
            [self recombineDataArrayWithHospitalModel:hospitalModel];
            [self.tableView reloadData];
        }
        else {
            [self.view showFailView:FailViewEmpty withAction:^{
                [self requestData];
            }];
            [self.view bringSubviewToFront:_customNavBar];
        }
        
    } failureHandle:^(NSError *error) {
        [LoadingView hideLoadinForView:self.view];
        [_tableView.mj_header endRefreshing];
    }];
}


- (void)doCollectRequstActionWithCollectedStatus:(BOOL)isCollect {
    [MBProgressHUDHelper showHudIndeterminate];
    [self.viewModel collectHospitalWithHospitalID:self.viewModel.hospitalModel.hospitalId successHandle:^{
        
    } failureHandle:^(NSError *error) {
        
    }];
}


- (void)publishEvaluation:(NSString *)evaluation {
    NSLog(@"输入的评价内容: %@", evaluation);
    if (!evaluation
        || [[evaluation stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]] isEqualToString:@""]
        || [[evaluation stringByTrimmingCharactersInSet:[NSCharacterSet newlineCharacterSet]] isEqualToString:@""]) {
        [MBProgressHUDHelper showHudWithText:@"请输入评价内容"];
        return;
    }
    
    if (evaluation.length > EVALUATION_PUBLISH_LIMITED) {
        [MBProgressHUDHelper showHudWithText:@"最多输入149个字"];
        return;
    }
    
    [MBProgressHUDHelper showHudIndeterminate];
    [self.viewModel publishEvaluation:evaluation withHospitalID:self.hospitalID successHandle:^{
        [_inputView dismiss];
        [self requestData];
    } failureHandle:^(NSError *error) {
        
    }];
}




#pragma mark - TableView Datasource && Delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.dataArray.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCHospitalHomePageCellModel *cellModel = self.dataArray[indexPath.row];
    switch (cellModel.cellType) {
        case HospitalHomePageCellTypeHeader: {
            SCHospitalHomePageHeaderCell *cell = [tableView dequeueReusableCellWithIdentifier:kHeaderCellReuseID forIndexPath:indexPath];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        case HospitalHomePageCellTypeLocal: {
            SCHospitalHomePageLocalCell *cell = [tableView dequeueReusableCellWithIdentifier:kLocalCellReuseID forIndexPath:indexPath];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        case HospitalHomePageCellTypeItems: {
            SCHospitalHomePageItemsCell *cell = [tableView dequeueReusableCellWithIdentifier:kItemsCellReuseID forIndexPath:indexPath];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        case HospitalHomePageCellTypeEvaluationTitle: {
            SCHospitalHomePageEvaluationTitleCell *cell = [tableView dequeueReusableCellWithIdentifier:kEvaluationTitleCellReuseID forIndexPath:indexPath];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        case HospitalHomePageCellTypeEvaluation: {
            SCHospitalEvaluationCell *cell = [tableView dequeueReusableCellWithIdentifier:kEvaluationCellReuseID forIndexPath:indexPath];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        
        default:
            break;
    }
}


- (void)configCell:(UITableViewCell *)cell withIndexPath:(NSIndexPath *)indexPath {
    SCHospitalHomePageCellModel *cellModel = self.dataArray[indexPath.row];
    
    __weak typeof(self) weakSelf = self;
    switch (cellModel.cellType) {
        case HospitalHomePageCellTypeHeader: {
            SCHospitalHomePageHeaderCell *headerCell = (SCHospitalHomePageHeaderCell *)cell;
            headerCell.model = cellModel.model;
            break;
        }
        case HospitalHomePageCellTypeLocal: {
            SCHospitalHomePageLocalCell *localCell = (SCHospitalHomePageLocalCell *)cell;
            localCell.model = cellModel.model;
            localCell.telClickedHandler = ^(NSString *tel){
                [self callActionWithTelNumber:tel];
            };
            break;
        }
        case HospitalHomePageCellTypeItems: {
            SCHospitalHomePageItemsCell *itemsCell = (SCHospitalHomePageItemsCell *)cell;
            itemsCell.tapHandler = ^(HospitalHomePageItemType type){
                __strong typeof(self) self = weakSelf;
                [self pushToItemVCWithType:type];
            };
            break;
        }
        case HospitalHomePageCellTypeEvaluationTitle: {
            SCHospitalHomePageEvaluationTitleCell *evaluationTitleCell = (SCHospitalHomePageEvaluationTitleCell *)cell;
            evaluationTitleCell.model = cellModel.model;
            evaluationTitleCell.moreTapHandler = ^(){
                __strong typeof(self) self = weakSelf;
                [self pushToEvaluationVC];
            };
            break;
        }
        case HospitalHomePageCellTypeEvaluation: {
            SCHospitalEvaluationCell *evaluationCell = (SCHospitalEvaluationCell *)cell;
            evaluationCell.model = cellModel.model;
            break;
        }
            
        default:
            break;
    }
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *identifier = nil;
    SCHospitalHomePageCellModel *cellModel = self.dataArray[indexPath.row];
    switch (cellModel.cellType) {
        case HospitalHomePageCellTypeHeader: {
            return 220;
            break;
        }
        case HospitalHomePageCellTypeLocal: {
            identifier = kLocalCellReuseID;
            break;
        }
        case HospitalHomePageCellTypeItems: {
            identifier = kItemsCellReuseID;
            break;
        }
        case HospitalHomePageCellTypeEvaluationTitle: {
            identifier = kEvaluationTitleCellReuseID;
            break;
        }
        case HospitalHomePageCellTypeEvaluation: {
            identifier = kEvaluationCellReuseID;
            break;
        }
            
        default:
            break;
    }
    
    return [tableView fd_heightForCellWithIdentifier:identifier configuration:^(id cell) {
        [self configCell:cell withIndexPath:indexPath];
    }];
}


- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    CGFloat offsetY = scrollView.contentOffset.y;
//    NSLog(@"------: %f", offsetY);
    
    
    /// 更改版本: 1.0
    /// 修改人员: Joseph Gao
    /// 修改描述: 暂时别删
//    CGFloat h = 100.0;
//    if (offsetY <= 0) {
//        _customNavBar.alpha = 1.0;
//    }
//    else if (offsetY > 0 && offsetY <= h) {
//        _customNavBar.alpha = (h - offsetY)/h;
//    }
//    else {
//        _customNavBar.alpha = 0;
//    }
    
    
    CGFloat h = 100.0;
    if (offsetY <= 0) {
        [self setupNavTopState];
        
    } else {
        _navBarBgView.alpha = (offsetY <= h) ? offsetY/h : 1;
        [self setupNavScrollingState];
    }
}


- (void)setupNavTopState {
    _navBarBgView.alpha = 0.0;
    _gradientLayer.opacity = 1.0;
    _navTitleLabel.textColor = [UIColor whiteColor];
    [_navBackBtn setImage:[UIImage imageNamed:@"login_back"] forState:UIControlStateNormal];
    [_navCollectBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_navCollectBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateSelected];
    [_navCollectBtn setImage:[UIImage imageNamed:@"icon未关注"] forState:UIControlStateNormal];
    [_navCollectBtn setImage:[UIImage imageNamed:@"icon已关注"] forState:UIControlStateSelected];
}


- (void)setupNavScrollingState {
    _gradientLayer.opacity = 0.0;
    _navTitleLabel.textColor = [UIColor tc1Color];
    [_navBackBtn setImage:[UIImage imageNamed:@"icon_back"] forState:UIControlStateNormal];
    [_navCollectBtn setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
    [_navCollectBtn setTitleColor:[UIColor tc1Color] forState:UIControlStateSelected];
    [_navCollectBtn setImage:[UIImage imageNamed:@"icon未关注灰"] forState:UIControlStateNormal];
    [_navCollectBtn setImage:[UIImage imageNamed:@"icon已关注"] forState:UIControlStateSelected];
}



#pragma mark - Data

- (void)recombineDataArrayWithHospitalModel:(SCHospitalHomePageModel *)hospitalModel {
    [self.dataArray removeAllObjects];
    
    [self.dataArray addObject:[SCHospitalHomePageCellModel modelWithCellType:HospitalHomePageCellTypeHeader model:hospitalModel]];
    [self.dataArray addObject:[SCHospitalHomePageCellModel modelWithCellType:HospitalHomePageCellTypeLocal model:hospitalModel]];
    [self.dataArray addObject:[SCHospitalHomePageCellModel modelWithCellType:HospitalHomePageCellTypeItems model:nil]];
    [self.dataArray addObject:[SCHospitalHomePageCellModel modelWithCellType:HospitalHomePageCellTypeEvaluationTitle model:hospitalModel]];
    
    if (!hospitalModel.evaluList || hospitalModel.evaluList.count == 0) {
        return;
    }
    [hospitalModel.evaluList enumerateObjectsUsingBlock:^(SCHospitalHomePageModel * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        [self.dataArray addObject:[SCHospitalHomePageCellModel modelWithCellType:HospitalHomePageCellTypeEvaluation model:obj]];
    }];
}



#pragma mark - Setup UI

- (void)setupSubviews {
    [self setupTableView];
    
    if ([UserManager manager].isLogin) {
        [self setupBottomInputView];
    }
    
}


- (void)setupTableView {
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _tableView.clipsToBounds = NO;
    _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(pullRequestData)];
    [self registerTableViewCell];
    [self.view addSubview:_tableView];
    
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.view);
        if ([UserManager manager].isLogin) {
            make.bottom.equalTo(self.view).offset(-kBottomInputViewHeight);
        }
        else {
            make.bottom.equalTo(self.view);
        }
    }];
}


- (void)setupBottomInputView {
    _inputView = [[SCEvaluationInputView alloc] init];
    __weak typeof(self) weakSelf = self;
    _inputView.sureHandler = ^(NSString *text){
        __strong typeof(self) self = weakSelf;
        [self publishEvaluation:text];
    };
    [self.view addSubview:_inputView];
    
    [_inputView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.height.mas_equalTo(kBottomInputViewHeight);
    }];
}


- (void)registerTableViewCell {
    [_tableView registerClass:[SCHospitalHomePageHeaderCell class] forCellReuseIdentifier:kHeaderCellReuseID];
    [_tableView registerClass:[SCHospitalHomePageLocalCell class] forCellReuseIdentifier:kLocalCellReuseID];
    [_tableView registerClass:[SCHospitalHomePageItemsCell class] forCellReuseIdentifier:kItemsCellReuseID];
    [_tableView registerClass:[SCHospitalHomePageEvaluationTitleCell class] forCellReuseIdentifier:kEvaluationTitleCellReuseID];
    [_tableView registerClass:[SCHospitalEvaluationCell class] forCellReuseIdentifier:kEvaluationCellReuseID];
}


- (void)setupCustomNavigationBar {
    _customNavBar = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 64)];
    [self.view addSubview:_customNavBar];
    
    //
    _navBarBgView = [[UIView alloc] initWithFrame:_customNavBar.bounds];
    _navBarBgView.backgroundColor = [UIColor whiteColor];
    _navBarBgView.alpha = 0.0;
    [_customNavBar addSubview:_navBarBgView];
    //

    _gradientLayer = [CAGradientLayer layer];
    [_customNavBar.layer addSublayer:_gradientLayer];
    _gradientLayer.frame = _customNavBar.bounds;

    
    _gradientLayer.startPoint = CGPointMake(0, 0);
    _gradientLayer.endPoint = CGPointMake(0, 1);
    
    _gradientLayer.colors = @[(__bridge id)[UIColor gard1Color].CGColor,
                             (__bridge id)[UIColor gard2Color].CGColor];
//    gradientLayer.locations = @[@(0.7)];
    
    
    
    _navBackBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [_navBackBtn setImage:[UIImage imageNamed:@"login_back"] forState:UIControlStateNormal];
    [_navBackBtn setImageEdgeInsets:UIEdgeInsetsMake(-10, -10, 10, 10)];
    _navBackBtn.frame = CGRectMake(0, 20, 64, 64);
    [_customNavBar addSubview:_navBackBtn];
    @weakify(self);
    [[_navBackBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
        @strongify(self);
        self.keepNavStatus = NO;
        [self.navigationController popViewControllerAnimated:YES];
    }];
    
    
    _navTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(64, 20, SCREEN_WIDTH - 64 * 2, 44)];
    _navTitleLabel.text = @"医院主页";
    _navTitleLabel.textColor = [UIColor whiteColor];
    _navTitleLabel.font = [UIFont systemFontOfSize:18];
    _navTitleLabel.textAlignment = NSTextAlignmentCenter;
    [_customNavBar addSubview:_navTitleLabel];
    
    
    _navCollectBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    _navCollectBtn.titleLabel.font = [UIFont systemFontOfSize:14];
    [_navCollectBtn setImage:[UIImage imageNamed:@"icon未关注"] forState:UIControlStateNormal];
    [_navCollectBtn setImage:[UIImage imageNamed:@"icon已关注"] forState:UIControlStateSelected];
    [_navCollectBtn setTitle:@"  未关注" forState:UIControlStateNormal];
    [_navCollectBtn setTitle:@"  已关注" forState:UIControlStateSelected];
    _navCollectBtn.adjustsImageWhenHighlighted = NO;
    [_customNavBar addSubview:_navCollectBtn];
    
    [_navCollectBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.offset(-15);
        make.centerY.equalTo(_navTitleLabel);
    }];
    
    
    [[_navCollectBtn rac_signalForControlEvents:UIControlEventTouchDown] subscribeNext:^(id x) {
        @strongify(self);
        [self doCollectAction:x];
    }];
}



#pragma mark - Lazy Loading

- (SCHospitalHomePageViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [[SCHospitalHomePageViewModel alloc] init];
    }
    
    return _viewModel;
}


- (NSMutableArray *)dataArray {
    if (!_dataArray) {
        _dataArray = [NSMutableArray array];
    }
    
    return _dataArray;
}

@end
