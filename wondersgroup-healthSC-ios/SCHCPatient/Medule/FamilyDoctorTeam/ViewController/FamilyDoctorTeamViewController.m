//
//  FamilyDoctorTeamViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "FamilyDoctorTeamViewController.h"
#import "FamilyDoctorTeamViewModel.h"
#import "FamilyDoctorTeamCell.h"
#import "FamilyDoctorWebViewController.h"
#import "LocationManager.h"
#import "LocationModel.h"

@interface FamilyDoctorTeamViewController ()<UITableViewDelegate,UITableViewDataSource>
{
    BOOL _isShowedDefaultLocation;  //默认定位就刚进来的时候显示一次，解决重复数据请求
}
@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) FamilyDoctorTeamViewModel *viewModel;

@end

@implementation FamilyDoctorTeamViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        
        self.viewModel = [FamilyDoctorTeamViewModel new];
        
    }
    return self;
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if ([LocationManager isEnableLocation]) {

    } else {
        if (_isShowedDefaultLocation) {
            
            
        } else {
            [self.view showFailViewWith:[UIImage imageNamed:@"无数据"] withTitle:@"无法获取团队列表\n请打开定位后再次尝试" withAction:^{
                
            }];
        }
    }
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    _isShowedDefaultLocation = NO;
    [self setupView];
    [self bindViewModel];

    if ([LocationManager isEnableLocation]) {
        WS(weakSelf)
        __weak LocationManager * ma = [LocationManager manager];
        
        ma.gpsSuccess = ^(LocationModel *model) {
            NSLog(@"%@,%@",model.latitude,model.longitude);
            weakSelf.viewModel.latitude = model.latitude;
            weakSelf.viewModel.longitude = model.longitude;
            [weakSelf requestData];
        };
        ma.gpsfailed = ^(){
            [self.view showFailViewWith:[UIImage imageNamed:@"无数据"] withTitle:@"无法获取团队列表\n请打开定位后再次尝试" withAction:^{
                
            }];
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

- (void)dealloc {
    
}


#pragma mark    - setupView
-(void)setupView {
    self.navigationItem.title = @"家庭医生团队";
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    
    UITableView *tableViewL = [[UITableView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height)
                                                           style: UITableViewStyleGrouped];
    tableViewL.delegate      = self;
    tableViewL.dataSource    = self;
    [tableViewL setBackgroundColor: [UIColor clearColor]];
    [tableViewL setSeparatorColor: [UIColor clearColor]];
    [tableViewL setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: tableViewL];
    self.myTableView = tableViewL;
    self.myTableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestData)];

    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(weakSelf.view);
        make.bottom.equalTo(weakSelf.view);
    }];
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

#pragma mark    - bindViewModel
-(void)bindViewModel {
        WS(weakSelf)
        [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
            if ([type intValue] == 0) {
                return ;
            }
            [LoadingView hideLoadinForView:self.view];
            [weakSelf endRefreshing];
    
            FailViewType failType = FailViewUnknow;
            switch ([type intValue]) {
                case RequestCompeleteEmpty:{
                    [weakSelf.view showFailViewWith:[UIImage imageNamed:@"无数据"] withTitle:@"附近暂时没有可以为您服务的家庭医生团队" withAction:^{
                        [weakSelf requestData];
                    }];

                }
                    break;
                case RequestCompeleteNoWifi:
                    failType = FailViewNoWifi;
                    break;
                case RequestCompeleteError:
                    failType = FailViewError;
                    break;
                case RequestCompeleteSuccess: {
                    [weakSelf.view hiddenFailView];
    
                    [weakSelf.myTableView reloadData];
                    failType = FailViewUnknow;
                }
                    break;
                default:
                    break;
            }
            if (failType != FailViewUnknow && failType != FailViewEmpty) {
    
                [weakSelf.view showFailView:failType withAction:^{
                    [weakSelf requestData];
                }];
            }
        }];
    
    
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(NSNumber *x) {
        BOOL more = x.boolValue;
        if (more) {
            weakSelf.myTableView.mj_footer =[UIUtility footerMoreTarget:self action:@selector(requestMoreData)];
        }else {
            weakSelf.myTableView.mj_footer = nil;
        }
    }];
    
}

#pragma mark    - method
- (void)requestData {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getFamilyDoctorTeamComplete:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
        [self.myTableView reloadData];
    } failure:^(NSError *error) {
        [self endRefreshing];
        [LoadingView hideLoadinForView:self.view];
    }];
}

- (void)requestMoreData {
    [self.viewModel getMoreFamilyDoctorTeamComplete:^{
        [self endRefreshing];
        [self.myTableView reloadData];
    } failure:^(NSError *error) {
        [self endRefreshing];
    }];
}

- (void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}


#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.teamArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *identifier = @"FamilyDoctorTeamCell";
    
    FamilyDoctorTeamCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if(!cell)
    {
        cell = [[FamilyDoctorTeamCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.lineTopHidden = YES;
        cell.lineBottomHidden = YES;
    }
    
    cell.model = self.viewModel.teamArray[indexPath.row];
    cell.isLast = (self.viewModel.teamArray.count-1 == indexPath.row);

    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {

    if (self.viewModel.teamArray.count < indexPath.row) {
        return;
    }
    
    WDBaseWebViewController * vc = [[WDBaseWebViewController alloc]initWithURL:@"http://10.1.64.194/healthSC-app-h5/familyDoctor/teamDetail"];
    vc.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:vc animated:YES];
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [FamilyDoctorTeamCell cellHeight];
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 10;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 0.5;
}

@end
