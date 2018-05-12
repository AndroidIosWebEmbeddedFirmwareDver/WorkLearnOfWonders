//
//  SCNewPasswprdCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCNewPasswprdCell.h"

@implementation SCNewPasswprdCell

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
    label.text = @"新密码";
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
    
    _myNewTf = [[UITextField alloc] init];
    _myNewTf = [UITextField new];
    _myNewTf.clearButtonMode = UITextFieldViewModeWhileEditing;
    _myNewTf.secureTextEntry = YES;
    if (SCREEN_HEIGHT <= 568.0) {
        _myNewTf.font = [UIFont systemFontOfSize:12];
    } else {
        _myNewTf.font = [UIFont systemFontOfSize:14];
    }
    _myNewTf.borderStyle = UITextBorderStyleNone;
    _myNewTf.placeholder = @"长度6-16位,字母与数字组合";
    [weakSelf.contentView addSubview:_myNewTf];
    [_myNewTf mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(label.mas_right).offset(10);
        make.centerY.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@25);
        make.right.equalTo(weakSelf.contentView).offset(-50);
    }];
    
    _newpwdeyeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [_newpwdeyeButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
    [_newpwdeyeButton addTarget:self action:@selector(showNewPwdFunction:) forControlEvents:UIControlEventTouchUpInside];
    [self.contentView addSubview:_newpwdeyeButton];
    [self.newpwdeyeButton mas_makeConstraints:^(MASConstraintMaker *make) {
        if (_newpwdeyeButton || IS_IPHONE_4_OR_LESS){
            make.right.equalTo(weakSelf.contentView).offset(-20);
        }else{
            make.right.equalTo(weakSelf.contentView).offset(-30);
        }
        make.centerY.equalTo(weakSelf.contentView);
        make.size.mas_equalTo(CGSizeMake(40., 40.));
    }];
    [_myNewTf.rac_textSignal subscribeNext:^(NSString *x){
        weakSelf.newpwdeyeButton.hidden = x.length ? NO : YES;
        if(weakSelf.textfieldblocK) {
            weakSelf.textfieldblocK(x);
        }
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
    [RACObserve(weakSelf.myNewTf,secureTextEntry)subscribeNext:^(NSNumber * secure) {
        if (weakSelf.myNewTf.secureTextEntry) {
            [weakSelf.newpwdeyeButton setImage:[UIImage imageNamed:@"icon眼灰"] forState:UIControlStateNormal];
        }else{
            [weakSelf.newpwdeyeButton setImage:[UIImage imageNamed:@"icon眼蓝"] forState:UIControlStateNormal];
        }
    }];
}

- (void)showNewPwdFunction:(id)sender{
    
    self.myNewTf.secureTextEntry = !self.myNewTf.secureTextEntry;
}

@end
