//
//  UIView+ShowFailView.m
//  CNHealthCloudDoctor
//
//  Created by ZJW on 16/5/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIView+ShowFailView.h"
#import "FailView.h"

#define SuppressPerformSelectorLeakWarning(Stuff) \
do { \
_Pragma("clang diagnostic push") \
_Pragma("clang diagnostic ignored \"-Warc-performSelector-leaks\"") \
Stuff; \
_Pragma("clang diagnostic pop") \
} while (0)


#define PerformSelector(action) [self performSelector: action withObject: nil]

#define FailViewTag 10010

@implementation UIView (ShowFailView)

- (void)showFailView:(FailViewType)type withAction:(void (^)())action{
    [self showTableFailView:type withTableViewShowHeadView:NO withAction:action];
}

- (void)showTableFailView:(FailViewType)type withTableViewShowHeadView:(BOOL)isShowTableViewHeadView withAction:(void (^)())action{
    if (IS_IOS7 && [self isKindOfClass:[UITableView class]]) {
        NSLog(@"ios7中UITableView上不让加view");
        return;
    }

    [LoadingView hideLoadinForView: self];
    if (![Global global].networkReachable) {
        type = FailViewNoWifi;
    }
    UIImage *failImage = [self viewImage: type];
    NSString *tip      = [self viewTip: type];
    [self viewFailShow: failImage andTip: tip withAction:action withTableViewShowHeadView:isShowTableViewHeadView];
    [self filterScrollActionWithFailView];
}

- (void)showFailViewWith:(UIImage *)image withTitle:(NSString *)title withAction:(void (^)())action {
    [LoadingView hideLoadinForView: self];
    UIImage *failImage = image;
    NSString *tip      = title;
    [self viewFailShow: failImage andTip: tip withAction:action withTableViewShowHeadView:NO];
    [self filterScrollActionWithFailView];
}

- (void)showFailViewWith:(FailViewType)type withAction:(void (^)())action {
    [LoadingView hideLoadinForView: self];
    UIImage *failImage = [self viewImage: type];
    NSString *tip      = [self viewTip: type];
    [self viewFailShow: failImage andTip: tip withAction:action withTableViewShowHeadView:NO];
    [self filterScrollActionWithFailView];
}


#pragma mark    - 关闭失败页面
- (void)hiddenFailView {
    
    if ([self viewWithTag:FailViewTag]) {
        [[self viewWithTag:FailViewTag] setHidden: YES];
        [self replyScrollActionWithFailView];
    }
}

#pragma mark    - 失败展示的View
- (void)viewFailShow:(UIImage *)image andTip:(NSString *)tip withAction:(void (^)())action withTableViewShowHeadView:(BOOL)isShowTableViewHeadView{
    FailView *failView = [self viewWithTag:FailViewTag];
    if (!failView) {
        failView = [[FailView alloc] initWithFrame: self.bounds];
        failView.tag = FailViewTag;
        [self addSubview: failView];
        [failView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self);
            make.width.equalTo(self);
                make.top.equalTo(self);
                make.height.equalTo(self);
            
        }];
    }
    failView.touchFailBlock = ^(void){
        if (action) {
            action();
        }
    };

    [failView setHidden: NO];
    [failView showFail: image withTip: tip];
    [self bringSubviewToFront: failView];
}

#pragma mark    - 展示所需图片
- (UIImage *)viewImage: (FailViewType)type {
    switch (type) {
        case FailViewStart:
            return nil;
            break;
        case FailViewEmpty:
            return [UIImage imageNamed:@"无数据"];
            break;
        case FailViewNoWifi:
            return [UIImage imageNamed:@"网络出错"];
            break;
        case FailViewError:
            return [UIImage imageNamed:@"网络出错"];
            break;
        default:
            break;
    }
    return [UIImage imageNamed:@"网络出错"];
}

#pragma mark   展示所需文字
- (NSString *)viewTip: (FailViewType)type {
    switch (type) {
        case FailViewStart:
            return @"";
            break;
        case FailViewEmpty:
            return @"暂无相关信息";
            break;
        case FailViewNoWifi:
            return @"网络不给力\n请查看网络设置或稍后重试";
            break;
        case FailViewError:
            return @"加载失败，点击重新加载";
            break;
        default:
            break;
    }
    return @"网络出错";
}

#pragma mark    - 点击失败页面重新调用刷新方法
- (void)touchReloadViewWithSEL:(NSString *)selStr {
    SEL currentAction = NSSelectorFromString(selStr);
    if ([self respondsToSelector: currentAction]) {
        SuppressPerformSelectorLeakWarning(PerformSelector(currentAction));
    }
}

#pragma mark    - 过滤失败页面的滑动事件
-(void)filterScrollActionWithFailView{
    if ([self isKindOfClass:[UIScrollView class]]) {
        ((UIScrollView *)self).scrollEnabled = NO;
        ((UIScrollView *)self).contentOffset = CGPointMake(0, 0);
    }
}

#pragma mark    - 恢复失败页面的滑动事件
-(void)replyScrollActionWithFailView {
    if ([self isKindOfClass:[UIScrollView class]]) {
        ((UIScrollView *)self).scrollEnabled = YES;
    }
}



@end
