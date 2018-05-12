//
//  MBProgressHUDHelper.m
//  Cocar
//
//  Created by boreanfrog on 14-9-18.
//  Copyright (c) 2014年 boreanfrog. All rights reserved.
//

#import "MBProgressHUDHelper.h"
#import "SpritesLoadingView.h"

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
//        [[MBProgressHUDHelper defaultHelper].progressHUD hideAnimated: YES afterDelay: LONG_DURATION];
    }
}

+ (void)hideHud
{
    [[MBProgressHUDHelper defaultHelper].progressHUD hide:NO afterDelay:0.0];
//    [[MBProgressHUDHelper defaultHelper].progressHUD hideAnimated: NO afterDelay: 0.0];
}

- (MBProgressHUD *)progressHUD
{
    MBProgressHUD *hud = _progressHUD;
    if (hud == nil) {
        
        hud = [[MBProgressHUD alloc] initWithView:[BPHUDWindow sharedWindow]];
        UIImageView *_loadingImageView=[[UIImageView alloc] initWithFrame:CGRectZero];
        
        _loadingImageView.layer.zPosition = MAXFLOAT;
        int dottCount = 12;
        NSMutableArray *imageArray = [NSMutableArray arrayWithCapacity: dottCount];
        for (int i = 1; i <= dottCount; i++) {
            [imageArray addObject:[UIImage imageNamed:[NSString stringWithFormat:@"dot%d",i]]];
        }
        _loadingImageView.animationImages = imageArray;
        _loadingImageView.animationDuration = 0.86;
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
    self.progressHUD.color = [UIColor colorWithWhite:0 alpha:0.8];
//    self.progressHUD.bezelView.color= [UIColor colorWithWhite:0 alpha:0.8];
    [[BPHUDWindow sharedWindow] addSubview:self.progressHUD];
    self.progressHUD.labelText  = text;
//    self.progressHUD.label.text  = text;
//    self.progressHUD.label.textColor = [UIColor whiteColor];
    [self show];
    return self.progressHUD;
}

- (MBProgressHUD *)showIndeterminate
{
    [self.progressHUD setMode:MBProgressHUDModeCustomView];
    [[BPHUDWindow sharedWindow] addSubview:self.progressHUD];
    self.progressHUD.dimBackground = NO;
//    self.progressHUD.backgroundView.style = MBProgressHUDBackgroundStyleSolidColor;
//    self.progressHUD.backgroundView.color = [UIColor clearColor];
    self.progressHUD.removeFromSuperViewOnHide = YES;
//    self.progressHUD.bezelView.style = MBProgressHUDBackgroundStyleSolidColor;
//    self.progressHUD.bezelView.color = [UIColor colorWithWhite:1 alpha:0];
//    self.progressHUD.label.text  = nil;
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
//    self.progressHUD.label.text  = text;
    [self show];
    return self.progressHUD;
}

- (void)show{
    
    [NSObject cancelPreviousPerformRequestsWithTarget:self.progressHUD];
    [BPHUDWindow sharedWindow].hidden = NO;
    
    // labelText 显示不下，使用detailsLabelText显示
//    NSString *tmp = self.progressHUD.label.text;
//    self.progressHUD.detailsLabel.text = tmp;
//    self.progressHUD.label.text = nil;
//    self.progressHUD.detailsLabel.textColor = [UIColor whiteColor];
//    self.progressHUD.detailsLabel.font = self.progressHUD.label.font;

    [self.progressHUD show:YES];
//    [self.progressHUD showAnimated: YES];
}

- (void)hudWasHidden:(MBProgressHUD *)hud
{
    [BPHUDWindow sharedWindow].hidden = YES;
}

- (void)test
{
}
@end
