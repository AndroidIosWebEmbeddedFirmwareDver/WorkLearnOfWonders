//
//  HealthFileListCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthFileListCell.h"
#import "UILabel+LEEMethod.h"


@interface HealthFileListCell ()

@property (nonatomic, strong) UILabel *timeLabel;
@property (nonatomic, strong) UILabel *timeDetailLabel;

@property (nonatomic, strong) UILabel *hospitalLabel;
@property (nonatomic, strong) UILabel *hospitalDetailLabel;

@property (nonatomic, strong) UILabel *officeLabel;
@property (nonatomic, strong) UILabel *officeDetailLabel;

@property (nonatomic, strong) UILabel *additionalLabel;
@property (nonatomic, strong) UILabel *additionalDetailLabel;

@end

@implementation HealthFileListCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self prepareUI];
        [self bindRac];
    }
    
    return self;
}

- (void)prepareUI {
    
    //
    self.timeLabel = [[UILabel alloc] initWithFrame:CGRectMake(15., 15., 65., 15.)];
    self.timeLabel.font = [UIFont systemFontOfSize:14.];
    self.timeLabel.textColor = [UIColor tc2Color];
    self.timeLabel.text = @"检查时间";
    [self.timeLabel changeAlignmentLeftAndRight];
    [self.contentView addSubview:self.timeLabel];
    
    self.timeDetailLabel = [UILabel new];
    self.timeDetailLabel.font = [UIFont systemFontOfSize:14.];
    self.timeDetailLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.timeDetailLabel];
    
    
    //
    self.hospitalLabel = [[UILabel alloc] initWithFrame:CGRectMake(15., 45., 65., 15.)];
    self.hospitalLabel.font = [UIFont systemFontOfSize:14.];
    self.hospitalLabel.textColor = [UIColor tc2Color];
    self.hospitalLabel.text = @"医院";
    [self.hospitalLabel changeAlignmentLeftAndRight];
    [self.contentView addSubview:self.hospitalLabel];
    
    self.hospitalDetailLabel = [UILabel new];
    self.hospitalDetailLabel.font = [UIFont systemFontOfSize:14.];
    self.hospitalDetailLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.hospitalDetailLabel];
    
    
    //
    self.officeLabel = [[UILabel alloc] initWithFrame:CGRectMake(15., 70., 65., 15.)];
    self.officeLabel.font = [UIFont systemFontOfSize:14.];
    self.officeLabel.textColor = [UIColor tc2Color];
    self.officeLabel.text = @"科室";
    [self.officeLabel changeAlignmentLeftAndRight];
    [self.contentView addSubview:self.officeLabel];
    
    self.officeDetailLabel = [UILabel new];
    self.officeDetailLabel.font = [UIFont systemFontOfSize:14.];
    self.officeDetailLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.officeDetailLabel];
    
    
    //
    self.additionalLabel = [[UILabel alloc] initWithFrame:CGRectMake(15., 95., 65., 15.)];
    self.additionalLabel.font = [UIFont systemFontOfSize:14.];
    self.additionalLabel.textColor = [UIColor tc2Color];
    self.additionalLabel.text = @"检查类别";
    [self.additionalLabel changeAlignmentLeftAndRight];
    [self.contentView addSubview:self.additionalLabel];
    
    self.additionalDetailLabel = [UILabel new];
    self.additionalDetailLabel.font = [UIFont systemFontOfSize:14.];
    self.additionalDetailLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.additionalDetailLabel];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor dc2Color];
    [self.contentView addSubview:bottomLineView];
    
    WS(weakSelf)
//    [self.timeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.top.left.equalTo(weakSelf.contentView).offset(15);
//        make.width.mas_equalTo(@65);
//        make.height.mas_equalTo(@15);
//    }];

    [self.timeDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.timeLabel);
        make.left.equalTo(weakSelf.timeLabel.mas_right).offset(5);
        make.right.equalTo(weakSelf.contentView).offset(-45);
        make.height.mas_equalTo(@15);
    }];
    
//    [self.hospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(weakSelf.timeLabel);
//        make.top.equalTo(weakSelf.timeLabel.mas_bottom).offset(10);
//        make.width.mas_equalTo(@65);
//        make.height.mas_equalTo(@15);
//    }];
    
    [self.hospitalDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.hospitalLabel);
        make.left.equalTo(weakSelf.hospitalLabel.mas_right).offset(5);
        make.right.equalTo(weakSelf.contentView).offset(-45);
        make.height.mas_equalTo(@15);
    }];
    
//    [self.officeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(weakSelf.timeLabel);
//        make.top.equalTo(weakSelf.hospitalLabel.mas_bottom).offset(10);
//        make.width.mas_equalTo(@65);
//        make.height.mas_equalTo(@15);
//    }];
    
    [self.officeDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.officeLabel);
        make.left.equalTo(weakSelf.officeLabel.mas_right).offset(5);
        make.right.equalTo(weakSelf.contentView).offset(-45);
        make.height.mas_equalTo(@15);
    }];
    
//    [self.additionalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(weakSelf.timeLabel);
//        make.top.equalTo(weakSelf.timeLabel.mas_bottom).offset(10);
//        make.width.mas_equalTo(@65);
//        make.height.mas_equalTo(@15);
//    }];
    
    [self.additionalDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.additionalLabel);
        make.left.equalTo(weakSelf.additionalLabel.mas_right).offset(5);
        make.right.equalTo(weakSelf.contentView).offset(-45);
        make.height.mas_equalTo(@15);
    }];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@0.5);
    }];
    
    
    //测试代码
    self.timeDetailLabel.text = @"2016-08-20";
    self.hospitalDetailLabel.text = @"医院名称";
    self.officeDetailLabel.text = @"急诊科";
    self.additionalDetailLabel.text = @"检查类别";
}

- (void)bindRac {
    
    
}





@end
