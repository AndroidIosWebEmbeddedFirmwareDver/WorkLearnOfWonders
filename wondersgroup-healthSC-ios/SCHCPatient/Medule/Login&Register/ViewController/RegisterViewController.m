//
//  RegisterViewController.m
//  CNHealthCloudPatient
//
//  Created by 杜凯 on 16/5/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "RegisterViewController.h"
#import "RegisterViewModel.h"
//#import "WDBaseWebViewController.h"
//#import "WDDynamicLoginViewController.h"
//#import "WDThreeLoginView.h"
//#import "AddressViewController.h"
//#import "RegiseterSetPwdViewController.h"

#import "SCSetPwdViewController.h"
#import "SCUserViewModel.h"
@interface RegisterViewController ()

@property (nonatomic,strong) SCUserViewModel *viewModel;

@property (nonatomic,strong) UITextField    *mobileTextField;

@property (nonatomic,strong) UITextField    *codeTextField;

@property (nonatomic,strong) UIButton       *sendCodeButon;

@property (nonatomic,strong) UILabel        *countDownLabel;

@property (nonatomic,strong) UIButton       *registerButton;


@property (nonatomic,strong)UITextField *   pwdTextField;


//@property (nonatomic,strong)UIButton * checkButton;

//@property (nonatomic,strong)UILabel * agreeLabel;

//@property (nonatomic,strong)UIButton * rightsButton;

@end

@implementation RegisterViewController

- (SCUserViewModel *)viewModel{
    if (!_viewModel) {
        _viewModel = [[SCUserViewModel alloc] init];
    }
    return _viewModel;
}


- (UITextField *)mobileTextField{
    if (!_mobileTextField) {
        _mobileTextField = [UITextField new];
        _mobileTextField.borderStyle = UITextBorderStyleNone;
        _mobileTextField.placeholder = @"手机号";
        _mobileTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _mobileTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _mobileTextField;
}
- (UITextField *)pwdTextField{
    if (!_pwdTextField) {
        _pwdTextField = [UITextField new];
        _pwdTextField.borderStyle = UITextBorderStyleNone;
        _pwdTextField.placeholder = @"密码";
        _pwdTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _pwdTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _pwdTextField;
}

- (UITextField *)codeTextField{
    if (!_codeTextField) {
        _codeTextField = [UITextField new];
        _codeTextField.borderStyle = UITextBorderStyleNone;
        _codeTextField.placeholder = @"验证码";
        _codeTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _codeTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _codeTextField;
}
/*
- (UILabel *)countDownLabel{
    if (!_countDownLabel) {
        _countDownLabel = [UILabel new];
        _countDownLabel.textColor = [UIColor tc4Color];
        _countDownLabel.font = [UIFont systemFontOfSize: 16.0];
        [_countDownLabel setTextAlignment: NSTextAlignmentCenter];
        [_countDownLabel setAlpha: 0.0];
    }
    return _countDownLabel;
}
- (UILabel *)agreeLabel{
    if (!_agreeLabel) {
        _agreeLabel = [UILabel new];
        _agreeLabel.font =  [UIFont systemFontOfSize:14.];
        _agreeLabel.text = @"同意";
    }
    return _agreeLabel;
}
- (UIButton *)checkButton{

    if (!_checkButton) {
        _checkButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_checkButton setImage:[UIImage imageNamed:@"icon_check_yes"] forState:UIControlStateNormal];
        [_checkButton setImage:[UIImage imageNamed:@"icon_check_no"] forState:UIControlStateSelected];
        [_checkButton addTarget:self action:@selector(checkRightFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _checkButton;
}

- (UIButton *)rightsButton{
    
    if (!_rightsButton) {
        _rightsButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_rightsButton setTitle:@"《健康云用户协议》" forState:UIControlStateNormal];
        _rightsButton.titleLabel.font = [UIFont systemFontOfSize:14.];
        [_rightsButton setTitleColor:[UIColor main1Color] forState:UIControlStateNormal];
        [_rightsButton addTarget:self action:@selector(readRightFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _rightsButton;
}
*/

- (UIButton *)sendCodeButon{
    if (!_sendCodeButon) {
        _sendCodeButon = [UIButton buttonWithType:UIButtonTypeCustom];
        [_sendCodeButon setTitle:@"获取验证码" forState:UIControlStateNormal];
        [_sendCodeButon.titleLabel setFont: [UIFont systemFontOfSize:14.]];
        [_sendCodeButon setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        _sendCodeButon.backgroundColor = [UIColor orangeColor];
        [_sendCodeButon addTarget:self action:@selector(getVeryCodeFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _sendCodeButon;
}


- (UIButton *)registerButton{
    if (!_registerButton) {
        _registerButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_registerButton setTitle:@"注册" forState:UIControlStateNormal];
        [_registerButton.titleLabel setFont: [UIFont systemFontOfSize:16.]];
        [_registerButton setBackgroundColor:[UIColor bc7Color]];
        [_registerButton setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
        [_registerButton addTarget:self action:@selector(finishFuntion:) forControlEvents:UIControlEventTouchUpInside];
        _registerButton.layer.cornerRadius = 5;
        _registerButton.clipsToBounds = YES;
    }
    return _registerButton;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title = @"注册";
    [self setupView];
    [self bindViewModel];
   
    // Do any additional setup after loading the view.
}

#pragma mark - setupView
- (void)setupView{
    WS(weakSelf)
    UIView *inputView = [[UIView alloc] init];
    [self.view addSubview: inputView];
    [inputView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.view);
        make.top.equalTo(weakSelf.view).offset(20);
        make.height.mas_equalTo(135.0);
    }];
    [[Global global]addTopLineInView:inputView withColor:[UIColor dc1Color]];
    [[Global global]addBottomLineInView:inputView withColor:[UIColor dc1Color]];
    [inputView addSubview: self.mobileTextField];
    [self.mobileTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15.);
        make.right.equalTo(weakSelf.view).offset(-15.);
        make.top.equalTo(inputView);
        make.height.mas_equalTo(44.0);
    }];
    
    [inputView addSubview: self.codeTextField];
    [self.codeTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-80);
        make.top.equalTo(weakSelf.mobileTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(44.0);
    }];
    
    
    [inputView addSubview: self.sendCodeButon];
    [self.sendCodeButon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.view).offset(-15);
        make.centerY.equalTo(self.codeTextField);
        make.width.mas_equalTo(75.0);
        make.height.mas_equalTo(44.0);
    }];
    /*
    [inputView addSubview: self.countDownLabel];
    [self.countDownLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.sendCodeButon);
    }];
    */
    UIView *toplineView = [UISetupView setupLineViewWithSuperView: inputView];
    [toplineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(15);
        make.top.equalTo(weakSelf.mobileTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    UIView *secondlineView = [UISetupView setupLineViewWithSuperView: inputView];
    [secondlineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(15);
        make.top.equalTo(weakSelf.codeTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    [inputView addSubview: self.pwdTextField];
    [self.pwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-75);
        make.top.equalTo(weakSelf.codeTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(44.0);
    }];

    [self.view addSubview: self.registerButton];
    [self.registerButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.mas_equalTo(weakSelf.view.mas_left).offset(15);
        make.right.mas_equalTo(weakSelf.view.mas_right).offset(-15.);
        make.top.mas_equalTo(inputView.mas_bottom).offset(30.0);
        make.height.mas_equalTo(@44.);
    }];
    

    
/*
    WDThreeLoginView *threeLoginView = [WDThreeLoginView new];
    [self.view addSubview:threeLoginView];
    [threeLoginView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(ws.view);
        make.right.equalTo(ws.view);
        make.bottom.equalTo(ws.view);
        make.height.mas_equalTo(150);
    }];
    threeLoginView.startLogin = ^(){
        [MBProgressHUDHelper showHudIndeterminate];
    };
    threeLoginView.loginSuccess = ^(){
        [MBProgressHUDHelper hideHud];
        [self dismissViewControllerAnimated:YES completion:nil];
    };
    threeLoginView.loginFailure = ^(NSString *message){
        
        [MBProgressHUDHelper showHudWithText:message];
    };
*/
}



#pragma mark - 检查是否同意协议
- (void)checkRightFunction:(id)sender{
    UIButton * btn = sender;
    btn.selected = !btn.selected;
   
}

#pragma mark - 检查是否同意协议
- (void)readRightFunction:(id)sender{
    
    /*
    WDBaseWebViewController * view = [[WDBaseWebViewController alloc]initWithURL:[TaskManager manager].appConfig.common.userAgreement];
    view.hidesBottomBarWhenPushed =YES;
    [self.navigationController pushViewController:view animated:YES];
     */

}
#pragma mark - bindViewModel
- (void)bindViewModel{
    
    WS(weakSelf)
    RAC(self.viewModel, phone) = self.mobileTextField.rac_textSignal;
    RAC(self.viewModel, code)   = self.codeTextField.rac_textSignal;
    RAC(self.viewModel, password)   = self.pwdTextField.rac_textSignal;
    RAC(self.registerButton, enabled) = [RACSignal
                                 combineLatest:@[self.mobileTextField.rac_textSignal , self.codeTextField.rac_textSignal,self.pwdTextField.rac_textSignal]
                                 reduce:^(NSString *mobile, NSString *code) {
                                     return @(([RegexKit validateMobile: mobile] && [code length] > 0));
                                 }];
    [RACObserve(self.registerButton, enabled)subscribeNext:^(id x) {
        weakSelf.registerButton.alpha   = [x boolValue] ? 1.0 : 0.3;
    }];
    [RACObserve([CountDownManager manager], countDownRegister) subscribeNext:^(NSNumber *countdown) {
        if ([countdown intValue] <= 0) {
            
            [weakSelf.sendCodeButon setTitle:@"获取验证码" forState:UIControlStateNormal];
            weakSelf.sendCodeButon.enabled = YES;
        }
        else {
            [weakSelf.sendCodeButon setTitle:[NSString stringWithFormat:@"重获验证码(%d)", [countdown intValue]] forState:UIControlStateDisabled];
            weakSelf.sendCodeButon.enabled = NO;
        }
    }];

  
}

- (void)getVeryCodeFunction:(id)sender{
    if (![RegexKit validateMobile:self.viewModel.phone]) {
        
        [MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        [self.mobileTextField becomeFirstResponder];
        return;
    }
     self.viewModel.codeType = VerifyCodeTypeDeafult;
    [self.viewModel getVerifyCode:^{
        [[CountDownManager manager] countdownTime: CD_COUNTDOWN_REGISTER];
    } failure:^{
        
    }];


}
- (void)finishFuntion:(id)sender{
    self.registerButton.userInteractionEnabled = NO;
    NSString *errorMSG = nil;
    if (![RegexKit validateMobile:self.viewModel.phone]) {
        errorMSG = @"请输入有效的手机号";
    }
    if (![RegexKit validateDynamicCode:self.viewModel.code]) {
        errorMSG = @"无效的验证码，请重新输入";
    }
//    if (self.checkButton.selected) {
//        errorMSG = @"请勾选用户协议";
//    }
    if (errorMSG) {
        [MBProgressHUDHelper showHudWithText: errorMSG];
        [self.mobileTextField becomeFirstResponder];
        self.registerButton.userInteractionEnabled = YES;
        return;
    }
    [self.viewModel registerUser:^{
        self.registerButton.userInteractionEnabled = YES;
        SCSetPwdViewController * set = [SCSetPwdViewController new];
        set.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:set animated:YES];
    } failure:^{
         self.registerButton.userInteractionEnabled = YES;
    }];
/*
    [self.viewModel registerUser:^{
        
        self.finishButton.userInteractionEnabled = YES;
        
        RegiseterSetPwdViewController * regist = [RegiseterSetPwdViewController new];
        regist.verycode = self.viewModel.verifyCode;
         regist.phone = self.viewModel.loginName;
        regist.needhasBack = YES;
        [self.navigationController  pushViewController:regist animated:YES];
        //AddressViewController * address = [AddressViewController new];
        //address.from = 0;
       // address.verycode = self.viewModel.verifyCode;
        //address.phone = self.viewModel.loginName;
       // [self.navigationController pushViewController:address animated:YES];

        
        
    } failure:^{
          self.finishButton.userInteractionEnabled = YES;
    }];
 
*/
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)popBack{
    [self.navigationController popViewControllerAnimated:YES];
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
