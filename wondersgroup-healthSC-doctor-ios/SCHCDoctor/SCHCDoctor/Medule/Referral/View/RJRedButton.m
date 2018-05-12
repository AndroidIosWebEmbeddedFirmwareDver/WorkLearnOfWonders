//
//  RJRedButton.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "RJRedButton.h"
@interface RJRedButton ()
@property (strong, nonatomic) UIView * redView;         //红点
@end

@implementation RJRedButton

- (void)showRedPoint:(BOOL)isShow {
    if (isShow) {
        _redView = [[UIView alloc] init];
        [_redView setBackgroundColor:RGB_COLOR(244, 117, 117)];
        [_redView.layer setCornerRadius:3];
        [self addSubview:_redView];
        MJWeakSelf
        [_redView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(weakSelf.titleLabel.mas_right);
            make.centerY.equalTo(weakSelf.titleLabel.mas_top);
            make.width.height.mas_equalTo(6);
        }];
    } else if(_redView){
        [_redView removeFromSuperview];
        _redView = nil;
    }
}


@end
