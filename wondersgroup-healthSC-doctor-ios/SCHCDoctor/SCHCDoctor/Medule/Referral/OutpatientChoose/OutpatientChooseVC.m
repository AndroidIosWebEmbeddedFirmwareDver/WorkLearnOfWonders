//
//  OutpatientChooseVC.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "OutpatientChooseVC.h"
#import "PatientInfoVC.h"
#import "ReferralRequestViewModel.h"

#import "PatientsListViewController.h"
@interface OutpatientChooseVC ()

@property (strong, nonatomic) UIView  * contentView;
@property (strong, nonatomic) UILabel * nameTitleLabel;
@property (strong, nonatomic) UILabel * idCardTitleLabel;
@property (strong, nonatomic) UILabel * nameLabel;
@property (strong, nonatomic) UIImageView * arrowImageView;
@property (strong, nonatomic) UILabel * idCardLabel;
@property (strong, nonatomic) UILabel * lineLabel;

@property (strong, nonatomic) UIButton * chooseButton;          //选取按钮
@property (strong, nonatomic) UIButton * extractButton;         //提取按钮

@property (strong, nonatomic) ReferralRequestViewModel * viewModel;
@end

@implementation OutpatientChooseVC
- (instancetype)initWithType:(ReferralType)type {
    self = [super init];
    if (self) {
        
        [self initData];
        _viewModel.referralType = type;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setTitle:@"患者信息"];
    
    [self initInterface];
}

#pragma mark - user-define initialization
- (void)initData {
    _viewModel = [[ReferralRequestViewModel alloc] init];
}

- (void)initInterface {
    _contentView = [[UIView alloc] init];
    [_contentView setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:_contentView];
    
    _nameTitleLabel = [[UILabel alloc] init];
    [_nameTitleLabel setText:@"选择患者"];
    _idCardTitleLabel = [[UILabel alloc] init];
    [_idCardTitleLabel setText:@"身份证号"];
    _lineLabel = [[UILabel alloc] init];
    [_lineLabel setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [_contentView addSubview:_lineLabel];
    
    
    for (UILabel * label in @[_nameTitleLabel, _idCardTitleLabel]) {
        [label setTextColor:RGBA_COLOR(102, 102, 102, 1)];
        [label setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [_contentView addSubview:label];
    }
    
    _arrowImageView = [[UIImageView alloc] init];
    [_arrowImageView setContentMode:UIViewContentModeCenter];
    [_arrowImageView setImage:[UIImage imageNamed:@"link_right"]];
    [_contentView addSubview:_arrowImageView];
    
    _nameLabel = [[UILabel alloc] init];
    [_nameLabel setText:@"患者姓名"];
    _idCardLabel = [[UILabel alloc] init];
    for (UILabel * label in @[_nameLabel, _idCardLabel]) {
        [label setTextColor:RGBA_COLOR(189, 189, 189, 1)];
        [label setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [label setTextAlignment:NSTextAlignmentRight];
        [_contentView addSubview:label];
    }
    
    _extractButton = [[UIButton alloc] init];
    [_extractButton setTitle:@"提取患者信息" forState:UIControlStateNormal];
    [_extractButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_extractButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
    [_extractButton setBackgroundColor:RGBA_COLOR(46, 122, 240, 0.5)];
    [_extractButton.layer setCornerRadius:4];
    [_extractButton setUserInteractionEnabled:NO];
    [self.view addSubview:_extractButton];
    
    _chooseButton = [[UIButton alloc] init];
    [_contentView addSubview:_chooseButton];
    
    [self buildConstraint];
    [self bindRAC];
}

- (void)buildConstraint {
    [_contentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view).offset(10);
        make.height.mas_greaterThanOrEqualTo(0);
    }];
    
    [_nameTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_contentView).offset(15);
        make.top.equalTo(_contentView);
        make.width.mas_equalTo(60);
        make.height.mas_equalTo(45);
    }];
    
    [_nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_nameTitleLabel);
        make.right.equalTo(_contentView).offset(-30);
        make.left.mas_greaterThanOrEqualTo(_nameTitleLabel.right);
    }];
    
    [_arrowImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_nameLabel);
        make.left.equalTo(_nameLabel.mas_right).offset(5);
        make.right.equalTo(_contentView).offset(-5);
    }];
    
    [_lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_nameTitleLabel.mas_bottom);
        make.height.mas_equalTo(1);
        make.left.equalTo(_nameTitleLabel);
        make.right.equalTo(_contentView);
    }];
    
    [_idCardTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.height.equalTo(_nameTitleLabel);
        make.top.equalTo(_lineLabel.mas_bottom);
        make.bottom.equalTo(_contentView);
    }];
    
    [_idCardLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_idCardTitleLabel.mas_right).offset(5);
        make.top.bottom.equalTo(_idCardTitleLabel);
        make.right.equalTo(_contentView).offset(-30);
    }];
    
    [_extractButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.center.equalTo(self.view);
        make.left.equalTo(self.view).offset(15);
        make.right.equalTo(self.view).offset(-15);
        make.height.mas_equalTo(44);
    }];
    
    [_chooseButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(_contentView);
        make.bottom.equalTo(_nameLabel);
    }];
}

- (void)bindRAC {
    MJWeakSelf
    [[_chooseButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
        [weakSelf choosePeople];
    }];
    
    [[_extractButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
        PatientInfoVC * vc = [[PatientInfoVC alloc] initWithType:weakSelf.viewModel.referralType model:weakSelf.viewModel.model];
        [weakSelf.navigationController pushViewController:vc animated:YES];
    }];
    
    [RACObserve(_viewModel.model, name) subscribeNext:^(id  _Nullable x) {
        weakSelf.nameLabel.text = x;
    }];
    
    [RACObserve(_viewModel.model, idCard) subscribeNext:^(id  _Nullable x) {
        weakSelf.idCardLabel.text = x;
    }];
}

- (void)doRequest {
    
}


#pragma mark - event

#pragma mark - function
- (void)choosePeople {
    PatientsListViewController * vc = [[PatientsListViewController alloc] initWithPatientTag:0];
    MJWeakSelf
    [vc setSelectBlock:^(PatientListModel * model) {
        weakSelf.viewModel.model.name = model.name;
        weakSelf.viewModel.model.idCard = @"510321199308182699";
        [weakSelf checkExtractButtonState];
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.navigationController pushViewController:vc animated:YES];
}
#pragma mark - delegate

#pragma mark - notification

#pragma mark - setter
- (void)checkExtractButtonState {
    BOOL canGoOn = (![_nameLabel.text isEqualToString:@""]
                    && [RegexKit validateIDCardNumber:_idCardLabel.text]);
    
    [_extractButton setUserInteractionEnabled:canGoOn];
    [_extractButton setBackgroundColor:canGoOn ? RGBA_COLOR(46, 122, 240, 1) : RGBA_COLOR(46, 122, 240, 0.5)];
}
#pragma mark - getter


@end
