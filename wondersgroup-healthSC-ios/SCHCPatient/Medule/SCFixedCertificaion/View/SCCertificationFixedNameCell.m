//
//  SCCertificationFixedNameCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCCertificationFixedNameCell.h"

@implementation SCCertificationFixedNameCell

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
    label.textColor = [UIColor tc2Color];
    label.text = @"姓名";
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(25);
    }];
    
    _nameTf = [[UITextField alloc] init];
    _nameTf.textAlignment = NSTextAlignmentRight;
    _nameTf.font = [UIFont systemFontOfSize:14];
    _nameTf.borderStyle = UITextBorderStyleNone;
    _nameTf.placeholder = @"请输入真实姓名";
    [self.contentView addSubview:_nameTf];
    [_nameTf mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@25);
        make.width.equalTo(@110);
    }];
    [_nameTf addTarget:self action:@selector(textFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
//    [_nameTf.rac_textSignal subscribeNext:^(NSString *x){
//        NSLog(@"%@",x);//这里的X就是文本框的文字
//        if (x.length > 20) {
//            _nameTf.text = [_nameTf.text substringToIndex:20];
//        }
//        if(weakSelf.nameBlock) {
//            weakSelf.nameBlock(_nameTf.text);
//        }
//    }];

    UILabel *lineLabel = [[UILabel alloc] init];
    lineLabel.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:lineLabel];
    [lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)textFieldDidChange:(UITextField *)textField
{
    static const NSInteger kMaxLength = 20;
    NSString *toBeString = self.nameTf.text;
    UITextInputMode *currentInputMode = _nameTf.textInputMode;
    NSString *lang = currentInputMode.primaryLanguage;
    if ([lang isEqualToString:@"zh-Hans"]) { //简体中文输入，包括简体拼音，健体五笔，简体
        UITextRange *selectedRange = [self.nameTf markedTextRange];
        UITextPosition *position = [self.nameTf positionFromPosition:selectedRange.start offset:0];
        if (!position) {//非高亮
            if (toBeString.length > kMaxLength) {
                self.nameTf.text = [toBeString substringToIndex:kMaxLength];
            }
            if (self.nameBlock) {
                self.nameBlock(_nameTf.text);
            }
        }
    } else {//中文输入法以外
        if (toBeString.length > kMaxLength) {
            self.nameTf.text = [toBeString substringToIndex:kMaxLength];
        }
        if (self.nameBlock) {
            self.nameBlock(_nameTf.text);
        }
        
    }
}

@end
