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
#import "HomeAreaSearchViewController.h"
#import "SCDepartmentViewController.h"
#import "HomeSearchViewController.h"
#import "LocationManager.h"
#import "LocationModel.h"
#import "SCMyCityDao.h"

static NSString *const HOSPITAL_LIST_CELL = @"HOSPITAL_LIST_CELL";

@interface WDHospitalListRootViewController () <UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UIView *searchView;
@property (nonatomic, strong) WDHospitalListRootViewModel *viewModel;
@property (nonatomic, strong) UILabel *searchViewLabel;
@property (nonatomic, strong) UIButton *searchViewButton;
@property (nonatomic, strong) UIImageView *searchIconImageView;
@property (nonatomic, strong) UILabel *searchTextLabel;
@property (nonatomic, strong) UIButton *locationBtn;
@property (nonatomic, strong) UILabel *locationNameLbl;
@property (nonatomic, strong) UIBarButtonItem *rightBarItem;

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

- (UIButton *)searchViewButton {
    if (!_searchViewButton) {
        _searchViewButton = [UIButton new];
        [_searchViewButton addTarget:self action:@selector(goSearch) forControlEvents:UIControlEventTouchUpInside];
    }
    return _searchViewButton;
}

- (UIView *)searchView {
    if (!_searchView) {
        _searchView = [UIView new];
        _searchView.backgroundColor = [UIColor bc1Color];
        
        _searchViewLabel = [UILabel new];
        _searchViewLabel.backgroundColor = [UIColor bc5Color];
        _searchViewLabel.layer.masksToBounds = YES;
        _searchViewLabel.layer.cornerRadius = 5;
        [_searchView addSubview:_searchViewLabel];
        [_searchViewLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(_searchView).offset(10);
            make.left.equalTo(_searchView).offset(15);
            make.right.equalTo(_searchView).offset(-15);
            make.bottom.equalTo(_searchView).offset(-10);
        }];
        
        _searchIconImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"ic_nav_gray_search"]];
        [_searchView addSubview:_searchIconImageView];
        [_searchIconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerY.equalTo(_searchViewLabel);
            make.centerX.equalTo(_searchViewLabel).offset(-30);
        }];
        
        _searchTextLabel = [UILabel new];
        _searchTextLabel.text = @"搜索医院";
        _searchTextLabel.textColor = [UIColor tc3Color];
        _searchTextLabel.font = [UIFont systemFontOfSize:14];
        [_searchView addSubview:_searchTextLabel];
        [_searchTextLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerY.equalTo(_searchViewLabel);
            make.centerX.equalTo(_searchViewLabel).offset(10);
        }];
        
        [_searchView addSubview:self.searchViewButton];
        [self.searchViewButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.edges.equalTo(_searchViewLabel);
        }];
        
        UIView *lineView = [UIView new];
        lineView.backgroundColor = [UIColor bc3Color];
        [_searchView addSubview:lineView];
        [lineView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.left.right.equalTo(_searchView);
            make.height.mas_equalTo(0.5);
        }];
    }
    return _searchView;
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
    
    SCMyCityModel *reCity = [[DBManager manager] getMyCityWithUid:[UserManager manager].uid];
    if (reCity) {
        self.locationNameLbl.text = reCity.cityName;
        self.viewModel.cityCode = reCity.cityCode;
        [self requestData];
    } else {
        [self getOtherCity];
    }
}

- (void)setupView {
    //self.navigationController.navigationBar.hidden = NO;
    self.title = @"预约挂号";
    
    _locationBtn = [UIButton buttonWithType:UIButtonTypeSystem];
    _locationBtn.frame = CGRectMake(0, 0, 66, 30);
    
    UIImageView *icon = [UISetupView setupImageViewWithSuperView:_locationBtn withImageName:@"icon定位"];
    [icon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_locationBtn.mas_centerY);
        make.left.equalTo(_locationBtn.mas_left).offset(1);
        make.size.mas_equalTo(CGSizeMake(13, 16));
    }];
    
    self.locationNameLbl = [UISetupView setupLabelWithSuperView:_locationBtn withText:@"成都" withTextColor:[UIColor tc5Color] withFontSize:14];
    [self.locationNameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(icon.mas_right).offset(3);
        make.centerY.equalTo(_locationBtn.mas_centerY);
        make.right.equalTo(_locationBtn.mas_right).offset(-3);
        make.height.mas_equalTo(20);
    }];
    
    self.rightBarItem = [[UIBarButtonItem alloc] initWithCustomView:_locationBtn];
    self.navigationItem.rightBarButtonItem = self.rightBarItem;
    [_locationBtn addTarget:self action:@selector(locationBtnClickDown) forControlEvents:UIControlEventTouchUpInside];
    
    WS(ws)
    
    [self.view addSubview:self.searchView];
    [self.searchView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(ws.view);
        make.height.mas_equalTo(50);
    }];

    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.searchView.mas_bottom);
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
    
    [RACObserve(_locationNameLbl, text) subscribeNext:^(NSString *text) {
        if (text) {
            ws.navigationItem.rightBarButtonItem = nil;
            if (text.length <= 4) {
                CGSize size = [text boundingRectWithSize:CGSizeMake(100, 30) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
                ws.locationBtn.frame = CGRectMake(0, 0, size.width+20, 30);
            } else {
                NSString *subStr = [text substringToIndex:4];
                subStr = [subStr stringByAppendingString:@"..."];
                ws.locationBtn.frame = CGRectMake(0, 0, 96, 30);
            }
            ws.rightBarItem.customView = ws.locationBtn;
            ws.navigationItem.rightBarButtonItem = ws.rightBarItem;
        }
    }];
}

- (void)getOtherCity {
    if ([LocationManager isEnableLocation]) {
        WS(weakSelf)
        __weak LocationManager * ma = [LocationManager manager];
        
        ma.gpsSuccess = ^(LocationModel *model) {
            weakSelf.locationNameLbl.text = model.areaName;
            weakSelf.viewModel.cityCode = model.areaCode;
            [weakSelf requestData];
        };
        ma.gpsfailed = ^(){
            weakSelf.locationNameLbl.text = @"成都";
            weakSelf.viewModel.cityCode = @"510100000000";
            [weakSelf requestData];
        };
        [ma restartLocation];
    } else {
        self.locationNameLbl.text = @"成都";
        self.viewModel.cityCode = @"510100000000";
        [self requestData];
        [self alertAllowLocation];
    }
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

- (void)locationBtnClickDown{
    HomeAreaSearchViewController *vc = [[HomeAreaSearchViewController alloc] init];
    vc.block = ^(NSString * cityName,NSString * cicyCode){
        self.locationNameLbl.text = cityName;
        self.viewModel.cityCode = cicyCode;
        [self requestData];
        SCMyCityModel *city = [SCMyCityModel new];
        city.cityName = cityName;
        city.cityCode = cicyCode;
        city.uid = [UserManager manager].uid;
        [[DBManager manager] updateMyCity:city];
    };
    [self.navigationController pushViewController:vc animated:YES];
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
         
- (void)goSearch {
    HomeSearchViewController *vc = [[HomeSearchViewController alloc] init];
    vc.type = HomeSearchType_Hospital;
    vc.viewType = SearchTypeContrller_Appointment;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)alertAllowLocation{
    WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
    [alert reloadTitle:@"提示" content:@"“无法定位”，请在iPhone“设置-隐私-定位服务”中允许微健康使用定位服务"];
    [alert.submitBtn setTitle:@"确定" forState:UIControlStateNormal];
    [alert.cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
    alert.submitBlock = ^(WDAlertView *view) {
        [view dismiss];
        if (IS_IOS10_OR_LATER) {
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:UIApplicationOpenSettingsURLString] options:@{} completionHandler:^(BOOL success) {
                
            }];
        } else {
            NSURL *url = [NSURL URLWithString:@"prefs:root=LOCATION_SERVICES"];
            if ([[UIApplication sharedApplication] canOpenURL:url]) {
                [[UIApplication sharedApplication] openURL:url];
            }
        }
    };
    
    alert.cancelBlock = ^(WDAlertView *view) {
        self.locationNameLbl.text = @"成都";
        self.viewModel.cityCode = @"510100000000";
        [self requestData];
        [view dismiss];
    };
    
    [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
}

@end
