//
//  UIImageView+RJImageVIew.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "UIImageView+RJImageView.h"

@implementation UIImageView (RJImageView)
- (void)rj_setCornerRadiusWithBounds:(CGRect)bounds {
    UIBezierPath *maskPath = [UIBezierPath bezierPathWithRoundedRect:bounds byRoundingCorners:UIRectCornerAllCorners cornerRadii:bounds.size];
    CAShapeLayer *maskLayer = [[CAShapeLayer alloc]init];
    maskLayer.frame = bounds;
    maskLayer.path = maskPath.CGPath;
    self.layer.mask = maskLayer;
}
@end
