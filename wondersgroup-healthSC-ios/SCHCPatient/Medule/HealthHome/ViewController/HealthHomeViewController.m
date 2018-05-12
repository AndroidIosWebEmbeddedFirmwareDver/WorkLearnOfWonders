//
//  HealthHomeViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthHomeViewController.h"
#import "HealthHomeViewModel.h"
#import "HealthHomeFunctionCell.h"
#import "HealthHomeInformationHeaderView.h"
#import "HealthHomeInformationCell.h"
#import "HealthHomeInformationNullCell.h"
#import "RotatingImageViewCell.h"
#import "WDAlertView.h"
#import "SCHealthIdentifyViewController.h"
#import "HealthFilesViewController.h"

#import "WDBaseWebViewController.h"
#import "WDBannerCell.h"

#warning LHN_TEST
#import "MyAttentionViewController.h"
#import "DoctorDetailViewController.h"
#import "FeedBackViewController.h"

#import "UserService.h"
#import "SignUpFamilyDoctorViewController.h"

@interface HealthHomeViewController () <UITableViewDelegate, UITableViewDataSource, UIScrollViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) HealthHomeViewModel *viewModel;

@property (nonatomic, strong) UIView *customNav;
@property (nonatomic, strong) UIView *customNavBackView;
@property (nonatomic, strong) UILabel *customTitleLabel;

@property (nonatomic, assign) NSUInteger informationSelectedIndex;

@end

@implementation HealthHomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor cyanColor];
    
    [self prepareData];
    [self prepareUI];
}

- (void)prepareData {
    
    self.informationSelectedIndex = 0;
    self.viewModel = [[HealthHomeViewModel alloc] init];
}

- (void)prepareUI {
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT-49.) style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.tableView];
    self.tableView.tableFooterView = [UIView new];
    
    self.tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestAction)];
    
    //自定义导航条
    self.customNav = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 64.)];
    self.customNav.backgroundColor = [UIColor clearColor];
    [self.view addSubview:self.customNav];
    
    self.customNavBackView = [UIView new];
    self.customNavBackView.backgroundColor = [UIColor bc1Color];
    self.customNavBackView.alpha = 0.;
    [self.customNav addSubview:self.customNavBackView];
    
    self.customTitleLabel = [UILabel new];
    self.customTitleLabel.textAlignment = NSTextAlignmentCenter;
    self.customTitleLabel.font = [UIFont systemFontOfSize:18.];
    self.customTitleLabel.textColor = [UIColor tc0Color];
    self.customTitleLabel.text = @"健康";
    [self.customNav addSubview:self.customTitleLabel];
    
    UIView *navBottomLineView = [UIView new];
    navBottomLineView.backgroundColor = [UIColor dc2Color];
    [self.customNavBackView addSubview:navBottomLineView];
    
    WS(weakSelf)
    
    [self.customNavBackView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(weakSelf.customNav);
    }];
    
    [self.customTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.customNav);
        make.centerY.equalTo(weakSelf.customNav.mas_centerY).offset(10);
    }];
    
    [navBottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.left.right.equalTo(weakSelf.customNavBackView);
        make.height.mas_equalTo(@0.5);
    }];
}

- (void)requestAction {
    
    if (![Global global].networkReachable) {
        [MBProgressHUDHelper showHudWithText:@"无网络, 请稍后再试!"];
        [self.viewModel getDBData];
        [self.tableView reloadData];
        [self endRefresh];
    }
    else {
        [self.viewModel getHealthHomeFunction:^{
            
            [self.tableView reloadData];
            [self endRefresh];
            
        } failure:^{
            [self.tableView reloadData];
            [self endRefresh];
        }];
        
        [self.viewModel getHealthHomeInformationList:^{
            
            [self.tableView reloadData];
            [self endRefresh];
            
        } failure:^{
            [self.tableView reloadData];
            [self endRefresh];
        }];
    }
}

- (void)endRefresh {
    
    [self.tableView.mj_header endRefreshing];
}

- (void)viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = YES;
    [self requestAction];
    if ([UserManager manager].isLogin ) {
        [[UserService service]updateMessageCount:@{@"uid": [UserManager manager].uid} complete:^{
            
        } failure:^{
            
        }];
    }

}

- (void)viewWillDisappear:(BOOL)animated {
    
    [super viewWillDisappear:animated];
    
    self.navigationController.navigationBar.hidden = NO;
}

#pragma mark - scrollView delegate

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    
    CGFloat offsetY = scrollView.contentOffset.y;
    
    self.customNavBackView.alpha = offsetY/64.;
    if (offsetY > 0) {
        self.customTitleLabel.textColor = [UIColor tc1Color];
    }
    else {
        self.customTitleLabel.textColor = [UIColor tc0Color];
    }
    
    if (offsetY > AdaptiveFrameHeight(375/2.0)+150.) {
        self.tableView.contentInset = UIEdgeInsetsMake(64., 0, 0, 0);
    }
    else {
        self.tableView.contentInset = UIEdgeInsetsMake(0, 0, 0, 0);
    }
}

#pragma mark - tableView delegate && dataSource

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return self.viewModel.layoutArray.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    HealthHomeLayoutModel *layoutModel = self.viewModel.layoutArray[section];
    if (layoutModel.type == HealthHomeLayoutTypeInformation) {
        
        HHInformationListModel *listModel = self.viewModel.dataArray[self.informationSelectedIndex];
        if (listModel.list.count == 0) {
            return 1;
        }
        else {
            return listModel.list.count;
        }
    }
    else {
        return 1;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    
    HealthHomeLayoutModel *layoutModel = self.viewModel.layoutArray[section];
    if (layoutModel.type == HealthHomeLayoutTypeInformation) {
        return 40.;
    }
    else {
        return 0.;
    }
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    
    HealthHomeLayoutModel *layoutModel = self.viewModel.layoutArray[section];
    if (layoutModel.type == HealthHomeLayoutTypeInformation) {
        
        return [self tableView:tableView inforationHeaderInSection:section];
    }
    else {
        
        return nil;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    HealthHomeLayoutModel *layoutModel = self.viewModel.layoutArray[indexPath.section];
    if (layoutModel.type == HealthHomeLayoutTypeInformation) {
        HHInformationListModel *listModel = self.viewModel.dataArray[self.informationSelectedIndex];
        if (listModel.list.count == 0) {
            return 350.;
        }
    }
    return layoutModel.cellHeight;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    HealthHomeLayoutModel *layoutModel = self.viewModel.layoutArray[indexPath.section];
    switch (layoutModel.type) {
        case HealthHomeLayoutTypeRotatingImage: {
            return [self tableView:tableView rotatingCellForRowAtIndexPath:indexPath];
        }
            break;
        case HealthHomeLayoutTypeFunction: {
            
            return [self tableView:tableView functionCellForRowAtIndexPath:indexPath];
        }
            break;
            
        case HealthHomeLayoutTypeInformation: {
            
            HHInformationListModel *listModel = self.viewModel.dataArray[self.informationSelectedIndex];
            if (listModel.list.count == 0) {
                return [self tableView:tableView nullCellForRowAtIndexPath:indexPath];
            }
            else {
                return [self tableView:tableView informationCellForRowAtIndexPath:indexPath];
            }
        }
            break;
            
        default: {
            static NSString *identifier = @"HealthHomeNullCellIdentifier";
            UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
            if (!cell) {
                cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            }
            return cell;
        }
            break;
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    HealthHomeLayoutModel *layoutModel = self.viewModel.layoutArray[indexPath.section];
    if (layoutModel.type == HealthHomeLayoutTypeInformation) {
        HHInformationListModel *listModel = self.viewModel.dataArray[self.informationSelectedIndex];
        if (listModel.list.count == 0) {
            
        }
        else {
            HHInformationListModel *listModel = self.viewModel.dataArray[self.informationSelectedIndex];
            HHInformationModel *cellModel = listModel.list[indexPath.row];
            
            [[BFRouter router] open:cellModel.url];
        }
    }
}

#pragma mark - informationHeaderView

- (UIView *)tableView:(UITableView *)tableView inforationHeaderInSection:(NSInteger)section {
    
    static NSString *identifier = @"informationHeaderIdentifier";
    HealthHomeInformationHeaderView *header = [tableView dequeueReusableHeaderFooterViewWithIdentifier:identifier];
    if (!header) {
        header = [[HealthHomeInformationHeaderView alloc] initWithReuseIdentifier:identifier];
    }
    
    header.selectedIndex = self.informationSelectedIndex;
    [header setDataWithItems:self.viewModel.dataArray];
    
    header.segmentControlSelectedBlock = ^(NSUInteger index) {
        self.informationSelectedIndex = index;
        [self.tableView reloadData];
        [self scrollViewDidScroll:tableView];
    };
    
    return header;
}

- (void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    
    view.tintColor = [UIColor whiteColor];
}

#pragma mark - custom cell

- (UITableViewCell *)tableView:(UITableView *)tableView rotatingCellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"HHRotatingImageCellIdentifier";
    WDBannerCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[WDBannerCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    cell.bannerDataArray = self.viewModel.bannerArray;
    // 轮播图点击
    cell.bannerClickedHandler = ^(BannersModel * bannerData){
        if (bannerData.hoplink == nil || [bannerData.hoplink isEqualToString:@""]) {
            return;
        }
        [[BFRouter router]open:bannerData.hoplink];
    };
    
    return cell;
}

- (UITableViewCell *)tableView:(UITableView *)tableView functionCellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"HealthHomeFunctionCellIdentifier";
    HealthHomeFunctionCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[HealthHomeFunctionCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier noSubTitle:YES];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    if (self.viewModel.functionArray.count > 2) {
        
        [cell setCellWithArray:self.viewModel.functionArray];
        cell.rightBottomFuncitonInvalid = YES;
        
        cell.leftButtonBlock = ^{
            //跳转健康档案
            if ([UserManager manager].isLogin) {
                FunctionModel *leftModel = self.viewModel.functionArray[0];
                [PerfectInformationView showPerfectInformationAlertIsSuccess:^(BOOL success) {
                    if (!success) {
                        if (leftModel.hoplink && ![leftModel.hoplink isEqualToString:@""]) {
                            [[BFRouter router] open:[leftModel.hoplink stringByAppendingString:[NSString stringWithFormat:@"?uid=%@&page=0&forbidden=true", [UserManager manager].uid]]];
                        }
                    }
                    
                }];
                
            }
            else {
                [[VCManager manager] presentLoginViewController:YES];
            }
        };
        
        cell.rightTopButtonBlock = ^{
            
            FunctionModel *rightTopModel = self.viewModel.functionArray[1];
            [[BFRouter router] open:rightTopModel.hoplink];
//            [[BFRouter router] open:@"http://10.10.13.66:3003/healthSC-app-h5-te/measurement/question?registerid=123213123"];
        };
        
        cell.rightBottomButtonBlock = ^{
          
            SignUpFamilyDoctorViewController *vc = [SignUpFamilyDoctorViewController new];
            vc.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:vc animated:YES];
            
        };
        
    }
    return cell;
}

- (UITableViewCell *)tableView:(UITableView *)tableView informationCellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"HealthHomeNullCellIdentifier";
    HealthHomeInformationCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[HealthHomeInformationCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    HHInformationListModel *listModel = self.viewModel.dataArray[self.informationSelectedIndex];
    HHInformationModel *cellModel = listModel.list[indexPath.row];
    
    cell.cellModel = cellModel;
    
    return cell;
}

- (UITableViewCell *)tableView:(UITableView *)tableView nullCellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"nullCellIdentifier";
    HealthHomeInformationNullCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[HealthHomeInformationNullCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    return cell;
}


@end
