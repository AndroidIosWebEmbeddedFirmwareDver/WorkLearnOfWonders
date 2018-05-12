//
//  SCSetPwdViewController.m
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSetPwdViewController.h"
#import "SCUserViewModel.h"
#import "SCBindingIphoneViewController.h"
@interface SCSetPwdViewController ()<UITextFieldDelegate>
@property (nonatomic,strong) UITextField    *pwdTextField;

@property (nonatomic,strong) UITextField    *rePwdTextField;

//显示密码
@property (nonatomic,strong)UIButton * showPwdButton;
//显示密码
@property (nonatomic,strong)UIButton * showRePwdButton;

@property (nonatomic,strong) UIButton       *finishButton;


@property (nonatomic,strong)UILabel * tipLabel;

@property (nonatomic,strong)SCUserViewModel * viewModel;


@property (nonatomic,strong)UIView * pwdLineView;
@property (nonatomic,strong)UIView * rePwdLineView;

@property (nonatomic,copy)NSString * pwdError;
@property (nonatomic,copy)NSString * rePwdError;
@end

@implementation SCSetPwdViewController
- (UITextField *)pwdTextField{
    if (!_pwdTextField) {
        _pwdTextField = [UITextField new];
        _pwdTextField.font = [UIFont systemFontOfSize:14];
        _pwdTextField.borderStyle = UITextBorderStyleNone;
        _pwdTextField.secureTextEntry = YES;
         _pwdTextField.delegate = self;
        _pwdTextField.placeholder = @"密码(长度6-16位,字母与数字组合)";
       // _pwdTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _pwdTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _pwdTextField;
}
- (UITextField *)rePwdTextField{
    if (!_rePwdTextField) {
        _rePwdTextField = [UITextField new];
        _rePwdTextField.borderStyle = UITextBorderStyleNone;
        _rePwdTextField.font = [UIFont systemFontOfSize:14];
        _rePwdTextField.placeholder = @"确认密码";
        _rePwdTextField.secureTextEntry = YES;
        _rePwdTextField.delegate = self;
        //_rePwdTextField.keyboardType    = UIKeyboardTypeNumberPad;
        _rePwdTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }
    return _rePwdTextField;
}
- (UIButton *)finishButton{
    if (!_finishButton) {
        _finishButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_finishButton setTitle:@"完成" forState:UIControlStateNormal];
        [_finishButton.titleLabel setFont: [UIFont systemFontOfSize:16.]];

        [_finishButton setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];

        [_finishButton addTarget:self action:@selector(finishFuntion1:) forControlEvents:UIControlEventTouchUpInside];
        _finishButton.layer.cornerRadius = 5;
        _finishButton.layer.borderColor = [UIColor tc3Color].CGColor;
        _finishButton.layer.borderWidth = 0.5;
        _finishButton.clipsToBounds = YES;
    }
    return _finishButton;
}
- (UIButton *)showPwdButton{
    if (!_showPwdButton) {
        _showPwdButton = [UIButton buttonWithType:UIButtonTypeCustom];
       // [_showPwdButton setImage:[UIImage imageNamed:@"ico_unsee"] forState:UIControlStateNormal];
        [_showPwdButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        //[_showPwdButton setImage:
        
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
- (UILabel *)tipLabel{
    if (!_tipLabel) {
        _tipLabel = [UILabel new];
        _tipLabel.font = [UIFont systemFontOfSize:12.];
        _tipLabel.textColor = [UIColor stc2Color];
        }
    return _tipLabel;
}

- (SCUserViewModel *)viewModel{

    if (!_viewModel) {
        _viewModel = [SCUserViewModel new];
    }
    return _viewModel;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title = @"设置密码";
    self.navigationItem.leftBarButtonItem = nil;
    self.navigationItem.hidesBackButton=YES;
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"跳过" style:UIBarButtonItemStylePlain target:self action:@selector(skipFunction:)];
    self.view.backgroundColor = [UIColor whiteColor];
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
        make.top.equalTo(weakSelf.view).offset(20);
        make.height.mas_equalTo(135.0);
    }];
    
    [inputView addSubview: self.pwdTextField];
    [self.pwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-55.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-65.);
        };
        make.top.equalTo(inputView);
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
    UIView *toplineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    [toplineView mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        };
        make.top.equalTo(weakSelf.pwdTextField.mas_bottom).offset(0.5);
        make.height.mas_equalTo(0.5);
    }];
    self.pwdLineView = toplineView;
    [inputView addSubview: self.rePwdTextField];
    [self.rePwdTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-55.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-65.);
        }
        
         make.top.equalTo(weakSelf.pwdLineView.mas_bottom);
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

   
    UIView *secondlineView = [UISetupView setupLineViewWithSuperView: inputView color:[UIColor tc3Color]];
    self.rePwdLineView = secondlineView;
    [secondlineView mas_makeConstraints:^(MASConstraintMaker *make) {
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
    [inputView addSubview:self.tipLabel];
    [self.tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        if (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS){
            make.left.equalTo(weakSelf.view).offset(35);
            make.right.equalTo(weakSelf.view).offset(-35.);
        }else{
            make.left.equalTo(weakSelf.view).offset(45);
            make.right.equalTo(weakSelf.view).offset(-45.);
        }
        make.top.equalTo(secondlineView.mas_bottom).offset(5);
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
    self.viewModel.phone  = [UserManager manager].accountName;
     RAC(self.viewModel, password)   = self.pwdTextField.rac_textSignal;
    [RACObserve(self.finishButton, enabled)subscribeNext:^(NSNumber * enable) {
        if ([enable boolValue]) {
            weakSelf.finishButton.layer.borderColor = [UIColor tc5Color].CGColor;
            [weakSelf.finishButton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        }else{
            weakSelf.finishButton.layer.borderColor = [UIColor tc3Color].CGColor;
            [weakSelf.finishButton setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
        }
    }];
    [self.pwdTextField.rac_textSignal subscribeNext:^(NSString * text) {
        weakSelf.showPwdButton.hidden = text.length ? NO : YES;
    }];
    [self.rePwdTextField.rac_textSignal subscribeNext:^(NSString * text) {
        self.showRePwdButton.hidden = text.length ? NO : YES;
    }];
    RAC(self.finishButton,enabled) = [RACSignal combineLatest:@[self.pwdTextField.rac_textSignal, self.rePwdTextField.rac_textSignal] reduce:^id(NSString *pwd, NSString *rePwd){
        return @([pwd length] > 0 && ([rePwd length] >0));
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
}

#pragma mark - 完成方法
- (void)finishFuntion1:(id)sender{
    WS(weakSelf)
    if (![SpecialRegexKit validateSpecialPassword:self.pwdTextField.text]) {
        
        //[MBProgressHUDHelper showHudWithText:@"请输入有效的手机号"];
        self.tipLabel.text = @"密码(长度6-16位，字母与数字组合)";
        self.pwdError = @"密码(长度6-16位，字母与数字组合)";
        self.pwdLineView.backgroundColor = [UIColor stc2Color];
        [self.pwdTextField becomeFirstResponder];
        return;
    }
    self.pwdError = @"";
    self.pwdLineView.backgroundColor = [UIColor tc3Color];
    if (![self.pwdTextField.text isEqualToString:self.rePwdTextField.text]) {
         self.tipLabel.text = @"两次密码输入不一致";
        self.rePwdError = @"两次密码输入不一致";
        self.rePwdLineView.backgroundColor = [UIColor stc2Color];
        [self.rePwdTextField becomeFirstResponder];
        return;
    }
    self.tipLabel.text = @"";
  
    self.rePwdError = @"";
    self.rePwdLineView.backgroundColor = [UIColor tc3Color];
    self.viewModel.phone = self.phone;
    self.viewModel.code  = self.code;
    [self.viewModel setPassword:^{
        if(weakSelf.viewType   == SetPopType){
            
            [weakSelf dismissViewControllerAnimated:YES completion:nil];
        }else{
          [weakSelf.navigationController popViewControllerAnimated:YES];
          ;
        }
        
    } failure:^{
        
    }];
}

- (void)skipFunction:(id)sender{
     [self dismissViewControllerAnimated:YES completion:nil];
    
}
#pragma mark - 密码可见

- (void)showPwdFunction:(id)sender{
    
    self.pwdTextField.secureTextEntry = !self.pwdTextField.secureTextEntry;
    
    
}
- (void)showRePwdFunction:(id)sender{
    
    self.rePwdTextField.secureTextEntry = !self.rePwdTextField.secureTextEntry;
    
    
}
- (void)textFieldDidBeginEditing:(UITextField *)textField{
    if ([textField isEqual:self.pwdTextField]) {
        if ([self.pwdError length]) {
            self.pwdLineView.backgroundColor = [UIColor stc2Color];
        }else{
            self.pwdLineView.backgroundColor = [UIColor tc5Color];
        }
       
        
    }else{
        self.pwdLineView.backgroundColor = [UIColor tc3Color];
        
    }
    if ([textField isEqual:self.rePwdTextField]) {
        if ([self.rePwdError length]) {
            self.rePwdLineView.backgroundColor = [UIColor stc2Color];
        }else{
            self.rePwdLineView.backgroundColor = [UIColor tc5Color];
        }
        
    }else{
        self.rePwdLineView.backgroundColor = [UIColor tc3Color];
        
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
