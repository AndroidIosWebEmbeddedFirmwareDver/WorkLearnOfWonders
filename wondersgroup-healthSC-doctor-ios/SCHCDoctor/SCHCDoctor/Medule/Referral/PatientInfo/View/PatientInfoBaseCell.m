//
//  PatientInfoBaseCell.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientInfoBaseCell.h"


@interface PatientInfoBaseCell () <UITextViewDelegate, UITextFieldDelegate>



@end

@implementation PatientInfoBaseCell

#pragma mark - event
- (void)showTopBlank:(BOOL)isShow {
    [_topBlankLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.contentView);
        make.height.mas_equalTo(isShow ? 10 : 0);
    }];
}

- (void)showBottomLine:(BOOL)isShow {
    [_bottomLineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.mas_equalTo(15);
        make.bottom.right.equalTo(self.contentView);
        make.height.mas_equalTo(isShow ? 1 : 0);
    }];
}

- (void)reSetTextView {
    InfoBaseTextView * textView = [[InfoBaseTextView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH - 100, 1)];
    [textView setTextAlignment:NSTextAlignmentRight];
    textView.textContainerInset = UIEdgeInsetsMake(13, 0, 0, 0);
    [textView setDelegate:self];
    [textView setTextColor:RGB_COLOR(51, 51, 51)];
    [textView setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    
    CGFloat height = textView.contentSize.height;
    if (height < 45) {
        height = 45;
    }
    textView = nil;
    [_textView mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(self.contentView);
        make.left.equalTo(_titleLabel.mas_right).offset(5);
        make.right.equalTo(self.contentView).offset(-27);
        make.height.mas_equalTo(height);
    }];
}
#pragma mark - setter
- (void)setImportant:(BOOL)isImportant {
    [_importantLabel setText:isImportant ? @"❋" : @""];
    
    [_importantLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.top.bottom.equalTo(_titleLabel);
        if (isImportant) {
            make.width.mas_equalTo(8);
        } else {
            make.width.mas_equalTo(0);
        }
    }];
}


#pragma mark - getter
- (UILabel *)configTopBlankLine {
    if(!_topBlankLabel){
        _topBlankLabel = [[UILabel alloc] init];
        [_topBlankLabel setBackgroundColor:RGB_COLOR(244, 244, 244)];
        [self.contentView addSubview:_topBlankLabel];
    }
    return _topBlankLabel;
}

- (UILabel *)configTitleLabel {
    if (!_titleLabel) {
        _titleLabel = [[UILabel alloc] init];
        [_titleLabel setTextColor:RGB_COLOR(102, 102, 102)];
        [_titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [_titleLabel setText:@"标题"];
        [self.contentView addSubview:_titleLabel];
    }
    return _titleLabel;
}

- (UILabel *)configImportantLabel {
    if (!_importantLabel) {
        _importantLabel = [[UILabel alloc] init];
        [_importantLabel setText:@"❋"];
        [_importantLabel setTextColor:RGB_COLOR(245, 103, 53)];
        [_importantLabel setContentMode:UIViewContentModeCenter];
        [_importantLabel setFont:[UIFont systemFontOfSize:10]];
        [self.contentView addSubview:_importantLabel];
    }
    return _importantLabel;
}

- (UILabel *)configDetailLabel {
    if (!_detailLabel) {
        _detailLabel = [[UILabel alloc] init];
        [_detailLabel setTextColor:RGB_COLOR(51, 51, 51)];
        [_detailLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [self.contentView addSubview:_detailLabel];
    }
    return _detailLabel;
}

- (UIImageView *)configArrowImageView {
    if (!_arrowImageView) {
        _arrowImageView = [[UIImageView alloc] init];
        [_arrowImageView setImage:[UIImage imageNamed:@"link_right"]];
        [_arrowImageView setContentMode:UIViewContentModeCenter];
        [self.contentView addSubview:_arrowImageView];
    }
    return _arrowImageView;
}

- (UITextField *)configTextField {
    if (!_textField) {
        _textField = [[UITextField alloc] init];
        [_textField setTextColor:RGB_COLOR(51, 51, 51)];
        [_textField setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [_textField setTextAlignment:NSTextAlignmentRight];
        [_textField setUserInteractionEnabled:YES];
        _textField.returnKeyType = UIReturnKeyDone;
        [_textField setDelegate:self];
        [self.contentView addSubview:_textField];
    }
    return _textField;
}

- (InfoBaseTextView *)configTextView {
    if (!_textView) {
        _textView = [[InfoBaseTextView alloc] init];
        [_textView setTextAlignment:NSTextAlignmentRight];
        _textView.textContainerInset = UIEdgeInsetsMake(13, 0, 0, 0);
        [_textView setDelegate:self];
        [_textView setTextColor:RGB_COLOR(51, 51, 51)];
        [_textView setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [self.contentView addSubview:_textView];
    }
    return _textView;
}

- (UILabel *)configBottomLine {
    if (!_bottomLineLabel) {
        _bottomLineLabel = [[UILabel alloc] init];
        [_bottomLineLabel setBackgroundColor:RGB_COLOR(244, 244, 244)];
        [self.contentView addSubview:_bottomLineLabel];
    }
    return _bottomLineLabel;

}
#pragma mark - delegate
- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (self.editBlock) {
        MJWeakSelf
        self.editBlock(YES,textField.text, weakSelf);
    }
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    if (self.editBlock) {
        MJWeakSelf
        NSString * endString = [textField.text stringByReplacingCharactersInRange:range withString:string];
        self.editBlock(NO,endString, weakSelf);
    }
    return YES;
}

- (void)textViewDidEndEditing:(UITextView *)textView {
    
    if (self.editBlock) {
        MJWeakSelf
        self.editBlock(YES,textView.text, weakSelf);
    }
}

- (void)textViewDidChange:(UITextView *)textView {
    if (self.editBlock) {
        MJWeakSelf
        self.editBlock(NO,textView.text, weakSelf);
    }
}

@end
