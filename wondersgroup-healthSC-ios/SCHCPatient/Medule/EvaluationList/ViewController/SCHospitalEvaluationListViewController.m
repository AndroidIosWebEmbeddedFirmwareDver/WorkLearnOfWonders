//
//  SCHospitalEvaluationListViewController.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/2.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalEvaluationListViewController.h"
#import "SCHospitalEvaluationListViewModel.h"
#import "SCHospitalEvaluationCell.h"
#import "SCEvaluationInputView.h"

static NSString *const kEvaluationListCellReuseID = @"kEvaluationListCellReuseID";
static CGFloat kBottomInputViewHeight = 44.0;

@interface SCHospitalEvaluationListViewController ()<UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) SCHospitalEvaluationListViewModel *viewModel;
@property (nonatomic, strong) SCEvaluationInputView *inputView;


@end

@implementation SCHospitalEvaluationListViewController


#pragma mark - Life Circle

- (void)viewDidLoad {
    [super viewDidLoad];

    if (self.evaluationCount) {
        self.navigationItem.title = [NSString stringWithFormat:@"医院评价(%@)", self.evaluationCount];
    }
    else {
        self.navigationItem.title = @"医院评价";
    }
    
    [self setupSubviews];
    [self bind];
    [_tableView.mj_header beginRefreshing];
}



#pragma mark - Bind

- (void)bind {
    RAC(_tableView.mj_footer, hidden) = [RACObserve(self.viewModel, hasMore) not];
    
    @weakify(self);
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(id x) {
        RequestCompeleteType complelteType = [x integerValue];
        FailViewType type = FailViewUnknow;
        
        switch (complelteType) {
            case RequestCompeleteNoWifi:
                type = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                type = FailViewError;
                break;
            case RequestCompeleteEmpty:
                type = FailViewEmpty;
                break;
            case RequestCompeleteSuccess:
                type = FailViewUnknow;
                break;
            default:
                break;
        }
        
        @strongify(self);
        if (self.viewModel.tipErrorString && type != FailViewUnknow) {
            _inputView.hidden = YES;
            [self.view showFailView:type withAction:^{
                [self requstFreshData];
            }];
        }
    }];
}



#pragma mark - Request

- (void)requstFreshData {
    __weak typeof(self) weakSelf = self;
    [self.viewModel requstEvaluationListWithHospitalID:self.hospitalID isMoreRequest:NO successBlock:^(NSArray *evaluationList) {
        __strong typeof(self) self = weakSelf;
        [_tableView.mj_header endRefreshing];
        [self.view hiddenFailView];
        _inputView.hidden = NO;
        
        if (self.evaluationCount) {
            self.navigationItem.title = [NSString stringWithFormat:@"医院评价(%@)", self.evaluationCount];
        }
        
        if (evaluationList && evaluationList.count != 0) {
            [_tableView reloadData];
        }
        else {
            [self.view showFailView:FailViewEmpty withAction:^{
                [self requstFreshData];
            }];
        }

    } failureBlock:^(NSError *error) {
        [_tableView.mj_header endRefreshing];
        
        __strong typeof(self) self = weakSelf;
        [self.view showFailView:FailViewError withAction:^{
            [self requstFreshData];
        }];
    }];
}


- (void)requstMoreData {
    [self.viewModel requstEvaluationListWithHospitalID:self.hospitalID isMoreRequest:YES successBlock:^(NSArray *evaluationList) {
        [_tableView.mj_footer endRefreshing];
        [_tableView reloadData];
        
    } failureBlock:^(NSError *error) {
        [_tableView.mj_footer endRefreshing];
    }];
}


- (void)postEvaluation:(NSString *)evaluation {
    if (!evaluation || [[evaluation stringByDeletingTerminalSpace] isEqualToString:@""]) {
        return;
    }
    [MBProgressHUDHelper showHudIndeterminate];
    [self.viewModel publishEvaluation:evaluation withHospitalID:self.hospitalID successHandle:^{
        
    } failureHandle:^(NSError *error) {
        
    }];
}



#pragma mark - Tableview Delegate && Datasource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.evaluationList.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCHospitalEvaluationCell *cell = [tableView dequeueReusableCellWithIdentifier:kEvaluationListCellReuseID forIndexPath:indexPath];
    [self configCell:cell withIndexPath:indexPath];
    return cell;
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    __weak typeof(self) weakSelf = self;
    return [tableView fd_heightForCellWithIdentifier:kEvaluationListCellReuseID configuration:^(id cell) {
        __strong typeof(self) self = weakSelf;
        [self configCell:cell withIndexPath:indexPath];
    }];
}


- (void)configCell:(SCHospitalEvaluationCell *)cell withIndexPath:(NSIndexPath *)indexPath {
    cell.model = self.viewModel.evaluationList[indexPath.row];
}



#pragma mark - Setup UI

- (void)setupSubviews {
    [self setupTableView];
    
    /// 更改版本: 1.0
    /// 修改人员: Joseph Gao
    /// 修改描述: UI变更,暂时别删
//    [self setupBottomInputView];
}


- (void)setupTableView {
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.separatorStyle = UITableViewCellSelectionStyleNone;
    _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requstFreshData)];
    _tableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(requstMoreData)];
    [self registerCell];
    [self.view addSubview:_tableView];
    
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.view);
//        make.bottom.equalTo(self.view).offset(-kBottomInputViewHeight);
        make.bottom.equalTo(self.view);
    }];
}


- (void)registerCell {
    [_tableView registerClass:[SCHospitalEvaluationCell class] forCellReuseIdentifier:kEvaluationListCellReuseID];
}


- (void)setupBottomInputView {
    _inputView = [[SCEvaluationInputView alloc] init];
    
    __weak typeof(self) weakSelf = self;
    _inputView.sureHandler = ^(NSString *text){
        __strong typeof(self) self = weakSelf;
        [self postEvaluation:text];
    };
    [self.view addSubview:_inputView];
    
    [_inputView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.height.mas_equalTo(kBottomInputViewHeight);
    }];
}



#pragma mark - Lazy Loading

- (SCHospitalEvaluationListViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [[SCHospitalEvaluationListViewModel alloc] init];
    }
    return _viewModel;
}

@end
