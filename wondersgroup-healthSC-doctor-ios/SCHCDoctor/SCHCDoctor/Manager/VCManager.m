//
//  VCManager.m
//  VaccinePatient
//
//  Created by maorenchao on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "VCManager.h"
#import "RootTabBarController.h"
#import "BaseNavigationController.h"
#import "LoginViewController.h"
#import "BFRouter.h"
#import "CustomTransition.h"

@interface VCManager()<UITabBarControllerDelegate, UIViewControllerTransitioningDelegate> {
    CustomTransition *_presentTransition;
}

@property (nonatomic, strong) BaseNavigationController *rootNavController;
@property (nonatomic, strong) RootTabBarController      *rootTabController;

@end

@implementation VCManager
+ (instancetype)manager {
    static VCManager *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[VCManager alloc] init];
    });
    return instance;
}


#pragma mark - APP启动根据USER来加载首页或登录页
- (void)loadRootViewController
{
   [self showLoginViewController: NO];
    return;
    /*
    id viewController = nil;
    BOOL barHidden    = YES;
    if(![UserManager manager].isLogin){
        
        LoginViewController *loginVC = [[LoginViewController alloc] init];
        loginVC.viewModel.viewType = LoginViewTypePWD;
        loginVC.hasBack = NO;
        viewController = loginVC;
        barHidden = NO;
        
        BaseNavigationController *navC = [[BaseNavigationController alloc] initWithRootViewController: viewController];
        [navC setNavigationBarHidden:barHidden];
        [APP.window setRootViewController: navC];
        self.rootNavController = navC;
        [BFRouter router].navi = navC;
    }
    else {
        //显示首页
        [self showHomeViewController: NO];
    }
     */
}

//#pragma mark - 弹出登录页
//- (void)showLoginViewController:(BOOL)animation {
//    
//    NSArray *vcList = [self.rootNavController viewControllers];
//    if (vcList.count == 1) {
//        if ([[vcList firstObject] isKindOfClass: [LoginViewController class]]) {
//            return;
//        }
//    }
//
//    [[BFRouter router].navi popToRootViewControllerAnimated: NO];
//    
//    LoginViewController *loginVC = [[LoginViewController alloc] init];
//    loginVC.viewModel.viewType = LoginViewTypePWD;
//    loginVC.hasBack = NO;
//    
//    if (!self.rootNavController) {
//        
//        BaseNavigationController *navC = [[BaseNavigationController alloc] initWithRootViewController: loginVC];
//        [APP.window setRootViewController: navC];
//        self.rootNavController = navC;
//    }
//    
//    //把登录插入RootNav的第一位
//    NSMutableArray *viewControllers = [NSMutableArray arrayWithArray: [self.rootNavController viewControllers]];
//    [viewControllers insertObject: loginVC atIndex: 0];
//    [self.rootNavController setViewControllers: viewControllers animated: NO];
//    
//    //显示RootNav的NavBar
//    [self.rootNavController setNavigationBarHidden: NO];
//    //返回到登录页面
//    [self.rootNavController popToRootViewControllerAnimated: animation];
//    
//}
//- (void)presentLoginViewController:(BOOL)animation{
//    NSArray *vcList = [self.rootNavController viewControllers];
//    if (vcList.count == 1) {
//        if ([[vcList firstObject] isKindOfClass: [LoginViewController class]]) {
//            return;
//        }
//    }
//    
//    LoginViewController *loginVC = [[LoginViewController alloc]init];
//    //把登录插入RootNav的第一位
//    NSMutableArray *viewControllers = [NSMutableArray arrayWithArray: [self.rootNavController viewControllers]];
//    [viewControllers insertObject: loginVC atIndex: 0];
//    [self.rootNavController setViewControllers: viewControllers animated: NO];
//    [self.rootNavController popToRootViewControllerAnimated: YES];
//}

#pragma mark - 弹出登录页
- (void)showLoginViewController:(BOOL)animation {
#warning 强制登录
    if ((1)) {
//        if ([UserManager manager].isLogin) {
        [self showHomeViewController:YES];
    }else {
        LoginViewController *loginVC = [[LoginViewController alloc]init];
        
        if (!self.rootNavController) {
            
            BaseNavigationController *navC = [[BaseNavigationController alloc] initWithRootViewController: loginVC];
            [APP.window setRootViewController: navC];
            self.rootNavController = navC;
        }
        [self.rootNavController setNavigationBarHidden: YES];
        [self.rootNavController setViewControllers: @[loginVC] animated: animation];
    }
}

#pragma mark - 弹出首页
- (void)showHomeViewController:(BOOL)animation {
    RootTabBarController *rootVC = [[RootTabBarController alloc]init];
    rootVC.delegate = self;
    if (!self.rootNavController) {
        
        BaseNavigationController *navC = [[BaseNavigationController alloc] initWithRootViewController: rootVC];
        [APP.window setRootViewController: navC];
        self.rootNavController = navC;
    }
    [self.rootNavController setNavigationBarHidden: YES];
    [self.rootNavController setViewControllers: @[rootVC] animated: animation];
    self.rootTabController = rootVC;
}

#pragma mark - APP FinishLaunching
- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    application.delegate.window = [[UIWindow alloc] initWithFrame: [[UIScreen mainScreen] bounds]];
    [application.delegate.window setBackgroundColor: [UIColor whiteColor]];
    [self loadRootViewController];
    [application.delegate.window makeKeyAndVisible];
    
    [[VCManager manager] showLaunch:^(SplashView *splashView) {
        NSLog(@"引导页＋广告页加载结束");
    }];
}

////Tabbar根据指定下标跳转
//- (void)showTabbarControllerAtIndex:(NSInteger)index;
//
#pragma mark - Tabbar根据指定下标跳转
- (void)showTabbarControllerAtIndex:(NSInteger)index {
    if (self.rootTabController) {
        NSInteger count = [self.rootTabController viewControllers].count;
        if (index > count -1) {
            NSLog(@"指定下标越界");
            return;
        }
        [self.rootTabController setSelectedIndex: index];
    }
}

#pragma mark - APP启动后调用，集合了启动页、导航页、广告页
- (void)showLaunch: (void (^) (SplashView *splashView))complete {
    [SplashView appLaunch:^(SplashView *splashView) {
        
        complete(splashView);
    }];
}

// Get the two view controllers
- (nullable id <UIViewControllerAnimatedTransitioning>)animationControllerForPresentedController:(UIViewController *)presented presentingController:(UIViewController *)presenting sourceController:(UIViewController *)source {
    _presentTransition.type = AnimationTypePresent;
    return _presentTransition;

}

- (nullable id <UIViewControllerAnimatedTransitioning>)animationControllerForDismissedController:(UIViewController *)dismissed {
    _presentTransition.type = AnimationTypeDismiss;
    return _presentTransition;
}

//- (nullable id <UIViewControllerInteractiveTransitioning>)interactionControllerForPresentation:(id <UIViewControllerAnimatedTransitioning>)animator {
//    return [[CustomTransition alloc] init];
//}



#pragma mark - UITabBarControllerDelegate
-(BOOL) tabBarController:(UITabBarController *)tabBarController shouldSelectViewController:(UIViewController *)viewController {
    
//    if(((BaseNavigationController *)viewController).Tag==2) {
//        
//        if (![UserManager manager].isLogin) { //判断登录
//            [[VCManager manager]showHomeViewController:NO];
//            //                        [[VCManager manager] presentLoginViewController: YES];
//            return FALSE;
//        }
//    }
    
    return YES;
}

- (void)tabBarController:(UITabBarController *)tabBarController didSelectViewController:(UIViewController *)viewController {

//    NSUInteger index = tabBarController.selectedIndex;
//    if (index == 0) {
//        [YMEvent event: @"YcTabHome"];
//    }
//    else if (index == 1) {
//        [YMEvent event: @"YcTabDiscovery"];
//    }
//    else {
//        [YMEvent event: @"YcTabMy"];
//    }
  
   
    
    [BFRouter router].navi = (UINavigationController *)viewController;
}

#pragma mark - 广告页跳转
- (void)showADView:(NSString *)url {
    if (url) {
        [[BFRouter router] open: url];
    }
}

#pragma mark - 弹出升级页面
- (void)showUpdateView:(UpdateInfoModel *)updateInfo {
    if (updateInfo) {
        /*
        WDAlertViewType type =  [updateInfo.enforceUpdate boolValue] ? WDAlertViewTypeOne : WDAlertViewTypeTwo;
        
        WDUpdateView * alertView = [[WDUpdateView alloc]initWithNavigationController: [BFRouter router].navi withType: type];
        [alertView.submitBtn setTitle:@"立即升级" forState:UIControlStateNormal];
        [alertView.submitBtn setTitle:@"立即升级" forState:UIControlStateHighlighted];
        
        @weakify(alertView)
        alertView.submitBlock = ^(WDUpdateView *alert){
            @strongify(alertView)
            [alertView dismiss];
            [[UIApplication sharedApplication]openURL:[NSURL URLWithString: updateInfo.downloadUrl]];
            [TaskManager manager].appConfig.updateInfo = nil;
        };

        if(type == WDAlertViewTypeTwo) {
            [alertView.cancelBtn setTitle:@"稍后再说" forState:UIControlStateNormal];
            [alertView.cancelBtn setTitle:@"稍后再说" forState:UIControlStateHighlighted];
            
            alertView.cancelBlock = ^(WDUpdateView *alert){
                @strongify(alertView)
                [alertView dismiss];
                [TaskManager manager].appConfig.updateInfo = nil;
            };
        }
        else {
        }
        
        [alertView reloadTitle: nil content: updateInfo.updateLogs];
        [alertView showViewWithHaveBackAction: NO withHaveBackView: YES];
         */
    }
}



@end
