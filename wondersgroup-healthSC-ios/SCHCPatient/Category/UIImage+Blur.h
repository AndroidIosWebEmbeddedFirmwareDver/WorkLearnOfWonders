//
//  UIImage+Blur.h
//  HCPatient
//
//  Created by maorenchao on 15/8/12.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage(UIImage_Blur)

+ (UIImage *)renterView:(UIView *)view size:(CGSize)size;

+ (UIImage *)boxblurImage:(UIImage *)image withBlurNumber:(CGFloat)blur withCoverColor:(UIColor *)coverColor;

- (UIImage *)blurredImageWithRadius:(CGFloat)radius iterations:(NSUInteger)iterations tintColor:(UIColor *)tintColor;

- (UIImage *)accelerateBlurWithImage:(UIImage *)image;

- (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)size;

- (UIImage *)scaleToSize:(UIImage *)img scale:(float)scale;

- (UIImage *)blurImage:(UIImage *)image;

@end
