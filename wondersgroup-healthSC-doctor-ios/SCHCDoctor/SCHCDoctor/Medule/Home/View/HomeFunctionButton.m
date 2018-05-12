//
//  HomeFunctionButtonView.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "HomeFunctionButton.h"

@interface HomeFunctionButton ()
@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *contentLabel;
@property (nonatomic, strong) UIImageView *redPointView;

@end

@implementation HomeFunctionButton

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
    
    self.contentLabel.font = [UIFont systemFontOfSize:14.];
    [self addSubview:self.contentLabel];
    
    self.redPointView = [UISetupView setupImageViewWithSuperView:self withImageName:@"首页消息红点"];
    self.redPointView.hidden = YES;
    
    WS(weakSelf)
    
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf);
        make.top.equalTo(weakSelf);
        make.height.width.mas_equalTo(61);
    }];
    
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.iconImageView.mas_bottom).offset(15);
        make.centerX.equalTo(weakSelf);
        make.height.mas_equalTo(@16);
    }];
    
    [self.redPointView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(-4);
        make.top.equalTo(self).offset(4);
        make.size.mas_equalTo(CGSizeMake(12, 12));
    }];
    
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, invalid) subscribeNext:^(NSNumber *x) {
        BOOL invalid = [x boolValue];
        weakSelf.enabled = !invalid;
        if (invalid) {
            weakSelf.contentLabel.textColor = [UIColor tc4Color];
        }
        else {
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
    
    [RACObserve(self, isNeedRedPoint) subscribeNext:^(NSNumber *x) {
        weakSelf.redPointView.hidden = ![x boolValue];
    }];

}

@end
