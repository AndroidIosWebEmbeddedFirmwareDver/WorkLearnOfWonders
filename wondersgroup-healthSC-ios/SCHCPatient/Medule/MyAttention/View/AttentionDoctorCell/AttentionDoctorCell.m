//
//  AttentionDoctorCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AttentionDoctorCell.h"


@interface AttentionDoctorCell ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *officeLabel;

@property (nonatomic, strong) UIButton *registerButton;     //挂号按钮

@property (nonatomic, strong) UILabel *countLabel;

@property (nonatomic, strong) UILabel *goodAtLabel;

@end


@implementation AttentionDoctorCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self prepareUI];
        [self bindRac];
    }
    
    return self;
}

- (void)prepareUI {
    
    self.iconImageView = [UIImageView new];
    self.iconImageView.layer.masksToBounds = YES;
    self.iconImageView.layer.cornerRadius = 25.;
    [self.contentView addSubview:self.iconImageView];
    
    self.nameLabel = [UILabel new];
    self.nameLabel.font = [UIFont systemFontOfSize:16.];
    self.nameLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.nameLabel];
    
    self.officeLabel = [UILabel new];
    self.officeLabel.font = [UIFont systemFontOfSize:12.];
    self.officeLabel.textColor = [UIColor tc2Color];
    [self.contentView addSubview:self.officeLabel];
    
    self.registerButton = [UIButton new];
    [self.registerButton setTitleColor:[UIColor sbc3Color] forState:UIControlStateNormal];
    [self.registerButton setTitle:@"挂号" forState:UIControlStateNormal];
    self.registerButton.layer.masksToBounds = YES;
    self.registerButton.layer.cornerRadius = 8.;
    self.registerButton.layer.borderColor = [UIColor sbc3Color].CGColor;
    self.registerButton.layer.borderWidth = 0.5;
    self.registerButton.titleLabel.font = [UIFont systemFontOfSize:12.];
    [self.contentView addSubview:self.registerButton];
    
    self.countLabel = [UILabel new];
    self.countLabel.textColor = [UIColor stc1Color];
    self.countLabel.font = [UIFont systemFontOfSize:12.];
    [self.contentView addSubview:self.countLabel];
    
    self.goodAtLabel = [UILabel new];
    self.goodAtLabel.font = [UIFont systemFontOfSize:12.];
    self.goodAtLabel.textColor = [UIColor tc3Color];
    [self.contentView addSubview:self.goodAtLabel];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:bottomLineView];
    
    WS(weakSelf)
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView).offset(15);
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.width.height.mas_equalTo(@48.);
    }];
    
    UILabel *tempLabel = [UILabel new];
    tempLabel.text = @"啊啊啊啊啊...";
    tempLabel.font = [UIFont systemFontOfSize:16.];
    CGSize size = [tempLabel sizeThatFits:CGSizeMake(MAXFLOAT, 16.)];
    
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.iconImageView.mas_right).offset(15);
        make.top.equalTo(weakSelf.iconImageView);
        make.height.mas_equalTo(@16);
        make.width.mas_lessThanOrEqualTo(size.width);
    }];
    
    [self.officeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.nameLabel);
        make.height.mas_equalTo(@12);
        make.left.equalTo(weakSelf.nameLabel.mas_right).offset(5);
        make.right.equalTo(weakSelf.registerButton.mas_left).offset(-5);
    }];
    
    [self.registerButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.nameLabel);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.width.mas_equalTo(@34);
        make.height.mas_equalTo(@16);
    }];
    
    [self.countLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.nameLabel.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.nameLabel);
        make.height.mas_equalTo(@12);
    }];
    
    [self.goodAtLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.countLabel.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.nameLabel);
        make.height.mas_equalTo(@12);
        make.right.equalTo(weakSelf.contentView).offset(-23.5);
    }];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.nameLabel.mas_left);
        make.bottom.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@0.5);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    
    [RACObserve(self, cellModel.headphoto) subscribeNext:^(NSString *x) {
        
        NSString *imageName;
        if ([self.cellModel.gender integerValue] == 2) {
            imageName = @"默认医生女96";
        }
        else {
            imageName = @"默认医生男96";
        }
        
        if (x.length) {
            [weakSelf.iconImageView sd_setImageWithURL:[NSURL URLWithString:x] placeholderImage:[UIImage imageNamed:imageName]];
        }
        else {
            weakSelf.iconImageView.image = [UIImage imageNamed:imageName];
        }
    }];
    
    [RACObserve(self, cellModel.doctorName) subscribeNext:^(NSString *x) {
        
        NSString *string;
        if (x.length > 5) {
            string = [x stringByReplacingCharactersInRange:NSMakeRange(5, x.length-5) withString:@"..."];
            
        }
        else {
            string = x;
        }
        weakSelf.nameLabel.text = string;
    }];
    
    [RACObserve(self, cellModel.doctorTitle) subscribeNext:^(NSString *x) {
        NSString *string;
        if (x.length > 5) {
            string = [x stringByReplacingCharactersInRange:NSMakeRange(5, x.length-5) withString:@"..."];
            
        }
        else {
            string = x;
        }
        weakSelf.officeLabel.text = string;
    }];
    
    [RACObserve(self, cellModel.orderCount) subscribeNext:^(id x) {
        weakSelf.countLabel.text = [NSString stringWithFormat:@"接诊量 %@", x];
    }];
    
    [RACObserve(self, cellModel.expertin) subscribeNext:^(NSString *x) {
        
        if (x.length) {
            weakSelf.goodAtLabel.text = [NSString stringWithFormat:@"擅长: %@", x];
        }
        else {
            weakSelf.goodAtLabel.text = @"擅长: ";
        }
    }];
}

@end
