//
//  WDAlertView.h
//  EyeProtection
//
//  Created by ZJW on 16/3/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIViewController+MJPopupViewController.h"

typedef NS_ENUM(NSInteger, WDAlertViewType) {
    WDAlertViewTypeOne = 0,
    WDAlertViewTypeTwo,
};

@class WDAlertView;
typedef void (^SubmitBtnLogoutClickBlock)(WDAlertView *view);
typedef void (^CancelBtnLogoutClickBlock)(WDAlertView *view);

@interface WDAlertView : UIView

@property (strong, nonatomic) UIButton *submitBtn;
@property (strong, nonatomic) UIButton *cancelBtn;
@property (strong, nonatomic) UILabel *titleLabel;
@property (strong, nonatomic) UILabel *contentLabel;
@property (nonatomic, strong) SubmitBtnLogoutClickBlock submitBlock;
@property (nonatomic, strong) CancelBtnLogoutClickBlock cancelBlock;



/**
 *  初始化
 *
 *  @param nav 当前的UINavigationController
 *  @param type 单个按钮 2个按钮
 *
 *  @return self
 */
-(instancetype)initWithNavigationController:(UINavigationController *)nav withType:(WDAlertViewType)type;

/**
 *  加载数据
 *
 *  @param title   标题
 *  @param content 文本
 */
-(void)reloadTitle:(NSString *)title content:(NSString *)content;

/**
 *  显示
 *
 *  @param isHaveBackAction 是否要点击view外部区域消失view
 *  @param isHaveBackView   是否要显示view外部区域为50%的黑色背景
 */
-(void)showViewWithHaveBackAction:(BOOL)isHaveBackAction withHaveBackView:(BOOL)isHaveBackView;

/**
 *  解散
 */
-(void)dismiss;

@end






