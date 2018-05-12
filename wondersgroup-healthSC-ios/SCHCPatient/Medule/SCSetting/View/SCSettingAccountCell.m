//
//  SCSettingAccountCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSettingAccountCell.h"

@implementation SCSettingAccountCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubview];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}

- (void)setupSubview
{
    WS(weakSelf)
    UILabel *label = [[UILabel alloc] init];
    label.text = @"账号设置";
    label.textColor = [UIColor tc2Color];
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(25);
    }];
    
    UIImageView *arrowsView = [[UIImageView alloc] init];
    [self.contentView addSubview:arrowsView];
    arrowsView.backgroundColor = [UIColor clearColor];
    arrowsView.image = [UIImage imageNamed:@"link_right"];
    [arrowsView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.size.mas_equalTo(arrowsView.image.size);
    }];
    
    UILabel *lineLabel = [[UILabel alloc] init];
    lineLabel.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:lineLabel];
    [lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView.mas_top);
        make.height.mas_equalTo(0.5);
    }];
    
    UILabel *lineLabel2 = [[UILabel alloc] init];
    lineLabel2.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:lineLabel2];
    [lineLabel2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
}

@end
