//
//  MBProgressHUDHelper.h
//  Cocar
//
//  Created by boreanfrog on 14-9-18.
//  Copyright (c) 2014年 boreanfrog. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MBProgressHUD/MBProgressHUD.h>

UIKIT_EXTERN NSTimeInterval SHORT_DURATION;
UIKIT_EXTERN NSTimeInterval LONG_DURATION;

@interface BPHUDWindow : UIWindow

+ (BPHUDWindow *)sharedWindow;
@end

@interface MBProgressHUDHelper : NSObject<MBProgressHUDDelegate>

+ (MBProgressHUDHelper *)defaultHelper;

@property (readonly, nonatomic) MBProgressHUD *progressHUD;

- (MBProgressHUD *)showHudWithText:(NSString *)text mode:(MBProgressHUDMode)mode;
- (MBProgressHUD *)showCompleteHudWithText:(NSString *)text;
- (MBProgressHUD *)showHudWithText:(NSString *)text image:(UIImage *)image;
+ (void)showHudIndeterminate;

+ (void)showHudWithText:(NSString *)text;

+ (void)hideHud;
- (void)show;
@end
