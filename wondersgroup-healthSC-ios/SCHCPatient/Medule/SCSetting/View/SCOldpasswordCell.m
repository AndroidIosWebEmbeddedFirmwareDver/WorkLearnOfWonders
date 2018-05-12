//
//  SCOldpasswordCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCOldpasswordCell.h"

@implementation SCOldpasswordCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubview];
        [self bindRac];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}

- (void)setupSubview
{
    WS(weakSelf);
    UILabel *label = [[UILabel alloc] init];
    label.text = @"原密码";
    label.textColor = [UIColor tc2Color];
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(70);
        make.height.mas_equalTo(25);
    }];

    _oldpwdeyeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [_oldpwdeyeButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
    [_oldpwdeyeButton addTarget:self action:@selector(showPwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    [self.contentView addSubview:_oldpwdeyeButton];
    [self.oldpwdeyeButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (_oldpwdeyeButton || IS_IPHONE_4_OR_LESS){
            make.right.equalTo(weakSelf.contentView).offset(-20);
        }else{
            make.right.equalTo(weakSelf.contentView).offset(-30);
        }
        make.centerY.equalTo(weakSelf.contentView);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];
    
    _oldPassWordTextField = [[UITextField alloc] init];
    _oldPassWordTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    _oldPassWordTextField.secureTextEntry = YES;
    _oldPassWordTextField.font = [UIFont systemFontOfSize:14];
    _oldPassWordTextField.borderStyle = UITextBorderStyleNone;
    _oldPassWordTextField.placeholder = @"请输入原密码";
    [self.contentView addSubview:_oldPassWordTextField];
    [_oldPassWordTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(label.mas_right).offset(10);
        make.centerY.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@25);
        make.right.equalTo(weakSelf.contentView).offset(-50);
    }];
    [_oldPassWordTextField.rac_textSignal subscribeNext:^(NSString *x){
        weakSelf.oldpwdeyeButton.hidden = x.length ? NO : YES;
        if(weakSelf.passwordBlock) {
            weakSelf.passwordBlock(x);
        }
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
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];

}

- (void)bindRac
{
    WS(weakSelf);
    [RACObserve(self.oldPassWordTextField,secureTextEntry)subscribeNext:^(NSNumber * secure) {
        if (weakSelf.oldPassWordTextField.secureTextEntry) {
            [weakSelf.oldpwdeyeButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
        }else{
            [weakSelf.oldpwdeyeButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        }
    }];
}

- (void)showPwdFunction:(id)sender{
    
    self.oldPassWordTextField.secureTextEntry = !self.oldPassWordTextField.secureTextEntry;
}

@end
