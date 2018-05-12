//
//  MBProgressHUDHelper.m
//  Cocar
//
//  Created by boreanfrog on 14-9-18.
//  Copyright (c) 2014年 boreanfrog. All rights reserved.
//

#import "MBProgressHUDHelper.h"

NSTimeInterval SHORT_DURATION = 1;
NSTimeInterval LONG_DURATION = 2;

@implementation BPHUDWindow

+ (BPHUDWindow *)sharedWindow
{
    static BPHUDWindow *_window = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _window = [[BPHUDWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
        _window.windowLevel = UIWindowLevelStatusBar + 1;
    });
    
    return _window;
}
@end

@implementation MBProgressHUDHelper
@synthesize progressHUD = _progressHUD;

+ (void)showHudIndeterminate
{
    [[MBProgressHUDHelper defaultHelper] showIndeterminate];
}

+ (MBProgressHUDHelper *)defaultHelper
{
    static MBProgressHUDHelper *_helper = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _helper = [[MBProgressHUDHelper allocWithZone:NULL] init];
    });
    return _helper;
}

+ (void)showHudWithText:(NSString *)text
{
    if (text.length) {
        [[MBProgressHUDHelper defaultHelper] showHudWithText:text mode:MBProgressHUDModeText];
        [[MBProgressHUDHelper defaultHelper].progressHUD hide:YES afterDelay: LONG_DURATION];
    }
}

+ (void)hideHud
{
    [[MBProgressHUDHelper defaultHelper].progressHUD hide:NO afterDelay:0.0];
}

- (MBProgressHUD *)progressHUD
{
    MBProgressHUD *hud = _progressHUD;
    if (hud == nil) {
        hud = [[MBProgressHUD alloc] initWithView:[BPHUDWindow sharedWindow]];
        UIImage *image = [UIImage imageNamed:[NSString stringWithFormat:@"load1"]];
        UIImageView *_loadingImageView=[[UIImageView alloc] initWithFrame:CGRectMake(0, 0, image.size.width, image.size.height)];
        
        _loadingImageView.layer.zPosition = MAXFLOAT;
        NSMutableArray *imageArray = [NSMutableArray arrayWithCapacity:6];
        for (int i = 1; i <= 6; i++) {
            [imageArray addObject:[UIImage imageNamed:[NSString stringWithFormat:@"load%d",i]]];
        }
        _loadingImageView.animationImages = imageArray;
        _loadingImageView.animationDuration = 0.68;
        [_loadingImageView startAnimating];
        
        hud.customView = _loadingImageView;
        hud.removeFromSuperViewOnHide = YES;
        hud.delegate = self;
        _progressHUD = hud;
    }
    return hud;
}

- (MBProgressHUD *)showHudWithText:(NSString *)text mode:(MBProgressHUDMode)mode
{
    [self.progressHUD setMode:mode];
    self.progressHUD.margin = 10.f;
    self.progressHUD.color = [UIColor colorWithWhite:0 alpha:0.5];
    [[BPHUDWindow sharedWindow] addSubview:self.progressHUD];
    self.progressHUD.labelText  = text;
    [self show];
    return self.progressHUD;
}

- (MBProgressHUD *)showIndeterminate
{
    [self.progressHUD setMode:MBProgressHUDModeCustomView];
    [[BPHUDWindow sharedWindow] addSubview:self.progressHUD];
    self.progressHUD.dimBackground = NO;
    self.progressHUD.removeFromSuperViewOnHide = YES;
    self.progressHUD.margin = 10.f;
    self.progressHUD.opacity = 0.0;
    self.progressHUD.color = [UIColor colorWithWhite:0 alpha:0.5];
    self.progressHUD.labelText  = nil;
    [self show];
    return self.progressHUD;
}

- (MBProgressHUD *)showCompleteHudWithText:(NSString *)text
{
    return [self showHudWithText:text image:[UIImage imageNamed:@"37x-Checkmark.png"]];
}

- (MBProgressHUD *)showHudWithText:(NSString *)text image:(UIImage *)image
{
    UIImageView *imageView = [[UIImageView alloc] initWithImage:image];
    [[BPHUDWindow sharedWindow] addSubview:self.progressHUD];
    self.progressHUD.customView = imageView;
    self.progressHUD.mode = MBProgressHUDModeCustomView;
    self.progressHUD.labelText = text;
    [self show];
    return self.progressHUD;
}

- (void)show{
    
    [NSObject cancelPreviousPerformRequestsWithTarget:self.progressHUD];
    [BPHUDWindow sharedWindow].hidden = NO;
    
    // labelText 显示不下，使用detailsLabelText显示
    NSString *tmp = self.progressHUD.labelText;
    self.progressHUD.detailsLabelText = tmp;
    self.progressHUD.labelText = nil;
    self.progressHUD.detailsLabelFont = self.progressHUD.labelFont;
    
    [self.progressHUD show:YES];
}

- (void)hudWasHidden:(MBProgressHUD *)hud
{
    [BPHUDWindow sharedWindow].hidden = YES;
}

- (void)test
{
}
@end
