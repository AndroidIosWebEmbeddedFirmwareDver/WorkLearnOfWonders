//
//  FeedBackViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "FeedBackViewController.h"
#import "FeedBackViewModel.h"


@interface FeedBackViewController ()

@property (nonatomic, strong) FeedBackViewModel *viewModel;

@property (nonatomic, strong) UITextView *contentView;
@property (nonatomic, strong) UITextField *phoneNumberTextField;
@property (nonatomic, strong) UIButton *submitButton;

@end


@implementation FeedBackViewController
- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self prepareData];
    [self prepareUI];
}

- (void)prepareData {
    
    self.viewModel = [[FeedBackViewModel alloc] init];
    
}

- (void)prepareUI {
    
    self.navigationItem.title = @"意见反馈";
    self.view.backgroundColor = [UIColor bc2Color];
    
    UIView *backView1 = [UIView new];
    backView1.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:backView1];
    
    self.contentView = [UITextView new];
    self.contentView.placeholder = @"请输入您的意见或反馈";
    self.contentView.font = [UIFont systemFontOfSize:14.];
    [backView1 addSubview:self.contentView];
    
    UIView *backView2 = [UIView new];
    backView2.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:backView2];
    
    self.phoneNumberTextField = [UITextField new];
    self.phoneNumberTextField.font = [UIFont systemFontOfSize:14.];
    [backView2 addSubview:self.phoneNumberTextField];
    
    self.submitButton = [UIButton new];
    [self.submitButton setTitle:@"提交" forState:UIControlStateNormal];
    self.submitButton.titleLabel.font = [UIFont systemFontOfSize:14.];
    self.submitButton.layer.masksToBounds = YES;
    self.submitButton.layer.cornerRadius = 4.;
    self.submitButton.backgroundColor = [UIColor tc5Color];
    [self.submitButton addTarget:self action:@selector(submitButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:self.submitButton];
    
    WS(weakSelf)
    
    [backView1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.view).offset(10);
        make.left.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(@250);
    }];
    
    [self.contentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(backView1).offset(15);
        make.top.equalTo(backView1).offset(12);
        make.right.equalTo(backView1).offset(-15);
        make.bottom.equalTo(backView1).offset(-12);
    }];
    
    [backView2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(backView1.mas_bottom).offset(10);
        make.left.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(@45);
    }];
    
    [self.phoneNumberTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(backView2).offset(15);
        make.right.equalTo(backView2).offset(-15);
        make.top.bottom.equalTo(backView2);
    }];
    
    [self.submitButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(backView2.mas_bottom).offset(45);
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15);
        make.height.mas_equalTo(@44);
    }];
}

- (void)submitButtonAction:(id)sender {
    
    
}

@end
