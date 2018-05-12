//
//  SCBindingIphoneViewController.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCBindingIphoneViewController.h"

@interface SCBindingIphoneViewController ()

@end

@implementation SCBindingIphoneViewController

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
    self.navigationItem.title = @"绑定手机号";
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
    UIView *footView = [[UIView alloc] init];
    footView.backgroundColor = [UIColor bc2Color];
    footView.frame = CGRectMake(0,0,SCREEN_WIDTH,150);
    _tableView.tableFooterView = footView;
    
    UIButton *handlebutton = [UIButton buttonWithType:UIButtonTypeCustom];
    handlebutton.userInteractionEnabled = YES;
    handlebutton.layer.cornerRadius = 5;
    handlebutton.layer.borderColor = [[UIColor bc3Color] CGColor];
    handlebutton.layer.borderWidth = 0.5;
    [footView addSubview:handlebutton];
    [handlebutton setTitle:@"完成" forState:UIControlStateNormal];
    [handlebutton setBackgroundColor:[UIColor bc7Color]];
    handlebutton.titleLabel.font = [UIFont systemFontOfSize:16.0];
    [handlebutton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [[handlebutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        if (![RegexKit validateMobile:self.phoneNumStr]) {
            [MBProgressHUDHelper showHudWithText:@"手机号码不正确"];
            return;
        }
        if (self.VerificationCode.length <= 0) {
            [MBProgressHUDHelper showHudWithText:@"请输入验证码"];
            return;
        }
    }];
    [handlebutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(15);
        make.right.equalTo(self.view).offset(-15);
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
    titleLabel.numberOfLines = 0;
     if (SCREEN_HEIGHT <= 568.0) {
        titleLabel.font = [UIFont systemFontOfSize:11];
     } else {
         titleLabel.font = [UIFont systemFontOfSize:12];
     }
    titleLabel.text = @"点击手机号,更换手机号,下次登录时使用更换的手机号";
    titleLabel.textAlignment = NSTextAlignmentLeft;
    titleLabel.backgroundColor = [UIColor clearColor];
    titleLabel.textColor = [UIColor tc2Color];
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
    
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"phoneNumberCellIdentifier";
    if (indexPath.row == 0) {
        SCphoneNumberCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCphoneNumberCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.phoneNumberBlock = ^(NSString *tfText){
            self.phoneNumStr = tfText;
        };
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    } else if (indexPath.row == 1) {
        SCVerificationCodeCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCVerificationCodeCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.inCodeAction = ^(NSString *text) {
            self.VerificationCode = text;
        };
        cell.getCodeAction = ^(NSString *verificationStr){
            
        if (![RegexKit validateMobile:self.phoneNumStr]) {
            [MBProgressHUDHelper showHudWithText:@"手机号码不正确"];
            return;
          }
                        
        //NSDictionary *params = @{@"mobile" : self.phoneNumStr,
 //                                    @"type"   : @"5"};
            [self.viewModel getVerifyCode:^{
                
                 [[CountDownManager manager] countdownTime:CD_COUNTDOWN_BIND];
                
            } failure:^{
                
            }];

        };
        
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
        
    }
    return nil;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    //    if (indexPath.row == 0) {
    //
    //    } else if (indexPath.row == 1) {
    //        SCChangePassWordViewController *changePassWord = [[SCChangePassWordViewController alloc] init];
    //        [self.navigationController pushViewController:changePassWord animated:YES];
    //    } else if (indexPath.row == 2) {
    //        SCBindingIphoneViewController *bindingIphone = [[SCBindingIphoneViewController alloc] init];
    //        [self.navigationController pushViewController:bindingIphone animated:YES];
    //    }
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


@end
