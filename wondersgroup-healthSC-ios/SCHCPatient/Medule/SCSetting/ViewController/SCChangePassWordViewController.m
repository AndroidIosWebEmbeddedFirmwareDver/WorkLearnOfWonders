//
//  SCChangePassWordViewController.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCChangePassWordViewController.h"
#import "UserService.h"
#import "SpecialRegexKit.h"
#import "RSA.h"
@interface SCChangePassWordViewController ()
@end

@implementation SCChangePassWordViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setupView];
}

- (instancetype)init {
    self = [super init];
    if (self) {
        self.viewModel = [[SCUserViewModel alloc] init];
    }
    return self;
}


- (void)setupView
{
    WS(weakSelf);
    self.title = @"修改密码";
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
    [self setupFootView];
}

- (void)setupFootView
{
    WS(weakSelf);
    UIView *footView = [[UIView alloc] init];
    footView.backgroundColor = [UIColor bc2Color];
    footView.frame = CGRectMake(0,0,SCREEN_WIDTH,150);
    _tableView.tableFooterView = footView;
    
    _alertLabel = [[UILabel alloc] init];
    _alertLabel.font = [UIFont systemFontOfSize:12.0];
    _alertLabel.textColor = [UIColor stc2Color];
    [footView addSubview:_alertLabel];
    [_alertLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(footView).offset(15);
        make.right.equalTo(footView).offset(-15);
        make.top.equalTo(footView);
        make.height.equalTo(@25);
    }];
    
    _handlebutton = [UIButton buttonWithType:UIButtonTypeCustom];
    _handlebutton.enabled = YES;
    _handlebutton.layer.cornerRadius = 5;
    _handlebutton.layer.borderColor = [[UIColor bc3Color] CGColor];
    _handlebutton.layer.borderWidth = 0.5;
    [footView addSubview:_handlebutton];
    [_handlebutton setTitle:@"完成" forState:UIControlStateNormal];
    [_handlebutton setBackgroundColor:[UIColor bc7Color]];
    _handlebutton.titleLabel.font = [UIFont systemFontOfSize:16.0];
    [_handlebutton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [[_handlebutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
       if (![SpecialRegexKit validateSpecialPassword:self.oldPasswrodStr]) {
            _alertLabel.text = @"原密码格式不符,请重新输入";
            return ;
        } else {
            _alertLabel.text = @"";
        }
        
        if (![SpecialRegexKit validateSpecialPassword:self.passwordStrNew]) {
            _alertLabel.text = @"新密码格式不符,请重新输入";
            return;
        } else {
            _alertLabel.text = @"";
        }
        
        if (![self.passwordStrNew isEqualToString:self.surewordStr]) {
            _alertLabel.text = @"确认密码与新密码不匹配，请重新输入";
            return ;
        }else {
            _alertLabel.text = @"";
        }
      
        [self setEditing:NO];
        [[UserManager manager] saveUserPwd:[RSA rsaPassword:self.oldPasswrodStr]];
        self.viewModel.phone = [UserManager manager].mobile;
        self.viewModel.password = weakSelf.passwordStrNew;
        [self.viewModel resetPassword:^{
            NSLog(@"－－－修改成功－－－");
            [[UserService service] userKickLogoff];
            [[VCManager manager] presentLoginViewController:YES];
            [weakSelf.navigationController popToRootViewControllerAnimated:NO];
        } failure:^{
            NSLog(@"－－－修改失败－－－");
        }];
        
    }];
    [_handlebutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15);
        make.centerY.equalTo(footView);
        make.height.mas_equalTo(44);
    }];
}

- (void)setupHeadView
{
    UIView *headView = [[UIView alloc] init];
    headView.backgroundColor = [UIColor bc2Color];
    headView.frame = CGRectMake(0,0,SCREEN_WIDTH,44);
    _tableView.tableHeaderView = headView;
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.text = @"修改密码后，将重新登录账户";
    titleLabel.textColor = [UIColor tc2Color];
    titleLabel.textAlignment = NSTextAlignmentLeft;
    titleLabel.backgroundColor = [UIColor clearColor];
    titleLabel.font = [UIFont systemFontOfSize:12];
    [headView addSubview:titleLabel];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(headView);
        make.left.equalTo(headView).offset(15);
        make.right.equalTo(headView);
        make.height.mas_equalTo(20);
    }];

}

#pragma mark - tableView delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 3;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
     WS(weakSelf)
    static NSString *identifier = @"passwordCellIdentifier";
    if (indexPath.row == 0) {
        SCOldpasswordCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCOldpasswordCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.passwordBlock = ^(NSString *text){
            weakSelf.oldPasswrodStr = text;
        };
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    } else if (indexPath.row == 1) {
        SCNewPasswprdCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCNewPasswprdCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.textfieldblocK = ^(NSString *text){
            weakSelf.passwordStrNew = text;
        };
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
        
    } else if (indexPath.row == 2) {
        SCSurePassWordCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCSurePassWordCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.suretextfieldblocK = ^(NSString *text){
            weakSelf.surewordStr = text;
        };
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    }
    return nil;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


@end
