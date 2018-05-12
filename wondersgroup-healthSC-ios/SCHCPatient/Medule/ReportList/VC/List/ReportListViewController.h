//
//  ReportListViewController.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"

@interface ReportListViewController : BaseViewController

@property (copy  , nonatomic) NSString  *medicalOrgId;//医疗机构代码
@property (strong, nonatomic) NSNumber  *day;//查询时间区间

@end
