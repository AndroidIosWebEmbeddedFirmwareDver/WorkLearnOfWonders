//
//  NewAddPatientButton.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/15.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "NewAddPatientButton.h"

@interface NewAddPatientButton ()

@property (nonatomic, strong) UIImageView *redPointView;

@end

@implementation NewAddPatientButton

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
    [self setImage:[UIImage imageNamed:@"添加患者"] forState:UIControlStateNormal];
    
    self.redPointView = [UISetupView setupImageViewWithSuperView:self withImageName:@"首页消息红点"];
    [self.redPointView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self).offset(-4);
        make.top.equalTo(self).offset(4);
        make.size.mas_equalTo(CGSizeMake(12, 12));
    }];
}

- (void)bindRac {
    @weakify(self)
    [RACObserve(self, isShowRedPoint) subscribeNext:^(NSNumber *x) {
        @strongify(self)
        self.redPointView.hidden = ![x boolValue];
    }];
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
