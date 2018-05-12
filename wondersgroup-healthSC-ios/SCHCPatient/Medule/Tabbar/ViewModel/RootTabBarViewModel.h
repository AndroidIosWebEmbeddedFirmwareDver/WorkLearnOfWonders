//
//  RootTabBarViewModel.h
//  EyeProtection
//
//  Created by Jam on 16/2/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "TabbarItemModel.h"

@interface RootTabBarViewModel : BaseViewModel


@property (nonatomic, strong) NSArray *tabbarControllers;


@property (nonatomic, strong) TabbarItemModel *tabModel;
@property (nonatomic, assign) BOOL showLocalTabImage;

   


- (void)checkTabbarItemImageNeedDown;

@end
