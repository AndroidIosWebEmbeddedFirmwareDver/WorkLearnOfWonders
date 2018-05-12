//
//  DoctorDetailViewModel.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCDoctorSchedulingViewModel.h"
#import "SCAppointmentDoctorDateModel.h"

@implementation SCDoctorSchedulingViewModel

//获取预约医生排班详情
//-(void)getDoctorScheduling {
- (void)getDoctorScheduling:(void (^)(void))success failure:(void (^)(NSError *))failure {
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:@{
                                                                                  @"hospitalCode":self.hospitalCode,
                                                                                  @"hosDeptCode":self.hosDeptCode,
                                                                                  @"hosDoctCode":self.hosDoctCode}];
    
    //测试数据 hospitalCode=450755531&hosDeptCode=29&hosDoctCode=4
//    NSMutableDictionary *params = [NSMutableDictionary dictionary];
//    [params setObject:@"450755531" forKey:@"hospitalCode"];
//    [params setObject:@"29" forKey:@"hosDeptCode"];
//    [params setObject:@"4" forKey:@"hosDoctCode"];
    
    
    [self.adapter request:QUERYSCHEDULINFO params:params class:[SCDoctorSchedulingModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        [self.dates removeAllObjects];
        
        if (response.data) {
            self.model = response.data;
            
            NSString *dateStr = self.model.systemTime;
            
            NSDate *date = [NSDate dateFromString:dateStr withFormat:@"yyyy-MM-dd"];
            
            for (int i = 0; i < 7; i++) {
                
                NSDate *nextDate = [date mt_startOfNextDay];
                SCAppointmentDoctorDateModel *model = [SCAppointmentDoctorDateModel new];
                model.date = [nextDate stringWithFormat:@"yyyy-MM-dd"];
                model.week = [self week:nextDate];
                date = nextDate;
                [self.dates addObject:model];
            }
            
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        else {
            self.message = response.message;
        }
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
        failure(error);
    }];
    
}

-(NSMutableArray *)dates {
    if (_dates == nil) {
        _dates = [NSMutableArray array];
    }
    return _dates;
}

- (NSString *)week:(NSDate *)date{
    NSCalendar *calendar = [[NSCalendar alloc] initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
    NSDateComponents *comps = [[NSDateComponents alloc] init];
    NSInteger unitFlags = NSCalendarUnitYear |
    NSCalendarUnitMonth |
    NSCalendarUnitDay |
    NSCalendarUnitWeekday |
    NSCalendarUnitHour |
    NSCalendarUnitMinute |
    NSCalendarUnitSecond;
    comps = [calendar components:unitFlags fromDate:date];
    
    return [self weekStr:[comps weekday]];
    
}

-(NSString *)weekStr:(NSInteger)week {
    switch (week) {
            case 2:
            return @"周一";
            break;
            case 3:
            return @"周二";
            break;
            case 4:
            return @"周三";
            break;
            case 5:
            return @"周四";
            break;
            case 6:
            return @"周五";
            break;
            case 7:
            return @"周六";
            break;
            case 1:
            return @"周日";
            break;
            
        default:
            return @"";
            break;
    }
}


@end
