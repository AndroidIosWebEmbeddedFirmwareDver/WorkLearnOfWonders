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

@interface TaskService : NSObject <CommonIMPL>



+ (TaskService *)service;

- (void)startService;

@end
