//
//  SCAppointmentDoctorDateViewModel.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDoctorDateViewModel.h"

@implementation SCAppointmentDoctorDateViewModel

-(NSMutableArray *)dates {
    if (_dates == nil) {
        _dates = [NSMutableArray array];
    }
    return _dates;
}

-(void)getAppointmentDate {
    
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:@{}];
    [self.adapter request:GETSYSTEMTIME params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSString *dateStr = response.data[@"date"];
        
        NSDate *date = [NSDate dateFromString:dateStr withFormat:@"yyyy-MM-dd"];
        
        for (int i = 0; i < 7; i++) {
            
            NSDate *nextDate = [date mt_startOfNextDay];
            SCAppointmentDoctorDateModel *model = [SCAppointmentDoctorDateModel new];
            model.date = [nextDate stringWithFormat:@"yyyy-MM-dd"];
            model.week = [self week:nextDate];
            date = nextDate;
            [self.dates addObject:model];
        }
        
        self.dateRequestCompeleteType = RequestCompeleteSuccess;

    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
    }];
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

-(void)getAppointmentDoctorWithDate{
    
    self.moreParams = [NSDictionary dictionary];
    self.hasMore = NO;
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }

    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:@{
                                                                                  @"hospitalCode":self.hospitalCode,
                                                                                  @"hosDeptCode":self.hosDeptCode,
                                                                                  @"time":self.time}];
    
    [self.adapter request:QUERYSCHEDULBYTIME params:params class:[SCAppointmentDetailModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        self.datas = listModel.content;
        self.hasMore = listModel.more.boolValue;
        if (self.hasMore == NO) {
            self.moreParams = nil;
        }
        else
        {
            self.moreParams = listModel.more_params;
        }
        
        if (self.datas.count) {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }else {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
    }];
}

-(void)getAppointmentDoctorMoreWithDate {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:@{
                                                                                  @"hospitalCode":self.hospitalCode,
                                                                                  @"hosDeptCode":self.hosDeptCode,
                                                                                  @"time":self.time}];
    
    if (self.moreParams) {
        [params addEntriesFromDictionary:self.moreParams];
    }
    
    [self.adapter request:QUERYSCHEDULBYTIME params:params class:[SCAppointmentDetailModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        self.datas = [self.datas arrayByAddingObjectsFromArray:listModel.content];
        self.hasMore = listModel.more.boolValue;
        if (self.hasMore == NO) {
            self.moreParams = nil;
        }
        else
        {
            self.moreParams = listModel.more_params;
        }
        
        if (self.datas.count) {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }else {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
    }];
}

@end
