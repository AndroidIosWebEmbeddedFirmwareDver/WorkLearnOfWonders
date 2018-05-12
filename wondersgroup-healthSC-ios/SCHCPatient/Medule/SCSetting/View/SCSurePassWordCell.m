//
//  SCSurePassWordCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSurePassWordCell.h"

@implementation SCSurePassWordCell


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
    label.text = @"确认密码";
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
    
    _surePassWordTextField = [[UITextField alloc] init];
    _surePassWordTextField.clearButtonMode = UITextFieldViewModeWhileEditing;
    _surePassWordTextField.secureTextEntry = YES;
    if (SCREEN_HEIGHT <= 568.0) {
        _surePassWordTextField.font = [UIFont systemFontOfSize:12];
    } else {
        _surePassWordTextField.font = [UIFont systemFontOfSize:14];
    }
    _surePassWordTextField.borderStyle = UITextBorderStyleNone;
    _surePassWordTextField.placeholder = @"长度6-16位,字母与数字组合";
    [weakSelf.contentView addSubview:_surePassWordTextField];
    [_surePassWordTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(label.mas_right).offset(10);
        make.centerY.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@25);
        make.right.equalTo(weakSelf.contentView).offset(-50);
    }];
    
    _surepwdeyeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [_surepwdeyeButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
    [_surepwdeyeButton addTarget:self action:@selector(showNewPwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    [self.contentView addSubview:_surepwdeyeButton];
    [self.surepwdeyeButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (_surepwdeyeButton || IS_IPHONE_4_OR_LESS){
            make.right.equalTo(weakSelf.contentView).offset(-20);
        }else{
            make.right.equalTo(weakSelf.contentView).offset(-30);
        }
        make.centerY.equalTo(weakSelf.contentView);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];
    [_surePassWordTextField.rac_textSignal subscribeNext:^(NSString *x){
        weakSelf.surepwdeyeButton.hidden = x.length ? NO : YES;
        if(weakSelf.suretextfieldblocK) {
            weakSelf.suretextfieldblocK(x);
        }
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

- (void)bindRac
{
    WS(weakSelf);
    [RACObserve(weakSelf.surePassWordTextField,secureTextEntry)subscribeNext:^(NSNumber * secure) {
        if (weakSelf.surePassWordTextField.secureTextEntry) {
            [weakSelf.surepwdeyeButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
        }else{
            [weakSelf.surepwdeyeButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        }
    }];
}

- (void)showNewPwdFunction:(id)sender{
    
    self.surePassWordTextField.secureTextEntry = !self.surePassWordTextField.secureTextEntry;
}

@end
