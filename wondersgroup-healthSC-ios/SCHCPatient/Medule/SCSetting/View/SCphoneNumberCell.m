//
//  SCphoneNumberCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCphoneNumberCell.h"

@implementation SCphoneNumberCell


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
    label.text = @"手机号";
    label.textColor = [UIColor tc2Color];
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont systemFontOfSize:14];
    [weakSelf.contentView addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(70);
        make.height.mas_equalTo(25);
    }];
    
    _oldPassWordTextField = [[UITextField alloc] init];
    _oldPassWordTextField = [UITextField new];
    _oldPassWordTextField.font = [UIFont systemFontOfSize:14];
    _oldPassWordTextField.borderStyle = UITextBorderStyleNone;
    _oldPassWordTextField.placeholder = @"请输入更换的手机号";
    [weakSelf.contentView addSubview:_oldPassWordTextField];
    [_oldPassWordTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(label.mas_right).offset(10);
        make.centerY.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@25);
        make.right.equalTo(weakSelf.contentView).offset(-45);
    }];
    [_oldPassWordTextField.rac_textSignal subscribeNext:^(id x){
        NSLog(@"%@",x);//这里的X就是文本框的文字
        if(weakSelf.phoneNumberBlock) {
            weakSelf.phoneNumberBlock(x);
        }
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
