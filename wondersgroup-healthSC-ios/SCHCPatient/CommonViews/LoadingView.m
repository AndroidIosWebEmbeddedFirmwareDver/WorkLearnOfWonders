//
//  LoadingView.m
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "LoadingView.h"

@interface LoadingView () {
    UIImageView *loadingImage;
}

@end


@implementation LoadingView

- (instancetype)initWithFrame:(CGRect)frame withImage:(NSString *)image withImageCount:(int)count {
    self = [super initWithFrame:frame];
    if (self) {
        [self setBackgroundColor: [UIColor clearColor]];
        
        UIImage *img = [UIImage imageNamed:  [NSString stringWithFormat: @"%@1", image]];
        
        loadingImage = [[UIImageView alloc] init];
        [loadingImage setBackgroundColor: [UIColor clearColor]];
        [self addSubview: loadingImage];
        
        WS(weakSelf)
        [loadingImage mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(weakSelf);
            make.centerY.equalTo(weakSelf);
            make.width.mas_equalTo(img.size.width);
            make.height.mas_equalTo(img.size.height);
        }];
        
        //        UIImage *gifImage = [UIImage sd_animatedGIFWithData: LOADING_GIF];
        //        loadingImage.image = gifImage;
        
        NSMutableArray *gifArray = [NSMutableArray array];
        for (int i = 1; i < count + 1; i++) {
            [gifArray addObject:[UIImage imageNamed: [NSString stringWithFormat:@"%@%d", image, i]]];
        }
        loadingImage.animationImages = gifArray; //动画图片数组
        loadingImage.animationDuration = 0.86; //执行一次完整动画所需的时长
        loadingImage.animationRepeatCount = 0;  //动画重复次数
        [loadingImage startAnimating];
        
    }
    return self;
}

- (id)initWithView: (UIView *)view  withImage:(NSString *)image withImageCount:(int)count {
    
    return [self initWithFrame:view.bounds withImage: image withImageCount: count];
}

#pragma mark - 显示loading
+ (void)showLoadingInView:(UIView *)view {
    [self hideLoadinForView:view];
    LoadingView *loadingView = [[LoadingView alloc] initWithView: view withImage: @"dot" withImageCount: 12];
    [view addSubview: loadingView];
    [view bringSubviewToFront:loadingView];
    [loadingView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.bottom.equalTo(view);
    }];
}


//Loading菊花显示
+ (void)showHudIndeterminateInView:(UIView *)view {
    [self hideLoadinForView:view];
    LoadingView *loadingView = [[LoadingView alloc] initWithView: view withImage: @"dot" withImageCount: 12];
    [view addSubview: loadingView];
    [view bringSubviewToFront:loadingView];
}


#pragma mark - 关闭loading
+ (BOOL)hideLoadinForView:(UIView *)view {
    LoadingView *loadingView = [LoadingView LoadingViewForView: view];
    if (loadingView) {
        [loadingView removeFromSuperview];
        return YES;
    }
    return NO;
}

#pragma mark - 获取当前View上的LoadingView
+ (instancetype)LoadingViewForView:(UIView *)view {
    
    NSEnumerator *subviewsEnum = [view.subviews reverseObjectEnumerator];
    for (UIView *subview in subviewsEnum) {
        if ([subview isKindOfClass:self]) {
            return (LoadingView *)subview;
        }
    }
    return nil;
}


@end
