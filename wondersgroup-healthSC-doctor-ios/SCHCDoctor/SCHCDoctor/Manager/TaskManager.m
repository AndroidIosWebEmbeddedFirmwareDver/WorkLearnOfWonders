//
//  TaskManager.m
//  VaccinePatient
//
//  Created by Jam on 16/5/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "TaskManager.h"
#import "TaskService.h"

@interface TaskManager ()

@end

@implementation TaskManager

+ (TaskManager *)manager {
    
    static dispatch_once_t onceToken;
    static TaskManager *manager = nil;
    dispatch_once(&onceToken, ^{
        manager = [[TaskManager alloc] init];
    });
    return manager;
}

-(instancetype)init {
    self = [super init];
    if (self) {
        
    }
    return self;
}


-(void)setMyCityCode:(NSString *)cityCode andCityName:(NSString *)cicyName{

 [[UserDefaults shareDefaults]setObject:cityCode forKey:@"UsersCityCode"];
 [[UserDefaults shareDefaults]setObject:cicyName forKey:@"UsersCityName"];

}

-(NSString *)getCityName{

return [[UserDefaults shareDefaults]objectForKey:@"UsersCityName"];
}
- (NSString *)getMyCityCode{
    
    return [[UserDefaults shareDefaults]objectForKey:@"UsersCityCode"];
}
@end
