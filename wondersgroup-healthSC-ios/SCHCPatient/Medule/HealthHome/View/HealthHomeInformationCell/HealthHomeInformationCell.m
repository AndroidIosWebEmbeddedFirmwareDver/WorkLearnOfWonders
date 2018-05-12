//
//  HealthHomeInformationCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthHomeInformationCell.h"


@interface HealthHomeInformationCell ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UILabel *contentLabel;

@end

@implementation HealthHomeInformationCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    self.iconImageView = [UIImageView new];
    [self.contentView addSubview:self.iconImageView];
    
    self.titleLabel = [UILabel new];
    self.titleLabel.font = [UIFont systemFontOfSize:16.];
    self.titleLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.titleLabel];
    
    self.contentLabel = [UILabel new];
    self.contentLabel.font = [UIFont systemFontOfSize:14.];
    self.contentLabel.textColor = [UIColor tc3Color];
    self.contentLabel.numberOfLines = 2;
    [self.contentView addSubview:self.contentLabel];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:bottomLineView];
    
    WS(weakSelf)
    
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(@90);
        make.height.mas_equalTo(@67);
    }];
    
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.iconImageView.mas_right).offset(15);
        make.top.equalTo(weakSelf.contentView).offset(20);
        make.height.mas_equalTo(@16);
        make.right.equalTo(weakSelf.contentView).offset(-15);
    }];
    
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.titleLabel.mas_bottom).offset(10);
        make.left.right.equalTo(weakSelf.titleLabel);
    }];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.iconImageView);
        make.right.bottom.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@0.5);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    
    [RACObserve(self, cellModel.title) subscribeNext:^(id x) {
        weakSelf.titleLabel.text = x;
    }];
    
    [RACObserve(self, cellModel.thumb) subscribeNext:^(id x) {
        [weakSelf.iconImageView sd_setImageWithURL:[NSURL URLWithString:x] placeholderImage:[UIImage imageNamed:@"文章列表加载失败180-135"]];
    }];
    
    [RACObserve(self, cellModel.desc) subscribeNext:^(id x) {
        weakSelf.contentLabel.text = x;
    }];
}

@end
