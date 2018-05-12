//
//  MapPaopaoView.m
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "MapPaopaoView.h"

@interface MapPaopaoView ()

@property (nonatomic, strong) UILabel *nameLbl;

@end

@implementation MapPaopaoView

- (id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor bc1Color];
        
        _nameLbl = [UISetupView setupLabelWithSuperView:self withText:@"" withTextColor:[UIColor tc1Color] withFontSize:15];
        _nameLbl.textAlignment = NSTextAlignmentCenter;
        WS(weakSelf)
        [_nameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(weakSelf.mas_centerX);
            make.centerY.equalTo(weakSelf.mas_centerY);
            make.left.equalTo(weakSelf.mas_left).offset(10);
            make.right.equalTo(weakSelf.mas_right).offset(-10);
        }];
        
        [self bindRAC];
    }
    return self;
}

- (void)bindRAC{
    [RACObserve(self, name) subscribeNext:^(NSString *str) {
        if (str) {
            _nameLbl.text = str;
        }
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
