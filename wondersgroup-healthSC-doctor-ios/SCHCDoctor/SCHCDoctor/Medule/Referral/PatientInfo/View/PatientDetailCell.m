//
//  PatientDetailCell.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientDetailCell.h"
@interface PatientDetailCell () <UITextViewDelegate>

@end

@implementation PatientDetailCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        [self initInterface];
    }
    return self;
}

- (void)initInterface {
    [self setBackgroundColor:RGB_COLOR(246, 246, 246)];
    
    _importantImageView = [[UIImageView alloc] init];
    [self.contentView addSubview:_importantImageView];
    
    _titleLabel = [[UILabel alloc] init];
    [_titleLabel setText:@"标题"];
    [_titleLabel setTextColor:RGB_COLOR(102, 102, 102)];
    [_titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    [self.contentView addSubview:_titleLabel];
    
    _textView = [[UITextView alloc] init];
    [_textView setTextColor:RGB_COLOR(51, 51, 51)];
    [_textView setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:13]];
    _textView.textContainerInset = UIEdgeInsetsMake(10, 14, 27, 14);
    [_textView setDelegate:self];
    _textView.placeholder = @"请输入文字";
    _textView.placeholderColor = RGB_COLOR(187, 187, 187);
    [_textView.placeholderLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:13]];
    [self.contentView addSubview:_textView];
    
    _countLabel = [[UILabel alloc] init];
    [_countLabel setText:@"0/500"];
    [_countLabel setFont:[UIFont systemFontOfSize:12]];
    [_countLabel setTextColor:RGB_COLOR(187, 187, 187)];
    [_countLabel setTextAlignment:NSTextAlignmentRight];
    [self.contentView addSubview:_countLabel];
    
    [self buildConstraint];
}

- (void)buildConstraint {
    [_importantImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.top.equalTo(self.contentView).offset(10);
        make.height.mas_equalTo(14);
        make.width.mas_equalTo(0);
    }];
    
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_importantImageView);
        make.left.equalTo(_importantImageView.mas_right);
        make.right.equalTo(self.contentView).offset(-15);
    }];
    
    [_textView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.contentView);
        make.top.equalTo(_titleLabel.mas_bottom).offset(10);
        make.bottom.equalTo(self.contentView);
        make.height.mas_equalTo(130);
    }];
    
    [_countLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(_textView).offset(-15);
        make.bottom.equalTo(_textView).offset(-10);
        make.height.mas_equalTo(12);
    }];
    
}

- (void)textViewDidChange:(UITextView *)textView {
    NSInteger count = textView.text.length;
    [_countLabel setText:[NSString stringWithFormat:@"%ld/500",count]];
    if (count > 500) {
        [_countLabel setTextColor:[UIColor redColor]];
    } else {
        [_countLabel setTextColor:RGB_COLOR(187, 187, 187)];
    }
    
    if (self.editBlock) {
        MJWeakSelf
        self.editBlock(NO, textView.text, weakSelf);
    }
}

- (void)textViewDidEndEditing:(UITextView *)textView {
    if (self.editBlock) {
        MJWeakSelf
        self.editBlock(YES, textView.text, weakSelf);
    }
}

- (BOOL)textViewShouldBeginEditing:(UITextView *)textView {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"PatientDetailCellEditTextView" object:textView];
    return YES;
}

@end
