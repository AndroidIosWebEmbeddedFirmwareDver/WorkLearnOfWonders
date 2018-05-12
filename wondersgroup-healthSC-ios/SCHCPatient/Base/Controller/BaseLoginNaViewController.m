//
//  BaseLoginViewController.m
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseLoginNaViewController.h"
#import "BaseNavigationBar.h"

@interface BaseLoginNaViewController ()

@end

@implementation BaseLoginNaViewController

- (instancetype)init {
    self = [super initWithNavigationBarClass:[BaseNavigationBar class] toolbarClass:nil];
    if(self) {
        // Custom initialization here, if needed.
        [self.navigationBar setBarTintColor: [UIColor tc5Color]];
        
    }
    return self;
}

- (id)initWithRootViewController:(UIViewController *)rootViewController {
    self = [super initWithNavigationBarClass:[BaseNavigationBar class] toolbarClass:nil];
    if(self) {
        NSLog(@"%@", rootViewController);
        self.viewControllers = @[rootViewController];
        [self.navigationBar setBarTintColor: [UIColor tc5Color]];
    }
    
    return self;
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    
//    self.navigationBar.barStyle = UIBarStyleDefault;
//    //    [[UIApplication sharedApplication] setStatusBarStyle: UIStatusBarStyleLightContent];
//    [[UINavigationBar appearance] setTitleTextAttributes:@{
//                                                           NSFontAttributeName            : [UIFont fontWithName:@"STHeitiSC-Light" size:18],
//                                                           NSForegroundColorAttributeName : [UIColor whiteColor]
//                                                           }];
//    //设置左边返回箭头颜色
//   self.navigationBar.tintColor = [UIColor whiteColor];
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
                                                           NSForegroundColorAttributeName : [UIColor whiteColor]
                                                           }];
    //设置左边返回箭头颜色
    self.navigationBar.tintColor = [UIColor whiteColor];
    //改变导航栏下面的线
    [self.navigationBar setBottomBorderColor:[UIColor dc1Color] height:0.5];
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
