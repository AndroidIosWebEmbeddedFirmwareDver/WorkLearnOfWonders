//
//  FailView.m
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#define FAILIMAGE_SIZE CGSizeMake(100, 84)

#import "FailView.h"

@implementation FailView

#pragma mark 构建UI
- (void)setupUIViews {
    self.backgroundColor = [UIColor bc2Color];
    
    _imageFail = [[UIImageView alloc] init];
    [self addSubview: _imageFail];
    WS(weakSelf)
    [_imageFail mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf);
        make.centerY.equalTo(weakSelf).offset(-45);
        make.width.mas_equalTo(FAILIMAGE_SIZE.width);
        make.height.mas_equalTo(FAILIMAGE_SIZE.height);
    }];
//    (58.5 154; 203 172
    _labelFail = [[UILabel alloc] init];
    [_labelFail setBackgroundColor:[UIColor clearColor]];
    _labelFail.textColor = [UIColor colorWithHex:0x333333 alpha:1.];
    _labelFail.font = [UIFont systemFontOfSize: 16];
    _labelFail.numberOfLines = 0;
    _labelFail.textAlignment = NSTextAlignmentCenter;
    [self addSubview:_labelFail];
    [_labelFail sizeToFit];
    
    [_labelFail mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_imageFail.mas_bottom).offset(30);
        make.left.equalTo(weakSelf).offset(15);
        make.right.equalTo(weakSelf).offset(-15);
    }];
    
}

#pragma mark 初始化
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        
        [self setupUIViews];
        
        UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapAction:)];
        [self addGestureRecognizer:tapGesture];
    }
    return self;
}

#pragma mark 显示失败页面
- (void)showFail:(UIImage *)dataImage withTip:(NSString *)tip {
    self.imageFail.image = dataImage;
    self.labelFail.text  = tip;
    
    CGFloat width = self.width-15-15;
    CGSize size = [Utility heightWithAttributesWithFont:[UIFont systemFontOfSize:16] withText:tip withWidth:width withHeight:MAXFLOAT];
    
    WS(weakSelf)
    [self.imageFail mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf);
        make.centerY.equalTo(weakSelf).offset(-(size.height+30)/2.0);
        make.width.mas_equalTo(dataImage.size.width);
        make.height.mas_equalTo(dataImage.size.height);
    }];
    
    //[self setNeedsDisplay];
}

#pragma mark 失败页面点击
- (void)tapAction:(UITapGestureRecognizer *)aTapGesture{
    
    if (_touchFailBlock) {
        _touchFailBlock();
    }
}


@end
