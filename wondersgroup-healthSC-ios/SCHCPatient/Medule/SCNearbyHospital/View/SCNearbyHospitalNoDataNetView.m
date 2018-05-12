//
//  SCNearbyHospitalNoDataNetView.m
//  SCHCPatient
//
//  Created by Gu Jiajun on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCNearbyHospitalNoDataNetView.h"

@interface SCNearbyHospitalNoDataNetView ()

@property (nonatomic, strong) UIImageView *tipImageView;
@property (nonatomic, strong) UILabel *tipLabel;
@property (nonatomic, strong) UIButton *reloadBtn;

@end

@implementation SCNearbyHospitalNoDataNetView

- (id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor bc2Color];
        
        
        [self setupView];
        [self bindRAC];
    }
    return self;
}

- (void)setupView{
    WS(weakSelf)
    _tipImageView = [UISetupView setupImageViewWithSuperView:self withImageName:nil];
    [_tipImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.mas_centerX);
        make.centerY.equalTo(weakSelf.mas_centerY).offset(-30);
        make.width.mas_equalTo(458/2);
        make.height.mas_equalTo(360/2);
    }];
    
    _tipLabel = [UISetupView setupLabelWithSuperView:self withText:@"" withTextColor:[UIColor colorWithHex:333333] withFontSize:16];
    _tipLabel.textAlignment = NSTextAlignmentCenter;
    _tipLabel.numberOfLines = 0;
    [_tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_tipImageView.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.mas_left).offset(60);
        make.right.equalTo(weakSelf.mas_right).offset(-60);
    }];
    
    _reloadBtn = [UISetupView setupButtonWithSuperView:self withTitleToStateNormal:@"点击屏幕刷新" withTitleColorToStateNormal:[UIColor tc5Color] withTitleFontSize:14 withAction:^(UIButton *sender) {
        if (_block) {
            _block();
        }
    }];
    
    [_reloadBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.mas_centerX);
        make.top.equalTo(_tipLabel.mas_bottom).offset(5);
        make.width.mas_equalTo(100);
        make.height.mas_equalTo(20);
    }];
}

- (void)bindRAC{

}

- (void)setType:(NearbyHospitalDataType)type{
    WS(weakSelf)
    if (type == NearbyNoData) {
        _tipImageView.image = [UIImage imageNamed:@"img坐标灰大"];
        [_tipImageView mas_remakeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(weakSelf.mas_centerX);
            make.centerY.equalTo(weakSelf.mas_centerY).offset(-30);
            make.width.mas_equalTo(128/2);
            make.height.mas_equalTo(158/2);
        }];
        _tipLabel.text = @"您附近没有就医资源";
        _reloadBtn.hidden = YES;
    } else if (type == NearbyNoWIFI) {
        _tipImageView.image = [UIImage imageNamed:@"网络出错"];
        [_tipImageView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(weakSelf.mas_centerX);
            make.centerY.equalTo(weakSelf.mas_centerY).offset(-30);
            make.width.mas_equalTo(458/2);
            make.height.mas_equalTo(360/2);
        }];
        _tipLabel.text = @"网络不通畅，定位失败";
        _reloadBtn.hidden = NO;
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
