//
//  DoctorDetailViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailViewController.h"
#import "DoctorDetailViewModel.h"
#import "SCDoctorSchedulingInfoCell.h"
#import "DoctorDetailTopCell.h"
#import "DoctorDetailJudgeHeader.h"
#import "DoctorDetailJudgeCell.h"
#import "DoctorDetailJudgeModel.h"
#import "DoctorDetailContentViewController.h"
#import "DoctorDetailMoreJudgeViewController.h"
#import "SCDoctorSchedulingViewController.h"

#import "SCDoctorSchedulingViewModel.h"

@interface DoctorDetailViewController () <UITableViewDelegate, UITableViewDataSource>

//顶部导航
@property (nonatomic, strong) UIView *customNavigation;
@property (nonatomic, strong) UIView *navBackView;
@property (nonatomic, strong) UIButton *popButton;
@property (nonatomic, strong) UIButton *attentionButton;
@property (nonatomic, strong) UIImageView *attentionImageView;
@property (nonatomic, strong) UILabel *attentionLabel;

@property (nonatomic, assign) BOOL is_attention;

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) DoctorDetailViewModel *viewModel;

@property (nonatomic, strong) UIButton *registerButton;


//这是一个非常操蛋的判断用的viewModel, 跳转预约挂号请求数据
//  如果没有排班信息, 就提示一闪而过, 不允许跳转
//  有排班信息的话就把数据传到下一个页面
@property (nonatomic, strong) SCDoctorSchedulingViewModel *schedulingViewModel;

@end

@implementation DoctorDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self prepareData];
    [self prepareUI];
    [self bindRac];
    
 
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self.navigationController setNavigationBarHidden:YES animated:NO];
       [self requestAction];
}
- (void)prepareData {
    
    self.viewModel = [[DoctorDetailViewModel alloc] init];
    self.schedulingViewModel = [[SCDoctorSchedulingViewModel alloc] init];
}

- (void)prepareUI {

    CGFloat tableHeight = self.showRegister ? SCREEN_HEIGHT-64. : SCREEN_HEIGHT;

    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, tableHeight) style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.tableFooterView = [UIView new];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.tableView];
    
    if (self.showRegister) {
        UIView *bottomBackView = [UIView new];
        bottomBackView.backgroundColor = [UIColor bc1Color];
        [self.view addSubview:bottomBackView];
        
        UIView *topLineView = [UIView new];
        topLineView.backgroundColor = [UIColor dc2Color];
        [bottomBackView addSubview:topLineView];
        
        self.registerButton = [UIButton new];
        [self.registerButton setTitle:@"预约挂号" forState:UIControlStateNormal];
        [self.registerButton setTitleColor:[UIColor bc1Color] forState:UIControlStateNormal];
        self.registerButton.backgroundColor = [UIColor tc5Color];
        self.registerButton.layer.masksToBounds = YES;
        self.registerButton.layer.cornerRadius = 4.;
        [self.registerButton addTarget:self action:@selector(registerButtonAction:) forControlEvents:UIControlEventTouchUpInside];
        [bottomBackView addSubview:self.registerButton];
        
        WS(weakSelf)
        [bottomBackView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.bottom.equalTo(weakSelf.view);
            make.height.mas_equalTo(@64);
        }];
        [topLineView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.top.equalTo(bottomBackView);
            make.height.mas_equalTo(@0.5);
        }];
        [self.registerButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(bottomBackView).offset(15);
            make.right.equalTo(bottomBackView).offset(-15);
            make.centerY.equalTo(bottomBackView);
            make.height.mas_equalTo(@44);
        }];
    }
    
    [self createCustomNav];
}

- (void)createCustomNav {

    self.customNavigation = [UIView new];
    self.customNavigation.backgroundColor = [UIColor clearColor];
    [self.view addSubview:self.customNavigation];
    
    self.navBackView = [UIView new];
    self.navBackView.backgroundColor = [UIColor whiteColor];
    self.navBackView.alpha = 0;
    [self.customNavigation addSubview:self.navBackView];
    
    UILabel *titleLabel = [UILabel new];
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.text = @"医生详情";
    titleLabel.font = [UIFont systemFontOfSize:18.];
    titleLabel.textColor = [UIColor tc1Color];
    [self.navBackView addSubview:titleLabel];
    
    self.popButton = [UIButton new];
    [self.popButton setImage:[UIImage imageNamed:@"back_white"] forState:UIControlStateNormal];
    [self.popButton addTarget:self action:@selector(popButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.customNavigation addSubview:self.popButton];

    
    self.attentionButton = [UIButton new];
    [self.attentionButton addTarget:self action:@selector(attentionButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.customNavigation addSubview:self.attentionButton];
    
    self.attentionImageView = [UIImageView new];
    self.attentionImageView.image = [UIImage imageNamed:@"icon未关注"];
    [self.attentionButton addSubview:self.attentionImageView];
    
    self.attentionLabel = [UILabel new];
    self.attentionLabel.font = [UIFont systemFontOfSize:14.];
    self.attentionLabel.text = @"未关注";
    self.attentionLabel.textColor = [UIColor tc0Color];
    [self.attentionButton addSubview:self.attentionLabel];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor dc2Color];
    [self.navBackView addSubview:bottomLineView];
    
    WS(weakSelf)
    
    [self.customNavigation mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(@64);
    }];
    
    [self.navBackView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.bottom.equalTo(weakSelf.customNavigation);
    }];
    
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.popButton);
        make.centerX.equalTo(weakSelf.navBackView).offset(10);
    }];
    
    [self.popButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.customNavigation);//.offset(15);
        make.top.equalTo(weakSelf.customNavigation).offset(20);
        make.bottom.equalTo(weakSelf.customNavigation);
        make.width.equalTo(weakSelf.popButton.mas_height);
    }];
    
    [self.attentionButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.customNavigation).offset(-15);
        make.centerY.equalTo(weakSelf.popButton);
        make.width.mas_equalTo(@70);
        make.height.mas_equalTo(@40);
    }];
    
    [self.attentionImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.attentionLabel.mas_left).offset(-5);
        make.centerY.equalTo(weakSelf.attentionButton);
        make.width.mas_equalTo(@18);
        make.height.mas_equalTo(@17);
    }];
    
    [self.attentionLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.attentionButton);
        make.centerY.equalTo(weakSelf.attentionButton);
        make.height.mas_equalTo(@16);
    }];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(weakSelf.navBackView);
        make.height.mas_equalTo(@0.5);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self.viewModel, resultModel.concern) subscribeNext:^(id x) {
        
        BOOL attention = [x boolValue];
        weakSelf.is_attention = attention;
        weakSelf.attentionLabel.text = attention ? @"已关注" : @"未关注";
        weakSelf.attentionImageView.image = attention ? [UIImage imageNamed:@"icon已关注"] : [UIImage imageNamed:@"icon未关注"];
    }];
    
    [RACObserve(self, is_attention) subscribeNext:^(id x) {
        BOOL attention = [x boolValue];
        if (attention) {
            weakSelf.attentionImageView.image = [UIImage imageNamed:@"icon已关注"];
            weakSelf.attentionLabel.text = @"已关注";
        }
        else {
            weakSelf.attentionImageView.image = [UIImage imageNamed:@"icon未关注"];
            weakSelf.attentionLabel.text = @"未关注";
        }
    }];
}

- (void)requestAction {
    
    if (self.hospitalCode) {
        self.viewModel.hospitalCode = self.hospitalCode;
    }
    else {
        ALERT(@"温馨提醒", @"医院代码获取错误, 请重新尝试");
        return;
    }
    
    if (self.hosDeptCode) {
        self.viewModel.hosDeptCode = self.hosDeptCode;
    }
    else {
        ALERT(@"温馨提醒", @"科室代码获取错误, 请重新尝试");
        return;
    }
    
    if (self.hosDoctCode) {
        self.viewModel.hosDoctCode = self.hosDoctCode;
    }
    else {
        ALERT(@"温馨提醒", @"医生代码获取错误, 请重新尝试");
        return;
    }
    
    WS(weakSelf)
    
    [LoadingView showLoadingInView:self.view];
    
    [self.viewModel getDoctorDetail:^{
        weakSelf.is_attention = [weakSelf.viewModel.resultModel.concern boolValue];
        [LoadingView hideLoadinForView:weakSelf.view];
        [weakSelf.tableView reloadData];
        
    } failure:^{
        
        [LoadingView hideLoadinForView:weakSelf.view];
    }];
}



- (void)viewWillDisappear:(BOOL)animated {
    
    [super viewWillDisappear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:NO];
}


#pragma mark - 按钮点击事件

//自定义返回按钮
- (void)popButtonAction:(UIButton *)button {
    
    [self popBack];
}

//关注按钮
- (void)attentionButtonAction:(UIButton *)button {
    
    if ([UserManager manager].isLogin) {
        WS(weakSelf)
        [LoadingView showLoadingInView:self.view];
        
        [self.viewModel postFavoriteDoctor:^(NSString *message) {
            
            [LoadingView hideLoadinForView:weakSelf.view];
            
            self.is_attention = !self.is_attention;
            
            [MBProgressHUDHelper showHudWithText:message];
            
        } failure:^(NSString *message) {
            
            [LoadingView hideLoadinForView:weakSelf.view];
            [MBProgressHUDHelper showHudWithText:message];
        }];
    }
    else {
        [[VCManager manager] presentLoginViewController:YES];
    }
}

#pragma mark - 预约挂号按钮
//预约挂号按钮
- (void)registerButtonAction:(id)sender {
    
    
    if(![UserManager manager].isLogin) {
        [[VCManager manager] presentLoginViewController:YES];
        return;
    }
    else {
        [PerfectInformationView showPerfectInformationAlertIsSuccess:^(BOOL success) {
            if (!success) {
                
                [LoadingView showLoadingInView:self.view];
                
                self.schedulingViewModel.hospitalCode = self.hospitalCode;
                self.schedulingViewModel.hosDeptCode = self.hosDeptCode;
                self.schedulingViewModel.hosDoctCode = self.hosDoctCode;
                
                [self.schedulingViewModel getDoctorScheduling:^{
                    
                    [LoadingView hideLoadinForView:self.view];
                    if (self.schedulingViewModel.message) {
                        [MBProgressHUDHelper showHudWithText:self.schedulingViewModel.message];
                    }
                    else {
                        SCDoctorSchedulingViewController *vc = [SCDoctorSchedulingViewController new];
//                        vc.viewModel = self.schedulingViewModel;
                        vc.viewModel.hosDeptCode = self.viewModel.resultModel.hosDeptCode;
                        vc.viewModel.hosDoctCode = self.viewModel.resultModel.hosDoctCode;
                        vc.viewModel.hospitalCode = self.viewModel.resultModel.hosOrgCode;
                        [self.navigationController pushViewController:vc animated:YES];
                    }
                } failure:^(NSError *error) {
                    [LoadingView hideLoadinForView:self.view];
                    [MBProgressHUDHelper showHudWithText:error.localizedDescription];
                }];
            }
        }];
    }
}

#pragma mark - scrollView delegate

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    
    NSInteger offsetY = scrollView.contentOffset.y;
    
    CGFloat colorFloat = (255-(205*(MIN(offsetY/64., 1.0))))/255.;
    
    NSLog(@"%f", colorFloat);
    
    self.navBackView.alpha = offsetY/64.;
    self.attentionLabel.textColor = [UIColor colorWithRed:colorFloat green:colorFloat blue:colorFloat alpha:1];
    
    if (offsetY > 64.) {
        //显示
        [self.popButton setImage:[UIImage imageNamed:@"icon_back"] forState:UIControlStateNormal];
        self.attentionImageView.image = self.is_attention ? [UIImage imageNamed:@"icon已关注"] : [UIImage imageNamed:@"icon未关注灰"];
    }
    else {
        //不显示
        [self.popButton setImage:[UIImage imageNamed:@"back_white"] forState:UIControlStateNormal];
        self.attentionImageView.image = self.is_attention ? [UIImage imageNamed:@"icon已关注"] : [UIImage imageNamed:@"icon未关注"];
    }
}

#pragma mark - tableView delegate && dataSource


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    
    DoctorDetailLayoutModel *layoutModel = self.viewModel.layoutArray[section];
    return layoutModel.type == DoctorDetailLayoutTypeJudgeSection ? 30. : 0.;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    
    DoctorDetailLayoutModel *layoutModel = self.viewModel.layoutArray[section];
    if (layoutModel.type == DoctorDetailLayoutTypeJudgeSection) {
        
        return [self tableView:tableView judgeSectionViewForHeaderInSection:section];
    }
    
    return nil;
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return self.viewModel.layoutArray.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    DoctorDetailLayoutModel *layoutModel = self.viewModel.layoutArray[section];
    
    switch (layoutModel.type) {
        case DoctorDetailLayoutTypeHeaderSection: {
            return 1;
        }
            break;
        case DoctorDetailLayoutTypeTopSection: {
            return 1;
        }
            break;
        case DoctorDetailLayoutTypeJudgeSection: {
            return self.viewModel.resultModel.evaluList.count > 5 ? 5 :self.viewModel.resultModel.evaluList.count;
        }
            break;
        default:
            return 0;
            break;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    DoctorDetailLayoutModel *layoutModel = self.viewModel.layoutArray[indexPath.section];
    
    switch (layoutModel.type) {
        case DoctorDetailLayoutTypeHeaderSection: {
            return [SCDoctorSchedulingInfoCell cellHeightWithModel:self.viewModel.headerModel];
        }
            break;
        case DoctorDetailLayoutTypeTopSection: {
            return self.viewModel.topCellHeight;
        }
            break;
        case DoctorDetailLayoutTypeJudgeSection: {
            DoctorDetailJudgeModel *cellModel = self.viewModel.resultModel.evaluList[indexPath.row];
            return cellModel.cellHeight;
        }
            break;
        default:
            return 0;
            break;
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    DoctorDetailLayoutModel *layoutModel = self.viewModel.layoutArray[indexPath.section];
    
    switch (layoutModel.type) {
        case DoctorDetailLayoutTypeHeaderSection: {
            return [self tableView:tableView headerCellForRowAtIndexPath:indexPath];
        }
            break;
        case DoctorDetailLayoutTypeTopSection: {
            return [self tableView:tableView topCellForRowAtIndexPath:indexPath];
        }
            break;
        case DoctorDetailLayoutTypeJudgeSection: {
            return [self tableView:tableView judgeCellForRowAtIndexPath:indexPath];
        }
            break;
        default: {
            static NSString *nullID = @"doctorDetailNULLID";
            UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:nullID];
            if (!cell) {
                cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nullID];
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
            }
            return cell;
        }
            break;
    }
}

- (void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    
    view.tintColor = [UIColor bc1Color];
}


#pragma mark - custom cell

- (UITableViewCell *)tableView:(UITableView *)tableView headerCellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"DoctorDetailHeaderCellIdentifier";
    SCDoctorSchedulingInfoCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[SCDoctorSchedulingInfoCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    cell.model = self.viewModel.headerModel;
    return cell;
}

- (UIView *)tableView:(UITableView *)tableView judgeSectionViewForHeaderInSection:(NSInteger)section {
    
    static NSString *identifier = @"DoctorDetailJudgeHeaderIdentifier";
    DoctorDetailJudgeHeader *header = [tableView dequeueReusableHeaderFooterViewWithIdentifier:identifier];
    if (!header) {
        header = [[DoctorDetailJudgeHeader alloc] initWithReuseIdentifier:identifier];
    }
    
    header.judgeCount = self.viewModel.resultModel.evaluateCount;
    
    header.moreBlock = ^ {
        if ([self.viewModel.resultModel.evaluateCount integerValue]<= 5) {
            return ;
        }
        DoctorDetailMoreJudgeViewController *viewController = [[DoctorDetailMoreJudgeViewController alloc] init];
        viewController.judgeCount = self.viewModel.resultModel.evaluateCount;
        viewController.doctorId = self.viewModel.resultModel.mid;
        [self.navigationController pushViewController:viewController animated:YES];
    };
    
    return header;
}

- (UITableViewCell *)tableView:(UITableView *)tableView topCellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"doctorDetailTopCellIdentifier";
    DoctorDetailTopCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[DoctorDetailTopCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    cell.cellModel = self.viewModel.resultModel;
    
    cell.moreButtonBlock = ^ {
        
        if (!self.viewModel.resultModel.doctorDesc.length) {
            return ;
        }
        else {
            DoctorDetailContentViewController *viewController = [[DoctorDetailContentViewController alloc] init];
            viewController.content = self.viewModel.resultModel.doctorDesc;
            [self.navigationController pushViewController:viewController animated:YES];
        }
    };
    
    return cell;
}

- (UITableViewCell *)tableView:(UITableView *)tableView judgeCellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"doctorDetailJudgeCellIdentifier";
    DoctorDetailJudgeCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[DoctorDetailJudgeCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    DoctorDetailJudgeModel *cellModel = self.viewModel.resultModel.evaluList[indexPath.row];
    cell.cellModel = cellModel;
    
    return cell;
}

@end
