//
//  UIImageView+RoundImage.m
//  localSocketDemo
//
//  Created by maorenchao on 16/7/18.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import "UIImageView+RoundImage.h"
#import <objc/runtime.h>

static const NSString* isRoundKey = @"ISRoundKey";

@implementation UIImageView(UIImageView_RoundImage)

- (void)setFrame:(CGRect)frame
{
    [super setFrame:frame];
    
    [self resetRoundLayer];
}

- (void)setIsRound:(BOOL)isRound
{
    objc_setAssociatedObject(self, (__bridge const void *)(isRoundKey), [NSNumber numberWithBool:isRound], OBJC_ASSOCIATION_COPY_NONATOMIC);
    [self resetRoundLayer];
}

- (BOOL)isRound
{
    NSNumber *isRound = objc_getAssociatedObject(self, (__bridge const void *)(isRoundKey));
    return isRound.boolValue;
}

- (void)resetRoundLayer
{
    if (!self.isRound) {
        self.layer.mask = nil;
        return;
    }
    
    
    UIBezierPath *path = [UIBezierPath bezierPathWithOvalInRect:self.bounds];
    
    CAShapeLayer *maskLayer = [CAShapeLayer layer];
    maskLayer.path = path.CGPath;
    maskLayer.fillColor = [UIColor redColor].CGColor;
    self.layer.mask = maskLayer;
}

@end
