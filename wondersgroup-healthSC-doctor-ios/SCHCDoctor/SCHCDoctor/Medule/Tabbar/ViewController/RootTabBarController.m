//
//  RootTabBarController.m
//  EyeProtection
//
//  Created by Jam on 16/2/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "RootTabBarController.h"
#import "RootTabBarViewModel.h"
#import "BadgeModel.h"
#import "UITabBar+badge.h"

@interface RootTabBarController ()

@property (nonatomic, strong)   RootTabBarViewModel *viewModel;
@property (nonatomic, strong)   UIImageView * backImageView;

@end

@implementation RootTabBarController

- (instancetype)init
{
    self = [super init];
    if (self) {
    }
    return self;
}

- (UIImageView *)backImageView{
    if (!_backImageView) {
        _backImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 49.)];
        _backImageView.image = [UIImage imageNamed:@"tab_bg"];
    }
    return _backImageView;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    self.viewModel = [[RootTabBarViewModel alloc] init];
    //加载Controllers
    [self loadTabBarControllers];
    //修改Tabbar样式
    [self changeTabbarUI];
    
    //RAC绑定
    [self bindTabBarEvent];

    [self bindModel];
    
//    //检查是否有新的TabImage需要下载
//    [self.viewModel checkTabbarItemImageNeedDown];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - 加载Tabbar的Controllers
- (void)loadTabBarControllers {
    [self setViewControllers: self.viewModel.tabbarControllers];
    
    if (self.viewModel.tabbarControllers.count > 0) {
        [BFRouter router].navi = (UINavigationController *)(self.viewModel.tabbarControllers[0]);
    }
}

#pragma mark - 用RAC绑定Tabbar上的各个事件
- (void)bindTabBarEvent {
//    for (int i=0; i<[self.viewModel.tabbarBadgeList count]; i++) {
//        WDBadgeModel *badge = self.viewModel.tabbarBadgeList[i];
//        [RACObserve(badge, badgeNum) subscribeNext:^(NSNumber *badgeNum) {
//            if ([badgeNum intValue] > 0) {
//                //红点显示提醒
//                [self.tabBar showBadgeOnItemIndex: badge.badgeIndex];
//            }
//            else {
//                //红点隐藏提醒
//                [self.tabBar hideBadgeOnItemIndex: badge.badgeIndex];
//            }
//        }];
//    }
    
    dispatch_async(dispatch_get_main_queue(), ^{
        [RACObserve([UserManager manager], messageCount) subscribeNext:^(NSNumber *badgeNum) {
            if ([badgeNum intValue] > 0) {
                //红点显示提醒
                [self.tabBar showBadgeOnItemIndex:2];
                
            }
            else {
                //红点隐藏提醒
                [self.tabBar hideBadgeOnItemIndex: 2];
            }
        }];
    });
}


#pragma mark - 修改Tabbar样式
-(void)changeTabbarUI {
    
//    NSLog(@"self.tabBar frame %@, count %lu",NSStringFromCGRect(self.tabBar.frame),(unsigned long)[self.tabBar.subviews count]);
//    UIView *line = [[UIView alloc] init];
//    line.frame = CGRectMake(0, 0, self.tabBar.frame.size.width, 1);
//
//    line.backgroundColor = [UIColor dc1Color];
    //把一条线插入到 tabbargroundView上面
//    [self.tabBar insertSubview:line belowSubview:self.tabBar.subviews[0]];

    
//    UIColor *titleColor = [UIColor tc3Color];
//    //设置选择字体颜色
//    [[UITabBarItem appearance] setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
//                                                       titleColor, NSForegroundColorAttributeName,
//                                                       [UIFont fontWithName: @"Helvetica" size: 10.0], NSFontAttributeName,
//                                                       nil] forState:UIControlStateNormal];
//    
//    titleColor = [UIColor tc0Color];
//    [[UITabBarItem appearance] setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
//                                                       titleColor, NSForegroundColorAttributeName,
//                                                       nil] forState:UIControlStateSelected];
    //TabBar 颜色修改
    [self.tabBar setBarTintColor: [UIColor whiteColor]];
//    [self.tabBar insertSubview:self.backImageView belowSubview:self.tabBar.subviews[0]];
    
    self.tabBar.backgroundImage = [[UIImage alloc]init];
    self.tabBar.shadowImage = [[UIImage alloc]init];

}



- (UIImage *)reSizeImage:(UIImage *)image toSize:(CGSize)reSize {
    
    UIGraphicsBeginImageContextWithOptions(reSize, NO, [UIScreen mainScreen].scale);
    
    [image drawInRect:CGRectMake(0, 0, reSize.width, reSize.height)];
    UIImage *reSizeImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return reSizeImage;
    
}

#pragma mark - 改变Tabbar的图片
//- (void)changeTabarImages {
//
//    SDImageCache *cache = [SDImageCache  sharedImageCache];
//    
//    for (int i=0; i<[self.viewModel.tabModel.tabImages count]; i++) {
//        
//        NSArray * parms = [self.viewModel.tabModel.tabImages objectAtIndex:i];
//        
//        if (parms.count > 1) {
//            UIViewController *vc = ((BaseNavigationController*)self.viewControllers[i]).viewControllers[0];
//            UIImage * higthImage = nil;
//            UIImage * noramImage = nil;
//            if (self.viewModel.tabModel.isBundleImage) {
//                higthImage = [UIImage imageNamed: parms[0]];
//                noramImage = [UIImage imageNamed: parms[1]];
//            }
//            else {
//                higthImage = [self reSizeImage: [cache imageFromDiskCacheForKey: parms[0]] toSize: CGSizeMake( 49.0, 49.0)];
//                noramImage = [self reSizeImage: [cache imageFromDiskCacheForKey: parms[1]] toSize: CGSizeMake( 49.0, 49.0)];
//            }
//            if(noramImage.size.height == 67.||noramImage.size.height == 134.||noramImage.size.height == 268.){
//                vc.tabBarItem.imageInsets = UIEdgeInsetsMake(-4, 0, 4, 0);
//                
//            }else{
//                vc.tabBarItem.imageInsets = UIEdgeInsetsMake(8, 0, -8, 0);
//            }
//            [vc.tabBarItem setSelectedImage:[higthImage imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
//            [vc.tabBarItem setImage:[noramImage imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
//        }
//        else {
//            if (self.viewModel.tabModel.isBundleImage)
//                self.backImageView.image = [UIImage imageNamed: parms[0]];
//            else
//                self.backImageView.image = [cache imageFromDiskCacheForKey: parms[0]];
//        }
//        
//    }
//}

- (void)bindModel{
    
//    [RACObserve(self.viewModel, tabModel)subscribeNext:^(TabbarItemModel *tabModel) {
//        //更改Tab图片
//        [self changeTabarImages];
//    }];
}


@end
