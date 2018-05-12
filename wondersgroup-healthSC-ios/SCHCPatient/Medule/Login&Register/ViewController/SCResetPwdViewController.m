//
//  SCResetPwdViewController.m
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCResetPwdViewController.h"
#import "SCUserViewModel.h"

@interface SCResetPwdViewController ()<UITextFieldDelegate>
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
@property (nonatomic,copy)NSString * mobileError;
@property (nonatomic,copy)NSString * codeError;
@property (nonatomic,copy)NSString * pwdError;
@property (nonatomic,copy)NSString * repwdError;
@property (nonatomic,strong)SCUserViewModel * viewModel;

//显示密码
@property (nonatomic,strong)UIButton * showPwdButton;

//显示密码
@property (nonatomic,strong)UIButton * showRePwdButton;

@end

@implementation SCResetPwdViewController

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
        _mobileTextField.placeholder = @"手机号码";
         _mobileTextField.delegate = self;
        _mobileTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _mobileTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
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
         _rePwdTextField.delegate = self;
        _rePwdTextField.placeholder = @"确认密码(长度6-16位,字母与数字组合)";
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
        _pwdTextField.delegate = self;
        _pwdTextField.placeholder = @"密码(长度6-16位,字母与数字组合)";
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
        _codeTextField.placeholder = @"验证码";
        _codeTextField.delegate = self;
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
        
        [_finishButton setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
        [_finishButton addTarget:self action:@selector(finishFuntion:) forControlEvents:UIControlEventTouchUpInside];
        _finishButton.layer.cornerRadius = 5;
        _finishButton.layer.borderColor = [UIColor tc3Color].CGColor;
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
        _tipLabel.font = [UIFont systemFontOfSize:14.];
        _tipLabel.textColor = [UIColor stc2Color];
        
    }
    return _tipLabel;
}
- (UIButton *)showPwdButton{
    if (!_showPwdButton) {
        _showPwdButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_showPwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
       
        [_showPwdButton addTarget:self action:@selector(showPwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _showPwdButton;
    
}
- (UIButton *)showRePwdButton{
    if (!_showRePwdButton) {
        _showRePwdButton = [UIButton buttonWithType:UIButtonTypeCustom];

        [_showRePwdButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
   
       
        [_showRePwdButton addTarget:self action:@selector(showRePwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _showRePwdButton;
    
}
- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title = @"重置密码";
    self.view.backgroundColor = [UIColor whiteColor];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"login_back"] style:UIBarButtonItemStyleDone target:self action:@selector(popBack)];
    [self setupView];
    [self bindViewModel];
   
    // Do any additional setup after loading the view.
}
- (void)setupView{
    WS(weakSelf)
    UIView *inputView = [[UIView alloc] init];
    [self.view addSubview: inputView];
    [inputView mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        
        make.top.equalTo(weakSelf.view).offset(40);
        make.height.mas_equalTo(210.0);
    }];
   // [[Global global]addBottomLineInView:inputView withColor:[UIColor tc3Color]];
    [inputView addSubview: self.mobileTextField];
    [self.mobileTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(inputView);
        make.height.mas_equalTo(44.0);
    }];
    UIView *toplineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [toplineView mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(weakSelf.mobileTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.moblieLine = toplineView;
    [inputView addSubview: self.codeTextField];
    [self.codeTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-115.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-125.);
        }
        make.top.equalTo(weakSelf.moblieLine.mas_bottom);
        make.height.mas_equalTo(44.0);
    }];
    
    
    [inputView addSubview: self.sendCodeButon];
    [self.sendCodeButon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.view).offset(-45);
        make.centerY.equalTo(weakSelf.codeTextField);
        make.width.mas_equalTo(75.0);
        make.height.mas_equalTo(44.0);
    }];

  
    UIView *secondlineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [secondlineView mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(weakSelf.codeTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.codeLine = secondlineView;
    [inputView addSubview: self.pwdTextField];
    [self.pwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-55.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-65.);
        }
     
        make.top.equalTo(weakSelf.codeLine.mas_bottom);
        make.height.mas_equalTo(44.0);
    }];
    
    
    [inputView addSubview: self.showPwdButton];
    [self.showPwdButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.right.equalTo(weakSelf.view).offset(-25);
        }else{
            make.right.equalTo(weakSelf.view).offset(-35);
        }
        make.centerY.equalTo(weakSelf.pwdTextField);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];
    UIView *thirdLineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [thirdLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(weakSelf.pwdTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
     self.pwdLine = thirdLineView;
    [inputView addSubview: self.rePwdTextField];
    [self.rePwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-55.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-65.);
        }
        
        make.top.equalTo(weakSelf.pwdLine.mas_bottom);
        make.height.mas_equalTo(44.0);
    }];
    [inputView addSubview: self.showRePwdButton];
    [self.showRePwdButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
           make.right.equalTo(weakSelf.view).offset(-25);
        }else{
            make.right.equalTo(weakSelf.view).offset(-35);
        }
        make.centerY.equalTo(weakSelf.rePwdTextField);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];

    UIView *fouthLineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [fouthLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(weakSelf.rePwdTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.rePwdLine = fouthLineView;
    [inputView addSubview:self.tipLabel];
    [self.tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(weakSelf.rePwdLine.mas_bottom).offset(5);
        make.height.mas_equalTo(30.);
    }];
   

    [self.view addSubview: self.finishButton];
    [self.finishButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.mas_equalTo(inputView.mas_bottom).offset(30.0);
        make.height.mas_equalTo(@44.);
    }];
    


}
#pragma mark - bindViewModel

- (void)bindViewModel{
    WS(weakSelf)
    RAC(self.viewModel, phone) = self.mobileTextField.rac_textSignal;
    RAC(self.viewModel, code)   = self.codeTextField.rac_textSignal;
    RAC(self.viewModel, password)   = self.pwdTextField.rac_textSignal;
   
    [self.mobileTextField.rac_textSignal subscribeNext:^(NSString * text) {
        if ([text length]) {
             [weakSelf.sendCodeButon setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
           
        }else{
             [weakSelf.sendCodeButon setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
          
        }
    }];
    RAC(self.sendCodeButon, enabled) = [RACSignal combineLatest:@[self.mobileTextField.rac_textSignal,RACObserve([CountDownManager manager], countDownForget)] reduce:^(NSString * text,NSNumber *countdown){
        if ([countdown intValue] <= 0 && [text length]){
            return @YES;
        }else{
            return @NO;
        }
        
        
    }];
    [RACObserve([CountDownManager manager], countDownForget) subscribeNext:^(NSNumber *countdown) {
        if ([countdown intValue] <= 0) {
            
            [weakSelf.sendCodeButon setTitle:@"获取验证码" forState:UIControlStateNormal];
            
        }
        else {
            [weakSelf.sendCodeButon setTitle:[NSString stringWithFormat:@"       %dS", [countdown intValue]] forState:UIControlStateNormal];
            
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
    RAC(self.finishButton, enabled) = [RACSignal
                                       combineLatest:@[self.mobileTextField.rac_textSignal , self.codeTextField.rac_textSignal,self.pwdTextField.rac_textSignal,self.rePwdTextField.rac_textSignal]reduce:^(NSString *mobile, NSString *code,NSString * pwd,NSString * rePwd){
                                           return @(( [mobile length] && [code length]&& [pwd length] && [rePwd length] > 0));
                                       }];
    [RACObserve(self.finishButton, enabled)subscribeNext:^(NSNumber * enable) {
        if ([enable boolValue]) {
            weakSelf.finishButton.layer.borderColor = [UIColor tc5Color].CGColor;
             [weakSelf.finishButton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        }else{
            weakSelf.finishButton.layer.borderColor = [UIColor tc3Color].CGColor;
            [weakSelf.finishButton setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
        }
    }];
}
#pragma mark - 获取验证码
- (void)getVeryCodeFunction:(id)sender{
    
    if (![RegexKit validateMobile:self.mobileTextField.text]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"请输入正确的11位手机号码";
        self.moblieLine.backgroundColor = [UIColor stc2Color];
        [self.mobileTextField becomeFirstResponder];
        return;
    }
    self.viewModel.phone = self.mobileTextField.text;
    self.tipLabel.text = @"";
    self.moblieLine.backgroundColor = [UIColor tc3Color];
    self.viewModel.codeType = VerifyCodeTypeForgetPassword;
    [self.viewModel getVerifyCode:^{
         [[CountDownManager manager] countdownTime: CD_COUNTDOWN_FORGET];
    } failure:^{
        
    }];
    
}

#pragma mark - 完成方法
- (void)finishFuntion:(id)sender{
    WS(weakSelf)
    if (![RegexKit validateMobile:self.mobileTextField.text]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"请输入正确的11位手机号码";
        self.mobileError = @"请输入正确的11位手机号码";
        self.moblieLine.backgroundColor = [UIColor stc2Color];
        [self.mobileTextField becomeFirstResponder];
        return;
    }
    self.mobileError = @"";
     self.moblieLine.backgroundColor = [UIColor tc3Color];
    if (![RegexKit validateDynamicCode:self.codeTextField.text]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"验证码格式错误";
        self.codeError = @"验证码格式错误";
        self.codeLine.backgroundColor = [UIColor stc2Color];
        [self.codeTextField becomeFirstResponder];
        return;
    }
    self.codeError = @"";
    self.codeLine.backgroundColor = [UIColor tc3Color];
    if (![SpecialRegexKit validateSpecialPassword:self.viewModel.password]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"密码(长度6-16位，字母与数字组合)";
        self.pwdError = @"密码(长度6-16位，字母与数字组合)";
        self.pwdLine.backgroundColor = [UIColor stc2Color];
        [self.pwdTextField becomeFirstResponder];
        return;
    }
    self.pwdError = @"";
   self.pwdLine.backgroundColor = [UIColor tc3Color];
    if (![self.pwdTextField.text isEqualToString:self.rePwdTextField.text]) {
       // [MBProgressHUDHelper showHudWithText:@"两次密码输入不一致"];
        self.tipLabel.text  = @"两次密码输入不一致";
          self.repwdError = @"两次密码输入不一致";
         self.rePwdLine.backgroundColor = [UIColor stc2Color];
        [self.rePwdTextField becomeFirstResponder];
        return;
    }
    self.tipLabel.text = @"";
    
    self.repwdError = @"";
    
    
    
     self.rePwdLine.backgroundColor = [UIColor tc3Color];
    [self.viewModel setPassword:^{
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
- (void)textFieldDidBeginEditing:(UITextField *)textField{
    if ([textField isEqual:self.mobileTextField]) {
        if ([self.mobileError length]) {
            self.moblieLine.backgroundColor = [UIColor stc2Color];
        }else{
            self.moblieLine.backgroundColor = [UIColor tc5Color];
        }
        
        
    }else{
        self.moblieLine.backgroundColor = [UIColor tc3Color];
        
    }
    if ([textField isEqual:self.pwdTextField]) {
        if ([self.pwdError length]) {
            self.pwdLine.backgroundColor = [UIColor stc2Color];
        }else{
            self.pwdLine.backgroundColor = [UIColor tc5Color];
        }
        
        
    }else{
        self.pwdLine.backgroundColor = [UIColor tc3Color];
        
    }
    
    if ([textField isEqual:self.codeTextField]) {
        if ([self.codeError length]) {
            self.codeLine.backgroundColor = [UIColor stc2Color];
        }else{
            self.codeLine.backgroundColor = [UIColor tc5Color];
        }
        
        
    }else{
        self.codeLine.backgroundColor = [UIColor tc3Color];
        
    }
    if ([textField isEqual:self.pwdTextField]) {
        if ([self.pwdError length]) {
            self.pwdLine.backgroundColor = [UIColor stc2Color];
        }else{
            self.pwdLine.backgroundColor = [UIColor tc5Color];
        }
        
        
    }else{
        self.pwdLine.backgroundColor = [UIColor tc3Color];
        
    }
    if ([textField isEqual:self.rePwdTextField]) {
        if ([self.repwdError length]) {
            self.rePwdLine.backgroundColor = [UIColor stc2Color];
        }else{
            self.rePwdLine.backgroundColor = [UIColor tc5Color];
        }
        
        
    }else{
        self.rePwdLine.backgroundColor = [UIColor tc3Color];
        
    }

}
- (void)viewWillAppear:(BOOL)animated
{
    
    [super viewWillAppear:animated];
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
    
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleDefault];
    
}
- (void)popBack{

    [self.navigationController popViewControllerAnimated:YES];
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
