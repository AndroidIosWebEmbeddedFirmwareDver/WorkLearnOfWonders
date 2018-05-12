//
//  SCSettingViewController.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSettingViewController.h"
#import "UserService.h"
#import "SCSettingAccountCell.h"
#import "SCSettingViewModel.h"
#import "FunctionIntroductionViewController.h"

@interface SCSettingViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic,strong) UITableView        *tableView;
@property (nonatomic,strong) SCSettingViewModel *viewModel;

@end

@implementation SCSettingViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [SCSettingViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];

    [self.viewModel reloadDatas];
    
    [self setupview];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

#pragma mark    - setupView

- (void)setupview {
    WS(weakSelf);
    self.navigationItem.title = @"设置";
    self.view.backgroundColor = [UIColor bc2Color];
    self.tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = [UIColor bc2Color];
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.right.equalTo(weakSelf.view);
    }];
    [self setupHeadView];
//    if([UserManager manager].isLogin) {
        [self setupfootView];
//    }
}
- (void)setupHeadView
{
    UIView *headView = [[UIView alloc] init];
    headView.backgroundColor = [UIColor bc2Color];
    headView.frame = CGRectMake(0,0,SCREEN_WIDTH,200);
    _tableView.tableHeaderView = headView;
    
    UIImageView *iconimageView = [[UIImageView alloc] init];
    iconimageView.image = [UIImage imageNamed:@"logo"];
    [headView addSubview:iconimageView];
    iconimageView.layer.cornerRadius = 10;
    [iconimageView.layer setMasksToBounds:YES];
    [iconimageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(headView);
        make.top.equalTo(headView).offset(40);
        make.size.mas_equalTo(CGSizeMake(80,80));
    }];
    NSDictionary *infoDictionary = [[NSBundle mainBundle] infoDictionary];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    NSString *app_Name = [infoDictionary objectForKey:@"CFBundleDisplayName"];
    titleLabel.text = app_Name;
    titleLabel.textColor = [UIColor tc3Color];
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.backgroundColor = [UIColor clearColor];
    titleLabel.font = [UIFont systemFontOfSize:14];
    [headView addSubview:titleLabel];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(iconimageView.mas_bottom).offset(5);
        make.centerX.equalTo(headView);
        make.height.mas_equalTo(20);
    }];
    
    UILabel *versonLabel = [[UILabel alloc] init];
    // app版本
    NSString *app_Version = [infoDictionary objectForKey:@"CFBundleShortVersionString"];
    versonLabel.text = [NSString stringWithFormat:@"V%@版",app_Version];
    versonLabel.textColor = [UIColor tc3Color];
    versonLabel.textAlignment = NSTextAlignmentCenter;
    versonLabel.backgroundColor = [UIColor clearColor];
    versonLabel.font = [UIFont systemFontOfSize:14];
    [headView addSubview:versonLabel];
    [versonLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(titleLabel.mas_bottom).offset(5);
        make.left.equalTo(titleLabel);
        make.right.equalTo(titleLabel);
        make.height.mas_equalTo(20);
    }];
    
}

- (void)setupfootView
{
    WS(weakSelf)
    UIView *footView = [[UIView alloc] init];
    footView.backgroundColor = [UIColor bc2Color];
    footView.frame = CGRectMake(0,0,SCREEN_WIDTH,200);
    _tableView.tableFooterView = footView;
    
    UIButton *handlebutton = [UIButton buttonWithType:UIButtonTypeCustom];
    handlebutton.userInteractionEnabled = YES;
    handlebutton.layer.cornerRadius = 5;
    handlebutton.layer.borderColor = [[UIColor bc3Color] CGColor];
    handlebutton.layer.borderWidth = 0.5;
    [footView addSubview:handlebutton];
    [handlebutton setTitle:@"退出当前账号" forState:UIControlStateNormal];
    [handlebutton setBackgroundColor:[UIColor whiteColor]];
    handlebutton.titleLabel.font = [UIFont systemFontOfSize:16.0];
    [handlebutton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    [[handlebutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
        [alert reloadTitle:@"" content:@"是否退出登录"];
        [alert.cancelBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        [alert.submitBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        [alert.submitBtn setTitle:@"确定" forState:UIControlStateNormal];
        [alert.cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
        alert.submitBlock = ^(WDAlertView *view) {
#pragma mark 退出登录
            [view dismiss];
            [[UserService service] userLogoff];
            [[VCManager manager] showLoginViewController:NO];
        };
        alert.cancelBlock = ^(WDAlertView *view) {
            [view dismiss];
        };
        [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
    }];
    [handlebutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15);
        make.centerY.equalTo(footView);
        make.height.mas_equalTo(44);
    }];
}
#pragma mark - tableView delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return self.viewModel.modelArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {

    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"SettingCellIdentifier";
    SCSettingAccountCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[SCSettingAccountCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
    }
    cell.model = self.viewModel.modelArray[indexPath.row];
    cell.isLast = (indexPath.row == 1)?YES:NO;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 1) {
        FunctionIntroductionViewController *vc = [FunctionIntroductionViewController new];
        [self.navigationController pushViewController:vc animated:YES];
    }

}



@end
