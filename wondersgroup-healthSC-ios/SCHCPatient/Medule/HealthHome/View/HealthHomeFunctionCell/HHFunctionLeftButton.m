//
//  HHFLeftButton.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HHFunctionLeftButton.h"


@interface HHFunctionLeftButton ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *customTitleLabel;
@property (nonatomic, strong) UILabel *subTitleLabel;

@property (nonatomic, assign) BOOL noSubTitle;

@end


@implementation HHFunctionLeftButton


- (instancetype)initWithNoSubTitle:(BOOL)noSubTitle {
    
    if (self == [super init]) {
        self.noSubTitle = noSubTitle;
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    UIView *backView = [UIView new];
    [self addSubview:backView];
    
    self.iconImageView = [UIImageView new];
    [backView addSubview:self.iconImageView];
    
    self.customTitleLabel = [UILabel new];
    self.customTitleLabel.textAlignment = NSTextAlignmentCenter;
    self.customTitleLabel.font = [UIFont systemFontOfSize:16.];
    self.customTitleLabel.textColor = [UIColor tc1Color];
    [backView addSubview:self.customTitleLabel];
    
    if (!self.noSubTitle) {
        self.subTitleLabel = [UILabel new];
        self.subTitleLabel.font = [UIFont systemFontOfSize:12.];
        self.subTitleLabel.textColor = [UIColor tc3Color];
        self.subTitleLabel.textAlignment = NSTextAlignmentCenter;
        [backView addSubview:self.subTitleLabel];
    }
    
    WS(weakSelf)
    
    [backView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.center.equalTo(weakSelf);
    }];
    
    if (!self.noSubTitle) {
        
        [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(backView);
            make.centerX.equalTo(backView);
            make.width.height.mas_equalTo(@64);
        }];
        
        [self.customTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(weakSelf.iconImageView.mas_bottom).offset(15);
            make.centerX.equalTo(weakSelf);
        }];
        
        [self.subTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(weakSelf.customTitleLabel.mas_bottom).offset(8);
            make.bottom.equalTo(backView);
            make.centerX.equalTo(backView);
        }];
    }
    else {
        [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(backView);
            make.centerX.equalTo(backView);
            make.width.mas_equalTo(@46);
            make.height.mas_equalTo(@66);
        }];
        
        [self.customTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(weakSelf.iconImageView.mas_bottom).offset(10);
            make.centerX.equalTo(weakSelf);
            make.bottom.equalTo(backView);
        }];
    }
}

- (void)bindRac {
    
    WS(weakSelf)
    
    [RACObserve(self, imageString) subscribeNext:^(NSString *x) {
        if([x rangeOfString:@"http"].location !=NSNotFound){
            NSString *imageString = self.noSubTitle ? @"健康档案默认" : @"预约挂号默认";
            [weakSelf.iconImageView sd_setImageWithURL:[NSURL URLWithString:x] placeholderImage:[UIImage imageNamed:imageString]];
            
        }else{
            weakSelf.iconImageView.image = [UIImage imageNamed:x];
        }
    }];
    
    [RACObserve(self, titleString) subscribeNext:^(NSString *x) {
        weakSelf.customTitleLabel.text = x;
    }];
    
    [RACObserve(self, subTitleString) subscribeNext:^(NSString *x) {
        weakSelf.subTitleLabel.text = x;
    }];
}

@end
