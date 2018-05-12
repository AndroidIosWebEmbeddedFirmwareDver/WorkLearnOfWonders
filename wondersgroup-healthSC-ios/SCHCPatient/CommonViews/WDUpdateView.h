//
//  WDUpdateView.h
//  VaccinePatient
//
//  Created by ZJW on 16/9/21.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WDAlertView.h"

@class WDUpdateView;
typedef void (^SubmitUpDateBtnClickBlock)(WDUpdateView *view);
typedef void (^CancelUpdateBtnClickBlock)(WDUpdateView *view);

@interface WDUpdateView : UIView
{
    WDAlertViewType viewType;
}
@property (strong, nonatomic) UIButton *submitBtn;
@property (strong, nonatomic) UIButton *cancelBtn;
@property (strong, nonatomic) UILabel *titleLabel;
@property (strong, nonatomic) UILabel *contentLabel;
@property (nonatomic, strong) UINavigationController *nav;
@property (nonatomic, strong) SubmitUpDateBtnClickBlock submitBlock;
@property (nonatomic, strong) CancelUpdateBtnClickBlock cancelBlock;
@property (nonatomic, strong) UIView *bgView;
@property (nonatomic, strong) UIView *lineView;
/**
 *  初始化
 *
 *  @param nav 当前的UINavigationController
 *
 *  @return self
 */

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

- (void)loadTitle:(NSString *)title;

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




// WDUpdateView * _goToRealNameAuthenticaView= [[WDUpdateView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
// [[UIApplication sharedApplication].keyWindow addSubview:_goToRealNameAuthenticaView];
// NSString *titlt     = [NSString stringWithFormat:@"发现新版本V%@",[TaskManager manager].appConfig.appUpdate.appVersion];
// NSString *content   = @"123123123123123123123123123123123";//[TaskManager manager].appConfig.appUpdate.updateMsg;
// [_goToRealNameAuthenticaView showViewWithHaveBackAction:NO withHaveBackView:YES];
// [_goToRealNameAuthenticaView reloadTitle:titlt content:content];
//
// [_goToRealNameAuthenticaView mas_makeConstraints:^(MASConstraintMaker *make) {
//
// make.centerX.centerY.equalTo([UIApplication sharedApplication].keyWindow);
// make.height.mas_equalTo(@(390. / 2));
// make.width.mas_equalTo(SCREEN_WIDTH - 20);
// }];
//
// [_goToRealNameAuthenticaView.submitBtn setTitle:@"升级" forState:UIControlStateNormal];
//
// WS(weakSelf)
// _goToRealNameAuthenticaView.cancelBlock = ^(WDUpdateView *view){
// //
// //        [weakSelf dismissAlertView];
//
// };
//
// _goToRealNameAuthenticaView.submitBlock = ^(WDUpdateView *view){
//
// //        [weakSelf dismissAlertView];
//
// /* 去实名认证 */ /*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*/
//};



