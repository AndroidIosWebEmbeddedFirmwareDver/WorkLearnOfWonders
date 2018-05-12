//
//  UIScrollView+PanBack.m
//  VaccinePatient
//
//  Created by Joseph Gao on 2016/10/26.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIScrollView+PanBack.h"

@implementation UIScrollView (PanBack)

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
    if ([gestureRecognizer isKindOfClass:[UIPanGestureRecognizer class]] && [otherGestureRecognizer isKindOfClass:[UIScreenEdgePanGestureRecognizer class]]) {
        return YES;
    }
    else {
        return NO;
    }
}
@end
