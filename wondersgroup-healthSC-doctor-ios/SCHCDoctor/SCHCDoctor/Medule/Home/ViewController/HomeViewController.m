//
//  HomeViewController.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "HomeViewController.h"
#import "HomeViewModel.h"
#import "WDBannerCell.h"
#import "HomeFunctionTableViewCell.h"
#import "ConsultationViewController.h"
#import "ReferralVC.h"
#import "PatientManagerViewController.h"

#define ROTATINGCELL @"WDBannerCell"
#define FUNCTIONCELL @"HomeFunctionTableViewCell"

@interface HomeViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) HomeViewModel *viewModel;

@end

@implementation HomeViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [HomeViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];

    [self setupView];
    [self bindViewModel];
    [self.viewModel getHomeRedTip];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    
}


#pragma mark    - setupView
-(void)setupView {
    self.navigationItem.title = @"我的工作台";
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    [self.myTableView setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(weakSelf.view);
        make.bottom.equalTo(weakSelf.view);
    }];
    
    [self.myTableView registerClass:[WDBannerCell class] forCellReuseIdentifier:ROTATINGCELL];
    [self.myTableView registerClass:[HomeFunctionTableViewCell class] forCellReuseIdentifier:FUNCTIONCELL];

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
                [weakSelf.viewModel getHomeRedTip];
            }];
        }
    }];
    
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    switch (indexPath.section){
        case 0:
        {
            WDBannerCell *cell = [tableView dequeueReusableCellWithIdentifier:ROTATINGCELL];
//            cell.bannerDataArray = self.viewModel.model.banners;
            // 轮播图点击
//            cell.bannerClickedHandler = ^(BannersModel * bannerData){
//                if (bannerData.hoplink == nil || [bannerData.hoplink isEqualToString:@""]) {
//                    return;
//                }
//                [[BFRouter router]open:bannerData.hoplink];
//            };
            return cell;
        }
            break;
        case 1:
        {
            HomeFunctionTableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:FUNCTIONCELL];

            cell.datas = self.viewModel.datas;
            cell.functionButtonBlock = ^(NSInteger type) {
                switch (type) {
                    case 300:
                    {
                        //图文咨询
                        ConsultationViewController *vc = [[ConsultationViewController alloc] init];
                        vc.hidesBottomBarWhenPushed = YES;
                        [self.navigationController pushViewController:vc animated:YES];
                    }
                        break;
                    case 301:
                    {
                        //患者管理
                        PatientManagerViewController *vc = [[PatientManagerViewController alloc] init];
                        vc.hidesBottomBarWhenPushed = YES;
                        [self.navigationController pushViewController:vc animated:YES];
                    }
                        break;
                    case 302:
                    {
                        ReferralVC * vc = [[ReferralVC alloc] init];
                        vc.hidesBottomBarWhenPushed = YES;
                        [self.navigationController pushViewController:vc animated:YES];
                        
                    }
                        break;
                        
                    default:
                        break;
                }
            };
            return cell;
        }
        
            break;
        default:
            return nil;
            break;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        return 186/375.0*SCREEN_WIDTH;
    }else if (indexPath.section == 1) {
        return 268;
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
