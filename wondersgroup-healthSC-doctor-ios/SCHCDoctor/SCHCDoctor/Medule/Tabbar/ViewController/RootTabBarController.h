//
//  RootTabBarController.h
//  EyeProtection
//
//  Created by Jam on 16/2/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
//--------------------------------------------------------------
/*  RootTabBarController
 *
 *  TabBarController
 */
//--------------------------------------------------------------

@interface RootTabBarController : UITabBarController
<UINavigationControllerDelegate>


//TabBar当前选中的下标
@property (nonatomic, assign) int currentSelectedIndex;

@end
