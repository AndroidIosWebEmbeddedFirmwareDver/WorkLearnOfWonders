//
//  LoadingView.h
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LoadingView : UIView


//Loading显示
+ (void)showLoadingInView:(UIView *)view;
//Loading消失
+ (BOOL)hideLoadinForView:(UIView *)view;

//Loading菊花显示
+ (void)showHudIndeterminateInView:(UIView *)view;


@end
