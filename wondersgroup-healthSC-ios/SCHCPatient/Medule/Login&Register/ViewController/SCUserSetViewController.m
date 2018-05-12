//
//  SCUserSetViewController.m
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCUserSetViewController.h"
#import "SCUserViewModel.h"
#import "SCBindingIphoneViewController.h"
@interface SCUserSetViewController ()
@property (nonatomic,strong) UITextField    *mobileTextField;

@property (nonatomic,strong) UITextField    *codeTextField;

@property (nonatomic,strong)UITextField *   pwdTextField;

@property (nonatomic,strong)UITextField *   rePwdTextField;

@property (nonatomic,strong) UIButton       *sendCodeButon;

@property (nonatomic,strong) UIButton       *finishButton;

@property (nonatomic,strong)UIView * moblieLine;

@property (nonatomic,strong)UIView * codeLine;

@property (nonatomic,strong)UIView * pwdLine;

@property (nonatomic,strong)UIView * rePwdLine;

@property (nonatomic,strong)UILabel * tipLabel;

@property (nonatomic,copy)NSString * tipText;

@property (nonatomic,strong)SCUserViewModel * viewModel;

//显示密码
@property (nonatomic,strong)UIButton * showPwdButton;

//显示密码
@property (nonatomic,strong)UIButton * showRePwdButton;
@end

@implementation SCUserSetViewController

- (SCUserViewModel * )viewModel{
    if (!_viewModel) {
        _viewModel = [SCUserViewModel new];
    }
    return _viewModel;
}

- (UITextField *)mobileTextField{
    if (!_mobileTextField) {
        _mobileTextField = [UITextField new];
        _mobileTextField.font = [UIFont systemFontOfSize:14.];
        _mobileTextField.borderStyle = UITextBorderStyleNone;
        _mobileTextField.placeholder = @"手机号";
        _mobileTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _mobileTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
        _mobileTextField.userInteractionEnabled = NO;
    }
    return _mobileTextField;
}

- (UITextField *)rePwdTextField{
    if (!_rePwdTextField) {
        _rePwdTextField = [UITextField new];
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            _rePwdTextField.font = [UIFont systemFontOfSize:13.];
        }else{
            _rePwdTextField.font = [UIFont systemFontOfSize:14.];
        }
        
        _rePwdTextField.borderStyle = UITextBorderStyleNone;
        _rePwdTextField.secureTextEntry = YES;
        _rePwdTextField.placeholder = @"长度6-16位,字母与数字组合";
        //_rePwdTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _rePwdTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _rePwdTextField;
}
- (UITextField *)pwdTextField{
    if (!_pwdTextField) {
        _pwdTextField = [UITextField new];
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            _pwdTextField.font = [UIFont systemFontOfSize:13.];
        }else{
            _pwdTextField.font = [UIFont systemFontOfSize:14.];
        }
        _pwdTextField.borderStyle = UITextBorderStyleNone;
        _pwdTextField.secureTextEntry = YES;
        _pwdTextField.placeholder = @"长度6-16位,字母与数字组合";
        //_pwdTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _pwdTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _pwdTextField;
}
- (UITextField *)codeTextField{
    if (!_codeTextField) {
        _codeTextField = [UITextField new];
        _codeTextField.font = [UIFont systemFontOfSize:14.];
        _codeTextField.borderStyle = UITextBorderStyleNone;
        //_codeTextField.placeholder = @"验证码";
        
        _codeTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _codeTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _codeTextField;
}
- (UIButton *)finishButton{
    if (!_finishButton) {
        _finishButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_finishButton setTitle:@"完成" forState:UIControlStateNormal];
        [_finishButton.titleLabel setFont: [UIFont systemFontOfSize:16.]];
        
        [_finishButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
         [_finishButton setBackgroundColor:[UIColor tc5Color]];
        [_finishButton addTarget:self action:@selector(finishFuntion:) forControlEvents:UIControlEventTouchUpInside];
        _finishButton.layer.cornerRadius = 5;
        _finishButton.layer.borderColor = [UIColor tc5Color].CGColor;
        _finishButton.layer.borderWidth = 0.5;
        _finishButton.clipsToBounds = YES;
    }
    return _finishButton;
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
- (UILabel *)tipLabel{
    if (!_tipLabel) {
        _tipLabel = [UILabel new];
        _tipLabel.font = [UIFont systemFontOfSize:12.];
        _tipLabel.textColor = [UIColor stc2Color];
        
    }
    return _tipLabel;
}
- (UIButton *)showPwdButton{
    if (!_showPwdButton) {
        _showPwdButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_showPwdButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        //[_showPwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateSelected];
       
        [_showPwdButton addTarget:self action:@selector(showPwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _showPwdButton;
    
}
- (UIButton *)showRePwdButton{
    if (!_showRePwdButton) {
        _showRePwdButton = [UIButton buttonWithType:UIButtonTypeCustom];
        // [_showPwdButton setImage:[UIImage imageNamed:@"ico_unsee"] forState:UIControlStateNormal];
        [_showRePwdButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        // [_showRePwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateSelected];
        
        [_showRePwdButton addTarget:self action:@selector(showRePwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _showRePwdButton;
    
}
- (void)viewDidLoad {
    
    [super viewDidLoad];
    self.navigationItem.title = @"设置密码";
    //self.view.backgroundColor = [UIColor whiteColor];
    [self setupView];
    [self bindViewModel];
    // Do any additional setup after loading the view.
}
- (void)setupView{
    WS(weakSelf)
    UIView *inputView = [[UIView alloc] init];
    inputView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview: inputView];
    [inputView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(0);
        make.right.equalTo(weakSelf.view).offset(0);
        
        make.top.equalTo(weakSelf.view.mas_top).offset(10);
        make.height.mas_equalTo(180.0);
    }];
    // [[Global global]addBottomLineInView:inputView withColor:[UIColor dc1Color]];
    [inputView addSubview: self.mobileTextField];
    UILabel * phoneLabel = [UISetupView setupLabelWithSuperView:inputView withText:@"手机号" withTextColor:[UIColor tc1Color] withFontSize:14.];
    [phoneLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view.mas_left).offset(15.);
        make.top.equalTo(inputView).offset(10);
        make.width.mas_equalTo(60.);
        make.height.mas_equalTo(20.0);
    }];
    [self.mobileTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(phoneLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
             make.left.equalTo(phoneLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(inputView);
        make.height.mas_equalTo(44.0);
    }];
    UILabel * codeLabel = [UISetupView setupLabelWithSuperView:inputView withText:@"验证码" withTextColor:[UIColor tc1Color] withFontSize:14.];
    [codeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15.);
        make.top.equalTo(weakSelf.mobileTextField.mas_bottom).offset(10);
        make.width.mas_equalTo(60.);
        make.height.mas_equalTo(20.0);
    }];
    [inputView addSubview: self.codeTextField];
    [self.codeTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(codeLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-115.);
        }else{
            make.left.equalTo(codeLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-125.);
        }
        make.top.equalTo(weakSelf.mobileTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(44.0);
    }];
    
    
    [inputView addSubview: self.sendCodeButon];
    [self.sendCodeButon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.view).offset(-15);
        make.centerY.equalTo(weakSelf.codeTextField);
        make.width.mas_equalTo(75.0);
        make.height.mas_equalTo(44.0);
    }];
    
    UIView *toplineView = [UISetupView setupLineViewWithSuperView: inputView];
    [toplineView mas_makeConstraints:^(MASConstraintMaker *make) {
       
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15.);
       
        make.top.equalTo(weakSelf.mobileTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.moblieLine = toplineView;
    UIView *secondlineView = [UISetupView setupLineViewWithSuperView: inputView];
    [secondlineView mas_makeConstraints:^(MASConstraintMaker *make) {
       
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15.);
       
        make.top.equalTo(weakSelf.codeTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    UILabel * pwdLabel = [UISetupView setupLabelWithSuperView:inputView withText:@"输入密码" withTextColor:[UIColor tc1Color] withFontSize:14.];
    [pwdLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15.);
        make.top.equalTo(secondlineView.mas_bottom).offset(10);
        make.width.mas_equalTo(60.);
        make.height.mas_equalTo(20.0);
    }];
    [inputView addSubview: self.pwdTextField];
    [self.pwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
           make.left.equalTo(pwdLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }else{
          make.left.equalTo(pwdLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        
        make.top.equalTo(weakSelf.codeTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(44.0);
    }];
    self.codeLine = secondlineView;
    
    [inputView addSubview: self.showPwdButton];
    [self.showPwdButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.right.equalTo(weakSelf.view).offset(-15);
        }else{
            make.right.equalTo(weakSelf.view).offset(-15);
        }
        make.centerY.equalTo(weakSelf.pwdTextField);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];
    UIView *thirdLineView = [UISetupView setupLineViewWithSuperView: inputView];
    [thirdLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15.);
        make.top.equalTo(weakSelf.pwdTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.pwdLine = thirdLineView;
    UILabel * rePwdLabel = [UISetupView setupLabelWithSuperView:inputView withText:@"确认密码" withTextColor:[UIColor tc1Color] withFontSize:14.];
    [rePwdLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15.);
        make.top.equalTo(thirdLineView.mas_bottom).offset(10);
        make.width.mas_equalTo(60.);
        make.height.mas_equalTo(20.0);
    }];
    [inputView addSubview: self.rePwdTextField];
    [self.rePwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
           make.left.equalTo(rePwdLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }else{
            make.left.equalTo(rePwdLabel.mas_right).offset(15);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        
        make.top.equalTo(weakSelf.pwdTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(44.0);
    }];
    [inputView addSubview: self.showRePwdButton];
    [self.showRePwdButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.right.equalTo(weakSelf.view).offset(-15);
        }else{
            make.right.equalTo(weakSelf.view).offset(-15);
        }
        make.centerY.equalTo(weakSelf.rePwdTextField);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];
    
    UIView *fouthLineView = [UISetupView setupLineViewWithSuperView: inputView];
    [fouthLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15.);
        make.top.equalTo(weakSelf.rePwdTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.rePwdLine = fouthLineView;
    self.rePwdLine.hidden = YES;
    [self.view addSubview:self.tipLabel];
    [self.tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15.);
        make.top.equalTo(weakSelf.rePwdLine.mas_bottom).offset(5);
        make.height.mas_equalTo(30.);
    }];
    
    
    [self.view addSubview: self.finishButton];
    [self.finishButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15.);
        make.top.mas_equalTo(inputView.mas_bottom).offset(67.0);
        make.height.mas_equalTo(@44.);
    }];
    
    
    
}
#pragma mark - bindViewModel

- (void)bindViewModel{
    WS(weakSelf)
    NSString *phone = [UserManager manager].accountName;
    self.mobileTextField.text= [NSString stringWithFormat:@"%@****%@", [phone substringToIndex:3], [phone substringFromIndex:7]];
    self.viewModel.phone = [UserManager manager].accountName;
    //RAC(self.viewModel, phone) = self.mobileTextField.rac_textSignal;
    RAC(self.viewModel, code)   = self.codeTextField.rac_textSignal;
    RAC(self.viewModel, password)   = self.pwdTextField.rac_textSignal;
    [RACObserve([CountDownManager manager], countDownPWD) subscribeNext:^(NSNumber *countdown) {
        if ([countdown intValue] <= 0) {
            
            [weakSelf.sendCodeButon setTitle:@"获取验证码" forState:UIControlStateNormal];
            weakSelf.sendCodeButon.enabled = YES;
        }
        else {
            [weakSelf.sendCodeButon setTitle:[NSString stringWithFormat:@"       %dS", [countdown intValue]] forState:UIControlStateDisabled];
            weakSelf.sendCodeButon.enabled = NO;
        }
    }];
    [self.rePwdTextField.rac_textSignal subscribeNext:^(NSString * text) {
        weakSelf.showRePwdButton.hidden = text.length ? NO : YES;
    }];
    [RACObserve(self.pwdTextField,secureTextEntry)subscribeNext:^(NSNumber * secure) {
        if (weakSelf.pwdTextField.secureTextEntry) {
            [weakSelf.showPwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
        }else{
            [weakSelf.showPwdButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        }
    }];
    
    [RACObserve(self.rePwdTextField,secureTextEntry)subscribeNext:^(NSNumber * secure) {
        if (weakSelf.rePwdTextField.secureTextEntry) {
            [weakSelf.showRePwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
        }else{
            [weakSelf.showRePwdButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        }
    }];
    [self.pwdTextField.rac_textSignal subscribeNext:^(NSString * text) {
        weakSelf.showPwdButton.hidden = text.length ? NO : YES;
    }];
    /*
    RAC(self.finishButton, enabled) = [RACSignal
                                       combineLatest:@[self.mobileTextField.rac_textSignal , self.codeTextField.rac_textSignal,self.pwdTextField.rac_textSignal,self.rePwdTextField.rac_textSignal]reduce:^(NSString *mobile, NSString *code,NSString * pwd,NSString * rePwd){
                                           return @(( [mobile length] && [code length]&& [pwd length] && [rePwd length] > 0));
                                       }];
    [RACObserve(self.finishButton, enabled)subscribeNext:^(NSNumber * enable) {
        if ([enable boolValue]) {
            weakSelf.finishButton.layer.borderColor = [UIColor tc0Color].CGColor;
            [weakSelf.finishButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        }else{
            weakSelf.finishButton.layer.borderColor = [UIColor tc0Color].CGColor;
            [weakSelf.finishButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        }
    }];
     */
}
#pragma mark - 获取验证码
- (void)getVeryCodeFunction:(id)sender{
    
    //self.viewModel.phone = self.mobileTextField.text;
    self.tipLabel.text = @"";
    self.moblieLine.backgroundColor = [UIColor dc1Color];
    self.viewModel.codeType = VerifyCodeTypeSetPassword;
    [self.viewModel getVerifyCode:^{
        [[CountDownManager manager] countdownTime: CD_COUNTDOWN_PWD];
    } failure:^{
        
    }];
    
}
#pragma mark - 完成方法
- (void)finishFuntion:(id)sender{
    WS(weakSelf)
    if (self.codeTextField.text.length == 0) {
        self.tipLabel.text = @"请输入验证码";
        [self.codeTextField becomeFirstResponder];
        return;
    }
    if (![RegexKit validateDynamicCode:self.codeTextField.text]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"验证码格式错误";
        [self.codeTextField becomeFirstResponder];
        return;
    }
    if (![SpecialRegexKit validateSpecialPassword:self.viewModel.password]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"密码(长度6-16位，字母与数字组合)";
       // self.pwdLine.backgroundColor = [UIColor stc2Color];
        [self.pwdTextField becomeFirstResponder];
        return;
    }
    // self.pwdLine.backgroundColor = [UIColor dc1Color];
    if (![self.pwdTextField.text isEqualToString:self.rePwdTextField.text]) {
        // [MBProgressHUDHelper showHudWithText:@"两次密码输入不一致"];
        self.tipLabel.text  = @"两次密码输入不一致";
       // self.rePwdLine.backgroundColor = [UIColor stc2Color];
        [self.rePwdTextField becomeFirstResponder];
        return;
    }
    self.tipLabel.text = @"";
    //self.rePwdLine.backgroundColor = [UIColor dc1Color];
    [self.viewModel setPassword:^{
        [UserManager manager].password_complete = YES;
        [weakSelf.navigationController popViewControllerAnimated:YES];
    } failure:^{
        
    }];
    
}

- (void)showPwdFunction:(id)sender{
    
    self.pwdTextField.secureTextEntry = !self.pwdTextField.secureTextEntry;
    
    
}
- (void)showRePwdFunction:(id)sender{
    
    self.rePwdTextField.secureTextEntry = !self.rePwdTextField.secureTextEntry;
    
    
}
- (void)viewWillAppear:(BOOL)animated
{
    
    [super viewWillAppear:animated];
   
    
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
 
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
