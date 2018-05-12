//
//  ThreeLoginViewModel.h
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import <ShareSDK/ShareSDK.h>
@interface ThreeLoginViewModel : BaseViewModel


- (void)thirdpartyLogin: (NSDictionary *)params type:(SSDKPlatformType)type
                Success: (void (^)(void))success
                failure: (void (^)(NSError *error))failure;


@end
