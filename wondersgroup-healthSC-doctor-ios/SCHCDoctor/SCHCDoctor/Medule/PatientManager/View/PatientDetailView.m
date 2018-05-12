//
//  PatientDetailView.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientDetailView.h"

@interface PatientDetailView ()

@property (nonatomic, strong) UIImageView *avatarView;
@property (nonatomic, strong) UILabel *nameLbl;
@property (nonatomic, strong) UILabel *genderLbl;
//@property (nonatomic, strong) UILabel *addressLbl;
@property (nonatomic, strong) UILabel *ageLbl;

@property (nonatomic, strong) UILabel *keyLbl;  //重点
@property (nonatomic, strong) UILabel *poorLbl; //贫困

@end

@implementation PatientDetailView

- (instancetype)init {
    
    if (self == [super init]) {
        [self prepareUI];
        [self bindRac];
    }
    
    return self;
}

- (instancetype)initWithFrame:(CGRect)frame {
    if (self == [super initWithFrame:frame]) {
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    self.backgroundColor = [UIColor bc1Color];
    WS(weakSelf)
    
    _avatarView = [UISetupView setupImageViewWithSuperView:self withImageName:nil];
    [_avatarView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.mas_top).offset(14);
        make.left.equalTo(weakSelf.mas_left).offset(15);
        make.size.mas_equalTo(CGSizeMake(61, 61));
    }];
    
    _nameLbl = [UISetupView setupLabelWithSuperView:self withText:nil withTextColor:[UIColor tc1Color] withFontSize:16];
    [_nameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.avatarView.mas_top).offset(10);
        make.left.equalTo(weakSelf.avatarView.mas_right).offset(14);
        make.height.mas_equalTo(16);
        
    }];
    
    _genderLbl = [UISetupView setupLabelWithSuperView:self withText:nil withTextColor:[UIColor tc3Color] withFontSize:16];
    [_genderLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.nameLbl.mas_top).offset(0);
        make.left.equalTo(weakSelf.nameLbl.mas_right).offset(10);
        make.height.mas_equalTo(16);
    }];
    
    _ageLbl = [UISetupView setupLabelWithSuperView:self withText:nil withTextColor:[UIColor tc3Color] withFontSize:16];
    [_ageLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.nameLbl.mas_top).offset(0);
        make.left.equalTo(weakSelf.genderLbl.mas_right).offset(10);
        make.height.mas_equalTo(16);
    }];
    
    _keyLbl = [UISetupView setupLabelWithSuperView:self withText:@"重点" withTextColor:[UIColor stc2Color] withFontSize:11];
    _keyLbl.textAlignment = NSTextAlignmentCenter;
    [_keyLbl setCornerRadius:8.5];
    [_keyLbl setborderWithColor:[UIColor stc2Color] withWidth:0.5];
    [_keyLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.nameLbl.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.avatarView.mas_right).offset(14);
        make.size.mas_equalTo(CGSizeMake(31, 17));
    }];
    _poorLbl = [UISetupView setupLabelWithSuperView:self withText:@"贫困" withTextColor:[UIColor stc2Color] withFontSize:11];
    _poorLbl.textAlignment = NSTextAlignmentCenter;
    [_poorLbl setCornerRadius:8.5];
    [_poorLbl setborderWithColor:[UIColor stc2Color] withWidth:0.5];
    [_poorLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.nameLbl.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.avatarView.mas_right).offset(14+61+8);
        make.size.mas_equalTo(CGSizeMake(31, 17));
    }];

    UIButton *callBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [callBtn setBackgroundImage:[UIImage imageNamed:@"电话"] forState:UIControlStateNormal];
    [callBtn addTarget:self action:@selector(callBtnAction) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:callBtn];
    [callBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.mas_centerY);
        make.right.equalTo(weakSelf.mas_right).offset(-23);
        make.size.mas_equalTo(CGSizeMake(35, 35));
    }];
    
}

- (void)bindRac {
    @weakify(self)
    [RACObserve(self, model) subscribeNext:^(PatientListModel *x) {
        @strongify(self)
        self.avatarView.image = [UIImage imageNamed:@"电话"];
        self.nameLbl.text = x.name;
        self.genderLbl.text = @"男";
        self.ageLbl.text = @"100";
        
        BOOL isKey = NO;
        BOOL isPoor = YES;
        [self relayoutTagIsKey:isKey isPoor:isPoor];
    }];
}

//重新调整 重点和贫困 标签
- (void)relayoutTagIsKey:(BOOL)isKey isPoor:(BOOL)isPoor {
    WS(weakSelf)
    if (isKey && isPoor) {
        //两个都显示
        self.keyLbl.hidden = NO;
        self.poorLbl.hidden = NO;
        [_keyLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.avatarView.mas_right).offset(14);
        }];
        [_poorLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.avatarView.mas_right).offset(14+61+8);
        }];
        
    } else if (isKey && !isPoor) {
        //只显示重点
        self.keyLbl.hidden = NO;
        self.poorLbl.hidden = YES;
        [_keyLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.avatarView.mas_right).offset(14);
        }];
    } else if (!isKey && isPoor) {
        //只显示贫困
        self.keyLbl.hidden = YES;
        self.poorLbl.hidden = NO;
        [_poorLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.avatarView.mas_right).offset(14);
        }];
    } else {
        //两个都不显示
        self.keyLbl.hidden = YES;
        self.poorLbl.hidden = YES;
    }
}

- (void)callBtnAction {
    if (_callActionBlock) {
        _callActionBlock(_model.mobile);
    }
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
