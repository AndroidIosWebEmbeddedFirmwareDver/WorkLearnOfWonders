//
//  SCAccountSetCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAccountSetCell.h"

@implementation SCAccountSetCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubview];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}

- (void)setupSubview
{
    WS(weakSelf);
    UILabel *label = [[UILabel alloc] init];
    label.text = @"账号";
    label.textColor = [UIColor tc2Color];
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont systemFontOfSize:14];
    [weakSelf.contentView addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(25);
    }];
    
    UILabel *numberLabel = [[UILabel alloc] init];
   // numberLabel.text = [UserManager manager].accountName;
    numberLabel.text= [NSString stringWithFormat:@"%@****%@", [[UserManager manager].accountName substringToIndex:3], [[UserManager manager].accountName substringFromIndex:7]];
    numberLabel.textColor = [UIColor tc2Color];
    numberLabel.backgroundColor = [UIColor clearColor];
    numberLabel.textAlignment = NSTextAlignmentRight;
    numberLabel.font = [UIFont systemFontOfSize:14];
    [weakSelf.contentView addSubview:numberLabel];
    [numberLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(145);
        make.height.mas_equalTo(25);
    }];
    
    UILabel *lineLabel = [[UILabel alloc] init];
    lineLabel.backgroundColor = [UIColor bc3Color];
    [weakSelf.contentView addSubview:lineLabel];
    [lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView.mas_top);
        make.height.mas_equalTo(0.5);
    }];
    
    UILabel *lineLabel2 = [[UILabel alloc] init];
    lineLabel2.backgroundColor = [UIColor bc3Color];
    [weakSelf.contentView addSubview:lineLabel2];
    [lineLabel2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
}
@end
