//
//  ProfileViewController.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ProfileViewController.h"
#import "ProfileViewModel.h"
#import "ProfileHeadView.h"
#import "ProfileFunctionView.h"
#import "ProfileTableViewCell.h"
#import "FamilyDoctorTeamViewController.h"
#import "SCSettingViewController.h"
#import "WDHospitalListRootViewController.h"

#define HEADCELL        @"ProfileHeadView"
#define FUNCTIONCELL    @"ProfileFunctionView"
#define PROFILECELL     @"ProfileTableViewCell"

@interface ProfileViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) ProfileViewModel *viewModel;

@end

@implementation ProfileViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [ProfileViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self.viewModel reloadDatas];
    [self.viewModel getProfileData];
    
    [self setupView];
    [self bindViewModel];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBarHidden = YES;
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    self.navigationController.navigationBarHidden = NO;
}

#pragma mark    - setupView
-(void)setupView {
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    [self.myTableView setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(weakSelf.view);
        make.bottom.equalTo(weakSelf.view);
    }];
    
    [self.myTableView registerClass:[ProfileHeadView class] forCellReuseIdentifier:HEADCELL];
    [self.myTableView registerClass:[ProfileFunctionView class] forCellReuseIdentifier:FUNCTIONCELL];
    [self.myTableView registerClass:[ProfileTableViewCell class] forCellReuseIdentifier:PROFILECELL];
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    WS(weakSelf)
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        
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
                [weakSelf.view hiddenFailView];
                [weakSelf.myTableView reloadData];
                
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            
            [weakSelf.view showFailView:failType withAction:^{
                [self.viewModel getProfileData];
            }];
        }
    }];
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 2) {
        return 2;
    }
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    switch (indexPath.section){
        case 0:
        {
            ProfileHeadView *cell = [tableView dequeueReusableCellWithIdentifier:HEADCELL];
            
            cell.model = self.viewModel.headModel;
            
            return cell;
        }
            break;
        case 1:
        {
            ProfileFunctionView * cell = [tableView dequeueReusableCellWithIdentifier:FUNCTIONCELL];
            
            cell.model = self.viewModel.functionModel;
            cell.functionButtonBlock = ^(NSInteger type) {
                if (type == 200) {
                    
                    WDHospitalListRootViewController *vc = [WDHospitalListRootViewController new];
                    vc.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:vc animated:YES];
                }else if (type == 201) {
                    
                    FamilyDoctorTeamViewController *vc = [FamilyDoctorTeamViewController new];
                    vc.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:vc animated:YES];
                }
            };
            
            return cell;
        }
        case 2:
        {
            ProfileTableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:PROFILECELL];
            
            ProfileCellModel *model = self.viewModel.datas[indexPath.row];
            cell.imageName = model.image;
            cell.title = model.content;
            cell.isLast = (indexPath.row == 1)?YES:NO;
            
            return cell;
        }

            break;
        default:
            return nil;
            break;
    }
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 2) {
        if (indexPath.row == 0) {
            //帮助与反馈跳转
            //appConfig.CommonModel.helpCenter
            NSString *urlStr = [NSString stringWithFormat:@"%@?userId=%@&tel=%@", [TaskManager manager].appConfig.common.helpCenter, [UserManager manager].uid, [UserManager manager].mobile];
            [[BFRouter router] open:urlStr];

        }else if (indexPath.row == 1) {
            SCSettingViewController *settingVC = [[SCSettingViewController alloc] init];
            settingVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:settingVC animated:YES];
        }
    }
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        return 205;
    }else if (indexPath.section == 1) {
        return 80;
    }else if (indexPath.section == 2) {
        return 44;
    }
    return 0;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 10;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 0.5;
}


@end
