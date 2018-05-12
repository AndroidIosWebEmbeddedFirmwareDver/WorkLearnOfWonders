//
//  LoginInputView.m
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "LoginInputView.h"

#define LeaderSize CGSizeMake(30, 30)

@interface LoginInputView ()


@end

@implementation LoginInputView

- (instancetype)init {
    self = [super init];
    if (self) {
        self.isCodeView = NO;
    }
    return self;
}

#pragma mark - 输入框模式
- (void)setupPlaceHolder: (NSString *)placeHolder
             leaderImage: (NSString *)imageName
              needSecure: (BOOL)needSecure {
    WS(weakSelf);
    UIImage *image = [UIImage imageNamed: imageName];
    UIImageView *leaderView = [UISetupView setupImageViewWithSuperView: self
                                                         withImageName: imageName];
    
    CGFloat w = image ? image.size.width : LeaderSize.width;
    CGFloat h = image ? image.size.height : LeaderSize.height;
    
    [leaderView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(w, h));
        make.left.equalTo(weakSelf).offset(22.0);
        make.top.equalTo(weakSelf);
    }];
    
//    UIView *lineView = [UISetupView setupViewWithSuperView: self
//                                       withBackGroundColor: HEX_COLOR(0X979797)];
//    [lineView mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.size.mas_equalTo(CGSizeMake(0.5, 14.0));
//        make.centerY.equalTo(self);
//        make.left.equalTo(leaderView.mas_right).offset(18.0);
//    }];

    UITextField *textFiled = [UISetupView setupTextFieldWithSuperView: self
                                                             withText: @""
                                                        withTextColor: [UIColor tc1Color]
                                                         withFontSize: 16.0
                                                      withPlaceholder: placeHolder
                                                         withDelegate: self
                                                    withReturnKeyType: UIReturnKeyDone
                                                     withKeyboardType:UIKeyboardTypeDefault];
    [textFiled setSecureTextEntry: needSecure];
    [textFiled setClearButtonMode: UITextFieldViewModeWhileEditing];
    [textFiled mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(23.0);
        make.left.equalTo(leaderView.mas_right).offset(24.0);
        make.right.equalTo(weakSelf).offset(-20.0);
        make.centerY.equalTo(leaderView);
    }];
    self.textFiled = textFiled;
    
    [UISetupView setupBottomLineViewWithSuperView: self withSpace: 20.0];
    
}


#pragma mark - 带验证码输入框模式
- (void)setupCodeInputShortView: (NSString *)placeHolder
                    leaderImage: (NSString *)imageName
                 needBottomLine: (BOOL)needLine {
    
    self.isCodeView = YES;

    WS(weakSelf);
    UIImage *image = [UIImage imageNamed: imageName];
    
    CGFloat w = image ? image.size.width : LeaderSize.width;
    CGFloat h = image ? image.size.height : LeaderSize.height;

    UIImageView *leaderView = [UISetupView setupImageViewWithSuperView: self
                                                         withImageName: imageName];
    
    [leaderView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(w, h));
        make.left.equalTo(weakSelf).offset(22.0);
        make.top.equalTo(weakSelf);
    }];
    
    
    
    UIButton *sendBtn = [UISetupView setupButtonWithSuperView: self
                                       withTitleToStateNormal: @"获取验证码"
                                  withTitleColorToStateNormal: [UIColor tc9Color]
                                            withTitleFontSize: 14.0 withAction:^(UIButton *sender) {
                                                
                                            }];
    
    [sendBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf).offset(-20.0);
        make.centerY.equalTo(leaderView);
        make.height.equalTo(leaderView);
        make.width.mas_equalTo(80.0);
    }];
    self.smsButton = sendBtn;
    
    //倒计时label
    UILabel *countlbl = [UISetupView setupLabelWithSuperView: self
                                                    withText: @""
                                               withTextColor: [UIColor tc4Color] withFontSize: 16.0];
    [countlbl setTextAlignment: NSTextAlignmentCenter];
    [countlbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(sendBtn);
    }];
    [countlbl setAlpha: 0.0];
    self.countDownLabel = countlbl;
    
    
    UIView *lineView = [UISetupView setupViewWithSuperView: self
                                       withBackGroundColor: HEX_COLOR(0X979797)];
    [lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(0.5, 14.0));
        make.centerY.equalTo(sendBtn);
        make.right.equalTo(sendBtn.mas_left).offset(-15.0);
    }];
    lineView.alpha = 0.0;
    UITextField *textFiled = [UISetupView setupTextFieldWithSuperView: self
                                                             withText: @""
                                                        withTextColor: [UIColor tc1Color]
                                                         withFontSize: 16.0
                                                      withPlaceholder: placeHolder
                                                         withDelegate: self
                                                    withReturnKeyType: UIReturnKeyDone
                                                     withKeyboardType:UIKeyboardTypeDefault];
    [textFiled setKeyboardType: UIKeyboardTypeNumberPad];
    [textFiled setClearButtonMode: UITextFieldViewModeWhileEditing];
    [textFiled mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(leaderView.mas_right).offset(24.0);
        make.height.equalTo(leaderView);
        make.centerY.equalTo(leaderView);
        make.right.equalTo(lineView.mas_left).offset(-5);
    }];
    self.textFiled = textFiled;

    if (needLine) {
        [UISetupView setupBottomLineViewWithSuperView: self withSpace: 20.0];
    }
}



#pragma mark - 带标题输入框模式
- (void)setupInputWithImage: (NSString *)headImage
                  withTilte: (NSString *)title
            withPlaceHolder: (NSString *)placeHolder
             needBottomLine: (BOOL)needLine {
    
    
    WS(weakSelf);
    UIImage *image = [UIImage imageNamed: headImage];
    CGFloat w = image ? image.size.width : LeaderSize.width;
    CGFloat h = image ? image.size.height : LeaderSize.height;

    UIImageView *leaderView = [UISetupView setupImageViewWithSuperView: self
                                                         withImageName: headImage];
    [leaderView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(w, h));
        make.left.equalTo(weakSelf).offset(10.0);
        make.top.equalTo(weakSelf);
    }];
    
    UILabel *titleLabel = [UISetupView setupLabelWithSuperView: self
                                                      withText: title
                                                 withTextColor: [UIColor tc4Color]
                                                  withFontSize: 16.0];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(leaderView.mas_right).offset(10.0);
        make.centerY.equalTo(leaderView.mas_centerY);
        make.width.mas_equalTo(64.0);
    }];

    
    UITextField *textFiled = [UISetupView setupTextFieldWithSuperView: self
                                                             withText: @""
                                                        withTextColor: [UIColor tc1Color]
                                                         withFontSize: 16.0
                                                      withPlaceholder: placeHolder
                                                         withDelegate: self
                                                    withReturnKeyType: UIReturnKeyDone
                                                     withKeyboardType:UIKeyboardTypeDefault];
    [textFiled setClearButtonMode: UITextFieldViewModeWhileEditing];
    [textFiled setTextAlignment: NSTextAlignmentRight];
    [textFiled mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(leaderView);
        make.height.equalTo(leaderView.mas_height);
        make.left.equalTo(titleLabel.mas_right).offset(5.0);
        make.right.equalTo(weakSelf).offset(-22.0);
    }];
    self.textFiled = textFiled;
    
    if (needLine)
        [UISetupView setupBottomLineViewWithSuperView: self withSpace: 20.0];

}

#pragma mark - 带标题选择模式
- (void)setupSelectedWithImage: (NSString *)headImage
                     withTilte: (NSString *)title
                   withContext: (NSString *)context
                needBottomLine: (BOOL)needLine {
    
    WS(weakSelf);
    UIImage *image = [UIImage imageNamed: headImage];
    CGFloat w = image ? image.size.width : LeaderSize.width;
    CGFloat h = image ? image.size.height : LeaderSize.height;
    
    UIImageView *leaderView = [UISetupView setupImageViewWithSuperView: self
                                                         withImageName: headImage];
    [leaderView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(w, h));
        make.left.equalTo(weakSelf).offset(10.0);
        make.top.equalTo(weakSelf);
    }];
    
    UILabel *titleLabel = [UISetupView setupLabelWithSuperView: self
                                                      withText: title
                                                 withTextColor: [UIColor tc4Color]
                                                  withFontSize: 16.0];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(leaderView.mas_right).offset(10.0);
        make.centerY.equalTo(leaderView.mas_centerY);
        make.width.mas_equalTo(64.0);
    }];
    
    UIImageView *linkImageView = [UISetupView setupImageViewWithSuperView: self
                                                            withImageName: @"link28_hui"];
    [linkImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf).offset(-10.0);
        make.centerY.equalTo(leaderView);
        make.size.mas_equalTo(CGSizeMake(7.0, 14.0));
    }];

    
    UILabel *contextLabel = [UISetupView setupLabelWithSuperView: self
                                                        withText: context
                                                   withTextColor: [UIColor tc1Color]
                                                    withFontSize: 16.0];
    [contextLabel setTextAlignment: NSTextAlignmentRight];
    [contextLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf).offset(-22.0);
        make.centerY.equalTo(leaderView.mas_centerY);
        make.left.equalTo(titleLabel.mas_right).offset(-10);
    }];
    self.contextLabel = contextLabel;
    
    if (needLine)
        [UISetupView setupBottomLineViewWithSuperView: self withSpace: 20.0];
    
    UIButton *selBtn = [UIButton buttonWithType: UIButtonTypeCustom];
    [self addSubview: selBtn];
    [selBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf);
    }];
    self.selectedBtn = selBtn;

}

#pragma mark - UITextFieldDelegate
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    if (!self.isCodeView) {
        return YES;
    }
    if ([textField.text length] >= 6 && [string length] > 0) {
        return NO;
    }
    return YES;
}


@end
