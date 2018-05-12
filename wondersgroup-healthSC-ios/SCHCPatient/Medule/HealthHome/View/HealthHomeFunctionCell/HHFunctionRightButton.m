//
//  HHFunctionRightButton.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HHFunctionRightButton.h"


@interface HHFunctionRightButton ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *contentLabel;
@property (nonatomic, strong) UIImageView *descImageView;

@end

@implementation HHFunctionRightButton

- (instancetype)init {
    
    if (self == [super init]) {
        [self prepareUI];
        [self bindRac];
    }
    
    return self;
}

- (void)prepareUI {
    
    self.iconImageView = [UIImageView new];
    [self addSubview:self.iconImageView];
    
    self.contentLabel = [UILabel new];
    self.contentLabel.font = [UIFont systemFontOfSize:16.];
    [self addSubview:self.contentLabel];
    
    self.descImageView = [UIImageView new];
    self.descImageView.hidden = YES;
    self.descImageView.image = [UIImage imageNamed:@"temporarily_invalid"];
    [self addSubview:self.descImageView];
    
    WS(weakSelf)
    
    CGFloat leftSpace = SCREEN_WIDTH <= 320 ? 15 : 25;
    CGFloat middleSpace = SCREEN_WIDTH <= 320 ? 10 : 15;
    
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf).offset(leftSpace);
        make.centerY.equalTo(weakSelf);
        make.height.width.mas_equalTo(@48);
    }];
    
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.iconImageView.mas_right).offset(middleSpace);
        make.centerY.equalTo(weakSelf);
        make.height.mas_equalTo(@16);
    }];
    
    [self.descImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.right.equalTo(weakSelf);
        make.width.mas_equalTo(@49.6);
        make.height.mas_equalTo(@60);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, invalid) subscribeNext:^(NSNumber *x) {
        
        BOOL invalid = [x boolValue];
        if (invalid) {
            weakSelf.descImageView.hidden = NO;
            weakSelf.contentLabel.textColor = [UIColor tc3Color];
        }
        else {
            weakSelf.descImageView.hidden = YES;
            weakSelf.contentLabel.textColor = [UIColor tc1Color];
        }
    }];
    
    [RACObserve(self, imageString) subscribeNext:^(NSString *x) {
        if (x.length == 0) {
            weakSelf.iconImageView.image = [UIImage imageNamed:@"医院默认96"];
        }
        else {
            if([x rangeOfString:@"http"].location !=NSNotFound){
                [weakSelf.iconImageView sd_setImageWithURL:[NSURL URLWithString:x] placeholderImage:[UIImage imageNamed:@"医院默认96"]];
                
            }else{
                weakSelf.iconImageView.image = [UIImage imageNamed:x];
            }
        }
    }];
    
    [RACObserve(self, titleName) subscribeNext:^(id x) {
        weakSelf.contentLabel.text = x;
    }];
}


@end
