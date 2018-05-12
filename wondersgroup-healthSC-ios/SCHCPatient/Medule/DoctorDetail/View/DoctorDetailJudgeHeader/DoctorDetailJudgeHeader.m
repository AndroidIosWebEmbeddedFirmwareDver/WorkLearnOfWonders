//
//  DoctorDetailJudgeHeader.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/22.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailJudgeHeader.h"

@interface DoctorDetailJudgeHeader ()

@property (nonatomic, strong) UILabel *titleLabel;

@property (nonatomic, strong) UIButton *moreButton;
@property (nonatomic, strong) UILabel *moreLabel;
@property (nonatomic, strong) UIImageView *moreArrow;

@end

@implementation DoctorDetailJudgeHeader


- (instancetype)initWithReuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithReuseIdentifier:reuseIdentifier]) {
        [self prepareUI];
        [self bindRac];
    }
    
    return self;
}


- (void)prepareUI {
    
    self.titleLabel = [UILabel new];
    self.titleLabel.font = [UIFont systemFontOfSize:15.];
    self.titleLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.titleLabel];
    
    self.moreButton = [UIButton new];
    [self.moreButton addTarget:self action:@selector(moreButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.contentView addSubview:self.moreButton];
    
    self.moreLabel = [UILabel new];
    self.moreLabel.text = @"更多";
    self.moreLabel.textAlignment = NSTextAlignmentRight;
    self.moreLabel.font = [UIFont systemFontOfSize:14.];
    self.moreLabel.textColor = [UIColor tc3Color];
    [self.moreButton addSubview:self.moreLabel];
    
    self.moreArrow = [UIImageView new];
    self.moreArrow.image = [UIImage imageNamed:@"back"];
    [self.moreButton addSubview:self.moreArrow];
    
    WS(weakSelf)
    
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.bottom.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@15);
    }];
    
    [self.moreButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.top.bottom.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(@50);
    }];
    
    [self.moreLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.moreArrow.mas_left).offset(-10);
        make.bottom.equalTo(weakSelf.moreButton);
        make.height.mas_equalTo(@14);
    }];
    
    [self.moreArrow mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.right.equalTo(weakSelf.moreButton);
        make.width.mas_equalTo(@7);
        make.height.mas_equalTo(@14);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    
    [RACObserve(self, judgeCount) subscribeNext:^(id x) {
        weakSelf.titleLabel.text = [NSString stringWithFormat:@"患者评价 (%ld)", [x integerValue]];
        if ([x integerValue] <= 5) {
            weakSelf.moreLabel.hidden = YES;
            weakSelf.moreArrow.hidden = YES;
        }
        else {
            weakSelf.moreLabel.hidden = NO;
            weakSelf.moreArrow.hidden = NO;
        }
    }];
}


- (void)moreButtonAction:(id)sender {
    
    if (self.moreBlock) {
        self.moreBlock();
    }
}

@end
