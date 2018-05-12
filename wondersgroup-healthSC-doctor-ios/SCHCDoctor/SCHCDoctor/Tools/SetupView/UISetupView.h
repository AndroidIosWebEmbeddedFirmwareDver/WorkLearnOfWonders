//
//  HCSetupView.h
//  HCPatient
//
//  Created by ZJW on 16/3/16.
//  Copyright © 2016年 陈刚. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UISetupView : NSObject

/**
 *  初始化UILabel
 *
 *  @param view         父视图
 *  @param text         文本
 *  @param textColor    文本颜色
 *  @param fontSize     文本大小
 *
 *  @return UILabel
 */
+ (UILabel *) setupLabelWithSuperView:(UIView *)view withText:(NSString *)text withTextColor:(UIColor *)textColor withFontSize:(CGFloat)fontSize ;

/**
 *  初始化UIImageView
 *
 *  @param view         父视图
 *  @param imageName    图片名字
 *
 *  @return UIImageView
 */
+ (UIImageView *) setupImageViewWithSuperView:(UIView *)view withImageName:(NSString *)imageName ;

/**
 *  初始化UIButton
 *
 *  @param view             父视图
 *  @param title            文本
 *  @param titleColor       文本颜色
 *  @param titleFontSize    文本大小
 *  @param action           点击事件UIControlEventTouchUpInside
 *
 *  @return UIButton
 */
+ (UIButton *) setupButtonWithSuperView:(UIView *)view withTitleToStateNormal:(NSString *)title withTitleColorToStateNormal:(UIColor *)titleColor withTitleFontSize:(CGFloat)titleFontSize withAction:(void(^)(UIButton *sender))action;

/**
 *  初始化线
 *
 *  @param view     父视图
 *
 *  @return UIView
 */
+ (UIView *) setupLineViewWithSuperView:(UIView *)view ;

/**
 *  初始化线
 *
 *  @param view     父视图
 *
 *  @return UIView
 */
+ (UIView *) setupLineViewWithSuperView:(UIView *)view color:(UIColor *)color;

/**
 *  初始化Bottom线
 *
 *  @param view     父视图
 *  @param space    左右间距 0为满屏线
 *
 *  @return UIView
 */
+ (UIView *) setupBottomLineViewWithSuperView:(UIView *)view withSpace:(CGFloat)space;

/**
 *  初始化Top线
 *
 *  @param view     父视图
 *  @param space    左右间距 0为满屏线
 *
 *  @return UIView
 */
+ (UIView *) setupTopLineViewWithSuperView:(UIView *)view withSpace:(CGFloat)space;

/**
 *  初始化UIView
 *
 *  @param view     父视图
 *  @param color    背景颜色
 *
 *  @return UIView
 */
+ (UIView *) setupViewWithSuperView:(UIView *)view withBackGroundColor:(UIColor *)color;

/**
 *  初始化UITableView
 *
 *  @param view         父视图
 *  @param style        UITableViewStyle
 *  @param delegate     代理
 *
 *  @return UITableView
 */
+ (UITableView *) setupTableViewWithSuperView:(UIView *)view withStyle:(UITableViewStyle)style withDelegateAndDataSource:(id)delegate;

/**
 *  初始化UITextView
 *
 *  @param view             父视图
 *  @param text             文本
 *  @param textColor        文本颜色
 *  @param fontSize         文本大小
 *  @param delegate         代理
 *  @param returnKeyType    确定按钮样式
 *  @param keyboardType     键盘样式
 *
 *  @return UITextView
 */
+ (UITextView *) setupTextViewWithSuperView:(UIView *)view withText:(NSString *)text withTextColor:(UIColor *)textColor withFontSize:(CGFloat)fontSize withDelegate:(id)delegate withReturnKeyType:(UIReturnKeyType)returnKeyType withKeyboardType:(UIKeyboardType)keyboardType;

/**
 *  初始化UITextField
 *
 *  @param view             父视图
 *  @param text             文本
 *  @param textColor        文本颜色
 *  @param fontSize         文本大小
 *  @param placeholder      占位符文本
 *  @param delegate         代理
 *  @param returnKeyType    确定按钮样式
 *  @param keyboardType     键盘样式
 *
 *  @return UITextField
 */
+ (UITextField *) setupTextFieldWithSuperView:(UIView *)view withText:(NSString *)text withTextColor:(UIColor *)textColor withFontSize:(CGFloat)fontSize withPlaceholder:(NSString *)placeholder withDelegate:(id)delegate withReturnKeyType:(UIReturnKeyType)returnKeyType withKeyboardType:(UIKeyboardType)keyboardType;

/**
 *  初始化UIScrollView
 *
 *  @param view                 父视图
 *  @param delegate             代理
 *  @param isPagingEnabled      是否分页
 *
 *  @return UIScrollView
 */
+ (UIScrollView *) setupScrollViewWithSuperView:(UIView *)view withDelegate:(id)delegate withPagingEnabled:(BOOL)isPagingEnabled;


/**
 *  初始化虚线
 *
 *  @param view        父视图
 *  @param color       虚线的颜色
 *  @param size        虚线的宽高
 *  @param lineLength  虚线的宽度
 *  @param lineSpacing 虚线的间距
 *
 *  @return UIView
 */
+ (UIView *) setupDashLineWithSuperView:(UIView *)view withLineColor:(UIColor *)color withSize:(CGSize)size withLineLength:(int)lineLength withLineSpacing:(int)lineSpacing;


@end
