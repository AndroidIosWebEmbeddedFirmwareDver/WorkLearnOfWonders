//
//  PatientSearchView.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientSearchView.h"

@implementation PatientSearchView

- (instancetype)init {
    
    if (self == [super init]) {
        [self prepareUI];
        [self bindRac];
    }
    
    return self;
}

- (instancetype)initWithFrame:(CGRect)frame {
    if (self == [super initWithFrame:frame]) {
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    self.backgroundColor = [UIColor bc1Color];
    WS(weakSelf)
    UIView *backView = [[UIView alloc] init];
    backView.backgroundColor = [UIColor colorWithHex:0xF4F5F9];
    backView.layer.masksToBounds = YES;
    backView.layer.cornerRadius = 3;
    [self addSubview:backView];
    
    [backView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.mas_top).offset(15);
        make.bottom.equalTo(weakSelf.mas_bottom).offset(-15);
        make.left.equalTo(weakSelf.mas_left).offset(38);
        make.right.equalTo(weakSelf.mas_right).offset(-38);
    }];
    
    //可以用leftView代替
    UIImageView *searchIcon = [[UIImageView alloc] init];
    searchIcon.image = [UIImage imageNamed:@"icon关闭有底色"];
    [self addSubview:searchIcon];
    
    [searchIcon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(backView.mas_top).offset(10);
        make.left.equalTo(backView.mas_left).offset(10);
        make.size.mas_equalTo(CGSizeMake(10, 10));
    }];
    
    _textField = [[UITextField alloc] init];
    _textField.borderStyle = UITextBorderStyleNone;
    _textField.font = [UIFont systemFontOfSize:14];
    _textField.textColor = [UIColor tc4Color];
    _textField.returnKeyType = UIReturnKeySearch;
    _textField.delegate = self;
    _textField.placeholder = @"搜索患者";
    _textField.clearButtonMode = UITextFieldViewModeWhileEditing;
    [self addSubview:_textField];
    
    [_textField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.mas_top).offset(0);
        make.bottom.equalTo(weakSelf.mas_bottom).offset(0);
        make.left.equalTo(searchIcon.mas_right).offset(8);
        make.right.equalTo(backView.mas_right).offset(-8);
    }];
}

- (void)bindRac {
    
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField.text.length > 0 && _searchActionBlock) {
        _searchActionBlock(textField.text);
        [_textField resignFirstResponder];
    }
    return YES;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
