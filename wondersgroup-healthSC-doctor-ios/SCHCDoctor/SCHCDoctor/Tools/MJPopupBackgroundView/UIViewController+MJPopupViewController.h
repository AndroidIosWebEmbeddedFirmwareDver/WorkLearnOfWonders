//
//  UIViewController+MJPopupViewController.h
//  MJModalViewController
//
//  Created by Martin Juhasz on 11.05.12.
//  Copyright (c) 2012 martinjuhasz.de. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum {
    MJPopupViewAnimationSlideBottomTop = 1,
    MJPopupViewAnimationSlideRightLeft,
    MJPopupViewAnimationSlideBottomBottom,
    MJPopupViewAnimationFade
} MJPopupViewAnimation;

@interface UIViewController (MJPopupViewController)

- (void)presentPopupView:(UIView*)popupView animationType:(MJPopupViewAnimation)animationType
              isHaveBack:(BOOL)isHaveBack isHaveBackView:(BOOL)isHaveBackView;
- (void)dismissPopupViewControllerWithanimationType:(MJPopupViewAnimation)animationType;

- (void)dismissPopupView:(UIView *)view WithanimationType:(MJPopupViewAnimation)animationType;

@end