//
//  UIView+BorderColor.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIView+BorderColor.h"

typedef NS_ENUM(NSInteger, BorderRect) {
    BorderRectTop,
    BorderRectLeft,
    BorderRectBottom,
    BorderRectRight,
};

@implementation UIView (BorderColor)

- (void)addBorderWithRect:(BorderRect)borderRect withColor:(UIColor *)color borderWidth:(CGFloat)borderWidth {
    CALayer *borderLayer = [CALayer layer];
    borderLayer.backgroundColor = color.CGColor;
    CGRect frame = CGRectZero;
    switch (borderRect) {
        case BorderRectTop:
            frame = CGRectMake(0, 0, self.frame.size.width, borderWidth);
            break;
        case BorderRectLeft:
            frame = CGRectMake(0, 0, borderWidth, self.frame.size.height);
            break;
        case BorderRectBottom:
            frame = CGRectMake(0, self.frame.size.height - borderWidth, self.frame.size.width, borderWidth);
            break;
        case BorderRectRight:
            frame = CGRectMake(0, self.frame.size.width - borderWidth, borderWidth, self.frame.size.height);
            break;
        default:
            break;
    }
    borderLayer.frame = frame;
    [self.layer addSublayer:borderLayer];
}


-(void)addTopBorderWithColor:(UIColor *)color borderWidth:(CGFloat)borderWidth {
    [self addBorderWithRect:BorderRectTop withColor:color borderWidth:borderWidth];
}


-(void)addLeftBorderWithColor:(UIColor *)color borderWidth:(CGFloat) borderWidth {
    [self addBorderWithRect:BorderRectLeft withColor:color borderWidth:borderWidth];
}


-(void)addBottomBorderWithColor:(UIColor *)color borderWidth:(CGFloat)borderWidth {
    [self addBorderWithRect:BorderRectBottom withColor:color borderWidth:borderWidth];
}


-(void)addRightBorderWithColor:(UIColor *)color borderWidth:(CGFloat)borderWidth {
    [self addBorderWithRect:BorderRectRight withColor:color borderWidth:borderWidth];
}


@end
