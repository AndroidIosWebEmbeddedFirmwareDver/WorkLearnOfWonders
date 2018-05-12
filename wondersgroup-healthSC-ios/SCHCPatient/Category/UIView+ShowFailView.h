//
//  UIView+ShowFailView.h
//  CNHealthCloudDoctor
//
//  Created by ZJW on 16/5/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, FailViewType) {
    FailViewStart = 99,        //展示正在加载页面
    FailViewEmpty,        //展示空页面
    FailViewNoWifi,       //展示没有网络
    FailViewError,        //展示错误页面
    FailViewUnknow        //未知页面
};


@interface UIView (ShowFailView)


/**
 *  展示失败页面
 *
 *  @param type       失败类型 FailViewType
 *  @param action     失败界面点击所需要再次Request的方法
 */
- (void)showFailView:(FailViewType)type withAction:(void (^)())action;

/**
 *  在UITableView上展示失败页面
 *
 *  @param type       失败类型 FailViewType
 *  @param isShowTableViewHeadView     是否显示TableViewHeadView
 *  @param action     失败界面点击所需要再次Request的方法
 */
- (void)showTableFailView:(FailViewType)type withTableViewShowHeadView:(BOOL)isShowTableViewHeadView withAction:(void (^)())action;

/**
 *  展示失败页面
 *
 *  @param image  显示图片
 *  @param title  显示文本
 *  @param action     失败界面点击所需要再次Request的方法
 */
- (void)showFailViewWith:(UIImage *)image withTitle:(NSString *)title withAction:(void (^)())action;


/**
 *  展示失败页面
 *
 *  @param type         失败类型 FailViewType
 *  @param action       失败界面点击所需要再次Request的方法
 */
- (void)showFailViewWith:(FailViewType)type withAction:(void (^)())action;

/**
 *  关闭失败页面
 */
- (void)hiddenFailView;


@end
