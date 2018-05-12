//
//  LoginViewController.m
//  CNHealthCloudPatient
//
//  Created by 杜凯 on 16/5/13.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "LoginViewController.h"

typedef enum{
    LoginWithViewMobileType,
    LoginWithViewPWDType,
}LoginWithViewType;

@interface LoginViewController ()

@property (nonatomic,strong) UITextField *mobileTextField;
@property (nonatomic,strong) UITextField *pwdTextField;
@property (nonatomic,strong) UIImageView *mobileImageView;
@property (nonatomic,strong) UIImageView *pwdImageView;
@property (nonatomic,strong) UIButton *loginButton;

@end

@implementation LoginViewController

#pragma mark    - lifecycle

-(instancetype)init{
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

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBarHidden = YES;
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    self.navigationController.navigationBarHidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindViewModel];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    
}


#pragma mark    - setupView
-(void)setupView {
    self.view.backgroundColor = [UIColor bc1Color];
    
    UIImageView *iconView = [UISetupView setupImageViewWithSuperView:self.view withImageName:@"logo"];
    [iconView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(self.view).offset(103);
        make.size.mas_equalTo(CGSizeMake(80, 80));
    }];
    
    UIView *mobileView = [self textFieldViewWithType:LoginWithViewMobileType];
    [mobileView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(iconView.mas_bottom).offset(40);
        make.height.mas_equalTo(25);
    }];
    
    UIView *pwdView = [self textFieldViewWithType:LoginWithViewPWDType];
    [pwdView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(mobileView.mas_bottom).offset(30);
        make.height.mas_equalTo(25);
    }];
    WS(weakSelf)
    self.loginButton = [UISetupView setupButtonWithSuperView:self.view withTitleToStateNormal:@"登录" withTitleColorToStateNormal:[UIColor tc0Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        [weakSelf finishFuntion];
    }];
    self.loginButton.backgroundColor = [UIColor bc7Color];
    [self.loginButton setCornerRadius:20];
    [self.loginButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(pwdView.mas_bottom).offset(55);
        make.centerX.equalTo(self.view);
        make.left.equalTo(self.view).offset(55);
        make.height.mas_equalTo(44);
    }];
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    WS(weakSelf)
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:{
                [weakSelf.view showFailView:FailViewEmpty withAction:^{
                    [weakSelf finishFuntion];
                }];
            }
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [weakSelf.view hiddenFailView];
                
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow && failType != FailViewEmpty) {
            
            [weakSelf.view showFailView:failType withAction:^{
                [weakSelf finishFuntion];
            }];
        }
    }];
    
    self.mobileTextField.text = [UserManager manager].accountName;
    RAC(self.viewModel, mobile) = self.mobileTextField.rac_textSignal;
    RAC(self.viewModel, password) = self.pwdTextField.rac_textSignal;
    
    [RACObserve(self.mobileTextField, text)subscribeNext:^(NSString * text) {
        if (text.length) {
            self.mobileImageView.image = [UIImage imageNamed:@"手机"];
        }else {
            self.mobileImageView.image = [UIImage imageNamed:@"手机未选中状态"];
        }
    }];
    
    [RACObserve(self.pwdTextField, text)subscribeNext:^(NSString * text) {
        if (text.length) {
            self.pwdImageView.image = [UIImage imageNamed:@"密码-选中状态"];
        }else {
            self.pwdImageView.image = [UIImage imageNamed:@"密码"];
        }
    }];
    
    RAC(self.loginButton, enabled) =[RACSignal combineLatest:@[self.mobileTextField.rac_textSignal,self.pwdTextField.rac_textSignal] reduce:^(NSString *mobile, NSString *pwd){
        return @(( [mobile length] && [pwd length]  > 0));
    }];
    
    [RACObserve(self.loginButton,enabled)subscribeNext:^(NSNumber * enable) {
        if ([enable boolValue]) {
            weakSelf.loginButton.backgroundColor = [UIColor bc7Color];
        }else{
            weakSelf.loginButton.backgroundColor = [UIColor bc12Color];
        }
    }];

}

#pragma mark    - method
-(UIView *)textFieldViewWithType:(LoginWithViewType)type {
    UIView *view = [UISetupView setupViewWithSuperView:self.view withBackGroundColor:[UIColor clearColor]];
    
    UIImageView *imageView = [UISetupView setupImageViewWithSuperView:view withImageName:@""];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(view).offset(45);
        make.top.equalTo(view);
        make.size.mas_equalTo(CGSizeMake(14, 14));
    }];

    UITextField *textField = [UISetupView setupTextFieldWithSuperView:view withText:@"" withTextColor:[UIColor tc1Color] withFontSize:13 withPlaceholder:@"" withDelegate:self withReturnKeyType:UIReturnKeyDefault withKeyboardType:UIKeyboardTypeDefault];
    textField.clearButtonMode = UITextFieldViewModeWhileEditing;
    [textField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(imageView.mas_right).offset(20);
        make.top.equalTo(imageView);
        make.right.equalTo(view).offset(-45);
        make.height.mas_equalTo(15);
    }];
    
    UIView *line = [UISetupView setupLineViewWithSuperView:view];
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(textField);
        make.bottom.equalTo(view);
        make.height.mas_equalTo(0.5);
    }];
    if (type == LoginWithViewMobileType) {
        imageView.image = [UIImage imageNamed:@"手机未选中状态"];
        textField.placeholder = @"请输入您的手机号";
        textField.keyboardType = UIKeyboardTypePhonePad;
        self.mobileImageView = imageView;
        self.mobileTextField = textField;
        
    }else if (type == LoginWithViewPWDType){
        imageView.image = [UIImage imageNamed:@"密码"];
        textField.placeholder = @"请输入密码";
        self.pwdImageView = imageView;
        self.pwdTextField = textField;
    }
    
    return view;
}

- (void)finishFuntion{
    
    WS(weakSelf);
    if(![RegexKit validateMobile:self.viewModel.mobile]){
        [MBProgressHUDHelper showHudWithText:@"手机号不正确"];
        [self.mobileTextField becomeFirstResponder];
        return;
        
    }
    
    if(![SpecialRegexKit validateSpecialPassword:self.viewModel.password]){
        [MBProgressHUDHelper showHudWithText:@"密码(长度6-16位，字母与数字组合)"];
        [self.pwdTextField becomeFirstResponder];
        return;
    }
    
    [self.viewModel userLogin:^{
        //push
        [[VCManager manager] showHomeViewController:YES];
    }];

}


@end
