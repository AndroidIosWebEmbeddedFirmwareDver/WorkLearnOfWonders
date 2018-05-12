//
//  SplashView.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SplashView : UIView


typedef void (^SplashCompleteBlock) (SplashView *splashView);



@property (nonatomic, copy) SplashCompleteBlock completeBlock;

/**
 *  显示SplashView
 *
 *  @param  block : Splash显示结束后的回调
 */

+ (void)appLaunch:(SplashCompleteBlock)block;


@end
