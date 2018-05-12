//
//  LoginInputView.h
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

//@protocol SendSMSDelegate <NSObject>
//
//- (void)userSendSMS;
//
//@end

@interface LoginInputView : UIView

@property (nonatomic, strong) UITextField *textFiled;
@property (nonatomic, strong) UIButton    *smsButton;
@property (nonatomic, strong) UILabel     *countDownLabel;
@property (nonatomic, strong) UILabel     *contextLabel;
@property (nonatomic, strong) UIButton    *selectedBtn;
@property (nonatomic, assign) BOOL isCodeView;
//@property (nonatomic, assign) id<SendSMSDelegate> sendDelegate;

//输入框模式
- (void)setupPlaceHolder: (NSString *)placeHolder
             leaderImage: (NSString *)imageName
              needSecure: (BOOL)needSecure;


//- (void)setupCodeInputShortView: (NSString *)placeHolder
//                    leaderImage: (NSString *)imageName;
//

//带验证码输入框模式
- (void)setupCodeInputShortView: (NSString *)placeHolder
                    leaderImage: (NSString *)imageName
                 needBottomLine: (BOOL)needLine;


//带标题输入框模式
- (void)setupInputWithImage: (NSString *)headImage
                  withTilte: (NSString *)title
            withPlaceHolder: (NSString *)placeHolder
             needBottomLine: (BOOL)needLine;

//带标题选择模式
- (void)setupSelectedWithImage: (NSString *)headImage
                     withTilte: (NSString *)title
                   withContext: (NSString *)context
                needBottomLine: (BOOL)needLine;



@end
