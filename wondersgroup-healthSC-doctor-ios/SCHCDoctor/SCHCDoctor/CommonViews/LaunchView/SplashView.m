//
//  SplashView.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SplashView.h"
#import "TaskManager.h"


#define SPLASH_DURATION 2.0


@interface SplashView ()<UIScrollViewDelegate> {
    
    UIScrollView    *introductionScroll;
    NSArray         *coverList;
    NSArray         *bgList;
    UIButton        *enterBtn;
//    UIButton        *loginBtn;
    NSInteger        currentPage;
    UIPageControl   *pagecontroller;
    BOOL     adShowing;
}

@end

@implementation SplashView


- (instancetype)init {
    self = [super initWithFrame: SCREEN_BOUNDS];
    if (self) {
        [self setBackgroundColor: [UIColor clearColor]];
//        [APP.window.rootViewController.view addSubview: self];
//        [APP.window.rootViewController.view  bringSubviewToFront: self];
        
        [APP.window addSubview: self];
        [APP.window bringSubviewToFront: self];

        adShowing = YES;
    }
    return self;
}


#pragma mark - 显示SplashView
+ (void)appLaunch:(SplashCompleteBlock)block {
    SplashView *instance = [[SplashView alloc] init];
    instance.completeBlock = block;
    [instance showLaunch];
}

#pragma mark - 页面显示相关
#pragma mark -

#pragma mark   弹出启动页   停留2秒
- (void)showLaunch {
    //启动页的ImageView
    /*
    NSDictionary *launchImageInfo = @{
                                      @"LaunchImage-700"      : @[@"640x960_2", @"640x960_3", @"640x960_4", @"640x960_5"],       //4S 414*736
                                      @"LaunchImage-700-568h" : @[@"640x1136_2", @"640x1136_3", @"640x1136_4", @"640x1136_5"],   //5、5S 375*667
                                      @"LaunchImage-800-667h" : @[@"750x1334_2", @"750x1334_3", @"750x1334_4", @"750x1334_5"],   //6、6S 320*480
                                      @"LaunchImage-800-Portrait-736h" : @[@"1242x2208_2", @"1242x2208_3", @"1242x2208_4", @"1242x2208_5"],   //6P、6SP 320*568
                                      };

    */
    NSArray* imagesDict = [[[NSBundle mainBundle] infoDictionary] valueForKey:@"UILaunchImages"];
    
    CGSize viewSize = APP.window.bounds.size;
    NSString *viewOrientation = @"Portrait";    //横屏请设置成 @"Landscape"
    NSString *launchImage = nil;
    
    
    for (NSDictionary* dict in imagesDict)
    {
        CGSize imageSize = CGSizeFromString(dict[@"UILaunchImageSize"]);
        
        if (CGSizeEqualToSize(imageSize, viewSize) && [viewOrientation isEqualToString:dict[@"UILaunchImageOrientation"]])
        {
            launchImage = dict[@"UILaunchImageName"];
            break;
        }
    }
    
    UIImageView *launchView = [[UIImageView alloc] init];
    [launchView setImage: [UIImage imageNamed: launchImage]];
    [launchView setFrame: SCREEN_BOUNDS];
    [self addSubview: launchView];
    
    //[self hiddenComplete];

//    启动页后进入后面画面展示
   [self showLaunchNextView: launchView];

    
/*
    NSMutableArray *launchImageList = [NSMutableArray arrayWithCapacity: 5];
    for (NSDictionary* dict in imagesDict)
    {
        CGSize imageSize = CGSizeFromString(dict[@"UILaunchImageSize"]);
        
        if (CGSizeEqualToSize(imageSize, viewSize) && [viewOrientation isEqualToString:dict[@"UILaunchImageOrientation"]])
        {
            launchImage = dict[@"UILaunchImageName"];
            
            //添加动画图片数组
            [launchImageList addObject: [UIImage imageNamed: launchImage]];
            for (NSString *name in launchImageInfo[launchImage]) {
                [launchImageList addObject: [UIImage imageNamed: name]];
            }
            break;
        }
    }
    UIImageView *launchView = [[UIImageView alloc] init];
    [launchView setImage: [UIImage imageNamed: launchImage]];
    [launchView setFrame: SCREEN_BOUNDS];
    [self addSubview: launchView];
 
    if (launchImageList.count > 1) {
    [launchView setAnimationImages: launchImageList];
    [launchView setAnimationDuration: 1.5];
    [launchView startAnimating];
    }
    //启动页后进入后面画面展示
    [self showLaunchNextView: launchView];
*/

}



#pragma mark - 启动页后页面展示判断
- (void)showLaunchNextView: (UIView *)launchView {
    
    WS(weakSelf);
    if ([[Context context] needADView]) {//需要展示广告页
        //APP 启动后启动页两秒后进入广告页面
        UIView *adView = [self showADView: launchView];
        //需要介绍页判断
        BOOL needIntroduction = [[Context context] needIntroduction];
        [self hiddenView: launchView afterDelay: SPLASH_DURATION withBlock:^{
            
            if (needIntroduction) { //需要介绍页
                [weakSelf showIntroduction: adView];
            }
            
//            [weakSelf hiddenView: adView afterDelay: [[Context context] ADViewDelay] withBlock:^{
//                if (!needIntroduction) { //需要介绍页
//                    [weakSelf hiddenComplete];
//                }
//                else {
//                    adShowing = NO;
//                }
//            }];
            //广告页面消失
            [self performSelector: @selector(hiddenViewADView:) withObject: adView afterDelay: [[Context context] ADViewDelay]];
            
        }];
    }
    else if ([[Context context] needIntroduction]) { //需要介绍页
        //APP 启动后启动页两秒后进入广告页面
        [self showIntroduction: launchView];
        [self hiddenView: launchView afterDelay: SPLASH_DURATION withBlock:^{
            adShowing = NO;
        }];
    }
    else {
        [self performSelector: @selector(stopImageAnimation:) withObject: launchView afterDelay: SPLASH_DURATION];
    }
}

- (void)hiddenViewADView:(UIView *)adView {
    
    WS(weakSelf);
    [UIView animateWithDuration: 1.0 animations:^{
        adView.alpha     = 0;
        adView.transform = CGAffineTransformMakeScale( 2.0, 2.0 );

    } completion:^(BOOL finished) {
        [adView removeFromSuperview];
        BOOL needIntroduction = [[Context context] needIntroduction];
        if (!needIntroduction) { //需要介绍页
            [weakSelf removeFromSuperview];
            weakSelf.completeBlock(weakSelf);
        }
        else {
            adShowing = NO;
        }
    }];

}

//Launch停止动画
- (void)stopImageAnimation:(UIImageView *)imageView {
    [imageView stopAnimating];
    [self hiddenComplete];
}


#pragma mark   初始化广告页
- (UIView *)showADView:(UIView *)hiddenView {
    
    UIView *aView = [[UIView alloc] init];
    [aView setFrame: SCREEN_BOUNDS];
    [self insertSubview: aView belowSubview: hiddenView];

    UIImageView *adImageView = [[UIImageView alloc] init];
    UIImage *image = [[SDImageCache  sharedImageCache] imageFromDiskCacheForKey: [[Context context] getAds]];
    [adImageView setImage: image];
    [adImageView setFrame: SCREEN_BOUNDS];
    [aView addSubview: adImageView];
//    [self insertSubview: adImageView belowSubview: hiddenView];
    
    if ([[Context context] isShowJumpButton]) {
        UIView *btnBgView = [[UIView alloc] initWithFrame: CGRectMake(SCREEN_WIDTH-80, 30, 60, 40)];
        [btnBgView setBackgroundColor: [UIColor blackColor]];
        [btnBgView setAlpha: 0.4];
        [btnBgView.layer setCornerRadius: 5.0];
        [aView addSubview: btnBgView];
        
        WS(weakSelf);
        UIButton *jumpBtn = [UISetupView setupButtonWithSuperView: aView
                                           withTitleToStateNormal: @"跳过"
                                      withTitleColorToStateNormal: [UIColor whiteColor]
                                                withTitleFontSize: 16.0
                                                       withAction:^(UIButton *sender) {
                                                           [[self class] cancelPreviousPerformRequestsWithTarget: self];
                                                           [weakSelf hiddenView: aView afterDelay: 0.0 withBlock:^{
                                                               weakSelf.completeBlock(weakSelf);
                                                               [weakSelf removeFromSuperview];
                                                           }];
                                                           
                                                       }];
        [jumpBtn setFrame: btnBgView.frame];

    }
    
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget: self action: @selector(jumpToADUrl:)];
    [aView addGestureRecognizer: tap];
    
    return aView;
}

#pragma mark - 广告页跳转
- (void)jumpToADUrl:(UITapGestureRecognizer *)tap {
    [[self class] cancelPreviousPerformRequestsWithTarget: self];
    if ([TaskManager manager].appConfig.advertisement.hoplinks) {
        [[VCManager manager] showADView: [TaskManager manager].appConfig.advertisement.hoplinks];
        [self removeFromSuperview];
        self.completeBlock(self);
    }
}



- (void)showIntroduction:(UIView *)hiddenView {
    [self resetIntroductionData];
    [self setupIntroduction: hiddenView];
}



#pragma mark - View在指定时间后消失
- (void)hiddenView:(UIView *)view afterDelay:(CGFloat)delay withBlock: (void(^)(void)) complete {

    [UIView animateWithDuration: 1.0 delay: delay options: UIViewAnimationOptionTransitionNone animations:^{
        view.alpha     = 0;
        view.transform = CGAffineTransformMakeScale( 2.0, 2.0 );
    } completion:^(BOOL finished) {
        if ([view isKindOfClass: [UIImageView class]]) {
            [(UIImageView *)view stopAnimating];
        }
        [view removeFromSuperview];
        complete();
    }];
}


#pragma mark - 页面消失动画
#pragma mark -

#pragma mark   页面消失的动画
- (void)hiddenComplete {
    WS(weakSelf)
    [UIView animateWithDuration: 1.0 animations:^{
        weakSelf.alpha     = 0;
        weakSelf.transform = CGAffineTransformMakeScale( 2.0, 2.0 );
    } completion:^(BOOL finished) {
        [weakSelf removeFromSuperview];
        self.completeBlock(self);
    }];
}


#pragma mark - 导航页相关方法
#pragma mark -

#pragma mark   导航页结束，调用完成的回调
- (void)enter: (id)sender {
    [self hiddenComplete];
}

#pragma mark   导航页数据初始化
- (void)resetIntroductionData {
    coverList   = [[Context context] coverImages];
    bgList      = [[Context context] coverBgImages];
    currentPage = 0;
    if (enterBtn) {
        [enterBtn removeFromSuperview];
        
        [[NSNotificationCenter defaultCenter] postNotificationName:LOCATION_WARING_SETTING object:nil];
        enterBtn =  nil;
    }
}

#pragma mark   导航页UI构建
- (void)setupIntroduction: (UIView *) hiddenView {
    UIScrollView *scroll = [[UIScrollView alloc] initWithFrame: SCREEN_BOUNDS];
    [scroll setContentSize: CGSizeMake((coverList.count)* SCREEN_WIDTH, 0)];
    [scroll setShowsHorizontalScrollIndicator: NO];
    [scroll setShowsVerticalScrollIndicator:   NO];
    [scroll setPagingEnabled: YES];
    [scroll setBounces:NO];
    [scroll setDelegate: self];
    [scroll setBackgroundColor: [UIColor bc1Color]];
    [self insertSubview: scroll belowSubview: hiddenView];
    
    introductionScroll = scroll;
    
    CGFloat rate = IS_IPHONE_4_OR_LESS ? 2.0/3 : 1;
    
    CGRect imageFrame = CGRectZero;
    for (int i=0; i<coverList.count; i++) {
        imageFrame = CGRectMake(SCREEN_WIDTH * i, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        UIImageView *bgImage = [[UIImageView alloc] initWithFrame: imageFrame];
        if ([UIImage imageNamed: bgList[0]]) {
            [bgImage setImage: [UIImage imageNamed: bgList[0]]];
        }
        else {
            [bgImage setBackgroundColor: [UIColor bc1Color]];
        }
        [scroll addSubview: bgImage];
        [bgImage setTag: i + 100];
        
        UIImage *cover = [UIImage imageNamed: coverList[i]];
        imageFrame.size.width   = cover.size.width*rate;
        imageFrame.size.height  = cover.size.height*rate;
        imageFrame.origin.x     = imageFrame.origin.x + (SCREEN_WIDTH - cover.size.width*rate)/2;
        imageFrame.origin.y     = imageFrame.origin.y + (SCREEN_HEIGHT - cover.size.height*rate)/2 - 20;
        
        UIImageView *coverImage = [[UIImageView alloc] initWithFrame: imageFrame];
        [coverImage setContentMode:UIViewContentModeScaleAspectFit];
        [coverImage setImage: [UIImage imageNamed: coverList[i]]];
        [scroll addSubview: coverImage];
    }
    
    UIPageControl * _pagecontrtoller = [[UIPageControl alloc] init];
    [_pagecontrtoller addTarget:self action:@selector(pageTurn) forControlEvents:UIControlEventValueChanged];
    _pagecontrtoller.pageIndicatorTintColor = [UIColor bc4Color];
    _pagecontrtoller.currentPageIndicatorTintColor= [UIColor tc5Color] ;
//    _pagecontrtoller.pageIndicatorTintColor  =RGBA_COLOR(2*16+12, 12*16+1, 7*16+11,0.7); //HEXA_COLOR(#2cc17b, 0.3) ;
    _pagecontrtoller.numberOfPages = [coverList count];
    _pagecontrtoller.currentPage = 0;
//    [self addSubview:_pagecontrtoller];
    [self insertSubview: _pagecontrtoller aboveSubview: scroll];

    pagecontroller = _pagecontrtoller;
    
    [_pagecontrtoller mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.bottom.equalTo(self).offset(-35);
        make.width.mas_equalTo(100);
        make.height.mas_equalTo(44);
    }];
    
    //pagecontroller.hidden = YES;
//    UIImage *enterImage = [UIImage imageNamed: @"引导页btn"];
    enterBtn = [UIButton buttonWithType: UIButtonTypeCustom];
//    [enterBtn setImage: enterImage forState: UIControlStateNormal];
    [enterBtn setTitle: @"立即体验" forState: UIControlStateNormal];
    enterBtn.titleLabel.font = [UIFont systemFontOfSize:14];
    [enterBtn setborderWithColor:[UIColor tc5Color] withWidth:0.5];
    [enterBtn setCornerRadius:18];
    [enterBtn setTitleColor: [UIColor tc5Color] forState: UIControlStateNormal];
    //    [enterBtn setImage:[UIImage imageNamed:@"btn2"] forState:UIControlStateNormal];
    //    [enterBtn setImage:[UIImage imageNamed:@"btn2"] forState:UIControlStateHighlighted];
    [self insertSubview: enterBtn aboveSubview: scroll];
    [enterBtn addTarget:self action:@selector(enter:) forControlEvents:UIControlEventTouchUpInside];
    [enterBtn sizeToFit];
    [enterBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self);
        make.bottom.equalTo(self).offset(-35);
        make.width.mas_equalTo(110);
        make.height.mas_equalTo(35);
    }];
    [enterBtn setAlpha: 0];

    
}

-(void)pageTurn
{
}

#pragma mark   导航页滑动的处理
- (void)pagingScrollViewDidChangePages:(UIScrollView *)pagingScrollView
{
    if ([self isLast]) {
        
        [UIView animateWithDuration:0.4 animations:^{
            enterBtn.alpha = 1;
            pagecontroller.alpha = 0.0;
        }];
    } else {
        if (enterBtn.alpha == 1) {
            [UIView animateWithDuration:0.4 animations:^{
                enterBtn.alpha = 0;
                pagecontroller.alpha = 1.0;
            }];
        }
    }
}

#pragma mark   导航页后面是否还有的判断
- (BOOL)hasNext {
    return coverList.count > currentPage + 1;
}

#pragma mark   导航页是否最后一页的判断
- (BOOL)isLast {
    return coverList.count == currentPage + 1;
}

#pragma mark   UIScrollViewDelegate
- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    if (adShowing) {
        [scrollView setContentOffset: CGPointMake(0, 0)];
        return;
    }
    NSInteger   index = scrollView.contentOffset.x / self.frame.size.width;
    CGFloat     alpha = 1 - ((scrollView.contentOffset.x - index * self.frame.size.width) / self.frame.size.width);
    
    UIImageView *bgImage = [scrollView viewWithTag: index + 100];
    if (bgImage) {
        NSLog(@"%.f", alpha);
        [bgImage setAlpha:alpha];
    }
    currentPage = scrollView.contentOffset.x / (scrollView.contentSize.width / [coverList count]);
    [self pagingScrollViewDidChangePages: scrollView];
    pagecontroller.currentPage = index;
    
}

- (void)scrollViewWillBeginDecelerating:(UIScrollView *)scrollView
{
    if ([scrollView.panGestureRecognizer translationInView:scrollView.superview].x < 0) {
        if (![self hasNext]) {
            //最后一页滑动进入首页
//            [self enter: nil];
        }
    }
}



@end
