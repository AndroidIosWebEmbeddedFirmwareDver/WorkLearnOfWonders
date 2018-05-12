//
//  SCChangePasswordCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCChangePasswordCell.h"

@implementation SCChangePasswordCell

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
    _label = [[UILabel alloc] init];
    _label.textColor = [UIColor tc2Color];
    _label.backgroundColor = [UIColor clearColor];
    _label.font = [UIFont systemFontOfSize:14];
    [weakSelf.contentView addSubview:_label];
    [_label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(25);
    }];
    
    UIImageView *arrowsView = [[UIImageView alloc] init];
    [weakSelf.contentView addSubview:arrowsView];
    arrowsView.backgroundColor = [UIColor clearColor];
    arrowsView.image = [UIImage imageNamed:@"link_right"];
    [arrowsView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
         make.size.mas_equalTo(arrowsView.image.size);
    }];
    
    UILabel *lineLabel2 = [[UILabel alloc] init];
    lineLabel2.backgroundColor = [UIColor bc3Color];
    [weakSelf.contentView addSubview:lineLabel2];
    [lineLabel2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)changeLabelState
{
    if ([UserManager manager].password_complete) {
        _label.text = @"修改密码";
    } else {
        _label.text = @"设置密码";
    }
}

@end
