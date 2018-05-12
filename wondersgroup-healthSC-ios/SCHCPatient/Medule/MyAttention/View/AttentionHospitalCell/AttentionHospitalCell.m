//
//  AttentionHospitalCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AttentionHospitalCell.h"


@interface AttentionHospitalCell ()

@property (nonatomic, strong) UIImageView *iconImageView;

@property (nonatomic, strong) UILabel *nameLabel;

@property (nonatomic, strong) UILabel *levelLabel;
@property (nonatomic, strong) UILabel *countLabel;

@end


@implementation AttentionHospitalCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self prepareUI];
        [self bindRac];
        self.lineTopHidden = YES;
    }
    
    return self;
}

- (void)prepareUI {
    
    self.iconImageView = [UIImageView new];
    self.iconImageView.layer.masksToBounds = YES;
    self.iconImageView.layer.cornerRadius = 25.;
    [self.contentView addSubview:self.iconImageView];
    
    self.nameLabel = [UILabel new];
    self.nameLabel.font = [UIFont systemFontOfSize:15.];
    self.nameLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.nameLabel];
    
    self.levelLabel = [UILabel new];
    self.levelLabel.font = [UIFont systemFontOfSize:12.];
    self.levelLabel.textColor = [UIColor stc1Color];
    [self.contentView addSubview:self.levelLabel];
    
    self.countLabel = [UILabel new];
    self.countLabel.font = [UIFont systemFontOfSize:12.];
    self.countLabel.textColor = [UIColor tc3Color];
    self.countLabel.text = @"预约量";
    [self.contentView addSubview:self.countLabel];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:bottomLineView];
    
    WS(weakSelf)
    
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(weakSelf.contentView).offset(15);
        make.width.height.mas_equalTo(@48);
    }];
    
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.iconImageView.mas_right).offset(15);
        make.top.equalTo(weakSelf.contentView).offset(18.5);
        make.height.mas_equalTo(@15);
    }];
    
    [self.levelLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.nameLabel.mas_bottom).offset(15);
        make.left.equalTo(weakSelf.nameLabel);
        make.height.mas_equalTo(@12);
    }];
    
    [self.countLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.levelLabel.mas_right).offset(10);
        make.centerY.equalTo(weakSelf.levelLabel);
        make.height.mas_equalTo(@12);
    }];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.nameLabel.mas_left);
        make.bottom.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@0.5);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    
    [RACObserve(self, cellModel.hospitalPhoto) subscribeNext:^(id x) {
        [weakSelf.iconImageView sd_setImageWithURL:[NSURL URLWithString:x] placeholderImage:[UIImage imageNamed:@"医院默认96"]];
    }];
    
    [RACObserve(self, cellModel.hospitalName) subscribeNext:^(id x) {
        weakSelf.nameLabel.text = x;
    }];
    
    [RACObserve(self, cellModel.hospitalGrade) subscribeNext:^(id x) {
        weakSelf.levelLabel.text = x;
    }];
    
    [RACObserve(self, cellModel.receiveCount) subscribeNext:^(id x) {
        if (!x) return;
        weakSelf.countLabel.text = [NSString stringWithFormat:@"预约量%@", x];
    }];
}


@end
