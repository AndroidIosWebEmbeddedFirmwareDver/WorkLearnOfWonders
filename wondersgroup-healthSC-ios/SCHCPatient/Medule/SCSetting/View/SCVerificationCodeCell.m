//
//  SCVerificationCodeCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCVerificationCodeCell.h"

@implementation SCVerificationCodeCell


- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubview];
        [self RACBind];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}

- (void)setupSubview
{
    WS(weakSelf);
    UILabel *label = [[UILabel alloc] init];
    label.text = @"验证码";
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
    _oldPassWordTextField.placeholder = @"请输入验证码";
    [weakSelf.contentView addSubview:_oldPassWordTextField];
    [_oldPassWordTextField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(label.mas_right).offset(10);
        make.centerY.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@25);
        make.width.equalTo(@90);
    }];
    [_oldPassWordTextField.rac_textSignal subscribeNext:^(id x){
        NSLog(@"%@",x);//这里的X就是文本框的文字
        if(weakSelf.inCodeAction) {
            weakSelf.inCodeAction(x);
        }
    }];
    
    _getVeryCodeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [_getVeryCodeButton setTitle:@"获取验证码" forState:UIControlStateNormal];
    _getVeryCodeButton.titleLabel.font = [UIFont systemFontOfSize:14];
    [_getVeryCodeButton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    //_getVeryCodeButton.alpha = 0.5;
    [weakSelf.contentView addSubview:_getVeryCodeButton];
    [_getVeryCodeButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(@80);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
    }];
    [[_getVeryCodeButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^( id x) {
        if (weakSelf.getCodeAction) {
            weakSelf.getCodeAction(_oldPassWordTextField.text);
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

- (void)RACBind {
    WS(weakSelf)
    [RACObserve([CountDownManager manager], countDownBind) subscribeNext:^(NSNumber *countDownMobile) {
        if ([countDownMobile intValue] == -1) {
           
            weakSelf.getVeryCodeButton.enabled = YES;
        }else {
            [weakSelf.getVeryCodeButton setTitle:[NSString stringWithFormat:@"(%d)", [countDownMobile intValue]] forState:UIControlStateDisabled];
            weakSelf.getVeryCodeButton.enabled = NO;
        }
    }];
        
}


@end
