//
//  BaseNavigationController.m
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseNavigationController.h"
#import "BaseNavigationBar.h"

@interface BaseNavigationController ()

@end

@implementation BaseNavigationController

- (instancetype)init {
    self = [super initWithNavigationBarClass:[BaseNavigationBar class] toolbarClass:nil];
    if(self) {
        // Custom initialization here, if needed.
        [self.navigationBar setBarTintColor: [UIColor tc0Color]];
        
    }
    return self;
}

- (id)initWithRootViewController:(UIViewController *)rootViewController {
    self = [super initWithNavigationBarClass:[BaseNavigationBar class] toolbarClass:nil];
    if(self) {
        NSLog(@"%@", rootViewController);
         self.viewControllers = @[rootViewController];
        [self.navigationBar setBarTintColor: [UIColor tc0Color]];
//        [self.navigationBar setBottomBorderColor:[UIColor redColor] height:0.5];
    }
    
    return self;
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    
//    self.navigationBar.barStyle = UIBarStyleDefault;
////    [[UIApplication sharedApplication] setStatusBarStyle: UIStatusBarStyleLightContent];
//    [[UINavigationBar appearance] setTitleTextAttributes:@{
//                                                           NSFontAttributeName            : [UIFont fontWithName:@"STHeitiSC-Light" size:18],
//                                                           NSForegroundColorAttributeName : [UIColor tc1Color]
//                                                           }];
//    //设置左边返回箭头颜色
//    self.navigationBar.tintColor = [UIColor lightGrayColor];
//    //改变导航栏下面的线
//    [self.navigationBar setBottomBorderColor:[UIColor dc1Color] height:0.5];
    
}
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationBar.barStyle = UIBarStyleDefault;
    //    [[UIApplication sharedApplication] setStatusBarStyle: UIStatusBarStyleLightContent];
    [[UINavigationBar appearance] setTitleTextAttributes:@{
                                                           NSFontAttributeName            : [UIFont fontWithName:@"STHeitiSC-Light" size:18],
                                                           NSForegroundColorAttributeName : [UIColor tc1Color]
                                                           }];
    //设置左边返回箭头颜色
    self.navigationBar.tintColor = [UIColor lightGrayColor];
    //改变导航栏下面的线
    [self.navigationBar setBottomBorderColor:[UIColor dc1Color] height:0.5];
}

@end
