//
//  CheckReportViewController.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "ReportViewModel.h"
@interface CheckReportViewController : BaseViewController

@property (strong, nonatomic) ReportViewModel *viewModel;
- (void)requestData;

@end
