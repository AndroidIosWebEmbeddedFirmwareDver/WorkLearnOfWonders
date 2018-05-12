//
//  BaseViewController.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef NS_ENUM(NSInteger, WDViewControllerBackType) {
    WDViewControllerBackNomal = 0,
    WDViewControllerBackToRoot,
    WDViewControllerBackToRootH5First,
};

typedef void (^BFVoidBlock)(void);

@interface BaseViewController : UIViewController

//需要返回按钮
@property (nonatomic, assign)   BOOL hasBack;
@property (nonatomic, assign)   WDViewControllerBackType backType;
@property (nonatomic, strong) BFVoidBlock forceBackBlock;
//@property (nonatomic, assign)   BOOL needHiddenBar;

/**
 是否显示返回首页的文字
 */
@property (nonatomic, assign) BOOL isShowTitlePopRoot;

/**
 *  返回
 */
- (void)popBack;

/**
 *  返回
 *
 *  @param vcStr 需要返回的页面
 */
- (void)popBackToViewController:(NSString *)vcStr;


/**
 导航栏右边的按钮

 @param string 显示的文字
 @param sel    事件
 */
-(void)setRightItemWithString:(NSString *)string withAction:(SEL)sel;


/**
 判断是否一个类是否加载过

 @param vcString 类名
 @return yes Or no
 */
-(BOOL)isHaveViewControllerWithClassString:(NSString *)vcString;


@end
