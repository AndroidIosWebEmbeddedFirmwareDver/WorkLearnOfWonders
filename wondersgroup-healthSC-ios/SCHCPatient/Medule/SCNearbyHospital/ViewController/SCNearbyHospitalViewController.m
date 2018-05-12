//
//  WCNearbyHospitalViewController.m
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCNearbyHospitalViewController.h"
#import "NearbyHospitalMapView.h"
#import "SCNearbyHospitalCell.h"
#import "SCNearbyHospitalViewModel.h"
#import "SCNearbyHospitalSearchBarView.h"
#import "SCHospitalHomePageViewController.h"
#import "HomeAreaSearchViewController.h"
#import "LocationManager.h"
#import "LocationModel.h"
#import "HomeSearchViewController.h"
#import "HomeAreaSearchViewController.h"
#import "SCNearbyHospitalNoDataNetView.h"

@interface SCNearbyHospitalViewController () <UITableViewDelegate,UITableViewDataSource,UIAlertViewDelegate> {
    BOOL _isShowedDefaultLocation;  //默认定位就刚进来的时候显示一次，解决重复数据请求
}

@property (nonatomic, strong) NearbyHospitalMapView *mapView;
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) SCNearbyHospitalViewModel *viewModel;
@property (nonatomic, strong) SCNearbyHospitalSearchBarView *searchBarView;
@property (nonatomic, strong) UIButton *locationBtn;
@property (assign, nonatomic) double  longitude;
@property (assign, nonatomic) double latitude;
@property (nonatomic, strong) NSDictionary *cityInfo;
@property (nonatomic, strong) UILabel *locationNameLbl;
@property (nonatomic ,strong) LocationModel * currentmodel;

@property (nonatomic, strong) SCNearbyHospitalNoDataNetView *tipView;
@property (nonatomic, strong) UIBarButtonItem *rightBarItem;

@end

@implementation SCNearbyHospitalViewController
- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
    }
    return self;
}

- (void)dealloc{
    NSLog(@"dealloc");
}

- (void)viewWillDisappear:(BOOL)animated{
    [super viewWillDisappear:animated];
    [self.mapView viewWillDisappear];
    self.mapView.delegate = nil; // 不用时，置nil
//    self.mapView = nil;
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self.mapView viewWillAppear];
    [self.mapView setMapViewDelegate]; // 此处记得不用的时候需要置nil，否则影响内存的释放
    
    if ([LocationManager isEnableLocation]) {

    } else {
        if (_isShowedDefaultLocation) {
            
        } else {
            [self setToDefaultLocation];
        }
    }
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.title = @"附近医院";
    _isShowedDefaultLocation = NO;
    [self setupView];
    [self bindRAC];
    
    //默认先请求一次成都市的医院
    //在初始化地图的时候，默认设置到成都市了，rac里会调用通过坐标查询方法
    
    if ([LocationManager isEnableLocation]) {
        WS(weakSelf)
        __weak LocationManager * ma = [LocationManager manager];
        
        ma.gpsSuccess = ^(LocationModel *model) {
            weakSelf.currentmodel = model;
            weakSelf.locationNameLbl.text = model.areaName;

        };
        ma.gpsfailed = ^(){
            [self setToDefaultLocation];
        };
        [ma restartLocation];
    } else {
        
        [self alertAllowLocation];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)setToDefaultLocation{
    CLLocationCoordinate2D coor;
    coor.latitude = 30.679942845419564;
    coor.longitude = 104.06792346330406;
    
    [self.mapView setCenterCoordinate:coor animated:NO];
    _isShowedDefaultLocation = YES;
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
        [view dismiss];
    };
    
    [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
}

- (void)setupView{
    WS(weakSelf)
    
    _locationBtn = [UIButton buttonWithType:UIButtonTypeSystem];
    _locationBtn.frame = CGRectMake(0, 0, 66, 30);
    
    UIImageView *icon = [UISetupView setupImageViewWithSuperView:_locationBtn withImageName:@"icon定位"];
    [icon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.locationBtn.mas_centerY);
        make.left.equalTo(weakSelf.locationBtn.mas_left).offset(1);
        make.size.mas_equalTo(CGSizeMake(13, 16));
    }];
    
    self.locationNameLbl = [UISetupView setupLabelWithSuperView:_locationBtn withText:@"成都市" withTextColor:[UIColor tc5Color] withFontSize:14];
    [self.locationNameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(icon.mas_right).offset(3);
        make.centerY.equalTo(weakSelf.locationBtn.mas_centerY);
        make.right.equalTo(weakSelf.locationBtn.mas_right).offset(-3);
        make.height.mas_equalTo(20);
    }];
    self.rightBarItem = [[UIBarButtonItem alloc] initWithCustomView:_locationBtn];
    self.navigationItem.rightBarButtonItem = self.rightBarItem;
    [_locationBtn addTarget:self action:@selector(locationBtnClickDown) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:self.mapView];
    [_mapView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.view.mas_top);
        make.left.equalTo(weakSelf.view.mas_left);
        make.right.equalTo(weakSelf.view.mas_right);
        make.height.mas_equalTo(230);
    }];
    
    
    [self.view addSubview:self.searchBarView];
    [_searchBarView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.view.mas_top).offset(5.5);
        make.left.equalTo(weakSelf.view.mas_left).offset(16);
        make.right.equalTo(weakSelf.view.mas_right).offset(-16);
        make.height.mas_equalTo(32);
    }];
    
    self.searchBarView.searchBlock = ^(NSString *word) {
        HomeSearchViewController *vc = [[HomeSearchViewController alloc] init];
        vc.type = HomeSearchType_Hospital;
        vc.viewType = SearchTypeContrller_NearBy;
        [weakSelf.navigationController pushViewController:vc animated:YES];
    };

    _tableView = [[UITableView alloc] initWithFrame: CGRectZero];
    [_tableView setBackgroundColor: [UIColor bc2Color]];
    [_tableView setSeparatorColor: [UIColor clearColor]];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    [_tableView registerClass:[SCNearbyHospitalCell class] forCellReuseIdentifier:@"SCNearbyHospitalCell"];
    [_tableView setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: _tableView];
    
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.mapView.mas_bottom);
        make.left.equalTo(weakSelf.view.mas_left);
        make.right.equalTo(weakSelf.view.mas_right);
        make.bottom.equalTo(weakSelf.view.mas_bottom);
    }];
    
}

#pragma mark tip View
- (SCNearbyHospitalNoDataNetView *)tipView{
    if (_tipView == nil) {
        _tipView = [[SCNearbyHospitalNoDataNetView alloc] initWithFrame:CGRectMake(0, 230, SCREEN_WIDTH, self.view.height-230)];
        WS(weakSelf)
        [self.view addSubview:_tipView];
        [_tipView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.view.mas_left).offset(0);
            make.top.equalTo(weakSelf.mapView.mas_bottom).offset(0);
            make.right.equalTo(weakSelf.view.mas_right).offset(0);
            make.bottom.equalTo(weakSelf.view.mas_bottom).offset(0);
        }];
    }
    return _tipView;
}

#pragma mark 
- (void)locationBtnClickDown{
    WS(weakSelf)
    HomeAreaSearchViewController *vc = [[HomeAreaSearchViewController alloc] init];
    vc.block = ^(NSString * cityName,NSString * cityCode){
        weakSelf.cityInfo = @{@"cityCode":cityCode};
//        [weakSelf loadDataWithNewCity];
        [weakSelf didSelectCity:cityName];
    };
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)didSelectCity:(NSString *)city{
    WS(weakSelf);
    
    LocationManager * ma = [LocationManager manager];
    [ma getCoordinateWithCity:city];
    ma.cityCoordinateBlock = ^(CLLocationCoordinate2D coordinate){
        [weakSelf.mapView setCenterCoordinate:coordinate animated:YES];
//        [weakSelf.locationBtn setTitle:city forState:UIControlStateNormal];
        weakSelf.locationNameLbl.text = city;
    };
}

#pragma mark MapView
- (NearbyHospitalMapView *)mapView{
    if (_mapView == nil) {
        _mapView = [[NearbyHospitalMapView alloc] initWithFrame:CGRectZero];
    }
    return _mapView;
}

#pragma mark SearchBarView
- (SCNearbyHospitalSearchBarView *)searchBarView{
    if (_searchBarView == nil) {
        _searchBarView = [[SCNearbyHospitalSearchBarView alloc] init];
    }
    return _searchBarView;
}

#pragma mark loadData
//正常获取数据
- (void)loadData{
    [LoadingView showLoadingInView:self.view];
    NSDictionary *params = nil;
    if (_longitude == 0 && _latitude == 0) {
        
    } else if (_longitude > 0 && _latitude > 0) {
        params = @{@"longitude":@(_longitude),
                   @"latitude":@(_latitude)};
    } else if (_cityInfo != nil) {
        params = _cityInfo;
    }
    WS(weakSelf)
    [self.viewModel getNearbyHospitalListWithParams:params Success:^{
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf.tableView.mj_footer endRefreshing];
    } failure:^{
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf.tableView.mj_footer endRefreshing];
    }];
}

//根据新坐标获取数据
- (void)loadDataWithNewLocation{
    [LoadingView showLoadingInView:self.view];
    NSDictionary *params = nil;
    if (_longitude == 0 && _latitude == 0) {
        
    } else if (_longitude > 0 && _latitude > 0) {
        params = @{@"longitude":@(_longitude),
                   @"latitude":@(_latitude)};
    }
    WS(weakSelf)
    [self.viewModel getNearbyHospitalListWithNewLocationParams:params Success:^{
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf.tableView.mj_footer endRefreshing];
    } failure:^{
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf.tableView.mj_footer endRefreshing];
    }];
}

//根据城市获取数据
- (void)loadDataWithNewCity{
    [LoadingView showLoadingInView:self.view];
    
    if (_cityInfo == nil) {
        //成都code
        self.cityInfo = @{@"cityCode":@"510100000000"};
    }
    WS(weakSelf)
    [self.viewModel getNearbyHospitalListWithNewCityParams:weakSelf.cityInfo Success:^{
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf.tableView.mj_footer endRefreshing];
    } failure:^{
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf.tableView.mj_footer endRefreshing];
    }];
}

- (void)requestMore{
    [self loadData];
}

#pragma mark bindRAC
- (void)bindRAC{
    WS(weakSelf)
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(NSNumber *x) {
        
        BOOL more = x.boolValue;
        if (more) {
            weakSelf.tableView.mj_footer = [UIUtility footerMoreTarget:weakSelf action:@selector(requestMore)];
        }else {
            weakSelf.tableView.mj_footer = nil;
        }
    }];
    
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *x) {
        
        if (x) {
            
            switch ([x intValue]) {
                case RequestCompeleteEmpty:
                    //附近没有医院
                    weakSelf.mapView.dataArray = weakSelf.viewModel.dataArray;
                    [weakSelf.tableView reloadData];
                    weakSelf.tipView.type = NearbyNoData;
                    weakSelf.tableView.hidden = YES;
                    weakSelf.tipView.hidden = NO;
                    
                    break;
                case RequestCompeleteNoWifi:
                {
                    [weakSelf.view showFailView:FailViewNoWifi withAction:^{
                        
                    }];
                    weakSelf.navigationItem.rightBarButtonItem = nil;
//                    self.tipView.type = NearbyNoWIFI;
//                    self.tableView.hidden = YES;
//                    self.tipView.hidden = NO;
//                    WS(weakSelf)
//                    [LoadingView hideLoadinForView:self.view];
//                    self.tipView.block = ^(){
//                        //重新加载
//                        [weakSelf loadDataWithNewCity];
//                    };
                }
                    break;
                case RequestCompeleteError:
                    
                    break;
                case RequestCompeleteSuccess: {
                    weakSelf.mapView.dataArray = weakSelf.viewModel.dataArray;
                    [weakSelf.tableView reloadData];
                    weakSelf.tableView.hidden = NO;
                    weakSelf.tipView.hidden = YES;
                }
                    break;
                default:
                    break;
            }
            
        }
    }];
 
    
    [RACObserve(self, currentmodel) subscribeNext:^(LocationModel *model) {
        if (model) {
            weakSelf.mapView.currentModel = model;
            weakSelf.longitude = model.longitude.doubleValue;
            weakSelf.latitude  = model.latitude.doubleValue;
            
        }else {
            weakSelf.longitude = 0;
            weakSelf.latitude  = 0;
            
        }
        
    }];
    
    [RACObserve(_mapView, centerCoordinateDict) subscribeNext:^(NSDictionary *center) {
        if (center) {
            if ([center[@"lat"] doubleValue] == 39.91488417326375 && [center[@"lon"] doubleValue] == 116.4038832180495) {
                //默认北京位置排除，防止二次请求
            } else if ((fabs(weakSelf.latitude - [center[@"lat"] doubleValue]) < 0.000001) && (fabs(weakSelf.longitude - [center[@"lon"] doubleValue]) < 0.000001)) {
                //防止二次请求同一地点
            } else {
                weakSelf.longitude = [center[@"lon"] doubleValue];
                weakSelf.latitude  = [center[@"lat"] doubleValue];
                
                [weakSelf loadDataWithNewLocation];
            }
            
        }
        
    }];
    
    //监听，设置右上角地区按钮
    [RACObserve(_locationNameLbl, text) subscribeNext:^(NSString *text) {
        if (text) {
            weakSelf.navigationItem.rightBarButtonItem = nil;
            if (text.length <= 4) {
                CGSize size = [text boundingRectWithSize:CGSizeMake(100, 30) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
                weakSelf.locationBtn.frame = CGRectMake(0, 0, size.width+20, 30);
            } else {
                NSString *subStr = [text substringToIndex:4];
                subStr = [subStr stringByAppendingString:@"..."];
                weakSelf.locationBtn.frame = CGRectMake(0, 0, 96, 30);
            }
            weakSelf.rightBarItem.customView = weakSelf.locationBtn;
            weakSelf.navigationItem.rightBarButtonItem = weakSelf.rightBarItem;
        }
    }];
}

#pragma mark viewModel
- (SCNearbyHospitalViewModel *)viewModel{
    if (_viewModel == nil) {
        _viewModel = [[SCNearbyHospitalViewModel alloc] init];
    }
    return _viewModel;
}

#pragma mark tableView
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return _viewModel.dataArray.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 100;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    SCNearbyHospitalCell *cell = [tableView dequeueReusableCellWithIdentifier:@"SCNearbyHospitalCell" forIndexPath:indexPath];
    
    cell.model = _viewModel.dataArray[indexPath.section];
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
    SCHospitalHomePageViewController *vc = [[SCHospitalHomePageViewController alloc] init];
    SCHospitalModel *model = _viewModel.dataArray[indexPath.section];
    vc.hospitalID = model.hospitalId;
    [self.navigationController pushViewController:vc animated:YES];
}



/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
