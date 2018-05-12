//
//  RJTools.m
//  CommonFrameDemo
//
//  Created by Po on 2017/4/11.
//  Copyright © 2017年 Po. All rights reserved.
//

#import "RJTools.h"

@implementation RJTools
+ (MJRefreshStateHeader *)getRefreshHeader:(void(^)(void))headerBlock {
    MJRefreshStateHeader * header = [MJRefreshStateHeader headerWithRefreshingBlock:^{
        headerBlock();
    }];
    return header;
}
+ (MJRefreshBackStateFooter *)getRefreshFooter:(void(^)(void))footerBlock {
    MJRefreshBackStateFooter * footer = [MJRefreshBackStateFooter footerWithRefreshingBlock:^{
        footerBlock();
    }];
    
    return footer;
}

+ (UIView *)getBackButtonWithEvent:(void(^)(UIButton * button))eventBlock {
    UIView *leftButtonView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 80, 25)];
    UIImageView *arrowImg = [[UIImageView alloc]initWithFrame:CGRectMake(0, 2, 13, 20)];
    UIImage *image = [UIImage imageNamed:@"back_arrow"];
    [arrowImg setImage:image];
    [leftButtonView addSubview:arrowImg];
    
    UILabel *alable = [[UILabel alloc]initWithFrame:CGRectMake(19, 0, 40, 25)];
    alable.text = @"返回";
    alable.textColor = [UIColor whiteColor];
    [leftButtonView addSubview:alable];
    
    UIButton *abte = [[UIButton alloc]initWithFrame:CGRectMake( 10, 20, 80, 40)];
    [leftButtonView addSubview:abte];
    eventBlock(abte);
    return leftButtonView;
}
@end
