//
//  DoctorDetailTopCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailTopCell.h"


@interface DoctorDetailTopCell ()

@property (nonatomic, strong) UILabel *hospitalDetailLabel;
@property (nonatomic, strong) UILabel *goodAtDetailLabel;
@property (nonatomic, strong) UILabel *doctorDetailLabel;

@property (nonatomic, strong) UIButton *moreButton;
@property (nonatomic, strong) UIImageView *moreArrow;

@end


@implementation DoctorDetailTopCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    self.contentView.backgroundColor = [UIColor bc2Color];
    
    UIView *backView = [UIView new];
    backView.backgroundColor = [UIColor whiteColor];
    [self.contentView addSubview:backView];
    
    UILabel *hospitalLabel = [UILabel new];
    hospitalLabel.text = @"所属医院";
    hospitalLabel.textColor = [UIColor tc1Color];
    hospitalLabel.font = [UIFont systemFontOfSize:15.];
    [backView addSubview:hospitalLabel];
    
    self.hospitalDetailLabel = [UILabel new];
    self.hospitalDetailLabel.font = [UIFont systemFontOfSize:14.];
    self.hospitalDetailLabel.textColor = [UIColor tc2Color];
    self.hospitalDetailLabel.numberOfLines = 2;
    [backView addSubview:self.hospitalDetailLabel];
    
    UIView *line1 = [UIView new];
    line1.backgroundColor = [UIColor dc4Color];
    [backView addSubview:line1];
    
    UILabel *goodAtLabel = [UILabel new];
    goodAtLabel.text = @"专业擅长";
    goodAtLabel.textColor = [UIColor tc1Color];
    goodAtLabel.font = [UIFont systemFontOfSize:15.];
    [backView addSubview:goodAtLabel];
    
    self.goodAtDetailLabel = [UILabel new];
    self.goodAtDetailLabel.font = [UIFont systemFontOfSize:14.];
    self.goodAtDetailLabel.textColor = [UIColor tc2Color];
    self.goodAtDetailLabel.numberOfLines = 0;
    [backView addSubview:self.goodAtDetailLabel];
    
    UIView *line2 = [UIView new];
    line2.backgroundColor = [UIColor dc4Color];
    [backView addSubview:line2];
    
    UILabel *doctorLabel = [UILabel new];
    doctorLabel.text = @"医生详情";
    doctorLabel.textColor = [UIColor tc1Color];
    doctorLabel.font = [UIFont systemFontOfSize:15.];
    [backView addSubview:doctorLabel];
    
    self.moreButton = [UIButton new];
    [self.moreButton setTitle:@"查看全部" forState:UIControlStateNormal];
    [self.moreButton setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
    self.moreButton.titleLabel.font = [UIFont systemFontOfSize:14.];
    [self.moreButton addTarget:self action:@selector(moreButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [backView addSubview:self.moreButton];
    
    self.moreArrow = [UIImageView new];
    self.moreArrow.image = [UIImage imageNamed:@"systemRightArrow"];
    [backView addSubview:self.moreArrow];
    
    self.doctorDetailLabel = [UILabel new];
    self.doctorDetailLabel.font = [UIFont systemFontOfSize:14.];
    self.doctorDetailLabel.textColor = [UIColor tc2Color];
    self.doctorDetailLabel.numberOfLines = 3;
    [backView addSubview:self.doctorDetailLabel];
    
    WS(weakSelf)
    
    [backView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView).offset(10);
        make.bottom.equalTo(weakSelf.contentView).offset(-10);
    }];
    
    [hospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.equalTo(backView).offset(15);
        make.height.mas_equalTo(@15);
    }];
    
    [self.hospitalDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(hospitalLabel.mas_bottom).offset(10);
        make.right.equalTo(backView).offset(-15);
        make.left.equalTo(hospitalLabel);
    }];

    [line1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(hospitalLabel);
        make.top.equalTo(weakSelf.hospitalDetailLabel.mas_bottom).offset(15);
        make.right.equalTo(backView);
        make.height.mas_equalTo(@0.5);
    }];
    
    [goodAtLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(line1.mas_bottom).offset(15);
        make.left.equalTo(hospitalLabel);
        make.height.mas_equalTo(@15);
    }];
    
    [self.goodAtDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(goodAtLabel.mas_bottom).offset(15);
        make.right.equalTo(backView).offset(-15);
        make.left.equalTo(goodAtLabel);
    }];

    [line2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.goodAtDetailLabel.mas_bottom).offset(15);
        make.left.equalTo(goodAtLabel);
        make.right.equalTo(backView);
        make.height.mas_equalTo(@0.5);
    }];

    [doctorLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(line2.mas_bottom).offset(15);
        make.left.equalTo(goodAtLabel);
        make.height.mas_equalTo(@15);
    }];
    
    [self.doctorDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(doctorLabel.mas_bottom).offset(15);
        make.left.equalTo(goodAtLabel);
        make.right.equalTo(backView).offset(-15);
    }];
    
    [self.moreButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.moreArrow.mas_left).offset(-10);
        make.centerY.equalTo(doctorLabel);
        make.height.mas_equalTo(@30);
    }];

    [self.moreArrow mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(backView).offset(-15);
        make.centerY.equalTo(doctorLabel);
        make.height.mas_equalTo(@16);
        make.width.mas_equalTo(@8);
    }];

}

- (void)bindRac {
    
    WS(weakSelf)
    
    [RACObserve(self, cellModel.hosName) subscribeNext:^(id x) {
        weakSelf.hospitalDetailLabel.text = x;
    }];
    
    [RACObserve(self, cellModel.expertin) subscribeNext:^(id x) {
        weakSelf.goodAtDetailLabel.text = x;
    }];
    
    
    
    [RACObserve(self, cellModel.doctorDesc) subscribeNext:^(NSString *x) {
        if (x.length) {
            weakSelf.doctorDetailLabel.text = x;
            
            if ([self moreThanThreeLines:x]) {
                self.moreButton.hidden = NO;
                self.moreArrow.hidden = NO;
            }
            else {
                self.moreButton.hidden = YES;
                self.moreArrow.hidden = YES;
            }
        }
        else {
            self.moreButton.hidden = YES;
            self.moreArrow.hidden = YES;
        }
    }];
}

- (BOOL)moreThanThreeLines:(NSString *)text {
    
    UILabel *label1 = [UILabel new];
    label1.font = [UIFont systemFontOfSize:14.];
    label1.text = text;
    label1.numberOfLines = 3;
    
    UILabel *label2 = [UILabel new];
    label2.font = [UIFont systemFontOfSize:14.];
    label2.text = text;
    label2.numberOfLines = 0;
    
    CGSize size1 = [label1 sizeThatFits:CGSizeMake(SCREEN_WIDTH-30., MAXFLOAT)];
    CGSize size2 = [label2 sizeThatFits:CGSizeMake(SCREEN_WIDTH-30., MAXFLOAT)];
    
    if (size2.height > size1.height) {
        return YES;
    }
    else {
        return NO;
    }
}


#pragma mark - 查看更多按钮点击事件

- (void)moreButtonAction:(id)sender {
    
    if (self.moreButtonBlock) {
        self.moreButtonBlock();
    }
}

@end
