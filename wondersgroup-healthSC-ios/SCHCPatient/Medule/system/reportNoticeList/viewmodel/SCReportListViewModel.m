//
//  SystemDetailViewModel.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCReportListViewModel.h"
#import "SCReportListModel.h"
@implementation SCReportListViewModel


-(void) getReportRequest
{

    NSMutableArray * a = [NSMutableArray new];
    {
    SCReportListModel * m = [[SCReportListModel alloc] init];
    m.title=@"检查报告";
    m.paitentName=@"张三";
    m.PaitentId=@"123";
    m.sex=@0;
    m.age=@23;
    m.hospitalName=@"成都市华西口腔医院成都市华西口腔医院成都市华西口腔医院成都市华西口腔医院";
    m.hospitalId=@"123123";
    m.checkProject=@"腹部立位";
    m.checkPart=@"腹部";
    m.department=@"CT";
    m.checkDate=@"2016-088-01";
    m.reportDate=@"2016-0117-01";
    m.date=@"2016-07-01";
    [a addObject:m];
    }
    {
        SCReportListModel * m = [[SCReportListModel alloc] init];
        m.title=@"检查报告";
        m.paitentName=@"张三";
        m.PaitentId=@"123";
        m.sex=@0;
        m.age=@23;
        m.hospitalName=@"成都市华西口腔医院成都市华西口腔医院成都市华西口腔医院成都市华西口腔医院";
        m.hospitalId=@"123123";
        m.checkProject=@"腹部立位";
        m.checkPart=@"腹部";
        m.department=@"CT";
        m.checkDate=@"2016-088-01";
        m.reportDate=@"2016-0117-01";
        m.date=@"2016-07-01";
        [a addObject:m];
    }
    
    
    
    self.dataArray = a.copy;
}
@end
