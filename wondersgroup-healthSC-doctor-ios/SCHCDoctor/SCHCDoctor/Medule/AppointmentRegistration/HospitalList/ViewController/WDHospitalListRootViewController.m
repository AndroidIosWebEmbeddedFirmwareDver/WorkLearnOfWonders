//
//  WDHospitalListRootViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDHospitalListRootViewController.h"
#import "WDHospitalListRootViewModel.h"
#import "SCNearbyHospitalCell.h"
#import "SCDepartmentViewController.h"

static NSString *const HOSPITAL_LIST_CELL = @"HOSPITAL_LIST_CELL";

@interface WDHospitalListRootViewController () <UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) WDHospitalListRootViewModel *viewModel;

@end

@implementation WDHospitalListRootViewController
- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
    }
    return self;
}

- (WDHospitalListRootViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [WDHospitalListRootViewModel new];
    }
    return _viewModel;
}

- (UITableView *)tableView {
    if (!_tableView) {
        _tableView = [UITableView new];
        _tableView.backgroundColor = [UIColor clearColor];
        _tableView.dataSource = self;
        _tableView.delegate = self;
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestData)];
        [_tableView registerClass:[SCNearbyHospitalCell class] forCellReuseIdentifier:HOSPITAL_LIST_CELL];
    }
    return _tableView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindModel];
#warning citycode
    self.viewModel.cityCode = @"510500000000";
    [self requestData];
}

- (void)setupView {
    //self.navigationController.navigationBar.hidden = NO;
    self.title = @"预约挂号";
    
    WS(ws)
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view);
        make.left.right.bottom.equalTo(ws.view);
    }];
    
}

- (void)bindModel {
    WS(ws)
    
//    [RACObserve(self.viewModel, cityCode) subscribeNext:^(NSString *x) {
//        if (x) {
//            [self requestData];
//        }
//    }];
    
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(id x) {
        if (ws.viewModel.hasMore) {
            MJRefreshAutoNormalFooter * footer =[UIUtility footerMoreTarget:self action:@selector(requestMoreData)];
            ws.tableView.mj_footer =footer;
        } else {
            ws.tableView.mj_footer = nil;
        }
    }];
    
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:
                failType = FailViewEmpty;
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [self.view hiddenFailView];
                //[self.tableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [self.view showFailView:failType withAction:^{
                [self requestData];
            }];
        }
    }];
}

- (void)requestData {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel requestHospitalList:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
        [self.tableView reloadData];
    } failure:^{
        [self endRefreshing];
        [LoadingView hideLoadinForView:self.view];
    }];
}

- (void)requestMoreData {
    [self.viewModel requestMoreHospitalList:^{
        [self endRefreshing];
        [self.tableView reloadData];
    } failure:^{
        [self endRefreshing];
    }];
}

- (void)endRefreshing {
    [self.tableView.mj_header endRefreshing];
    [self.tableView.mj_footer endRefreshing];
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.hospitalArr.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 100;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCNearbyHospitalCell *cell = [tableView dequeueReusableCellWithIdentifier:HOSPITAL_LIST_CELL forIndexPath:indexPath];
    cell.model = self.viewModel.hospitalArr[indexPath.row];
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (self.viewModel.hospitalArr.count <= indexPath.row) {
        return;
    }
    SCHospitalModel *model = self.viewModel.hospitalArr[indexPath.row];

    SCDepartmentViewController *vc = [SCDepartmentViewController new];
    vc.viewModel.hospitalID = model.hospitalId;
    vc.viewModel.hospitalCode = model.hospitalCode;
    [self.navigationController pushViewController:vc animated:YES];
    
}
         

@end
