//
//  TakeReportViewController.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"

typedef NS_ENUM(NSInteger, ReportCategory) {
      TakenReport,
      ElectronicReport,
};

typedef NS_ENUM(NSInteger,ChoosReportTime) {
    Today = 0,        //今天
    //ThreeDaysNearBy,    //最近三天
    //WeekDayNearBy,      //最近一周
    MonthNearBy,       // 最近一个月
    SixMonthsNearBy,   // 最近六个月
};

typedef NS_ENUM(NSInteger,ChoosReportTime1) {
    ChoosReportTimeToday = 0,        //今天
    ChoosReportTimeThreeDaysNearBy,    //最近三天
    ChoosReportTimeWeekDayNearBy,      //最近一周
    ChoosReportTimeMonthNearBy,       // 最近一个月
    
};

@interface TakeReportViewController : BaseViewController

@property (assign, nonatomic) ChoosReportTime reportTime;//提取报告
@property (assign, nonatomic) ChoosReportTime1 reportTime1;//电子处方
@property (assign, nonatomic) ReportCategory  type;
@end
