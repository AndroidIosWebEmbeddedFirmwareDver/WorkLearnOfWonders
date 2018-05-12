//
//  DoctorDetailJudgeCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailJudgeCell.h"


@interface DoctorDetailJudgeCell ()

@property (nonatomic, strong) UILabel *contentLabel;
@property (nonatomic, strong) UILabel *judgeNameLabel;
@property (nonatomic, strong) UILabel *timeLabel;

@end


@implementation DoctorDetailJudgeCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    self.contentLabel = [UILabel new];
    self.contentLabel.font = [UIFont systemFontOfSize:14.];
    self.contentLabel.numberOfLines = 0;
    self.contentLabel.textColor = [UIColor tc2Color];
    [self.contentView addSubview:self.contentLabel];
    
    self.judgeNameLabel = [UILabel new];
    self.judgeNameLabel.font = [UIFont systemFontOfSize:10.];
    self.judgeNameLabel.textColor = [UIColor tc3Color];
    [self.contentView addSubview:self.judgeNameLabel];
    
    self.timeLabel = [UILabel new];
    self.timeLabel.font = [UIFont systemFontOfSize:10.];
    self.timeLabel.textAlignment = NSTextAlignmentRight;
    self.timeLabel.textColor = [UIColor tc3Color];
    [self.contentView addSubview:self.timeLabel];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:bottomLineView];
    
    WS(weakSelf)
    
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
    }];
    
    [self.judgeNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(weakSelf.contentLabel.mas_bottom).offset(10);
        make.height.mas_equalTo(@10);
    }];
    
    [self.timeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.judgeNameLabel);
    }];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.right.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.contentLabel);
        make.height.mas_equalTo(@0.5);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    
    [RACObserve(self, cellModel.content) subscribeNext:^(id x) {
        weakSelf.contentLabel.text = x;
    }];
    
    [RACObserve(self, cellModel.nickName) subscribeNext:^(id x) {
        weakSelf.judgeNameLabel.text = x;
    }];
    
    [RACObserve(self, cellModel.createTime) subscribeNext:^(id x) {
        weakSelf.timeLabel.text = x;
    }];
}


@end
