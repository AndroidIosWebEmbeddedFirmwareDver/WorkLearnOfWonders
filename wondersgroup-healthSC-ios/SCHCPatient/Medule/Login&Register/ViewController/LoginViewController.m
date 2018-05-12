//
//  LoginViewController.m
//  CNHealthCloudPatient
//
//  Created by 杜凯 on 16/5/13.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "LoginViewController.h"
#import "HeadDetailView.h"
#import "LoginInputView.h"
#import "RegisterViewController.h"
//#import "SendCodeViewController.h"
#import "UserService.h"
//#import "FirstLoginViewController.h"
#import "SCSetPwdViewController.h"
#import "SCResetPwdViewController.h"
#import "SCUserSetViewController.h"
#import "SCUserViewModel.h"
#import "WDBaseWebViewController.h"
@interface LoginViewController ()<UITextFieldDelegate>
/*
@property (nonatomic, strong) UITextField *passwordMobileText;
@property (nonatomic, strong) UITextField *passwordText;

@property (nonatomic, strong) UITextField *codeMobileText;
@property (nonatomic, strong) UITextField *codeText;

@property (nonatomic, strong) UIButton    *loginBtn;
@property (nonatomic, strong) UIButton    *codeLoginBtn;
@property (nonatomic, strong) UIButton    *smsBtn;
@property (nonatomic, strong) UILabel     *countDownLabel;
*/
@property (nonatomic,strong) UITextField    *mobileTextField;

@property (nonatomic,strong)UIView * mobileLineView;

@property (nonatomic,strong) UITextField    *codeTextField;


@property (nonatomic,strong)UITextField * pwdTextField;

@property (nonatomic,strong)UIView * codeLineView;

@property (nonatomic,strong)UIView * pwdLineView;

@property (nonatomic,strong) UIButton       *sendCodeButon;
//显示密码
@property (nonatomic,strong)UIButton * showPwdButton;

@property (nonatomic,strong)UILabel * tipLabel;

@property (nonatomic,strong) UIButton       *loginButton;

@property (nonatomic,strong)UILabel * functionLabel;

@property (nonatomic,strong)UILabel * callPhoneLabel;

@property (nonatomic,copy)NSString * mobielError;
@property (nonatomic,copy)NSString * codeError;
@property (nonatomic,copy)NSString * pwdError;
@end

@implementation LoginViewController
- (UITextField *)mobileTextField{
    if (!_mobileTextField) {
        _mobileTextField = [UITextField new];
        _mobileTextField.borderStyle = UITextBorderStyleNone;
        _mobileTextField.placeholder = @"手机号码";
        _mobileTextField.font = [UIFont systemFontOfSize:14.];
        _mobileTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _mobileTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
        _mobileTextField.delegate = self;
    }
    return _mobileTextField;
}
- (UITextField *)codeTextField{
    if (!_codeTextField) {
        _codeTextField = [UITextField new];
        _codeTextField.borderStyle = UITextBorderStyleNone;
        _codeTextField.placeholder = @"验证码";
        _codeTextField.font = [UIFont systemFontOfSize:14];
        _codeTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _codeTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
         _codeTextField.delegate = self;
    }
    return _codeTextField;
}
- (UITextField *)pwdTextField{
    if (!_pwdTextField) {
        _pwdTextField = [UITextField new];
        _pwdTextField.borderStyle = UITextBorderStyleNone;
        _pwdTextField.placeholder = @"密码";
        _pwdTextField.secureTextEntry = YES;
        _pwdTextField.font = [UIFont systemFontOfSize:14];
        //_pwdTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _pwdTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
         _pwdTextField.delegate = self;
    }
    return _pwdTextField;
}
- (UIButton *)sendCodeButon{
    if (!_sendCodeButon) {
        _sendCodeButon = [UIButton buttonWithType:UIButtonTypeCustom];
        [_sendCodeButon setTitle:@"获取验证码" forState:UIControlStateNormal];
        [_sendCodeButon.titleLabel setFont: [UIFont systemFontOfSize:14.]];
        [_sendCodeButon setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        [_sendCodeButon addTarget:self action:@selector(getVeryCodeFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _sendCodeButon;
}
- (UIButton *)showPwdButton{
    if (!_showPwdButton) {
        _showPwdButton = [UIButton buttonWithType:UIButtonTypeCustom];
         [_showPwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
        [_showPwdButton addTarget:self action:@selector(showPwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _showPwdButton;
    
}


- (UILabel *)tipLabel{
    if (!_tipLabel) {
        _tipLabel = [UILabel new];
        _tipLabel.font = [UIFont systemFontOfSize:12.];
        _tipLabel.textColor = [UIColor stc2Color];
    }
    return _tipLabel;
}

- (UILabel *)functionLabel{
    if (!_functionLabel) {
        _functionLabel = [UILabel new];
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            _functionLabel.font = [UIFont systemFontOfSize:11.];
        }else{
            _functionLabel.font = [UIFont systemFontOfSize:12.];
        }
        
        _functionLabel.textColor = [UIColor tc3Color];
        
        _functionLabel.numberOfLines = 0;
     
    }
    return _functionLabel;
}

- (UILabel *)callPhoneLabel{
    if (!_callPhoneLabel) {
        _callPhoneLabel = [UILabel new];
    
        _callPhoneLabel.font = [UIFont systemFontOfSize:12.];
        _callPhoneLabel.textColor = [UIColor tc1Color];
        _callPhoneLabel.textAlignment = NSTextAlignmentCenter;
        _callPhoneLabel.numberOfLines = 0;
        
    }
    return _callPhoneLabel;
}
- (UIButton *)loginButton{
    if (!_loginButton) {
        _loginButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_loginButton setTitle:@"登录" forState:UIControlStateNormal];
        [_loginButton.titleLabel setFont: [UIFont systemFontOfSize:16.]];
      
        [_loginButton setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
        [_loginButton addTarget:self action:@selector(finishFuntion:) forControlEvents:UIControlEventTouchUpInside];
        _loginButton.layer.cornerRadius = 5;
        _loginButton.layer.borderColor = [UIColor tc3Color].CGColor;
        _loginButton.layer.borderWidth = 0.5;
        _loginButton.clipsToBounds = YES;
    }
    return _loginButton;
}

-(instancetype)init {
    self = [super init];
    if (self) {
        self.viewModel = [[LoginViewModel alloc] init];
    }
    return self;
}

- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
        self.viewModel = [[LoginViewModel alloc] init];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title = @"登录";
    [self setupUIViews];
    [self bindViewModel];
    //改变登录状态
    /*
    if([UserManager manager].isPWDLogin) {
        self.viewModel.viewType = LoginViewTypePWD;
    }
    else {
        self.viewModel.viewType = LoginViewTypeCode;
    }
     */
     self.viewModel.viewType = LoginViewTypeCode;
    self.view.backgroundColor = [UIColor whiteColor];
}

#pragma mark - RAC监听
- (void)bindViewModel {
    
    
    
    WS(weakSelf);
    self.mobileTextField.text = [UserManager manager].accountName;
    RAC(self.viewModel, mobile) = self.mobileTextField.rac_textSignal;
    RAC(self.viewModel, code) = self.codeTextField.rac_textSignal;
    RAC(self.viewModel, password) = self.pwdTextField.rac_textSignal;

    [self.mobileTextField.rac_textSignal subscribeNext:^(NSString * text) {
        if ([text length]) {
            [weakSelf.sendCodeButon setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
            // weakSelf.sendCodeButon.enabled = YES;
        }else{
            [weakSelf.sendCodeButon setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
              //weakSelf.sendCodeButon.enabled = YES;
        }
    }];
    RAC(self.sendCodeButon, enabled) = [RACSignal combineLatest:@[self.mobileTextField.rac_textSignal,RACObserve([CountDownManager manager], countDownLogin)] reduce:^(NSString * text,NSNumber *countdown){
        if ([countdown intValue] <= 0 && [text length]){
            return @YES;
        }else{
            return @NO;
        }
        
        
    }];
    [RACObserve([CountDownManager manager], countDownLogin) subscribeNext:^(NSNumber *countdown) {
        if ([countdown intValue] <= 0) {
            
            [weakSelf.sendCodeButon setTitle:@"获取验证码" forState:UIControlStateNormal];
            //weakSelf.sendCodeButon.enabled = YES;
        }
        else {
            [weakSelf.sendCodeButon setTitle:[NSString stringWithFormat:@"       %ldS", [countdown integerValue]] forState:UIControlStateNormal];
            //weakSelf.sendCodeButon.enabled = NO;
        }
    }];
    
    [RACObserve(self.pwdTextField,secureTextEntry)subscribeNext:^(NSNumber * secure) {
        if (weakSelf.pwdTextField.secureTextEntry) {
            [weakSelf.showPwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
        }else{
            [weakSelf.showPwdButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        }
    }];

    RAC(self.loginButton, enabled) =[RACSignal combineLatest:@[self.mobileTextField.rac_textSignal,self.pwdTextField.rac_textSignal,self.codeTextField.rac_textSignal,RACObserve(self.viewModel, viewType)] reduce:^(NSString *mobile, NSString *pwd,NSString * code){
        if (weakSelf.viewModel.viewType == LoginViewTypePWD) {
            return @(( [mobile length] && [pwd length]  > 0));
        }else{
             return @(( [mobile length] && [code length]  > 0));
        }
        
        }];
    
     [RACObserve(self.loginButton,enabled )subscribeNext:^(NSNumber * enable) {
         if ([enable boolValue]) {
             weakSelf.loginButton.layer.borderColor = [UIColor tc5Color].CGColor;
             [weakSelf.loginButton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
         }else{
             weakSelf.loginButton.layer.borderColor = [UIColor tc3Color].CGColor;
             [weakSelf.loginButton setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
         }
     }];
    
   }

#pragma makr - 构建视图
- (void)setupUIViews {
    WS(weakSelf);
    
    HeadDetailView *headView = [[HeadDetailView alloc] initWithLogo: @"登录页logo"
                                                         withSlogan: self.viewModel.headTip withfastLoginName:@"动态码登录" withPwdLoginName:@"密码登录"];
  
    headView.changeBlock = ^(NSInteger index){
        
        weakSelf.viewModel.viewType = (LoginViewType)index;
    };
    headView.delBlock = ^(void){
        NSLog(@"关闭登录");
        [self dismissViewControllerAnimated:YES completion:nil];
    };
    [self.view addSubview: headView];
    [headView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(199.0);
    }];

    UIView *inputView = [[UIView alloc] init];
    [self.view addSubview: inputView];
    [inputView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45);
        make.right.equalTo(weakSelf.view).offset(-45.);
        make.top.equalTo(headView.mas_bottom).offset(30);
        make.height.mas_equalTo(135.0);
    }];
    [inputView addSubview: self.mobileTextField];
    [self.mobileTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45.);
        make.right.equalTo(weakSelf.view).offset(-45.);
        make.top.equalTo(inputView);
        make.height.mas_equalTo(44.0);
    }];
    UIView *toplineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [toplineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45);
        make.right.equalTo(weakSelf.view).offset(-45);
        make.top.equalTo(weakSelf.mobileTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.mobileLineView = toplineView;
    [inputView addSubview: self.codeTextField];
    [self.codeTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45);
        make.right.equalTo(weakSelf.view).offset(-125.);
        make.top.equalTo(weakSelf.mobileLineView.mas_bottom);
        make.height.mas_equalTo(44.0);
    }];
    [inputView addSubview: self.pwdTextField];
    [self.pwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45);
        make.right.equalTo(weakSelf.view).offset(-65.);
        make.top.equalTo(weakSelf.mobileLineView.mas_bottom);
        make.height.mas_equalTo(44.0);
    }];
  
    UIView *secondlineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [secondlineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45);
        make.right.equalTo(weakSelf.view).offset(-45);
        make.top.equalTo(weakSelf.codeTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.codeLineView = secondlineView;
    
    [inputView addSubview: self.sendCodeButon];
    [self.sendCodeButon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.view).offset(-45);
        make.centerY.equalTo(weakSelf.codeTextField);
        make.width.mas_equalTo(75.0);
        make.height.mas_equalTo(44.0);
    }];
    UIView *pwdlineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [pwdlineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45);
        make.right.equalTo(weakSelf.view).offset(-45);
        make.top.equalTo(weakSelf.codeTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.pwdLineView = pwdlineView;

    [inputView addSubview: self.sendCodeButon];
    [self.sendCodeButon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.view).offset(-45);
        make.centerY.equalTo(weakSelf.codeTextField);
        make.width.mas_equalTo(75.0);
        make.height.mas_equalTo(44.0);
    }];
    
    [inputView addSubview: self.showPwdButton];
    [self.showPwdButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.view).offset(-35);
        make.centerY.equalTo(weakSelf.codeTextField);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];
    
    
    [inputView addSubview:self.tipLabel];
    [self.tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(45);
        make.right.equalTo(weakSelf.view).offset(45);
        make.top.equalTo(weakSelf.codeLineView.mas_bottom).offset(5);
        make.height.mas_equalTo(30.);
    }];
    [self.view addSubview: self.loginButton];
    [self.loginButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.mas_equalTo(weakSelf.view.mas_left).offset(45);
        make.right.mas_equalTo(weakSelf.view.mas_right).offset(-45.);
        make.top.mas_equalTo(inputView.mas_bottom).offset(10.0);
        make.height.mas_equalTo(@44.);
    }];
    [self.view addSubview: self.functionLabel];
    self.functionLabel.userInteractionEnabled = YES;
 
    [self.functionLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.mas_equalTo(weakSelf.view.mas_left).offset(42);
        make.right.mas_equalTo(weakSelf.view.mas_right).offset(-44.);
        make.top.mas_equalTo(weakSelf.loginButton.mas_bottom).offset(15.0);
    }];
    [self.view addSubview: self.callPhoneLabel];
    if(![[TaskManager manager].appConfig.common.consumerHotline length]){
        
        [TaskManager manager].appConfig.common.consumerHotline = @"400-900-9957";
    }
        NSString * str = [NSString stringWithFormat:@"微健康客服电话：%@", [TaskManager manager].appConfig.common.consumerHotline];
     NSMutableAttributedString * callPhoneText = [[NSMutableAttributedString alloc]initWithString:str] ;
    [callPhoneText addAttribute:NSForegroundColorAttributeName value:[UIColor tc5Color] range:NSMakeRange(callPhoneText.length-[TaskManager manager].appConfig.common.consumerHotline.length,[TaskManager manager].appConfig.common.consumerHotline.length)];
    self.callPhoneLabel.userInteractionEnabled = YES;
    self.callPhoneLabel.attributedText = callPhoneText;
    [self.callPhoneLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.mas_equalTo(weakSelf.view.mas_left).offset(45.);
        make.right.mas_equalTo(weakSelf.view.mas_right).offset(-45.);
        make.bottom.mas_equalTo(weakSelf.view.mas_bottom).offset(-22.5);
    }];
     NSString * phone = [NSString stringWithFormat:@"telprompt://%@", [TaskManager manager].appConfig.common.consumerHotline];
    UITapGestureRecognizer *callTapFunction = [[UITapGestureRecognizer alloc] init];
    [[callTapFunction rac_gestureSignal] subscribeNext:^(id x) {
        if([[UIApplication sharedApplication]canOpenURL:[NSURL URLWithString:phone]]){
            [[UIApplication sharedApplication]openURL:[NSURL URLWithString:phone]];
        }
        
    }];
    [self.callPhoneLabel addGestureRecognizer:callTapFunction];

    
    
    UITapGestureRecognizer *tapFunction = [[UITapGestureRecognizer alloc] init];
    [[tapFunction rac_gestureSignal] subscribeNext:^(id x) {
        if(weakSelf.viewModel.viewType == LoginViewTypeCode){
#pragma mark - web
            WDBaseWebViewController * view = [[WDBaseWebViewController alloc]initWithURL:[TaskManager manager].appConfig.common.userAgreement];
            view.hidesBottomBarWhenPushed =YES;
            [self.navigationController pushViewController:view animated:YES];
            
        }else{
        #pragma mark - 重置密码
            SCResetPwdViewController * resetVC = [SCResetPwdViewController new];
            resetVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:resetVC animated:YES];
        }
    }];
    [self.functionLabel addGestureRecognizer:tapFunction];
    
    NSMutableAttributedString * functionRightText = [[NSMutableAttributedString alloc]initWithString:@"未注册的手机号码将自动注册为微健康账户,且代表您已同意《微健康用户注册协议》"] ;
    [functionRightText addAttribute:NSForegroundColorAttributeName value:[UIColor tc5Color] range:NSMakeRange(functionRightText.length-11,11)];
    NSMutableAttributedString *forgetPwdText = [[NSMutableAttributedString alloc]initWithString:@"忘记密码？" ];
    [forgetPwdText addAttribute:NSUnderlineStyleAttributeName value:@(NSUnderlineStyleThick) range:NSMakeRange(0,forgetPwdText.length)];
    [RACObserve(self.viewModel, viewType) subscribeNext:^(id x) {
        NSInteger type =  [x integerValue];
        
        if (type == LoginViewTypeCode) {//验证码登录方式
           
            weakSelf.sendCodeButon.hidden = NO;
            weakSelf.showPwdButton.hidden = YES;
            weakSelf.codeTextField.hidden = NO;
            weakSelf.pwdTextField.hidden = YES;
            weakSelf.pwdLineView .hidden = YES;
            weakSelf.codeLineView.hidden = NO;
            weakSelf.functionLabel.attributedText = functionRightText;
            weakSelf.functionLabel.textAlignment = NSTextAlignmentLeft;
            //[weakSelf.mobileTextField becomeFirstResponder];
            [weakSelf resetTip];
        }
        else if (type == LoginViewTypePWD) {//密码登录方式
           
            self.sendCodeButon.hidden = YES;
            self.showPwdButton.hidden = NO;
            weakSelf.pwdTextField.hidden = NO;
            weakSelf.codeTextField.hidden = YES;
            weakSelf.pwdLineView .hidden = NO;
            weakSelf.codeLineView.hidden = YES;
            weakSelf.functionLabel.textAlignment = NSTextAlignmentCenter;
            weakSelf.functionLabel.attributedText = forgetPwdText;
            //[weakSelf.mobileTextField becomeFirstResponder];
            [weakSelf resetTip];
           
        }
    }];


     
}

- (UIStatusBarStyle)preferredStatusBarStyle{

    return UIStatusBarStyleLightContent;
}
#pragma mark - 用户登录
- (void)finishFuntion:(id)sender{
    WS(weakSelf);
    if(![RegexKit validateMobile:self.viewModel.mobile]){
        self.tipLabel.text = @"请输入正确的11位手机号码";
        self.mobielError = @"请输入正确的11位手机号码";
        self.mobileLineView.backgroundColor = [UIColor stc2Color];
         [self.mobileTextField becomeFirstResponder];
        return;
    
    }
       self.mobielError = @"";
     self.mobileLineView.backgroundColor = [UIColor tc3Color];
    if (self.viewModel.viewType == LoginViewTypeCode) {
        if(![RegexKit validateDynamicCode:self.viewModel.code]){
            self.tipLabel.text = @"验证码格式不正确";
            self.codeError = @"验证码格式不正确";
            self.codeLineView.backgroundColor = [UIColor stc2Color];
            [self.codeTextField becomeFirstResponder];
            return;
            
        }
        self.codeError = @"";
         self.codeLineView.backgroundColor = [UIColor tc3Color];
    }else{
        if(![SpecialRegexKit validateSpecialPassword:self.viewModel.password]){
            self.tipLabel.text = @"密码(长度6-16位，字母与数字组合)";
            self.pwdError = @"密码(长度6-16位，字母与数字组合)";
            self.codeLineView.backgroundColor = [UIColor stc2Color];
            [self.pwdTextField becomeFirstResponder];
            return;
            
        }
        self.pwdError = @"";
    }
   
    //[self resetTip];
    self.tipLabel.text = @"";
   
   
    self.pwdLineView.backgroundColor = [UIColor tc3Color];
    [self.viewModel userLogin:^{
        if([UserManager manager].first_login == YES ){
            SCSetPwdViewController * setVC = [SCSetPwdViewController new];
            setVC.viewType = SetPopType;
            setVC.code = self.viewModel.code;
            setVC.phone = self.viewModel.mobile;
            setVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:setVC animated:YES];
        }else{
             [weakSelf dismissViewControllerAnimated:YES completion:nil];
        }
        
    }];

}
#pragma mark - 重置页面线条颜色
- (void)resetTip{

    self.tipLabel.text = @"";
    self.mobileLineView.backgroundColor = [UIColor tc3Color];
    self.codeLineView.backgroundColor = [UIColor tc3Color];
    self.pwdLineView.backgroundColor = [UIColor tc3Color];
    self.mobielError = @"";
    self.pwdError = @"";
    self.codeError = @"";
}


#pragma mark - 发送登录验证码
//mobile|手机号码|string
//type|短信类型|integer|`0`:默认, `2`:手机动态码登录, `3`:重置密码, `4`:更换手机新手机验证码    `5` 预约

- (void)getVeryCodeFunction:(id)sender{
    if (![RegexKit validateMobile:self.viewModel.mobile]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"请输入正确的11位手机号码";
        self.mobileLineView.backgroundColor = [UIColor stc2Color];
        [self.pwdTextField becomeFirstResponder];
        return;
    }
    self.tipLabel.text = @"";
    self.mobileLineView.backgroundColor = [UIColor tc3Color];
    NSDictionary *params = @{
                             @"mobile" : self.viewModel.mobile,
                             @"type"   : [NSNumber numberWithInt: 1]
                             };
    [LoadingView showHudIndeterminateInView: self.view];
    [[UserService service] userSendSMS: params complete:^{
        [LoadingView hideLoadinForView: self.view];
        [[CountDownManager manager] countdownTime: CD_COUNTDOWN_LOGIN];
    } failure:^{
        [LoadingView hideLoadinForView: self.view];
    }];

}
- (void)showPwdFunction:(id)sender{
    
    self.pwdTextField.secureTextEntry = !self.pwdTextField.secureTextEntry;
    
    
}
- (void)textFieldDidBeginEditing:(UITextField *)textField{
    if ([textField isEqual:self.mobileTextField]) {
        if ([self.mobielError length]) {
            self.mobileLineView.backgroundColor = [UIColor stc2Color];
        }else{
           self.mobileLineView.backgroundColor = [UIColor tc5Color];
        }
      
        
    }else{
        self.mobileLineView.backgroundColor = [UIColor tc3Color];
    }
    
    if ([textField isEqual:self.pwdTextField]) {
        if ([self.pwdError length]) {
            self.pwdLineView.backgroundColor = [UIColor stc2Color];
        }else{
            self.pwdLineView.backgroundColor = [UIColor tc5Color];
        }
   
        
    }else{
        self.pwdLineView.backgroundColor = [UIColor tc3Color];
       
    }
    if ([textField isEqual:self.codeTextField]) {
        if ([self.codeError length]) {
            self.codeLineView.backgroundColor = [UIColor stc2Color];
        }else{
            self.codeLineView.backgroundColor = [UIColor tc5Color];
        }
       
    }else{
        self.codeLineView.backgroundColor = [UIColor tc3Color];
        
    }
}
- (void)viewWillAppear:(BOOL)animated
{

    [super viewWillAppear:animated];
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
      self.navigationController.navigationBarHidden = YES;
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
     [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleDefault];
//      self.navigationController.navigationBarHidden = NO;
}

-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    self.navigationController.navigationBarHidden = NO;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
