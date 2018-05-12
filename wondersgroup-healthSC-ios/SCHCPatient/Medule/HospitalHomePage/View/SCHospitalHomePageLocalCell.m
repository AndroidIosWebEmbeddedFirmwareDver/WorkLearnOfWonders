//
//  SCHospitalHomePageLocalCell.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalHomePageLocalCell.h"

@interface SCHospitalHomePageLocalCell()

@property (nonatomic, strong) UIImageView *locationIcon;
@property (nonatomic, strong) UILabel *addressLabel;
@property (nonatomic, strong) UIButton *telBtn;
@property (nonatomic, strong) UIView *lineView;

@end

@implementation SCHospitalHomePageLocalCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubviews];
        [self setupSubviewsLayout];
        [self bind];
        
        self.lineTopHidden = YES;
        self.lineBottomHidden = YES;
    }
    return self;
}



#pragma mark - Bind

- (void)bind {
    @weakify(self);
    [[_telBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
        @strongify(self);
        if (self.telClickedHandler) {
            self.telClickedHandler(self.model.hospitalTel);
        }
    }];
    
    RAC(_addressLabel, text) = RACObserve(self, model.hospitalAddress);
}



#pragma mark - Action




#pragma mark - Setup UI

- (void)setupSubviews {
    //
    UIImage *icon = [UIImage imageNamed:@"HospitalHomePage_local"];
    _locationIcon = [[UIImageView alloc] initWithImage:icon];
    _locationIcon.bounds = CGRectMake(0, 0, icon.size.width, icon.size.height);
    [self.contentView addSubview:_locationIcon];
    
    //
    _addressLabel = [[UILabel alloc] init];
    _addressLabel.textColor     = [UIColor tc1Color];
    _addressLabel.font          = [UIFont systemFontOfSize:16];
    _addressLabel.numberOfLines = 2;
    [self.contentView addSubview:_addressLabel];
    
    //
    _lineView = [[UIView alloc] init];
    _lineView.backgroundColor = [UIColor dc4Color];
    [self.contentView addSubview:_lineView];
    
    //
    _telBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [_telBtn setImage:[UIImage imageNamed:@"icon电话"] forState:UIControlStateNormal];
    [self.contentView addSubview:_telBtn];
    
}


- (void)setupSubviewsLayout {
    //
    UIImage *icon = [UIImage imageNamed:@"HospitalHomePage_local"];
    [_locationIcon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self.contentView);
        make.size.mas_equalTo(icon.size);
        make.left.offset(15);
    }];
    
    //
    [_lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.offset(5);
        make.bottom.offset(-5);
        make.right.offset(-49);
        make.width.mas_offset(0.5);
    }];
    
    //
    [_addressLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.contentView).offset(13);
        make.bottom.equalTo(self.contentView).offset(-13);
        make.left.equalTo(_locationIcon.mas_right).offset(10);
        make.right.equalTo(_lineView.mas_left).offset(-10);
    }];
    
    //
    [_telBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.height.right.equalTo(self.contentView);
        make.left.equalTo(_lineView);
    }];
    
}


@end













