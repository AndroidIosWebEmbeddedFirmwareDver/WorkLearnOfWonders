//
//  SCThreeLoginView.h
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void (^StartThreeLogin)();
typedef void (^ThreeLoginSuccess)();
typedef void (^ThreeLoginFailure)(NSString *massage);
@interface SCThreeLoginView : UIView
@property (nonatomic, copy) StartThreeLogin startLogin;
@property (nonatomic, copy) ThreeLoginSuccess loginSuccess;
@property (nonatomic, copy) ThreeLoginFailure loginFailure;
@end
