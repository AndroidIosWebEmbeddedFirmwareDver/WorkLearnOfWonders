//
//  TaskService.h
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "TaskManager.h"
#import "ViewModelIMPL.h"
#import <HyphenateLite/HyphenateLite.h>
#import "EaseUI.h"

@interface TaskService : NSObject <CommonIMPL,EMClientDelegate>



+ (TaskService *)service;

- (void)startService;

@end
