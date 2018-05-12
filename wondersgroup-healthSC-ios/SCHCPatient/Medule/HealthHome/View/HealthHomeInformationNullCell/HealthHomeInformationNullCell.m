//
//  HealthHomeInformationNullCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthHomeInformationNullCell.h"

@implementation HealthHomeInformationNullCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self prepareUI];
    }
    
    return self;
}

- (void)prepareUI {
    
    UIView *backView = [UIView new];
    [self.contentView addSubview:backView];
    
    UIImageView *imageView = [UIImageView new];
    imageView.image = [UIImage imageNamed:@"无数据"];
    [backView addSubview:imageView];
    
    UILabel *label = [UILabel new];
    label.textAlignment = NSTextAlignmentCenter;
    label.font = [UIFont systemFontOfSize:16.];
    label.textColor = [UIColor tc1Color];
    label.text = @"暂无相关消息";
    [backView addSubview:label];
    
    WS(weakSelf)
    [backView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.center.equalTo(weakSelf.contentView);
    }];
    
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(backView);
        make.centerX.equalTo(backView);
        make.bottom.equalTo(label.mas_top);
    }];
    
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(imageView.mas_bottom);
        make.centerX.equalTo(backView);
        make.bottom.equalTo(backView);
    }];
    
//    UILabel *label = [UILabel new];
//    label.textAlignment = NSTextAlignmentCenter;
//    label.text = @"无数据";
//    [self.contentView addSubview:label];
//    
//    WS(weakSelf)
//    
//    [label mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.center.equalTo(weakSelf.contentView);
//    }];
}


@end
