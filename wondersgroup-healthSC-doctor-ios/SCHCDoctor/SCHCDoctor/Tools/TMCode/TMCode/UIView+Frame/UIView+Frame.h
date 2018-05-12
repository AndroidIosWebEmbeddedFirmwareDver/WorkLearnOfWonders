//
//  UIView+Frame.h
//  Jikeyi
//
//  Created by zhengpeng on 14-4-8.
//  Copyright (c) 2014年 zhengpeng. All rights reserved.
//

#import <UIKit/UIKit.h>
CGPoint CGRectGetCenter(CGRect rect);
CGRect  CGRectMoveToCenter(CGRect rect, CGPoint center);
@interface UIView (Frame)

- (void)setHeight:(CGFloat)height;
- (CGFloat)left;
- (CGFloat)right;
- (CGSize)size;
- (CGFloat)top;
- (CGFloat)bottom;
- (CGFloat)width;
- (CGFloat)height;
- (CGFloat)centerX;
- (CGFloat)centerY;
- (void)setLeft:(CGFloat)left;
- (void)setRight:(CGFloat)right;
- (void)setBottom:(CGFloat)bottom;
- (void)setSize:(CGSize)size;
- (void)setTop:(CGFloat)top;
- (void)setWidth:(CGFloat)width;
- (void)setOrigin:(CGPoint)point;
- (void)setCenterX:(CGFloat)centerX;
- (void)setCenterY:(CGFloat)centerY;
- (void)setAddTop:(CGFloat)top;
- (void)setAddLeft:(CGFloat)left;
- (CGFloat)myCenterX;
- (CGFloat)myCenterY;
- (CGPoint)myCenter;
- (void) moveBy: (CGPoint) delta;
- (void) scaleBy: (CGFloat) scaleFactor;
- (void) fitInSize: (CGSize) aSize;

- (UIViewController *)viewController;

@end
