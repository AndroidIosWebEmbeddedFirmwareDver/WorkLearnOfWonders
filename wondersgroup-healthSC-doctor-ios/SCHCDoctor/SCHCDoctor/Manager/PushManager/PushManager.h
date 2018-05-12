//
//  PushManager.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/22.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "PushModel.h"

@interface PushManager : NSObject
+ (instancetype)manager;
- (void)addPushModel:(PushModel *)pushModel;
- (void)handelPushFromBackGround:(BOOL)isFromBackGround;
@end
