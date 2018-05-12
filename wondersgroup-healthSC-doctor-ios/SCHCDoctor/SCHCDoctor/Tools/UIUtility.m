//
//  UIUtility.m
//  VaccinePatient
//
//  Created by maorenchao on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIUtility.h"
#import "MJRefreshGifHeader.h"

@implementation UIUtility
#pragma mark - 生成GIF Header
+ (MJRefreshGifHeader *)headerRefreshTarget:(id)target action:(SEL)action {
    WDRefreshHeader *header = [WDRefreshHeader headerWithRefreshingTarget:target refreshingAction:action];
    
//    MJRefreshGifHeader *header = [MJRefreshGifHeader headerWithRefreshingTarget:target refreshingAction:action];
//    NSMutableArray *images = [[NSMutableArray alloc] init];
//    for (int i = 1; i < 3 ; i++) {
//        NSString *imageString = [NSString stringWithFormat:@"refresh%d",i];
//        UIImage *image = [UIImage imageNamed:imageString];
//        [images addObject:image];
//    }
//    
//    //     （1）设置普通状态的动画图片
//    [header setImages:@[images[0]] forState:MJRefreshStateIdle];
//    
//    //     （2）设置即将刷新状态的动画图片
//    [header setImages:@[images[0]] forState:MJRefreshStatePulling];
//    
//    //     （3）设置正在刷新状态的动画图片
//    [header setImages:images forState:MJRefreshStateRefreshing];
////    [header setImages:images duration:[images count]*0.1 forState:MJRefreshStateRefreshing];
//    
//    //     （4）设置自动切换透明度,下拉时alpha属性从0-1
//    header.automaticallyChangeAlpha = YES;
//    
////    //普通状态下的文字
////    [header setTitle:@"下拉刷新" forState:MJRefreshStateIdle];
////    //松开就可以刷新状态下的文字
////    [header setTitle:@"释放刷新" forState:MJRefreshStatePulling];
////    //正在舒心状态下得文字
////    [header setTitle:@"加载中" forState:MJRefreshStateRefreshing];
//    
//    //设置字体和颜色
////    [header setTitle: @"松开后刷新" forState: MJRefreshStateIdle];
//    header.stateLabel.font = [UIFont systemFontOfSize:16];
////    header.stateLabel.textAlignment = NSTextAlignmentLeft;
//    header.stateLabel.textColor = [UIColor tc1Color];
//    
//    header.lastUpdatedTimeLabel.font = [UIFont systemFontOfSize:12];
//    header.lastUpdatedTimeLabel.textColor = [UIColor tc2Color];
//
    
    return (MJRefreshGifHeader *)header;
}
#pragma mark - 生成 加载更多的Footer
+ (MJRefreshAutoNormalFooter *)footerMoreTarget:(id)target action:(SEL)action {
    id footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget: target refreshingAction: action];
    return footer;
}


@end
